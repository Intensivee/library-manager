package com.example.server.repository;

import com.example.server.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @RestResource(exported = false)
    Optional<Book> findById(Long id);

    @RestResource(exported = false)
    List<Book> findAll();

    @RestResource(exported = false)
    Page<Book> findAll(Pageable pageable);

    @RestResource(exported = false)
    Page<Book> findByCategoriesId(Long id, Pageable pageable);

    @RestResource(exported = false)
    List<Book> findFirst10ByTitleContaining(String title);

    @RestResource(exported = false)
    List<Book> findByAuthorId(@Param("id") Long id);
}
