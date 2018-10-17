package com.example.ryad.seaal_signalisation_fuite;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Ryad on 24/09/2018.
 */

public class utilities {
   public static String ipServeur=new String("192.168.43.35");
   public static String mailDestination=new String("fm_bousri@esi.dz");
    //methodes pour l'envoi HTTP
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public static String POST(String url,List<NameValuePair> urlParameters) {
        InputStream inputStream = null;
        String result ="";
        try {

            // create HttpClient
            HttpClient httpclient = com.example.ryad.seaal_signalisation_fuite.HttpClientUser.getHttpClient();

            // make GET request to the given URL
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(urlParameters));

            post.setHeader("User-Agent", "Android");
            HttpResponse httpResponse = httpclient.execute(post);
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null){
                result = convertInputStreamToString(inputStream);
            }
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.e("InputStream", e.getLocalizedMessage());
        }

        return result;
    }
}
