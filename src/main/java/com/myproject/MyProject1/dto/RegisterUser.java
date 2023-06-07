package com.myproject.MyProject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUser {
    private String currentUsername;
    private String username;
    private String password;
    private String passwordConfirmation;
    private String email;
    private String role;
}
