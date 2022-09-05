package com.shubham.billsplitterapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdsmdg.tastytoast.TastyToast;

public class LoginTabFragment extends Fragment {

    EditText email,password;
    TextView forgetPassword;
    Button login;
    float v=0;
    ProgressDialog progressDialog;

    String str_email,str_pass,str_confirmPass , str_mobile;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tabfragment,container,false);


        email = root.findViewById(R.id.tripeMail);
        password = root.findViewById(R.id.password);
        login = root.findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();
        forgetPassword = root.findViewById(R.id.forget_password);
        progressDialog = new ProgressDialog(getActivity());



        email.setTranslationX(800);
        password.setTranslationX(800);
        login.setTranslationX(800);
        forgetPassword.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        login.setAlpha(v);
        forgetPassword.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_email = email.getText().toString().trim();
                str_pass = password.getText().toString().trim();

                if(TextUtils.isEmpty(str_email)){
                    email.setError("Please Fill Field..");
                    email.requestFocus();
                    return;
                }if(TextUtils.isEmpty(str_pass)){
                    password.setError("Please Fill Field..");
                    password.requestFocus();
                    return;
                }
                progressDialog.setMessage("Trying to Login..");
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(str_email,str_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            TastyToast.makeText(getActivity(), "User Logged in Successfully", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                            Intent intent = new Intent(getActivity(),TripSelection.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else{

                            progressDialog.dismiss();
                            Log.e("akshara","createUserWithEmail:failure", task.getException());
                            TastyToast.makeText(getActivity(), "Error Occurred.."+task.getException().getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                        }

                    }
                });




            }
        });


        return root;
    }


}
