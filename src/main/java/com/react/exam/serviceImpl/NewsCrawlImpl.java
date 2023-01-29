package com.react.exam.serviceImpl;

import com.react.exam.service.NewsCrawlService;
import com.react.exam.vo.NewsCrawlVo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Array;
import java.util.*;
import java.util.stream.Stream;

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
            Elements writingElements = element.getElementsByAttributeValue("class", "writing");


            System.out.print(">>>>> "+i+" page - 크롤링 시작 ");
            //System.out.println(">>> url : "+url);
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

                Elements inputTimeElement = subDoc.getElementsByAttributeValue("class", "media_end_head_info_datestamp_time _ARTICLE_DATE_TIME");
                String inputTime = inputTimeElement.get(0).text();

                //System.out.println(title);
                //System.out.println(content);

                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("title", title);
                resultMap.put("content", content);

                String writing = writingElements.get(j).text();
                resultMap.put("writing", writing);

                resultMap.put("inputTime", inputTime);
                result.add(resultMap);
            }
            System.out.println(" - 크롤링 종료!");
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> analyzeText(List<Map<String, Object>> crawlingData) {

        List<Map<String, Object>> keywordList = new ArrayList<>();
        List<Map<String, Object>> result = new ArrayList<>();
        String[] copyArr = {};
        for(int z=0; z<crawlingData.size(); z++) {
            String[] splitData = crawlingData.get(z).get("content").toString().replaceAll("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]", " ").split(" ");
            copyArr = Stream.of(splitData, copyArr).flatMap(Stream::of).toArray(String[]::new);
        }

        String[] filterArr =  Arrays.stream(copyArr).filter(item -> !item.equals(" ")).toArray(String[]::new);
        //System.out.println(Arrays.toString(filterArr));

        String[] filterCopyArr = filterArr.clone();
        List<String> completeList = new ArrayList<>();

        for(int i=0; i<filterArr.length; i++) {

            Map<String, Object> resultMap = new HashMap<>();
            int cnt = 1;
            for (int j=i+1; j<filterArr.length; j++) {
                if (filterArr[i].equals(filterCopyArr[j])) {
                    cnt++;
                }
            }

            if(!completeList.contains(filterArr[i]) && cnt > 1) {
                completeList.add(filterArr[i]);
                resultMap.put(filterArr[i], cnt);
                keywordList.add(resultMap);
            }
        }

        for(int i=0; i<keywordList.size(); i++) {
            Map<String, Object> resultMap = new HashMap<>();
            String keyStr = getKey(keywordList, i);

            resultMap.put("keyword", keyStr);
            resultMap.put("count", keywordList.get(i).get(keyStr));

            if(keyStr.length() > 1 && Integer.parseInt(resultMap.get("count").toString()) > 39) {
                result.add(resultMap);
            }
        }

        return result;
    }
    public static String getKey(List<Map<String, Object>> paramList, int num) {
        for(int i = 0; i < paramList.size(); i++){
            for( Map.Entry<String, Object> elem : paramList.get(i).entrySet() ){
                if(i == num) {
                    return elem.getKey();
                }
            }
        }
        return "";
    }
}
