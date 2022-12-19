package com.paymybuddy.paymybuddy.model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@DynamicUpdate
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiving_user_id")
    private User receivingUser;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "transaction_comment")
    private String comment;

    @Column(name = "transaction_amount")
    private float amount;

    public Transaction() {

    }

    public Transaction(User senderUser, User receivingUser, LocalDate transactionDate, String comment, float amount) {
        this.senderUser = senderUser;
        this.receivingUser = receivingUser;
        this.transactionDate = transactionDate;
        this.comment = comment;
        this.amount = amount;
    }

    public void setSenderUser(User senderUser) {
        this.senderUser = senderUser;
    }

    public void setReceivingUser(User receivingUser) {
        this.receivingUser = receivingUser;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
