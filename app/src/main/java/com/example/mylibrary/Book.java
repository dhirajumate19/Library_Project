package com.example.mylibrary;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Book
{

    private String title, type;
    private int total = 0, available = 0, id;
    private List<Integer> unit = new ArrayList<Integer>();
    private List<Integer> fine = new ArrayList<Integer>();
    private int left_fine;
    private String name, email;
    public Book() {
    }

    public Book(String title, String type, int total, int id) {
        this.title = title;
        this.type = type;
        this.total = total;
        this.id = id;
        this.available=total;




    }

    public Book(View view) {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getUnit() {
        return unit;
    }

    public void setUnit(List<Integer> unit) {
        this.unit = unit;
    }

    public int getLeft_fine() {
        return left_fine;
    }

    public void setLeft_fine(int left_fine) {
        this.left_fine = left_fine;
    }
    public void setFine(List<Integer> fine) {
        this.fine = fine;
    }
    public List<Integer> getFine() {
        return fine;
    }



    public Book(String name, String email, int enroll, int card, int type) {
        this.name = name;
        this.email = email;
//      this.enroll = enroll;
//        this.card = card;
//        this.type = type;
       }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<Integer> getBook() {
//        return book;
//    }
//
//    public void setBook(List<Integer> book) {
//        this.book = book;
//    }


//    public List<Integer> getRe() {
//        return re;
//    }
//
//    public void setRe(List<Integer> re) {
//        this.re = re;
//    }
//
//    public List<Timestamp> getDate() {
//        return date;
//    }

//    public void setDate(List<Timestamp> date) {
//        this.date = date;
//    }
//
//    public int getEnroll() {
//        return enroll;
//    }
//
//    public void setEnroll(int enroll) {
//        this.enroll = enroll;
//    }
//
//    public int getCard() {
//        return card;
//    }
//
//    public void setCard(int card) {
//        this.card = card;
//}
}

