package com.management.dto;

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
}
