package com.villvay.blogger;

import com.villvay.blogger.entities.Author;
import com.villvay.blogger.entities.Post;
import com.villvay.blogger.models.dtos.*;
import com.villvay.blogger.repositories.AuthorReposirory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Collections;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BloggerApplicationTests {

	public String validateLogin() throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		LoginDto loginDto = new LoginDto();
		loginDto.setUsername("Nuwan");
		loginDto.setPassword("Nuwan1234");

		HttpEntity<LoginDto> request = new HttpEntity<>(loginDto);
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/auth/signin", HttpMethod.POST, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
		return (String) response.getBody();
	}

	public MultiValueMap<String, String> getHeaders() throws Exception {
		String token = validateLogin();
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.put("Authorization", Collections.singletonList("Bearer " + token));

		return headers;
	}

	@Test
	public void rejectUnauthenticatedRequest() {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<ResponseDto> request = new HttpEntity<>(null,  null);
		ResponseEntity<?> response = null;
		try{
			response = restTemplate
					.exchange("http://localhost:8080/api/author/getAll", HttpMethod.GET, request, String.class);
		} catch (HttpClientErrorException ex) {
			Assertions.assertEquals(ex.getStatusCode(), HttpStatus.FORBIDDEN);
		}

	}

	@Test
	public void getAllAuthors() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/author/getAll", HttpMethod.GET, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getAuthorById() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/author/getAuthor/2", HttpMethod.GET, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getAllPosts() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/post/getAll", HttpMethod.GET, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getPostById() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/post/getPost/1", HttpMethod.GET, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getPostByAuthorId() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/post/author/1/getPosts", HttpMethod.GET, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getAllComments() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/comments/getAll", HttpMethod.GET, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getCommentById() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/comments/getComment/2", HttpMethod.GET, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void getCommentByPostId() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/comments/post/1/getComments", HttpMethod.GET, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void addAuthor() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		AuthorDto authorDto = new AuthorDto();
		authorDto.setEmail("junitTest22@gmail.com");
		authorDto.setName("jUnit Test 22");
		authorDto.setUsername("jUnit Test 22");
		authorDto.setAddress("jUnit Test Address 22");

		HttpEntity<?> request = new HttpEntity<>(authorDto,  getHeaders());

		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/author/addAuthor", HttpMethod.POST, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}


	@Test
	public void updateAuthor() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		AuthorDto authorDto = new AuthorDto();
		authorDto.setEmail("junitTestUpdate2@gmail.com");
		authorDto.setName("jUnit Test Update");
		authorDto.setUsername("jUnit Test Update");
		authorDto.setAddress("jUnit Test Address Update");

		HttpEntity<?> request = new HttpEntity<>(authorDto,  getHeaders());

		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/author/updateAuthor/4", HttpMethod.PUT, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void deleteAuthor() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/author/delete/7", HttpMethod.DELETE, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}


	@Test
	public void addPost() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		PostDto postDto = new PostDto();
		postDto.setBody("junit new post");
		postDto.setCreated_on(new Timestamp(2022-7-8));
		postDto.setModified_on(new Timestamp(2022-7-8));
		postDto.setTitle("junit new post");
		Author postOwner = new Author();
		postOwner.setId(1);
		postDto.setAuthor(postOwner);

		HttpEntity<?> request = new HttpEntity<>(postDto,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/post/addPost", HttpMethod.POST, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}


	@Test
	public void updatePost() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		PostDto postDto = new PostDto();
		postDto.setBody("junit new post update");
		postDto.setCreated_on(new Timestamp(2022-7-8));
		postDto.setModified_on(new Timestamp(2022-7-8));
		postDto.setTitle("junit new post update");
		Author postOwner = new Author();
		postOwner.setId(1);
		postDto.setAuthor(postOwner);

		HttpEntity<?> request = new HttpEntity<>(postDto,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/post/updatePost/1", HttpMethod.PUT, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void deletePost() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/post/delete/6", HttpMethod.DELETE, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}


	@Test
	public void addComment() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		CommentDto commentDto = new CommentDto();
		commentDto.setBody("junit new comment");
		commentDto.setCreated_on(new Timestamp(2022-7-8));
		commentDto.setModified_on(new Timestamp(2022-7-8));
		commentDto.setEmail("junittest@gmail.com");
		commentDto.setName("junit new comment");
		Post commentOwner = new Post();
		commentOwner.setId(1);
		commentDto.setPost(commentOwner);

		HttpEntity<?> request = new HttpEntity<>(commentDto,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/comments/addComment", HttpMethod.POST, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}


	@Test
	public void updateComment() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		CommentDto commentDto = new CommentDto();
		commentDto.setBody("junit comment update");
		commentDto.setCreated_on(new Timestamp(2022-7-8));
		commentDto.setModified_on(new Timestamp(2022-7-8));
		commentDto.setEmail("junittestupdate@gmail.com");
		commentDto.setName("junit comment update");
		Post commentOwner = new Post();
		commentOwner.setId(1);
		commentDto.setPost(commentOwner);

		HttpEntity<?> request = new HttpEntity<>(commentDto,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/comments/updateComment/2", HttpMethod.PUT, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void deleteComment() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(null,  getHeaders());
		ResponseEntity<?> response = restTemplate
				.exchange("http://localhost:8080/api/comments/delete/5", HttpMethod.DELETE, request, String.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
