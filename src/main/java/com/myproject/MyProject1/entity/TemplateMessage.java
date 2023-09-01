package com.myproject.MyProject1.entity;

import com.myproject.MyProject1.dto.DropdownDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TemplateMessage")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@Component
public class TemplateMessage {
    @Id
    @Column(name = "templateMessageId")
    private String templateMessageId;

    @Column(name = "templateName")
    private String templateName;

    @Column(name ="bodyMessage")
    private String bodyMessage;

    @Column(name = "AttachmentType")
    private String attachmentType;

    @Column(name = "AttachmentDirectory")
    private String attachmentDirectory;

    @ManyToMany
    @JoinTable(name = "RecipientTemplate",
            joinColumns =@JoinColumn(name = "templateMessageId"),
            inverseJoinColumns = @JoinColumn(name = "recipientId"))
    private List<Recipient> recipients;
}
