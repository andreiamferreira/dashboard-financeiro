package com.dashboard.dashboard.repository;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dashboard.dashboard.Enum.UserRole;
import com.dashboard.dashboard.dto.UserDTO;
import com.dashboard.dashboard.model.User;

import static org.assertj.core.api.Assertions.*;

import jakarta.persistence.EntityManager;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        User newUser = new User("77475251098", "a", "aaaa@gmail.com", "aaaaaaa", null, null, false, UserRole.USER);
        userRepository.save(newUser);
    }

    @Test
    @DisplayName("Should get User successfully from database")
    void testFindByCpfSuccess() {
        String cpf = "85350558005";
        UserDTO userDTO = new UserDTO(cpf, "teste", "teste@gmail.com", "aaaaaa1234",
                UserRole.USER);
        this.createUser(userDTO);

        Optional<User> foundUser = this.userRepository.findByCpf(cpf);

        assertThat(foundUser.isPresent()).isTrue();
    }

    private User createUser(UserDTO data) {
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }

    // @Test
    // void testFindByEmailIgnoreCase() {

    // }

    // @Test
    // void testFindById() {

    // }

    // @Test
    // void testFindByIsDeletedFalse() {

    // }

    // @Test
    // void testFindByNameContainingIgnoreCase() {

    // }

    // @Test
    // void testFindByNameIgnoreCase() {

    // }
}
