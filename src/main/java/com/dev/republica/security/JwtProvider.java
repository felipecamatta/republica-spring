package com.dev.republica.security;

import com.dev.republica.exception.SpringRepublicaException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;

import static java.sql.Timestamp.from;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/republica.jks");
            keyStore.load(resourceAsStream, "republica".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringRepublicaException("Exception occurred while loading keystore");
        }

    }

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("republicaspring", "republica".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRepublicaException("Exception occured while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt) {
        Jwts.parserBuilder().setSigningKey(getPublickey()).build().parse(jwt);
        return true;
    }

    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("republicaspring").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringRepublicaException("Exception occured while " +
                    "retrieving public key from keystore");
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = (Claims) Jwts.parserBuilder()
                .setSigningKey(getPublickey())
                .build()
                .parse(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

}
