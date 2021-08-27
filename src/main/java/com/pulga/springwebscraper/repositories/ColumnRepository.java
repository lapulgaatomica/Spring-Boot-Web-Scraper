package com.pulga.springwebscraper.repositories;

import com.pulga.springwebscraper.entities.Column;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ColumnRepository {

    private final JdbcTemplate jdbcTemplate;

    public boolean linkExists(String link){
        String sql = "SELECT count(*) FROM COLUMN WHERE LINK = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, link) > 0;
    }

    public boolean titleExists(String title){
        String sql = "SELECT count(*) FROM COLUMN WHERE TITLE = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, title) > 0;
    }

    public int save(Column column){
        String sql = "INSERT INTO COLUMN (AUTHOR, LINK, TITLE) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql,  column.getAuthor(), column.getLink(), column.getTitle());
    }
}
