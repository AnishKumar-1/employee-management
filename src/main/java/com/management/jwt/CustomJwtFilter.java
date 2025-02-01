package com.management.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.management.security.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomJwtFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private CustomUserDetails customUserDetils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//extract token from header
		if(request.getServletPath().equals("/api/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			 String authToken=tokenFromHeader(request);
			 if(jwtUtil.validateToken(authToken)) {
				 String username=jwtUtil.extractUsername(authToken);
				 UserDetails userDetails=customUserDetils.loadUserByUsername(username);
				 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new
						 UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			 }
			
		}catch(Exception ex) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		filterChain.doFilter(request, response);
	}
	
	private String tokenFromHeader(HttpServletRequest request) {
		String token=request.getHeader("Authorization");
		if(token==null || !token.startsWith("Bearer")) {
			throw new IllegalArgumentException("Token is empty or not found");
		}
		return token.substring(7);
	}
	
}
