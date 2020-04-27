package com.example.valentina.talkwithme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.valentina.talkwithme.utilities.MyContact;
import com.example.valentina.talkwithme.utilities.MyQuestion;

/**
 * Created by valentina on 02/04/2018.
 */

public class ContactAdapter extends BaseAdapter {
    private final Context mContext;
    private final MyContact[] contacts;

    public ContactAdapter(Context context, MyContact[] contacts){
        this.mContext =  context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.length;
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
        final MyContact contact = contacts[position];
        if(convertView == null){
            final LayoutInflater mLayoutInflarter = LayoutInflater.from(mContext);
            convertView = mLayoutInflarter.inflate(R.layout.contacts_single_grid, null);
        }

        final TextView mContactFirstName = convertView.findViewById(R.id.ContactFirstName);
        mContactFirstName.setText(contact.getFirstName());

        final TextView mContactLastName = convertView.findViewById(R.id.ContactLastName);
        mContactLastName.setText(contact.getLastName());

        final TextView mContactDescription = convertView.findViewById(R.id.ContactDescription);
        mContactDescription.setText(contact.getDescription());

        final TextView mContactNumber = convertView.findViewById(R.id.ContactNumber);
        mContactNumber.setText(contact.getNumber());

        if (mContactNumber.getText()=="null"){
            mContactNumber.setVisibility(View.GONE);
        }

        final TextView mContactPhone = convertView.findViewById(R.id.ContactPhone);
        mContactPhone.setText(contact.getPhone());
        if (mContactPhone.getText()=="null"){
            mContactPhone.setVisibility(View.GONE);
        }

        final TextView mContactEmail = convertView.findViewById(R.id.Contactemail);
        mContactEmail.setText(contact.getemail());
        if (mContactEmail.getText()=="null"){
            mContactEmail.setVisibility(View.GONE);
        }

        return convertView;
    }
}

