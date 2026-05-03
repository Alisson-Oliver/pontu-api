package br.com.pontu.api.services;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import br.com.pontu.api.entities.User;
import br.com.pontu.api.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;

    @Value("${api.security.token.expiration}")
    private Duration tokenExpiration;

    public String loginGoogle(OAuth2User principal){
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");
        String photo = principal.getAttribute("picture");

        User user = repository.findByEmail(email)
                .map(u -> {
                     u.setPhotourl(photo);
                    return repository.save(u);
                })
                .orElseGet(() -> repository.save(new User(name, email, photo)));

        return generateToken(user);
    }

    private String generateToken(User user){
        String secret = System.getenv("JWT_SECRET");
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + tokenExpiration.toMillis()))
                .signWith(key)
                .compact();
    }
}
