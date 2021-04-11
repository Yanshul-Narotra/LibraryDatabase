package com.example.librarydatabase.model;

public class Students {
    private int id;
    private int student_id;
    private String student_name;
    private String Date_issued;
    private String book_name;
    private int debt=0;

    public Students() {
        debt=0;
    }

    public Students(int id, int student_id, String student_name, String date_issued, String book_name, int  debt) {
        this.id = id;
        this.student_id = student_id;
        this.student_name = student_name;
        Date_issued = date_issued;
        this.book_name = book_name;
        this.debt = debt;
    }

    public Students(int student_id, String student_name, String date_issued, String book_name, int  debt) {
        this.student_id = student_id;
        this.student_name = student_name;
        Date_issued = date_issued;
        this.book_name = book_name;
        this.debt = debt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getDate_issued() {
        return Date_issued;
    }

    public void setDate_issued(String date_issued) {
        Date_issued = date_issued;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int  getDebt() {
        return debt;
    }

    public void setDebt(int  debt) {
        this.debt = debt;
    }
}
