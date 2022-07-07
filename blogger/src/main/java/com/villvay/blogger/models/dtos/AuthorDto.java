package com.villvay.blogger.models.dtos;

import lombok.Data;

@Data
public class AuthorDto {
    private String name;
    private String username;
    private String email;
    private String address;
}
