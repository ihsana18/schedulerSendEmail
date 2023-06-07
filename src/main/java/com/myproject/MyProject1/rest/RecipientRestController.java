package com.myproject.MyProject1.rest;

import com.myproject.MyProject1.dto.InsertRecipient;
import com.myproject.MyProject1.service.abstraction.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/recipient")
public class RecipientRestController {

    @Autowired
    private RecipientService service;

    @PostMapping("/insert")
    public ResponseEntity<Object> insertRecipient(@Valid @RequestBody InsertRecipient dto){
        Object responseBody = service.save(dto);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/list-recipient")
    public ResponseEntity<Object> listRecipient(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "")String name){
        Object responseBody = service.findAll(page,name);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
