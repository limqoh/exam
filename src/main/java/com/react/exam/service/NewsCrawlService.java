package com.react.exam.service;

import com.react.exam.vo.NewsCrawlVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface NewsCrawlService {
    List<Map<String, Object>> crawling(String date, int page) throws IOException;
    List<Map<String, Object>> analyzeText(List<Map<String, Object>> crawlingData);
}
