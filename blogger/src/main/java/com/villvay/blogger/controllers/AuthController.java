package com.villvay.blogger.controllers;

import com.villvay.blogger.entities.Role;
import com.villvay.blogger.entities.User;
import com.villvay.blogger.models.dtos.ResponseDto;
import com.villvay.blogger.models.dtos.SignUpDto;
import com.villvay.blogger.models.enums.ERole;
import com.villvay.blogger.repositories.RoleRepository;
import com.villvay.blogger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/signout")
    public ResponseEntity<String> getLogoutPage(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);

        return new ResponseEntity<>("User signed-out successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        ResponseDto response = new ResponseDto();

        if(userRepository.existsByUsername(signUpDto.getUsername())){
            response.setData("failed");
            response.setMessage("Username is already taken!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpDto.getEmail())){
            response.setData("failed");
            response.setMessage("Email is already taken!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName(ERole.ROLE_ADMIN).get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        response.setData("success");
        response.setMessage("User registered successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
