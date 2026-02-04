package Process;

import java.sql.*;
import Database.DBConnect;

public class BaoHanh {
    
    DBConnect cn = new DBConnect();

    // 1. Lấy toàn bộ phiếu bảo hành (Sắp xếp mới nhất lên đầu)
    public ResultSet ShowPhieuBH() throws SQLException {
        cn.connectSQL();
        String sql =
            "SELECT "
          + "pb.MaPhieu, "
          + "kh.TenKhachHang, "
          + "spm.TenSanPham, "
          + "pb.NgayTiepNhan, "
          + "pb.TrangThai "
          + "FROM phieubaohanh pb "
          + "JOIN sanphamdaban spdb ON pb.MaSPDaBan = spdb.MaSPDaBan "
          + "JOIN sanphammodel spm ON spdb.MaModel = spm.MaModel "
          + "JOIN hoadon hd ON spdb.MaHoaDon = hd.MaHoaDon "
          + "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang "
          + "ORDER BY pb.MaPhieu DESC"; 
        return cn.LoadData(sql);
    }

    // 2. Tìm kiếm theo từ khóa (Đã bổ sung Serial và SĐT)
    public ResultSet ShowPhieuBHTheoTK(String keyword) {
        String sql = 
            "SELECT "
          + "pb.MaPhieu, "
          + "kh.TenKhachHang, "
          + "spm.TenSanPham, "
          + "pb.NgayTiepNhan, "
          + "pb.TrangThai "
          + "FROM phieubaohanh pb "
          + "JOIN sanphamdaban spdb ON pb.MaSPDaBan = spdb.MaSPDaBan "
          + "JOIN sanphammodel spm ON spdb.MaModel = spm.MaModel "
          + "JOIN hoadon hd ON spdb.MaHoaDon = hd.MaHoaDon "
          + "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang "
          + "WHERE kh.TenKhachHang LIKE N'%" + keyword + "%' " // Tìm theo Tên Khách
          + "   OR spm.TenSanPham LIKE N'%" + keyword + "%' " // Tìm theo Tên SP
          + "   OR spdb.SerialNumber LIKE '%" + keyword + "%' " // Tìm theo Số Seri
          + "   OR kh.SoDienThoai LIKE '%" + keyword + "%' "    // Tìm theo Số ĐT
          + "   OR pb.NgayTiepNhan LIKE '%" + keyword + "%' "   // Tìm theo Ngày
          + "ORDER BY pb.MaPhieu DESC";
        return cn.LoadData(sql);
    }

    // 3. Lấy phiếu theo mã (Đầy đủ thông tin để In Phiếu & Hiển thị chi tiết)
    public ResultSet ShowPhieuTheoMa(int maPhieu) {
        String sql =
            "SELECT "
          + "pb.MaPhieu, "
          + "kh.TenKhachHang, "
          + "kh.SoDienThoai, "        // Cần cho in phiếu
          + "spm.TenSanPham, "
          + "spdb.SerialNumber, "     // Cần cho in phiếu
          + "hd.NgayLap AS NgayMua, "
          + "DATE_ADD(hd.NgayLap, INTERVAL spm.ThoiHanBaoHanh MONTH) AS HanBaoHanh, "
          + "pb.NgayTiepNhan, "
          + "pb.TrangThai, "
          + "pb.LoiBaoCao "           // Cần cho hiển thị chi tiết
          + "FROM phieubaohanh pb "
          + "JOIN sanphamdaban spdb ON pb.MaSPDaBan = spdb.MaSPDaBan "
          + "JOIN sanphammodel spm ON spdb.MaModel = spm.MaModel "
          + "JOIN hoadon hd ON spdb.MaHoaDon = hd.MaHoaDon "
          + "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang "
          + "WHERE pb.MaPhieu = " + maPhieu;

        return cn.LoadData(sql);
    }

    // 4. Thống kê theo trạng thái (Dùng cho biểu đồ)
    public ResultSet thongKeTheoTrangThai() {
        String sql = "SELECT TrangThai, COUNT(*) AS SoLuong FROM phieubaohanh GROUP BY TrangThai";
        return cn.LoadData(sql);
    }
    
    // 5. Lấy danh sách trạng thái (Dùng cho ComboBox lọc)
    public ResultSet ShowTrangThai() {
        String sql = "SELECT DISTINCT TrangThai FROM phieubaohanh";
        return cn.LoadData(sql);
    }
    
    // 6. Lọc theo trạng thái
    public ResultSet locPhieuBaoHanhTheoTrangThai(String trangThai) {
        String sql =
            "SELECT pb.MaPhieu, kh.TenKhachHang, spm.TenSanPham, pb.NgayTiepNhan, pb.TrangThai " +
            "FROM phieubaohanh pb " +
            "JOIN sanphamdaban spdb ON pb.MaSPDaBan = spdb.MaSPDaBan " +
            "JOIN sanphammodel spm ON spdb.MaModel = spm.MaModel " +
            "JOIN hoadon hd ON spdb.MaHoaDon = hd.MaHoaDon " +
            "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang " +
            "WHERE pb.TrangThai = N'" + trangThai + "' " +
            "ORDER BY pb.MaPhieu DESC";

        return cn.LoadData(sql);
    }
}