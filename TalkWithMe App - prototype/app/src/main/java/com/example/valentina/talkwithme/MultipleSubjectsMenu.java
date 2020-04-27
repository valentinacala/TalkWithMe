package com.example.valentina.talkwithme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.valentina.talkwithme.utilities.MySubject;

import org.json.JSONArray;
import org.json.JSONObject;

public class MultipleSubjectsMenu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_subjects_menu);

        //extraction of all the subject_ids associated to the
        SharedPreferences pref;
        pref = getApplicationContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        String subject_id_name = pref.getString("DBrelated_subject_id_name", "none");

        SharedPreferences pref2;
        pref2=getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE);
        String selected_subject_name = pref2.getString("selected_subject_name","none");
        String selected_subject_id = pref2.getString("selected_subject_id","none");
        Integer selected_subject_image = pref2.getInt("selected_subject_image",0);


        ImageView mSubjectIconImage = findViewById(R.id.SelectedchildImage);
        mSubjectIconImage.setImageResource(selected_subject_image);

        TextView mSubjectName = findViewById(R.id.SelectedSubjectName);
        mSubjectName.setText(selected_subject_name);

        Button mChangeUserButton = findViewById(R.id.ChangeUserButton);
        mChangeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent OpenSelection = new Intent(MultipleSubjectsMenu.this, SubjectSelection.class);
                startActivity(OpenSelection);
            }
        });

        RelativeLayout mSurveyBox = findViewById(R.id.SurveyBox);
        mSurveyBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opening of a new activity related to the survey
                Intent OpenSurvey = new Intent(MultipleSubjectsMenu.this, SurveyActivity.class);
                startActivity(OpenSurvey);
            }
        });

        RelativeLayout mContactBox = findViewById(R.id.ContactBox);
        mContactBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opening of a new activity related to the survey
                Intent OpenContact = new Intent(MultipleSubjectsMenu.this, ContactActivity.class);
                startActivity(OpenContact);
            }
        });


    }

    /*************************************************************************************/

    private MySubject[] createSubjects(JSONArray subject_name, JSONArray subject_id) {
        MySubject[] subject_fromFor = new MySubject[subject_name.length()];
        for (int i = 0; i < subject_name.length(); ++i) {
            try {
                String subjectName = subject_name.getString(i);
                String subjectID = subject_id.getString(i);
                int ImageResource = R.mipmap.child1_round;
                subject_fromFor[i] = new MySubject(subjectName, subjectID, ImageResource);
            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + subject_name + "\"");
            }
        }
        return subject_fromFor;
    }


}
