package com.finance.syncfunds.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LoanDao {
    @Query("SELECT * FROM loan")
    List<Loan> getAllLoans();

    @Insert
    void insert(Loan loan);
}
