package models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hachi
 */
public class Course {
    private String ccode;
    private String scode;
    private String sname;
    private String semester;
    private String year;
    private int seats;
    private int registered;
    private double price;

    public Course() {
    }

    public Course(String ccode, String scode, String sname, String semester, String year, int seats, int registered, double price) {
        this.ccode = ccode;
        this.scode = scode;
        this.sname = sname;
        this.semester = semester;
        this.year = year;
        this.seats = seats;
        this.registered = registered;
        this.price = price;
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getRegistered() {
        return registered;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return String.format("%-10s | %-10s | %-20s | %-8s | %-8s | %-6d | %-10d | %-8.2f", 
                ccode, scode, sname, semester, year, seats, registered, price);
    }
    
    public String toFileString() {
        return ccode + "," + scode + "," + sname + "," + semester + "," + year + "," + seats + "," + registered + "," + price;
    }
}
