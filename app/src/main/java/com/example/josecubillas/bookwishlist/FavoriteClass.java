package com.example.josecubillas.bookwishlist;

import com.orm.SugarRecord;

public class FavoriteClass extends SugarRecord<FavoriteClass>{

    int ID;
    String title;
    String author;

    public FavoriteClass(){ }

    public FavoriteClass(int id, String title, String author) {
        this.ID = ID;
        this.title = title;
        this.author = author;
    }

    public int getBookId() {
        return ID;
    }

    public void setBookId(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
