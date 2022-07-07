package com.villvay.blogger.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "comments", uniqueConstraints = { @UniqueConstraint(columnNames = {"email"}) })
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String body;
    private Timestamp createdOn;
    private Timestamp modifiedOn;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="postId", nullable=false)
    private Post postId;
}
