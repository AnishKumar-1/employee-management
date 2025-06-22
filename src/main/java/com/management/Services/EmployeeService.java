package com.management.Services;
import com.management.models.Users;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import com.management.dto.EmployeeDto;
import com.management.exception.ResourceNotFoundException;
import com.management.models.DepartmentModel;
import com.management.models.EmployeeModel;

import com.management.repository.DepartmentRepo;
import com.management.repository.EmployeeRepo;
import com.management.repository.UserModelRepo;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private UserModelRepo userRepo;
	@Autowired
	private DepartmentRepo departmentRepo;
	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private ModelMapper modelMapper;

	// create employee other data will receive through body
	// employee id will receive through path variable
	// department id will receive through path variable
	public ResponseEntity<Object> createEmployee(Long userId, Long departmentId, EmployeeDto employee) {

		Users user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		// Check if department exists, otherwise throw exception
		DepartmentModel department = departmentRepo.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

		EmployeeModel employeeModel = new EmployeeModel();
		employeeModel.setFirstName(employee.getFirstName());
		employeeModel.setLastName(employee.getLastName());
		employeeModel.setUser(user);
		employeeModel.setPhoneNumber(employee.getPhoneNumber());
		employeeModel.setSalary(employee.getSalary());
		employeeModel.setDepartment(department);

		EmployeeModel result = employeeRepo.save(employeeModel);

		return ResponseEntity.status(HttpStatus.CREATED).body(convertToEmployeeDto(result));
	}

	// get all employee
//	public ResponseEntity<Object> employees() {
//		List<EmployeeModel> allEmp = employeeRepo.findAll();
//		EmployeeDto result = modelMapper.map(allEmp, EmployeeDto.class);
//		return ResponseEntity.ok(result);
//	}
	
	
	public ResponseEntity<Object> employees() {
	    List<EmployeeModel> allEmp = employeeRepo.findAll();
	    Type targetListType = new TypeToken<List<EmployeeDto>>() {}.getType();
	    List<EmployeeDto> result = modelMapper.map(allEmp, targetListType);
	    return ResponseEntity.ok(result);
	}

	// delete employees by employee id
	public ResponseEntity<Void> deleteEmployees(Long employeeId) {
		if (!employeeRepo.existsById(employeeId)) {
			throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
		}
		employeeRepo.deleteById(employeeId);
		return ResponseEntity.noContent().build();
	}

	// update employee details
	// will get employee id through pathvariable to update perticular employee
	// details
	// get first name and lastname, phone number and salary
	public ResponseEntity<Object> updateEmployee(Long employeeId, EmployeeDto employee) {
		Optional<EmployeeModel> empModel = employeeRepo.findById(employeeId);
		if (!empModel.isPresent()) {
			throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
		}

		EmployeeModel storedResult = empModel.get();
		storedResult.setFirstName(employee.getFirstName());
		storedResult.setLastName(employee.getLastName());
		storedResult.setPhoneNumber(employee.getPhoneNumber());
		storedResult.setSalary(employee.getSalary());
		employeeRepo.save(storedResult);

		return ResponseEntity.ok("Employee updated successfully.");

	}

	// get employee by its id
	// only admin manager and self employee can get this endpoint

	public ResponseEntity<Object> employeeById(Long employeeId) {
		Optional<EmployeeModel> empModel = employeeRepo.findById(employeeId);
		if (!empModel.isPresent()) {
			throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
		}
		EmployeeModel employee = empModel.get();

		// 🔹 Get the current logged-in user
		Users currentUser = userProfileService.getProfile();
		String currentUserRole = currentUser.getRole().getName();

		// ✅ Corrected authorization check
		if (!(currentUserRole.equals("ADMIN") || currentUserRole.equals("MANAGER")
				|| currentUser.getId().equals(employee.getUser()))) {
			throw new AuthorizationDeniedException("You are not authorized to access this resource");
		}

		return ResponseEntity.ok(convertToEmployeeDto(employee));
	}

	private EmployeeDto convertToEmployeeDto(EmployeeModel emp) {
		return modelMapper.map(emp, EmployeeDto.class);
	}

}
