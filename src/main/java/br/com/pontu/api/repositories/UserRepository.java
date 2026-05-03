package br.com.pontu.api.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontu.api.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}