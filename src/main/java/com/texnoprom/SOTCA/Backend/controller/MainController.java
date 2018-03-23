package com.texnoprom.SOTCA.Backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path = "/")
public class MainController {

    public MainController() {
    }

    @GetMapping("/")
    public ModelAndView showMDAM() {
        return new ModelAndView("mainpage");
    }

}

