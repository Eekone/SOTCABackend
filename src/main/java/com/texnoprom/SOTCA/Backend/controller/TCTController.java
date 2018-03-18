package com.texnoprom.SOTCA.Backend.controller;

import com.texnoprom.SOTCA.Backend.dao.tct.DBVersionRepository;
import com.texnoprom.SOTCA.Backend.model.tct.DBVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/tct")
public class TCTController {

    @Autowired
    private DBVersionRepository dbVersionRepository;


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

    @GetMapping("/test")
    public String tes() {
        return "test1!";
    }

}

