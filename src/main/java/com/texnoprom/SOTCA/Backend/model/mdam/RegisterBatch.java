package com.texnoprom.SOTCA.Backend.model.mdam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "mdam_registers", name = "register_batch")
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

}
