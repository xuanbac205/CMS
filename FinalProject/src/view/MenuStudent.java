package view;

import java.time.Year;
import java.util.Scanner;
import list.StudentList;
import list.CourseList;
import list.RegisteringList;
import models.Student;
import models.Course;
import node.RegisteringNode;
import utils.FileUtils;

/**
 * Menu quản lý sinh viên hoàn chỉnh theo đặc tả Assignment 1
 */
public class MenuStudent {

    private static final String STUDENT_FILE = "data/students.txt";
    private Scanner sc;
    private StudentList studentList;

    // Bổ sung thêm tham chiếu tới CourseList và RegisteringList để làm chức năng 2.6 và 2.8
    private CourseList courseList;
    private RegisteringList registeringList;

    // Constructor mặc định hoặc dùng độc lập
    public MenuStudent() {
        this(new Scanner(System.in), new StudentList(), null, null);
    }

    // Constructor tích hợp đầy đủ hệ thống
    public MenuStudent(Scanner sc, StudentList studentList, CourseList courseList, RegisteringList registeringList) {
        this.sc = sc;
        this.studentList = studentList;
        this.courseList = courseList;
        this.registeringList = registeringList;
    }

    public void run() {
        int choice;

        do {
            printStudentMenu();
            choice = inputChoice();

            switch (choice) {
                case 1:
                    loadStudent();
                    break;
                case 2:
                    addStudent();
                    break;
                case 3:
                    studentList.display();
                    break;
                case 4:
                    saveStudent();
                    break;
                case 5:
                    searchStudentByCode();
                    break;
                case 6:
                    deleteStudent();
                    break;
                case 7:
                    searchStudentByName();
                    break;
                case 8:
                    searchRegisteredCoursesByScode();
                    break;
                case 0:
                    System.out.println("Exit Student Menu.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    public static void printStudentMenu() {
        System.out.println("\n===== STUDENT MENU =====");
        System.out.println("1. Load from file");
        System.out.println("2. Add student");
        System.out.println("3. Display");
        System.out.println("4. Save to file");
        System.out.println("5. Search by code");
        System.out.println("6. Delete by code");
        System.out.println("7. Search by name");
        System.out.println("8. Search registered courses by scode");
        System.out.println("0. Exit");
    }

    private void loadStudent() {
        FileUtils.loadStudent(studentList, STUDENT_FILE);
    }

    private void saveStudent() {
        FileUtils.saveStudent(studentList, STUDENT_FILE);
    }

    private void addStudent() {
        String code = inputNonEmptyString("Code: ");

        if (studentList.searchByCode(code) != null) {
            System.out.println("Duplicated code.");
            return;
        }

        String name = inputNonEmptyString("Name: ");
        int byear = inputBirthYear("Birth year: ");

        try {
            Student s = new Student(code, name, byear);
            studentList.addLast(s);
            System.out.println("Added.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchStudentByCode() {
        String code = inputNonEmptyString("Code: ");
        Student s = studentList.searchByCode(code);

        if (s == null) {
            System.out.println("Not found.");
        } else {
            System.out.printf("%-10s %-25s %-6s\n", "Scode", "Name", "Byear");
            System.out.println(s);
        }
    }

    private void searchStudentByName() {
        String name = inputNonEmptyString("Name: ");
        boolean found = studentList.displayByName(name);

        if (!found) {
            System.out.println("Not found.");
        }
    }

    /**
     * Chức năng 2.6: Xóa sinh viên theo mã (scode) Đòi hỏi xóa các bản ghi liên
     * quan trong RegisteringList trước
     */
    private void deleteStudent() {
        String code = inputNonEmptyString("Code: ");
        Student s = studentList.searchByCode(code);

        if (s == null) {
            System.out.println("Not found.");
            return;
        }

        // 1. Tiến hành xóa tất cả bản ghi liên quan trong RegisteringList trước (nếu có tích hợp)
        if (registeringList != null) {
            // Gọi hàm xóa tất cả đăng ký của sinh viên này trong RegisteringList
            // Giả định RegisteringList có hàm deleteByScode(String scode)
            registeringList.deleteByScode(code);
        } else {
            System.out.println("[Warning] RegisteringList is not integrated. Deleting student only.");
        }

        // 2. Xóa sinh viên khỏi danh sách StudentList
        boolean deleted = studentList.deleteByCode(code);
        if (deleted) {
            System.out.println("Deleted successfully (and cleared related registrations).");
        } else {
            System.out.println("Delete failed.");
        }
    }

    /**
     * Chức năng 2.8: Tìm kiếm các khóa học mà sinh viên đã đăng ký bằng scode
     */
    private void searchRegisteredCoursesByScode() {
        String code = inputNonEmptyString("Code: ");
        Student s = studentList.searchByCode(code);

        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student info: " + s);

        if (registeringList == null || courseList == null) {
            System.out.println("Error: System not fully integrated (Missing CourseList or RegisteringList).");
            return;
        }

        System.out.println("\n--- Registered Courses List ---");
        System.out.printf("%-10s | %-10s | %-20s | %-8s | %-8s | %-6s | %-10s | %-8s\n",
                "Ccode", "Scode", "Sname", "Semester", "Year", "Seats", "Regist", "Price");

        boolean hasRegistration = false;
        RegisteringNode current = registeringList.getHead(); // Giả định RegisteringList có getHead()

        while (current != null) {
            // Nếu tìm thấy bản ghi đăng ký trùng mã sinh viên (scode)
            if (current.info.getScode().equalsIgnoreCase(code)) {
                String targetCcode = current.info.getCcode();

                // Tìm kiếm thông tin môn học/lớp học tương ứng trong CourseList
                // Giả định CourseList có hàm searchByCcode hoặc findNode trả về CourseNode/Course
                Course course = courseList.searchCourseByCcode(targetCcode);

                if (course != null) {
                    System.out.println(course);
                    hasRegistration = true;
                }
            }
            current = current.next;
        }

        if (!hasRegistration) {
            System.out.println("This student hasn't registered for any courses yet.");
        }
    }

    private int inputChoice() {
        while (true) {
            try {
                String value = inputNonEmptyString("Your choice: ");
                int choice = Integer.parseInt(value);
                if (choice >= 0 && choice <= 8) {
                    return choice;
                }
                System.out.println("Choice must be from 0 to 8.");
            } catch (NumberFormatException e) {
                System.out.println("Choice must be an integer.");
            }
        }
    }

    private String inputNonEmptyString(String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Cannot be empty.");
        }
    }

    private int inputBirthYear(String message) {
        int currentYear = Year.now().getValue();

        while (true) {
            try {
                System.out.print(message);
                int year = Integer.parseInt(sc.nextLine().trim());

                if (currentYear - year >= 18) {
                    return year;
                }

                System.out.println("Student must be at least 18 years old.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
            }
        }
    }
}
