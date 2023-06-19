package com.myproject.MyProject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipientAssignTemplate {

    @NotNull(message = "templateName is required")
    @NotBlank(message = "templateName is required")
    private String templateName;
    private String name;
}
