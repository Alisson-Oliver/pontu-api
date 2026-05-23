package br.com.pontu.api.dtos.timeEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.pontu.api.enums.TimeEntryType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import br.com.pontu.api.entities.TimeEntry;

public record CreateTimeEntryDto(
    @NotNull()
    TimeEntryType type, 

    @Size(max = 500)
    String observation,

    @PastOrPresent(message = "Date cannot be in the future")
    LocalDateTime timestamp

) {
    public TimeEntry toEntity() {
        LocalDateTime timestamp = (this.timestamp != null) ? this.timestamp : LocalDateTime.now();
        LocalDate competenceDate = timestamp.toLocalDate();

        return new TimeEntry(
            timestamp,
            competenceDate,
            this.type, 
            this.observation
        );
    }
}
