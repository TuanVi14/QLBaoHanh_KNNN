package Process;

import java.sql.*;
import Database.DBConnect;

public class BaoHanh {
    DBConnect cn = new DBConnect();

    // 1. Lấy toàn bộ phiếu bảo hành (GIỮ NGUYÊN)
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
          + "ORDER BY pb.MaPhieu DESC"; // Sửa nhẹ: Nên để DESC để phiếu mới nhất lên đầu
        return cn.LoadData(sql);
    }

    // 2. Tìm kiếm theo từ khóa (GIỮ NGUYÊN)
    public ResultSet ShowPhieuBHTheoTK(String keyword) {
        String sql = "SELECT pb.MaPhieu, kh.TenKhachHang, spm.TenSanPham, pb.NgayTiepNhan, pb.TrangThai " +
                "FROM phieubaohanh pb " +
                "JOIN sanphamdaban spdb ON pb.MaSPDaBan = spdb.MaSPDaBan " +
                "JOIN sanphammodel spm ON spdb.MaModel = spm.MaModel " +
                "JOIN hoadon hd ON spdb.MaHoaDon = hd.MaHoaDon " +
                "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang " +
                "WHERE kh.TenKhachHang LIKE N'%" + keyword + "%' " +
                "   OR spm.TenSanPham LIKE N'%" + keyword + "%' " +
                "   OR spdb.SerialNumber LIKE '%" + keyword + "%' " +
                "   OR pb.NgayTiepNhan LIKE '%" + keyword + "%'";
        return cn.LoadData(sql);
    }

    // 3. Lấy phiếu theo mã (CẬP NHẬT ĐỂ SỬA LỖI THIẾU CỘT)
    public ResultSet ShowPhieuTheoMa(int maPhieu) {
        // Đã bổ sung pb.LoiBaoCao và kh.SoDienThoai vào câu lệnh SELECT
        String sql =
            "SELECT pb.MaPhieu, " +
            "kh.TenKhachHang, " +
            "kh.SoDienThoai, " +        // <--- BỔ SUNG
            "spm.TenSanPham, " +
            "spdb.SerialNumber, " +
            "hd.NgayLap AS NgayMua, " +
            "DATE_ADD(hd.NgayLap, INTERVAL spm.ThoiHanBaoHanh MONTH) AS HanBaoHanh, " +
            "pb.NgayTiepNhan, " +
            "pb.TrangThai, " +
            "pb.LoiBaoCao " +           // <--- BỔ SUNG (Khắc phục lỗi Not Found)
            "FROM phieubaohanh pb " +
            "JOIN sanphamdaban spdb ON pb.MaSPDaBan = spdb.MaSPDaBan " +
            "JOIN sanphammodel spm ON spdb.MaModel = spm.MaModel " +
            "JOIN hoadon hd ON spdb.MaHoaDon = hd.MaHoaDon " +
            "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang " +
            "WHERE pb.MaPhieu = " + maPhieu;

        return cn.LoadData(sql);
    }

    // 4. Thống kê theo trạng thái (GIỮ NGUYÊN)
    public ResultSet thongKeTheoTrangThai() {
        String sql =
            "SELECT TrangThai, COUNT(*) AS SoLuong " +
            "FROM phieubaohanh GROUP BY TrangThai";
        return cn.LoadData(sql);
    }
    
    // 5. Lấy trạng thái (GIỮ NGUYÊN)
    public ResultSet ShowTrangThai() {
        String sql = "SELECT DISTINCT TrangThai FROM phieubaohanh";
        return cn.LoadData(sql);
    }
    
    // 6. Lọc theo trạng thái (GIỮ NGUYÊN)
    public ResultSet locPhieuBaoHanhTheoTrangThai(String trangThai) {
        String sql =
            "SELECT pb.MaPhieu, kh.TenKhachHang, spm.TenSanPham, " +
            "pb.NgayTiepNhan, pb.TrangThai " +
            "FROM phieubaohanh pb " +
            "JOIN sanphamdaban spdb ON pb.MaSPDaBan = spdb.MaSPDaBan " +
            "JOIN sanphammodel spm ON spdb.MaModel = spm.MaModel " +
            "JOIN hoadon hd ON spdb.MaHoaDon = hd.MaHoaDon " +
            "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang " +
            "WHERE pb.TrangThai = N'" + trangThai + "'";

        return cn.LoadData(sql);
    }
}