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

public class One_Three extends Fragment {

    private GridView gvAnswer;
    private View mView;
    private Answer_Adapter answer_adapter;
    private MainActivity mainActivity;
    private MediaPlayer mp;
    private Button btnBack;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one_three, container, false);
        mainActivity = (MainActivity) getActivity();
        initUi();
        getQuestionList();
        initListener();
        return mView;
    }


    private void initUi() {

        gvAnswer = mView.findViewById(R.id.gv_answer);
        btnBack = mView.findViewById(R.id.btn_back);


    }
    private List<Answer> getQuestionList(){
        List<Answer> list1 = new ArrayList<>();
        list1.add(new Answer(R.drawable.a));
        list1.add(new Answer(R.drawable.a1));
        list1.add(new Answer(R.drawable.a2));
        list1.add(new Answer(R.drawable.b));
        list1.add(new Answer(R.drawable.c));
        list1.add(new Answer(R.drawable.d));
        list1.add(new Answer(R.drawable.d1));
        list1.add(new Answer(R.drawable.e));
        list1.add(new Answer(R.drawable.e1));
        list1.add(new Answer(R.drawable.g));
        list1.add(new Answer(R.drawable.h));
        list1.add(new Answer(R.drawable.i));
        list1.add(new Answer(R.drawable.k));
        list1.add(new Answer(R.drawable.l));
        list1.add(new Answer(R.drawable.m));
        list1.add(new Answer(R.drawable.n));
        list1.add(new Answer(R.drawable.o));
        list1.add(new Answer(R.drawable.o1));
        list1.add(new Answer(R.drawable.o2));
        list1.add(new Answer(R.drawable.p));
        list1.add(new Answer(R.drawable.q));
        list1.add(new Answer(R.drawable.r));
        list1.add(new Answer(R.drawable.s));
        list1.add(new Answer(R.drawable.t));
        list1.add(new Answer(R.drawable.u));
        list1.add(new Answer(R.drawable.u1));
        list1.add(new Answer(R.drawable.v));
        list1.add(new Answer(R.drawable.x));
        list1.add(new Answer(R.drawable.y));

        answer_adapter = new Answer_Adapter(list1, R.layout.answer_item, this);
        gvAnswer.setAdapter(answer_adapter);

        return list1;
    }

    private void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new Age_fragment());
            }
        });

        gvAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0) {
                    mp = MediaPlayer.create(mainActivity,R.raw.a);
                    mp.start();
                }else if (position == 1) {
                    mp = MediaPlayer.create(mainActivity,R.raw.a1);
                    mp.start();
                }else if (position == 2) {
                    mp = MediaPlayer.create(mainActivity,R.raw.a2);
                    mp.start();
                }else if (position == 3) {
                    mp = MediaPlayer.create(mainActivity,R.raw.b);
                    mp.start();
                }else if (position == 4) {
                    mp = MediaPlayer.create(mainActivity,R.raw.c);
                    mp.start();
                }else if (position == 5) {
                    mp = MediaPlayer.create(mainActivity,R.raw.d);
                    mp.start();
                }else if (position == 6) {
                    mp = MediaPlayer.create(mainActivity,R.raw.d1);
                    mp.start();
                }else if (position == 7) {
                    mp = MediaPlayer.create(mainActivity,R.raw.e);
                    mp.start();
                }else if (position == 8) {
                    mp = MediaPlayer.create(mainActivity,R.raw.e1);
                    mp.start();
                }else if (position == 9) {
                    mp = MediaPlayer.create(mainActivity,R.raw.g);
                    mp.start();
                }else if (position == 10) {
                    mp = MediaPlayer.create(mainActivity,R.raw.h);
                    mp.start();
                }else if (position == 11) {
                    mp = MediaPlayer.create(mainActivity,R.raw.i);
                    mp.start();
                }else  if (position == 12) {
                    mp = MediaPlayer.create(mainActivity,R.raw.k);
                    mp.start();
                }else if (position == 13) {
                    mp = MediaPlayer.create(mainActivity,R.raw.l);
                    mp.start();
                }else if (position == 14) {
                    mp = MediaPlayer.create(mainActivity,R.raw.m);
                    mp.start();
                }else if (position == 15) {
                    mp = MediaPlayer.create(mainActivity,R.raw.n);
                    mp.start();
                }else if (position == 16) {
                    mp = MediaPlayer.create(mainActivity,R.raw.o);
                    mp.start();
                }else if (position == 17) {
                    mp = MediaPlayer.create(mainActivity,R.raw.o1);
                    mp.start();
                }else if (position == 18) {
                    mp = MediaPlayer.create(mainActivity,R.raw.o2);
                    mp.start();
                }else if (position == 19) {
                    mp = MediaPlayer.create(mainActivity,R.raw.p);
                    mp.start();
                }else if (position == 20) {
                    mp = MediaPlayer.create(mainActivity,R.raw.q);
                    mp.start();
                }else if (position == 21) {
                    mp = MediaPlayer.create(mainActivity,R.raw.r);
                    mp.start();
                }else if (position == 22) {
                    mp = MediaPlayer.create(mainActivity,R.raw.s);
                    mp.start();
                }else if (position == 23) {
                    mp = MediaPlayer.create(mainActivity,R.raw.t);
                    mp.start();
                }else if (position == 24) {
                    mp = MediaPlayer.create(mainActivity,R.raw.u);
                    mp.start();
                }else if (position == 25) {
                    mp = MediaPlayer.create(mainActivity,R.raw.u1);
                    mp.start();
                }else if (position == 26) {
                    mp = MediaPlayer.create(mainActivity,R.raw.v);
                    mp.start();
                }else if (position == 27) {
                    mp = MediaPlayer.create(mainActivity,R.raw.x);
                    mp.start();
                }else if (position == 28) {
                    mp = MediaPlayer.create(mainActivity,R.raw.y);
                    mp.start();
                }
            }

        });
    }
}
