package model;

import model.People.Account;

import java.util.ArrayList;

public class Wallet {
    static ArrayList<Wallet> wallets = new ArrayList<>();
    static double minRemain;
    double balance;
    Account account;

    public Wallet(double balance, Account account) {
        this.balance = balance;
        this.account = account;
    }

    public void deposit (double amount){
        balance+=amount;
    }

    public void withdraw(double amount){
        balance-=amount;
    }

    public boolean canPay(double amount){
        return balance-amount>=minRemain;
    }
}
