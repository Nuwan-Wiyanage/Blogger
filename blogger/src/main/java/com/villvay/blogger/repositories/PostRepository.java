package com.villvay.blogger.repositories;

import com.villvay.blogger.entities.Author;
import com.villvay.blogger.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository  extends JpaRepository<Post, Long> {
    Optional<Post> findById(long id);
    Optional<Post> findByTitle(String title);
    Optional<Post> findByBody(String body);
    Optional<Post> findByCreatedOn(Timestamp createdOn);
    Optional<Post> findByModifiedOn(Timestamp modifiedOn);

    List<Post> getAllByAuthorId(Author author);
}
