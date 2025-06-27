package com.management.controllers;

import com.management.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@Validated
@Tag(name="Role Api",description = "update")
public class RoleController{

    @Autowired
    private RoleService roleService;

    @PatchMapping("/user/{userId}/role/{roleId}")
    @Operation(
            summary = "Update user's role (Admin/Manager only)",
            description = "Provide user ID and new role ID as path variables. Only accessible by Admin and Manager roles."
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<String> updateRole(
            @Parameter(description = "ID of the user whose role is to be updated") @NotNull @PathVariable Long userId,
            @Parameter(description = "New role ID to assign to the user") @NotNull @PathVariable Long roleId
    ) {
        return roleService.updateRole(userId, roleId);
    }

}
