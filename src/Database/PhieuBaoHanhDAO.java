package Database;

import Process.PhieuBaoHanh;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhieuBaoHanhDAO {

    // ==========================================================
    // 1. CHỨC NĂNG NGHIỆP VỤ (THÊM / SỬA)
    // ==========================================================

    // Thêm phiếu bảo hành mới (Dùng cho PanelTiepNhan)
    public boolean taoPhieuMoi(PhieuBaoHanh pbh) {
        DBConnect db = new DBConnect();
        Connection conn = db.getConnection();
        
        String sql = "INSERT INTO phieubaohanh (MaSPDaBan, NgayTiepNhan, LoiBaoCao, TrangThai) VALUES (?, ?, ?, ?)";
        try {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, pbh.getMaSPDaBan());
                ps.setDate(2, pbh.getNgayTiepNhan());
                ps.setString(3, pbh.getLoiBaoCao());
                ps.setString(4, pbh.getTrangThai());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    // Cập nhật trạng thái phiếu (Dùng cho Kỹ thuật viên / Trả máy)
    public boolean capNhatTrangThai(int maPhieu, String trangThaiMoi) {
        DBConnect db = new DBConnect();
        Connection conn = db.getConnection();
        
        String sql = "UPDATE phieubaohanh SET TrangThai = ? WHERE MaPhieu = ?";
        try {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, trangThaiMoi);
                ps.setInt(2, maPhieu);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    // ==========================================================
    // 2. CHỨC NĂNG TRA CỨU / HIỂN THỊ
    // ==========================================================
    
    /**
     * Lấy danh sách phiếu đầy đủ (ĐÃ TỐI ƯU)
     * - Bổ sung JOIN bảng KhachHang để hiển thị tên khách (Quan trọng cho Admin)
     */
    public List<Object[]> layDanhSachPhieuDayDu() {
        List<Object[]> list = new ArrayList<>();
        DBConnect db = new DBConnect();
        Connection conn = db.getConnection();
        
        // SQL đã sửa để lấy Tên Khách Hàng
        String sql = "SELECT p.MaPhieu, k.TenKhachHang, m.TenSanPham, p.NgayTiepNhan, p.TrangThai, p.LoiBaoCao " +
                     "FROM phieubaohanh p " +
                     "JOIN sanphamdaban s ON p.MaSPDaBan = s.MaSPDaBan " +
                     "JOIN sanphammodel m ON s.MaModel = m.MaModel " +
                     "JOIN hoadon h ON s.MaHoaDon = h.MaHoaDon " +
                     "JOIN khachhang k ON h.MaKhachHang = k.MaKhachHang " +
                     "ORDER BY p.MaPhieu DESC";
        try {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Object[] row = {
                        rs.getInt("MaPhieu"),
                        rs.getString("TenKhachHang"), // Đã có tên khách
                        rs.getString("TenSanPham"),
                        rs.getDate("NgayTiepNhan"),
                        rs.getString("TrangThai"),
                        rs.getString("LoiBaoCao")    // Lấy thêm lỗi để hiển thị chi tiết nếu cần
                    };
                    list.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return list;
    }

    /**
     * [MỚI] Tra cứu lịch sử bảo hành của một thiết bị
     * Dùng cho Kỹ thuật viên xem máy này đã từng sửa gì chưa
     */
    public List<Object[]> layLichSuBaoHanhTheoSerial(String serial) {
        List<Object[]> list = new ArrayList<>();
        DBConnect db = new DBConnect();
        Connection conn = db.getConnection();
        
        String sql = "SELECT p.MaPhieu, p.NgayTiepNhan, p.LoiBaoCao, p.TrangThai " +
                     "FROM phieubaohanh p " +
                     "JOIN sanphamdaban s ON p.MaSPDaBan = s.MaSPDaBan " +
                     "WHERE s.SerialNumber = ? " +
                     "ORDER BY p.NgayTiepNhan DESC";
        try {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, serial);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    list.add(new Object[]{
                        rs.getInt("MaPhieu"),
                        rs.getDate("NgayTiepNhan"),
                        rs.getString("LoiBaoCao"),
                        rs.getString("TrangThai")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return list;
    }
}