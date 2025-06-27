package com.management.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
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

	private static String secretkey;
	private static String paddedSecretKey;  // Padding from right to 32 chars (256 bits)
	private static long jwtExp;

	public JwtUtil(@Value("${secret.key}") String secretkey, @Value("${jwt.expiration}") long jwtExp) {

		JwtUtil.secretkey = secretkey;
		JwtUtil.paddedSecretKey = String.format("%-32s", secretkey);
		JwtUtil.jwtExp = jwtExp;
	}


	//convert secret key into byte code
	public SecretKey secretKey() {

		return Keys.hmacShaKeyFor(paddedSecretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	//generate token
	public String generate_token(UserDetails userDetails) {
		List<String> roles=userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
		Map<String,Object> claims=new HashMap<>();
		claims.put("roles",roles);
		String username = userDetails.getUsername();
        return Jwts.builder().
				claims(claims).
				subject(username).
				issuedAt(new Date(System.currentTimeMillis())).
				expiration(new Date(System.currentTimeMillis()  + jwtExp*1000)).
				signWith(secretKey())
               .compact();
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
		return extractClaim(token,Claims::getSubject);
	}
	//extract all claims from token
	public <T> T extractClaim(String token,Function<Claims, T>claimResolver) {
		Claims claims=Jwts.parser().verifyWith(secretKey())
				.build().parseSignedClaims(token).getPayload();
		return claimResolver.apply(claims);
	}
	
	//extract expiration date
	public Boolean hasTokenExpired(String token) {
		return extractClaim(token,Claims::getExpiration).before(new Date());
	}

	@SuppressWarnings("unchecked")
	public List<String> extractRolesFromToken(String token) {
		return extractClaim(token, claims -> claims.get("roles", List.class));
	}



}
