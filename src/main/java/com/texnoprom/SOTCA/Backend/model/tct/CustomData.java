package com.texnoprom.SOTCA.Backend.model.tct;

import lombok.Data;

@Data
public class CustomData {

    private String readableParamValuesId;
    private String sessionBeginDateTime;
    private String objName;
    private String paramName;
    private String paramValueTxt;
    private String measUnit;
}
