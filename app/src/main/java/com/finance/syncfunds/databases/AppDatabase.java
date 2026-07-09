package com.finance.syncfunds.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Loan.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LoanDao loanDao();
}
