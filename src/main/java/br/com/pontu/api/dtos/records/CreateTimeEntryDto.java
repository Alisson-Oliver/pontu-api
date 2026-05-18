package br.com.pontu.api.dtos.records;

import java.time.LocalDateTime;

import br.com.pontu.api.enums.TimeEntryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import br.com.pontu.api.entities.TimeEntry;


public record CreateTimeEntryDto(
    @NotNull()
    LocalDateTime timestamp,

    @NotBlank()
    TimeEntryType type,

    @Size(max = 500)
    String observation
) {
    public TimeEntry toEntity() {
        return new TimeEntry(null, this.timestamp, this.type, this.observation, null);
    }
}
