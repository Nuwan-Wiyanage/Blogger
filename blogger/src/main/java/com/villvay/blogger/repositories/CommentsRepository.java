package com.villvay.blogger.repositories;

import com.villvay.blogger.entities.Author;
import com.villvay.blogger.entities.Comment;
import com.villvay.blogger.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(long id);
    Optional<Comment> findByName(String name);
    Optional<Comment> findByEmail(String email);
    Optional<Comment> findByBody(String body);
    Optional<Comment> findByCreatedOn(Timestamp createdOn);
    Optional<Comment> findByModifiedOn(Timestamp modifiedOn);

    List<Comment> findAllByPostId(Post postId);
}
