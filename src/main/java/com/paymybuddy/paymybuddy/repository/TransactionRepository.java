package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.Transaction;
import com.paymybuddy.paymybuddy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Integer> {

    @Query(value = "SELECT * FROM transaction WHERE sender_user_id = :user or  receiving_user_id = :user ORDER BY transaction_date DESC, transaction_id DESC" , nativeQuery = true)
    public Iterable<Transaction> findTransactionByUser(
            @Param("user")int user
    );

    public Iterable<Transaction> findAll ();
}
