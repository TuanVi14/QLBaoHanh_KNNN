package Process;

import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DBConnect;

public class VaiTro {
    public DBConnect cn = new DBConnect();     

    // Lấy danh sách vai trò
    public ResultSet ShowVaiTro() throws SQLException {    
        // cn.connectSQL(); // Không cần gọi lại vì Constructor đã làm rồi
        String sql = "SELECT * FROM vaitro";         
        return cn.LoadData(sql); 
    }
    
    // Lấy theo mã
    public ResultSet ShowVaiTro(String ml) throws SQLException {    
       String sql = "SELECT * FROM vaitro WHERE MaVaiTro='" + ml +"'";         
       return cn.LoadData(sql); 
    } 
  
    // Tìm theo tên
    public ResultSet ShowVTTheoten(String ten) throws SQLException {    
       String sql = "SELECT * FROM vaitro WHERE TenVaiTro LIKE '%" + ten + "%'";         
       return cn.LoadData(sql); 
    } 
    
    // Thêm mới
    public void InsertVaiTro(int ma, String ten) throws SQLException {
        String sql = "INSERT INTO vaitro (MaVaiTro, TenVaiTro) VALUES (" + ma + ", N'" + ten + "')";
        cn.UpdateData(sql);
    }

    // Sửa (ĐÃ FIX LỖI THIẾU DẤU NHÁY ĐƠN)
    public void EditVaiTro(int ma, String ten) throws SQLException {    
        String sql = "UPDATE vaitro SET TenVaiTro = N'" + ten + "' WHERE MaVaiTro = " + ma;
        cn.UpdateData(sql);
    } 
    
    // Xóa
    public void DeleteVaiTro(String ma) throws SQLException {    
       String sql = "DELETE FROM vaitro WHERE MaVaiTro='" + ma +"'";         
       cn.UpdateData(sql); 
    }
}