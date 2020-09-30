package com.example.server.repository;

import com.example.server.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "SELECT a.author_id, a.first_name, a.last_name, a.birth_date from author a WHERE a.first_name LIKE %?1% or a.last_name LIKE %?1%",
            countQuery="SELECT COUNT(*) from author WHERE a.first_name LIKE %?1% or a.last_name LIKE %?1%",
            nativeQuery = true)
    Page<Author> findByName(@Param("name") String name, Pageable pageable);
}
