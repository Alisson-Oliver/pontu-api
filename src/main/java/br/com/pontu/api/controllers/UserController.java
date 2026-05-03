package br.com.pontu.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontu.api.dtos.UserResponseDto;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/me")
    public UserResponseDto aboutMe(@AuthenticationPrincipal User user) {
        return service.findById(user.getId());
    }
    
}
