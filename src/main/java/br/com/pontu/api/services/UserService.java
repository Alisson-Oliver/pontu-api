package br.com.pontu.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontu.api.dtos.users.UserResponseDto;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.exceptions.ResourceNotFoundException;
import br.com.pontu.api.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserResponseDto findById(Long id) {
       return repository.findById(id)
            .map(UserResponseDto::fromEntity)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void deleteById(Long id) {
        User user = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        repository.delete(user);
    }
}
