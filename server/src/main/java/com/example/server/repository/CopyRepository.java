package com.example.server.repository;

import com.example.server.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@Repository
@RestResource(exported = false)
public interface CopyRepository extends JpaRepository<Copy, Long> {

    List<Copy> findByBookId(@Param("id") Long id);

    List<Copy> findByUserId(@Param("id")Long id);

    List<Copy> findByBorrowedTrue();
}
