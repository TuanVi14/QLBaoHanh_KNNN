package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KetNoiCSDL {
    
    // Cấu hình thông số kết nối
    // Dựa vào file SQL bạn gửi, MySQL đang chạy port 3307
    public static final String HOST_NAME = "localhost";
    public static final String PORT = "3307"; 
    public static final String DB_NAME = "quanlybaohanh";
    public static final String USER_NAME = "root"; // Mặc định của XAMPP
    public static final String PASSWORD = "";      // Mặc định XAMPP không có mật khẩu
    
    /**
     * Hàm lấy kết nối đến cơ sở dữ liệu MySQL
     * @return Connection hoặc null nếu thất bại
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // 1. Đăng ký Driver MySQL (Sử dụng thư viện mysql-connector-j)
            // Nếu dùng bản cũ (mysql-connector-java-5.x) thì bỏ chữ ".cj" đi
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Tạo chuỗi kết nối URL
            // useUnicode=true&characterEncoding=UTF-8: Đảm bảo lưu và đọc tiếng Việt không bị lỗi font
            // useSSL=false: Tắt bảo mật SSL để tránh lỗi cảnh báo trên localhost
            String connectionUrl = "jdbc:mysql://" + HOST_NAME + ":" + PORT + "/" + DB_NAME 
                    + "?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
            
            // 3. Mở kết nối
            conn = DriverManager.getConnection(connectionUrl, USER_NAME, PASSWORD);
            System.out.println("Kết nối CSDL thành công!"); // Bỏ comment để test lúc đầu
            
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy thư viện JDBC MySQL (mysql-connector-j.jar)!");
            System.err.println("Vui lòng thêm file .jar vào thư mục lib và Add to Build Path.");
            // e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi: Không thể kết nối đến Database!");
            System.err.println("Hãy kiểm tra lại: Tên DB, Port (3306 hay 3307?), Username/Password.");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Hàm đóng kết nối để giải phóng tài nguyên
     * @param c Connection cần đóng
     */
    public static void closeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Hàm main để chạy thử file này xem có kết nối được không
    public static void main(String[] args) {
        Connection c = getConnection();
        if (c != null) {
            System.out.println("Test kết nối thành công! Database: " + DB_NAME);
            closeConnection(c);
        } else {
            System.out.println("Test kết nối thất bại!");
        }
    }
}