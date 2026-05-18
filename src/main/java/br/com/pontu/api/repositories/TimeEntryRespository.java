package br.com.pontu.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontu.api.entities.TimeEntry;

public interface TimeEntryRespository extends JpaRepository<TimeEntry, Long> {
}