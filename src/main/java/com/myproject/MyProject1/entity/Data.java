package com.myproject.MyProject1.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Data")
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@Component
public class Data {
    @Id
    @Column(name = "dataId")
    private String dataId;

    @Column(name = "email")
    private String email;

    @Column(name = "inputBy")
    private String inputBy;

}
