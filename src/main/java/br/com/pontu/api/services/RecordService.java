package br.com.pontu.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontu.api.dtos.records.CreateRecordDto;
import br.com.pontu.api.dtos.records.RecordResponseDto;
import br.com.pontu.api.entities.Record;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.repositories.RecordRespository;

@Service
public class RecordService {
    @Autowired
    private RecordRespository respository;

    public RecordResponseDto create(CreateRecordDto record, User user){
        Record newRecord = record.toEntity();
        newRecord.setUser(user);
        return RecordResponseDto.fromEntity(respository.save(newRecord));
    }
}
