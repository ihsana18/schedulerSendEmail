package com.myproject.MyProject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ReportGrid {
    private String sender;
    private String templateName;
    private String email;
    private LocalDate dateSent;
    private LocalTime timeSent;
    private String bodyMessage;
}
