package com.example.server.repository;

import com.example.server.dtos.CopyProjection;
import com.example.server.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource
public interface CopyRepository extends JpaRepository<Copy, Long> {

    List<Copy> findByBookId(@Param("id") Long id);

    @Query(value = "SELECT c.copy_id id, c.pages, borrowed,c.borrow_date borrowDate, c.return_date returnDate " +
            "FROM copy c NATURAL JOIN user u " +
            "WHERE u.user_id=?1", nativeQuery = true)
    List<CopyProjection> findByUserId(@Param("id")Long id);
}
