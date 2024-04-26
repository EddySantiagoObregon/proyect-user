package com.example.pruebajava.service.impl;

import com.example.pruebajava.config.JwtUtil;
import com.example.pruebajava.dto.PhoneDTO;
import com.example.pruebajava.dto.request.RegisterUserRequestDTO;
import com.example.pruebajava.dto.response.RegisterUserResponseDTO;
import com.example.pruebajava.model.Phone;
import com.example.pruebajava.model.User;
import com.example.pruebajava.repository.IUserRepository;
import com.example.pruebajava.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Override
    public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO requestDTO) {
        if (userRepository.findByEmail(requestDTO.getEmail()) != null){
            throw new RuntimeException("El correo ya esta registrado");
        }

        if (!isValidEmail(requestDTO.getEmail())){
            throw new RuntimeException("El formato del correo electrónico es incorrecto");
        }

        List<Phone> phones = new ArrayList<>();
        for (PhoneDTO phoneDTO : requestDTO.getPhones()){
            Phone phone = new Phone(phoneDTO.getNumber(), phoneDTO.getCityCode(), phoneDTO.getCountryCode());
            phones.add(phone);
        }

        User user = new User(
                requestDTO.getName(),
                requestDTO.getEmail(),
                requestDTO.getPassword(),
                phones
        );

        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());

        RegisterUserResponseDTO responseDTO = new RegisterUserResponseDTO(
                user.getId(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                token,
                user.isActive()
        );

        return responseDTO;
    }

    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
