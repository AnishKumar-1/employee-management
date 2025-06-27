package com.management.utils;

import com.management.dto.employeeDto.EmployeeRequest;
import com.management.dto.employeeDto.EmployeeResponse;
import com.management.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Helper {


    public EmployeeModel toEmployeeModel(EmployeeRequest request, Users users, DepartmentModel department, DesignationModel designation){
        return EmployeeModel.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .salary(request.getSalary())
                .user(users)
                .department(department)
                .designation(designation)
                .build();
    }

    public EmployeeResponse toEmployeeResponse(EmployeeModel result, Users users, Set<String> projectName){
        return EmployeeResponse
                .builder()
                .id(result.getId())
                .firstName(result.getFirstName())
                .lastName(result.getLastName())
                .email(users.getEmail())
                .phoneNumber(result.getPhoneNumber())
                .salary(result.getSalary())
                .departmentName(result.getDepartment().getName())
                .designation(result.getDesignation().getTitle())
                .projects(projectName).build();
    }


    //get current logged-in user details
    public String currentLoggedInUserEmail() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }


}
