package com.management.dto.employeeDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email; // From Users table
    private String phoneNumber;
    private BigDecimal salary;
    private String departmentName;
    private String designation;
    private Set<String> projects; // Optional
}
