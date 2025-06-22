package com.management.controllers;

import com.management.dto.departmentDto.DepartmentRequest;
import com.management.dto.departmentDto.DepartmentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api")
@Tag(name="Department Apis",description = "create,update,get,delete")
//@SecurityRequirement(name = "bearerAuth")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	// create department
	@PostMapping("/departments")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(
			summary = "Create department.",
			description = "Only Admin can create"
	)
	public ResponseEntity<DepartmentResponse> create(@RequestBody DepartmentRequest dto) {
		return new ResponseEntity<>(departmentService.createDepartment(dto), HttpStatus.CREATED);
	}

	// get all departments
	@GetMapping("/departments")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
	@Operation(
			summary = "get all departments.",
			description = "only Admin and Manager can get."
	)
	public ResponseEntity<Object> departments() {
		return departmentService.listDepartments();
	}
	
	//get department by id
	@GetMapping("/departments/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@Operation(
			summary = "get department by department id.",
			description = "only Admin and Manager can get."
	)
	public ResponseEntity<Object> departmentbyid(@PathVariable Long id){
		if(id==null) {
			throw new IllegalArgumentException("department id not found");
		}
		return departmentService.department(id);
	}
	
	//update department by id
	@PutMapping("/departments/{deparmentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(
			summary = "update department by department id.",
			description = "only Admin can update."
	)
	public ResponseEntity<String> updateRecord(@RequestBody DepartmentRequest dto,@PathVariable Long deparmentId){
		if(deparmentId==null) {
			throw new IllegalArgumentException("department id not found");
		}
		return departmentService.updateDepartment(dto, deparmentId);
	}
	
	//delete department by id
	@DeleteMapping("/departments/{deparmentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(
			summary = "delete department by department id.",
			description = "only Admin can delete it."
	)
	public ResponseEntity<Object> deleteRecord(@PathVariable Long deparmentId){
		if(deparmentId==null) {
			throw new IllegalArgumentException("department id not found");
		}
		return departmentService.deleteDepartment(deparmentId);
	}
	
}
