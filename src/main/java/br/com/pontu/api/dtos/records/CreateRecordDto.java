package br.com.pontu.api.dtos.records;

import java.time.LocalDateTime;

import br.com.pontu.api.enums.RecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import br.com.pontu.api.entities.Record;


public record CreateRecordDto(
    @NotNull()
    LocalDateTime timestamp,

    @NotBlank()
    RecordType type,

    @Size(max = 500)
    String observation
) {
    public Record toEntity() {
        return new Record(null, this.timestamp, this.type, this.observation, null);
    }
}
