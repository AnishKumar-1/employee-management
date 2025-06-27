package com.management.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

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
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver handlerExceptionResolver;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//by passing few endpoint to allow access for development
		String path = request.getServletPath();
		boolean pathFlag=whitelistedPath(request.getServletPath());
		if (pathFlag){
			filterChain.doFilter(request, response);
			return;
		}

		try {
			 String authToken=tokenFromHeader(request);
			 if(jwtUtil.validateToken(authToken)) {
				 String username=jwtUtil.extractUsername(authToken);
				 List<GrantedAuthority> authorities=jwtUtil.extractRolesFromToken(authToken)
						 .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

				 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new
						 UsernamePasswordAuthenticationToken(username, null,authorities);

				 usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			 }
			
		}catch (Exception ex) {
			handlerExceptionResolver.resolveException(request, response, null, ex);
	        return;
	    }
		filterChain.doFilter(request, response);
	}
	
	private String tokenFromHeader(HttpServletRequest request) {
		String token=request.getHeader("Authorization");
		if(token==null || !token.startsWith("Bearer")) {
			throw new IllegalArgumentException("Token is required. Please login");
		}
		return token.substring(7);
	}


	//whitelisted urls/bypass
	public boolean whitelistedPath(String path){
		return path.startsWith("/api/auth")
				|| path.startsWith("/swagger-ui")
				|| path.startsWith("/v3/api-docs")
				|| path.startsWith("/swagger-resources")
				|| path.startsWith("/webjars")
				|| path.startsWith("/h2-db"); // ✅ Corrected
	}


}
