/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.time.Year;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class ValidationStudent {

    private final static Scanner sc = new Scanner(System.in);
    // input String
    public static String inputString(String msg) {

        while (true) {

            System.out.print(msg);

            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Cannot be empty.");
        }
    }
    // valid byear
    public static int inputBirthYear(String msg) {
        boolean valid = false;
        int year = 0;
        int currentYear = Year.now().getValue();

        while (valid == false) {

            try {

                System.out.print(msg);

                year = Integer.parseInt(sc.nextLine());

                if (currentYear - year >= 18) {
                    valid = true;
                    continue;
                }

                System.out.println("Student must be at least 18 years old.");

            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
        return year;
    }
}
