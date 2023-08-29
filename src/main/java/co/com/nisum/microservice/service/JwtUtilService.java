package co.com.nisum.microservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import co.com.nisum.microservice.utility.ConfigApplication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtilService {
	
    @Autowired
    private ConfigApplication config;
	
    public String extractUsername(String token) {
      return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
      return Jwts.parser().setSigningKey(config.getSecretKey()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
      Map<String, Object> claims = new HashMap<>();
      GrantedAuthority rol = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0);
      claims.put("rol", rol);
      return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
	return Jwts
	          .builder()
	          .setClaims(claims)
	          .setSubject(subject)
	          .setIssuedAt(new Date(System.currentTimeMillis()))
	          .setExpiration(new Date(System.currentTimeMillis() + config.getTokenValidity()))
	          .signWith(SignatureAlgorithm.HS256, config.getSecretKey())
	          .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
}
