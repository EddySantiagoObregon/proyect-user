package com.example.pruebajava.dto.request;

import com.example.pruebajava.dto.PhoneDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestDTO {

    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;

}
