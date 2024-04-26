package com.example.pruebajava.controller;

import com.example.pruebajava.dto.request.RegisterUserRequestDTO;
import com.example.pruebajava.dto.response.RegisterUserResponseDTO;
import com.example.pruebajava.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Test
    void RegisterUserSuccessfully() throws Exception {
        RegisterUserRequestDTO requestDTO = new RegisterUserRequestDTO("Test Name", "good-email@test.com", "password", new ArrayList<>());
        RegisterUserResponseDTO responseDTO = new RegisterUserResponseDTO(1L, new Date(), new Date(), new Date(), "token", true);

        given(userService.registerUser(any(RegisterUserRequestDTO.class))).willReturn(responseDTO);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Name\",\"email\":\"good-email@test.com\",\"password\":\"password\",\"phones\":[]}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token"));
    }

}
