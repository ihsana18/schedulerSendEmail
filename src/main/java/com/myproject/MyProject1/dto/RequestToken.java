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
public class RequestToken {
    @NotBlank(message = "username is required")
    @NotNull(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    @NotNull(message = "password is required")
    private String password;
}
