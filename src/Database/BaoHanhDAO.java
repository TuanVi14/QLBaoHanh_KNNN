package Database;

import Process.ThongTinBaoHanh;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaoHanhDAO {

    /**
     * Tra cứu thông tin bảo hành dựa trên Serial Number
     * Câu lệnh SQL này nối 4 bảng: SanPhamDaBan -> HoaDon -> KhachHang -> SanPhamModel
     */
    public ThongTinBaoHanh traCuuBaoHanh(String serialInput) {
        ThongTinBaoHanh info = null;
        DBConnect db = new DBConnect();
        Connection conn = db.getConnection();
        
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
            db.close();
        }
        return info;
    }
}