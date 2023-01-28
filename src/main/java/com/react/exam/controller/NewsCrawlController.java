package com.react.exam.controller;

import com.react.exam.service.NewsCrawlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
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

        List<Map<String, Object>> result = ncService.crawling("20230128", 1);

        return result;
    }
}
