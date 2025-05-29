package com.gianmarques.estoqueapi.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtils {

    public static final String JWT_BEARER = "Bearer ";

    public static final String JWT_AUTHORIZATION = "Authorization";

    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";

    public static final long EXPIRE_DAYS = 0;

    public static final long EXPIRE_HOURS = 0;

    public static final long EXPIRE_MINUTES = 10;

    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);


    public JwtUtils() {
    }


    private static SecretKey gerarChaveSecreta() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public static Date dataExpiracao(Date dataInicio) {
        LocalDateTime data = dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime dataFinal = data.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(dataFinal.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwtToken gerarToken(String email, String perfil) {
        Date issuedAt = new Date();
        Date limit = dataExpiracao(issuedAt);
        String token = Jwts.builder()
                .subject(email)
                .issuedAt(issuedAt)
                .expiration(limit)
                .signWith(gerarChaveSecreta())
                .claim("perfil", perfil)
                .compact();
        return new JwtToken(token);
    }

    private static String refatorarToken(String token) {
        if (token.contains(JWT_BEARER)) {
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }

    private static Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(gerarChaveSecreta())
                    .build()
                    .parseSignedClaims(refatorarToken(token)).getPayload();
        } catch (JwtException e) {
            log.error("Token inválido");
        }
        return null;
    }

    public static String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public static boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(gerarChaveSecreta())
                    .build()
                    .parseSignedClaims(refatorarToken(token));
            return true;
        } catch (JwtException ex) {
            log.error(String.format("Token invalido %s", ex.getMessage()));
        }
        return false;
    }
}
