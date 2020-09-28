package com.example.server.repository;

import com.example.server.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource
public interface CopyRepository extends JpaRepository<Copy, Long> {
}
