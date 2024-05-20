package com.datn.shopshoesbackend.jwt;

import com.datn.shopshoesbackend.entity.Account;
import com.datn.shopshoesbackend.exception.AppException;
import com.datn.shopshoesbackend.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenProvider {
//    @Value("${datn.jwt.secret}")
//    private String JWT_SECRET;
private static final String JWT_SECRET = generateSecretKey();
    @Value("${datn.jwt.expiration}")
    private int JWT_EXPIRRATION;

//    private Key getSignKey() {
//        byte[] bytes = Decoders.BASE64.decode(JWT_SECRET);//Base64.getDecoder().decode(jjQR04rLn+7fgR0V06JjkCN90hIVneOmNq5KMu+/nhc=)
//        return Keys.hmacShaKeyFor(bytes);
//    }
//
//    private String generateSecretKey() {
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[32];
//        random.nextBytes(keyBytes);
//        String secretKey = Base64.getEncoder().encodeToString(keyBytes);
//        return secretKey;
//
//    }
private static String generateSecretKey() {
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    return Base64.getEncoder().encodeToString(key.getEncoded());
}

    private Key getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(CustomUserDetails account) throws Exception {

        Date now = new Date();
        Date dateExpiration = new Date(now.getTime() + JWT_EXPIRRATION);
//        this.generateSecretKey();
        try {
            String token = Jwts.builder()
                    .setSubject(account.getAccount().getUsername())
                    //ngay bat dau hieu luc
                    .setIssuedAt(now)
                    //ngay het han
                    .setExpiration(dateExpiration)
                    //ma hoa
                    .signWith(getSignKey())
                    .compact();
            return token;
        } catch (Exception e) {
            System.out.println("Cannot create jwt token, error: " + e.getMessage());
            return null;
        }

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = this.extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String getUserNameFromJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken).getBody();
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            throw new AppException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            throw new AppException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            throw new AppException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            throw new AppException("JWT claims string is empty.");
        }
    }
}
