package com.example.InvoiceSystemCB.service;


import com.example.InvoiceSystemCB.dto.UserDTO;
import com.example.InvoiceSystemCB.mapper.UserMapper;
import com.example.InvoiceSystemCB.model.User;
import com.example.InvoiceSystemCB.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public Optional<UserDTO> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return Optional.of(userMapper.toDTO(user.get()));
        } else {
            return Optional.empty();
        }
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}
