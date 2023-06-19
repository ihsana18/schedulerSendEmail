package com.myproject.MyProject1.dto;

import com.myproject.MyProject1.validation.anotation.RecipientNameUpsert;
import com.myproject.MyProject1.validation.anotation.UniqueRecipientEmail;
import com.myproject.MyProject1.validation.anotation.UniqueTemplateName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@RecipientNameUpsert(name = "name",currentName = "currentName",message = "name already exist")
public class InsertRecipient {
    private String currentName;
    @NotBlank(message = "name is required")
    @NotNull(message = "name is required")
    private String name;
    @NotBlank(message = "email is required")
    @NotNull(message = "email is required")
//    @UniqueRecipientEmail(message = "email already exist")
    private String email;
//    @NotBlank(message = "templateName is required")
//    @NotNull(message = "templateName is required")
//    private String templateName;
}
