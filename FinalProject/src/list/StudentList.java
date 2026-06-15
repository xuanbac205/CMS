package list;

import node.StudentNode;
import models.Student;

public class StudentList {

    // fields
    private StudentNode head;
    private StudentNode tail;

    // constructor
    public StudentList() {
        head = tail = null;
    }

    // check Empty
    public boolean isEmpty() {
        return head == null;
    }

    // clear list
    public void clear() {
        head = tail = null;
    }

    // get Head node (Sử dụng cho các chức năng duyệt từ bên ngoài lớp)
    public StudentNode getHead() {
        return head;
    }

    /**
     * HÀM BỔ TRỢ ĐẶC BIỆT: Tìm kiếm trả về cả một StudentNode thay vì chỉ lấy
     * Student info. Hàm này giúp tối ưu hóa, tái sử dụng code cho việc duyệt,
     * sửa đổi hoặc xóa Node.
     */
    private StudentNode findNodeByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        StudentNode p = head;
        while (p != null) {
            if (p.info.getScode().equalsIgnoreCase(code.trim())) {
                return p; // Trả về toàn bộ Node tìm thấy
            }
            p = p.next;
        }
        return null;
    }

    // =========================================================================
    // MỤC 2.1: Load data from file (Thường được triển khai kết hợp với lớp IO)
    // =========================================================================
    // Lưu ý: Khi đọc từng dòng từ file text, bạn phân tách thuộc tính (scode, name, byear) 
    // và gọi hàm addLastFromFile() dưới đây để thêm thẳng vào danh sách mà không cần validation lại tuổi.
    public void addLastFromFile(Student x) {
        if (x == null) {
            return;
        }
        StudentNode p = new StudentNode(x);
        if (isEmpty()) {
            head = tail = p;
        } else {
            tail.next = p;
            tail = p;
        }
    }

    // =========================================================================
    // MỤC 2.2: Input & add to the end (Có đầy đủ Validation)
    // =========================================================================
    public void addLast(Student x) {
        if (x == null) {
            throw new IllegalArgumentException("Dữ liệu sinh viên không được null.");
        }

        // KIỂM TRA ĐIỀU KIỆN 1: Mã sinh viên (scode) phải là duy nhất (Unique)
        if (findNodeByCode(x.getScode()) != null) {
            throw new IllegalArgumentException("Lỗi: Mã sinh viên (scode) đã tồn tại trong hệ thống!");
        }

        // KIỂM TRA ĐIỀU KIỆN 2: Tuổi sinh viên phải >= 18 tuổi tại năm hiện tại (2026)
        int currentYear = 2026;
        if (currentYear - x.getByear() < 18) {
            throw new IllegalArgumentException("Lỗi: Sinh viên phải từ 18 tuổi trở lên (Năm sinh phải <= " + (currentYear - 18) + ").");
        }

        StudentNode p = new StudentNode(x);
        if (isEmpty()) {
            head = tail = p;
            return;
        }

        tail.next = p;
        tail = p;
    }

    // =========================================================================
    // MỤC 2.3: Display data
    // =========================================================================
    public void display() {
        if (isEmpty()) {
            System.out.println("Danh sách sinh viên rỗng.");
            return;
        }

        StudentNode p = head;
        System.out.printf("%-10s %-25s %-6s\n", "Scode", "Name", "Byear");
        while (p != null) {
            System.out.println(p.info); // Đảm bảo lớp Student đã override hàm toString() đúng định dạng
            p = p.next;
        }
    }

    // =========================================================================
    // MỤC 2.4: Save student list to file (Thường được triển khai chung với tầng IO)
    // =========================================================================
    // =========================================================================
    // MỤC 2.5: Search by scode
    // =========================================================================
    public Student searchByCode(String code) {
        StudentNode foundNode = findNodeByCode(code);
        return (foundNode != null) ? foundNode.info : null;
    }

    // =========================================================================
    // MỤC 2.6: Delete by scode (Bắt buộc tích hợp xóa liên kết với RegisteringList)
    // =========================================================================
    // Giải thuật bắt buộc: Tìm và xóa tất cả bản ghi đăng ký của sinh viên này trong RegisteringList trước,
    // sau đó mới tiến hành cắt Node sinh viên ra khỏi StudentList hiện tại để tránh lỗi mồ côi dữ liệu.
    public boolean deleteByCode(String code, RegisteringList registeringList, CourseList courseList) {
    if (code == null || code.trim().isEmpty() || isEmpty()) {
        return false;
    }

    String cleanCode = code.trim();

    if (findNodeByCode(cleanCode) == null) {
        return false;
    }

    // === ĐOẠN THÊM MỚI ĐỂ GIẢM REGISTERED ===
    // Duyệt qua danh sách đăng ký, tìm những môn mà thằng sinh viên này từng đăng ký để trừ bớt đi
    node.RegisteringNode pReg = registeringList.getHead();
    while (pReg != null) {
        if (pReg.info.getScode().equalsIgnoreCase(cleanCode)) {
            String targetCcode = pReg.info.getCcode();
            
            // Tìm môn học tương ứng trong CourseList
            models.Course course = courseList.searchCourseByCcode(targetCcode);
            if (course != null) {
                course.setRegistered(course.getRegistered() - 1); // Giảm registered đi 1 người
                // Không cần tăng seats vì seats ở đây đề bài định nghĩa là tổng số chỗ ngồi cố định của phòng học (seats >= registered)
            }
        }
        pReg = pReg.next;
    }
    // =======================================

    // 2. Sau khi hoàn trả số lượng xong mới xóa sạch bản ghi đăng ký của nó
    registeringList.deleteByScode(cleanCode);

    // 3. Logic cắt Node sinh viên ra khỏi StudentList (Giữ nguyên toàn bộ phần bên dưới của bạn)
    if (head.info.getScode().equalsIgnoreCase(cleanCode)) {
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return true;
    }

        // Trường hợp 2: Sinh viên cần xóa nằm ở GIỮA hoặc CUỐI danh sách
        StudentNode prev = head;
        StudentNode cur = head.next;

        while (cur != null) {
            if (cur.info.getScode().equalsIgnoreCase(cleanCode)) {
                prev.next = cur.next;

                // Nếu Node xóa chính là Node cuối cùng (tail), cập nhật lại tail về Node phía trước
                if (cur == tail) {
                    tail = prev;
                }
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }

    // =========================================================================
    // MỤC 2.7: Search by name (student name) - Tìm kiếm tương đối chứa chuỗi
    // =========================================================================
    public boolean displayByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        boolean found = false;
        StudentNode p = head;
        String searchKey = name.trim().toLowerCase();

        while (p != null) {
            // Sử dụng .contains() giúp tìm kiếm linh hoạt (Ví dụ: nhập "anh" sẽ ra cả "Tuấn Anh", "Trọng Anh")
            if (p.info.getName().toLowerCase().contains(searchKey)) {
                if (!found) {
                    System.out.printf("%-10s %-25s %-6s\n", "Scode", "Name", "Byear");
                }
                System.out.println(p.info);
                found = true;
            }
            p = p.next;
        }
        return found;
    }

    // =========================================================================
    // MỤC 2.8: Search registered courses by scode
    // =========================================================================
    // Logic yêu cầu: Tìm kiếm thông tin sinh viên theo mã, nếu tìm thấy thì in thông tin cá nhân ra,
    // sau đó phải liên kết sang danh sách đăng ký (RegisteringList) hiển thị tất cả môn học sinh viên đó chọn.
    public void searchRegisteredCourses(String code, RegisteringList registeringList) {
        Student student = searchByCode(code);
        if (student == null) {
            System.out.println("Không tìm thấy dữ liệu sinh viên có mã: " + code);
            return;
        }

        // In thông tin cá nhân của sinh viên được tìm thấy
        System.out.println("\n--- THÔNG TIN SINH VIÊN ---");
        System.out.printf("%-10s %-25s %-6s\n", "Scode", "Name", "Byear");
        System.out.println(student);

        System.out.println("--- CÁC MÔN HỌC ĐÃ ĐĂNG KÝ ---");

        // 2. Lấy Node đầu tiên của RegisteringList để tự duyệt bằng vòng lặp
        // (Đảm bảo bên lớp RegisteringList của bạn có hàm getHead() trả về Node đầu tiên)
        node.RegisteringNode pReg = registeringList.getHead();
        boolean hasRegistration = false;

        while (pReg != null) {
            // Kiểm tra xem mã sinh viên trong bản ghi đăng ký có trùng với code cần tìm không
            if (pReg.info.getScode().equalsIgnoreCase(code.trim())) {
                System.out.printf("- Mã lớp: %-8s | Ngày ĐK: %-10s | Điểm: %-4.1f | Trạng thái: %s\n",
                        pReg.info.getCcode(),
                        pReg.info.getBdate(),
                        pReg.info.getMark(),
                        (pReg.info.getState() == 1 ? "Passed" : "Failed")
                );
                hasRegistration = true;
            }
            pReg = pReg.next; // Chuyển sang node đăng ký tiếp theo
        }

        // 3. Nếu duyệt từ đầu đến cuối danh sách mà không có bản ghi nào trùng scode
        if (!hasRegistration) {
            System.out.println("Sinh viên này hiện chưa đăng ký lớp môn học nào.");
        }
    }
}
