package br.com.pontu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontu.api.entities.Record;

public interface RecordRespository extends JpaRepository<Record, Long> {
}