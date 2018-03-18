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
    @Column(name = "parametertypesid")
    private Integer parameterTypesId;

    @Column(name = "devtypeid")
    private Integer devTypeId;

    @Column(name = "writable")
    private Boolean writable;

    @Column(name = "parametertypeclassid")
    private Integer parameterTypeClassId;

    @Column(name = "paramtypename")
    private String paramTypeName;

    @Column(name = "typerelation")
    private Integer typeRelation;

    @Column(name = "extraid")
    private String extraId;

}
