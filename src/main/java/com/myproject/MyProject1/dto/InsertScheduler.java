package com.myproject.MyProject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class InsertScheduler {
    private String currentSchedulerName;
    private String schedulerName;
    private String period;
    private String intervalWeek;
    private String intervalMonth;
    private String templateName;
    private String sendTime;
    private LocalTime send;

    public InsertScheduler(String currentSchedulerName, String schedulerName, String period, String intervalWeek, String intervalMonth, String templateName, LocalTime send) {
        this.currentSchedulerName = currentSchedulerName;
        this.schedulerName = schedulerName;
        this.period = period;
        this.intervalWeek = intervalWeek;
        this.intervalMonth = intervalMonth;
        this.templateName = templateName;
        this.send = send;
    }
}
