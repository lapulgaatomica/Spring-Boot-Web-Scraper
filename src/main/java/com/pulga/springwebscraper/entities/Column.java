package com.pulga.springwebscraper.entities;

import lombok.Data;

@Data
public class Column {

    private Long id;
    private String title;
    private String link;
    private String author;

    public Column(String title, String link, String author) {
        this.title = title;
        this.link = link;
        this.author = author;
    }
}
