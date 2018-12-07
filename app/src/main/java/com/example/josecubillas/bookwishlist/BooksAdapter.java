package com.example.josecubillas.bookwishlist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BooksAdapter extends ArrayAdapter<BookClass> {

    Activity context;
    List<BookClass> books;

    public BooksAdapter(Activity context, int textViewResourceId, List<BookClass> books) {
        super(context, textViewResourceId, books);
        this.context = context;
        this.books = books;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View book_item = inflater.inflate(R.layout.book_item, null);
        TextView title = book_item.findViewById(R.id.title);
        TextView author = book_item.findViewById(R.id.author);

        BookClass book = getItem(position);

        title.setText(book.getTitle());
        author.setText(book.getAuthor());

        return book_item;
    }
}
