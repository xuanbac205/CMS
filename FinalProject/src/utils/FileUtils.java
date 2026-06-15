/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import models.Student;
import list.StudentList;
import node.StudentNode;

import java.io.*;
/**
 *
 * @author Admin
 */
public class FileUtils {
    public static void saveStudent(StudentList list,
                                   String fileName) {
        if (list == null) {
            System.out.println("Save failed: student list is null.");
            return;
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("Save failed: file name is empty.");
            return;
        }

        File file = new File(fileName.trim());
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (PrintWriter pw =
//                     new PrintWriter(new FileWriter(fileName))) {
                     new PrintWriter(new FileWriter(file))) {

            StudentNode p = list.getHead();

            while (p != null) {

                Student s = p.info;

                pw.println(
                        s.getScode() + "|" +
                        s.getName() + "|" +
                        s.getByear()
                );

                p = p.next;
            }

            System.out.println("Saved successfully.");

        } catch (Exception e) {
            System.out.println("Save failed.");
        }
    }

    public static void loadStudent(StudentList list,
                                   String fileName) {
        if (list == null) {
            System.out.println("Load failed: student list is null.");
            return;
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("Load failed: file name is empty.");
            return;
        }

        File file = new File(fileName.trim());
        if (!file.exists()) {
            System.out.println("Load failed: file not found.");
            return;
        }

        try (BufferedReader br =
//                     new BufferedReader(new FileReader(fileName))) {
                     new BufferedReader(new FileReader(file))) {

            String line;
            int lineNumber = 0;
            list.clear();

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }
                String[] arr = line.split("\\|");

                if (arr.length != 3) {
                    System.out.println("Line " + lineNumber + " invalid: must have 3 fields.");
                    continue;
                }

                String scode = arr[0].trim();
                String name = arr[1].trim();
                int byear;

                try {
                    byear = Integer.parseInt(arr[2].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Line " + lineNumber + " invalid: birth year must be an integer.");
                    continue;
                }

                try {
//                    Student s = new Student(
//                            arr[0],
//                            arr[1],
//                            Integer.parseInt(arr[2])
//                    );
                    Student s = new Student(scode, name, byear);
                    list.addLast(s);
                } catch (IllegalArgumentException e) {
                    System.out.println("Line " + lineNumber + " invalid: " + e.getMessage());
                    continue;
                }

            }

            System.out.println("Loaded successfully.");

        } catch (Exception e) {
            System.out.println("Load failed.");
        }
    }
}
