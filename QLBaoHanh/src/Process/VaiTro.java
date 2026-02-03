package Process;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DBConnect;

public class VaiTro {
	public DBConnect cn= new DBConnect();     
	//Truy van tat ca du lieu trong Table LoaiSP
	public ResultSet ShowVaiTro() throws SQLException{    
        cn.connectSQL(); 
        String sql = "SELECT * FROM vaitro";         
        return cn.LoadData(sql); 
     }
	 //Truy van cac dong du lieu trong Table LoaiSP theo Maloai 
    public ResultSet ShowVaiTro(String ml) throws SQLException{    
       String sql = "SELECT * FROM vaitro where MaVaiTro='" + ml +"'";         
       return cn.LoadData(sql); 
    } 
  
    public ResultSet ShowVTTheoten(String ten) throws SQLException{    
       String sql = "SELECT *  FROM vaitro where  TenVaiTro like '%" + ten + "%'";         
       return cn.LoadData(sql); 
    } 
    //Theo moi 1 dong du lieu vao table Sanpham 
    public void InsertVaiTro(int ma, String ten) throws SQLException {
        String sql =
            "INSERT INTO vaitro (MaVaiTro, TenVaiTro) " +
            "VALUES (" + ma + ", '" + ten + "')";

        cn.UpdateData(sql);
    }

    //Dieu chinh 1 dong du lieu vao table Sanpham 
    public void EditVaiTro(int ma, String ten) throws SQLException {    
        String sql =
            "UPDATE vaitro " +
            "SET TenVaiTro='" + ten +
            " WHERE MaVaiTro=" + ma;

        cn.UpdateData(sql);
    } 
    //Xoa 1 dong du lieu vao table Sanpham 
    public void DeleteVaiTro(String ma) throws SQLException{    
       String sql = "Delete from vaitro where MaVaiTro='" + ma +"'";         
       cn.UpdateData(sql); 
    }
}
