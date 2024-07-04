package com.example.InvoiceSystemCB.mapper;


import com.example.InvoiceSystemCB.dto.UserDTO;
import com.example.InvoiceSystemCB.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole());
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }

}
