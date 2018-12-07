package com.example.josecubillas.bookwishlist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class FavoritesActivity extends Activity implements AdapterView.OnItemClickListener {

    List<FavoriteClass> favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //Se carga la lista de Favoritos.
        favorites = FavoriteClass.listAll(FavoriteClass.class);
        generateList();
    }

    public void generateList() {
        FavoritesAdapter adaptador = new FavoritesAdapter(this, R.layout.favorite_item, favorites);
        ListView list = findViewById(R.id.FavoritesList);
        list.setAdapter(adaptador);
        list.setOnItemClickListener(this);
    }

    public void removeFavorite(View v){
        View parentRow = (View) v.getParent();
        ListView listView = (ListView) parentRow.getParent();

        final int position = listView.getPositionForView(parentRow);

        FavoriteClass favoriteToDelete = favorites.get(position);
        favoriteToDelete.delete();

        favorites = FavoriteClass.listAll(FavoriteClass.class);

        generateList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
