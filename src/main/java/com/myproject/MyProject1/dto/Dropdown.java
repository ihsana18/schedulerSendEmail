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
    public static List<DropdownDTO> getMonthly(){
        List<DropdownDTO> roles = new LinkedList<>();
        roles.add(new DropdownDTO("1", "1"));
        roles.add(new DropdownDTO("2", "2"));
        roles.add(new DropdownDTO("3", "3"));
        roles.add(new DropdownDTO("4", "4"));
        roles.add(new DropdownDTO("5", "5"));
        roles.add(new DropdownDTO("6", "6"));
        roles.add(new DropdownDTO("7", "7"));
        roles.add(new DropdownDTO("8", "8"));
        roles.add(new DropdownDTO("9", "9"));
        roles.add(new DropdownDTO("10", "10"));
        roles.add(new DropdownDTO("11", "11"));
        roles.add(new DropdownDTO("12", "12"));
        roles.add(new DropdownDTO("13", "13"));
        roles.add(new DropdownDTO("14", "14"));
        roles.add(new DropdownDTO("15", "15"));
        roles.add(new DropdownDTO("16", "16"));
        roles.add(new DropdownDTO("17", "17"));
        roles.add(new DropdownDTO("18", "18"));
        roles.add(new DropdownDTO("19", "19"));
        roles.add(new DropdownDTO("20", "20"));
        roles.add(new DropdownDTO("21", "21"));
        roles.add(new DropdownDTO("22", "22"));
        roles.add(new DropdownDTO("23", "23"));
        roles.add(new DropdownDTO("24", "24"));
        roles.add(new DropdownDTO("25", "25"));
        roles.add(new DropdownDTO("26", "26"));
        roles.add(new DropdownDTO("27", "27"));
        roles.add(new DropdownDTO("28", "28"));
        roles.add(new DropdownDTO("29", "29"));
        roles.add(new DropdownDTO("30", "30"));
        roles.add(new DropdownDTO("31", "31"));
        return roles;
    }
}
