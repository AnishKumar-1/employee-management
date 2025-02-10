package com.management.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import com.management.dto.EmployeeDto;
import com.management.dto.ProjectDto;
import com.management.exception.DuplicateResourceException;
import com.management.exception.ResourceNotFoundException;
import com.management.models.EmployeeModel;
import com.management.models.ProjectModel;
import com.management.repository.EmployeeRepo;
import com.management.repository.ProjectRepo;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private ModelMapper modelMapper;

	// create project by manager id
	public ResponseEntity<Object> createProject(Long manager_id, ProjectDto project) {
		EmployeeModel manager = employeeRepo.findById(manager_id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + manager_id));

		String managerRole = manager.getUser().getRole().getName();

		if (!managerRole.equalsIgnoreCase("MANAGER")) {
			throw new AuthorizationDeniedException("Employee is not a manager");
		}

		// Check if a project with the same name already exists

		if (projectRepo.findByName(project.getName()).isPresent()) {
			throw new DuplicateResourceException("Project with name '" + project.getName() + "' already exists.");
		}

		ProjectModel projectModel = modelMapper.map(project, ProjectModel.class);
		projectModel.setManager(manager);

		ProjectModel savedProjectModel = projectRepo.save(projectModel);

		ProjectDto pDto = modelMapper.map(savedProjectModel, ProjectDto.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(pDto);
	}

	// get project by
	// delete project by its id
	public void deleteProject(Long project_id) {
		if (!projectRepo.existsById(project_id)) {
			throw new ResourceNotFoundException("project not found with id : " + project_id);
		}
		projectRepo.deleteById(project_id);
	}

	// get all project
	public ResponseEntity<Object> AllProject() {
		List<ProjectModel> projects = projectRepo.findAll();
		if (projects == null) {
			return ResponseEntity.ok("No data found");
		}
		return ResponseEntity.ok(projects.stream().map(sourceData -> modelMapper.map(sourceData, ProjectDto.class))
				.collect(Collectors.toList()));
	}

	// get project by id
	public ResponseEntity<Object> projectById(Long project_id) {
		ProjectModel project = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + project_id));

		return ResponseEntity.ok(modelMapper.map(project, ProjectDto.class));
	}

	// update project by project id
	public ResponseEntity<Object> updateProjectById(Long project_id, ProjectDto projectDto) {
		ProjectModel storedProject = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + project_id));
		storedProject.setName(projectDto.getName());
		storedProject.setDescription(projectDto.getDescription());
		projectRepo.save(storedProject);
		return ResponseEntity.ok("Project record updated successfully.");
	}

	// assign employee to a project
	// project_id/assign/empid
	public ResponseEntity<Object> assginEmToProject(Long project_id, Long emp_id) {
		ProjectModel storedProject = projectRepo.findById(project_id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + project_id));

		EmployeeModel storedEmployee = employeeRepo.findById(emp_id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + emp_id));

		Set<EmployeeModel> employeesSet = storedProject.getEmployee();
		   if (employeesSet == null) {
		        employeesSet = new HashSet<>();
		    }
		employeesSet.add(storedEmployee);
		storedProject.setEmployee(employeesSet);
		   // Save the updated project
	    projectRepo.save(storedProject);

	    // Convert assigned employees to DTO
	    Set<EmployeeDto> employeeDtos = employeesSet.stream()
	            .map(employee -> modelMapper.map(employee, EmployeeDto.class))
	            .collect(Collectors.toSet());


		return ResponseEntity.status(HttpStatus.CREATED).body(employeeDtos);
	}
}
