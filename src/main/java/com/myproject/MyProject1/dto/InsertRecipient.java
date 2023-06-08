package com.myproject.MyProject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class InsertRecipient {

    private String currentName;
    @NotBlank(message = "name is required")
    @NotNull(message = "name is required")
    private String name;
    @NotBlank(message = "email is required")
    @NotNull(message = "email is required")
    private String email;
    @NotBlank(message = "templateName is required")
    @NotNull(message = "templateName is required")
    private String templateName;
}
