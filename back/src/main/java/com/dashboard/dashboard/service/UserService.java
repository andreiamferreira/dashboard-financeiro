package com.dashboard.dashboard.service;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dashboard.dashboard.Enum.UserRole;
import com.dashboard.dashboard.dto.UserDTO;
import com.dashboard.dashboard.dto.UserResponseDTO;
import com.dashboard.dashboard.model.User;
import com.dashboard.dashboard.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    Instant currentTime = Instant.now();
    Date today = Date.from(currentTime);

    private UserDTO toUserDto(User user) {
        return new UserDTO(user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getPassword(),
                user.getRole());
    }

    private UserResponseDTO toUserResponseDto(User user) {
        return new UserResponseDTO(user.getId(), user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getRole());
    }

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findByIsDeletedFalse();
        return users.stream()
                .map(this::toUserResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDTO getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Transactional
    public UserDTO getUserByName(String name) {
        Optional<User> user = userRepository.findByNameIgnoreCase(name);
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserDTO getUserByCpf(String cpf) {
        Optional<User> user = userRepository.findByCpf(cpf);
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public List<UserDTO> getAllUsersWithACeratainName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return users.stream().map(this::toUserDto).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws MessagingException, IOException {

        if (userRepository.findByEmailIgnoreCase(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use: " + userDTO.getEmail());

        }

        if (userRepository.findByCpf(userDTO.getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF already in use: " + userDTO.getCpf());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        User user = new User();
        user.setName(userDTO.getName());
        user.setCpf(userDTO.getCpf());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encryptedPassword);
        user.setRole(UserRole.USER);

        userRepository.save(user);

        emailService.sendWelcomingEmail(user.getName(), user.getEmail());

        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserDTO updateUser(Integer id, UserDTO userDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        user.setName(userDTO.getName());
        user.setCpf(userDTO.getCpf());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUpdatedAt(today);

        userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public void deleteUser(User currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found: " + currentUser.getId()));

        user.setUpdatedAt(today);
        user.setDeletedAt(today);

        userRepository.deleteById(currentUser.getId());
    }

    @Transactional
    public String forgotPassword(String email) throws IOException {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        try {
            emailService.sendForgotPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send password reset email! Please, try again");
        }
        return "Please, check your email";
    }

    @Transactional
    public String resetPassword(String email, String newPassword) throws IOException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        System.out.println(email);
        System.out.println(newPassword);

        String encryptedNewPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encryptedNewPassword);
        user.setUpdatedAt(today);
        userRepository.save(user);

        return "New password set succesfully! Login with your new credentials";
    }

}
