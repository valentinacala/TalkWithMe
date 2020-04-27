package com.example.valentina.talkwithme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.valentina.talkwithme.utilities.MyQuestion;
import com.example.valentina.talkwithme.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        RelativeLayout mNoQuestionsRelativeLayout = findViewById(R.id.NoQuestionsRelativeLayout);
        mNoQuestionsRelativeLayout.setVisibility(View.INVISIBLE);
        RelativeLayout mReadyQuestionsRelativeLayout = findViewById(R.id.ReadyQuestionsRelativeLayout);
        mReadyQuestionsRelativeLayout.setVisibility(View.INVISIBLE);
        RelativeLayout mLoadingQuestionsRelativeLayout = findViewById(R.id.loadingQuestionsRelativeLayout);
        mLoadingQuestionsRelativeLayout.setVisibility(View.VISIBLE);

        //population of the survey
        QuestionRelatedToThisRelationship();

        //when the submit button is pressed, answers are sent to server and the user is redirected to
        //the main page (handler to wisualize a ""thank you for your feedback" before the activity is closed)
        Button mAnswersSubmitButton = findViewById(R.id.AnswersSubmitButton);
        mAnswersSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject DataToPost = new JSONObject();

                GridView mGridViewQuestionAnswers = findViewById(R.id.GridViewQuestionAnswers);
                int numerOfItem = mGridViewQuestionAnswers.getCount();
                for(int i=0; i<numerOfItem;i++){
                    View child = mGridViewQuestionAnswers.getChildAt(i);
                    TextView mQuestionIdTextView = child.findViewById(R.id.QuestionIdTextView);
                    RatingBar mratingBar = child.findViewById(R.id.ratingBar);

                    String single_question_id = mQuestionIdTextView.getText().toString();
                    float single_answer = mratingBar.getRating();
                    String single_answer_string = String.valueOf(single_answer);

                    try{
                        DataToPost.put("question_id",single_question_id);
                        DataToPost.put("answer",single_answer_string);
                        //creation of the URL which can recive the http request to verify the inserted login credentials
                        String AnswerMethod = "SaveAnswers.php";
                        URL myUrl = Utilities.buildURL(AnswerMethod);
                        //creation of a new thred to send answers to the server
                        POSTAnswersQueryTask QueryTask = new POSTAnswersQueryTask();
                        QueryTask.execute(myUrl.toString(), DataToPost.toString());

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                //local storage of the answers of the user
                //file name: TemporalFile
                SharedPreferences.Editor editor;
                SharedPreferences pref;
                pref = getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE );
                editor = pref.edit();
                editor.putString("user_answers", DataToPost.toString());
                editor.putString("survey_done","true");
                editor.apply();

                //go back to the main menu
                Intent OpenMainMenu = new Intent(SurveyActivity.this, MainMenu.class);
                startActivity(OpenMainMenu);
            }
        });


    }

    /***********************************************************************/
    public void QuestionRelatedToThisRelationship(){

        //extraction of user_id and subject_id from UserdData file
        SharedPreferences pref;
        pref = getApplicationContext().getSharedPreferences("userData", Context.MODE_PRIVATE );
        String user_id = pref.getString("DBuser_id","none");
        pref = getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE );
        String subject_id = pref.getString("selected_subject_id","none");

        //prepare to request to server
        //creation of the URL which can recive the http request to verify the inserted login credentials
        String LoginMethod = "GetQuestions.php";
        URL myUrl = Utilities.buildURL(LoginMethod);
        //creation of the JSON object which will be sent by POST request to server
        JSONObject DataToPost = new JSONObject();
        try{
            DataToPost.put("userid", user_id);
            DataToPost.put("subjectid", subject_id);

            //send of the post request to server
            //creation of a new thread to manage the connection request to the server
            SurveyActivity.POSTQuestionQueryTask QueryTask = new SurveyActivity.POSTQuestionQueryTask();
            QueryTask.execute(myUrl.toString(), DataToPost.toString());

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /************************************************************************/
    private class POSTQuestionQueryTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            StringBuilder data = new StringBuilder();
            HttpURLConnection httpUrlConnection = null;
            try{
                httpUrlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpUrlConnection.getOutputStream());
                wr.writeBytes("PostData="+params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data.append(current);
                }
            }catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }
            return data.toString();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String questions = ExtractQuestionsFromQueryResult(result);

            //save questions in shared preferences
            SharedPreferences.Editor editor;
            SharedPreferences pref;
            pref = getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE );
            editor = pref.edit();
            editor.putString("DBquestions",questions);
            editor.apply();

            RelativeLayout mLoadingQuestionsRelativeLayout = findViewById(R.id.loadingQuestionsRelativeLayout);
            mLoadingQuestionsRelativeLayout.setVisibility(View.INVISIBLE);

            if ("failed".equals(questions)){
                //no questions to display!
                RelativeLayout mNoQuestionsRelativeLayout = findViewById(R.id.NoQuestionsRelativeLayout);
                mNoQuestionsRelativeLayout.setVisibility(View.VISIBLE);
            } else {
                //questions to display
                RelativeLayout mReadyQuestionsRelativeLayout = findViewById(R.id.ReadyQuestionsRelativeLayout);
                mReadyQuestionsRelativeLayout.setVisibility(View.VISIBLE);

                //extract questions
                JSONArray question_text = new JSONArray();
                JSONArray question_id = new JSONArray();
                try{
                    JSONArray jsonquestion_text_id = new JSONArray(questions);
                    for (int i=0; i<jsonquestion_text_id.length();++i){
                        JSONObject ijsonobject = jsonquestion_text_id.getJSONObject(i);
                        String ijsonobject_id = ijsonobject.getString("question_id");
                        String ijsonobject_text = ijsonobject.getString("question_text");

                        question_text.put(ijsonobject_text);
                        question_id.put(ijsonobject_id);
                    }
                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + questions + "\"");
                }

                //inflate questions inside the grid
                GridView mGridViewQuestionAnswers = findViewById(R.id.GridViewQuestionAnswers);
                final MyQuestion[] questions_autogenerated = createQuestions(question_text,question_id);
                SurveyAdapter surveyAdapter = new SurveyAdapter(getApplicationContext(), questions_autogenerated);
                mGridViewQuestionAnswers.setAdapter(surveyAdapter);
            }

        }
    }

    /*********************************************************/
    public String ExtractQuestionsFromQueryResult (String result){
        try {
            JSONObject jsonResult = new JSONObject(result);
            JSONArray received_data = jsonResult.getJSONArray("user_related_data");
            if ("[]".equals(received_data.toString())) {
                //no questions available
                return "failed";
            } else {
                //questions available
                return received_data.toString();
            }
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
        }
        return "failed";
    }

    /*********************************************************/
    private MyQuestion[] createQuestions(JSONArray question_text,JSONArray question_id){
        MyQuestion[] questionForSurvey = new MyQuestion[question_id.length()];
        for(int i = 0; i<question_id.length(); i++){
            try{
                String questionText = question_text.getString(i);
                String questionId = question_id.getString(i);
                String questionAnswer = "";
                questionForSurvey[i]= new MyQuestion(questionText,questionId,questionAnswer);
            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + question_text + "\"");
            }
        }
        return questionForSurvey;

    }

    /**************************************************************/
    private class POSTAnswersQueryTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            StringBuilder data = new StringBuilder();
            HttpURLConnection httpUrlConnection = null;
            try{
                httpUrlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpUrlConnection.getOutputStream());
                wr.writeBytes("PostData="+params[1]);
                wr.flush();
                wr.close();

                InputStream in = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data.append(current);
                }
            }catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }
            return data.toString();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }

    /*************************************************************/
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
        //check the id of the option menu element
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            //SharedPreferences pref3;
            //pref3 = getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE);
            //editor = pref3.edit();
            //editor.putString("selected_subject_number", numberOfSubject);

            Intent startSettingsActivity = new Intent(this, SettingActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
