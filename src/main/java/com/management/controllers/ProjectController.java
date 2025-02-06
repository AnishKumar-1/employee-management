package com.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.management.Services.ProjectService;
import com.management.dto.ProjectDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	//create project
	@PostMapping("/project/{employee_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> create(@PathVariable Long employee_id,@Valid @RequestBody ProjectDto project) throws NoResourceFoundException{
		if(employee_id==null) {
			throw new NoResourceFoundException(null, "Employee id not found.");
		}
		return projectService.createProject(employee_id, project);
	}
	
	//project delete by id
	@DeleteMapping("/project/{project_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> deleteProject(@PathVariable Long project_id) {
	    // Delete the project by ID
		projectService.deleteProject(project_id);

	    // Return a 204 No Content response
	    return ResponseEntity.noContent().build();
	}
}
