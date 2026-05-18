package br.com.pontu.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontu.api.dtos.records.CreateTimeEntryDto;
import br.com.pontu.api.dtos.records.TimeEntryResponseDto;
import br.com.pontu.api.entities.TimeEntry;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.repositories.TimeEntryRespository;

@Service
public class TimeEntryService {
    @Autowired
    private TimeEntryRespository respository;

    public TimeEntryResponseDto create(CreateTimeEntryDto timeEntry, User user){
        TimeEntry newTimeEntry = timeEntry.toEntity();
        newTimeEntry.setUser(user);
        return TimeEntryResponseDto.fromEntity(respository.save(newTimeEntry));
    }
}
