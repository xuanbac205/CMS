/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.Year;

/**
 *
 * @author Admin
 */
public class Student {
    private String scode;
    private String name;
    private int byear; 

    public Student(String scode, String name, int byear) {
//        this.scode = scode;
//        this.name = name;
//        this.byear = byear;
        setScode(scode);
        setName(name);
        setByear(byear);
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
//        this.scode = scode;
        if (scode == null || scode.trim().isEmpty()) {
            throw new IllegalArgumentException("Scode must not be empty.");
        }
        this.scode = scode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
//        this.name = name;
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty.");
        }
        this.name = name.trim();
    }

    public int getByear() {
        return byear;
    }

    public void setByear(int byear) {
//        this.byear = byear;
        int currentYear = Year.now().getValue();
        if (currentYear - byear < 18) {
            throw new IllegalArgumentException("Student must be at least 18 years old.");
        }
        this.byear = byear;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-25s %-6d", scode, name, byear);
    }
}
