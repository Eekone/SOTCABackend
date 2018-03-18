package com.texnoprom.SOTCA.Backend.model.tct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "tctdb11", name = "parametertypes")
public class ParameterType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer parameterTypesId;
    private Integer devTypeId;
    private Boolean writable;
    private Integer parameterTypeClassId;
    private String paramTypeName;
    private Integer typeRelation;
    private String extraId;

}
