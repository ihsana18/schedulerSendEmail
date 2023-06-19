package com.myproject.MyProject1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany
    @JoinTable(name = "RecipientTemplate",
        joinColumns =@JoinColumn(name = "recipientId"),
        inverseJoinColumns = @JoinColumn(name = "templateMessageId"))
    private List<TemplateMessage> templateMessages;

}
