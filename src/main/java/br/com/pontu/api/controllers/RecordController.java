package br.com.pontu.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontu.api.dtos.records.CreateRecordDto;
import br.com.pontu.api.dtos.records.RecordResponseDto;
import br.com.pontu.api.entities.Record;
import br.com.pontu.api.entities.User;
import br.com.pontu.api.services.RecordService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("records")
public class RecordController {

    @Autowired
    private RecordService service; 
    
    @PostMapping()
    public RecordResponseDto create(
        @RequestBody @Valid CreateRecordDto data,
        @AuthenticationPrincipal User user
    ) {
        return service.create(data, user);
    }
    
}
