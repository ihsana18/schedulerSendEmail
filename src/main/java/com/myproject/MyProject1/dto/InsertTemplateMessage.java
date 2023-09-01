package com.myproject.MyProject1.dto;

import com.myproject.MyProject1.validation.anotation.UniqueTemplateName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@UniqueTemplateName(message = "templateName already exist",templateName = "templateName",currentTemplateName = "currentTemplateName")
public class InsertTemplateMessage {

    private String currentTemplateName;
    @NotNull(message = "templateName is required")
    @NotBlank(message = "templateName cannot empty")
    private String templateName;

    @NotNull(message = "bodyMessage is required")
    @NotBlank(message = "bodyMessage cannot empty")
    private String bodyMessage;

    private String attachmentType;

    private MultipartFile fileAttachment;

    private String attachmentPatch;

    public InsertTemplateMessage(String currentTemplateName, String templateName, String bodyMessage, String attachmentType) {
        this.currentTemplateName = currentTemplateName;
        this.templateName = templateName;
        this.bodyMessage = bodyMessage;
        this.attachmentType = attachmentType;
    }
}
