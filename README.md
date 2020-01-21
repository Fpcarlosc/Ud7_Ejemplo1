# Ud7_Ejemplo1
_Ejemplo 1 de la Unidad 7._

Vamos a implementar una aplicación que acceda a la _SWAPI_ (Star Wars API) alojada en el servidor _https://swapi.co/_, obtenga 
el nombre y género de los primeros 10 personajes almacenados y los liste en un _RecyclerView_.
Para ello vamos a hacer uso de la librería _Retrofit_ y obtendremos los datos utilizando el método HTTP _GET_.

Los pasos serán los siguientes:


## Paso 1: Modificar el fichero _build.gradle(Module:app)_
Añadiremos las siguientes dependencias para poder hacer uso de la librería _Retrofit_, del convertidor de _JSON_ _GSON_ y del _RecyclerView_
(Podemos ver la versión actual en _https://github.com/square/retrofit_):
```
    implementation 'com.squareup.retrofit2:retrofit:2.7.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.7.0'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
```

Además, para su correcto funcionamiento, deberemos añadir las siguientes líneas en el bloque _android_:
```
android {
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    ...
}
```

## Paso 2: Dar permisos a la aplicación
Para poder realizar conexiones con servidores deberemos darle permisos a la aplicación. Para ello se debe añadir la siguiente línea en el fichero _AndrodManifest.xml_:
```
<uses-permission android:name="android.permission.INTERNET"/>
```

## Paso 3: Creación de la interfaz _ApiStarWars_
Creamos una interfaz con los métodos HTTP a utilizar en nuestra aplicación. En este caso solo un método _GET_ sobre la ruta _people_ del servidor. 
Además declaramos un método para obtener la información de los personajes que devuelve un objeto _Call_ de tipo _Respuesta_.
```
public interface ApiStarWars {
    // Realizamos la consulta sobre la ruta "people" del servidor https://swapi.co/api/ para
    // obtener los personajes.
    // Cada Call hace una petición HTTP asíncrona al servidor.
    @GET("people")
    Call<Respuesta> obtenerPersonaje();
}
```

## Paso 4: Creación de la clase _Cliente_
Creamos una clase en la que construiremos el objeto _Retrofit_.
```
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
```

## Paso 5: Creación de la clase _Personaje_
Creamos un _POJO_ con los datos que queremos obtener del personaje (nombre y género).
```
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
```

## Paso 6: Creación de la clase _Respuesta_
Creamos una clase que obtiene el array results del fichero _JSON_.
```
public class Respuesta {
    @SerializedName("results")
    private ArrayList<Personaje> results;

    public ArrayList<Personaje> getResults() {
        return results;
    }
}
```

## Paso 7: Creación de los _layouts_
Creamos los _layouts_ de la aplicación con el _RecyclerView_.

### _activity_main.xml_
```
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
### _elementos_lista.xml_
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/nombretextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:text="nombre"/>
    <TextView
        android:id="@+id/generotextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        tools:text="genero"/>

</LinearLayout>
```

## Paso 8: Creación del adaptador _PersonajeAdapter_
Creamos el adaptador para trabajar con el _RecyclerView_.

```
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
```

## Paso 9: Creación de la clase _MainActivity_
Por último, en la clase _MainActivity_ haremos uso de todas las clases creadas y podremos conectar con el servidor y listar los 
datos obtenidos.
```
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
```
