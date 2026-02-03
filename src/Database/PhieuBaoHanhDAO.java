package Database;

import Process.PhieuBaoHanh;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhieuBaoHanhDAO {

    // 1. Thêm phiếu bảo hành mới
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

    // 2. Cập nhật trạng thái phiếu
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
    
    // 3. Lấy danh sách phiếu đầy đủ
    public List<Object[]> layDanhSachPhieuDayDu() {
        List<Object[]> list = new ArrayList<>();
        DBConnect db = new DBConnect();
        Connection conn = db.getConnection();
        
        String sql = "SELECT pbh.MaPhieu, sp.SerialNumber, md.TenSanPham, pbh.NgayTiepNhan, pbh.LoiBaoCao, pbh.TrangThai " +
                     "FROM phieubaohanh pbh " +
                     "JOIN sanphamdaban sp ON pbh.MaSPDaBan = sp.MaSPDaBan " +
                     "JOIN sanphammodel md ON sp.MaModel = md.MaModel " +
                     "ORDER BY pbh.MaPhieu DESC";
        try {
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Object[] row = {
                        rs.getInt("MaPhieu"),
                        rs.getString("SerialNumber"),
                        rs.getString("TenSanPham"),
                        rs.getDate("NgayTiepNhan"),
                        rs.getString("LoiBaoCao"),
                        rs.getString("TrangThai")
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
}