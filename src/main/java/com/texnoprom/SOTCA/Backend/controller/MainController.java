package com.texnoprom.SOTCA.Backend.controller;


import com.texnoprom.SOTCA.Backend.Pager;
import com.texnoprom.SOTCA.Backend.RegisterService;
import com.texnoprom.SOTCA.Backend.dao.mdam.BatchRepository;
import com.texnoprom.SOTCA.Backend.model.mdam.RegisterBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@RequestMapping(path = "/")
public class MainController {
    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 10;
    private static final int[] PAGE_SIZES = {5, 10, 15, 20};

    @Autowired
    private BatchRepository batchRepository;

    private RegisterService registerService;

    public MainController(RegisterService r) {
        this.registerService = r;
    }

    /*public MainController() {
    }*/

    /*@GetMapping("/")
    public ModelAndView mainpage() {
        return new ModelAndView("mainpage");
    }
*/
    @GetMapping("/")
    public ModelAndView showMDAM(@RequestParam("pageSize") Optional<Integer> pageSize,
                                 @RequestParam("page") Optional<Integer> page) {
        ModelAndView modelAndView = new ModelAndView("mdam");

        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<RegisterBatch> batches = registerService.findAllPageable(new PageRequest(evalPage, evalPageSize, new Sort(
                new Sort.Order(Sort.Direction.DESC, "timestamp")
        )));
        Pager pager = new Pager(batches.getTotalPages(), batches.getNumber(), BUTTONS_TO_SHOW);


        modelAndView.addObject("batches", batches);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        return modelAndView;
    }

}

