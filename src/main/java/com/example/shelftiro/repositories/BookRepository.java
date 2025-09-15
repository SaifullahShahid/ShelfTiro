package com.example.shelftiro.repositories;

import com.example.shelftiro.domain.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository <BookEntity, String> {

}
