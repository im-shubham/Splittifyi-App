package com.shubham.billsplitterapp.navigationDrawer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.shubham.billsplitterapp.DatabaseHelper;
import com.shubham.billsplitterapp.MainActivity;
import com.shubham.billsplitterapp.R;

import java.util.ArrayList;
import java.util.List;

public class TripDetail extends AppCompatActivity {

    DatabaseHelper myDb;
    Cursor cursor;
    TextView tripName,totalExpense;
    ListView memberList;
    Button endTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        myDb = new DatabaseHelper(this);
        cursor = myDb.getAllData();
        endTrip = findViewById(R.id.finish_trip);
        tripName = findViewById(R.id.trip_getName);
        totalExpense = findViewById(R.id.totalExpense);
        cursor.moveToFirst();
        String trip = cursor.getString(2);
        tripName.setText(trip);
        List<String> lst = new ArrayList<String>();
        if(cursor.moveToFirst()){
            do{
                String member = cursor.getString(1);
                String id = cursor.getString(0);
                lst.add(id+". "+member);
            }while (cursor.moveToNext());
        }

        cursor = myDb.getAllExpense();
        int sum=0;
        if(cursor.moveToFirst()){
            do{
                Integer value = Integer.parseInt(cursor.getString(2));
                sum+=value;
            }while (cursor.moveToNext());
        }
        totalExpense.setText("Rs. "+sum);
        endTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_Builder = new AlertDialog.Builder(TripDetail.this);
                a_Builder.setMessage("Are you sure you want to finish trip and close the app ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDropped = myDb.dropDatabase();
                        if(isDropped){
                            TastyToast.makeText(TripDetail.this,"Trip Ended Successfully",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT",true);
                            startActivity(intent);
                            finish();
                        }else{
                            TastyToast.makeText(TripDetail.this,"Error in Ending Trip",TastyToast.LENGTH_LONG,TastyToast.ERROR);
                            dialog.cancel();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = a_Builder.create();
                alertDialog.setTitle("Alert !!!");
                alertDialog.show();
            }
        });
    }
}
