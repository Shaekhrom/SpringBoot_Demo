package com.shaekhrom.demojwt.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // Clave secreta codificada en Base64 para firmar los tokens.
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    // Genera un token para el usuario proporcionado.
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    // Genera un token con reclamaciones adicionales.
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)                         // Reclamaciones adicionales.
                .setSubject(user.getUsername())                 // Usuario (subject).
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creación.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Expira en 24 minutos.
                .signWith(getKey(), SignatureAlgorithm.HS256)   // Firma el token.
                .compact();                                     // Construye el token.
    }

    // Decodifica la clave secreta desde Base64 y crea una clave compatible.
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Extrae el nombre de usuario (subject) del token.
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    // Valida si el token es válido para el usuario proporcionado.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Obtiene todas las reclamaciones del token.
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método genérico para obtener una reclamación específica del token.
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrae la fecha de expiración del token.
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    // Comprueba si el token ya ha expirado.
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
