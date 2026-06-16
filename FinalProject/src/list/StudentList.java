package list;

import node.StudentNode;
import models.Student;

/**
 * Lớp quản lý danh sách liên kết đơn (Singly Linked List) của Sinh viên.
 * Được viết từ đầu (Scratch) để đáp ứng các yêu cầu của môn Cấu trúc dữ liệu và Giải thuật.
 */
public class StudentList {
    
    // =========================================================================
    // KHỞI TẠO CÁC BIẾN VÀ CONSTRUCTOR
    // =========================================================================
    
    /** Con trỏ quản lý Node đầu tiên của danh sách */
    private StudentNode head;
    
    /** Con trỏ quản lý Node cuối cùng của danh sách, giúp thêm vào cuối với độ phức tạp O(1) */
    private StudentNode tail;

    /**
     * Constructor khởi tạo một danh sách sinh viên trống.
     */
    public StudentList() {
        head = tail = null;
    }

    // =========================================================================
    // CÁC HÀM BỔ TRỢ CƠ BẢN
    // =========================================================================

    /**
     * Kiểm tra xem danh sách liên kết có bị rỗng hay không.
     * @return true nếu rỗng, false nếu có phần tử.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Xóa sạch toàn bộ phần tử trong danh sách (giải phóng bộ nhớ RAM).
     */
    public void clear() {
        head = tail = null;
    }
    
    /**
     * Lấy ra Node đầu tiên (head) của danh sách.
     * Thường dùng để duyệt danh sách từ bên ngoài lớp (như trong các hàm phối hợp nhiều bảng).
     * @return Node đầu tiên.
     */
    public StudentNode getHead() {
        return head;
    }

    // =========================================================================
    // CÁC CHỨC NĂNG CHÍNH THEO ĐẶC TẢ ĐỀ BÀI (Mục 2.x)
    // =========================================================================

    /**
     * MỤC 2.2: Kiểm tra dữ liệu hợp lệ và thêm một sinh viên mới vào cuối danh sách.
     * @param x Đối tượng Student cần thêm.
     * @throws IllegalArgumentException Nếu dữ liệu null hoặc mã sinh viên (scode) bị trùng.
     */
    public void addLast(Student x) {
        // Kiểm tra dữ liệu đầu vào: Chặn không cho thêm đối tượng null
        if (x == null) {
            throw new IllegalArgumentException("Dữ liệu sinh viên không được null.");
        }
        
        // Ràng buộc nghiệp vụ: Mã sinh viên (scode) phải là duy nhất (Unique)
        if (searchByCode(x.getScode()) != null) {
            throw new IllegalArgumentException("Duplicated scode.");
        }
        
        // Bọc dữ liệu sinh viên vào trong một cái hộp Node
        StudentNode p = new StudentNode(x);
        
        // Trường hợp 1: Danh sách đang trống rỗng
        if (isEmpty()) {
            head = tail = p;
            return;
        }

        // Trường hợp 2: Danh sách đã có phần tử -> Nối đuôi Node mới và dịch chuyển con trỏ tail
        tail.next = p;
        tail = p;
    }

    /**
     * MỤC 2.3: Duyệt qua danh sách liên kết và hiển thị dữ liệu sinh viên dạng bảng.
     */
    public void display() {
        if (isEmpty()) {
            System.out.println("Danh sách sinh viên rỗng.");
            return;
        }

        StudentNode p = head;
        // In tiêu đề cột cho giao diện console đẹp mắt
        System.out.printf("%-10s %-25s %-6s\n", "Scode", "Name", "Byear");
        
        // Vòng lặp chạy liên tục cho đến khi con trỏ chạm vạch cuối cùng (null)
        while (p != null) {
            System.out.println(p.info); // Tự động kích hoạt hàm toString() của lớp Student
            p = p.next;                 // Dịch chuyển con trỏ sang Node tiếp theo
        }
    }

    /**
     * MỤC 2.5: Tìm kiếm một sinh viên cụ thể dựa trên mã sinh viên (scode).
     * Sử dụng thuật toán Tìm kiếm tuyến tính (Linear Search): Độ phức tạp O(N).
     * @param code Mã sinh viên cần tìm.
     * @return Đối tượng Student nếu tìm thấy, ngược lại trả về null.
     */
    public Student searchByCode(String code) {
        // Kiểm tra an toàn: Tránh lỗi tìm kiếm chuỗi rỗng hoặc khoảng trắng
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        
        StudentNode p = head;
        // Quét tuyến tính từ đầu đến cuối danh sách
        while (p != null) {
            if (p.info.getScode().equalsIgnoreCase(code.trim())) {
                return p.info; // Khớp mã -> Trả về thông tin sinh viên ngay lập tức
            }
            p = p.next;
        }
        return null; // Duyệt hết danh sách mà không thấy ai trùng mã
    }

    /**
     * Hàm bổ trợ: Tìm kiếm sinh viên khớp chính xác tuyệt đối theo Tên.
     * @param name Tên sinh viên cần tìm.
     * @return Đối tượng Student hoặc null.
     */
    public Student searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        
        StudentNode p = head;
        while (p != null) {
            if (p.info.getName().equalsIgnoreCase(name.trim())) {
                return p.info;
            }
            p = p.next;
        }
        return null;
    }

    /**
     * MỤC 2.7: Tìm kiếm sinh viên tương đối theo Tên (Tìm kiếm chuỗi con - Substring Search).
     * Hiển thị tất cả các kết quả chứa ký tự tìm kiếm (Ví dụ: nhập "anh" ra "Vũ Tuấn Anh").
     * @param name Từ khóa tên cần tìm.
     * @return true nếu tìm thấy ít nhất 1 kết quả, false nếu không tìm thấy ai.
     */
    public boolean displayByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        boolean found = false;
        StudentNode p = head;
        String searchKey = name.trim().toLowerCase();
        
        while (p != null) {
            // Kiểm tra xem tên sinh viên có chứa từ khóa tìm kiếm hay không (không phân biệt hoa thường)
            if (p.info.getName().toLowerCase().contains(searchKey)) {
                // Chỉ in tiêu đề bảng một lần duy nhất khi tìm thấy phần tử đầu tiên khớp
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

    /**
     * MỤC 2.6: Tìm và xóa một Node sinh viên khỏi danh sách dựa trên mã scode.
     * Xử lý chặt chẽ các trường hợp biên để tránh làm đứt gãy liên kết con trỏ.
     * @param code Mã sinh viên cần xóa.
     * @return true nếu xóa thành công, false nếu không tìm thấy mã để xóa.
     */
    public boolean deleteByCode(String code) {
        // Kiểm tra điều kiện biên cơ bản
        if (code == null || code.trim().isEmpty() || isEmpty()) {
            return false;
        }
        
        String cleanCode = code.trim();
        
        // TRƯỜNG HỢP 1: Node cần xóa nằm ngay ĐẦU danh sách (head)
        if (head.info.getScode().equalsIgnoreCase(cleanCode)) {
            head = head.next; // Đẩy head lên một nấc để ngắt liên kết với Node đầu cũ
            
            // Nếu sau khi xóa, danh sách trống rỗng hoàn toàn thì phải reset tail về null
            if (head == null) {
                tail = null;
            }
            return true;
        }
        
        // TRƯỜNG HỢP 2: Node cần xóa nằm ở Giữa hoặc Cuối danh sách
        StudentNode prev = head;     // Con trỏ chạy trước
        StudentNode cur = head.next; // Con trỏ chạy sau track theo phần tử hiện tại

        // Duyệt bằng bộ đôi con trỏ đuổi nhau để dễ dàng cắt móc xích
        while (cur != null) {
            if (cur.info.getScode().equalsIgnoreCase(cleanCode)) {
                // Thực hiện bắc cầu: Cho nốt trước nối thẳng sang nốt sau của nốt hiện tại
                prev.next = cur.next;

                // Điều kiện biên phụ: Nếu Node cần xóa vô tình lại chính là Node CUỐI (tail)
                if (cur == tail) {
                    tail = prev; // Kéo con trỏ tail lùi lại một bước về Node phía trước
                }
                return true;
            }
            
            // Tịnh tiến bộ đôi con trỏ lên một bước
            prev = cur;
            cur = cur.next;
        }
        return false; // Chạy hết vòng lặp mà không tìm thấy mã khớp để xóa
    }
}