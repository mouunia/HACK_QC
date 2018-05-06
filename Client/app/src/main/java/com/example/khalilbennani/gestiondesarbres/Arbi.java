package com.example.khalilbennani.gestiondesarbres;

import android.app.Application;

import java.util.List;

public class Arbi extends Application{

    private List<Arbre> listArbre;

    public void setList(java.util.List<Arbre> list)
    {
        listArbre= list;
    }

    public List<Arbre> getList()
    {
        return listArbre;
    }
}
