package com.example.final_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.final_project.MainActivity;
import com.example.final_project.R;

public class Home_fragment extends Fragment {


    private View mView;
    private Button btnNewGame;
    private MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_home,container,false);
        mainActivity = (MainActivity) getActivity();
        initUi();
        initListener();
        return mView;
    }

    private void initUi() {
        btnNewGame = mView.findViewById(R.id.btn_new_game);
    }

    private void initListener(){
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new Age_fragment());
            }
        });
    }
}
