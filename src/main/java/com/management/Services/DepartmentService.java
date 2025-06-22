package com.management.Services;

import com.management.dto.departmentDto.DepartmentRequest;
import com.management.dto.departmentDto.DepartmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
	public DepartmentResponse createDepartment(DepartmentRequest dto) {
		DepartmentModel department=DepartmentModel.builder()
				.name(dto.getName()).description(dto.getDescription()).build();
		DepartmentModel result = departmentRepo.save(department);
        return DepartmentResponse.builder().id(result.getId()).name(result.getName())
                .description(result.getDescription()).build();
	}

	// get all departments
	public ResponseEntity<Object> listDepartments() {
		List<DepartmentModel> departments = departmentRepo.findAll();
		if (departments.isEmpty()) {
			throw new IllegalArgumentException("No record found");
		}
		return ResponseEntity
				.ok(departments.stream().map(data ->
						DepartmentResponse.builder()
								.id(data.getId()).name(data.getName()).description(data.getDescription()).build()
						).collect(Collectors.toList()));
	}
	
	//deparment by id
	public ResponseEntity<Object> department(Long departmentId){
		Optional<DepartmentModel> department=departmentRepo.findById(departmentId);
		if(department.isEmpty()) {
//			ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
		    throw new IllegalArgumentException("No record found");
		}
		DepartmentModel result=department.get();
		DepartmentResponse response=DepartmentResponse.builder().id(result.getId())
				.name(result.getName()).description(result.getDescription()).build();
		return ResponseEntity.ok(response);
	}

	//update department by id
	//id will get  through path variable
	//data will get through request body
	public ResponseEntity<String> updateDepartment(DepartmentRequest dto,Long deparmentId){
		Optional<DepartmentModel> department=departmentRepo.findById(deparmentId);
		if(department.isEmpty()) {
			throw new IllegalArgumentException("No record found");
		}
		DepartmentModel storedDate=department.get();
		storedDate.setName(dto.getName());
		storedDate.setDescription(dto.getDescription());
		departmentRepo.save(storedDate);
		return ResponseEntity.ok("department updated successfully.");
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

}
