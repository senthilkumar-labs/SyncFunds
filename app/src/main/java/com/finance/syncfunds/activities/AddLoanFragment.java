package com.finance.syncfunds.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.finance.syncfunds.R;
import com.finance.syncfunds.databases.AppDatabase;
import com.finance.syncfunds.databases.Loan;

public class AddLoanFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_loan, container, false);

        EditText amountEdit = view.findViewById(R.id.amount_edit);
        EditText nameEdit = view.findViewById(R.id.name_edit);
        Button lendBtn = view.findViewById(R.id.lend_button);
        Button borrowBtn = view.findViewById(R.id.borrow_button);

        AppDatabase db = Room.databaseBuilder(requireContext(), AppDatabase.class, "fintrack").build();

        View.OnClickListener save = v -> {
            String amountStr = amountEdit.getText().toString();
            String name = nameEdit.getText().toString();
            if (amountStr.isEmpty() || name.isEmpty()) {
                Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            Loan loan = new Loan();
            loan.amount = Double.parseDouble(amountStr);
            loan.isActive = true;
            if (v.getId() == R.id.lend_button) {
                loan.type = "lend";
                loan.debtorName = name;
            } else {
                loan.type = "borrow";
                loan.creditorName = name;
            }
            new Thread(() -> {
                db.loanDao().insert(loan);
                requireActivity().runOnUiThread(() -> requireActivity().getSupportFragmentManager().popBackStack());
            }).start();
        };

        lendBtn.setOnClickListener(save);
        borrowBtn.setOnClickListener(save);
        return view;
    }
}
