package com.spring.jwt.entity;

import java.sql.Date;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;

   public String generateToken(String user) {
	   return Jwts.builder()
            .setSubject(user)
            .setExpiration(Date.from(Instant.now().plusSeconds(3600))) // 1 hour expiration
            .signWith(SignatureAlgorithm.HS512, secret )
            .compact();
}

}
