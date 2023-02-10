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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.final_project.MainActivity;
import com.example.final_project.R;
import com.example.final_project.entities.Answer;
import com.example.final_project.entities.Answer_Adapter;
import com.example.final_project.entities.Question;
import com.example.final_project.entities.Question_Adapter;

import java.util.ArrayList;
import java.util.List;

public class Six_Seven_2 extends Fragment {

    private GridView gvAnswer;
    private View mView;
    private Answer_Adapter answer_adapter;
    private TextView tvNumQuestion;
    private ListView lvQuestion;
    private Button btnBack;
    private Question_Adapter question_adapter;
    private MainActivity mainActivity;
    private MediaPlayer mp;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_six_seven_2, container, false);
        mainActivity = (MainActivity) getActivity();
        initUi();
        getQuestionList();
        initListener();
        return mView;
    }


    private void initUi() {

        gvAnswer = mView.findViewById(R.id.gv_answer);

        btnBack = mView.findViewById(R.id.btn_back);

        lvQuestion = mView.findViewById(R.id.lv_question);


    }
    private List<Question> getQuestionList(){
        List<Answer> list1 = new ArrayList<>();
        list1.add(new Answer(R.drawable.c_goc));
        list1.add(new Answer(R.drawable.b_goc));
        list1.add(new Answer(R.drawable.e2_goc));
        list1.add(new Answer(R.drawable.g_goc));
        answer_adapter = new Answer_Adapter(list1, R.layout.answer_item, this);
        gvAnswer.setAdapter(answer_adapter);
        List<Question> list = new ArrayList<>();
        list.add(new Question(1, R.drawable.b_cat, list1));
        question_adapter = new Question_Adapter(list, this, R.layout.question_item);
        lvQuestion.setAdapter(question_adapter);

        return list;
    }

    private void initListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new Six_Seven_3());
            }
        });
        gvAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0) {
                    gameOver();
                }else if (position == 1) {
                    mp = MediaPlayer.create(mainActivity,R.raw.chinhxac);
                    mp.start();
                    congratulations();
                }else if (position == 2) {
                    gameOver();
                }else if (position == 3) {
                    gameOver();
                }
            }

        });
    }

    private void congratulations() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.congratulations);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mainActivity.replaceFragment(new Six_Seven_3());
            }
        });
        builder.show();
    }

    private void showAnswerCorrect() {
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

    private void nextQuestion() {

    }
}
