package com.example.final_project.fragment;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.final_project.MainActivity;
import com.example.final_project.R;
import com.example.final_project.entities.Answer;
import com.example.final_project.entities.Answer_Adapter;

import java.util.ArrayList;
import java.util.List;

public class Three_Six_2 extends Fragment {

    private GridView gvAnswer;
    private View mView;
    private Answer_Adapter answer_adapter;
    private MainActivity mainActivity;
    private MediaPlayer mp;
    private Button btnBack;
    private ImageView btnPlayVoice;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_three_six_2, container, false);
        mainActivity = (MainActivity) getActivity();
        initUi();
        getQuestionList();
        initListener();
        return mView;
    }
    private void initUi() {

        gvAnswer = mView.findViewById(R.id.gv_answer);
        btnBack = mView.findViewById(R.id.btn_back);
        btnPlayVoice = mView.findViewById(R.id.btn_play_voice);


    }

    private List<Answer> getQuestionList(){
        List<Answer> list1 = new ArrayList<>();
        list1.add(new Answer(R.drawable.i));
        list1.add(new Answer(R.drawable.c));
        list1.add(new Answer(R.drawable.e1));
        list1.add(new Answer(R.drawable.e));
        list1.add(new Answer(R.drawable.h));
        list1.add(new Answer(R.drawable.d1));
        answer_adapter = new Answer_Adapter(list1, R.layout.answer_item, this);
        gvAnswer.setAdapter(answer_adapter);
        return list1;
    }
    private void initListener(){
        btnPlayVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp = MediaPlayer.create(mainActivity,R.raw.e1);
                mp.start();
            }
        });
        gvAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    gameOver();

                }

                if (position == 1) {
                    gameOver();
                }

                if (position == 2) {
                    mp = MediaPlayer.create(mainActivity,R.raw.chinhxac);
                    mp.start();
                    Congratulations();
                }

                if (position == 3) {
                    gameOver();
                }
                if (position == 4) {
                    gameOver();
                }

                if (position == 5) {
                    gameOver();
                }
            }

        });
    }
    private void Congratulations() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.congratulations);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mainActivity.replaceFragment(new Three_Six_3());


            }
        });
        builder.show();
    }


    private void gameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.game_over);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
