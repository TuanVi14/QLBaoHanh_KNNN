package Process;

import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DBConnect;

public class KhachHang {
    public DBConnect cn = new DBConnect();

    public ResultSet ShowKhachHang() throws SQLException {
        return cn.LoadData("SELECT * FROM khachhang");
    }

    public ResultSet ShowKHTheoma(String ma) throws SQLException {
        return cn.LoadData("SELECT * FROM khachhang WHERE MaKhachHang='" + ma + "'");
    }

    public ResultSet ShowSPTheoten(String ten) throws SQLException {
        return cn.LoadData("SELECT * FROM khachhang WHERE TenKhachHang LIKE N'%" + ten + "%'");
    }

    public void InsertKhachHang(int ma, String ten, String sdt, String dc) throws SQLException {
        String sql = "INSERT INTO khachhang (MaKhachHang, TenKhachHang, SoDienThoai, DiaChi) " +
                     "VALUES (" + ma + ", N'" + ten + "', '" + sdt + "', N'" + dc + "')";
        cn.UpdateData(sql);
    }

    // Đã sửa tham số dc từ int -> String
    public void EditKhachHang(int ma, String ten, String sdt, String dc) throws SQLException {
        String sql = "UPDATE khachhang SET TenKhachHang = N'" + ten + "', " +
                     "SoDienThoai = '" + sdt + "', DiaChi = N'" + dc + "' " +
                     "WHERE MaKhachHang = " + ma;
        cn.UpdateData(sql);
    }

    public void DeleteKhachHang(String ma) throws SQLException {
        cn.UpdateData("DELETE FROM khachhang WHERE MaKhachHang='" + ma + "'");
    }
}