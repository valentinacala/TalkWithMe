package com.example.valentina.talkwithme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.valentina.talkwithme.utilities.NotificationUtils;
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

public class LoginActivity extends AppCompatActivity {
    EditText userAccessID;
    EditText userAccessPASSWORD;
    Button LoginButton;
    ProgressBar LoginProgressBar;
    String userDataBaseID;
    TextView messageToUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userAccessID = findViewById(R.id.EditUserId);
        userAccessPASSWORD = findViewById(R.id.EditUserPassword);
        LoginButton = findViewById(R.id.LoginButton);
        LoginProgressBar = findViewById(R.id.LoadingBar);
        userDataBaseID = null;
        messageToUser = findViewById(R.id.LoginMessageToUser);

        //find if there are stored username and password
        SharedPreferences pref;
        pref = getApplicationContext().getSharedPreferences("userData", Context.MODE_PRIVATE );
        String user_username = pref.getString("DBusername","");
        String user_password  = pref.getString("DBpassword","");
        userAccessID.setText(user_username);
        userAccessPASSWORD.setText(user_password);

        //hide the keyboard when the edit boxes are unfocused
        userAccessID.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        userAccessPASSWORD.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        //when clicked, the loginButton send a request to the server to verify if the userid and password corespond to a registered user
        //if it is verified, the userID into the DB is stored in a shared preference file and
        // the app allows the access to resources
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creation of the URL which can recive the http request to verify the inserted login credentials
                String LoginMethod = "Login.php";
                URL myUrl = Utilities.buildURL(LoginMethod);
                //creation of the JSON object which will be sent by POST request to server
                JSONObject DataToPost = new JSONObject();
                try{
                    DataToPost.put("userid", userAccessID.getText().toString());
                    DataToPost.put("password",userAccessPASSWORD.getText().toString());

                    //send of the post request to server
                    //creation of a new thread to manage the connection request to the server
                    POSTQueryTask QueryTask = new POSTQueryTask();
                    QueryTask.execute(myUrl.toString(), DataToPost.toString());

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        TextView mLoginMessageToUser = findViewById(R.id.LoginMessageToUser);
        mLoginMessageToUser.setVisibility(View.INVISIBLE);
    }

    /********************************/
    //allows to hide the keyboard when the edit boxes are unfocused
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /*******************************/
    private class POSTQueryTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoginButton.setVisibility(View.INVISIBLE);
            LoginProgressBar.setVisibility(View.VISIBLE);
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
            userDataBaseID = ExtractUserIdFromQueryResult(result);

            LoginProgressBar.setVisibility(View.INVISIBLE);
            LoginButton.setVisibility(View.VISIBLE);
            messageToUser.setVisibility(View.VISIBLE);

            //verity that userDBID is a valid user_id
            if("failed".equals(userDataBaseID)){
                messageToUser.setText(getResources().getString(R.string.ConnectionFailed));
            } else if("none".equals(userDataBaseID)){
                messageToUser.setText(getResources().getString(R.string.AccessDenied));
            } else {
                messageToUser.setText(getResources().getString(R.string.AccessGranted));
                //local storage of userID,username and password which represent the user inside the server db
                //file name: userData
                SharedPreferences.Editor editor;
                SharedPreferences pref;
                pref = getApplicationContext().getSharedPreferences("userData", Context.MODE_PRIVATE );
                editor = pref.edit();
                editor.putString("DBuser_id", userDataBaseID);
                editor.putString("DBusername",userAccessID.getText().toString());
                editor.putString("DBpassword",userAccessPASSWORD.getText().toString());

                //save subject id associated to the user to set the next activity
                String subjectIdFromQueryResultJSONArray =  ExtractSubjectIdFromQueryResult(result);
                editor.putString("DBrelated_subject_id_name",subjectIdFromQueryResultJSONArray);
                editor.apply();
                Handler DelayHandler = new Handler();
                DelayHandler.postDelayed (GoToMainMenuActivity, 600);
            }
        }
    }
    /**********************************************************/
    public static String ExtractUserIdFromQueryResult(String result) {
        try {
            JSONObject jsonResult = new JSONObject(result);
            JSONArray received_data = jsonResult.getJSONArray("user_related_data");
            JSONObject user_related_data = received_data.getJSONObject(0);
            result = user_related_data.getString("user_id");
            Log.d("My App", jsonResult.toString());
            return result ;
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
        }
        return "failed";
    }

    public String ExtractSubjectIdFromQueryResult(String result){
        JSONArray subject_id = new JSONArray();
        try{
            JSONObject jsonResult = new JSONObject(result);
            JSONArray received_data = jsonResult.getJSONArray("user_related_data");



            for (int i=0; i<received_data.length();++i){
                JSONObject ijsonobject = received_data.getJSONObject(i);
                String name = ijsonobject.keys().next();
                //String value = ijsonobject.optString(name);
                if("subject_id".equals(name)){
                    subject_id.put(ijsonobject);
                }
            }

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
        }
        return subject_id.toString();
    }
    /**********************************************************/
    /* this method provide what to do after waiting by the mHandler */
    private Runnable GoToMainMenuActivity = new Runnable(){
        public void run(){
            Intent OpenMainMenu = new Intent(LoginActivity.this, MainMenu.class);
            startActivity(OpenMainMenu);
        }
    };
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
        //check the id id of the option menu element
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /***************************************************************/
    public void testNotification (View view) {
        NotificationUtils.remindUserBecauseCharging(this);
    }
}