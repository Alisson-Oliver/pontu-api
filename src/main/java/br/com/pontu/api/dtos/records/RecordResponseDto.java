package br.com.pontu.api.dtos.records;

import java.time.LocalDateTime;

import br.com.pontu.api.entities.Record;
import br.com.pontu.api.enums.RecordType;

public record RecordResponseDto(
        Long id,
        LocalDateTime timestamp,
        RecordType type,
        String observation
        
) {

    public static RecordResponseDto fromEntity(Record record) {
        return new RecordResponseDto(
               record.getId(),
               record.getTimestamp(),
               record.getType(),
               record.getObservation() 
        );
    }
}