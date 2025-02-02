package com.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.Services.DepartmentService;
import com.management.dto.DepartmentDto;

@RestController
@RequestMapping("/api")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	// create department
	@PostMapping("/departments")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<DepartmentDto> create(@RequestBody DepartmentDto dto) {
		return new ResponseEntity<>(departmentService.createDepartment(dto), HttpStatus.CREATED);
	}

	// get all departments
	@GetMapping("/departments")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
	public ResponseEntity<Object> departments() {
		return departmentService.listDepartments();
	}
}
