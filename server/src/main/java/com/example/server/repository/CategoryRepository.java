package com.example.server.repository;

import com.example.server.dtos.CategoryProjection;
import com.example.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT c.category_id, c.category_name FROM category c " +
            "NATURAL JOIN book_category bc WHERE bc.book_id = ?1", nativeQuery = true)
    List<Category> findByBookId(@Param("id") Long id);

    @Query(value = "SELECT c.category_id as id, c.category_name as name FROM category c " +
            "NATURAL JOIN book_category bc WHERE bc.book_id = ?1", nativeQuery = true)
    List<CategoryProjection> getDtoByBookId(@Param("id") Long id);
}

