package view;

import java.util.Scanner;
import list.CourseList;
import list.RegisteringList;
import list.StudentList;

public class MainMenu {

    private Scanner sc;
    private CourseMenu courseMenu;
    private MenuStudent studentMenu;
    private MenuRegistering registeringMenu;
    private CourseList courseList;
    private StudentList studentList;
    private RegisteringList registeringList;

    public MainMenu() {
        sc = new Scanner(System.in);
        courseList = new CourseList(sc);
        studentList = new StudentList();
        registeringList = new RegisteringList();
        courseMenu = new CourseMenu(sc, courseList, studentList, registeringList);
        studentMenu = new MenuStudent(sc, studentList,courseList,registeringList);
        registeringMenu = new MenuRegistering(sc, courseList, studentList, registeringList);
    }

    public void run() {
        int choice;

        do {
            showMainMenu();
            choice = inputChoice();

            switch (choice) {
                case 1:
                    courseMenu.displayCourseMenu();
                    break;
                case 2:
                    studentMenu.run();
                    break;
                case 3:
                    registeringMenu.run();
                    break;
                case 0:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void showMainMenu() {
        System.out.println("\n========== COURSE MANAGEMENT SYSTEM ==========");
        System.out.println("1. Course list");
        System.out.println("2. Student list");
        System.out.println("3. Registering list");
        System.out.println("0. Exit");
    }

    private int inputChoice() {
        while (true) {
            try {
                System.out.print("Your choice: ");
                int choice = Integer.parseInt(sc.nextLine().trim());

                if (choice >= 0 && choice <= 3) {
                    return choice;
                }

                System.out.println("Choice must be from 0 to 3.");
            } catch (NumberFormatException e) {
                System.out.println("Choice must be an integer.");
            }
        }
    }
}
