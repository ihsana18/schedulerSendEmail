package com.myproject.MyProject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class InsertScheduler {
    private String period;
    private String intervalWeek;
    private String intervalMonth;
    private String templateName;
    private String sendTime;
}
