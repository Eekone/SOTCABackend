package com.texnoprom.SOTCA.Backend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "register_batch")
public class RegisterBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String timestamp;
    private String type;


    @OneToMany(mappedBy = "registerBatch", cascade = CascadeType.ALL)
    public List<Register> registers = new ArrayList<>();

    public void addRegister(Register reg) {
        reg.setRegisterBatch(this);
        registers.add(reg);
    }

    public RegisterBatch() {
    }

    public RegisterBatch(String timestamp, String type, List<Register> registers) {
        this.timestamp = timestamp;
        this.type = type;
        this.registers = registers;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Register> getRegisters() {
        return registers;
    }

    public void setRegisters(List<Register> registers) {
        this.registers = registers;
    }
}
