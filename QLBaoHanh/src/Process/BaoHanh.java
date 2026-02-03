package Process;

import java.sql.*;

import Database.DBConnect;

public class BaoHanh {
	 DBConnect cn = new DBConnect();

	    // 1. Lấy toàn bộ phiếu bảo hành
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
		      + "ORDER BY pb.MaPhieu";

		    return cn.LoadData(sql);
		}


	    // 2. Tìm kiếm theo từ khóa (mã phiếu / serial / khách hàng)
	    public ResultSet ShowPhieuBHTheoTK(String keyword) {
	        String sql =
	        		"SELECT pb.MaPhieu, spm.TenSanPham, spdb.SerialNumber, kh.TenKhachHang, " +
	        		        "pb.NgayTiepNhan, pb.TrangThai, nv.TenNhanVien " +
	        		        "FROM phieubaohanh pb " +
	        		        "JOIN sanphamdaban spdb ON pb.MaSPDaBan = spdb.MaSPDaBan " +
	        		        "JOIN sanphammodel spm ON spdb.MaModel = spm.MaModel " +
	        		        "JOIN hoadon hd ON spdb.MaHoaDon = hd.MaHoaDon " +
	        		        "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang " +
	        		        "LEFT JOIN lichsuxuly ls ON pb.MaPhieu = ls.MaPhieu " +
	        		        "LEFT JOIN nhanvien nv ON ls.MaNhanVien = nv.MaNhanVien " +
	        		        "WHERE pb.MaPhieu LIKE '%" + keyword + "%' " +
	        		        "   OR spdb.SerialNumber LIKE '%" + keyword + "%' " +
	        		        "   OR kh.TenKhachHang LIKE '%" + keyword + "%'";
	        return cn.LoadData(sql);
	    }

	    // 3. Lấy phiếu theo mã
	    public ResultSet ShowPhieuTheoMa(int maPhieu) {
	        String sql =
	            "SELECT pb.MaPhieu, " +
	            "kh.TenKhachHang, " +
	            "spm.TenSanPham, " +
	            "spdb.SerialNumber, " +
	            "hd.NgayLap AS NgayMua, " +
	            "DATE_ADD(hd.NgayLap, INTERVAL spm.ThoiHanBaoHanh MONTH) AS HanBaoHanh, " +
	            "pb.NgayTiepNhan, " +
	            "pb.TrangThai " +
	            "FROM phieubaohanh pb " +
	            "JOIN sanphamdaban spdb ON pb.MaSPDaBan = spdb.MaSPDaBan " +
	            "JOIN sanphammodel spm ON spdb.MaModel = spm.MaModel " +
	            "JOIN hoadon hd ON spdb.MaHoaDon = hd.MaHoaDon " +
	            "JOIN khachhang kh ON hd.MaKhachHang = kh.MaKhachHang " +
	            "WHERE pb.MaPhieu = " + maPhieu;

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
	    	String sql = "SELECT DISTINCT  TrangThai FROM phieubaohanh";
	    	return cn.LoadData(sql);
	    }
	    
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
