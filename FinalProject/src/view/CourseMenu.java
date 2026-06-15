package view;

import java.util.Scanner;
import list.CourseList;
import list.RegisteringList;
import list.StudentList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author admin
 */
public class CourseMenu {

    private CourseList cl;
    private StudentList sl;
    private RegisteringList rl;
    private Scanner sc = new Scanner(System.in);

    public CourseMenu(Scanner sc, CourseList courseList, StudentList sl, RegisteringList rl) {
        this.sc = sc;
        this.cl = courseList;
        this.sl = sl;
        this.rl = rl;
    }

    public CourseMenu() {
        this(new Scanner(System.in));
    }

    public CourseMenu(Scanner sc) {
        this(sc, new CourseList(sc));
    }

    public CourseMenu(Scanner sc, CourseList courseList) {
        this.sc = sc;
        this.cl = courseList;
    }

    public void displayCourseMenu() {
        String choice;
        while (true) {
            System.out.println("\n================= COURSE LIST MENU =================");
            System.out.println("1.1. Load data from file (courses.txt)");
            System.out.println("1.2. Input & add to the end");
            System.out.println("1.3. Display data");
            System.out.println("1.4. Save course list to file");
            System.out.println("1.5. Search by ccode");
            System.out.println("1.6. Delete by ccode");
            System.out.println("1.7. Sort by ccode");
            System.out.println("1.8. Input & add to beginning");
            System.out.println("1.9. Add after position k");
            System.out.println("1.10. Delete position k");
            System.out.println("1.11. Search course by name");
            System.out.println("1.12. Search course by ccode & list students");
            System.out.println("0. Exit Course Menu");
            System.out.print("Please choose an option (e.g., 1.1, 1.3, 0): ");

            choice = sc.nextLine().trim();

            switch (choice) {
                case "1.1":
                    cl.loadFromFile();
                    break;
                case "1.2":
                    cl.addEndFromInput();
                    break;
                case "1.3":
                    cl.display();
                    break;
                case "1.4":
                    cl.saveToFile();
                    break;
                case "1.5":
                    cl.searchByCcode();
                    break;
                case "1.6":
                    cl.deleteByCcode();
                    break;
                case "1.7":
                    cl.sortByCcode();
                    break;
                case "1.8":
                    cl.addBeginningFromInput();
                    break;
                case "1.9":
                    cl.addAfterPosK();
                    break;
                case "1.10":
                    cl.deleteAtPosK();
                    break;
                case "1.11":
                    cl.searchByName();
                    break;
                case "1.12":
                    System.out.print("Nhap ccode can tim: ");
                    String ccode12 = sc.nextLine().trim();
                    // Gọi trực tiếp hàm xử lý từ cl, truyền thêm rl và sl vào làm tham số
                    cl.searchCourseAndListStudents(ccode12, rl, sl);
                    break;
                case "0":
                    System.out.println("Exiting Course Menu...");
                    return;
                default:
                    System.out.println("Invalid option! Please try again with notation like 1.1, 1.2...");
            }
        }
    }
}
