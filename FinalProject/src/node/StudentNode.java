/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package node;


import models.Student;

/**
 *
 * @author Admin
 */
public class StudentNode {

    public Student info;
    public StudentNode next;

    public StudentNode(Student info) {
        this.info = info;
        this.next = null;
    }

}
