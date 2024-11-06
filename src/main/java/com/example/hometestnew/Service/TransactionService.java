package com.example.hometestnew.Service;

import com.example.hometestnew.models.Transaction;
import com.example.hometestnew.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Page<Transaction> getTransactionsByUserId(Long userId, int offset, int limit) {
        return transactionRepository.findByUserId(userId, PageRequest.of(offset, limit));
    }
}