package com.management.Services;

import com.management.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.management.repository.UserModelRepo;

@Service
public class UserProfileService {

	@Autowired
	private UserModelRepo userModelRepo;


    public Users getProfile() {
        // Get authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Ensure authentication is not null and user is logged in
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("No authenticated user found");
        }

        // Get logged-in user's email
        String email = authentication.getName();

        // Fetch user from database
        Users user = userModelRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return user;
    }
}
