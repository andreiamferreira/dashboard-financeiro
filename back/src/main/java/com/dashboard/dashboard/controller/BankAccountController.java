package com.dashboard.dashboard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.dashboard.Enum.InstitutionEnum;
import com.dashboard.dashboard.dto.BankAccountDTO;
import com.dashboard.dashboard.dto.SumOfAccountsByInstitutionDTO;
import com.dashboard.dashboard.dto.SumOfAccountsByTypeDTO;
import com.dashboard.dashboard.model.User;
import com.dashboard.dashboard.service.BankAccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Accounts", description = "Accounts routes")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/all")
    public ResponseEntity<List<BankAccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(bankAccountService.getAllAccounts());
    }

    @GetMapping("/all")
    public ResponseEntity<List<BankAccountDTO>> getAllAccountsByUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(bankAccountService.getAllAccountsByUser(currentUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> getAccountById(@AuthenticationPrincipal User currentUser, Integer id) {
        return ResponseEntity.ok(bankAccountService.getAccountById(currentUser, id));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<SumOfAccountsByTypeDTO> getAccountsAndTotalBalanceByType(
            @AuthenticationPrincipal User currentUser,
            @PathVariable String type) {
        return ResponseEntity.ok(bankAccountService.getAccountsByType(currentUser, type));
    }

    @GetMapping("/institution/{institution}")
    public ResponseEntity<SumOfAccountsByInstitutionDTO> getAccountsByInstitution(
            @AuthenticationPrincipal User currentUser,
            @PathVariable String institution) {
        return ResponseEntity.ok(bankAccountService.getAccountsByInstitution(currentUser, institution));
    }

    @GetMapping("/institution/mostused")
    public ResponseEntity<List<InstitutionEnum>> getMostUsedInstitution(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(bankAccountService.getMostUsedInstitution(currentUser));
    }

    @PostMapping("/add")
    public ResponseEntity<BankAccountDTO> addBankAcc(@AuthenticationPrincipal User currentUser,
            @RequestBody BankAccountDTO bankAccDTO) {

        BankAccountDTO newBankAcc = bankAccountService.addBankAcc(currentUser, bankAccDTO);

        return new ResponseEntity<>(newBankAcc, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BankAccountDTO> updateBankAcc(
            @AuthenticationPrincipal User currentUser,
            @RequestBody BankAccountDTO bankAccDTO,
            @PathVariable Integer id) {
        BankAccountDTO bankAcc = bankAccountService.updateBankAcc(currentUser, bankAccDTO, id);

        return new ResponseEntity<>(bankAcc, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBankAccount(@AuthenticationPrincipal User currentUser, @PathVariable Integer id) {
        bankAccountService.deleteBankAccount(currentUser, id);
    }

}
