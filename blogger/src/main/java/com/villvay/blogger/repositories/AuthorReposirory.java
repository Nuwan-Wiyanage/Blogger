package com.villvay.blogger.repositories;

import com.villvay.blogger.entities.Author;
import com.villvay.blogger.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorReposirory extends JpaRepository<Author, Long> {
    Author findById(long id);
    Optional<Author> findByName(String name);
    Optional<Author> findByUsername(String username);
    Optional<Author> findByEmail(String email);
    Optional<Author> findByAddress(String address);
    Boolean existsByEmail(String email);

}
