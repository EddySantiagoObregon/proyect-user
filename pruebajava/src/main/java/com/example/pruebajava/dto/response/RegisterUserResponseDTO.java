package com.example.pruebajava.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponseDTO {

    private Long id;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
    private boolean isActive;

}
