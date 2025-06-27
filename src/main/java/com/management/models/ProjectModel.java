package com.management.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.management.models.EmployeeModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class ProjectModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String start_date;

	@ManyToMany
	@JoinTable(
			name = "employee_project",
			joinColumns = @JoinColumn(name = "project_id"),
			inverseJoinColumns = @JoinColumn(name = "employee_id")
	)
	@JsonBackReference
	private Set<EmployeeModel> employees;

	@ManyToOne
	@JoinColumn(name = "manager_id")
	private EmployeeModel manager;
}
