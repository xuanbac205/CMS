package view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import list.CourseList;
import list.RegisteringList;
import list.StudentList;
import models.Course;
import models.Registering;
import models.Student;
import utils.ValidatorRegistering;

public class MenuRegistering {

    private Scanner sc;
    private CourseList courseList;
    private StudentList studentList;
    private RegisteringList registeringList;

    public MenuRegistering() {
        this(new Scanner(System.in));
    }

    public MenuRegistering(Scanner sc) {
        this(sc, new CourseList(sc), new StudentList(), new RegisteringList());
    }

    public MenuRegistering(Scanner sc, CourseList courseList, StudentList studentList, RegisteringList registeringList) {
        this.sc = sc;
        ValidatorRegistering.setScanner(sc);
        this.courseList = courseList;
        this.studentList = studentList;
        this.registeringList = registeringList;
    }

    public void run() {
        int choice;

        do {
            showRegisteringMenu();
            choice = ValidatorRegistering.getInt(
                    "Your choice: ",
                    "Choice must be from 0 to 6.",
                    "Choice must be from 0 to 6.",
                    "Choice must be an integer.",
                    0,
                    6
            );

            switch (choice) {
                case 1:
                    loadRegisterings();
                    break;
                case 2:
                    registerCourse();
                    break;
                case 3:
                    registeringList.display();
                    break;
                case 4:
                    saveRegisterings();
                    break;
                case 5:
                    registeringList.sortByCcodeScode();
                    System.out.println("Sort successfully.");
                    break;
                case 6:
                    updateMark();
                    break;
                case 0:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void showRegisteringMenu() {
        System.out.println("\n========== REGISTERING LIST ==========");
        System.out.println("1. Load data from file");
        System.out.println("2. Register the course");
        System.out.println("3. Display data");
        System.out.println("4. Save registering list to file");
        System.out.println("5. Sort by ccode + scode");
        System.out.println("6. Update mark by ccode + scode");
        System.out.println("0. Exit");
    }

    private void loadRegisterings() {
        String filename = ValidatorRegistering.getString(
                "Input file name: ",
                "Input must not be empty.",
                ".+"
        );

        registeringList.loadFromFile(filename, courseList, studentList);
    }

    private void saveRegisterings() {
        String filename = ValidatorRegistering.getString(
                "Output file name: ",
                "Input must not be empty.",
                ".+"
        );
        registeringList.saveToFile(filename);
    }

    private void updateMark() {
        try {
            String ccode = ValidatorRegistering.getString(
                    "Input ccode: ",
                    "Ccode must not be empty.",
                    "\\S+"
            );
            String scode = ValidatorRegistering.getString(
                    "Input scode: ",
                    "Scode must not be empty.",
                    "\\S+"
            );
            double mark = ValidatorRegistering.getDouble(
                    "Input new mark: ",
                    "Mark must be from 0 to 10.",
                    "Mark must be from 0 to 10.",
                    "Input must be a number.",
                    0,
                    10
            );

            registeringList.updateMark(ccode, scode, mark);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void registerCourse() {
        try {
            String ccode = ValidatorRegistering.getString(
                    "Input ccode: ",
                    "Ccode must not be empty.",
                    "\\S+"
            );
            String scode = ValidatorRegistering.getString(
                    "Input scode: ",
                    "Scode must not be empty.",
                    "\\S+"
            );

            Course course = courseList.searchCourseByCcode(ccode);
            if (course == null) {
                System.out.println("Course not found.");
                return;
            }

            Student student = studentList.searchByCode(scode);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }

            if (registeringList.isRegistered(ccode, scode)) {
                System.out.println("This student already registered this course.");
                return;
            }

            if (course.getRegistered() >= course.getSeats()) {
                System.out.println("Course is full.");
                return;
            }

            String today = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            Registering registering = new Registering(ccode, scode, today, 0);
            registeringList.addFirst(registering);
            course.setRegistered(course.getRegistered() + 1);

            System.out.println("Register course successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
