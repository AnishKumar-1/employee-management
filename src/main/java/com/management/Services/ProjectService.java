package com.management.Services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import com.management.dto.MangerDto;
import com.management.dto.ProjectDto;
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

	// create project by getting manager id
	public ResponseEntity<Object> createProject(Long manager_id, ProjectDto project) {
		EmployeeModel manager = employeeRepo.findById(manager_id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + manager_id));
		String managerRole = manager.getUser().getRole().getName();
		if (!managerRole.equalsIgnoreCase("MANAGER")) {
			throw new AuthorizationDeniedException("Employee is not a manager");
		}
		ProjectModel projectModel = modelMapper.map(project, ProjectModel.class);
		projectModel.setManager(manager);
		ProjectModel savedProjectModel=projectRepo.save(projectModel);
		ProjectDto pDto=new ProjectDto();
		pDto.setId(savedProjectModel.getId());
		pDto.setName(savedProjectModel.getName());
		pDto.setDescription(savedProjectModel.getDescription());
		MangerDto managerDto=modelMapper.map(savedProjectModel.getManager(),MangerDto.class);
		pDto.setManager(managerDto);
		
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

}
