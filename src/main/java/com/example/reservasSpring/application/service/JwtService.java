package com.example.reservasSpring.application.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import com.example.reservasSpring.domain.model.User;

@Service
public record JwtService(
  @Value("${application.security.jwt.secret-key}")
  String secretKey,

  @Value("${application.security.jwt.expiration}")
  Long jwtExpiration) {


  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  private String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
    if (userDetails instanceof User) {
      extraClaims.put("roles", ((User) userDetails).getRoles()); // Suponiendo que User tiene un método getRole().
    }
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  private String buildToken(
    HashMap<String, Object> extraClaims,
    UserDetails userDetails,
    Long expiration) {
    JwtBuilder builder = Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(Date.from(
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
            ))
            .setExpiration(Date.from(
                    LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().plusMillis(expiration)
            ))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256);

    // Agregar todos los extraClaims excepto los roles
    extraClaims.forEach((key, value) -> {
      if (!key.equals("role")) {
        builder.claim(key, value);
      }
    });

    // Finalmente, agrega los roles
    if (extraClaims.containsKey("role")) {
      builder.claim("role", extraClaims.get("role"));
    }

    return builder.compact();

  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(getSignInKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).isBefore(LocalDateTime.now());
  }

  private LocalDateTime extractExpiration(String token) {
    Date date = extractClaim(token, Claims::getExpiration);
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }
}
