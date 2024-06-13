package com.adem.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.adem.exception.message.ErrorMessage;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

//	@Value( "${greenrent.app.jwtSecret}")
//	private String jwtSecret ;
//	
//	@Value("${greenrent.app.jwtExpirationMs}")  
//	private long jwtExpirationMs;   
	 
	public static final String secretKey = "cf83e1357eefb8bdf158d2877eec2f63b931bd47417a81a538327af927da3e";
	
	// JWT token üretecek
	public String generateJwtToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				             .setIssuedAt(new Date(System.currentTimeMillis()))
                             .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                             .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}
	
	
	// JWT token içinden kullanıcının email bilgisi alınmacak
	public String getEmailFromToken(String token) {
		return Jwts.parserBuilder()
				   .setSigningKey(getSignKey())
				   .build()
				   .parseClaimsJws(token)
				   .getBody()
				   .getSubject();
		
	}
	
	// JWT token valide edecek
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |  
				SecurityException | IllegalArgumentException e ) {
			logger.error(String.format(ErrorMessage.JWTTOKEN_ERROR_MESSAGE, e.getMessage()));
		}
		return false ;
	}
		 private Key getSignKey() {
		        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
		        return Keys.hmacShaKeyFor(keyBytes);
		    }
		
	}