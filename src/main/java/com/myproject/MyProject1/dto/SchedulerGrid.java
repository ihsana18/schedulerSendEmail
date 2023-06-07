package com.myproject.MyProject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class SchedulerGrid {
    private String period;
    private String intervalWeek;
    private String intervalMonth;
    private String templateName;
    private LocalTime sendTime;
}
