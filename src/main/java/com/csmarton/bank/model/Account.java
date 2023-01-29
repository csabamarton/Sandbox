package com.csmarton.bank.model;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Account {
    private String iban;
    private double balance;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Account(String iban, double balance) {
        this.iban = iban;
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public double getBalance() {
        lock.readLock().lock();
        try {
            return balance;
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean withdraw(double amount) {
        lock.writeLock().lock();

        try {
            if(amount > getBalance()) {
                System.out.println(String.format("Not enough credit for this transaction! Iban: %s, balance: %.0f, amount: %.0f", iban, balance, amount));
                return false;
            }

            balance -= amount;

            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean deposit(double amount) {
        lock.writeLock().lock();
        try {
            balance += amount;

            return true;
        } finally {
            lock.writeLock().unlock();
        }

    }
}
