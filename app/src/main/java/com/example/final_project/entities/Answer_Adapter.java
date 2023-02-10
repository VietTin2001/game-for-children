package com.example.final_project.entities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.final_project.R;

import java.util.List;

public class Answer_Adapter extends BaseAdapter {

    private List<Answer> answerList;
    private Fragment context;
    private int layout;

    public Answer_Adapter(List<Answer> answerList,int layout,Fragment context) {
        this.answerList = answerList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return answerList.size();
    }

    @Override
    public Object getItem(int i) {
        return answerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class Answer_View{
        public ImageView iView;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Answer_View answer_view = null;

        if (answer_view == null ){
            LayoutInflater inflater = (LayoutInflater) context.getLayoutInflater();
            view = inflater.inflate(layout, null);
            answer_view = new Answer_View();
            answer_view.iView = view.findViewById(R.id.img_answer);

            view.setTag(answer_view);
        }else{
            answer_view = (Answer_View) view.getTag();
        }

        answer_view.iView.setImageResource(answerList.get(i).Id);
        return view;
    }
}
