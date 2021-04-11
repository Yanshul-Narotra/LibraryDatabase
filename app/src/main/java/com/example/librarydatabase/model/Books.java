package com.example.librarydatabase.model;

public class Books {
    private int id;
    private String book_name;
    private String author;
    private int stock;
    private String dateitemadded;

    public Books() {
    }

    public Books(int id, String book_name, String author, int stock, String dateitemadded) {
        this.id = id;
        this.book_name = book_name;
        this.author = author;
        this.stock = stock;
        this.dateitemadded=dateitemadded;
    }

    public Books(String book_name, String author, int stock,String dateitemadded) {
        this.book_name = book_name;
        this.author = author;
        this.stock = stock;
        this.dateitemadded=dateitemadded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDateitemadded() {
        return dateitemadded;
    }

    public void setDateitemadded(String dateitemadded) {
        this.dateitemadded = dateitemadded;
    }
}
