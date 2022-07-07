package com.villvay.blogger.models.dtos;

import com.villvay.blogger.entities.Author;
import com.villvay.blogger.entities.Post;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentDto {
    private String body;
    private Timestamp created_on;
    private Timestamp modified_on;
    private String email;
    private String name;
    private Post post;
}
