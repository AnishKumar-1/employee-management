package com.management.services;

import com.management.dto.employeeDto.EmployeeRequest;
import com.management.dto.employeeDto.EmployeeResponse;
import com.management.exception.ResourceNotFoundException;
import com.management.mappers.EmployeeMapper;
import com.management.models.*;
import com.management.repository.*;
import com.management.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final UserModelRepo userModelRepo;
    private final DepartmentRepo departmentRepo;
    private final DesignationRepo designationRepo;
    private final EmployeeMapper employeeMapper;
    private final Helper helper;

   /*
   *  create employee by using userid,department id and initial designation
   */

    public ResponseEntity<EmployeeResponse> createEmployee( EmployeeRequest request,Long userId, Long departmentId
    , Long designationId){

        //checking all id are not null
        if(userId==null)throw new IllegalArgumentException("user id is required");
        //finding user is exists with the given id or not
        Users users = userModelRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this ID: " + userId));

        if (employeeRepo.existsByUser_Id(users.getId())) {
            throw new IllegalArgumentException("Employee already exists for this user");
        }
        if(departmentId==null)throw new IllegalArgumentException("department id is required");
        if(designationId==null)throw new IllegalArgumentException("designation id is required");

        //finding department with department id
        DepartmentModel department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("department not found with this ID: " + departmentId));

        //finding designation
        DesignationModel designation = designationRepo.findById(designationId)
                .orElseThrow(() -> new ResourceNotFoundException("designation not found with this ID: " + designationId));

        //converting EmployeeRequest dto to EmployeeModel to save because,Employee model we have thats' why
        EmployeeModel employee=helper.toEmployeeModel(request,users,department,designation);

        EmployeeModel result=employeeRepo.save(employee);

        Set<String> projectName = result.getProjects() != null
                ? result.getProjects().stream().map(ProjectModel::getName).collect(Collectors.toSet())
                : new HashSet<>();
        EmployeeResponse response= helper.toEmployeeResponse(result,users,projectName);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //get employee by its id or admin can see it
    public ResponseEntity<EmployeeResponse> getEmployeeById(Long empId){
      EmployeeModel emp=employeeRepo.findById(empId).orElseThrow(()->new ResourceNotFoundException("employee not found with id: "+empId));
      String dbUserEmail=emp.getUser().getEmail();
      //current logged-in emp
       String currentEmpEmail=helper.currentLoggedInUserEmail();
       boolean isAdmin= helper.isAdmin();

        if (!isAdmin && !dbUserEmail.equals(currentEmpEmail)) {
            throw new AuthorizationDeniedException("Access denied");
        }

        Set<String> projects=emp.getProjects().stream().map(ProjectModel::getName).collect(Collectors.toSet());

        EmployeeResponse response=helper.toEmployeeResponse(emp,emp.getUser(),projects);
        return ResponseEntity.ok(response);

    }

    //get all employee
    public ResponseEntity<List<EmployeeResponse>> employees(){
       List<EmployeeModel> emp=employeeRepo.findAll();
       if(emp.isEmpty()){
           throw new ResourceNotFoundException("No record found");
       }
       Set<String> projects=emp.stream().flatMap(p->p.getProjects().stream())
               .map(ProjectModel::getName)
               .collect(Collectors.toSet());

       List<EmployeeResponse> responses=emp.stream().map(data->
               helper.toEmployeeResponse(data,data.getUser(),projects)).toList();
       return ResponseEntity.ok(responses);
    }




}
