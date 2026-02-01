package Process;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DBConnect;

public class NhanVien {
	public DBConnect cn= new DBConnect();     
	//Truy van tat ca du lieu trong Table LoaiSP
	public ResultSet ShowVaitro() throws SQLException{    
        cn.connectSQL(); 
        String sql = "SELECT * FROM vaitro";         
        return cn.LoadData(sql); 
     }
	 //Truy van cac dong du lieu trong Table LoaiSP theo Maloai 
    public ResultSet ShowVaitro(String ml) throws SQLException{    
       String sql = "SELECT * FROM vaitro where MaVaiTro='" + ml +"'";         
       return cn.LoadData(sql); 
    } 
    //Truy van lieu trong Table Sanpham theo Maloai 
    public ResultSet ShowNVtheoVaiTro(String ml) {            
    String sql = "SELECT MaNhanVien, TenNhanVien, TenDangNhap, L.MaVaiTro, TenVaiTro  FROM nhanvien nv, vaitro L where L.MaVaiTro=nv.MaVaiTro and L.MaVaiTro='" + ml +"'";         
    return cn.LoadData(sql); 
    } 
    //Truy van tat ca du lieu trong Table Sanpham 
    public ResultSet ShowNhanVien() throws SQLException{    
       cn.connectSQL(); 
       String sql = "SELECT MaNhanVien, TenNhanVien, TenDangNhap, L.MaVaiTro, TenVaiTro  FROM nhanvien nv, vaitro L where L.MaVaiTro=nv.MaVaiTro";         
       return cn.LoadData(sql); 
    } 
    //Truy van du lieu trong Table Sanpham theo MaSP 
    public ResultSet ShowSPTheoma(String ma) throws SQLException{    
       String sql = "SELECT MaNhanVien, TenNhanVien, TenDangNhap, L.MaVaiTro, TenVaiTro  FROM nhanvien nv, vaitro L where L.MaVaiTro=nv.MaVaiTro and MaNhanVien='" + ma +"'";         
       return cn.LoadData(sql); 
    }  
    public ResultSet ShowSPTheoten(String ten) throws SQLException{    
       String sql = "SELECT MaNhanVien, TenNhanVien, TenDangNhap, L.MaVaiTro, TenVaiTro  FROM nhanvien nv, vaitro L where L.MaVaiTro=nv.MaVaiTro and TenNhanVien like '%" + ten + "%'";         
       return cn.LoadData(sql); 
    } 
    //Theo moi 1 dong du lieu vao table Sanpham 
    public void InsertNhanVien(String ma, String ten, String tendn, String vt)  throws SQLException{    
       String sql = "INSERT INTO nhanvien values('" + ma +"',N'" + ten +"'," + tendn + ",'" + vt + "')";
       cn.UpdateData(sql); 
    } 
    //Dieu chinh 1 dong du lieu vao table Sanpham 
    public void EditNhanVien(String ma, String ten, String vt)  throws SQLException{    
       String sql = "Update nhanvien set TenNhanVien=N'" + ten +",MaVaiTro='" + vt +"'  where MaNhanVien='" + ma +"'";         
       cn.UpdateData(sql); 
    } 
    //Xoa 1 dong du lieu vao table Sanpham 
    public void DeleteNhanVien(String ma) throws SQLException{    
       String sql = "Delete from nhanvien where MaNhanVien='" + ma +"'";         
       cn.UpdateData(sql); 
    }
    
    public ResultSet DangNhap(String tendn, String mk) throws SQLException{
    	String sql = "SELECT * FROM nhanvien WHERE TenDangNhap ='" + tendn +"' and MatKhauHash = '"+mk+"'";
    	return cn.LoadData(sql);
    }
}
