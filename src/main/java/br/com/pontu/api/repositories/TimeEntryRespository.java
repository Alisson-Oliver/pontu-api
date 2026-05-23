package br.com.pontu.api.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontu.api.entities.TimeEntry;
import br.com.pontu.api.enums.TimeEntryType;

import java.util.List;


public interface TimeEntryRespository extends JpaRepository<TimeEntry, Long> {
    boolean existsByTypeAndCompetenceDate(TimeEntryType type, LocalDate competenceDate, Long userId);
    List<TimeEntry> findByCompetenceDateAndUserId(LocalDate competenceDate, Long userId);
    List<TimeEntry> findByUserId(Long id);
    long deleteByIdAndUserId(Long id, Long userId);
}