package com.helderzack.expenses.controller;

import com.helderzack.expenses.dto.TransactionDTO;
import com.helderzack.expenses.model.Transaction;
import com.helderzack.expenses.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Object> getTransactions() {
        List<Transaction> transactions = transactionService.getTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addTransaction(@RequestBody TransactionDTO transaction) {
        Transaction transactionEntity = new Transaction();
        mapper.map(transaction, transactionEntity);
        transactionService.saveTransaction(transactionEntity);
        return new ResponseEntity<>("New Transaction saved", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Integer id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>("Transaction deleted", HttpStatus.OK);
    }
}
