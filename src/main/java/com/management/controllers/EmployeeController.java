package com.management.controllers;

import com.management.Services.EmployeeService;
import com.management.dto.employeeDto.EmployeeRequest;
import com.management.dto.employeeDto.EmployeeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/employee")
@RestController
@Tag(name="Employee Apis",description = "create,update,get,delete")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/user/{userId}/department/{departmentId}/designation/{designationId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create employee (Admin only)",
            description = "Creates a new employee with the given user ID, department ID, and designation ID. Only accessible to admins."
    )
    public ResponseEntity<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request,
            @Parameter(description = "User ID to be linked") @PathVariable Long userId,
            @Parameter(description = "Department ID") @PathVariable Long departmentId,
            @Parameter(description = "Designation ID") @PathVariable Long designationId
    ) {
        return employeeService.createEmployee(request, userId, departmentId, designationId);
    }

    //get employee details by its id (ADMIN/Self Employee)
    @GetMapping("/{empId}")
    @Operation(summary = "get employee (Admin/Self Employee)",description = "required employee id to get its record")
    public ResponseEntity<EmployeeResponse> getProjectById(@NotNull @PathVariable Long empId){
        return employeeService.getEmployeeById(empId);
    }

}
