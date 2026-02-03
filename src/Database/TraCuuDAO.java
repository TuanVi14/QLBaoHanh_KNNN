package Database; // Đổi package về Database

import Process.ThongTinBaoHanh; // Import DTO từ package Process
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TraCuuDAO {

    /**
     * Tra cứu thông tin bảo hành dựa trên Serial Number
     * Hàm này thực hiện JOIN 4 bảng: SanPhamDaBan -> HoaDon -> KhachHang -> SanPhamModel
     * Để lấy ra: Tên máy, Tên khách, Ngày mua, Hạn bảo hành
     */
    public ThongTinBaoHanh traCuuBaoHanh(String serialInput) {
        ThongTinBaoHanh info = null;
        Connection conn = KetNoiCSDL.getConnection(); // Gọi hàm kết nối cùng package Database
        
        // Câu lệnh SQL quan trọng nhất hệ thống
        String sql = "SELECT sp.MaSPDaBan, sp.SerialNumber, md.TenSanPham, " +
                     "       kh.TenKhachHang, kh.SoDienThoai, hd.NgayLap, md.ThoiHanBaoHanh " +
                     "FROM sanphamdaban sp " +
                     "JOIN hoadon hd ON sp.MaHoaDon = hd.MaHoaDon " +
                     "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang " +
                     "JOIN sanphammodel md ON sp.MaModel = md.MaModel " +
                     "WHERE sp.SerialNumber = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, serialInput);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Map dữ liệu từ SQL vào đối tượng Java (DTO)
                info = new ThongTinBaoHanh();
                info.setMaSPDaBan(rs.getInt("MaSPDaBan"));
                info.setSerialNumber(rs.getString("SerialNumber"));
                info.setTenSanPham(rs.getString("TenSanPham"));
                info.setTenKhachHang(rs.getString("TenKhachHang"));
                info.setSoDienThoai(rs.getString("SoDienThoai"));
                info.setNgayMua(rs.getDate("NgayLap"));
                info.setThoiHanBaoHanh(rs.getInt("ThoiHanBaoHanh"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            KetNoiCSDL.closeConnection(conn);
        }
        return info;
    }

    // --- HÀM MAIN ĐỂ TEST RIÊNG FILE NÀY ---
    public static void main(String[] args) {
        TraCuuDAO dao = new TraCuuDAO();
        
        // Nhập thử 1 serial có thật trong Database của bạn để test
        // Ví dụ: DEL123456 (Laptop Dell) hoặc IPH654321 (iPhone)
        String serialCanTest = "DEL123456"; 
        
        System.out.println("Đang kiểm tra kết nối và tìm kiếm serial: " + serialCanTest);
        ThongTinBaoHanh info = dao.traCuuBaoHanh(serialCanTest);
        
        if (info != null) {
            System.out.println("--- KẾT QUẢ TÌM THẤY ---");
            System.out.println("Tên máy: " + info.getTenSanPham());
            System.out.println("Khách hàng: " + info.getTenKhachHang());
            System.out.println("Ngày mua: " + info.getNgayMua());
            System.out.println("Hạn bảo hành: " + info.getThoiHanBaoHanh() + " tháng");
        } else {
            System.out.println("LỖI: Không tìm thấy Serial này trong CSDL!");
            System.out.println("1. Hãy chắc chắn XAMPP đã bật MySQL.");
            System.out.println("2. Kiểm tra file KetNoiCSDL.java xem đúng Port chưa.");
            System.out.println("3. Kiểm tra bảng 'sanphamdaban' đã có dữ liệu mẫu chưa.");
        }
    }
}