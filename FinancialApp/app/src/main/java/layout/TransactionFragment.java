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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ludwig.financialapp.R;

import java.util.ArrayList;
import java.util.List;

import Models.Transaction;
import TransactionDB.DatabaseHelper;

import static com.example.ludwig.financialapp.R.id.expenceSpinner;
import static com.example.ludwig.financialapp.R.id.incomeSpinner;


public class TransactionFragment extends android.support.v4.app.Fragment {
    TextView welcomeText;
    ListView transactionList;
    List<Transaction> transactions;
    List<String> transactionText;
    Transaction[] transactionArray;
    DatabaseHelper dbHelper;
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
        transactionList = (ListView) view.findViewById(R.id.TransactionList);
        transactions = dbHelper.getAllTransactions();
        transactionText = new ArrayList<String>();
        transactionArray = new Transaction[transactions.size()];
        populateTransactionArray();
        populateTransactionList();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        welcomeText.setText("Welcome " +  preferences.getString("firstName", "") + " " + preferences.getString("lastName", "") + "!");
    }

    private void populateTransactionList(){
        if (transactions.size() != 0){
            for (int i = 0; i < transactions.size(); i++){
                //String s = new String(transactions.get(i).getDate());
                transactionText.add(transactionArray[i].getDate());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
            adapter.addAll(transactionText);
            transactionList.setAdapter(adapter);
        }
    }

    private void populateTransactionArray(){
        for (int i = 0; i < transactions.size(); i++){
            transactionArray[i] = transactions.get(i);
        }
    }
}
