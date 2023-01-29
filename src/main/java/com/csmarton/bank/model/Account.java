package com.csmarton.bank.model;

public class Account {
    private String iban;
    private double balance;

    public Account(String iban, double balance) {
        this.iban = iban;
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if(amount > getBalance()) {
            System.out.println(String.format("Not enough credit for this transaction! Iban: %s, balance: %.0f, amount: %.0f", iban, balance, amount));
            return false;
        }

        balance -= amount;

        return true;
    }

    public boolean deposit(double amount) {
        balance += amount;

        return true;
    }
}
