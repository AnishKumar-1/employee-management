package com.management.dto;

import java.math.BigDecimal;
import com.management.dto.departmentDto.DepartmentResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	private Long id; 
	private UserDto user; 
	@NotBlank(message="first name cannot be empty")
	private String firstName;
	@NotBlank(message="last name cannot be empty")
	private String lastName;
	@NotBlank(message="phone number cannot be empty")
	@Size(min = 10,max = 15,message = "Phone number must be between 10 and 15 digits")
	private String phoneNumber;
	@NotNull(message = "salary cannot be empty")
	private BigDecimal salary; 
	private DepartmentResponse department;
	  // ✅ Include the projects and managedProjects fields
    private Set<ProjectDto> projects;  // Projects employee is working on
}
