package com.example.final_project.entities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.final_project.R;

import java.util.List;

public class Question_Adapter extends BaseAdapter {
    private List<Question> questionList;
    private Fragment context;
    private int layout;

    public Question_Adapter(List<Question> questionList, Fragment context, int layout) {
        this.questionList = questionList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class Question_View{
        public ImageView iView;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Question_View question_view = null;
        if (question_view == null){
            LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();
            view = inflater.inflate(layout, null);
            question_view  = new Question_View();
            question_view.iView = view.findViewById(R.id.img_question);

            view.setTag(question_view);
        }else{
            question_view = (Question_View) view.getTag();
        }
        question_view.iView.setImageResource(questionList.get(i).getID());
        return view;
    }
}
