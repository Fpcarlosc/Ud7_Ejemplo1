package com.example.ud7_ejemplo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ud7_ejemplo1.conexion.ApiStarWars;
import com.example.ud7_ejemplo1.conexion.Cliente;
import com.example.ud7_ejemplo1.modelo.Personaje;
import com.example.ud7_ejemplo1.modelo.Respuesta;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonajeAdapter personajeAdapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);

        // Añadimos la línea de separación de elementos de la lista
        // 0 para horizontal y 1 para vertical
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, 1));

        // Creamos un LinearLayout que contendrá cada elemento del RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        personajeAdapter = new PersonajeAdapter(this);

        recyclerView.setAdapter(personajeAdapter);

        retrofit = Cliente.obtenerCliente();

        obtenerDatos();
    }

    // Método para acceder obtener los datos a través de la API y añadirlos a la lista.
    private void obtenerDatos(){
        // Hacemos uso de la ApiStarWars creada para obtener los valores pedidos.
        ApiStarWars api = retrofit.create(ApiStarWars.class);

        // Obtenemos la respuesta.
        Call<Respuesta> respuesta = api.obtenerPersonaje();

        // Realizamos una petición asíncrona y debemos implementar un Callback con dos métodos:
        // onResponse y onFailure.
        respuesta.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                // Si la respuesta es correcta accedemos al cuerpo del mensaje recibido,
                // extraemos la información y la añadimos a la lista.
                if(response.isSuccessful()){
                    Respuesta respuesta = response.body();
                    ArrayList<Personaje> listapersonajes = respuesta.getResults();
                    personajeAdapter.anyadirALista(listapersonajes);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Fallo en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
