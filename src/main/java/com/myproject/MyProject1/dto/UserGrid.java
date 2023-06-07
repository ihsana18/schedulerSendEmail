package com.myproject.MyProject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGrid {
    private String username;
    private String email;
    private String role;
}
