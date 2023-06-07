package com.myproject.MyProject1.rest;

import com.myproject.MyProject1.dto.InsertScheduler;
import com.myproject.MyProject1.dto.SchedulerGrid;
import com.myproject.MyProject1.service.abstraction.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerRestController {

    @Autowired
    private SchedulerService service;

    @PostMapping("/insert")
    public ResponseEntity<Object> insertScheduler(@RequestBody InsertScheduler dto){
        Object responseBody = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }
    @GetMapping("/list-scheduler")
    public ResponseEntity<Object> listScheduler(@RequestParam(defaultValue = "1")int page,@RequestParam(defaultValue = "")String search){
        Page<SchedulerGrid> response=service.getListScheduler(page,search);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
