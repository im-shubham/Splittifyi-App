package com.shubham.billsplitterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shubham.billsplitterapp.navigationDrawer.AddMembers;

public class GroupCreation extends AppCompatActivity {

    String TripName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_creation);
        TripName = getIntent().getStringExtra("TripName");

        Button sizeSelectionBtn = findViewById(R.id.add_grpSize_btn);
        sizeSelectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView grps = findViewById(R.id.groupSize_editText);
                TextView warningText = findViewById(R.id.warningText_groupCreation);
                try{
                    Integer GrpSize = Integer.parseInt(grps.getText().toString());
                    if(GrpSize<=1){
                        warningText.setText("Please enter a valid group size");
                    }else{
                        warningText.setText("");
                        Intent i = new Intent(GroupCreation.this, AddMembers.class);
                        i.putExtra("TripName",TripName);
                        i.putExtra("GroupSize",GrpSize.toString());
                        startActivity(i);
                    }
                }catch (Exception e){
                    warningText.setText("Please enter a valid group size");
                }

            }
        });
    }
}
