package com.myproject.MyProject1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
//@AllArgsConstructor
@Setter @Getter
public class Dropdown {
    public static List<DropdownDTO> getRoleDropdown(){
        List<DropdownDTO> roles = new LinkedList<>();
        roles.add(new DropdownDTO("Administrator", "Administrator"));
        roles.add(new DropdownDTO("Supervisor", "Supervisor"));
        roles.add(new DropdownDTO("Data Entry", "Data Entry"));
        return roles;
    }
}
