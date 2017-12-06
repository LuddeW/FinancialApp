package layout;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ludwig.financialapp.R;

import java.util.ArrayList;
import java.util.List;

import Models.Transaction;
import TransactionDB.DatabaseHelper;

import static com.example.ludwig.financialapp.R.id.add;
import static com.example.ludwig.financialapp.R.id.expenceSpinner;
import static com.example.ludwig.financialapp.R.id.incomeSpinner;


public class TransactionFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    TextView welcomeText;
    TextView amountText;
    EditText filterText;
    ListView transactionList;
    List<Transaction> transactions;
    List<String> transactionText;
    Transaction[] transactionArray;
    DatabaseHelper dbHelper;
    Button submitButton;
    Button incomeButton;
    Button expenceButton;
    int balance;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        initViews(view);
        return view;
    }

    private void initViews(View view){
        dbHelper = new DatabaseHelper(getContext());
        welcomeText = (TextView) view.findViewById(R.id.WelcomeText);
        amountText = (TextView) view.findViewById(R.id.AmountText);
        transactionList = (ListView) view.findViewById(R.id.TransactionList);
        submitButton = (Button) view.findViewById(R.id.DateSubmit);
        incomeButton = (Button) view.findViewById(R.id.Income);
        expenceButton = (Button) view.findViewById(R.id.Expence);
        filterText = (EditText) view.findViewById(R.id.Date);
        transactions = dbHelper.getAllTransactions();
        transactionText = new ArrayList<String>();
        transactionArray = new Transaction[transactions.size()];
        submitButton.setOnClickListener(this);
        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterIncome(false);
            }
        });
        expenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterIncome(true);
            }
        });
        populateTransactionArray(transactions);
        populateTransactionList(transactions);
        setAmountText();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        welcomeText.setText("Welcome " +  preferences.getString("firstName", "") + " " + preferences.getString("lastName", "") + "!");
    }

    private void populateTransactionList(List<Transaction> transactions){
        if (transactions.size() != 0){
            for (int i = 0; i < transactions.size(); i++){
                //String s = new String(transactions.get(i).getDate());
                transactionText.add(transactionArray[i].getDate().toString() + "\t \t" + "\t \t" + "\t \t" + "\t \t" + transactionArray[i].getAmount().toString()
                     + "\t \t" + "\t \t" + "\t \t" + "\t \t" + transactionArray[i].getType());
                balance += transactionArray[i].getAmount();
            }
            //transactionList.setAdapter(null);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
            adapter.addAll(transactionText);
            transactionList.setAdapter(adapter);
            transactionText.clear();
        }
        else {
            balance = 0;
        }
    }

    private void populateTransactionArray(List<Transaction> transactions){
        for (int i = 0; i < transactions.size(); i++){
            transactionArray[i] = transactions.get(i);
        }
    }

    private void setAmountText(){
        amountText.setText("Your balance is: " + balance + "kr");
    }

    private void filterDate(int date){
        if (transactions.size() != 0){
            List<Transaction> filterTransactions = new ArrayList<Transaction>();
            filterTransactions = dbHelper.filterDate(date);
            populateTransactionArray(filterTransactions);
            populateTransactionList(filterTransactions);
        }
    }

    private void filterIncome(boolean positive){
        if (positive == true){
            List<Transaction> filterTransactions = new ArrayList<Transaction>();
            filterTransactions = dbHelper.filterPositive(positive);
            populateTransactionArray(filterTransactions);
            populateTransactionList(filterTransactions);
        }
        else {
            List<Transaction> filterTransactions = new ArrayList<Transaction>();
            filterTransactions = dbHelper.filterPositive(positive);
            populateTransactionArray(filterTransactions);
            populateTransactionList(filterTransactions);
        }
    }

    @Override
    public void onClick(View v) {
        if (filterText.getText().toString() != "1") {
            filterDate(Integer.parseInt(filterText.getText().toString()));
        }
        else{
            populateTransactionList(transactions);
        }
    }
}
