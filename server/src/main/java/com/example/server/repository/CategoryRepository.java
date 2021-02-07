package com.example.server.repository;

import com.example.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT b.categories FROM Book b WHERE b.id = ?1")
    List<Category> findByBookId(@Param("id") Long id);

    @RestResource(exported = false)
    Optional<Category> findById(@Param("id") Long id);
}

