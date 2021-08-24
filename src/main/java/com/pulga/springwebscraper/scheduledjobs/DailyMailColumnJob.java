package com.pulga.springwebscraper.scheduledjobs;

import com.pulga.springwebscraper.entities.Column;
import com.pulga.springwebscraper.repositories.ColumnRepository;
import com.pulga.springwebscraper.services.email.EmailService;
import com.pulga.springwebscraper.utilities.Scraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class DailyMailColumnJob {

    private final Scraper scraper;
    private final ColumnRepository columnRepository;
    private final EmailService emailService;
    @Value("${email.recipient}")
    private String emailReceiver;//Application fails to start when declared final

    @Scheduled(fixedDelay = 60000)
    @Async
    public void scrape(){
        List<Column> listOfLatestColumns = scraper.getLatestDailyMailColumns();
        for (Column column : listOfLatestColumns) {
            boolean columnTitleExists = columnRepository.findByTitle(column.getTitle()).isEmpty();
            boolean columnLinkExists = columnRepository.findByLink(column.getLink()).isEmpty();
            if(columnTitleExists || columnLinkExists){
                try{
                    columnRepository.save(column);
                    log.info(column.toString() + " was saved");
                    String email = "Hello  You have a new DailyMail column by "
                            + column.getAuthor() + " with the title " + column.getTitle() +
                            "\n To read, please click here: " + column.getLink() +
                            "\n Sincerely, \n Your Web Scraper \n Note: replies to this email address are not monitored.";
                    emailService.send(emailReceiver, email);
                }catch (Exception e){
                    log.error(e.getMessage());
                }
            }else{
                log.warn("Column with that title or link already exists");
            }
        }
    }
}
