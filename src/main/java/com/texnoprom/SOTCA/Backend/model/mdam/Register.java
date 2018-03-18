package com.texnoprom.SOTCA.Backend.model.mdam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "mdam_registers", name = "register")
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer command;
    private Integer number;
    private String name;
    private Float value;

    @ManyToOne
    private RegisterBatch registerBatch;

}
