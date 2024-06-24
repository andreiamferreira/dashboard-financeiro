package com.dashboard.dashboard.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.dashboard.dashboard.Enum.TransactionCategories;
import com.dashboard.dashboard.Enum.TransactionTypeEnum;
import com.dashboard.dashboard.dto.BankAccountDTO;
import com.dashboard.dashboard.dto.TransactionDTO;
import com.dashboard.dashboard.dto.UpdateTransactionDTO;
import com.dashboard.dashboard.exceptions.InvalidPathVariableException;
import com.dashboard.dashboard.exceptions.TransactionNotFoundException;
import com.dashboard.dashboard.model.User;
import com.dashboard.dashboard.service.BankAccountService;
import com.dashboard.dashboard.service.TransactionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transactions", description = "Transaction routes")
@SecurityRequirement(name = "bearerAuth")
@Validated
@RestController
@RequestMapping("/transactions")
public class TransactionController {
        @Autowired
        private TransactionService transactionService;

        @Autowired
        private BankAccountService accountService;

        @GetMapping("/all")
        public ResponseEntity<List<TransactionDTO>> getAllTransactionsByUser(
                        @AuthenticationPrincipal User currentUser) {

                List<TransactionDTO> transactions = transactionService.getTransactionsByUser(currentUser);

                return ResponseEntity.ok(transactions);
        }

        @GetMapping("/account/{id}")
        public ResponseEntity<List<EntityModel<TransactionDTO>>> getAllTransactionsByAccount(
                        @AuthenticationPrincipal User currentUser,
                        @PathVariable Integer id) {
                List<TransactionDTO> transactions = transactionService.getAllTransactionsByAccount(currentUser, id);

                List<EntityModel<TransactionDTO>> entityModels = transactions.stream()
                                .map(transactionDTO -> EntityModel.of(transactionDTO,
                                                linkTo(methodOn(TransactionController.class)
                                                                .getTransactionById(currentUser, id,
                                                                                transactionDTO.getId()))
                                                                .withSelfRel()))
                                .collect(Collectors.toList());
                return ResponseEntity.ok(entityModels);
        }

        @GetMapping("/account/{accountId}/{id}")
        public ResponseEntity<EntityModel<TransactionDTO>> getTransactionById(@AuthenticationPrincipal User currentUser,
                        @PathVariable Integer accountId,
                        @PathVariable Integer id) {
                BankAccountDTO acc = accountService.getAccountById(currentUser, accountId);
                TransactionDTO transaction = transactionService.getTransactionById(currentUser, acc.getId(), id);

                if (Objects.isNull(transaction)) {
                        throw new TransactionNotFoundException();
                }
                EntityModel<TransactionDTO> entityModel = EntityModel.of(transaction,
                                linkTo(methodOn(TransactionController.class).getAllTransactionsByAccount(currentUser,
                                                accountId))
                                                .withRel("Transaction list"));

                return ResponseEntity.ok(entityModel);
        }

        @GetMapping("/account/{accountId}/category={category}")
        public ResponseEntity<List<EntityModel<TransactionDTO>>> getByCategory(
                        @AuthenticationPrincipal User currentUser, @PathVariable Integer accountId,
                        @PathVariable String category) {
                BankAccountDTO acc = accountService.getAccountById(currentUser, accountId);
                List<TransactionDTO> transactions = transactionService.getTransectionsByCategory(currentUser,
                                acc.getId(),
                                category);
                if (transactions == null) {
                        throw new InvalidPathVariableException("Invalid transaction category: " + category);
                }

                List<EntityModel<TransactionDTO>> entityModels = transactions.stream()
                                .map(transactionDTO -> EntityModel.of(transactionDTO,
                                                linkTo(methodOn(TransactionController.class)
                                                                .getTransactionById(currentUser, accountId,
                                                                                transactionDTO.getId()))
                                                                .withSelfRel()))
                                .collect(Collectors.toList());
                return ResponseEntity.ok(entityModels);
        }

        @GetMapping("/account/{accountId}/type={type}")
        public ResponseEntity<List<EntityModel<TransactionDTO>>> getByType(@AuthenticationPrincipal User currentUser,
                        @PathVariable Integer accountId,
                        @PathVariable String type) {
                BankAccountDTO acc = accountService.getAccountById(currentUser, accountId);

                List<TransactionDTO> types = transactionService.getTransectionsByType(currentUser, acc.getId(), type);

                if (types == null) {
                        throw new InvalidPathVariableException("Invalid transaction type: " + type);

                }
                List<EntityModel<TransactionDTO>> entityModels = types.stream()
                                .map(transactionDTO -> EntityModel.of(transactionDTO,
                                                linkTo(methodOn(TransactionController.class)
                                                                .getTransactionById(currentUser, accountId,
                                                                                transactionDTO.getId()))
                                                                .withSelfRel()))
                                .collect(Collectors.toList());
                return ResponseEntity.ok(entityModels);

        }

        @GetMapping("/account/{accountId}/isOut={isOut}")
        public ResponseEntity<List<TransactionDTO>> getInTransactions(@AuthenticationPrincipal User currentUser,
                        @PathVariable Integer accountId,
                        @PathVariable boolean isOut) {
                BankAccountDTO acc = accountService.getAccountById(currentUser, accountId);

                List<TransactionDTO> inOrOutTransactions = transactionService.getTransactionByInOrOut(currentUser,
                                acc.getId(),
                                isOut);
                return ResponseEntity.ok(inOrOutTransactions);
        }

        @GetMapping("/mostRecents/account/{accountId}")
        public ResponseEntity<List<EntityModel<TransactionDTO>>> getMostRecents(
                        @AuthenticationPrincipal User currentUser, @PathVariable Integer accountId) {
                List<TransactionDTO> mostRecents = transactionService.getMostRecents(currentUser, accountId);

                List<EntityModel<TransactionDTO>> entityModels = mostRecents.stream()
                                .map(transactionDTO -> EntityModel.of(transactionDTO,
                                                linkTo(methodOn(TransactionController.class)
                                                                .getTransactionById(currentUser, accountId,
                                                                                transactionDTO.getId()))
                                                                .withSelfRel()))
                                .collect(Collectors.toList());
                return ResponseEntity.ok(entityModels);
        }

        @GetMapping("/categories")
        public ResponseEntity<Map<TransactionCategories, Long>> getMostUsedCategoriesByUser(
                        @AuthenticationPrincipal User currentUser) {

                List<TransactionDTO> transactions = transactionService.getTransactionsByUser(currentUser);

                Map<TransactionCategories, Long> categoriesRankedByMostUsed = transactions.stream()
                                .collect(Collectors.groupingBy(TransactionDTO::getCategory, Collectors.counting()));

                return ResponseEntity.ok(categoriesRankedByMostUsed);
        }

        @GetMapping("/types")
        public ResponseEntity<Map<TransactionTypeEnum, Long>> getTransactionTypesUsedByUser(
                        @AuthenticationPrincipal User currentUser) {

                List<TransactionDTO> transactions = transactionService.getTransactionsByUser(currentUser);

                Map<TransactionTypeEnum, Long> transactionTypes = transactions.stream()
                                .collect(Collectors.groupingBy(TransactionDTO::getTransactionType,
                                                Collectors.counting()));

                return ResponseEntity.ok(transactionTypes);
        }

        @GetMapping("/inout")
        public ResponseEntity<Map<String, Map<String, BigDecimal>>> getTransactionSummaryByMonth(
                        @AuthenticationPrincipal User currentUser) {

                List<TransactionDTO> transactions = transactionService.getTransactionsByUser(currentUser);

                Map<String, Map<String, BigDecimal>> monthlySummary = transactions.stream()
                                .filter(t -> t.getTransactionDate() != null)
                                .collect(Collectors.groupingBy(
                                                t -> t.getTransactionDate().getYear() + "-"
                                                                + t.getTransactionDate().getMonthValue(),
                                                Collectors.groupingBy(
                                                                t -> t.getIsOut() ? "out" : "in",
                                                                Collectors.reducing(BigDecimal.ZERO,
                                                                                TransactionDTO::getAmount,
                                                                                BigDecimal::add))));

                return ResponseEntity.ok(monthlySummary);
        }

        @GetMapping("/types/monthly")
        public ResponseEntity<Map<String, Map<TransactionTypeEnum, Map<String, BigDecimal>>>> getTransactionSummaryByCategory(
                        @AuthenticationPrincipal User currentUser) {

                List<TransactionDTO> transactions = transactionService.getTransactionsByUser(currentUser);

                Map<String, Map<TransactionTypeEnum, Map<String, BigDecimal>>> monthlyTransactionTypeSummary = transactions
                                .stream()
                                .filter(t -> t.getTransactionDate() != null)
                                .collect(Collectors.groupingBy(
                                                t -> String.format("%d-%02d", t.getTransactionDate().getYear(),
                                                                t.getTransactionDate().getMonthValue()),
                                                Collectors.groupingBy(
                                                                TransactionDTO::getTransactionType,
                                                                Collectors.groupingBy(
                                                                                t -> t.getIsOut() ? "out" : "in",
                                                                                Collectors.reducing(BigDecimal.ZERO,
                                                                                                TransactionDTO::getAmount,
                                                                                                BigDecimal::add)))));

                return ResponseEntity.ok(monthlyTransactionTypeSummary);
        }

        @PostMapping("/add")
        public ResponseEntity<TransactionDTO> addTransaction(@AuthenticationPrincipal User currentUser,
                        @RequestBody TransactionDTO transactionDTO) {

                TransactionDTO newTransaction = transactionService.addTransaction(currentUser, transactionDTO);

                return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<UpdateTransactionDTO> updateTransaction(@AuthenticationPrincipal User currentUser,
                        @PathVariable Integer id,
                        @RequestBody UpdateTransactionDTO transactionDTO) {
                UpdateTransactionDTO transaction = transactionService.updateTransaction(currentUser, id,
                                transactionDTO);

                return new ResponseEntity<>(transaction, HttpStatus.OK);
        }

        @DeleteMapping("/delete/{id}")
        public void deleteTransaction(@AuthenticationPrincipal User currentUser, @PathVariable Integer id) {
                transactionService.deleteTransaction(currentUser, id);
        }
}
