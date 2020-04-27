package com.example.valentina.talkwithme;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.valentina.talkwithme.utilities.MyQuestion;

public class SurveyAdapter extends BaseAdapter {
    private final Context mContext;
    private final MyQuestion[] questions;

    public SurveyAdapter(Context context, MyQuestion[] questions){
        this.mContext =  context;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.length;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyQuestion question = questions[position];
        if(convertView == null){
            final LayoutInflater mLayoutInflarter = LayoutInflater.from(mContext);
            convertView = mLayoutInflarter.inflate(R.layout.survey_single_grid, null);
        }
        final TextView mQuestionTextView = convertView.findViewById(R.id.QuestionTextView);
        mQuestionTextView.setText(question.getText());

        final TextView mQuestionIdView = convertView.findViewById(R.id.QuestionIdTextView);
        mQuestionIdView.setText(question.getId());

        return convertView;
    }
}
