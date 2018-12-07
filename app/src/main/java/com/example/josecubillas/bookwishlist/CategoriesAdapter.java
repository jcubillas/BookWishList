package com.example.josecubillas.bookwishlist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CategoriesAdapter extends ArrayAdapter<CategoryClass> {

    Activity context;
    List<CategoryClass> categories;

    public CategoriesAdapter(Activity context, int textViewResourceId, List<CategoryClass> categories) {
        super(context, textViewResourceId, categories);
        this.context = context;
        this.categories = categories;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View category_item = inflater.inflate(R.layout.category_item, null);
        TextView name = category_item.findViewById(R.id.name);

        CategoryClass category = getItem(position);
        name.setText(category.getName());

        return category_item;
    }
}
