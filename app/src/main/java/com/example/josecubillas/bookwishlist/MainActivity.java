package com.example.josecubillas.bookwishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //OnClick del botón "Go to Categories". Genera un Intent. Redirecciona al activity de Categorias.
    public void goToCategories(View v){
        Intent categoriesIntent = new Intent(MainActivity.this, CategoriesActivity.class);
        startActivity(categoriesIntent);
    }

    //OnClick del botón "Go to Favorites". Genera un Intent. Redirecciona al activity de Favorites.
    public void goToFavorites(View v){
        Intent favoritesIntent = new Intent(MainActivity.this, FavoritesActivity.class);
        startActivity(favoritesIntent);
    }
}
