package com.shubham.billsplitterapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdsmdg.tastytoast.TastyToast;

public class SignupTabFragment extends Fragment {
    EditText email, mobile,password,confirm_pass;
    Button signupBtn;
    ProgressDialog progressDialog;

    String str_email,str_pass,str_confirmPass , str_mobile;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);

        email = root.findViewById(R.id.tripeMail);
        mobile = root.findViewById(R.id.mobile);
        password = root.findViewById(R.id.password);
        confirm_pass = root.findViewById(R.id.confirm_password);
        signupBtn = root.findViewById(R.id.signup_btnfrag);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });






        return root;
    }

    private void Validation() {
        str_email = email.getText().toString().trim();
        str_mobile = mobile.getText().toString();
        str_pass = password.getText().toString().trim();
        str_confirmPass = confirm_pass.getText().toString();

        if(str_email.isEmpty()){
            email.setError("Please Fill Field..");
            email.requestFocus();
            return;
        }
        if(str_mobile.isEmpty()){
            mobile.setError("Please Fill Details..");
            mobile.requestFocus();
            return;
        }
        if(str_pass.isEmpty()){
            password.setError("Please Fill Details..");
            password.requestFocus();
            return;
        }
        if(str_confirmPass.isEmpty()){
            confirm_pass.setError("Please Fill Details..");
            confirm_pass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()){
            email.setError("Invalid Email ID...");
            email.requestFocus();
            return;
        }
        if(!str_pass.matches(str_confirmPass)){
            confirm_pass.setError("Password Not Matched");
            confirm_pass.requestFocus();
            return;
        }if(str_pass.length()<6){
            password.setError("Enter Minimum 6 digits");
            password.requestFocus();
            return;
        }

        createAccount();
    }

    private void createAccount() {
        progressDialog.setMessage("Creating Account..");
        progressDialog.show();

        sendDataToFirebase();
    }

    private void sendDataToFirebase() {

        mAuth.createUserWithEmailAndPassword(str_email,str_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    TastyToast.makeText(getActivity(), "User Created Successfully", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    Intent intent = new Intent(getActivity(),TripSelection.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{

                    progressDialog.dismiss();
                    Log.d("akshara","createUserWithEmail:failure", task.getException());
                    TastyToast.makeText(getActivity(), "Error Occurred..", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                }
            }
        });

    }


}
