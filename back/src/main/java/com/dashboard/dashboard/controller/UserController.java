package com.dashboard.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.dashboard.dashboard.repository.UserRepository;
import com.dashboard.dashboard.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import com.dashboard.dashboard.dto.UserResponseDTO;
import com.dashboard.dashboard.dto.UserDTO;
import com.dashboard.dashboard.model.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Users", description = "User routes")
@SecurityRequirement(name = "bearerAuth")
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserRepository userRepository) {
    }

    @GetMapping("/")
    public ResponseEntity<EntityModel<UserResponseDTO>> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        UserResponseDTO user = userService.getUserById(currentUser.getId());
        EntityModel<UserResponseDTO> entityModel = EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUsers()).withRel("Users list"));
        return ResponseEntity.ok(entityModel);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<List<EntityModel<UserResponseDTO>>> getUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();

        List<EntityModel<UserResponseDTO>> entityModels = users.stream()
                .map(userDTO -> EntityModel.of(userDTO,
                        linkTo(methodOn(UserController.class).getUserById(userDTO.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(entityModels);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> getUserById(@PathVariable Integer id) {
        UserResponseDTO user = userService.getUserById(id);
        EntityModel<UserResponseDTO> entityModel = EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUsers()).withRel("Users list"));
        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/name/")
    public UserDTO getUserByName(@AuthenticationPrincipal User currentUser) {
        return userService.getUserByName(currentUser.getName());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/name/{name}")
    public UserDTO getAnyUseByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/all/name/{name}")
    public List<UserDTO> getAllUsersWithACeratainName(@PathVariable String name) {
        return userService.getAllUsersWithACeratainName(name);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/cpf={cpf}")
    public UserDTO getUserByCpf(@PathVariable String cpf) {
        return userService.getUserByCpf(cpf);
    }

    @PostMapping("/add")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserDTO userDTO)
            throws MessagingException, IOException {
        UserDTO newUser = userService.createUser(userDTO);
        UserResponseDTO newUserResponseDTO = new UserResponseDTO(newUser.getId(), newUser.getName(),
                newUser.getCpf(), newUser.getEmail(), newUser.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(newUserResponseDTO);

    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@AuthenticationPrincipal User currentUser, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(currentUser.getId(), userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@AuthenticationPrincipal User currentUser) {
        userService.deleteUser(currentUser);
    }

}
