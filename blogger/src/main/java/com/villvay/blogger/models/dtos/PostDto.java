package com.villvay.blogger.models.dtos;

import com.villvay.blogger.entities.Author;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PostDto {
    private String body;
    private Timestamp created_on;
    private Timestamp modified_on;
    private String title;
    private Author author;
}
