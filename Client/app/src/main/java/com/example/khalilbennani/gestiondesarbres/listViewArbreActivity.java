package com.example.khalilbennani.gestiondesarbres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

public class listViewArbreActivity extends AppCompatActivity {

    List<Arbre> listArbres= null;
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray =new JSONArray();
    JSONObject geometryJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_arbre);
        listArbres = new ArrayList<>();


        OkHttpClient client = new OkHttpClient();
        String url = "https://www.longueuil.quebec/sites/longueuil/files/donnees_ouvertes/arbres.json";

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Erreur:","il y a un probleme");
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
                    //creation d un tableau de json
                    jsonArray  =  jsonObject.getJSONArray("features");
                    String type,geometry,porpriete;

                    //creer un tableau de json pour mettre les objet lu
                    while (count < jsonArray.length()) {

                    Arbre arbreTemp = new Arbre();

                    JSONObject Jo = jsonArray.getJSONObject(count);

                    //String pour recuperer les valeur des string qui constitues le json
                     type = Jo.getString("type");
                     arbreTemp.setType(type);
                     geometry = Jo.getString("geometry");
                     porpriete = Jo.getString("properties");

                     //Creation des objet json -- les sous tableaux du json pere
                    geometryJson = new JSONObject(geometry);
                    JSONObject porprieteJson = new JSONObject(porpriete);

                    //mettre les coordones dans un tableau de json pour pouvoir acceder au jeu de donnees
                    JSONArray tableau = geometryJson.getJSONArray("coordinates");
                    arbreTemp.setTypeGeo(geometryJson.getString("type"));

                    //mettre les donnes recuperer sous forme de objet dans un tableau de double (double parse)
                    arbreTemp.setCoord_x(Double.parseDouble(tableau.getString(0)));
                    arbreTemp.setCoord_y(Double.parseDouble(tableau.getString(1)));

                    //affichier les porprietes
                    arbreTemp.setEspece(porprieteJson.get("Espece").toString());
                    Object diametreObject = porprieteJson.get("Diametre_Tronc");
                    String diametreString = diametreObject.toString();
                    double diametre = 0;
                    try {
                        diametre = Double.parseDouble(diametreString);
                        arbreTemp.setDiametre(diametre);

                    } catch (NumberFormatException e)
                    {
                        e.printStackTrace();
                    }

                    listArbres.add(arbreTemp);
                    count++;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
        loadArbreListView();
    }


    void loadArbreListView(){

        final ListView listview = findViewById(R.id.list);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<Arbre> adapter = (ArrayAdapter<Arbre>) listview.getAdapter();
                Arbre arbre = (Arbre) listview.getAdapter().getItem(position);
               // startViewEndroitActivity(arbre);
            }
        });

        ArrayAdapter<Arbre> adapter = new ArrayAdapter<Arbre>(
                this,
                android.R.layout.simple_list_item_1, listArbres
        );

        listview.setAdapter(adapter);
    }

    //TODO...
    private void startViewEndroitActivity(Arbre e){
        Intent intent = new Intent(this, ViewArbreActivity.class);
       // intent.putExtra("id", e.getId()); // <-- TODO : get this to work
       // intent.putExtra("adresse", e.getAdresse());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(com.example.mounia.tp1.Logique.FacadeModele.instance().hasDatabase()){
//            loadEndroitListView();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
