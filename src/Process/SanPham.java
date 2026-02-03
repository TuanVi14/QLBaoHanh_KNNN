package Process;

import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DBConnect;

public class SanPham {
    public DBConnect cn = new DBConnect();

    // 1. Lấy danh sách sản phẩm (Kèm tên loại)
    public ResultSet ShowSanPham() throws SQLException {
        String sql = "SELECT sp.MaModel, sp.TenSanPham, sp.CauHinh, sp.ThoiHanBaoHanh, l.MaLoai, l.TenLoai " +
                     "FROM sanphammodel sp, loaisanpham l WHERE l.MaLoai = sp.MaLoai";
        return cn.LoadData(sql);
    }

    // 2. Lấy loại sản phẩm để đổ vào ComboBox
    public ResultSet ShowLoaiSP() throws SQLException {
        String sql = "SELECT * FROM loaisanpham";
        return cn.LoadData(sql);
    }
    
    // 3. Lấy tên loại theo mã (để hiển thị khi click bảng)
    public ResultSet ShowLoaiSP(String maLoai) throws SQLException {
        String sql = "SELECT * FROM loaisanpham WHERE MaLoai='" + maLoai + "'";
        return cn.LoadData(sql);
    }

    // 4. Tìm kiếm
    public ResultSet ShowSPTheoten(String ten) throws SQLException {
        String sql = "SELECT sp.MaModel, sp.TenSanPham, sp.CauHinh, sp.ThoiHanBaoHanh, l.MaLoai, l.TenLoai " +
                     "FROM sanphammodel sp, loaisanpham l " +
                     "WHERE l.MaLoai = sp.MaLoai AND sp.TenSanPham LIKE '%" + ten + "%'";
        return cn.LoadData(sql);
    }
    
    public ResultSet ShowSPTheoma(String ma) throws SQLException {
        String sql = "SELECT sp.MaModel, sp.TenSanPham, sp.CauHinh, sp.ThoiHanBaoHanh, l.MaLoai, l.TenLoai " +
                     "FROM sanphammodel sp, loaisanpham l " +
                     "WHERE l.MaLoai = sp.MaLoai AND sp.MaModel='" + ma + "'";
        return cn.LoadData(sql);
    }

    // 5. Thêm Mới (ĐÃ SỬA LỖI SQL: Insert vào sanphammodel chứ không phải nhanvien)
    public void InsertSanPham(int ma, String ten, String cauhinh, int tg, int vt) throws SQLException {
        String sql = "INSERT INTO sanphammodel (MaModel, TenSanPham, CauHinh, ThoiHanBaoHanh, MaLoai) " +
                     "VALUES (" + ma + ", N'" + ten + "', N'" + cauhinh + "', " + tg + ", " + vt + ")";
        cn.UpdateData(sql);
    }

    // 6. Cập Nhật
    public void EditSanPham(int ma, String ten, String cauhinh, int tg) throws SQLException {
        String sql = "UPDATE sanphammodel SET TenSanPham = N'" + ten + "', " +
                     "CauHinh = N'" + cauhinh + "', ThoiHanBaoHanh = " + tg + 
                     " WHERE MaModel = " + ma;
        cn.UpdateData(sql);
    }

    // 7. Xóa
    public void DeleteSanPham(String ma) throws SQLException {
        String sql = "DELETE FROM sanphammodel WHERE MaModel='" + ma + "'";
        cn.UpdateData(sql);
    }
}