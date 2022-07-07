package com.villvay.blogger.controllers;

import com.villvay.blogger.entities.Comment;
import com.villvay.blogger.entities.Post;
import com.villvay.blogger.models.dtos.CommentDto;
import com.villvay.blogger.models.dtos.ResponseDto;
import com.villvay.blogger.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsRepository commentsRepository;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllComments(HttpServletRequest request, HttpServletResponse response){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(commentsRepository.findAll());
        responseDto.setMessage("Success");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getComment/{id}")
    public ResponseEntity<?> getCommentsById(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long commentId){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(commentsRepository.findById(commentId));
        responseDto.setMessage("Success");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/post/{id}/getComments")
    public ResponseEntity<?> getCommentsByPostId(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long postId){
        ResponseDto responseDto = new ResponseDto();
        Post post = new Post();
        post.setId(postId);
        responseDto.setData(commentsRepository.findAllByPostId(post));
        responseDto.setMessage("Success");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @PostMapping("/addComment")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto){
        ResponseDto responseDto = new ResponseDto();
        commentsRepository.save(setCommentData(commentDto));

        responseDto.setData("success");
        responseDto.setMessage("Comment Added successfully");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public Comment setCommentData(CommentDto commentDto){
        Comment newComment = new Comment();
        newComment.setBody(commentDto.getBody());
        newComment.setCreatedOn(commentDto.getCreated_on());
        newComment.setModifiedOn(commentDto.getModified_on());
        newComment.setEmail(commentDto.getEmail());
        newComment.setName(commentDto.getName());
        newComment.setPostId(commentDto.getPost());

        return newComment;
    }

    @PutMapping("/updateComment/{id}")
    public Comment replaceComment(@RequestBody CommentDto commentDto, @PathVariable Long id) {

        return commentsRepository.findById(id)
                .map(newComment -> {
                    newComment.setBody(commentDto.getBody());
                    newComment.setCreatedOn(commentDto.getCreated_on());
                    newComment.setModifiedOn(commentDto.getModified_on());
                    newComment.setEmail(commentDto.getEmail());
                    newComment.setName(commentDto.getName());
                    newComment.setPostId(commentDto.getPost());
                    return commentsRepository.save(newComment);
                })
                .orElseGet(() -> {
                    return commentsRepository.save(setCommentData(commentDto));
                });
    }

    @DeleteMapping("/delete/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentsRepository.deleteById(id);
    }
}
