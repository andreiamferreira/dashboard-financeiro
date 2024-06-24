package com.dashboard.dashboard.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.dashboard.dashboard.Enum.AccountTypeEnum;
import com.dashboard.dashboard.Enum.InstitutionEnum;
import com.dashboard.dashboard.Enum.UserRole;
import com.dashboard.dashboard.dto.BankAccountDTO;
import com.dashboard.dashboard.dto.UserDTO;
import com.dashboard.dashboard.model.User;
import com.dashboard.dashboard.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BankAccountService accountService;

    @Mock
    private UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // @DisplayName("aaa")
    // void createaaaaaaaa() {
    // UserDTO userDTO = new UserDTO("26926031096", "teste", "teste@gmail.com",
    // "aaaaaa1234",
    // null);
    // User user = new User(userDTO);

    // BankAccountDTO acc = new BankAccountDTO(user.getId(),
    // AccountTypeEnum.CONTA_CORRENTE,
    // InstitutionEnum.BANCO_DO_BRASIL,
    // new BigDecimal(2000.00));

    // when(accountService.addBankAcc(user, acc)).thenReturn(acc);

    // BankAccountDTO createdBankAccount = accountService.addBankAcc(user, acc);

    // verify(accountService, times(1)).addBankAcc(user, acc);
    // assertEquals(acc, createdBankAccount);

    // }

    @Test
    @DisplayName("aaaaa")
    void createAAAA() throws MessagingException, IOException {
        UserDTO user = new UserDTO("26926031096", "teste", "teste@gmail.com", "aaaaaa1234",
                null);
        when(userService.getUserByName(user.getName())).thenReturn(user);

        verify(userService, times(1)).createUser(any());

    }

    @Test
    @DisplayName("Should get User successfully from database")
    void testFindByCpfSuccess() {
        String cpf = "85350558005";
        UserDTO userDTO = new UserDTO(cpf, "teste", "teste@gmail.com", "aaaaaa1234",
                UserRole.USER);
        User newUser = new User(userDTO);

        Optional<User> foundUser = this.userRepository.findByCpf(cpf);

        assertThat(foundUser.isPresent()).isTrue();

    }

}
