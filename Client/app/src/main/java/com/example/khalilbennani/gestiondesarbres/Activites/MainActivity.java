package com.example.khalilbennani.gestiondesarbres.Activites;

//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.khalilbennani.gestiondesarbres.Activites.ListFonctActivity;
//import com.example.khalilbennani.gestiondesarbres.CommunicationClientServeur.Communication;
//import com.example.khalilbennani.gestiondesarbres.R;
//import com.example.khalilbennani.gestiondesarbres.Utilitaires.Preferences;
//import com.github.nkzawa.socketio.client.IO;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.khalilbennani.gestiondesarbres.CommunicationClientServeur.Communication;
import com.example.khalilbennani.gestiondesarbres.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    private CheckBox rememberMe;
    private CheckBox rememberPassword;
    private Button buttonLogin;
    private EditText editPassword;
    private EditText editUsername;

    private OkHttpClient okHttpClient;
    private Request request;

    private Communication communication;
    {
        try {
            communication = Communication.getInstance();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPassword = findViewById(R.id.password);
        editUsername = findViewById(R.id.username);

        buttonLogin = findViewById(R.id.login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String USERNAME = editUsername.getText().toString().trim();
                final String PASSWORD = editPassword.getText().toString().trim();

                System.out.print("USERNAME : " + USERNAME + ", PASSWORD : " + PASSWORD + "\n");

                final AsyncTask<Void, Void, Void> login = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        if (communication.login(USERNAME, PASSWORD)) {
                            okHttpClient = new OkHttpClient();
                            Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();

                            // Lancer la prochaine activite
                            onStartClick(buttonLogin);

                        } else {
                            Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                        }

                        return null;
                    }
                }.execute();
            }
        });
    }

    public void onStartClick(View view){
        Intent intent = new Intent(this, ListFonctActivity.class);
        startActivity(intent);
    }
}

//public class MainActivity extends Activity
//{
//    private CheckBox rememberMe;
//    private CheckBox rememberPassword;
//    private Button buttonLogin;
//    private EditText editPassword;
//    private EditText editUsername;
//
//    private Socket socket = null;
//    private final String SERVER_IP = "10.240.201.120"; // "192.168.239.1";
//    private final int SERVER_PORT = 5010; //5001;
//    private Thread socketThread = null;
//    private final String USERNAME = "khalil";
//    private final String PASSWORD = "ben";
//
//    private static void println(String texte) {
//        System.out.print(texte + "\n");
//    }
//
//    public void send(JSONObject message) throws IOException {
//        PrintWriter writer = new PrintWriter(socket.getOutputStream());
//        writer.println(message.toString());
//        writer.flush();
//    }
//
//    public void send(String message) throws IOException {
//        PrintWriter writer = new PrintWriter(socket.getOutputStream());
//        writer.println(message);
//        writer.flush();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        socketThread = new Thread(new ConnectionThread());
//        socketThread.start();
//        try {
//            socketThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        println("Arrive ici");
//    }
//
//    public void onStartClick(View view){
//        Intent intent = new Intent(this, ListFonctActivity.class);
//        if (socket != null && !socket.isClosed() && socket.isConnected()) {
//            try {
//                send(USERNAME);
//                println("Sent USERNAME to server");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        startActivity(intent);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        try { socketThread.join(); }
//        catch (InterruptedException e) { e.printStackTrace(); }
//
//        if (socket != null && !socket.isClosed()) {
//            try { socket.close(); }
//            catch (IOException e) { e.printStackTrace(); }
//        }
//    }
//
//    class ConnectionThread implements Runnable
//    {
//        @Override
//        public void run()
//        {
//            println("Dans run");
//            try
//            {
//                InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
//                socket = new Socket(serverAddress, SERVER_PORT);
//                if (socket.isConnected()) {
//                    println("Socket is connected");
//                } else {
//                    println("Socket is not connected");
//                }
//
//                //send(USERNAME + "\r\n");
//                //send(PASSWORD + "\r\n");
//
//                //System.out.println("Sent \"" + USERNAME + "\"");
//                //System.out.println("Sent \"" + PASSWORD + "\"");
//                //socket.close();
//            }
//            catch (UnknownHostException e) { e.printStackTrace(); }
//            catch (IOException e) { e.printStackTrace(); }
//        }
//    }
//}

//public class MainActivity extends Activity
//{
//    private CheckBox rememberMe;
//    private CheckBox rememberPassword;
//    private Button buttonLogin;
//    private EditText editPassword;
//    private EditText editUsername;
//    private final String SERVER_IP = "10.240.201.120"; // "192.168.239.1";
//    private final int SERVER_PORT = 5010; //5001;
//    private final String USERNAME = "khalil";
//    private final String PASSWORD = "ben";
//
//    private Socket mSocket = null;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        try {
//            //mSocket = IO.socket("http://" + "10.240.201.45" + ":" + String.valueOf(5002));
//            mSocket = IO.socket("https://2bc2b87e.ngrok.io");
//            //mSocket = IO.socket("http://" + "142.137.45.94" + ":" + String.valueOf(80));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        if (mSocket != null) {
//            mSocket.connect();
//            if (mSocket.connected())
//                Toast.makeText(this, "Socket is connected", Toast.LENGTH_SHORT).show();
//            send("Hello from Android");
//            Toast.makeText(this, "Sent 'Hello from Android' message", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Socket problem", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void send(String message) {
//        mSocket.emit("new message", message);
//    }
//
//    private static void println(String texte) {
//        System.out.print(texte + "\n");
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        mSocket.disconnect();
//        mSocket.off("new message", onNewMessage);
//    }
//
//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    String data = (String) args[0];
//
//                    // add the message to view
//                    System.out.print(data);
//                    //addMessage(data);
//                }
//            });
//        }
//    };
//
//    public void onStartClick(View view){
//        Intent intent = new Intent(this, ListFonctActivity.class);
//        startActivity(intent);
//    }
//}
