package Process;

import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DBConnect;

public class ThongKe {

    public DBConnect cn = new DBConnect();

    // ================= ĐẾM DÙNG CHUNG =================
    private int dem(String sql) throws SQLException {
        cn.connectSQL();
        ResultSet rs = cn.LoadData(sql);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    // ================= THỐNG KÊ NHANH =================
    public int ThongKeHomNay() throws SQLException {
        String sql =
            "SELECT COUNT(*) FROM phieubaohanh " +
            "WHERE DATE(NgayTiepNhan) = CURDATE()";
        return dem(sql);
    }

    public int ThongKeTuanNay() throws SQLException {
        String sql =
            "SELECT COUNT(*) FROM phieubaohanh " +
            "WHERE WEEK(NgayTiepNhan) = WEEK(CURDATE()) " +
            "AND YEAR(NgayTiepNhan) = YEAR(CURDATE())";
        return dem(sql);
    }

    public int ThongKeThangNay() throws SQLException {
        String sql =
            "SELECT COUNT(*) FROM phieubaohanh " +
            "WHERE MONTH(NgayTiepNhan) = MONTH(CURDATE()) " +
            "AND YEAR(NgayTiepNhan) = YEAR(CURDATE())";
        return dem(sql);
    }

    // ================= THEO LOẠI SẢN PHẨM =================
    public ResultSet ThongKeTheoLoaiSP() throws SQLException {
        cn.connectSQL();
        String sql =
            "SELECT l.TenLoai, " +
            "COUNT(p.MaPhieu) AS TongBH, " +
            "SUM(CASE WHEN p.TrangThai = 'Đang sửa' THEN 1 ELSE 0 END) AS DangXuLy, " +
            "SUM(CASE WHEN p.TrangThai = 'Hoàn thành' THEN 1 ELSE 0 END) AS HoanThanh " +
            "FROM loaisanpham l " +
            "LEFT JOIN sanphammodel m ON l.MaLoai = m.MaLoai " +
            "LEFT JOIN sanphamdaban s ON m.MaModel = s.MaModel " +
            "LEFT JOIN phieubaohanh p ON s.MaSPDaBan = p.MaSPDaBan " +
            "GROUP BY l.MaLoai, l.TenLoai";
        return cn.LoadData(sql);
    }

    // ================= THEO THỜI GIAN =================
    public ResultSet ThongKeTheoThoiGian(String tuNgay, String denNgay) throws SQLException {
        cn.connectSQL();
        String sql =
            "SELECT p.MaPhieu, p.TrangThai, p.NgayTiepNhan, " +
            "k.TenKhachHang, m.TenSanPham " +
            "FROM phieubaohanh p " +
            "JOIN sanphamdaban s ON p.MaSPDaBan = s.MaSPDaBan " +
            "JOIN sanphammodel m ON s.MaModel = m.MaModel " +
            "JOIN hoadon h ON s.MaHoaDon = h.MaHoaDon " +
            "JOIN khachhang k ON h.MaKhachHang = k.MaKhachHang " +
            "WHERE p.NgayTiepNhan BETWEEN '" + tuNgay + "' AND '" + denNgay + "'";
        return cn.LoadData(sql);
    }

    // ================= TỶ LỆ HOÀN THÀNH =================
    public double TyLeHoanThanh() throws SQLException {
        cn.connectSQL();
        String sql =
            "SELECT " +
            "CASE WHEN COUNT(*) = 0 THEN 0 " +
            "ELSE SUM(CASE WHEN TrangThai = 'Hoàn thành' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) END " +
            "FROM phieubaohanh";
        ResultSet rs = cn.LoadData(sql);
        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0;
    }
}
