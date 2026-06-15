package node;

import models.Course;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author PC
 */
public class CourseNode {

    public Course info;
    public CourseNode next;

    public CourseNode() {
    }

    public CourseNode(Course info, CourseNode next) {
        this.info = info;
        this.next = next;
    }

    public CourseNode(Course x) {
        this(x, null);
    }
}
