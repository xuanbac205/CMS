package list;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import models.Registering;
import node.RegisteringNode;

/**
 * Lớp quản lý danh sách đăng ký học phần sử dụng Linked List
 */
public class RegisteringList {

    private RegisteringNode head;

    public RegisteringList() {
        head = null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
    }

    public void addFirst(Registering x) {
        if (x == null) {
            throw new IllegalArgumentException("Register must not null");
        }
        if (isRegistered(x.getCcode(), x.getScode())) {
            throw new IllegalArgumentException("Ccode or scode already exist");
        }
        RegisteringNode newNode = new RegisteringNode(x);
        newNode.next = head;
        head = newNode;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Registering list is empty.");
            return;
        }

        System.out.printf("%-10s %-10s %-12s %-6s %-5s\n",
                "CCode", "SCode", "BDate", "Mark", "State");

        RegisteringNode p = head;
        while (p != null) {
            System.out.println(p.info);
            p = p.next;
        }
    }

    public Registering search(String ccode, String scode) {
        RegisteringNode p = head;
        if (ccode == null || ccode.trim().isEmpty() || scode == null || scode.trim().isEmpty()) {
            throw new IllegalArgumentException("Ccode or scode is null");
        }
        while (p != null) {
            if (p.info.getCcode().equalsIgnoreCase(ccode)
                    && p.info.getScode().equalsIgnoreCase(scode)) {
                return p.info;
            }
            p = p.next;
        }

        return null;
    }

    public boolean isRegistered(String ccode, String scode) {
        return search(ccode, scode) != null;
    }

    public boolean updateMark(String ccode, String scode, double newMark) {
        if (ccode == null || ccode.trim().isEmpty() || scode == null || scode.trim().isEmpty()) {
            throw new IllegalArgumentException("Ccode or scode is null");
        }
        if (newMark < 0 || newMark > 10) {
            System.out.println("Mark must be from 0 to 10.");
            return false;
        }

        Registering r = search(ccode, scode);

        if (r == null) {
            System.out.println("Registering not found.");
            return false;
        }

        r.setMark(newMark);
        System.out.println("Mark updated successfully.");
        return true;
    }

    public void sortByCcodeScode() {
        if (head == null || head.next == null) {
            return;
        }

        for (RegisteringNode i = head; i != null; i = i.next) {
            for (RegisteringNode j = i.next; j != null; j = j.next) {
                if (compareRegistering(i.info, j.info) > 0) {
                    Registering temp = i.info;
                    i.info = j.info;
                    j.info = temp;
                }
            }
        }
    }

    private int compareRegistering(Registering a, Registering b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Register is null");
        }
        int ccodeCompare = a.getCcode().compareToIgnoreCase(b.getCcode());

        if (ccodeCompare != 0) {
            return ccodeCompare;
        }

        return a.getScode().compareToIgnoreCase(b.getScode());
    }

    public void deleteByCcode(String ccode) {
        if (ccode == null || ccode.trim().isEmpty()) {
            throw new IllegalArgumentException("Ccode is null");
        }
        while (head != null && head.info.getCcode().equalsIgnoreCase(ccode)) {
            head = head.next;
        }

        RegisteringNode p = head;

        while (p != null && p.next != null) {
            if (p.next.info.getCcode().equalsIgnoreCase(ccode)) {
                p.next = p.next.next;
            } else {
                p = p.next;
            }
        }
    }

    /**
     * Yêu cầu 2.6: Tự động xóa sạch tất cả các bản ghi đăng ký liên quan của sinh viên theo scode
     * Đã sửa lỗi thuật toán chạy vòng lặp để tránh lỗi bỏ sót node kế tiếp.
     */
    public void deleteByScode(String scode) {
        if (head == null || scode == null || scode.trim().isEmpty()) {
            return;
        }

        // 1. Xóa các node trùng mã scode nằm liên tiếp ngay đầu danh sách
        while (head != null && head.info.getScode().equalsIgnoreCase(scode.trim())) {
            head = head.next;
        }

        // 2. Xóa các node trùng mã scode nằm ở giữa hoặc cuối danh sách
        RegisteringNode p = head;
        while (p != null && p.next != null) {
            if (p.next.info.getScode().equalsIgnoreCase(scode.trim())) {
                p.next = p.next.next; // Bỏ qua node trùng, liên kết trực tiếp tới node tiếp theo
            } else {
                p = p.next; // Chỉ dịch chuyển con trỏ lên khi node tiếp theo không bị xóa
            }
        }
    }

    public void loadFromFile(String filename, CourseList courseList, StudentList studentList) {
        if (filename == null || filename.trim().isEmpty()) {
            System.out.println("Filename is empty.");
            return;
        }

        File f = new File(filename);

        if (!f.exists()) {
            System.out.println("File not found: " + filename);
            return;
        }

        clear();

        try {
            Scanner sc = new Scanner(f);
            int lineNumber = 0;

            while (sc.hasNextLine()) {
                lineNumber++;
                String line = sc.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length != 5) {
                    System.out.println("Line " + lineNumber + " invalid: must have 5 fields.");
                    continue;
                }

                String ccode = parts[0].trim();
                String scode = parts[1].trim();
                String bdate = parts[2].trim();
                String markText = parts[3].trim();
                String stateText = parts[4].trim();
                
                if (courseList == null || studentList == null) {
                    System.out.println("Course list and student list must be loaded first.");
                    sc.close();
                    return;
                }

                if (courseList.searchCourseByCcode(ccode) == null) {
                    System.out.println("Line " + lineNumber + " invalid: course does not exist.");
                    continue;
                }

                if (studentList.searchByCode(scode) == null) {
                    System.out.println("Line " + lineNumber + " invalid: student does not exist.");
                    continue;
                }
                
                if (ccode.isEmpty() || scode.isEmpty() || bdate.isEmpty()) {
                    System.out.println("Line " + lineNumber + " invalid: Missing required fields.");
                    continue;
                }

                double mark;
                try {
                    mark = Double.parseDouble(markText);
                } catch (NumberFormatException e) {
                    System.out.println("Line " + lineNumber + " invalid: mark is not a number.");
                    continue;
                }

                if (mark < 0 || mark > 10) {
                    System.out.println("Line " + lineNumber + " invalid: mark must be from 0 to 10.");
                    continue;
                }

                int state;
                try {
                    state = Integer.parseInt(stateText);
                } catch (NumberFormatException e) {
                    System.out.println("Line " + lineNumber + " invalid: state is not an integer.");
                    continue;
                }

                if (state != 0 && state != 1) {
                    System.out.println("Line " + lineNumber + " invalid: state must be 0 or 1.");
                    continue;
                }

                int expectedState = mark >= 5 ? 1 : 0;
                if (state != expectedState) {
                    System.out.println("Line " + lineNumber + " invalid: state does not match mark.");
                    continue;
                }

                if (isRegistered(ccode, scode)) {
                    System.out.println("Line " + lineNumber + " invalid: duplicated registering.");
                    continue;
                }

                try {
                    Registering r = new Registering(ccode, scode, bdate, mark);
                    addFirst(r);
                } catch (IllegalArgumentException e) {
                    System.out.println("Line " + lineNumber + " invalid: " + e.getMessage());
                }
            }

            sc.close();
            System.out.println("Load registering list successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    public void saveToFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("File name null or is empty");
        }
        try {
            PrintWriter pw = new PrintWriter(filename);

            RegisteringNode p = head;

            while (p != null) {
                pw.println(p.info.toFileLine());
                p = p.next;
            }

            pw.close();
            System.out.println("Save registering list successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // Hàm lấy Node đầu tiên phục vụ cho việc duyệt danh sách từ bên ngoài lớp (ví dụ: MenuStudent)
    public RegisteringNode getHead() {
        return head;
    }
}
