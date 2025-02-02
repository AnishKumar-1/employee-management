package com.management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.management.dto.DepartmentDto;
import com.management.models.DepartmentModel;
import com.management.repository.DepartmentRepo;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;

	// method to create department
	public DepartmentDto createDepartment(DepartmentDto dto) {
		DepartmentModel data = departmentRepo.save(departmentDtoToModel(dto));
		return modelToDepartmentDto(data);
	}

	// get all departments
	public ResponseEntity<Object> listDepartments() {
		List<DepartmentModel> departments = departmentRepo.findAll();
		if (departments.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No data found");
		}
		return ResponseEntity
				.ok(departments.stream().map(data -> modelToDepartmentDto(data)).collect(Collectors.toList()));
	}

	// method to convert DepartmentDto to DepartmentModel
	private DepartmentModel departmentDtoToModel(DepartmentDto dto) {
		DepartmentModel department = new DepartmentModel();
		department.setName(dto.getName());
		department.setDescription(dto.getDescription());
		return department;
	}

	// method to convert DepartmentModel to DepartmentDto
	private DepartmentDto modelToDepartmentDto(DepartmentModel model) {
		DepartmentDto dto = new DepartmentDto();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setDescription(model.getDescription());
		return dto;
	}

}
