package br.com.pontu.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontu.api.dtos.records.CreateTimeEntryDto;
import br.com.pontu.api.dtos.records.TimeEntryResponseDto;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.services.TimeEntryService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("timeEntry")
public class TimeEntryController {

    @Autowired
    private TimeEntryService service; 
    
    @PostMapping()
    public TimeEntryResponseDto create(
        @RequestBody @Valid CreateTimeEntryDto data,
        @AuthenticationPrincipal User user
    ) {
        return service.create(data, user);
    }
    
}
