package com.myproject.MyProject1.dto;

import com.myproject.MyProject1.validation.anotation.Compare;
import com.myproject.MyProject1.validation.anotation.EmailValidation;
import com.myproject.MyProject1.validation.anotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@UniqueUsername(username = "username",currentUsername = "currentUsername",message = "username already exist")
@Compare(firstField = "password",secondField = "passwordConfirmation",message = "password not match")
public class RegisterUser {
    private String currentUsername;
    @NotNull(message = "username is required")
    @NotBlank(message = "username is required")
    private String username;
    @NotNull(message = "password is required")
    @NotBlank(message = "password is required")
    private String password;
    @NotNull(message = "passwordConfirmation is required")
    @NotBlank(message = "passwordConfirmation is required")
    private String passwordConfirmation;
    @NotNull(message = "email is required")
    @NotBlank(message = "email is required")
    @EmailValidation(message = "Format email @gmail.com or @yahoo.com or @yahoo.co.id")
    private String email;
    @NotNull(message = "role is required")
    @NotBlank(message = "role is required")
    private String role;
}
