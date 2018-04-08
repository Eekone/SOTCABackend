package com.texnoprom.SOTCA.Backend.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.texnoprom.SOTCA.Backend.dao.tct.DBVersionRepository;
import com.texnoprom.SOTCA.Backend.dao.tct.ParameterTypeRepository;
import com.texnoprom.SOTCA.Backend.model.tct.CustomData;
import com.texnoprom.SOTCA.Backend.model.tct.DBVersion;
import com.texnoprom.SOTCA.Backend.model.tct.ParameterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/tct")
public class TCTController {

    @Autowired
    private DBVersionRepository dbVersionRepository;

    @Autowired
    private ParameterTypeRepository parameterTypeRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

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
    public ModelAndView parametertypes() {
        ModelAndView modelAndView = new ModelAndView("params");
        List<ParameterType> types = parameterTypeRepository.findTop25ByOrderByParameterTypesId();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(types);
        modelAndView.addObject("json", json);
        modelAndView.addObject("parameters", types);

        return modelAndView;
    }

    @GetMapping("/customquery")
    public ModelAndView cusrom() {
        ModelAndView modelAndView = new ModelAndView("custom");
        String query = "SELECT readableparamvaluesid, s.sessionbegindatetime, o.objname, pl.paramname, rpv.paramvaluetxt, pl.measunit FROM readableparamvalues rpv\n" +
                "  JOIN sessions s ON rpv.sessionid = s.sessionid\n" +
                "  JOIN parameterslist pl ON pl.parameterslistid = rpv.parameterslistid\n" +
                "  JOIN objects o ON o.devtypeid = pl.devtypeid\n" +
                "WHERE measunit='В'\n" +
                "ORDER BY readableparamvaluesid ASC LIMIT 50";

        List<CustomData> list = jdbcTemplate.query(query, resultSet -> {
            List<CustomData> multimap = new ArrayList<>();
            while (resultSet.next()) {
                CustomData row = new CustomData();
                row.setReadableParamValuesId(resultSet.getString("readableparamvaluesid"));
                row.setSessionBeginDateTime(resultSet.getString("sessionbegindatetime"));
                row.setObjName(resultSet.getString("objname"));
                row.setParamName(resultSet.getString("paramname"));
                row.setParamValueTxt(resultSet.getString("paramvaluetxt"));
                row.setMeasUnit(resultSet.getString("measunit"));
                multimap.add(row);
            }
            return multimap;
        });
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(list);
        modelAndView.addObject("json", json);
        modelAndView.addObject("parameters", list);

        return modelAndView;
    }

    @RequestMapping(value = "/parametertypesjson", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String returnjson() {
        List<ParameterType> types = parameterTypeRepository.findTop25ByOrderByParameterTypesId();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(types);
    }

    @RequestMapping(value = "/customqueryjson", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String returnCustom() {
        String query = "SELECT readableparamvaluesid, s.sessionbegindatetime, o.objname, pl.paramname, rpv.paramvaluetxt, pl.measunit FROM readableparamvalues rpv\n" +
                "  JOIN sessions s ON rpv.sessionid = s.sessionid\n" +
                "  JOIN parameterslist pl ON pl.parameterslistid = rpv.parameterslistid\n" +
                "  JOIN objects o ON o.devtypeid = pl.devtypeid\n" +
                "WHERE measunit='В'\n" +
                "ORDER BY readableparamvaluesid ASC LIMIT 50";

        List<CustomData> list = jdbcTemplate.query(query, resultSet -> {
            List<CustomData> multimap = new ArrayList<>();
            while (resultSet.next()) {
                CustomData row = new CustomData();
                row.setReadableParamValuesId(resultSet.getString("readableparamvaluesid"));
                row.setSessionBeginDateTime(resultSet.getString("sessionbegindatetime"));
                row.setObjName(resultSet.getString("objname"));
                row.setParamName(resultSet.getString("paramname"));
                row.setParamValueTxt(resultSet.getString("paramvaluetxt"));
                row.setMeasUnit(resultSet.getString("measunit"));
                multimap.add(row);
            }
            return multimap;
        });
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(list);
    }

}

