package com.example.ryad.seaal_signalisation_fuite;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by Ryad on 24/09/2018.
 */

public class HttpClientUser {
    protected static DefaultHttpClient client=new DefaultHttpClient();

    public static HttpClient getHttpClient(){

        return client;
    }
}
