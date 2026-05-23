package br.com.pontu.api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontu.api.dtos.records.CreateTimeEntryDto;
import br.com.pontu.api.dtos.records.TimeEntryResponseDto;
import br.com.pontu.api.entities.TimeEntry;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.exceptions.BusinessException;
import br.com.pontu.api.exceptions.ResourceNotFoundException;
import br.com.pontu.api.repositories.TimeEntryRespository;
import jakarta.transaction.Transactional;

@Service
public class TimeEntryService {
    @Autowired
    private TimeEntryRespository respository;

    public TimeEntryResponseDto create(CreateTimeEntryDto timeEntry, User user){
        TimeEntry newTimeEntry = timeEntry.toEntity();
        newTimeEntry.setUser(user);
        boolean exist = respository.existsByTypeAndCompetenceDate(
            newTimeEntry.getType(), 
            newTimeEntry.getCompetenceDate(), 
            user.getId()
        );

        if (exist) {
            throw new BusinessException("There is already an entry point at that time.");
        };

        return TimeEntryResponseDto.fromEntity(respository.save(newTimeEntry));
    }

    public List<TimeEntryResponseDto> getTimeEntriesToday(Long userId) {
        return TimeEntryResponseDto.transformList(
            respository.findByCompetenceDateAndUserId(LocalDate.now(), userId)
        );
    }

    public List<TimeEntryResponseDto> getAllByUser(Long userId){
        return TimeEntryResponseDto.transformList(
            respository.findByUserId(userId)
        );
    }

    @Transactional
    public void deleteById(Long id, Long userId) {
        long deleted = respository.deleteByIdAndUserId(id, userId);
        if(deleted == 0) {
            throw new ResourceNotFoundException("Time Entry not found");
        }
    }
}
