package br.com.pontu.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontu.api.dtos.timeEntry.CreateTimeEntryDto;
import br.com.pontu.api.dtos.timeEntry.TimeEntryResponseDto;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.services.TimeEntryService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("time-entries")
public class TimeEntryController {

    @Autowired
    private TimeEntryService service; 
    
    @PostMapping()
    public TimeEntryResponseDto create(
        @RequestBody @Valid CreateTimeEntryDto data, @AuthenticationPrincipal User user
    ) {
        return service.create(data, user);
    }

    @GetMapping("me/today")
    public List<TimeEntryResponseDto> getToday(@AuthenticationPrincipal User user) {
        return service.getTimeEntriesToday(user.getId());
    }

    @GetMapping("me")
    public List<TimeEntryResponseDto> getAllByUser(@AuthenticationPrincipal User user) {
        return service.getAllByUser(user.getId());
    }

    @DeleteMapping("me/{id}")
    public void delete(
        @AuthenticationPrincipal User user, @PathVariable("id") Long id
    ) {
        service.deleteById(id, user.getId());
    }
}