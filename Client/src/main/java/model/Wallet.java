package model;

import control.TokenGenerator;

import java.util.ArrayList;

public class Wallet {
    static ArrayList<Wallet> wallets = new ArrayList<>();
    static double minRemain = 0;
    static int interest;
    private static Wallet shopWallet;
    double balance;
    String id;

    // mishe wallet ba balance kam tar az minimum sakht.
    public Wallet(double balance) {
        this.balance = balance;
        this.id = TokenGenerator.generateWalletid();
        wallets.add(this);
    }

    public static void pay(Wallet customer, Wallet seller, double amount) throws Exception {
        if (!customer.canPay(amount)) throw new Exception("can't pay this amount");
        customer.withdraw(amount);
        seller.deposit(amount * (100 - interest) / 100);
        shopWallet.deposit(amount / 100 * interest);
    }

    public static void setMinRemain(double newMinRemain) {
        minRemain = newMinRemain;
    }

    public static void setInterest(int interest) {
        Wallet.interest = interest;
    }

    public static void setShopWallet(Wallet shopWallet) {
        Wallet.shopWallet = shopWallet;
    }

    private void deposit(double amount) {
        balance += amount;
    }

    private void withdraw(double amount) {
        balance -= amount;
    }

    public boolean canPay(double amount) {
        return balance - amount >= minRemain;
    }
}
