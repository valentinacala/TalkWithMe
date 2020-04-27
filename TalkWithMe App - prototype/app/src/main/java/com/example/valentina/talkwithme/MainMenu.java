package com.example.valentina.talkwithme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.valentina.talkwithme.utilities.MySubject;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        //overall relative layout
        final RelativeLayout mSubjectSelectionGrid = findViewById(R.id.SubjectSelectionGridRelativeLayout);
        mSubjectSelectionGrid.setVisibility(View.INVISIBLE);
        final RelativeLayout mOverallMainMenuForSelectedSubjectRelativeLayout = findViewById(R.id.OverallMainMenuForSelectedSubjectRelativeLayout);
        mOverallMainMenuForSelectedSubjectRelativeLayout.setVisibility(View.INVISIBLE);
        RelativeLayout mnoSubjectAvailableRelativeLayout = findViewById(R.id.noSubjectAvailableRelativeLayout);
        mnoSubjectAvailableRelativeLayout.setVisibility(View.INVISIBLE);

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

        //set the appropriate layout
        if (subject_name.length() == 0) {
            //no subjects related to the user
            mnoSubjectAvailableRelativeLayout.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear));
            mnoSubjectAvailableRelativeLayout.setVisibility(View.VISIBLE);
        } else if (subject_name.length() == 1) {
            //there is one subject only related to the user

            //layout modification
            RelativeLayout mRelativeLayoutSelectedChild = findViewById(R.id.RelativeLayoutSelectedChild);
            mRelativeLayoutSelectedChild.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear));
            ScrollView mActionScrollView = findViewById(R.id.ActionScrollView);
            mActionScrollView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear2000));
            mOverallMainMenuForSelectedSubjectRelativeLayout.setVisibility(View.VISIBLE);

            TextView mTextViewSelectedUser = findViewById(R.id.TextViewSelectedUser);
            mTextViewSelectedUser.setVisibility(View.INVISIBLE);
            Button mChangeUserButton = findViewById(R.id.ChangeUserButton);
            mChangeUserButton.setVisibility(View.INVISIBLE);
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

        }else {
            //there are many subjects related to the user
            mSubjectSelectionGrid.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear));
            mSubjectSelectionGrid.setVisibility(View.VISIBLE);


            GridView mgridview = findViewById(R.id.GridViewSubjectSelection);
            final MySubject[] subjects_autogenerated = createSubjects(subject_name,subject_id);
            final SubjectAdapter subjectsAdapter = new SubjectAdapter(this,subjects_autogenerated);
            mgridview.setAdapter(subjectsAdapter);

            //set click event on grid elements
            mgridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MySubject subject = subjects_autogenerated[position];
                    String selected_subject_id = subject.getId();
                    subjectsAdapter.notifyDataSetChanged();

                    //save selected subject
                    //file name: TemporalFile
                    SharedPreferences.Editor editor;
                    SharedPreferences pref;
                    pref = getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE );
                    editor = pref.edit();
                    editor.putString("selected_subject_name",subject.getName());
                    editor.putString("selected_subject_id", selected_subject_id);
                    editor.apply();
                    mSubjectSelectionGrid.setVisibility(View.INVISIBLE);

                    RelativeLayout mRelativeLayoutSelectedChild = findViewById(R.id.RelativeLayoutSelectedChild);
                    mRelativeLayoutSelectedChild.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear));
                    ScrollView mActionScrollView = findViewById(R.id.ActionScrollView);
                    mActionScrollView.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.appear2000));
                    mOverallMainMenuForSelectedSubjectRelativeLayout.setVisibility(View.VISIBLE);

                    ImageView mSubjectIconImage = findViewById(R.id.SelectedchildImage);
                    mSubjectIconImage.setImageResource(subject.getImageResource());

                    TextView mSubjectName = findViewById(R.id.SelectedSubjectName);
                    mSubjectName.setText(subject.getName());
                }
            });
        }

        Button mChangeUserButton = findViewById(R.id.ChangeUserButton);
        mChangeUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mOverallMainMenuForSelectedSubjectRelativeLayout.setVisibility(View.INVISIBLE);
                mSubjectSelectionGrid.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.appear2000));
                mSubjectSelectionGrid.setVisibility(View.VISIBLE);
            }
        });

        RelativeLayout mSurveyBox =findViewById(R.id.SurveyBox);
        mSurveyBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //opening of a new activity related to the survey
                Intent OpenSurvey = new Intent(MainMenu.this, SurveyActivity.class);
                startActivity(OpenSurvey);
            }
        });

        RelativeLayout mContactBox = findViewById(R.id.ContactBox);
        mContactBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //opening of a new activity related to the survey
                Intent OpenContact = new Intent(MainMenu.this, ContactActivity.class);
                startActivity(OpenContact);
            }
        });

    }

    /*************************************************************************************/

    private MySubject[] createSubjects(JSONArray subject_name,JSONArray subject_id){
        MySubject[] subject_fromFor = new  MySubject[subject_name.length()];
        for (int i=0; i<subject_name.length();++i) {
            try {
                String subjectName = subject_name.getString(i);
                String subjectID = subject_id.getString(i);
                int ImageResource = R.mipmap.child1_round;
                subject_fromFor[i] = new MySubject(subjectName, subjectID, ImageResource);
            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + subject_name + "\"");
            }
        }
        return   subject_fromFor;
    }


    /*************************************************************/
    public void ExtractSubjectNameFromSharedPReferences (){
        //extraction of subject_id associated to the
        SharedPreferences pref;
        pref = getApplicationContext().getSharedPreferences("userData", Context.MODE_PRIVATE );
        String subject_id_name = pref.getString("DBrelated_subject_id_name","none");

        if (!"none".equals(subject_id_name)){
            //evaluate if there are subjects
            JSONArray subject_id = new JSONArray();
            JSONArray subject_name = new JSONArray();
            try{
                JSONArray jsonsubject_id_name = new JSONArray(subject_id_name);
                for (int i=0; i<jsonsubject_id_name.length();++i){
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

    }

    /************************************************************/
    // Add the menu to the menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our visualizer_menu layout to this menu */
        inflater.inflate(R.menu.menu_settingleanguage, menu);
        /* Return true so that the visualizer_menu is displayed in the Toolbar */
        return true;
    }

    //When the "Settings" menu item is pressed, open SettingsActivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //check the id id of the option menu element
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

