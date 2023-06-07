package com.myproject.MyProject1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "Recipient")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@Component
public class Recipient {
    @Id
    @Column(name = "recipientId")
    private String recipientId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "templateMessageId")
    private String templateMessageId;

    @ManyToOne
    @JoinColumn(name = "templateMessageId",updatable = false,insertable = false)
    private TemplateMessage templateMessage;

}
