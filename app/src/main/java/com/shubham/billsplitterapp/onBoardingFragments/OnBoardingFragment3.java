package com.shubham.billsplitterapp.onBoardingFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shubham.billsplitterapp.LoginActivity;
import com.shubham.billsplitterapp.MainActivity;
import com.shubham.billsplitterapp.R;

public class OnBoardingFragment3 extends Fragment {

    FloatingActionButton floatingActionButton;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding3,container,false);

        floatingActionButton = root.findViewById(R.id.next_btn);
        textView = root.findViewById(R.id.textView9);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
