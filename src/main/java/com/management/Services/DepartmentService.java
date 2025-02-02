package com.management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.management.dto.DepartmentDto;
import com.management.models.DepartmentModel;
import com.management.repository.DepartmentRepo;
import java.util.List;
import java.util.Optional;
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
			throw new IllegalArgumentException("No record found");
		}
		return ResponseEntity
				.ok(departments.stream().map(data -> modelToDepartmentDto(data)).collect(Collectors.toList()));
	}
	
	//deparment by id
	public ResponseEntity<Object> department(Long departmentId){
		Optional<DepartmentModel> department=departmentRepo.findById(departmentId);
		if(department.isEmpty()) {
//			ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
		    throw new IllegalArgumentException("No record found");
		}
		return ResponseEntity.ok(modelToDepartmentDto(department.get()));
	}

	//update department by id
	//id will get  through path variable
	//data will get through request body
	public ResponseEntity<Object> updateDepartment(DepartmentDto dto,Long deparmentId){
		Optional<DepartmentModel> department=departmentRepo.findById(deparmentId);
		if(department.isEmpty()) {
			throw new IllegalArgumentException("No record found");
		}
		DepartmentModel storedDate=department.get();
		storedDate.setName(dto.getName());
		storedDate.setDescription(dto.getDescription());
		return ResponseEntity.ok(modelToDepartmentDto(departmentRepo.save(storedDate)));
	}
	
	
	//delete department by id 
	//admin only can delete it by id
	public ResponseEntity<Object> deleteDepartment(Long departmentId){
		if(!departmentRepo.existsById(departmentId)) {
			throw new IllegalArgumentException("No record found");
		}
		 departmentRepo.deleteById(departmentId);
		return ResponseEntity.status(HttpStatus.OK).body("Department deleted successfully..");
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
