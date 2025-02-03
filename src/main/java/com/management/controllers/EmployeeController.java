package com.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.Services.EmployeeService;
import com.management.dto.EmployeeDto;

import jakarta.validation.Valid;

@RequestMapping("/api")
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// create employee
	// require userid from pathvariable
	// require department id from pathvariable
	@PostMapping("/employees/{userId}/{departmentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
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
	public ResponseEntity<Object> allEmployee() {
		return employeeService.employees();
	}
}
