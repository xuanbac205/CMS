package utils;


import java.util.Scanner;
import list.CourseList;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author hachi
 */
public class ValidateCourse {

    //them
    public static int validateInt(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Loi: Vui long nhap so nguyen hop le!");
            }
        }
    }

    public static String validateNonEmptyString(Scanner sc, String message, String fieldName) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Loi: " + fieldName + " khong duoc de trong!");
        }
    }
    //
    
    
    public static String validateCcode(Scanner sc, CourseList currentList) {
        while (true) {
            System.out.print("Nhap ccode (Unique String): ");
            String ccode = sc.nextLine().trim();
            if (ccode.isEmpty()) {
                System.out.println("Loi: ccode khong duoc de trong!");
                continue;
            }
            if (currentList.findNode(ccode) != null) {
                System.out.println("Loi: ccode da ton tai! Vui long nhap ma khac.");
                continue;
            }
            return ccode;
        }
    }

    public static int validateSeats(Scanner sc) {
        while (true) {
            try {
                System.out.print("Nhap so cho ngoi (seats - Integer): ");
                int seats = Integer.parseInt(sc.nextLine().trim());
                if (seats < 0) {
                    System.out.println("Loi: So cho ngoi khong duoc la so am!");
                    continue;
                }
                return seats;
            } catch (NumberFormatException e) {
                System.out.println("Loi: seats phai la so nguyen hop le!");
            }
        }
    }

    public static int validateRegistered(Scanner sc, int seats) {
        while (true) {
            try {
                System.out.print("Nhap so cho da dang ky (registered - Integer): ");
                int registered = Integer.parseInt(sc.nextLine().trim());
                if (registered < 0) {
                    System.out.println("Loi: So cho dang ky khong duoc am!");
                    continue;
                }
                if (registered > seats) {
                    System.out.println("Loi Validate: registered khong duoc lon hon seats (" + seats + ")!");
                    continue;
                }
                return registered;
            } catch (NumberFormatException e) {
                System.out.println("Loi: registered phai la so nguyen hop le!");
            }
        }
    }

    public static double validatePrice(Scanner sc) {
        while (true) {
            try {
                System.out.print("Nhap gia hoc phi (price - Double): ");
                double price = Double.parseDouble(sc.nextLine().trim());
                if (price < 0) {
                    System.out.println("Loi: Gia khong duoc am!");
                    continue;
                }
                return price;
            } catch (NumberFormatException e) {
                System.out.println("Loi: price phai la so thuc hop le!");
            }
        }
    }
}
