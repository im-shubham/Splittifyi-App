package com.shubham.billsplitterapp.navigationDrawer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;
import com.shubham.billsplitterapp.DatabaseHelper;
import com.shubham.billsplitterapp.Home;
import com.shubham.billsplitterapp.R;

public class AddMembers extends AppCompatActivity{

    DatabaseHelper myDb;
    String TripName;
    String GroupSize;
    int Size;

    public void AddingMember(int i){
        final Button addMember = findViewById(R.id.add_grpMembers_btn);
        if(i==Size){
            addMember.setText("Submit");
        }
        if(i>Size){
            return;
        }
        final int id = i;
        final TextView memberName = findViewById(R.id.groupMembersName_editText);
        memberName.setHint(id+". Member Name");
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memberName.getText().length()==0) {
                    TextView warningText = findViewById(R.id.warningText_addMembersActivity);
                    warningText.setText("Please enter a valid member name");
                    return;
                }
                boolean isInserted = myDb.insertDataTb1(memberName.getText().toString(),TripName);
                if(isInserted)
                    TastyToast.makeText(AddMembers.this,"Member Added",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                else
                    TastyToast.makeText(AddMembers.this,"Member Not Added",TastyToast.LENGTH_LONG,TastyToast.ERROR);

                System.out.println(memberName.getText().toString()+id);
                if(addMember.getText().equals("Submit")){
                    Intent intent = new Intent(AddMembers.this, Home.class);
                    startActivity(intent);
                }
                memberName.setText("");
                AddingMember(id+1);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);
        myDb = new DatabaseHelper(this);
        TripName = getIntent().getStringExtra("TripName");
        GroupSize = getIntent().getStringExtra("GroupSize");
        Size = Integer.parseInt(GroupSize);
        AddingMember(1);
    }
}
