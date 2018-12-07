package com.example.josecubillas.bookwishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class BooksActivity extends Activity implements AdapterView.OnItemClickListener {

    public SingletonVolley volley;
    public RequestQueue colaPeticiones;
    TextView categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        volley = SingletonVolley.getInstance(getApplicationContext());
        colaPeticiones = volley.getRequestQueue();

        //Nuestro Intent tenía información necesaria para agregar información a la vista y para realizar la búsqueda de los libros.
        Intent myIntent = getIntent();

        //Recuperamos el Name.
        String name = myIntent.getStringExtra("categoryName");
        //Recuperamos el Nicename.
        String nicename = myIntent.getStringExtra("categoryNicename");

        //Seteamos en el TextView el nombre de la categoría para mostrarlo en la lista de libros.
        categoryName = findViewById(R.id.categoryName);
        categoryName.setText(name);

        //Llamamos al método getBooks, en donde realizaremos la llamada API, pasándole como parámetro el nicename del a categoría.
        getBooks(nicename);
    }

    public void AddRequest(Request request) {
        if (request != null) {
            request.setTag("REQUEST_LOGIN");
            if (colaPeticiones == null)
                colaPeticiones = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    6000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            colaPeticiones.add(request);
        }
    }

    public void getBooks(String nicename){
        //URL a la cual voy a realizar la llamada. Concateno el nicename.
        String url = "http://www.etnassoft.com/api/v1/get/?category=" + nicename + "&criteria=most_viewed";

        //Armo el request, indico el método a realizar: GET. Se realiza un StringRequest debido a que el JSON no viene armado correctamente.
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String stringResult) {

                //Debido a que la API posee caracteres impropios de un JSON, los eliminamos.
                stringResult = stringResult.replace("(", "");
                stringResult = stringResult.replace(");", "");

                //Utilizando la librería Gson podemos transformar nuestro JSON en un objeto.
                Gson gson = new Gson();

                //Debemos especificarle a Gson que queremos transformar el JSON en una Lista de Cateogorias.
                Type bookList = new TypeToken<ArrayList<BookClass>>(){}.getType();
                List<BookClass> books = gson.fromJson(stringResult, bookList);

                BooksAdapter adapter = new BooksAdapter(BooksActivity.this, R.layout.activity_books, books);

                ListView list = (ListView)findViewById(R.id.booksList);
                list.setAdapter(adapter);
                list.setOnItemClickListener(BooksActivity.this);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        AddRequest(request);
    }

    public void onItemClick(AdapterView<?> padre, View view, int posicion, long id) {

        BooksAdapter adapter = (BooksAdapter) padre.getAdapter();

        BookClass bookSelected = adapter.getItem(posicion);
        String bookid = Integer.toString(bookSelected.getBookId());

        //Crearemos un nuevo Intent, pasándole el Id del book para generar la vista con todos los detalles del mismo.
        Intent myIntent = new Intent(this, BookDetail.class);

        myIntent.putExtra("bookId", bookid);
        Log.e("bookId", bookid);


        startActivity(myIntent);
    }

}
