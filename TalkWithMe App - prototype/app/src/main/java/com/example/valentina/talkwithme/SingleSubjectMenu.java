package com.example.valentina.talkwithme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SingleSubjectMenu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_subject_menu);

        /**
        TextView mTextViewSelectedUser = findViewById(R.id.TextViewSelectedUser);
        mTextViewSelectedUser.setVisibility(View.INVISIBLE);
        Button mChangeUserButton = findViewById(R.id.ChangeUserButton);
        mChangeUserButton.setVisibility(View.INVISIBLE);
         */

        //extraction of all the subject_ids associated to the
        SharedPreferences pref;
        pref = getApplicationContext().getSharedPreferences("userData", Context.MODE_PRIVATE);
        String subject_id_name = pref.getString("DBrelated_subject_id_name", "none");

        //evaluate if there are subjects
        JSONArray subject_id = new JSONArray();
        JSONArray subject_name = new JSONArray();
        if (!"none".equals(subject_id_name)) {
            try {
                JSONArray jsonsubject_id_name = new JSONArray(subject_id_name);

                String numberOfSubject = String.valueOf(jsonsubject_id_name.length());

                SharedPreferences.Editor editor;
                SharedPreferences pref3;
                pref3 = getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE );
                editor = pref3.edit();
                editor.putString("selected_subject_number",numberOfSubject);

                for (int i = 0; i < jsonsubject_id_name.length(); ++i) {
                    JSONObject ijsonobject = jsonsubject_id_name.getJSONObject(i);
                    String ijsonobject_name = ijsonobject.getString("subject_name");
                    String ijsonobject_id = ijsonobject.getString("subject_id");

                    subject_name.put(ijsonobject_name);
                    subject_id.put(ijsonobject_id);
                }
            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + subject_id_name + "\"");
            }
        }

        TextView mSelectedSubjectName = findViewById(R.id.SelectedSubjectName);
        try {
            String selectedSubjectName = subject_name.getString(0);
            mSelectedSubjectName.setText(selectedSubjectName);

            //save in sharedpreference the data of the selected subject
            SharedPreferences.Editor editor;
            SharedPreferences pref2;
            pref2 = getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE );
            editor = pref2.edit();
            editor.putString("selected_subject_name", selectedSubjectName);
            editor.putString("selected_subject_id",subject_id.getString(0));
            editor.apply();

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + subject_id_name + "\"");
        }



    }
}
