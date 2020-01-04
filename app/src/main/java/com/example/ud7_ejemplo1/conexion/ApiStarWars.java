package com.example.ud7_ejemplo1.conexion;

import com.example.ud7_ejemplo1.modelo.Respuesta;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiStarWars {
    // Realizamos la consulta sobre la ruta "people" del servidor https://swapi.co/api/ para
    // obtener los personajes.
    // Cada Call hace una petición HTTP asíncrona al servidor.
    @GET("people")
    Call<Respuesta> obtenerPersonaje();
}
