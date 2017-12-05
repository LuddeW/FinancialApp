package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.ludwig.financialapp.R;

import Models.Transaction;
import TransactionDB.DatabaseHelper;

import static com.example.ludwig.financialapp.R.id.incomeSpinner;

public class IncomeFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    Spinner mSpinner;
    Button submitButton;
    DatabaseHelper dbHelper;
    EditText incomeAmount;
    EditText incomeDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        submitButton = (Button) view.findViewById(R.id.incomeSubmit);
        dbHelper = new DatabaseHelper(getContext());
        mSpinner = (Spinner) view.findViewById(incomeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.income_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        incomeAmount = (EditText) view.findViewById(R.id.incomeAmount);
        incomeDate = (EditText) view.findViewById(R.id.incomeDate);

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Transaction t = new Transaction();
        t.setType("Income");
        t.setAmount(incomeAmount.getText().toString());
        t.setDate(incomeDate.getText().toString());
        dbHelper.addTransaction(t);
    }
}
