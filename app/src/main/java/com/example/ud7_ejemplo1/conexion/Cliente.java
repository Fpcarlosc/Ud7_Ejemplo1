package com.example.ud7_ejemplo1.conexion;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cliente {

    // Dirección URL del servidor
    private static final String URL = "https://swapi.co/api/";
    private static Retrofit retrofit = null;

    public static Retrofit obtenerCliente(){
        if(retrofit == null){
            // Construimos el objeto Retrofit asociando la URL del servidor y el convertidor Gson
            // para formatear la respuesta JSON.
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
