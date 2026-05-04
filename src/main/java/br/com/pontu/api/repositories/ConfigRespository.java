package br.com.pontu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontu.api.entities.Config;

public interface ConfigRespository extends JpaRepository<Config, Long> {
}