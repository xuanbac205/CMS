/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package list;

import node.StudentNode;
import models.Student;

/**
 *
 * @author Admin
 */
public class StudentList {
    // field
    StudentNode head;
    StudentNode tail;
    // contructor
    public StudentList() {
        head = tail = null;
    }
    // check Empty
    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = tail = null;
    }

    // add Last
    public void addLast(Student x) {
//        StudentNode p = new StudentNode(x);
//        if (isEmpty()) {
//            head = tail = p;
//            return;
//        }
//
//        tail.next = p;
//        tail = p;
        if (x == null) {
            throw new IllegalArgumentException("Student must not be null.");
        }
        if (searchByCode(x.getScode()) != null) {
            throw new IllegalArgumentException("Duplicated scode.");
        }
        StudentNode p = new StudentNode(x);
        if (isEmpty()) {
            head = tail = p;
            return;
        }

        tail.next = p;
        tail = p;
    }
    // display List Student
    public void display() {
        if (isEmpty()) {
            System.out.println("List isEmpty.");
            return;
        }

        StudentNode p = head;
//        System.out.println("");
        System.out.printf("%-10s %-25s %-6s\n", "Scode", "Name", "Byear");
        while (p != null) {
//            System.out.print(p.info + " ");
            System.out.println(p.info);
            p = p.next;
        }
//        System.out.println("");
    }
    // search By Code
    public Student searchByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        StudentNode p = head;

        while (p != null) {
            if (p.info.getScode().equalsIgnoreCase(code.trim())) {
                return p.info;
            }
            p = p.next;
        }
        return null;
    }
    // search by name
    public Student searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        StudentNode p = head;

        while (p != null) {
            if (p.info.getName().equalsIgnoreCase(name.trim())) {
                return p.info;
            }
            p = p.next;
        }
        return null;
    }

    public boolean displayByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        boolean found = false;
        StudentNode p = head;
        while (p != null) {
            if (p.info.getName().toLowerCase().contains(name.trim().toLowerCase())) {
                if (!found) {
                    System.out.printf("%-10s %-25s %-6s\n", "Scode", "Name", "Byear");
                }
                System.out.println(p.info);
                found = true;
            }
            p = p.next;
        }
        return found;
    }

    // delete by code
    public boolean deleteByCode(String code) {

        if (code == null || code.trim().isEmpty()) {
            return false;
        }
        if (isEmpty()) {
            return false;
        }
        if (head.info.getScode().equalsIgnoreCase(code.trim())) {
            head = head.next;

            if (head == null) {
                tail = null;
            }

            return true;
        }
        StudentNode prev = head;
        StudentNode cur = head.next;

        while (cur != null) {

            if (cur.info.getScode().equalsIgnoreCase(code.trim())) {

                prev.next = cur.next;

                if (cur == tail) {
                    tail = prev;
                }

                return true;
            }

            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    public StudentNode getHead() {
        return head;
    }
}
