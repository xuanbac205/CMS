package utils;

import models.Student;
import list.StudentList;
import node.StudentNode;
import java.io.*;

/**
 * Lớp tiện ích chịu trách nhiệm xử lý việc Đọc/Ghi (I/O) file dữ liệu cho Sinh viên.
 * Giúp lưu trữ lâu dài danh sách dưới dạng file text thô, phân tách bằng dấu gạch đứng '|'.
 */
public class FileUtils {

    // =========================================================================
    // MỤC 2.4: Save student list to file (Ghi dữ liệu ra file)
    // =========================================================================
    
    /**
     * Chuyển đổi và ghi toàn bộ danh sách liên kết sinh viên trong bộ nhớ RAM xuống file text trên ổ cứng.
     * @param list     Danh sách liên kết đơn chứa các Node sinh viên hiện tại.
     * @param fileName Đường dẫn đến file text cần lưu.
     */
    public static void saveStudent(StudentList list, String fileName) {
        // Kiểm tra an toàn: Danh sách trống trong RAM thì không ghi
        if (list == null) {
            System.out.println("Save failed: student list is null.");
            return;
        }
        
        // Kiểm tra an toàn: Tên file trống thì báo lỗi
        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("Save failed: file name is empty.");
            return;
        }

        File file = new File(fileName.trim());
        File parent = file.getParentFile();
        
        // Cơ chế tự động: Nếu thư mục chứa file chưa tồn tại (ví dụ thư mục data/), tự động tạo mới thư mục đó
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // Cú pháp Try-with-resources: Tự động đóng luồng ghi PrintWriter sau khi chạy xong để giải phóng tài nguyên
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {

            StudentNode p = list.getHead();

            // Vòng lặp duyệt tuyến tính để ghi từng Node sinh viên xuống file
            while (p != null) {
                Student s = p.info;

                // Ghi dữ liệu dạng văn bản thô, ngăn cách các thuộc tính bằng ký tự '|'
                pw.println(
                        s.getScode() + "|" +
                        s.getName() + "|" +
                        s.getByear()
                );

                p = p.next; // Dịch chuyển con trỏ sang Node kế tiếp
            }

            System.out.println("Saved successfully.");

        } catch (Exception e) {
            // Khối catch bảo vệ chương trình không bị crash/văng lỗi hệ thống nếu ổ cứng bị lỗi ghi
            System.out.println("Save failed.");
        }
    }

    // =========================================================================
    // MỤC 2.1: Load data from file (Đọc dữ liệu từ file)
    // =========================================================================

    /**
     * Đọc file text phân tách bằng dấu '|' từng dòng một, phân tích cú pháp tạo đối tượng Student,
     * rồi nạp trực tiếp vào danh sách liên kết đơn chạy trong chương trình.
     * @param list     Danh sách liên kết đích dùng để hứng các Node dữ liệu đọc được.
     * @param fileName Đường dẫn dẫn tới file dữ liệu text nguồn trên ổ cứng.
     */
    public static void loadStudent(StudentList list, String fileName) {
        // Kiểm tra điều kiện đầu vào
        if (list == null) {
            System.out.println("Load failed: student list is null.");
            return;
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            System.out.println("Load failed: file name is empty.");
            return;
        }

        File file = new File(fileName.trim());
        
        // Kiểm tra phần cứng: File không tồn tại trên ổ đĩa thì dừng lại báo lỗi ngay
        if (!file.exists()) {
            System.out.println("Load failed: file not found.");
            return;
        }

        // Tạo luồng đọc bộ đệm ký tự BufferedReader giúp tối ưu tốc độ đọc file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            int lineNumber = 0;
            
            // BẮT BUỘC: Xóa sạch danh sách cũ trong RAM trước khi nạp dữ liệu mới từ file vào
            list.clear();

            // Vòng lặp đọc từng dòng một cho đến khi hết file (đọc ra chuỗi null)
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Bỏ qua các dòng trống vô nghĩa hoặc dòng bấm nhầm phím cách trong file text
                if (line.isEmpty()) {
                    continue;
                }
                
                // Cắt chuỗi dựa trên ký tự gạch đứng '|' (Sử dụng cú pháp \\| vì '|' là ký tự đặc biệt trong Regex)
                String[] arr = line.split("\\|");

                // Đảm bảo tính toàn vẹn dữ liệu: Mỗi dòng bắt buộc phải cắt ra đúng 3 trường thông tin
                if (arr.length != 3) {
                    System.out.println("Line " + lineNumber + " invalid: must have 3 fields.");
                    continue;
                }

                String scode = arr[0].trim();
                String name = arr[1].trim();
                int byear;

                // Khối chuyển đổi kiểu dữ liệu từ Chuỗi (String) sang Số nguyên (int)
                try {
                    byear = Integer.parseInt(arr[2].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Line " + lineNumber + " invalid: birth year must be an integer.");
                    continue;
                }

                // Khối kiểm tra ràng buộc nghiệp vụ hệ thống
                try {
                    Student s = new Student(scode, name, byear);
                    list.addLast(s); // Thêm sinh viên hợp lệ vào cuối danh sách liên kết
                } catch (IllegalArgumentException e) {
                    // Bẫy lỗi này kích hoạt nếu dòng file đó bị trùng mã scode hoặc sinh viên chưa đủ 18 tuổi
                    System.out.println("Line " + lineNumber + " invalid: " + e.getMessage());
                    // Dùng lệnh 'continue' để bỏ qua dòng lỗi và tiếp tục đọc các dòng sạch tiếp theo, không làm gián đoạn nạp file
                }
            }

            System.out.println("Loaded successfully.");

        } catch (Exception e) {
            System.out.println("Load failed.");
        }
    }
}