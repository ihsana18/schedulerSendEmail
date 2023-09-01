package com.myproject.MyProject1.rest;

import com.myproject.MyProject1.dto.InsertTemplateMessage;
import com.myproject.MyProject1.dto.TemplateMessageGrid;
import com.myproject.MyProject1.service.abstraction.TemplateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/template")
public class TemplateMessageRestController {

    @Autowired
    private TemplateMessageService service;

    @PostMapping("/insert")
    public ResponseEntity<Object> insertTemplate(@Valid @RequestBody InsertTemplateMessage dto) throws IOException {
//        try{
            String responseBody = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
    }
    @GetMapping("/list-template")
    public ResponseEntity<Object> listTemplate(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "")String search){
        Page<TemplateMessageGrid> responseBody = service.getListTemplate(page,search);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
