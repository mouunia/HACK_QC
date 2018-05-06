package com.example.khalilbennani.gestiondesarbres.CommunicationClientServeur;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

public class Communication {

    private static Communication instance;
    private InetAddress serverAddr;
    private Socket socket;
    public String IP = "192.168.239.1";
    //public int PORT = 5000;
    public int PORT = 80;
    private String serverHttpUrl;
    private boolean connected = false;

    private Communication() throws UnknownHostException {
        super();
        serverAddr = InetAddress.getByName(IP);
        socket = new Socket();
    }

    public static Communication getInstance() throws UnknownHostException {
        if (instance == null) instance = new Communication();
        return instance;
    }

    private String urlPost;

    // https://stackoverflow.com/questions/9286861/get-ip-address-with-url-string-java?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    public static String URL_2_IP(String urlString) {
        try {
            InetAddress address = InetAddress.getByName(new URL(urlString).getHost());
            String ip = address.getHostAddress();
            return ip;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String URL = "https://2bc2b87e.ngrok.io";

    public boolean login(String username, String password) {

        //serverHttpUrl = "http://" + IP + ":" + String.valueOf(PORT);
        String ip = URL_2_IP(URL);
        System.out.print(ip + '\n');
        serverHttpUrl = "http://" + IP + ":" + String.valueOf(PORT);
        urlPost = serverHttpUrl + "/users/login";
        OkHttpClient okHttpClient = new OkHttpClient();

        //MediaType json = MediaType.parse("application/json");
        RequestBody body = new FormEncodingBuilder()
                .add("user", "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}")
                .build();

        Request request = new Request.Builder().url(serverHttpUrl).post(body).build();

        boolean result = false;
        final Semaphore done = new Semaphore(0);
        Callback call = new Callback() {

            public boolean successful = false;
            public boolean getSuccess(){ return successful; }

            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Connection", "failed " + e.getMessage());
                done.release();
            }

            @Override
            public void onResponse(Response response) {
                Log.i("aloooooo",response.toString());
                successful = response.isSuccessful();
                if(!successful) Log.i("Connection"," was rejected");
                done.release();
            }
        };

        okHttpClient.newCall(request).enqueue(call);

        try {
            done.acquire();
            result = (boolean) call.getClass().getMethod("getSuccess").invoke(call);
        } catch (Exception e){
            e.printStackTrace();
        }

        connected = result;
        return result;
    }

    public boolean logout() {
        return false;
//        if(connected) {
//            String username = "";
//            String urlPost = serverHttpUrl + "/users/logout";
//
//            Log.i("Deconnection", urlPost);
//
//            OkHttpClient okHttpClient = new OkHttpClient();
//
//            //MediaType jason = MediaType.parse("application/json");
//            RequestBody body = new FormEncodingBuilder()
//                    .add("user", "{\"username\": \"" + username + "\"}")
//                    .build();
//
//            Request request = new Request.Builder().header("user", "user").url(urlPost).post(body).build();
//
//            boolean result = false;
//            final Semaphore done = new Semaphore(0);
//            Callback call = new Callback() {
//
//                public boolean successful = false;
//
//                public boolean getSuccess() {
//                    return successful;
//                }
//
//                @Override
//                public void onFailure(Request request, IOException e) {
//                    Log.e("Deconnection", "failed " + e.getMessage());
//                    done.release();
//                }
//
//                @Override
//                public void onResponse(Response response) {
//                    successful = response.isSuccessful();
//                    if (!successful) Log.i("Deconnection", " was rejected");
//                    done.release();
//                }
//            };
//
//            okHttpClient.newCall(request).enqueue(call);
//
//            try {
//                done.acquire();
//                result = (boolean) call.getClass().getMethod("getSuccess").invoke(call);
//            } catch (Exception e) {
//                Log.i("Deconnection", "error retrieving result: " + e.getMessage());
//            }
//
//            Log.i("Deconnection", String.valueOf(result));
//
//            return result;
//        }
//        else{
//            connected = false;
//            return true;
//        }
    }
}
