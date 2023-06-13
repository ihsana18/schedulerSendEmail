package com.myproject.MyProject1.dto;

import com.myproject.MyProject1.validation.anotation.UniqueSchedulerName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@UniqueSchedulerName(schedulerName = "schedulerName",currentSchedulerName = "currentSchedulerName",message = "schedulerName already exist")
public class InsertScheduler {

    private String currentSchedulerName;
    @NotBlank(message = "schedulerName is required")
    @NotNull(message = "schedulerName is required")
    private String schedulerName;

    @NotBlank(message = "period is required")
    @NotNull(message = "period is required")
    private String period;

    private String intervalWeek;
    private String intervalMonth;
    @NotBlank(message = "templateName is required")
    @NotNull(message = "templateName is required")
    private String templateName;

    @NotBlank(message = "sendTime is required")
    @NotNull(message = "sendTime is required")
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
