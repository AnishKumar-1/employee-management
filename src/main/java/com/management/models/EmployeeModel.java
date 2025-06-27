package com.management.models;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee") // ✅ Use lowercase by convention
public class EmployeeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	private String lastName;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private Users user;

	@Column(nullable = false, length = 15)
	private String phoneNumber;

	@Column(nullable = false)
	private BigDecimal salary;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", nullable = false)
	private DepartmentModel department;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "designation_id")
	private DesignationModel designation;

	// Projects managed by this employee (if any)
	@OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
	private Set<ProjectModel> managedProjects;

	// Projects assigned to this employee
	@ManyToMany(mappedBy = "employees", fetch = FetchType.LAZY) // ✅ use "employees", not "employee"
	@JsonManagedReference
	private Set<ProjectModel> projects;
}
