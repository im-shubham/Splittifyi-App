package com.shubham.billsplitterapp.navigationDrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.shubham.billsplitterapp.DatabaseHelper;
import com.shubham.billsplitterapp.R;

import java.util.ArrayList;

public class Add_Expenses extends AppCompatActivity {

    DatabaseHelper myDb;
    Button addExpense;
    Spinner sp;
    TextView expenseType, value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_expenses);
        myDb = new DatabaseHelper(Add_Expenses.this);


        ArrayList<String> nameList = initializeSpinner();
        System.out.println(nameList);


        sp = findViewById(R.id.spinner);
        expenseType = findViewById(R.id.expenseType);
        value = findViewById(R.id.value);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, R.id.txt, nameList);
        sp.setAdapter(adapter);
        addExpense = findViewById(R.id.add_expense_btn);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memSelected = sp.getSelectedItem().toString();
                String ExpenseType = expenseType.getText().toString();
                String Value = value.getText().toString();
                if (memSelected.length() == 0 || ExpenseType.length() == 0 || Value.length() == 0) {
                    TastyToast.makeText(Add_Expenses.this, "Please Fill In Valid Data", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                    return;
                }
                boolean isInserted = myDb.insertDataTb2(ExpenseType, Value, memSelected.charAt(0) + "");
                if (isInserted)
                    TastyToast.makeText(Add_Expenses.this, "Expense Added", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                else
                    TastyToast.makeText(Add_Expenses.this, "Expense Not Added", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        });
    }

    public ArrayList<String> initializeSpinner() {

            ArrayList<String> list = new ArrayList<String>();
            try {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    return list;
                } else {
                    while (res.moveToNext()) {
                        String memId = res.getString(0);
                        String memName = res.getString(1);
                        list.add(memId + " . " + memName);
                    }
                }

            } catch (Exception e) {
                System.out.println("Error " + e);
            }
        return list;
        }
    }



