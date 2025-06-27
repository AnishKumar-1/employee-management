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

@RestController
@RequestMapping("/api")
@Tag(name="Department Apis",description = "create,update,get,delete")
//@SecurityRequirement(name = "bearerAuth")
public class DepartmentController {

//	@Autowired
//	private DepartmentService departmentService;
//
//	// create department
//	@PostMapping("/departments")
//	@PreAuthorize("hasRole('ADMIN')")
//	@Operation(
//			summary = "Create department.",
//			description = "Only Admin can create"
//	)
//	public ResponseEntity<DepartmentResponse> create(@RequestBody DepartmentRequest dto) {
//		return new ResponseEntity<>(departmentService.createDepartment(dto), HttpStatus.CREATED);
//	}

}
