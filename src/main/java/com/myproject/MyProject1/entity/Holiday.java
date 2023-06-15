package com.myproject.MyProject1.entity;

import com.myproject.MyProject1.validation.anotation.Compare;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Holiday")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Holiday {
    @Id
    @Column(name = "holidayId")
    private String holidayId;

    @Column(name = "holidayName")
    private String holidayName;

    @Column(name = "holidayDate")
    private String holidayDate;

    @Column(name = "nationalHoliday")
    private boolean nationalHoliday;
}
