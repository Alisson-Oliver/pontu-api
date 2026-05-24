package br.com.pontu.api.dtos.timeEntry;

import java.time.LocalDateTime;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size; 
public record UpdateTimeEntryDto(
    @Size(max = 500)
    String observation,

    @PastOrPresent(message = "Date cannot be in the future")
    LocalDateTime timestamp
) {
    public UpdateTimeEntryDto {
        if(observation != null) observation = observation.trim(); 
    }
}