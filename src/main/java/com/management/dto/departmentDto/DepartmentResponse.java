package com.management.dto.departmentDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse {
	private Long id;
	private String name;
	private String description;
}
