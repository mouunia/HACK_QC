package com.example.khalilbennani.gestiondesarbres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewArbreActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_arbre);
        Bundle b= getIntent().getExtras();

        TextView typeFeat=findViewById(R.id.typeFeatures);
        TextView typeGeo=findViewById(R.id.typeGeometry);
        TextView textCoord=findViewById(R.id.textCoordinates);
        TextView textEspece=findViewById(R.id.textEspece);
        TextView textDiametre=findViewById(R.id.textDiametre);

        typeFeat.setText(b.getString("typeFeature"));
        typeGeo.setText(b.getString("typeGeometry"));
        textCoord.setText("("+String.valueOf(b.getDouble("textCoordinates_x"))+","+String.valueOf(b.getDouble("textCoordinates_y"))+")");
        textEspece.setText(b.getString("textEspece"));
        textDiametre.setText(String.valueOf(b.getFloat("textDiametre")));


    }
}
