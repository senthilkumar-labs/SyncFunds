package com.finance.syncfunds.databases;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Loan {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String type; // "lend" or "borrow"
    public double amount;
    public String debtorName, creditorName;
    public boolean isActive;
}
