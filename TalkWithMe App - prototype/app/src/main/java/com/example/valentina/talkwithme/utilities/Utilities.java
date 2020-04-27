package com.example.valentina.talkwithme.utilities;


import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class Utilities {

    public static URL buildURL(String requestedMethod) {

        String ServerIPaddress = "";//"192.168.0.88";//"192.168.1.113";
        String ServerPort = ":81";

        if (!"".equals(ServerPort)) {
            ServerIPaddress = ServerIPaddress.concat(ServerPort);
        }

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .encodedAuthority(ServerIPaddress)
                .appendPath("TalkWithMe_connectivity")
                .appendPath(requestedMethod);

        //connessione da remoto siteground luca
        //https://www.edutube.it/mobileapp/Login.php
        Uri.Builder builder2 = new Uri.Builder();
        builder2.scheme("https")
                .authority("www.edutube.it")
                .appendPath("mobileapp")
                .appendPath(requestedMethod);

        URL url = null;
        try {
            url = new URL(builder2.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
