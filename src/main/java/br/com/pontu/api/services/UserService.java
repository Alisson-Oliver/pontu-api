package br.com.pontu.api.services;

import br.com.pontu.api.dtos.UserResponseDto;
import br.com.pontu.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserResponseDto findById(Long id) {
       return repository.findById(id)
            .map(UserResponseDto::fromEntity)
            .orElseThrow(() -> new RuntimeException("User not found"));

    }
}
