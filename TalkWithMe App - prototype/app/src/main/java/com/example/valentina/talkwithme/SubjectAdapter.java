package com.example.valentina.talkwithme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.valentina.talkwithme.utilities.MySubject;

public class SubjectAdapter extends BaseAdapter{
    private final Context mContext;
    private final MySubject[] subjects;

    public SubjectAdapter(Context context, MySubject[] subjects){
        this.mContext = context;
        this.subjects = subjects;
    }

    @Override
    public int getCount() {
        return subjects.length;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MySubject subject = subjects[position];
        if(convertView == null ){
            final LayoutInflater mlayoutInflarter = LayoutInflater.from(mContext);
            convertView = mlayoutInflarter.inflate(R.layout.mainmenu_single_grid,null);
        }
        final ImageView imageView = convertView.findViewById(R.id.SubjectIconImage);
        final TextView  nameTextView = convertView.findViewById(R.id.SubjectName);
        imageView.setImageResource(subject.getImageResource());
        nameTextView.setText(subject.getName());

        return convertView;
    }
}