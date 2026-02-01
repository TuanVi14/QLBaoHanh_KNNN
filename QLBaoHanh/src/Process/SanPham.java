package Process;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DBConnect;

public class SanPham {
	public DBConnect cn= new DBConnect();     
	//Truy van tat ca du lieu trong Table LoaiSP
	public ResultSet ShowLoaiSP() throws SQLException{    
        cn.connectSQL(); 
        String sql = "SELECT * FROM loaisanpham";         
        return cn.LoadData(sql); 
     }
	 //Truy van cac dong du lieu trong Table LoaiSP theo Maloai 
    public ResultSet ShowLoaiSP(String ml) throws SQLException{    
       String sql = "SELECT * FROM loaisanpham where MaLoai='" + ml +"'";         
       return cn.LoadData(sql); 
    } 
    //Truy van lieu trong Table Sanpham theo Maloai 
    public ResultSet ShowSPtheoML(String ml) {            
    String sql = "SELECT MaModel, TenSanPham, CauHinh, ThoiHanBaoHanh, L.MaLoai, TenLoai  FROM sanphammodel sp, loaisanpham L where L.MaLoai=sp.MaLoai and L.MaLoai='" + ml +"'";         
    return cn.LoadData(sql); 
    } 
    //Truy van tat ca du lieu trong Table Sanpham 
    public ResultSet ShowSanPham() throws SQLException{    
       cn.connectSQL(); 
       String sql = "SELECT MaModel, TenSanPham, CauHinh, ThoiHanBaoHanh, L.MaLoai, TenLoai  FROM sanphammodel sp, loaisanpham L where L.MaLoai=sp.MaLoai";         
       return cn.LoadData(sql); 
    } 
    //Truy van du lieu trong Table Sanpham theo MaSP 
    public ResultSet ShowSPTheoma(String ma) throws SQLException{    
       String sql = "SELECT MaModel, TenSanPham, CauHinh, ThoiHanBaoHanh, L.MaLoai, TenLoai  FROM sanphammodel sp, loaisanpham L where L.MaLoai=sp.MaLoai and MaModel='" + ma +"'";         
       return cn.LoadData(sql); 
    }  
    public ResultSet ShowSPTheoten(String ten) throws SQLException{    
       String sql = "SELECT MaModel, TenSanPham, CauHinh, ThoiHanBaoHanh, L.MaLoai, TenLoai  FROM sanphammodel sp, loaisanpham L where L.MaLoai=sp.MaLoai and TenSanPham like '%" + ten + "%'";         
       return cn.LoadData(sql); 
    } 
    //Theo moi 1 dong du lieu vao table Sanpham 
    public void InsertSanPham(int ma, String ten, String cauhinh, int tg, int vt) throws SQLException {
        String sql =
            "INSERT INTO nhanvien (MaModel, TenSanPham, CauHinh, ThoiHanBaoHanh, MaLoai) " +
            "VALUES (" + ma + ", '" + ten + "', '" + cauhinh + "', '" + tg + "', " + vt + ")";
        cn.UpdateData(sql);
    }

    //Dieu chinh 1 dong du lieu vao table Sanpham 
 // Điều chỉnh 1 dòng dữ liệu trong table SanPham
    public void EditSanPham(int ma, String ten, String cauhinh, int tg) throws SQLException {

        String sql =
            "UPDATE sanphammodel " +
            "SET TenSanPham = N'" + ten + "', " +
            "CauHinh = N'" + cauhinh + "', " +
            "ThoiHanBaoHanh = " + tg +
            " WHERE MaSanPham = " + ma;

        cn.UpdateData(sql);
    }
 
    //Xoa 1 dong du lieu vao table Sanpham 
    public void DeleteSanPham(String ma) throws SQLException{    
       String sql = "Delete from sanphammodel where MaModel='" + ma +"'";         
       cn.UpdateData(sql); 
    }
}
