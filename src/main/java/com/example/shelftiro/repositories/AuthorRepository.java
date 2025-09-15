package com.example.shelftiro.repositories;

import com.example.shelftiro.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository <AuthorEntity, Long> {

}
