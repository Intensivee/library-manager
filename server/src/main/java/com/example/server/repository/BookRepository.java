package com.example.server.repository;

import com.example.server.dtos.BookProjection;
import com.example.server.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    String BOOK_DTO_BASE_STATEMENT = "SELECT book.book_id as id, book.title as title, book.description as description," +
            " book.image_url as imageUrl, author.author_id as authorId, CONCAT(author.first_name,' ',author.last_name) as authorName " +
            "FROM book as book " +
            "NATURAL JOIN " +
            "author as author";

    String BOOK_DTO_BASE_STATEMENT_ID = "SELECT book.book_id as id, book.title as title, book.description as description," +
            " book.image_url as imageUrl, author.author_id as authorId, CONCAT(author.first_name,' ',author.last_name) as authorName " +
            "FROM book as book " +
            "NATURAL JOIN " +
            "author as author WHERE book.book_id = ?1";

    @Query(value = BOOK_DTO_BASE_STATEMENT_ID, nativeQuery = true)
    Optional<BookProjection> getDtoBookById(Long id);

//    @RestResource(exported = false)
    @Query(value = BOOK_DTO_BASE_STATEMENT, nativeQuery = true)
    List<BookProjection> getDtoBooks();

//    @RestResource(exported = false)
    @Query(value = BOOK_DTO_BASE_STATEMENT, countQuery="SELECT COUNT(*) FROM book", nativeQuery = true)
    Page<BookProjection> getDtoBooksPaged(Pageable pageable);

}
