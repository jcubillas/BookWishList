package com.example.josecubillas.bookwishlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.io.InputStream;
import java.util.List;

public class BookDetail extends Activity {

    public SingletonVolley volley;
    public RequestQueue colaPeticiones;

    FavoriteClass favorite;
    BookClass book = new BookClass();

    TextView title;
    TextView author;
    TextView publisher;
    TextView publisherDate;
    TextView pages;
    TextView language;
    ImageView thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);

        volley = SingletonVolley.getInstance(getApplicationContext());
        colaPeticiones = volley.getRequestQueue();

        Intent myIntent = getIntent();
        String bookId = myIntent.getStringExtra("bookId");

        //Recupero cada input donde completaremos la información del libro.
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        publisher = findViewById(R.id.publisher);
        publisherDate = findViewById(R.id.publisherDate);
        pages = findViewById(R.id.pages);
        language = findViewById(R.id.language);
        thumbnail = findViewById(R.id.thumbnail);

        getBookDetails(bookId);
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

    public void getBookDetails(final String bookId){
        String url = "http://www.etnassoft.com/api/v1/get/?id=" + bookId;
        Log.e("url", url);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String stringResult) {

                stringResult = stringResult.replace("([", "");
                stringResult = stringResult.replace("]);", "");

                Gson gson = new Gson();
                book = gson.fromJson(stringResult, BookClass.class);

                //La información recuperada del JSON es agregada a la vista.
                title.setText(book.getTitle());
                author.setText(book.getAuthor());
                publisher.setText(book.getPublisher());
                publisherDate.setText(Integer.toString(book.getPublisher_date()));
                pages.setText(Integer.toString(book.getPages()));
                language.setText(book.getLanguage());

                //Método para cargar imágenes desde una URL
                new DownloadImageTask(thumbnail).execute(book.getCover());

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

        AgregarRequest(request);
    }

    //Código que permite mostrar una imagen desde una URL
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public void addToFavorites(View v) {
        FavoriteClass favorite = new FavoriteClass(book.getBookId(), book.getTitle(), book.getAuthor());
        favorite.save();
        Toast.makeText(this, "The book" + book.getTitle() + "was saved in your favorites list.", Toast.LENGTH_SHORT).show();
    }
}
