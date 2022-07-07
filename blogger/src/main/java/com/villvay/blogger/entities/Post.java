package com.villvay.blogger.entities;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String body;
    private Timestamp createdOn;
    private Timestamp modifiedOn;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="authorId", nullable=false)
    private Author authorId;

    @OneToMany(mappedBy="postId")
    private List<Comment> comments;
}
