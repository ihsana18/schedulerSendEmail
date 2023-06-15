package com.myproject.MyProject1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class DataGrid {
    @JsonProperty("holiday_name")
    private String holidayName;
    @JsonProperty("holiday_date")
    private String holidayDate;
    @JsonProperty("is_national_holiday")
    private boolean isNationalHoliday;
}
