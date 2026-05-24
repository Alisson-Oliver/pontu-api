package br.com.pontu.api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontu.api.dtos.timeEntry.CreateTimeEntryDto;
import br.com.pontu.api.dtos.timeEntry.TimeEntryResponseDto;
import br.com.pontu.api.dtos.timeEntry.UpdateTimeEntryDto;
import br.com.pontu.api.entities.TimeEntry;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.exceptions.BusinessException;
import br.com.pontu.api.exceptions.ResourceNotFoundException;
import br.com.pontu.api.repositories.TimeEntryRepository;
import jakarta.transaction.Transactional;

@Service
public class TimeEntryService {
    @Autowired
    private TimeEntryRepository repository;

    public TimeEntryResponseDto create(CreateTimeEntryDto timeEntry, User user){
        TimeEntry newTimeEntry = timeEntry.toEntity();
        newTimeEntry.setUser(user);
        boolean exist = existTimeEntry(newTimeEntry, user);

        if (exist) {
            throw new BusinessException("There is already an entry point at that time.");
        };

        return TimeEntryResponseDto.fromEntity(repository.save(newTimeEntry));
    }

    public List<TimeEntryResponseDto> getTimeEntriesToday(Long userId) {
        return TimeEntryResponseDto.transformList(
            repository.findByCompetenceDateAndUserId(LocalDate.now(), userId)
        );
    }

    public List<TimeEntryResponseDto> getAllByUser(Long userId){
        return TimeEntryResponseDto.transformList(
            repository.findByUserId(userId)
        );
    }

    @Transactional
    public void deleteById(Long id, Long userId) {
        long deleted = repository.deleteByIdAndUserId(id, userId);
        if(deleted == 0) {
            throw new ResourceNotFoundException("Time Entry not found");
        }
    }

    @Transactional
    public TimeEntryResponseDto update(Long id, UpdateTimeEntryDto dto) {
        TimeEntry timeEntry = this.findById(id);

        if(dto.observation() != null) {
             timeEntry.setObservation(dto.observation());
        }
        
        if(dto.timestamp() != null) {
            timeEntry.setTimestamp(dto.timestamp());
            timeEntry.setCompetenceDate(dto.timestamp().toLocalDate());
        }

        return TimeEntryResponseDto.fromEntity(repository.save(timeEntry));
    }

    public TimeEntry findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Time Entry not found"));
    }   

    public boolean existTimeEntry(TimeEntry timeEntry, User user) {
        return repository.existsByTypeAndCompetenceDate(
            timeEntry.getType(), 
            timeEntry.getCompetenceDate(), 
            user.getId()
        );
    }
}
