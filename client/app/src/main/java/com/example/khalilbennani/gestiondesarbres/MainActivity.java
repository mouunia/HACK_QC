package com.example.khalilbennani.gestiondesarbres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.khalilbennani.gestiondesarbres.Utilitaires.Preferences;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;



public class MainActivity extends AppCompatActivity {
    private CheckBox rememberMe;
    private CheckBox rememberPassword;
    private Button buttonLogin;
    private EditText editPassword;
    private EditText editUsername;


    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray =new JSONArray();
    String json_string;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Preferences.init(getBaseContext());

        editPassword = (EditText) findViewById(R.id.password);
        editUsername = (EditText) findViewById(R.id.username);
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);
        rememberPassword = (CheckBox) findViewById(R.id.rememberPassword);


        //code khalil test

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.longueuil.quebec/sites/longueuil/files/donnees_ouvertes/arbres.json";

            final Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.i("alllopppppppppppppp:","pas reisso");


                }

                @Override
                public void onResponse(Response response) throws IOException {

                    String response_khalil = response.body().string();

                    //transformer le string recu par la fonction get a un objet Json
                    try {
                        jsonObject = new JSONObject(response_khalil);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    try {
                        //pour le while
                        int count = 0;
                        jsonArray  =  jsonObject.getJSONArray("features");
                        String type,geometry;

                        //creer un tableau de json pour mettre les objet lu
                        while (count < jsonArray.length()) {

                            JSONObject Jo = jsonArray.getJSONObject(count);


                            type = Jo.getString("type");
                            geometry = Jo.getString("geometry");

                            Log.i("type: ", type);
                            Log.i("geometry: ", geometry);


                            count++;
                        }

                        Log.i("alllopppppppppppppp:", response_khalil);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ///

            });






        String username = Preferences.get(Preferences.USERNAME);
        editUsername.setText(username);
        String password = Preferences.get(Preferences.PASSWORD);
        editPassword.setText(password);
        rememberPassword.setChecked(Boolean.parseBoolean(Preferences.get(Preferences.REMEMBER_PASSWORD)));
        rememberMe.setChecked(Boolean.parseBoolean(Preferences.get(Preferences.REMEMBER_ME)));

        // LOGIN
        buttonLogin = findViewById(R.id.login);
        //code on button click

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(final View view) {

                final String username = editUsername.getText().toString().trim();
                final String password = editPassword.getText().toString().trim();

                if (rememberMe.isChecked()) {
                    Preferences.set(Preferences.USERNAME, username);
                    Preferences.set(Preferences.REMEMBER_ME, "true");
                } else {
                    Preferences.set(Preferences.USERNAME, "");
                    Preferences.set(Preferences.REMEMBER_ME, "false");
                }
                if (rememberPassword.isChecked()) {
                    Preferences.set(Preferences.PASSWORD, password);
                    Preferences.set(Preferences.REMEMBER_PASSWORD, "true");
                } else {
                    Preferences.set(Preferences.PASSWORD, "");
                    Preferences.set(Preferences.REMEMBER_PASSWORD, "false");
                }


                //bypass
                if (username.equals("bypass")) {
                    onStartClick(view);
                }
            }
        });
    }

    /*
   * Pour ouvrir le layout de mode de reception
   */
    public void onStartClick(View view){
        Intent intent = new Intent(this, ListFonctActivity.class);
        intent.putExtra("USER_NAME", editUsername.getText().toString());
        startActivity(intent);
    }

}
