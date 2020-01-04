package com.example.ud7_ejemplo1.modelo;

import com.google.gson.annotations.SerializedName;

// Solo vamos a hacer uso de las propiedades de nombre y género del personaje.
// Usamos la anotación "SerializedName" para indicar que queremos obtener el valor de la
// clave indicada, así podemos usar otro nombre para el atributo del POJO. En otro caso
// el atributo se debe llamar igual a la clave.
public class Personaje {
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private String gender;

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

}
