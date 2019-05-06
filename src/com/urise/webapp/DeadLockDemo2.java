package com.urise.webapp;

class Account {
    private int balance;

    protected Account(int balance) {
        this.balance = balance;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }
}

public class DeadLockDemo2 {
    public static void main(String[] args) {
        final Account a = new Account(1000);
        final Account b = new Account(2000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                transfer(a, b, 500);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                transfer(b, a, 300);
            }
        }).start();

    }

    private static void transfer(Account acc1, Account acc2, int amount) {
        if (acc1.getBalance() < amount) {
            throw new IllegalArgumentException();
        }
        synchronized (acc1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (acc2) {
                acc1.withdraw(amount);
                acc2.deposit(amount);
            }
        }
    }
}
