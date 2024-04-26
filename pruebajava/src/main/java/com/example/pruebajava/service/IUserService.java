package com.example.pruebajava.service;

import com.example.pruebajava.dto.request.RegisterUserRequestDTO;
import com.example.pruebajava.dto.response.RegisterUserResponseDTO;
import com.example.pruebajava.model.User;

public interface IUserService {

    RegisterUserResponseDTO registerUser(RegisterUserRequestDTO requestDTO);

}
