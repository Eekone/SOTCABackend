package com.texnoprom.SOTCA.Backend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.texnoprom.SOTCA.Backend.dao.tct.DBVersionRepository;
import com.texnoprom.SOTCA.Backend.dao.tct.ParameterTypeRepository;
import com.texnoprom.SOTCA.Backend.model.tct.DBVersion;
import com.texnoprom.SOTCA.Backend.model.tct.ParameterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(path = "/tct")
public class TCTController {

    @Autowired
    private DBVersionRepository dbVersionRepository;

    @Autowired
    private ParameterTypeRepository parameterTypeRepository;

    public TCTController() {
    }


    @GetMapping("/dbversion")
    public ModelAndView dbversion() {
        ModelAndView modelAndView = new ModelAndView("tctdbversion");
        DBVersion dbVersion = dbVersionRepository.findTopByOrderByIddbversionDesc();
        //ToDo: move to template
        String versionString = "Версия: " + dbVersion.getDbversionname() +
                "; Дата: " + dbVersion.getDbversionchange().toString();
        modelAndView.addObject("version", versionString);

        return modelAndView;
    }

    @GetMapping("/parametertypes")
    public ModelAndView tes() {
        ModelAndView modelAndView = new ModelAndView("params");
        List<ParameterType> types = parameterTypeRepository.findTop25ByOrderByParameterTypesId();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(types);
        modelAndView.addObject("json", json);

        return modelAndView;
    }

}

