package Process;

import java.sql.*;

import Database.DBConnect;

public class BaoHanh {
	 DBConnect cn = new DBConnect();

	    // 1. Lấy toàn bộ phiếu bảo hành
	    public ResultSet ShowPhieuBH() throws SQLException {
	        cn.connectSQL();
	        String sql =
	            "SELECT p.MaPhieu, sp.TenSanPham, p.Serial, kh.TenKhachHang, " +
	            "p.NgayNhan, p.TrangThai, nv.TenNhanVien " +
	            "FROM phieubaohanh p " +
	            "JOIN sanphammodel sp ON p.MaSanPham = sp.MaSanPham " +
	            "JOIN khachhang kh ON p.MaKhachHang = kh.MaKhachHang " +
	            "LEFT JOIN nhanvien nv ON p.MaNhanVien = nv.MaNhanVien";
	        return cn.LoadData(sql);
	    }

	    // 2. Tìm kiếm theo từ khóa (mã phiếu / serial / khách hàng)
	    public ResultSet ShowPhieuBHTheoTK(String keyword) {
	        String sql =
	            "SELECT p.MaPhieu, sp.TenSanPham, p.Serial, kh.TenKhachHang, " +
	            "p.NgayNhan, p.TrangThai, nv.TenNhanVien " +
	            "FROM phieubaohanh p " +
	            "JOIN sanphammodel sp ON p.MaSanPham = sp.MaSanPham " +
	            "JOIN khachhang kh ON p.MaKhachHang = kh.MaKhachHang " +
	            "LEFT JOIN nhanvien nv ON p.MaNhanVien = nv.MaNhanVien " +
	            "WHERE p.Serial LIKE N'%" + keyword + "%' " +
	            "OR kh.TenKhachHang LIKE N'%" + keyword + "%'";
	        return cn.LoadData(sql);
	    }

	    // 3. Lấy phiếu theo mã
	    public ResultSet ShowPhieuTheoMa(int maPhieu) {
	        String sql =
	            "SELECT * FROM phieubaohanh WHERE MaPhieu = " + maPhieu;
	        return cn.LoadData(sql);
	    }

	    // 4. Thống kê theo trạng thái (Admin)
	    public ResultSet thongKeTheoTrangThai() {
	        String sql =
	            "SELECT TrangThai, COUNT(*) AS SoLuong " +
	            "FROM phieubaohanh GROUP BY TrangThai";
	        return cn.LoadData(sql);
	    }
	    
	    //5. lấy trạng thái
	    public ResultSet ShowTrangThai() {
	    	String sql = "SELECT TrangThai FROM phieubaohanh";
	    	return cn.LoadData(sql);
	    }
}
