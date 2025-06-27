package com.management.controllers;

import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.dto.LoginDto;
import com.management.jwt.JwtUtil;

@RestController
@RequestMapping("/api")
@Tag(name="Auth Apis",description = "login")
public class LoginController {
    
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;


	@PostMapping("/auth")
	@Operation(
			summary = "Login to generate JWT token",
			description = "Pass valid email and password to receive a JWT token. This token is required for accessing protected APIs."
	)
	public ResponseEntity<?> userLogin(@RequestBody LoginDto loginDto) {
	    try {
	        authenticate(loginDto.getEmail(), loginDto.getPassword());
	        Map<String,Object>jwtresult=new HashMap<>();
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        String token=jwtUtil.generate_token(userDetails);	       
	        jwtresult.put("jwt",token);
	        jwtresult.put("status",200);
	        return ResponseEntity.ok(jwtresult);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }
	}
	

	
	   //authentication user for login purpose
    private void authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (authentication.isAuthenticated())
            SecurityContextHolder.getContext().setAuthentication(authentication);
    }
	
	
	
}
