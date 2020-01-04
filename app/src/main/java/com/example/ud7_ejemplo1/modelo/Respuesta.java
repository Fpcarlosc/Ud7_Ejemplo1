package com.example.ud7_ejemplo1.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Respuesta {
    @SerializedName("results")
    private ArrayList<Personaje> results;

    public ArrayList<Personaje> getResults() {
        return results;
    }
}
