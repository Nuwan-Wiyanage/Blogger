package com.villvay.blogger.controllers;

import com.villvay.blogger.entities.Author;
import com.villvay.blogger.models.dtos.AuthorDto;
import com.villvay.blogger.models.dtos.ResponseDto;
import com.villvay.blogger.repositories.AuthorReposirory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    private AuthorReposirory authorRepository;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAuthors(){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(authorRepository.findAll());
        responseDto.setMessage("Success");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @GetMapping("/getAuthor/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable("id") long authorId){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(authorRepository.findById(authorId));
        responseDto.setMessage("Success");

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/addAuthor")
    public ResponseEntity<?> addAuthor(@RequestBody AuthorDto authorDto){
        ResponseDto responseDto = new ResponseDto();

        // add check for email exists in a DB
        if(authorRepository.existsByEmail(authorDto.getEmail())){
            responseDto.setData("failed");
            responseDto.setMessage("Author is already exists!");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        authorRepository.save(setAuthorData(authorDto));

        responseDto.setData("success");
        responseDto.setMessage("Author registered successfully");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    public Author setAuthorData(AuthorDto authorDto){
        Author newAuthor = new Author();
        newAuthor.setEmail(authorDto.getEmail());
        newAuthor.setName(authorDto.getName());
        newAuthor.setUsername(authorDto.getUsername());
        newAuthor.setAddress(authorDto.getAddress());

        return newAuthor;
    }

    @PutMapping("/updateAuthor/{id}")
    public ResponseEntity<?> replaceAuthor(@RequestBody AuthorDto authorDto, @PathVariable Long id) {

        ResponseDto responseDto = new ResponseDto();
        if(authorRepository.existsByEmail(authorDto.getEmail())){
            responseDto.setData("failed");
            responseDto.setMessage("Author email is already exists!");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        authorRepository.findById(id)
                .map(newAuthor -> {
                    newAuthor.setName(authorDto.getName());
                    newAuthor.setEmail(authorDto.getEmail());
                    newAuthor.setUsername(authorDto.getUsername());
                    newAuthor.setAddress(authorDto.getAddress());
                    authorRepository.save(newAuthor);
                    return true;
                })
                .orElseGet(() -> {
                    authorRepository.save(setAuthorData(authorDto));
                    return true;
                });

        responseDto.setMessage("Author updated successfully");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }
}
