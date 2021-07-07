package com.pulga.springwebscraper.repositories;

import com.pulga.springwebscraper.entities.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<Column, Long> {
}
