package com.myproject.MyProject1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Scheduler")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@Component
public class Scheduler {
    @Id
    @Column(name = "schedulerId")
    private String schedulerId;

    @Column(name = "period")
    private String period;

    @Column(name = "intervalWeek")
    private String intervalWeek;

    @Column(name = "intervalMonthly")
    private String intervalMonthly;

    @Column(name = "sendTime")
    private LocalTime sendTime;

    @Column(name = "templateMessageId")
    private String templateMessageId;

    @ManyToOne
    @JoinColumn(name = "templateMessageId",updatable = false,insertable = false)
    private TemplateMessage templateMessage;
}
