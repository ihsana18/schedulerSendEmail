package com.myproject.MyProject1.dto;

import com.myproject.MyProject1.validation.anotation.UniqueTemplateName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsertTemplateMessage {

    private String currentTemplateName;
    @NotNull(message = "templateName is required")
    @NotBlank(message = "templateName cannot empty")
    @UniqueTemplateName(message = "templateName already exist")
    private String templateName;

    @NotNull(message = "bodyMessage is required")
    @NotBlank(message = "bodyMessage cannot empty")
    private String bodyMessage;


}
