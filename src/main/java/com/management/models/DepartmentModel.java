package com.management.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="department")
public class DepartmentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;

	private String description;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
	@JsonIgnore 
	private List<EmployeeModel> employees; // Changed 'employee' to 'employees' for consistency

}
