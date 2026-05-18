package br.com.pontu.api.dtos.records;

import java.time.LocalDateTime;

import br.com.pontu.api.entities.TimeEntry;
import br.com.pontu.api.enums.TimeEntryType;

public record TimeEntryResponseDto(
        Long id,
        LocalDateTime timestamp,
        TimeEntryType type,
        String observation
        
) {

    public static TimeEntryResponseDto fromEntity(TimeEntry timeEntry) {
        return new TimeEntryResponseDto(
               timeEntry.getId(),
               timeEntry.getTimestamp(),
               timeEntry.getType(),
               timeEntry.getObservation() 
        );
    }
}