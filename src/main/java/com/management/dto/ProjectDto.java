package com.management.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
	private Long id; 
	@NotBlank(message="project name cannot be empty")
	private String name; 
	@NotBlank(message = "project description cannot be empty")
	private String description; 
	private MangerDto manager;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Set<EmployeeDto>employee;

}
