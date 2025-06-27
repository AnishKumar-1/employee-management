package com.management.dto.employeeDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmployeeRequest {

    @NotBlank(message="first name cannot be empty")
    private String firstName;
    @NotBlank(message="last name cannot be empty")
    private String lastName;
    @NotBlank(message="phone number cannot be empty")
    @Size(min = 10,max = 15,message = "Phone number must be between 10 and 15 digits")
    private String phoneNumber;
    @NotNull(message = "salary cannot be empty")
    private BigDecimal salary;
}
