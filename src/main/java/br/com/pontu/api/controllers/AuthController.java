package br.com.pontu.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontu.api.services.AuthService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/success")
    public ResponseEntity<String> sucess(@AuthenticationPrincipal OAuth2User principal) {
        String token = authService.loginGoogle(principal);
        return ResponseEntity.ok(token);
    }
    
}
