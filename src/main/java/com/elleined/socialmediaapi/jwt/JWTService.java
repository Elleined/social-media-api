package com.elleined.socialmediaapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JWTService {
  private final String key;

  @Value("${application.security.jwt.expiration}")
  private long expiration;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  public JWTService() throws NoSuchAlgorithmException {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
    SecretKey secretKey = keyGenerator.generateKey();
    key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(String email) {
    return this.generateToken(email, expiration);
  }

  public String generateRefreshToken(String email) {
    return generateToken(email, refreshExpiration);
  }

  private String generateToken(String email, long expiration) {
    return Jwts.builder()
            .claims(new HashMap<>())
            .subject(email)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey())
            .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String email = this.getEmail(token);
    return (email.equals(userDetails.getUsername())) && !isTokenExpired(token.substring(7));
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSignInKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(key);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String getEmail(String token) {
    return extractClaim(token.substring(7), Claims::getSubject);
  }
}