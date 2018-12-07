package com.example.josecubillas.bookwishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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



public class CategoriesActivity extends Activity implements AdapterView.OnItemClickListener{

    public SingletonVolley volley;
    public RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        volley = SingletonVolley.getInstance(getApplicationContext());
        colaPeticiones = volley.getRequestQueue();

        getCategories();
    }

    public void AgregarRequest(Request request) {
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

    public void getCategories(){
        //URL a la cual voy a realizar la llamada.
        String url = "http://www.etnassoft.com/api/v1/get/?get_categories=all";

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
                Type categoryList = new TypeToken<ArrayList<CategoryClass>>(){}.getType();
                List<CategoryClass> categories = gson.fromJson(stringResult, categoryList);

                CategoriesAdapter adapter = new CategoriesAdapter(CategoriesActivity.this, R.layout.activity_categories, categories);

                ListView list = (ListView)findViewById(R.id.categoriesList);
                list.setAdapter(adapter);
                list.setOnItemClickListener(CategoriesActivity.this);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Log.e("ErrorLlamado", volleyError.toString());
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Log.e("ErrorLlamado", volleyError.toString());
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Log.e("ErrorLlamado", volleyError.toString());
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        AgregarRequest(request);
    }

    public void onItemClick(AdapterView<?> padre, View view, int posicion, long id) {

        CategoriesAdapter adapter = (CategoriesAdapter) padre.getAdapter();

        CategoryClass categorySelected = adapter.getItem(posicion);

        //Crearemos un nuevo Intent, pasándole el Name y el Nicename de la categoria para 1) Identificar la vista y 2) Buscar los libros asociados.
        Intent myIntent = new Intent(this, BooksActivity.class);

        myIntent.putExtra("categoryName", categorySelected.getName());
        myIntent.putExtra("categoryNicename", categorySelected.getNicename());

        startActivity(myIntent);
    }

}
