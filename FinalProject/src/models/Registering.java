package models;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author admin
 */
public class Registering {

    private String ccode;
    private String scode;
    private Date bdate;
    private double mark;
    private int state;

    public Registering(String ccode, String scode, String bdate, double mark) {
        setCcode(ccode);
        setScode(scode);
        setBdate(bdate);
        setMark(mark);
    }

    public Registering() {
    }

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        if (ccode == null || ccode.trim().isEmpty()) {
            throw new IllegalArgumentException("ccode is null or is empty");
        }
        this.ccode = ccode.trim();
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        if (scode == null || scode.trim().isEmpty()) {
            throw new IllegalArgumentException("scode is null or is empty");
        }
        this.scode = scode.trim();
    }

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        if (bdate == null || bdate.trim().isEmpty()) {
            throw new IllegalArgumentException("bdate is null or empty");
        }

        try {
            this.bdate = dateFormat.parse(bdate.trim());
        } catch (ParseException e) {
            throw new IllegalArgumentException("bdate must be in format dd/MM/yyyy");
        }
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        if (mark < 0 || mark > 10) {
            throw new IllegalArgumentException("Mark must be from 0 to 10.");
        }
        this.mark = mark;
        this.state = mark >= 5 ? 1 : 0;
    }

    public int getState() {
        return state;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%-10s %-10s %-12s %-6.2f %-5d",
                ccode, scode, dateFormat.format(bdate), mark, state);
    }

    public String toFileLine() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return ccode + "|" + scode + "|" + dateFormat.format(bdate) + "|" + mark + "|" + state;
    }
}
