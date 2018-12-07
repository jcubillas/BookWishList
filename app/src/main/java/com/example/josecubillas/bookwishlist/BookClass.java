package com.example.josecubillas.bookwishlist;

import com.orm.SugarRecord;

import java.util.List;

public class BookClass {

    private int ID;
    private String title;
    private String author;
    private String publisher;
    private int publisher_date;
    private int pages;
    private String language;
    private String cover;

    public BookClass(){}

    public BookClass(int ID, String title, String author, String publisher, int publisher_date, int pages, String language, String cover) {
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publisher_date = publisher_date;
        this.pages = pages;
        this.language = language;
        this.cover = cover;
    }


    public int getBookId() {
        return ID;
    }

    public void setBookId(int bookId) {
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublisher_date() {
        return publisher_date;
    }

    public void setPublisher_date(int publisher_date) {
        this.publisher_date = publisher_date;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

}
