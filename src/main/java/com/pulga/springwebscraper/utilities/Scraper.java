package com.pulga.springwebscraper.utilities;

import com.pulga.springwebscraper.entities.Column;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Scraper {
    public List<Column> getLatestDailyMailColumns(){
        List<Column> listOfLatestColumns = new ArrayList<>(20);/* Initial capacity set to 20 for better
        performance because there are at most 20 columns that can be scraped so the List doesn't need to reallocate
        memory space*/
        try{
            Document rawDocument = Jsoup.connect("https://www.dailymail.co.uk/columnists/index.html").get();
            Elements mostRecentColumns = rawDocument
                    .getElementsByClass("js-headers").first()
                    .getElementsByClass("columnist-archive-content cleared link-ccox");

            for(Element mostRecentColumn: mostRecentColumns){
                String author = mostRecentColumn.getElementsByClass("author").select("a").text();
                Element linkElement = mostRecentColumn.getElementsByClass("linkro-ccox").select("a").first();
                String link = "https://www.dailymail.co.uk" + linkElement.attr("href");
                String title = linkElement.text();
                Column column = new Column(title, link, author);
                listOfLatestColumns.add(column);
            }
        }catch (IOException e){
            log.error(e.getMessage());
        }
        return listOfLatestColumns;
    }
}
