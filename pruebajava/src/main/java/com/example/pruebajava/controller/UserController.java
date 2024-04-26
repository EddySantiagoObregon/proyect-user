package com.example.pruebajava.controller;

import com.example.pruebajava.dto.request.RegisterUserRequestDTO;
import com.example.pruebajava.dto.response.ErrorResponseDTO;
import com.example.pruebajava.dto.response.RegisterUserResponseDTO;
import com.example.pruebajava.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequestDTO requestDTO){
        try {
            RegisterUserResponseDTO responseDTO = userService.registerUser(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }catch (RuntimeException e){
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
