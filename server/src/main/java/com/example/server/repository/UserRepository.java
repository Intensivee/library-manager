package com.example.server.repository;

import com.example.server.dtos.UserProjection;
import com.example.server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(exported = false)
    @Query(value = "SELECT user_id as id, username, first_name as firstName, last_name as lastName, email, role from user",
            countQuery = "SELECT COUNT(*) from user", nativeQuery = true)
    Page<UserProjection> getUsersPaginated(Pageable pageable);

    @RestResource(exported = false)
    @Query(value = "SELECT user_id as id, username, first_name as firstName, last_name as lastName, email, role from user",
            countQuery = "SELECT COUNT(*) from user", nativeQuery = true)
    List<UserProjection> getUsers();

    @RestResource(exported = false)
    @Query(value = "SELECT user_id as id, username, first_name as firstName, last_name as lastName, email, role " +
            "FROM user WHERE user_id = ?1", nativeQuery = true)
    Optional<UserProjection> getUserById(Long id);
}
