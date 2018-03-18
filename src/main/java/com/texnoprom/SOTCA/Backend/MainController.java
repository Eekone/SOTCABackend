package com.texnoprom.SOTCA.Backend;

import com.texnoprom.SOTCA.Backend.dao.mdam.BatchRepository;
import com.texnoprom.SOTCA.Backend.dao.tct.DBVersionRepository;
import com.texnoprom.SOTCA.Backend.model.mdam.Register;
import com.texnoprom.SOTCA.Backend.model.mdam.RegisterBatch;
import com.texnoprom.SOTCA.Backend.model.tct.DBVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
//@RequestMapping(path="/")
public class MainController {
    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 10;
    private static final int[] PAGE_SIZES = {5, 10, 15, 20};

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private DBVersionRepository dbVersionRepository;

    private RegisterService registerService;

    public MainController(RegisterService r) {
        this.registerService = r;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity update(@RequestBody RegisterBatch batch) {
        Iterable<RegisterBatch> iter = batchRepository.findAll();
        for (RegisterBatch entry : iter) {
            if (entry.getTimestamp().equals(batch.getTimestamp())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("Уже было загружено");
            }
        }
        for (Register r : batch.registers) {
            r.setRegisterBatch(batch);
        }
        batchRepository.save(batch);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Уcпешно загружено");
    }

    @GetMapping("/")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> page) {
        ModelAndView modelAndView = new ModelAndView("main");

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

    @GetMapping("/test")
    public String test() {
        Iterable<DBVersion> iter = dbVersionRepository.findAll();

        return "ha-ha";
    }

}

