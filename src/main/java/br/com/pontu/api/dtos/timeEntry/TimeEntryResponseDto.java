package br.com.pontu.api.dtos.timeEntry;

import java.time.LocalDateTime;
import java.util.List;

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

    public static List<TimeEntryResponseDto> transformList(List<TimeEntry> timeEntries) {
        return timeEntries.stream()
            .map(e -> new TimeEntryResponseDto(
                e.getId(), e.getTimestamp(), e.getType(), e.getObservation()
            )
        ).toList();
    }
}