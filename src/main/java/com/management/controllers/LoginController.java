package com.management.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.dto.LoginDto;
import com.management.jwt.JwtUtil;

@RestController
@RequestMapping("/api")
public class LoginController {
    
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@PostMapping("/auth")
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
	
	
	@GetMapping("/test")
	@PreAuthorize("hasAuthority('EMPLOYEE')")
	public String test() {
		return "hello admin";
	}
	
	   //authentication user for login purpose
    private void authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (authentication.isAuthenticated())
            SecurityContextHolder.getContext().setAuthentication(authentication);
    }
	
	
	
}
