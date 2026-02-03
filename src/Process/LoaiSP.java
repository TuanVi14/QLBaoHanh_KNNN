package Process;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DBConnect;

public class LoaiSP {
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
  
    public ResultSet ShowLoaiTheoten(String ten) throws SQLException{    
       String sql = "SELECT *  FROM loaisanpham where  TenLoai like '%" + ten + "%'";         
       return cn.LoadData(sql); 
    } 
    //Theo moi 1 dong du lieu vao table Sanpham 
    public void InsertLoaiSP(int ma, String ten) throws SQLException {
        String sql =
            "INSERT INTO laoisanpham (MaLoai, TenLoai) " +
            "VALUES (" + ma + ", '" + ten + "')";

        cn.UpdateData(sql);
    }

    //Dieu chinh 1 dong du lieu vao table Sanpham 
    public void EditLoaiSP(int ma, String ten) throws SQLException {    
        String sql =
            "UPDATE loaisanpham " +
            "SET TenLoai='" + ten +
            " WHERE MaLoai=" + ma;

        cn.UpdateData(sql);
    } 
    //Xoa 1 dong du lieu vao table Sanpham 
    public void DeleteLoaiSP(String ma) throws SQLException{    
       String sql = "Delete from loaisanpham where MaLoai='" + ma +"'";         
       cn.UpdateData(sql); 
    }
}