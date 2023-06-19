package com.myproject.MyProject1.dto;

import com.myproject.MyProject1.entity.TemplateMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class RecipientGrid {
    private String name;
    private String email;
    private String templateNames;

    public RecipientGrid(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
