package com.management.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.Services.EmployeeService;
import com.management.dto.EmployeeDto;

import jakarta.validation.Valid;

@RequestMapping("/api")
@RestController
@Tag(name="Employee Apis",description = "create,update,get,delete")
//@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// create employee
	// require userid from pathvariable
	// require department id from pathvariable
	@PostMapping("/employees/{userId}/{departmentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(
			summary = "create employee record.",
			description = "only Admin can create."
	)
	public ResponseEntity<Object> createEmp(@PathVariable("userId") Long userId,
			@PathVariable("departmentId") Long departmentId, @Valid @RequestBody EmployeeDto dto) {
		if (userId == null) {
			throw new IllegalArgumentException("User id is required.");
		}
		if (departmentId == null) {
			throw new IllegalArgumentException("Department id is required.");
		}

		return employeeService.createEmployee(userId, departmentId, dto);
	}

	// get all employee
	@GetMapping("/employees")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@Operation(
			summary = "get all employee records.",
			description = "only Admin and Manager can get."
	)
	public ResponseEntity<Object> allEmployee() {
		return employeeService.employees();
	}

	// delete employee by employee id
	// employee id will get through pathvariable

	@DeleteMapping("/employees/{employeeId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(
			summary = "delete employee record by its id.",
			description = "only Admin can delete it."
	)
	public ResponseEntity<Void> deleteEmpoyee(@PathVariable Long employeeId) {
		if (employeeId == null) {
			throw new IllegalArgumentException("Employee id is required.");
		}
		return employeeService.deleteEmployees(employeeId);
	}
	
	//update employee details by its id
	//employee id will get through pathvariable 
	@PutMapping("/employees/{employeeId}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@Operation(
			summary = "update employee record by its id.",
			description = "only Admin and Manager can update it."
	)
	public ResponseEntity<Object> employeeUpdate(@PathVariable Long employeeId,@Valid @RequestBody EmployeeDto dto){
		if (employeeId == null) {
			throw new IllegalArgumentException("Employee id is required.");
		}
		return employeeService.updateEmployee(employeeId, dto);
	}
	
	//get employee by its id
	@GetMapping("/employees/{employeeId}")
	@Operation(
			summary = "get employee record by its id.",
			description = "only Admin, Manager and Employee(self) can get record."
	)
	public ResponseEntity<Object> empById(@PathVariable Long employeeId){
		if (employeeId == null) {
			throw new IllegalArgumentException("Employee id is required.");
		}
		return employeeService.employeeById(employeeId);
	}
}
