package com.example.pruebajava.service;

import com.example.pruebajava.config.JwtUtil;
import com.example.pruebajava.dto.request.RegisterUserRequestDTO;
import com.example.pruebajava.dto.response.RegisterUserResponseDTO;
import com.example.pruebajava.model.User;
import com.example.pruebajava.repository.IUserRepository;
import com.example.pruebajava.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void emailRegistered() {
        RegisterUserRequestDTO requestDTO = new RegisterUserRequestDTO("Test Name", "test@test.com", "password", new ArrayList<>());
        when(userRepository.findByEmail(anyString())).thenReturn(new User());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(requestDTO);
        });

        String expectedMessage = "El correo ya esta registrado";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void emailFormatIsIncorrect() {
        RegisterUserRequestDTO requestDTO = new RegisterUserRequestDTO("Test Name", "bad-email", "password", new ArrayList<>());
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(requestDTO);
        });

        String expectedMessage = "El formato del correo electrónico es incorrecto";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenRegisteringUserSuccessfully_thenExpectCorrectResponse() {
        RegisterUserRequestDTO requestDTO = new RegisterUserRequestDTO("Test Name", "good-email@test.com", "password", new ArrayList<>());

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setCreated(new Date());
        savedUser.setModified(new Date());
        savedUser.setLastLogin(new Date());
        savedUser.setActive(true);
        savedUser.setEmail("good-email@test.com");
        savedUser.setPassword("password");
        savedUser.setToken("token");

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtUtil.generateToken(anyString())).thenReturn("token");

        RegisterUserResponseDTO result = userService.registerUser(requestDTO);

        assertNotNull(result, "El resultado no debería ser nulo");
        assertEquals("token", result.getToken(), "El token debería coincidir con el esperado");
        assertEquals(Long.valueOf(1L), result.getId(), "El ID debería coincidir con el esperado");
    }
}
