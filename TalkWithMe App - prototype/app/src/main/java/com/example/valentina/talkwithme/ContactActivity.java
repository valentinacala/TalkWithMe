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
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.valentina.talkwithme.utilities.MyContact;
import com.example.valentina.talkwithme.utilities.MyQuestion;
import com.example.valentina.talkwithme.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ContactActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        RelativeLayout mNoContactRelativeLayout = findViewById(R.id.NoContactRelativeLayout);
        mNoContactRelativeLayout.setVisibility(View.INVISIBLE);
        RelativeLayout mloadingContactRelativeLayout = findViewById(R.id.loadingContactRelativeLayout);
        mloadingContactRelativeLayout.setVisibility(View.VISIBLE);
        RelativeLayout mFindContactsRelativeLayout  = findViewById(R.id.FindContactsRelativeLayout);
        mFindContactsRelativeLayout.setVisibility(View.INVISIBLE);

        //use subject_id to find the related contact
        findTheContacts();



    }

    /*************************************************************/
    protected void findTheContacts(){
        //identify the subject_id and user_id
        SharedPreferences pref;
        pref = getApplicationContext().getSharedPreferences("userData", Context.MODE_PRIVATE );
        String user_id = pref.getString("DBuser_id","none");
        pref = getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE );
        String subject_id = pref.getString("selected_subject_id","none");

        //prepare to request to server
        //creation of the URL which can recive the http request to verify the inserted login credentials
        String ContactRecoveryMethod = "GetContacts.php";
        URL myUrl = Utilities.buildURL(ContactRecoveryMethod);
        //creation of the JSON object which will be sent by POST request to server
        JSONObject DataToPost = new JSONObject();
        try{
            DataToPost.put("userid", user_id);
            DataToPost.put("subjectid", subject_id);

            //send of the post request to server
            //creation of a new thread to manage the connection request to the server
            ContactActivity.POSTContactsQueryTask QueryTask = new ContactActivity.POSTContactsQueryTask();
            QueryTask.execute(myUrl.toString(), DataToPost.toString());

        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    /*************************************************************/
    private class POSTContactsQueryTask extends AsyncTask<String, Void, String> {
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
            String contacts = ExtractContactsFromQueryResult(result);

            //save contacts in shared preferences
            SharedPreferences.Editor editor;
            SharedPreferences pref;
            pref = getApplicationContext().getSharedPreferences("TemporalFile", Context.MODE_PRIVATE );
            editor = pref.edit();
            editor.putString("DBcontacts",contacts);
            editor.apply();

            //TEST
            TextView mLoadingContactText = findViewById(R.id.LoadingContactText);
            mLoadingContactText.setText(result);

            RelativeLayout mloadingContactRelativeLayout = findViewById(R.id.loadingContactRelativeLayout);
            mloadingContactRelativeLayout.setVisibility(View.INVISIBLE);

            if ("noContact".equals(contacts)){
                //no contacts to display!
                RelativeLayout mNoContactRelativeLayout = findViewById(R.id.NoContactRelativeLayout);
                mNoContactRelativeLayout.setVisibility(View.VISIBLE);
                //TEST
                TextView mNoQuestionsText = findViewById(R.id.NoQuestionsText);
                mNoQuestionsText.setText("no contact");

            } else if ("not extracting properly".equals(contacts)) {
                //no contacts to display!
                RelativeLayout mNoContactRelativeLayout = findViewById(R.id.NoContactRelativeLayout);
                mNoContactRelativeLayout.setVisibility(View.VISIBLE);
                //TEST
                TextView mNoQuestionsText = findViewById(R.id.NoQuestionsText);
                //mNoQuestionsText.setText("other problems");
                mNoQuestionsText.setText("extraction failed");

            } else{
                    //contacts to display
                    RelativeLayout mFindContactsRelativeLayout = findViewById(R.id.FindContactsRelativeLayout);
                    mFindContactsRelativeLayout.setVisibility(View.VISIBLE);

                    //extract contacts
                    JSONArray contact_id = new JSONArray();
                    JSONArray contact_firstName = new JSONArray();
                    JSONArray contact_lastName = new JSONArray();
                    JSONArray contact_mobile = new JSONArray();
                    JSONArray contact_email = new JSONArray();
                    JSONArray contact_phone = new JSONArray();
                    JSONArray contact_description = new JSONArray();
                    try{
                        JSONArray jsoncontacts_text_id = new JSONArray(contacts);
                        for (int i=0; i<jsoncontacts_text_id.length();++i){
                            JSONObject ijsonobject = jsoncontacts_text_id.getJSONObject(i);
                            contact_id.put(ijsonobject.getString("user_id"));
                            contact_firstName.put(ijsonobject.getString("first_name"));
                            contact_lastName.put(ijsonobject.getString("last_name"));
                            contact_mobile.put(ijsonobject.getString("mobile"));
                            contact_email.put(ijsonobject.getString("email"));
                            contact_phone.put(ijsonobject.getString("phone"));
                            contact_description.put(ijsonobject.getString("description"));
                        }
                    } catch (Throwable t) {
                        Log.e("My App", "Could not parse malformed JSON: \"" + contacts + "\"");
                    }
                    //inflate questions inside the grid
                    GridView mGridViewContacts = findViewById(R.id.GridViewContacts);
                    final MyContact[] contacts_autogenerated = createContacts(contact_description,contact_id,contact_firstName,contact_lastName,contact_mobile,contact_email,contact_phone);
                    ContactAdapter contactAdapter = new ContactAdapter(getApplicationContext(), contacts_autogenerated);
                    mGridViewContacts.setAdapter(contactAdapter);
            }
        }
    }

    /*********************************************************/
    private MyContact[] createContacts(JSONArray contact_description,JSONArray contact_id, JSONArray contact_firstName,JSONArray contact_lastName, JSONArray contact_mobile, JSONArray contact_email,JSONArray contact_phone){
        MyContact[] contactForView = new MyContact[contact_id.length()];
        for(int i = 0; i<contact_id.length(); i++){
            try{
                String contactDescription = contact_description.getString(i);
                String contactName = contact_firstName.getString(i);
                String contactId = contact_id.getString(i);
                String contactSurname = contact_lastName.getString(i);
                String contactMobile = contact_mobile.getString(i);
                String contactEmail = contact_email.getString(i);
                String contactPhone = contact_phone.getString(i);
                contactForView[i]= new MyContact(contactDescription,contactName,contactSurname,contactMobile,contactPhone,contactEmail);
            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + contact_id + "\"");
            }
        }
        return contactForView;

    }

    /*************************************************************/
    public String ExtractContactsFromQueryResult(String result){
        try {
            JSONObject jsonResult = new JSONObject(result);
            JSONArray received_data = jsonResult.getJSONArray("contact_related_data");
            if ("[]".equals(received_data.toString())) {
                //no questions available
                return "noContact";
            } else {
                //questions available
                return received_data.toString();
            }
        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
        }
        return "not extracting properly";
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
