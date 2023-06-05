package com.gleb.vinnikov.social_network.configs;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtParser jwtParser(
            @Value("${application.security.jwt.secret-key}") String secret
    ) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build();
    }

}
