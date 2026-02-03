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
        Connection conn = KetNoiCSDL.getConnection();
        String sql = "INSERT INTO phieubaohanh (MaSPDaBan, NgayTiepNhan, LoiBaoCao, TrangThai) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pbh.getMaSPDaBan());
            ps.setDate(2, pbh.getNgayTiepNhan());
            ps.setString(3, pbh.getLoiBaoCao());
            ps.setString(4, pbh.getTrangThai());
            
            return ps.executeUpdate() > 0; // Trả về true nếu insert thành công
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            KetNoiCSDL.closeConnection(conn);
        }
        return false;
    }

    // 2. Cập nhật trạng thái phiếu (Ví dụ: Từ "Đang sửa" -> "Hoàn thành")
    public boolean capNhatTrangThai(int maPhieu, String trangThaiMoi) {
        Connection conn = KetNoiCSDL.getConnection();
        String sql = "UPDATE phieubaohanh SET TrangThai = ? WHERE MaPhieu = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, trangThaiMoi);
            ps.setInt(2, maPhieu);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            KetNoiCSDL.closeConnection(conn);
        }
        return false;
    }
    
    // 3. Lấy danh sách phiếu (Kèm tên máy và tên khách để hiển thị GUI)
    // Lưu ý: Hàm này trả về Object[] để dễ đổ vào JTable, hoặc bạn có thể tạo DTO mở rộng
    public List<Object[]> layDanhSachPhieuDayDu() {
        List<Object[]> list = new ArrayList<>();
        Connection conn = KetNoiCSDL.getConnection();
        // Join để lấy Serial và Tên Máy
        String sql = "SELECT pbh.MaPhieu, sp.SerialNumber, md.TenSanPham, pbh.NgayTiepNhan, pbh.LoiBaoCao, pbh.TrangThai " +
                     "FROM phieubaohanh pbh " +
                     "JOIN sanphamdaban sp ON pbh.MaSPDaBan = sp.MaSPDaBan " +
                     "JOIN sanphammodel md ON sp.MaModel = md.MaModel " +
                     "ORDER BY pbh.MaPhieu DESC";
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            KetNoiCSDL.closeConnection(conn);
        }
        return list;
    }
}