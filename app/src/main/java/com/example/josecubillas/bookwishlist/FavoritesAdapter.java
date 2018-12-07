package com.example.josecubillas.bookwishlist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FavoritesAdapter extends ArrayAdapter<FavoriteClass> {

    Activity context;
    List<FavoriteClass> favorites;

    public FavoritesAdapter(Activity context, int textViewResourceId, List<FavoriteClass> favorites) {
        super(context, textViewResourceId, favorites);
        this.context = context;
        this.favorites = favorites;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View favorite_item = inflater.inflate(R.layout.favorite_item, null);
        TextView title = favorite_item.findViewById(R.id.title);
        TextView author = favorite_item.findViewById(R.id.author);

        FavoriteClass favorite = getItem(position);

        title.setText(favorite.getTitle());
        author.setText(favorite.getAuthor());

        return favorite_item;
    }
}
