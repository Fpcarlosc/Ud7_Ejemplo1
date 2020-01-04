package com.example.ud7_ejemplo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ud7_ejemplo1.modelo.Personaje;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PersonajeAdapter extends RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder>{

    private Context context;
    ArrayList<Personaje> lista;


    public PersonajeAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public PersonajeAdapter.PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());

        View view=inflater.inflate(R.layout.elementos_lista,parent,false);

        PersonajeViewHolder miViewHolder=new PersonajeViewHolder(view);

        return miViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeViewHolder holder,int position){
        Personaje p = lista.get(position);

        holder.nombretextView.setText(p.getName());
        holder.generotextView.setText(p.getGender());
    }

    @Override
    public int getItemCount(){
        if(lista == null)
            return 0;
        else
            return lista.size();
    }

    // Método para añadir la lista al Recyclerview
    public void anyadirALista(ArrayList<Personaje> lista){
        this.lista.addAll(lista);
        notifyDataSetChanged(); // Actualizamos el recyclerView
    }

    class PersonajeViewHolder extends RecyclerView.ViewHolder {

        TextView nombretextView;
        TextView generotextView;

        public PersonajeViewHolder(View itemView) {
            super(itemView);

            nombretextView = itemView.findViewById(R.id.nombretextView);
            generotextView = itemView.findViewById(R.id.generotextView);
        }
    }
}
