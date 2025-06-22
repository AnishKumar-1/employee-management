package com.management.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	//private or access key final
	private static final String secretkey="randomkey123";
	private static final String paddedSecretKey = String.format("%-32s", secretkey);  // Padding from right to 32 chars (256 bits)
	private static final long JWT_TOKEN_VALIDITY=500*60*60;
	
	//convert secret key into byte code
	public SecretKey secretKey() {
		return Keys.hmacShaKeyFor(paddedSecretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	//generate token
	public String generate_token(UserDetails userDetails) {
		String username = userDetails.getUsername();
	     String jwtToken=Jwts.builder().subject(username).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()  + JWT_TOKEN_VALIDITY*1000)).signWith(secretKey())
				.compact();
		return jwtToken;
	}
	
	
	
	//validate token
	

		public boolean validateToken(String token) {
			try {
				Jwts.parser().verifyWith(secretKey()).build().parse(token);
				return true;
			}catch(ExpiredJwtException ex) {
				throw new ExpiredJwtException(null,null,"Token has expired");
			}catch(MalformedJwtException ex) {
				throw new MalformedJwtException("Invalid JWT token");
			}catch(Exception ex) {
				throw new RuntimeException("something went wrong");
			}catch(InternalError internal) {
				throw new InternalError("internal server error");
			}
		}
	//extract username from token
	public String extractUsername(String token) {
		return extractAllClaims(token,Claims::getSubject);
	}
	//extract all claims from token
	public <T> T extractAllClaims(String token,Function<Claims, T>claimResolver) {
		Claims claims=Jwts.parser().verifyWith(secretKey())
				.build().parseSignedClaims(token).getPayload();
		return claimResolver.apply(claims);
	}
	
	//extract expiration date
	public Boolean hasTokenExpired(String token) {
		return extractAllClaims(token,Claims::getExpiration).before(new Date());
	}
	
	
}
