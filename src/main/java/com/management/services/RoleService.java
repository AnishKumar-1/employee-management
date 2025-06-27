package com.management.services;

import com.management.exception.ResourceNotFoundException;
import com.management.models.Role;
import com.management.models.Users;
import com.management.repository.RoleRepo;
import com.management.repository.UserModelRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;
    private final UserModelRepo userModelRepo;

    //update role to the user

    public ResponseEntity<String> updateRole(Long userid,Long roleId){
        Users user = userModelRepo.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: "+userid));
        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: "+userid));
        user.setRole(role);
        userModelRepo.save(user);
        return ResponseEntity.ok("User role updated successfully.");
    }
}
