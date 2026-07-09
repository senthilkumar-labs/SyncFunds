package com.finance.syncfunds.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.finance.syncfunds.R;
import com.finance.syncfunds.databases.AppDatabase;
import com.finance.syncfunds.databases.Loan;

import java.util.List;

public class DashboardFragment extends Fragment {
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "fintrack").build();

        new Thread(() -> {
            List<Loan> loans = db.loanDao().getAllLoans();
            requireActivity().runOnUiThread(() -> {
                RecyclerView recyclerView = view.findViewById(R.id.loans_rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new LoanAdapter(loans));
            });
        }).start();

        view.findViewById(R.id.add_loan_button).setOnClickListener(v ->
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AddLoanFragment())
                        .addToBackStack(null)
                        .commit()
        );

        return view;
    }
}
