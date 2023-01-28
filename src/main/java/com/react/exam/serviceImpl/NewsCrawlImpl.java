package com.react.exam.serviceImpl;

import com.react.exam.service.NewsCrawlService;
import com.react.exam.vo.NewsCrawlVo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsCrawlImpl implements NewsCrawlService {
    @Override
    public List<Map<String, Object>> crawling(String date, int page) throws IOException {

        List<Map<String, Object>> result = new ArrayList<>();

        for (int i=1; i<page+1; i++) {
            String url = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid2=265&sid1=100&date="+date+"&page="+i;
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByAttributeValue("class", "list_body newsflash_body");

            Element element = elements.get(0);
            Elements photoElements = element.getElementsByAttributeValue("class", "photo");

            System.out.println("========== "+i+" page - 크롤링 시작 ==========");
            for(int j=0; j<photoElements.size(); j++) {
                System.out.print(".");
                Element articleElement = photoElements.get(j);
                Elements aElements = articleElement.select("a");
                Element aElement = aElements.get(0);

                String articleUrl = aElement.attr("href");

                Element imgElement = aElement.select("img").get(0);
                String title = imgElement.attr("alt");

                Document subDoc = Jsoup.connect(articleUrl).get();
                Element contentElement = subDoc.getElementById("dic_area");
                String content = contentElement.text();

                //System.out.println(title);
                //System.out.println(content);

                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("title", title);
                resultMap.put("content", content);
                result.add(resultMap);
            }
            System.out.println("");
            System.out.println("========== "+i+" page - 크롤링 종료 ==========");
        }
        return result;
    }
}
