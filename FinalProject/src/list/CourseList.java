package list;

import models.Student;
import node.RegisteringNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import models.Course;
import node.CourseNode;
import utils.ValidateCourse;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author hachi
 */
public class CourseList {

    CourseNode head, tail;
    private Scanner sc = new Scanner(System.in);

    public CourseList() {
        head = null;
        tail = null;
    }

    public CourseList(Scanner sc) {
        head = null;
        tail = null;
        this.sc = sc;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = tail = null;
    }

    int size() {
        CourseNode p = head;
        int count = 0;
        while (p != null) {
            count++;
            p = p.next;
        }
        return count;
    }

    void addFirst(Course x) {
        if (isEmpty() == true) {
            if (isEmpty() == true) {
                head = tail = new CourseNode(x, null);
            } else {
                CourseNode p = new CourseNode(x, null);
                tail.next = p;
                tail = p;
            }
        } else {
            CourseNode P = new CourseNode(x, null);
            P.next = head;
            head = P;
        }
    }

    void insertAfter(CourseNode p, Course x) {
        if (p == tail) {
            addLast(x);
        } else {
            CourseNode q = new CourseNode(x);
            q.next = p.next;
            p.next = q;
        }
    }

    void addLast(Course x) {
        if (isEmpty() == true) {
            head = tail = new CourseNode(x, null);
        } else {
            CourseNode q = new CourseNode(x, null);
            tail.next = q;
            tail = q;
        }
    }

    public CourseNode findNode(String ccode) {
        CourseNode curr = head;
        while (curr != null) {
            if (curr.info.getCcode().equalsIgnoreCase(ccode)) {
                return curr;
            }
            curr = curr.next;
        }
        return null;
    }

    public Course searchCourseByCcode(String ccode) {
        CourseNode found = findNode(ccode);
        if (found == null) {
            return null;
        }
        return found.info;
    }

    public Course inputCourseFlow() {
        System.out.println("\n--- NHAP THONG TIN MON HOC MOI ---");

        String ccode = ValidateCourse.validateCcode(sc, this);

        String scode = ValidateCourse.validateNonEmptyString(
                sc,
                "Nhap ma mon hoc (scode): ",
                "scode"
        );

        String sname = ValidateCourse.validateNonEmptyString(
                sc,
                "Nhap ten mon hoc (sname): ",
                "sname"
        );

        String semester = ValidateCourse.validateNonEmptyString(
                sc,
                "Nhap hoc ky (semester): ",
                "semester"
        );

        String year = ValidateCourse.validateNonEmptyString(
                sc,
                "Nhap nam hoc (year): ",
                "year"
        );

        int seats = ValidateCourse.validateSeats(sc);
        int registered = ValidateCourse.validateRegistered(sc, seats);
        double price = ValidateCourse.validatePrice(sc);

        return new Course(ccode, scode, sname, semester, year, seats, registered, price);
    }

    //--------------------------------------------------------------------------
    // 1.1. Load data from file
    // t sua ham nay nha
    public void loadFromFile() {
        clear();

        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] p = line.split(",");

                if (p.length != 8) {
                    System.out.println("Line " + lineNumber + " invalid: must have 8 fields.");
                    continue;
                }

                String ccode = p[0].trim();
                String scode = p[1].trim();
                String sname = p[2].trim();
                String semester = p[3].trim();
                String year = p[4].trim();

                if (ccode.isEmpty() || scode.isEmpty() || sname.isEmpty()
                        || semester.isEmpty() || year.isEmpty()) {
                    System.out.println("Line " + lineNumber + " invalid: text fields must not be empty.");
                    continue;
                }

                if (findNode(ccode) != null) {
                    System.out.println("Line " + lineNumber + " invalid: duplicated ccode.");
                    continue;
                }

                int seats;
                int registered;
                double price;

                try {
                    seats = Integer.parseInt(p[5].trim());
                    registered = Integer.parseInt(p[6].trim());
                    price = Double.parseDouble(p[7].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Line " + lineNumber + " invalid: seats, registered, or price format is wrong.");
                    continue;
                }

                if (seats < 0) {
                    System.out.println("Line " + lineNumber + " invalid: seats must be >= 0.");
                    continue;
                }

                if (registered < 0) {
                    System.out.println("Line " + lineNumber + " invalid: registered must be >= 0.");
                    continue;
                }

                if (registered > seats) {
                    System.out.println("Line " + lineNumber + " invalid: registered must be <= seats.");
                    continue;
                }

                if (price < 0) {
                    System.out.println("Line " + lineNumber + " invalid: price must be >= 0.");
                    continue;
                }

                Course c = new Course(ccode, scode, sname, semester, year, seats, registered, price);
                addLast(c);
            }

            System.out.println(">>> Load data from courses.txt successfully!");

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // 1.2. Thêm vào cuối danh sách 
    public void addEndFromInput() {
        Course c = inputCourseFlow(); // Gọi hàm nhập tại chính lớp List này
        if (c != null) {
            addLast(c);
            System.out.println(">>> Da them khoa hoc vao cuoi danh sach.");
        }
    }

    // 1.3. Display data
    public void display() {
        if (isEmpty()) {
            System.out.println("Danh sach khoa hoc trong.");
            return;
        }
        System.out.format("%-10s | %-10s | %-20s | %-8s | %-8s | %-6s | %-10s | %-8s\n",
                "Ccode", "Scode", "Subject Name", "Semester", "Year", "Seats", "Registered", "Price");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        CourseNode curr = head;
        while (curr != null) {
            System.out.println(curr.info); // Gọi hàm toString() của lớp Course
            curr = curr.next;
        }
    }

    // 1.4. Save course list to file
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("courses.txt"))) { // Ghi ra file courses.txt 
            CourseNode curr = head;
            while (curr != null) {
                pw.println(curr.info.toFileString());
                curr = curr.next;
            }
            System.out.println(">>> Da luu danh sach vao file courses.txt.");
        } catch (Exception e) {
            System.out.println("Loi ghi file: " + e.getMessage());
        }
    }

    // 1.5. Search by ccode
    public void searchByCcode() {
        System.out.print("Nhap ccode can tim: ");
        String ccode = sc.nextLine().trim(); // Nhận ccode từ bàn phím 
        CourseNode found = findNode(ccode);
        if (found != null) {
            System.out.println("Ket qua tim thay: " + found.info);
        } else {
            System.out.println("Khong tim thay khoa hoc (Not found)!");
        }
    }

    // 1.6. Delete by ccode 
    public void deleteByCcode() {
        System.out.print("Nhap ccode can xoa: ");
        String ccode = sc.nextLine().trim(); // Nhập ccode 
        CourseNode foundNode = findNode(ccode);

        if (foundNode == null) {
            System.out.println("Khong tim thay khoa hoc.");
            return;
        }
        if (foundNode.info.getRegistered() > 0) { // Điều kiện: registered phải bằng 0 mới được xóa 
            System.out.println("Khong the xoa! So luong sinh vien dang ky (registered) phai bang 0.");
            return;
        }

        // Tiến hành xóa Node khỏi Linked List
        if (head.info.getCcode().equalsIgnoreCase(ccode)) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
            System.out.println(">>> Xoa thanh cong môn học.");
            return;
        }

        CourseNode curr = head;
        while (curr.next != null) {
            if (curr.next.info.getCcode().equalsIgnoreCase(ccode)) {
                if (curr.next == tail) {
                    tail = curr;
                }
                curr.next = curr.next.next;
                System.out.println(">>> Xoa thanh cong mon học.");
                return;
            }
            curr = curr.next;
        }
    }

    public void sortByCcode() {
        if (isEmpty() || head.next == null) {
            return;
        }
        boolean swapped;
        do {
            swapped = false;
            CourseNode curr = head;
            while (curr.next != null) {
                if (curr.info.getCcode().compareToIgnoreCase(curr.next.info.getCcode()) > 0) {
                    Course temp = curr.info;
                    curr.info = curr.next.info;
                    curr.next.info = temp;
                    swapped = true;
                }
                curr = curr.next;
            }
        } while (swapped);
        System.out.println(">>> Da sap xep tang dan theo ccode.");
        display();
    }

    //1.8 
    public void addBeginningFromInput() {
        Course c = inputCourseFlow();
        if (c != null) {
            addFirst(c);
            System.out.println(">>> Da them khoa hoc vao dau danh sach.");
        }
    }

    // 1.9. Add after position k 
    public void addAfterPosK() {
//        System.out.print("Nhap vi tri k: ");
        //
        int k = ValidateCourse.validateInt(sc, "Nhap vi tri k: ");
        //
        Course c = inputCourseFlow();
        if (c == null) {
            return;
        }

        if (k < 0) {
            addFirst(c);
            return;
        }
        CourseNode current = head;
        int index = 0;
        while (current != null && index < k) {
            current = current.next;
            index++;
        }
        if (current != null) {
            CourseNode newNode = new CourseNode(c);
            newNode.next = current.next;
            current.next = newNode;
            if (current == tail) {
                tail = newNode;
            }
            System.out.println(">>> Da them vao sau vi tri " + k + ".");
        } else {
            addLast(c);
            System.out.println(">>> Vi tri k lon hon do dai list. Da tu dong them vao cuoi.");
        }
    }

    // 1.10. Delete position k 
    public void deleteAtPosK() {
//        System.out.print("Nhap vi tri k can xoa: ");
        //
        int k = ValidateCourse.validateInt(sc, "Nhap vi tri k can xoa: ");
        //
        if (isEmpty() || k < 0 || k >= size()) {
            System.out.println("Vi tri khong hop le!");
            return;
        }
        if (k == 0) {
            head = head.next;
            if (head == null) {
                tail = null;
            }
            System.out.println(">>> Da xoa tai vi tri " + k + ".");
            return;
        }
        CourseNode current = head;
        CourseNode prev = null;
        int index = 0;
        while (current != null && index < k) {
            prev = current;
            current = current.next;
            index++;
        }
        if (current != null) {
            prev.next = current.next;
            if (current == tail) {
                tail = prev;
            }
            System.out.println(">>> Da xoa tai vi tri " + k + ".");
        }
    }

    // 1.11. Search course by name
    public void searchByName() {
        System.out.print("Nhap ten mon hoc (sname) can tim: ");
        String name = sc.nextLine().trim().toLowerCase();
        CourseNode curr = head;
        boolean found = false;
        while (curr != null) {
            if (curr.info.getSname().toLowerCase().contains(name)) {
                System.out.println(curr.info);
                found = true;
            }
            curr = curr.next;
        }
        if (!found) {
            System.out.println("Khong tim thay khoa hoc nao thich hop (Not found).");
        }
    }
    
    // 1.12. Search course by ccode & list students
    public void searchCourseAndListStudents(String ccode, RegisteringList rl, StudentList sl) {
        // 1. Tìm kiếm khóa học bằng ccode có sẵn trong class
        CourseNode foundNode = findNode(ccode);
        if (foundNode == null) {
            System.out.println("Khong tim thay khoa hoc (Not found)!");
            return;
        }

        // 2. In thông tin khóa học tìm thấy
        System.out.println("\n--- Thong tin khoa hoc ---");
        System.out.format("%-10s | %-10s | %-20s | %-8s | %-8s | %-6s | %-10s | %-8s\n",
                "Ccode", "Scode", "Subject Name", "Semester", "Year", "Seats", "Registered", "Price");
        System.out.println(foundNode.info);

        // 3. Duyệt danh sách đăng ký để tìm sinh viên khớp với ccode này
        System.out.println("\n--- Danh sach sinh vien dang ky khoa hoc nay ---");
        boolean hasStudent = false;
        RegisteringNode rNode = rl.getHead(); // Hàm getHead() lấy từ RegisteringList

        while (rNode != null) {
            if (rNode.info.getCcode().equalsIgnoreCase(ccode)) {
                // Đã tìm thấy mã đăng ký khớp ccode -> Lấy scode đi tìm thông tin chi tiết sinh viên
                Student student = sl.searchByCode(rNode.info.getScode());
                if (student != null) {
                    if (!hasStudent) {
                        System.out.printf("%-10s %-25s %-6s\n", "Scode", "Name", "Byear");
                    }
                    System.out.println(student);
                    hasStudent = true;
                }
            }
            rNode = rNode.next;
        }

        if (!hasStudent) {
            System.out.println("Chua co sinh vien nao dang ky khoa hoc nay.");
        }
    }
}
