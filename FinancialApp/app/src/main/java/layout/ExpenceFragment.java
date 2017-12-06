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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ludwig.financialapp.R;

import Models.Transaction;
import TransactionDB.DatabaseHelper;

import static com.example.ludwig.financialapp.R.id.expenceSpinner;

public class ExpenceFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    Spinner mSpinner;
    Button submitButton;
    DatabaseHelper dbHelper;
    EditText expenceAmount;
    EditText expenceDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_expence, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        submitButton = (Button) view.findViewById(R.id.expenceSubmit);
        dbHelper = new DatabaseHelper(getContext());
        mSpinner = (Spinner) view.findViewById(expenceSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.expence_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        expenceAmount = (EditText) view.findViewById(R.id.expenceAmount);
        expenceDate = (EditText) view.findViewById(R.id.expenceDate);

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Transaction t = new Transaction();
        t.setType(mSpinner.getSelectedItem().toString());
        t.setAmount(Integer.parseInt(expenceAmount.getText().toString()) * -1);
        t.setDate(Integer.parseInt(expenceDate.getText().toString()));
        dbHelper.addTransaction(t);
        Toast.makeText(getContext(),"Sent!" , Toast.LENGTH_SHORT).show();
    }
}
