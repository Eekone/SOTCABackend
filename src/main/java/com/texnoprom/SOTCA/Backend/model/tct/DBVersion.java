package com.texnoprom.SOTCA.Backend.model.tct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "tctdb11", name = "dbversion")
public class DBVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer iddbversion;
    private String dbversionname;
    private Timestamp dbversionchange;

}
