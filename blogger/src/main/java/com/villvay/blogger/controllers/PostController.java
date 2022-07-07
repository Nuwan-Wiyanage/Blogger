package com.villvay.blogger.controllers;

import com.villvay.blogger.entities.Author;
import com.villvay.blogger.entities.Post;
import com.villvay.blogger.models.dtos.PostDto;
import com.villvay.blogger.models.dtos.ResponseDto;
import com.villvay.blogger.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPosts(HttpServletRequest request, HttpServletResponse response){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(postRepository.findAll());
        responseDto.setMessage("Success");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getPost/{id}")
    public ResponseEntity<?> getPostById(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long postId){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(postRepository.findById(postId));
        responseDto.setMessage("Success");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/author/{id}/getPosts")
    public ResponseEntity<?> getPostsByAuthorId(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") long authorId){
        ResponseDto responseDto = new ResponseDto();
        Author author = new Author();
        author.setId(authorId);
        responseDto.setData(postRepository.getAllByAuthorId(author));
        responseDto.setMessage("Success");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @PostMapping("/addPost")
    public ResponseEntity<?> addPost(@RequestBody PostDto postDto){
        ResponseDto responseDto = new ResponseDto();
        postRepository.save(setPostData(postDto));

        responseDto.setData("success");
        responseDto.setMessage("Post Added successfully");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public Post setPostData(PostDto postDto){
        Post newPost = new Post();
        newPost.setBody(postDto.getBody());
        newPost.setCreatedOn(postDto.getCreated_on());
        newPost.setModifiedOn(postDto.getModified_on());
        newPost.setTitle(postDto.getTitle());
        newPost.setAuthorId(postDto.getAuthor());

        return newPost;
    }

    @PutMapping("/updatePost/{id}")
    public Post replacePost(@RequestBody PostDto postDto, @PathVariable Long id) {

        return postRepository.findById(id)
                .map(newPost -> {
                    newPost.setBody(postDto.getBody());
                    newPost.setCreatedOn(postDto.getCreated_on());
                    newPost.setModifiedOn(postDto.getModified_on());
                    newPost.setTitle(postDto.getTitle());
                    newPost.setAuthorId(postDto.getAuthor());
                    return postRepository.save(newPost);
                })
                .orElseGet(() -> {
                    return postRepository.save(setPostData(postDto));
                });
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }
}
