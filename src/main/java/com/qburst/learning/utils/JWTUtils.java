package com.qburst.learning.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qburst.learning.model.AppUser;
import com.qburst.learning.model.Role;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class JWTUtils {
    // change logic to get secret key in real application
    private final Algorithm hmac256 = Algorithm.HMAC256("secret".getBytes());
    private  final JWTVerifier verifier = JWT.require(hmac256).build();
    public  String accessToken(User user, String requestUri) {
        String accessToken = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                .withIssuer(requestUri)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(hmac256);
        return  accessToken;
    }

    public String refreshToken(User user, String requestUri) {
        String refreshToken = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(requestUri)
                .sign(hmac256);
        return  refreshToken;
    }

    public DecodedJWT decode(String token) {
        return  verifier.verify(token);
    }

    public String accessTokenFromAppUser(AppUser user, String requestUri) {
        String accessToken = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + 10*60*1000))
                .withIssuer(requestUri)
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(hmac256);
        return accessToken;

    }

}
