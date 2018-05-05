package com.example.khalilbennani.gestiondesarbres;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FormulaireActivity extends AppCompatActivity {

    private ArrayList<Arbre> arbres = null;
    private ArbreAdapter m_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arbres = new ArrayList<Arbre>();
        this.m_adapter = new ArbreAdapter(this, R.layout.onefeature, arbres);
       // setListAdapter(this.m_adapter);

        ArrayAdapter adapter = new ArrayAdapter<LinearLayout>(this,R.layout.onefeature);


    }




    private class ArbreAdapter extends ArrayAdapter<Arbre> {

        private ArrayList<Arbre> items;

        public ArbreAdapter(Context context, int textViewResourceId, ArrayList<Arbre> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.onefeature, null);
            }
            Arbre arbre = items.get(position);
            if (arbre != null) {
                TextView typeFeatures = (TextView) v.findViewById(R.id.typeFeatures);
                TextView typeGeometry = (TextView) v.findViewById(R.id.typeGeometry);
                TextView textCoordinates = (TextView) v.findViewById(R.id.textCoordinates);
                TextView textEspece = (TextView) v.findViewById(R.id.textEspece);
                TextView textDiametre = (TextView) v.findViewById(R.id.textDiametre);
                if (typeFeatures != null) {
                    typeFeatures.setText(arbre.getType());
                }
                if(typeGeometry != null){
                    typeGeometry.setText(arbre.getTypeGeo());
                }
                if (textCoordinates != null) {
                    textCoordinates.setText(arbre.getCoord_x()+ " " + arbre.getCoord_y());
                }
                if(textEspece != null){
                    textEspece.setText(arbre.getEspece());
                }
                if (textDiametre != null) {
                    textDiametre.setText((int) arbre.getDiametre());
                }
            }
            return v;
        }
    }

}
