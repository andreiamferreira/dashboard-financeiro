package com.dashboard.dashboard.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboard.dashboard.Enum.TransactionCategories;
import com.dashboard.dashboard.Enum.TransactionTypeEnum;
import com.dashboard.dashboard.dto.TransactionDTO;
import com.dashboard.dashboard.dto.UpdateTransactionDTO;
import com.dashboard.dashboard.model.BankAccount;
import com.dashboard.dashboard.model.Transaction;
import com.dashboard.dashboard.model.User;
import com.dashboard.dashboard.repository.BankAccountRepository;
import com.dashboard.dashboard.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();

    private TransactionDTO toTransactionDTO(Transaction transaction) {
        return new TransactionDTO(transaction.getId(), transaction.getBankAccount().getId(),
                transaction.getTransactionType(),
                transaction.getCategory(),
                transaction.getAmount(),
                transaction.getIsOut(),
                transaction.getTransactionDate());
    }

    private BankAccount getAccount(Integer accountId) {
        return bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Account not found - Id: " + accountId));
    }

    private Transaction getTransaction(Integer transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found - Id: " + transactionId));
    }

    private String[] TRANSACTION_CATEGORIES_IN = {
            "SALARIO", "EMPRESTIMO"
    };

    Instant currentTime = Instant.now();
    Date today = Date.from(currentTime);

    @Transactional
    public List<TransactionDTO> getAllTransactionsByAccount(User currentUser, Integer accountId) {

        BankAccount account = getAccount(accountId);

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to see these transactions!");
        }

        List<Transaction> transactions = transactionRepository.findAllByBankAccount(account);

        return transactions.stream().map(this::toTransactionDTO).collect(Collectors.toList());
    }

    @Transactional
    public TransactionDTO getTransactionById(User currentUser, Integer accountId, Integer id) {

        BankAccount account = getAccount(accountId);

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to see this transaction!");
        }

        Optional<Transaction> transaction = transactionRepository.findByIdAndBankAccount(id, account);

        TransactionDTO mappedTransactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        return mappedTransactionDTO;
    }

    @Transactional
    public List<TransactionDTO> getTransectionsByCategory(User currentUser, Integer accountId, String categoryString) {

        BankAccount account = getAccount(accountId);

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to see this transaction!");
        }

        TransactionCategories category = TransactionCategories.fromString(categoryString);
        List<Transaction> transactions = transactionRepository.findByCategoryAndBankAccount(category, account);
        return transactions.stream().map(this::toTransactionDTO).collect(Collectors.toList());

    }

    @Transactional
    public List<TransactionDTO> getTransectionsByType(User currentUser, Integer accountId, String typeString) {

        BankAccount account = getAccount(accountId);

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to see these transactions!");
        }

        TransactionTypeEnum type = TransactionTypeEnum.fromString(typeString);
        List<Transaction> transactions = transactionRepository.findByTransactionTypeAndBankAccount(type,
                account);
        return transactions.stream().map(this::toTransactionDTO).collect(Collectors.toList());

    }

    @Transactional
    public List<TransactionDTO> getMostRecents(User currentUser, Integer accountId) {

        BankAccount account = getAccount(accountId);

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to see these transactions!");
        }

        LocalDate today = LocalDate.now();

        List<Transaction> mostRecentTransactions = transactionRepository
                .findByBankAccountAndTransactionDateLessThanEqualOrderByTransactionDateDesc(account, today);

        return mostRecentTransactions.stream().map(this::toTransactionDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<TransactionDTO> getTransactionByInOrOut(User currentUser, Integer accountId, boolean isOut) {

        BankAccount account = getAccount(accountId);

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to update this transaction!");
        }

        List<Transaction> transactionsPositiveOrNegative = (isOut
                ? transactionRepository.findByBankAccountAndIsOutTrue(account)
                : transactionRepository.findByBankAccountAndIsOutFalse(account));

        return transactionsPositiveOrNegative.stream().map(this::toTransactionDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<TransactionDTO> getTransactionsByUser(User currentUser) {

        List<BankAccount> accounts = bankAccountRepository.findAllByUserId(currentUser.getId());

        List<Transaction> allTransactions = accounts.stream()
                .flatMap(account -> transactionRepository.findAllByBankAccount(account).stream())
                .collect(Collectors.toList());

        return allTransactions.stream().map(this::toTransactionDTO).collect(Collectors.toList());
    }

    @Transactional
    public TransactionDTO addTransaction(User currentUser, TransactionDTO transactionDTO) {
        BankAccount account = getAccount(transactionDTO.getBankAccId());

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to create this transaction!");
        }

        Transaction transaction = new Transaction();

        String inputCategory = transactionDTO.getCategory().toString();
        boolean isOut = !Arrays.asList(TRANSACTION_CATEGORIES_IN).contains(inputCategory);

        if (isOut) {
            transactionDTO.setIsOut(true);
            account.setBalance(account.getBalance().subtract(transactionDTO.getAmount()));
        } else {
            transactionDTO.setIsOut(false);
            account.setBalance(account.getBalance().add(transactionDTO.getAmount()));
        }

        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setCategory(transactionDTO.getCategory());
        transaction.setIsOut(transactionDTO.getIsOut());
        transaction.setBankAccount(account);

        transactionDTO.setBankAccId(account.getId());

        transactionRepository.save(transaction);

        TransactionDTO mappedTransactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        return mappedTransactionDTO;
    }

    @Transactional
    public UpdateTransactionDTO updateTransaction(User currentUser, Integer id,
            UpdateTransactionDTO transactionDTO) {

        BankAccount account = getAccount(transactionDTO.getBankAccId());

        if (!account.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to update this transaction!");
        }

        Transaction transaction = getTransaction(id);

        BigDecimal oldAmount = transaction.getAmount();
        BigDecimal newAmount = transactionDTO.getAmount();
        boolean isOut = transaction.getIsOut();

        BigDecimal difference = newAmount.subtract(oldAmount);

        if (isOut) {

            account.setBalance(account.getBalance().add(difference));
        } else {

            account.setBalance(account.getBalance().subtract(difference));
        }

        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());

        transaction.setBankAccount(account);

        transaction.setUpdatedAt(today);

        Transaction updatedTransaction = transactionRepository.save(transaction);

        return modelMapper.map(updatedTransaction, UpdateTransactionDTO.class);
    }

    @Transactional
    public void deleteTransaction(User currentUser, Integer id) {
        Transaction transaction = getTransaction(id);

        if (!transaction.getBankAccount().getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You are not authorized to delete this account");
        }

        transaction.setUpdatedAt(today);
        transaction.setDeletedAt(today);

        transactionRepository.deleteById(id);

    }

}
