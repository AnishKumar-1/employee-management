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
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.management.Services.ProjectService;
import com.management.dto.ProjectDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name="Project Apis",description = "create,update,delete,get")
//@SecurityRequirement(name = "bearerAuth")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	//create project
	@PostMapping("/project/{employee_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(
			summary = "create new project record.",
			description = "only Admin can create new project record."
	)
	public ResponseEntity<Object> create(@PathVariable Long employee_id,@Valid @RequestBody ProjectDto project){
		return projectService.createProject(employee_id, project);
	}
	
	//project delete by id
	@DeleteMapping("/project/{project_id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	@Operation(
			summary = "delete project record.",
			description = "only Admin can delete project record."
	)
	public ResponseEntity<Void> deleteProject(@PathVariable Long project_id) {
	    // Delete the project by ID
		projectService.deleteProject(project_id);

	    // Return a 204 No Content response
	    return ResponseEntity.noContent().build();
	}
	
	
	//get all projects
	@GetMapping("/projects")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	@Operation(
			summary = "get all projects.",
			description = "only Admin can get all projects."
	)
	public ResponseEntity<Object> projects(){
		return projectService.AllProject();
	}
	
	//project by id
	@GetMapping("/projects/{project_id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@Operation(
			summary = "get project by its id.",
			description = "only Admin and project Manager(self) can get the record."
	)
	public ResponseEntity<Object> projectByProjectId(@PathVariable Long project_id) throws NoResourceFoundException{
		return projectService.projectById(project_id);
	}
	
	//update project by its id
	@PutMapping("/projects/{project_id}")
	@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
	@Operation(
			summary = "update project details by its id.",
			description = "only Admin and project Manager can update it."
	)
	public ResponseEntity<Object> updateProjectById(@PathVariable Long project_id,@Valid @RequestBody ProjectDto dto){
		return projectService.updateProjectById(project_id, dto);
	}
	
	    //assign employee to project
		@PostMapping("/{project_id}/assign/{employee_id}")
		@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
		@Operation(
				summary = "create employee record in project by project ID and employee ID.",
				description = "only Admin and project Manager can create it."
		)
		public ResponseEntity<Object> assginEmpToProject(@PathVariable Long project_id,@PathVariable Long employee_id){
			return projectService.assginEmToProject(project_id, employee_id);
		}
}
