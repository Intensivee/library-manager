package com.example.server.repository;

import com.example.server.dtos.BookDto;
import com.example.server.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    String BOOKS_QUERY = "SELECT b.book_id as id, b.title as title, b.description as description," +
            " b.image_url as imageUrl, a.author_id as authorId, CONCAT(a.first_name,' ',a.last_name) as authorName " +
            "FROM book b " +
            "NATURAL JOIN " +
            "author a";

    String BOOK_BY_ID_QUERY = "SELECT b.book_id as id, b.title as title, b.description as description, " +
            "b.image_url as imageUrl, a.author_id as authorId, CONCAT(a.first_name,' ',a.last_name) as authorName " +
            "FROM book b " +
            "NATURAL JOIN " +
            "author a WHERE b.book_id = ?1";

    String BOOK_BY_TITLE_QUERY = "SELECT b.book_id as id, b.title as title, b.description as description, " +
            "b.image_url as imageUrl, a.author_id as authorId, CONCAT(a.first_name,' ',a.last_name) as authorName " +
            "FROM book b " +
            "NATURAL JOIN " +
            "author a WHERE b.title LIKE %?1%";

    String BOOKS_BY_CATEGORY_QUERY = "SELECT b.book_id as Id, b.title as Title, b.description as Description, " +
            "b.image_url as imageUrl, a.author_id as AuthorId, CONCAT(a.first_name,' ',a.last_name) as authorName " +
            "FROM book b " +
            "NATURAL JOIN author a " +
            "NATURAL JOIN book_category as bc " +
            "WHERE bc.category_id = ?1";

    @RestResource(exported = false)
    @Query(value = BOOK_BY_ID_QUERY, nativeQuery = true)
    Optional<BookDto> getBookById(Long id);

    @RestResource(exported = false)
    @Query(value = BOOKS_QUERY, nativeQuery = true)
    List<BookDto> getBooks();

    @RestResource(exported = false)
    @Query(value = BOOKS_QUERY, countQuery = "SELECT COUNT(*) FROM book", nativeQuery = true)
    Page<BookDto> getBooksPaged(Pageable pageable);

    @RestResource(exported = false)
    @Query(value = BOOKS_BY_CATEGORY_QUERY, countQuery = "SELECT COUNT(*) FROM book_category WHERE category_id = ?1", nativeQuery = true)
    Page<BookDto> getBooksByCategory(Long id, Pageable pageable);


    @RestResource(exported = false)
    @Query(value = BOOK_BY_TITLE_QUERY, countQuery = "SELECT COUNT(*) FROM book WHERE book.title LIKE ?1", nativeQuery = true)
    Page<BookDto> getBooksByTitle(String title, Pageable pageable);
}
