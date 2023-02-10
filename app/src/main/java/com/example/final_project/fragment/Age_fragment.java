package com.example.final_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.final_project.MainActivity;
import com.example.final_project.R;


public class Age_fragment extends Fragment {

    private View mView;
    private MainActivity mainActivity;
    private Button btn13, btn36,btn67;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_age,container,false);
        mainActivity = (MainActivity) getActivity();
        initUi();
        initListener();
        return mView;
    }

    private void initUi(){
        btn13 = mView.findViewById(R.id.btn_1_3);
        btn36 = mView.findViewById(R.id.btn_3_6);
        btn67 = mView.findViewById(R.id.btn_6_7);
    }

    private void initListener(){
        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new One_Three());

            }
        });
        btn36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new Three_Six());

            }
        });
        btn67.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new Six_Seven());

            }
        });
    }
}
