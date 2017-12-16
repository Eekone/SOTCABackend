package com.texnoprom.SOTCA.Backend.model;

import javax.persistence.*;

@Entity
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

    public Register() {
    }

    public Register(Integer command, Integer number, String name, Float value) {
        this.command = command;
        this.number = number;
        this.name = name;
        this.value = value;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommand() {
        return command;
    }

    public void setCommand(Integer command) {
        this.command = command;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public void setRegisterBatch(RegisterBatch registerBatch) {
        this.registerBatch = registerBatch;
    }

    public RegisterBatch getRegisterBatch() {
        return registerBatch;
    }
}
