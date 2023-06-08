package com.myproject.MyProject1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Report")
@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class Report {
    @Id
    @Column(name = "reportId")
    private String reportId;

    @Column(name = "sentBy")
    private String sentBy;

    @Column(name = "emailRecipient")
    private String email;

    @Column(name = "dateSent")
    private LocalDate dateSent;

    @Column(name = "timeSent")
    private LocalTime timeSent;

    @Column(name = "bodyMessage")
    private String bodyMessage;

    @Column(name = "templateMessageId")
    private String templateMessageId;

    @ManyToOne
    @JoinColumn(name = "templateMessageId",updatable = false,insertable = false)
    private TemplateMessage templateMessage;
}
