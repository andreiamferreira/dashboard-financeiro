package com.dashboard.dashboard.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.dashboard.dashboard.Enum.AccountTypeEnum;
import com.dashboard.dashboard.Enum.InstitutionEnum;
import com.dashboard.dashboard.dto.BankAccountDTO;
import com.dashboard.dashboard.dto.SumOfAccountsByInstitutionDTO;
import com.dashboard.dashboard.dto.SumOfAccountsByTypeDTO;
import com.dashboard.dashboard.model.BankAccount;
import com.dashboard.dashboard.model.User;
import com.dashboard.dashboard.repository.BankAccountRepository;

import jakarta.transaction.Transactional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private BankAccountDTO toBankAccountDTO(BankAccount bankAcc) {
        return new BankAccountDTO(bankAcc.getId(), bankAcc.getUser().getId(), bankAcc.getAccountType(),
                bankAcc.getInstitution(),
                bankAcc.getBalance());
    }

    Instant currentTime = Instant.now();
    Date today = Date.from(currentTime);

    @Transactional
    public List<BankAccountDTO> getAllAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        return bankAccounts.stream().map(this::toBankAccountDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<BankAccountDTO> getAllAccountsByUser(User currentUser) {
        List<BankAccount> bankAccounts = bankAccountRepository.findByUser(currentUser);

        return bankAccounts.stream().map(this::toBankAccountDTO).collect(Collectors.toList());
    }

    @Transactional
    public SumOfAccountsByTypeDTO getAccountsByType(User currentUser, String typeString) {
        AccountTypeEnum type = AccountTypeEnum.fromString(typeString);
        List<BankAccount> accounts = bankAccountRepository.findByAccountTypeAndUser(type, currentUser);

        List<BankAccountDTO> accountDTOs = accounts.stream()
                .map(this::toBankAccountDTO)
                .collect(Collectors.toList());

        BigDecimal totalBalance = accounts.stream()
                .map(BankAccount::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer numberOfAccounts = accounts.size();

        return new SumOfAccountsByTypeDTO(accountDTOs, totalBalance, numberOfAccounts);
    }

    @Transactional
    public BankAccountDTO getAccountById(User currentUser, Integer id) {
        Optional<BankAccount> bankAcc = bankAccountRepository.findByIdAndUser(id, currentUser);

        return modelMapper.map(bankAcc, BankAccountDTO.class);
    }

    @Transactional
    public SumOfAccountsByInstitutionDTO getAccountsByInstitution(User currentUser, String institutionString) {

        InstitutionEnum institution = InstitutionEnum.fromString(institutionString);
        List<BankAccount> accounts = bankAccountRepository.findByInstitutionAndUser(institution, currentUser);

        List<BankAccountDTO> accountDTOs = accounts.stream()
                .map(this::toBankAccountDTO)
                .collect(Collectors.toList());

        BigDecimal totalBalance = accounts.stream()
                .map(BankAccount::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer numberOfAccounts = accounts.size();

        return new SumOfAccountsByInstitutionDTO(accountDTOs, totalBalance, numberOfAccounts);

    }

    @Transactional
    public List<InstitutionEnum> getMostUsedInstitution(User user) {
        List<BankAccount> mostUsed = bankAccountRepository.findByUser(user);

        Map<InstitutionEnum, Long> institutionsRankedByMostUsed = mostUsed.stream()
                .collect(Collectors.groupingBy(BankAccount::getInstitution, Collectors.counting()));

        List<Map.Entry<InstitutionEnum, Long>> sortedEntries = institutionsRankedByMostUsed.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        List<InstitutionEnum> mostUsedInstitutions = new ArrayList<>();
        for (Map.Entry<InstitutionEnum, Long> entry : sortedEntries) {
            mostUsedInstitutions.add(entry.getKey());
        }

        return mostUsedInstitutions;
    }

    @Transactional
    public BankAccountDTO addBankAcc(User currentUser, BankAccountDTO bankAccDTO) {

        BankAccount bankAcc = new BankAccount();

        bankAcc.setAccountType(bankAccDTO.getAccountType());
        bankAcc.setInstitution(bankAccDTO.getInstitution());
        bankAcc.setBalance(bankAccDTO.getBalance());

        bankAcc.setUser(currentUser);

        bankAccountRepository.save(bankAcc);
        return modelMapper.map(bankAcc, BankAccountDTO.class);

    }

    @Transactional
    public BankAccountDTO updateBankAcc(User currentUser, BankAccountDTO bankAccDTO, Integer id) {

        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + id));

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to update this account");
        }

        account.setAccountType(bankAccDTO.getAccountType());
        account.setInstitution(bankAccDTO.getInstitution());
        account.setBalance(bankAccDTO.getBalance());

        account.setUpdatedAt(today);

        bankAccountRepository.save(account);
        return modelMapper.map(account, BankAccountDTO.class);
    }

    public void deleteBankAccount(User user, Integer id) {
        BankAccount account = bankAccountRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));

        account.setUpdatedAt(today);
        account.setDeletedAt(today);

        bankAccountRepository.delete(account);

    }

}
