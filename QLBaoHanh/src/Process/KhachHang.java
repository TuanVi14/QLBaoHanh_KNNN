package Process;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DBConnect;

public class KhachHang {
	public DBConnect cn= new DBConnect();     
 
    //Truy van tat ca du lieu trong Table Sanpham 
    public ResultSet ShowKhachHang() throws SQLException{    
       cn.connectSQL(); 
       String sql = "SELECT *  FROM khachhang";         
       return cn.LoadData(sql); 
    } 
    //Truy van du lieu trong Table Sanpham theo MaSP 
    public ResultSet ShowKHTheoma(String ma) throws SQLException{    
       String sql = "SELECT *  FROM khachhang where  MaKhachHang='" + ma +"'";         
       return cn.LoadData(sql); 
    }  
    public ResultSet ShowSPTheoten(String ten) throws SQLException{    
       String sql = "SELECT *  FROM khachhang where TenKhachHang like '%" + ten + "%'";         
       return cn.LoadData(sql); 
    } 
    //Theo moi 1 dong du lieu vao table Sanpham 
    public void InsertKhachHang(int ma, String ten, String sdt, String dc) throws SQLException {
        String sql =
            "INSERT INTO khachhang (MaKhachHang, TenKhachHang, SoDienThoai, DiaChi) " +
            "VALUES (" + ma + ", '" + ten + "', '" + sdt + "', '" + dc + "')";
        cn.UpdateData(sql);
    }


    // Điều chỉnh 1 dòng dữ liệu trong table SanPham
    public void EditKhachHang(int ma, String ten, String sdt, int dc) throws SQLException {

        String sql =
            "UPDATE khachhang " +
            "SET TenKhachHang = N'" + ten + "', " +
            "SoDienThoai = N'" + sdt + "', " +
            "DiaChi = " + dc;

        cn.UpdateData(sql);
    }
 
    //Xoa 1 dong du lieu vao table Sanpham 
    public void DeleteKhachHang(String ma) throws SQLException{    
       String sql = "Delete from khachhang where MaKhachHang='" + ma +"'";         
       cn.UpdateData(sql); 
    }
}
