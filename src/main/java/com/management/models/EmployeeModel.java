package com.management.models;

import java.math.BigDecimal;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Employee")
public class EmployeeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String firstName; // Changed field name to camelCase
    
	private String lastName;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users user;
	
	@Column(nullable = false, length = 15)
	private String phoneNumber;

	@Column(nullable = false)
	private BigDecimal salary;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false) // Removed redundant																					// @Column(nullable = false)
	private DepartmentModel department; // Replaced DepartmentDto with DepartmentModel
	
	@OneToMany(mappedBy = "manager")
	private Set<ProjectModel> managedProjects;
	@ManyToMany(mappedBy = "employee")
	@JsonManagedReference
	private Set<ProjectModel> projects;
}
