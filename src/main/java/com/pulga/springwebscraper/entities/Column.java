package com.pulga.springwebscraper.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Column {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @javax.persistence.Column(unique = true)
    private String title;
    @javax.persistence.Column(unique = true)
    private String link;
    private String author;

    public Column(String title, String link, String author) {
        this.title = title;
        this.link = link;
        this.author = author;
    }
}
