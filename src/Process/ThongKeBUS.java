package Process;

import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DBConnect;
import Database.PhieuBaoHanhDAO;
import java.util.List;

public class ThongKeBUS {

    // Kết nối CSDL để chạy SQL phức tạp
    public DBConnect cn = new DBConnect();
    
    // Gọi DAO để lấy list (từ code cũ)
    private final PhieuBaoHanhDAO phieuDAO = new PhieuBaoHanhDAO();

    // =======================================================
    // PHẦN 1: LOGIC CHO KỸ THUẬT VIÊN (DASHBOARD - CHUNG)
    // =======================================================
    
    // Đếm số lượng máy đang sửa (Trạng thái KHÁC 'Hoàn thành')
    public int demMayDangSua() {
        List<Object[]> list = phieuDAO.layDanhSachPhieuDayDu();
        int count = 0;
        for (Object[] row : list) {
            String trangThai = (String) row[5]; 
            if (trangThai == null || !trangThai.equalsIgnoreCase("Hoàn thành")) {
                count++;
            }
        }
        return count;
    }

    // Đếm số lượng máy đã xong
    public int demMayDaXong() {
        List<Object[]> list = phieuDAO.layDanhSachPhieuDayDu();
        int count = 0;
        for (Object[] row : list) {
            String trangThai = (String) row[5];
            if (trangThai != null && trangThai.equalsIgnoreCase("Hoàn thành")) {
                count++;
            }
        }
        return count;
    }

    // =======================================================
    // PHẦN 2: LOGIC CHO ADMIN (BÁO CÁO TỔNG QUÁT)
    // =======================================================

    private int dem(String sql) {
        try {
            ResultSet rs = cn.LoadData(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int ThongKeHomNay() {
        return dem("SELECT COUNT(*) FROM phieubaohanh WHERE DATE(NgayTiepNhan) = CURDATE()");
    }

    public int ThongKeTuanNay() {
        return dem("SELECT COUNT(*) FROM phieubaohanh WHERE WEEK(NgayTiepNhan) = WEEK(CURDATE()) AND YEAR(NgayTiepNhan) = YEAR(CURDATE())");
    }

    public int ThongKeThangNay() {
        return dem("SELECT COUNT(*) FROM phieubaohanh WHERE MONTH(NgayTiepNhan) = MONTH(CURDATE()) AND YEAR(NgayTiepNhan) = YEAR(CURDATE())");
    }

    // Thống kê chi tiết theo loại sản phẩm
    public ResultSet ThongKeTheoLoaiSP() {
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

    // Tỷ lệ hoàn thành công việc (%)
    public double TyLeHoanThanh() {
        String sql =
            "SELECT CASE WHEN COUNT(*) = 0 THEN 0 " +
            "ELSE SUM(CASE WHEN TrangThai = 'Hoàn thành' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) END " +
            "FROM phieubaohanh";
        try {
            ResultSet rs = cn.LoadData(sql);
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // =======================================================
    // PHẦN 3: [MỚI BỔ SUNG] LOGIC THỐNG KÊ CÁ NHÂN
    // =======================================================

    /**
     * Đếm số phiếu mà nhân viên cụ thể (maNV) đang tham gia xử lý
     * Logic: Join bảng LichSuXuLy để tìm những phiếu có mã nhân viên này,
     * và trạng thái phiếu KHÔNG PHẢI là "Hoàn thành" hoặc "Đã trả khách".
     */
    public int demDangSuaCuaNV(int maNV) {
        String sql = "SELECT COUNT(DISTINCT p.MaPhieu) " +
                     "FROM phieubaohanh p " +
                     "JOIN lichsuxuly l ON p.MaPhieu = l.MaPhieu " +
                     "WHERE p.TrangThai NOT IN (N'Hoàn thành', N'Đã trả khách', N'Hủy bỏ') " +
                     "AND l.MaNhanVien = " + maNV;
        return dem(sql);
    }

    /**
     * Đếm số phiếu mà nhân viên cụ thể (maNV) đã hoàn thành
     * Logic: Join bảng LichSuXuLy để tìm những phiếu có mã nhân viên này,
     * và trạng thái phiếu LÀ "Hoàn thành" hoặc "Đã trả khách".
     */
    public int demDaXongCuaNV(int maNV) {
        String sql = "SELECT COUNT(DISTINCT p.MaPhieu) " +
                     "FROM phieubaohanh p " +
                     "JOIN lichsuxuly l ON p.MaPhieu = l.MaPhieu " +
                     "WHERE p.TrangThai IN (N'Hoàn thành', N'Đã trả khách') " +
                     "AND l.MaNhanVien = " + maNV;
        return dem(sql);
    }
}