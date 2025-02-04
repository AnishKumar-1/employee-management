package com.management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.management.dto.DepartmentDto;
import com.management.dto.EmployeeDto;
import com.management.dto.UserDto;
import com.management.exception.ResourceNotFoundException;
import com.management.models.DepartmentModel;
import com.management.models.EmployeeModel;
import com.management.models.UserModel;
import com.management.repository.DepartmentRepo;
import com.management.repository.EmployeeRepo;
import com.management.repository.UserModelRepo;

import java.util.ArrayList;
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

	// create employee other data will receive through body
	// employee id will receive through path variable
	// department id will receive through path variable
	public ResponseEntity<Object> createEmployee(Long userId, Long departmentId, EmployeeDto employee) {

		UserModel user = userRepo.findById(userId)
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
		EmployeeDto employeeDto = new EmployeeDto();

		employeeDto.setId(result.getId());
		UserDto userDto = new UserDto(result.getUser().getId(), result.getUser().getEmail(),
				result.getUser().getRole().getName());
		employeeDto.setUser(userDto);
		DepartmentDto departmentDto = new DepartmentDto(result.getDepartment().getId(),
				result.getDepartment().getName(), result.getDepartment().getDescription());
		employeeDto.setFirstName(result.getFirstName());
		employeeDto.setLastName(result.getLastName());
		employeeDto.setPhoneNumber(result.getPhoneNumber());
		employeeDto.setSalary(result.getSalary());
		employeeDto.setDepartment(departmentDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeDto);
	}

	// get all employee
	public ResponseEntity<Object> employees() {

		List<EmployeeModel> employee = employeeRepo.findAll();
		List<EmployeeDto> dto = new ArrayList<>();

		for (EmployeeModel employeeModel : employee) {
			DepartmentDto departmentDto = new DepartmentDto();

			departmentDto.setId(employeeModel.getDepartment().getId());
			departmentDto.setName(employeeModel.getDepartment().getName());
			departmentDto.setDescription(employeeModel.getDepartment().getDescription());
			UserDto userDto = new UserDto();
			userDto.setUser_id(employeeModel.getUser().getId());
			userDto.setEmail(employeeModel.getUser().getEmail());
			userDto.setRole(employeeModel.getUser().getRole().getName());
			EmployeeDto employeeDto = new EmployeeDto(employeeModel.getId(), userDto, employeeModel.getFirstName(),
					employeeModel.getLastName(), employeeModel.getPhoneNumber(), employeeModel.getSalary(),
					departmentDto);
			dto.add(employeeDto);
		}
		return ResponseEntity.ok(dto);

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

}
