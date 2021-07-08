package com.pulga.springwebscraper.services;

import com.pulga.springwebscraper.entities.Column;
import com.pulga.springwebscraper.repositories.ColumnRepository;
import com.pulga.springwebscraper.utilities.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class DailyMailColumnService {

    private final Scraper scraper;
    private final ColumnRepository columnRepository;

    @Scheduled(fixedDelay = 60000)
    public void scrape(){
        List<Column> listOfLatestColumns = scraper.getLatestDailyMailColumns();
        for (Column column : listOfLatestColumns) {
            if(columnRepository.findByTitle(column.getTitle()).isEmpty()
                    || columnRepository.findByLink(column.getLink()).isEmpty()){

                try{
                    columnRepository.save(column);
                    log.info(column.toString() + " was saved");
                }catch (Exception e){
                    log.error(e.getMessage());
                }

            }else{
                log.warn("Column with that title or link already exists");
            }
        }
    }
}
