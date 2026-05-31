package br.com.pontu.api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import br.com.pontu.api.dtos.timeEntry.CreateTimeEntryDto;
import br.com.pontu.api.dtos.timeEntry.TimeEntryResponseDto;
import br.com.pontu.api.dtos.timeEntry.UpdateTimeEntryDto;
import br.com.pontu.api.entities.TimeEntry;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.enums.TimeEntryState;
import br.com.pontu.api.enums.TimeEntryType;
import br.com.pontu.api.exceptions.BusinessException;
import br.com.pontu.api.exceptions.ResourceNotFoundException;
import br.com.pontu.api.repositories.TimeEntryRepository;
import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;

@Service
public class TimeEntryService {
    @Autowired
    private TimeEntryRepository repository;

    @Autowired
    private StateMachineFactory<TimeEntryState, TimeEntryType> factory;

    @Autowired
    private UserService userService;

    @Transactional
    public TimeEntryResponseDto create(CreateTimeEntryDto timeEntry, User user){
        
        LocalDate competenceDate = timeEntry.timestamp().toLocalDate();
        
        if (existTimeEntry(timeEntry.type(), competenceDate, user.getId())) {
            throw new BusinessException("There is already an entry point of this type at this date.");
        }

        TimeEntryState currentState = TimeEntryState.INITIAL;
        
        if (user.getStateDate() != null && user.getStateDate().equals(competenceDate)) {
            currentState = user.getCurrentDayState();
        } else {
            user.setCurrentDayState(TimeEntryState.INITIAL);
            user.setStateDate(competenceDate);
        }

        if (timeEntry.type() == TimeEntryType.OUT && user.getConfig() != null) {
            boolean mandatoryLunch = user.getConfig().isMandatoryLunch();
            
            if (mandatoryLunch && currentState == TimeEntryState.WORKING) {
                boolean hasLunchStart = repository.existsByTypeAndCompetenceDate(
                    TimeEntryType.LUNCH_START, 
                    competenceDate, 
                    user.getId()
                );
                
                if (hasLunchStart) {
                    throw new BusinessException(
                        "Lunch break is mandatory. You must clock back from lunch (LUNCH_END) before clocking out."
                    );
                }
            }
        }

        StateMachine<TimeEntryState, TimeEntryType> sm = factory.getStateMachine(
            user.getId() + "_" + competenceDate
        );

        boolean transitionAllowed = sm.startReactively()
            .thenMany(sm.sendEvent(Mono.just(MessageBuilder.withPayload(timeEntry.type()).build())))
            .map(result -> result.getResultType() != StateMachineEventResult.ResultType.DENIED)
            .blockFirst();

        if (!transitionAllowed) {
            String errorMsg = buildTransitionErrorMessage(currentState, timeEntry.type());
            throw new BusinessException(errorMsg);
        }

        TimeEntryState nextState = getNextState(currentState, timeEntry.type());
        user.setCurrentDayState(nextState);
        user.setStateDate(competenceDate);
        userService.save(user);

        TimeEntry newTimeEntry = timeEntry.toEntity();
        newTimeEntry.setUser(user);

        return TimeEntryResponseDto.fromEntity(repository.save(newTimeEntry));
    }

    private TimeEntryState getNextState(TimeEntryState currentState, TimeEntryType type) {
        return switch (currentState) {
            case INITIAL -> (type == TimeEntryType.IN) ? TimeEntryState.WORKING : TimeEntryState.INITIAL;
            case WORKING -> switch (type) {
                case LUNCH_START -> TimeEntryState.ON_LUNCH;
                case OUT -> TimeEntryState.FINISHED;
                default -> TimeEntryState.WORKING;
            };
            case ON_LUNCH -> (type == TimeEntryType.LUNCH_END) ? TimeEntryState.WORKING : TimeEntryState.ON_LUNCH;
            case FINISHED -> TimeEntryState.FINISHED;
        };
    }

    private String buildTransitionErrorMessage(TimeEntryState currentState, TimeEntryType type) {
        return switch (currentState) {
            case INITIAL -> switch (type) {
                case IN -> "Valid transition";
                case OUT -> "Cannot clock out without clocking in first";
                case LUNCH_START -> "Cannot start lunch without clocking in first";
                case LUNCH_END -> "Cannot end lunch without clocking in first";
                default -> "Invalid transition from INITIAL";
            };
            case WORKING -> switch (type) {
                case LUNCH_START, OUT -> "Valid transition";
                case IN -> "Already clocked in";
                case LUNCH_END -> "Not on lunch break";
                default -> "Invalid transition from WORKING";
            };
            case ON_LUNCH -> switch (type) {
                case LUNCH_END -> "Valid transition";
                case IN -> "Already clocked in";
                case LUNCH_START -> "Already on lunch break";
                case OUT -> "Must return from lunch before clocking out";
                default -> "Invalid transition from ON_LUNCH";
            };
            case FINISHED -> "Cannot perform any action after clocking out";
        };
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

    public boolean existTimeEntry(TimeEntryType type, LocalDate competenceDate, Long userId) {
        return repository.existsByTypeAndCompetenceDate(
            type, 
            competenceDate, 
            userId
        );
    }
}
