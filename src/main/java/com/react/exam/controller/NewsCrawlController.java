package com.react.exam.controller;

import com.react.exam.service.NewsCrawlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class NewsCrawlController {

    private final NewsCrawlService ncService;

    public NewsCrawlController(NewsCrawlService ncService) {
        this.ncService = ncService;
    }

    @GetMapping("/nCrawling")
    public List<Map<String, Object>> nCrawling() throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateTime = dateFormat.format(new Date());
        System.out.println(dateTime);

        List<Map<String, Object>> result = ncService.crawling(dateTime, 2);

        return result;
    }
}
