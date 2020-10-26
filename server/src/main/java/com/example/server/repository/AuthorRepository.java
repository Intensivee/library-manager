package com.example.server.repository;

import com.example.server.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "SELECT a.author_id, a.first_name, a.last_name, a.birth_date, a.memoir, a.url from author a " +
            "WHERE a.first_name LIKE %?1% or a.last_name LIKE %?1% LIMIT 5", nativeQuery = true)
    List<Author> findByName(@Param("name") String name);

}
