package com.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	//get department by id
	@GetMapping("/departments/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	public ResponseEntity<Object> departmentbyid(@PathVariable Long id){
		if(id==null) {
			throw new IllegalArgumentException("department id not found");
		}
		return departmentService.department(id);
	}
	
	//update department by id
	@PutMapping("/departments/{deparmentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> updateRecord(@RequestBody DepartmentDto dto,@PathVariable Long deparmentId){
		if(deparmentId==null) {
			throw new IllegalArgumentException("department id not found");
		}
		return departmentService.updateDepartment(dto, deparmentId);
	}
	
	//delete department by id
	@DeleteMapping("/departments/{deparmentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> deleteRecord(@PathVariable Long deparmentId){
		if(deparmentId==null) {
			throw new IllegalArgumentException("department id not found");
		}
		return departmentService.deleteDepartment(deparmentId);
	}
	
}
