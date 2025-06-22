package com.management.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Project")
public class ProjectModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	@Column(nullable = false,unique = true)
	private String name; 
	@Column(nullable = false,columnDefinition = "TEXT")
	private String description; 
	@ManyToOne
	@JoinColumn(name="manager_id",referencedColumnName = "id")
	private EmployeeModel manager;
	
	@ManyToMany
	@JoinTable(name="project_employee",joinColumns = @JoinColumn(name="project_id")
	,inverseJoinColumns = @JoinColumn(name="employee_id")
	)
	@JsonBackReference
	private Set<EmployeeModel>employee;
}
