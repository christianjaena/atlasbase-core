package com.atlasbase.atlasbase_core.application.services;

import com.atlasbase.atlasbase_core.infrastructure.properties.JwtProperties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class JwtService {

	private final JwtProperties jwtProperties;

	public JwtService(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	public String generateToken(String username) {
		return Jwts.builder()
			.setSubject(username)
			.setIssuedAt(new Date())
			.setIssuer(jwtProperties.issuer())
			.setExpiration(new Date(System.currentTimeMillis() + jwtProperties.expiration()))
			.signWith(getSigningKey(), SignatureAlgorithm.HS256)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
			return true;
		}
		catch (JwtException e) {
			return false;
		}
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(jwtProperties.secret().getBytes());
	}

}
