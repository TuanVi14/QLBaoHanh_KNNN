package Interface.admin;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Interface.Admin;
import Process.VaiTro;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class jfVaiTro extends JFrame{
	
	private JTextField txtTimKiem;
	private JTextField txtMaVaiTro;
	private JTextField txtTenVaiTro;
	private JTable table;
	
	private final VaiTro vt = new VaiTro();
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private boolean cothem=true;
	
	private JButton btnThem, btnXoa,btnSua,btnLuu,btnKluu;
	
	public jfVaiTro() throws SQLException {
		setTitle("Quản Lý Tai Trò");
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tên Vai Trò:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(43, 37, 73, 14);
		getContentPane().add(lblNewLabel);
		
		txtTimKiem = new JTextField();
		txtTimKiem.setBounds(132, 35, 270, 20);
		getContentPane().add(txtTimKiem);
		txtTimKiem.setColumns(10);
		
		JButton btnTimKiem = new JButton("Tìm Kiếm");
		btnTimKiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tsp = txtTimKiem.getText().toString();
				try {
					ShowData(tsp);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnTimKiem.setBounds(412, 33, 96, 23);
		getContentPane().add(btnTimKiem);
		
		JLabel lblNewLabel_1 = new JLabel("Mã Vai Trò:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(48, 82, 68, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtMaVaiTro = new JTextField();
		txtMaVaiTro.setBounds(132, 79, 126, 20);
		getContentPane().add(txtMaVaiTro);
		txtMaVaiTro.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Tên Vai trò:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(291, 82, 73, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtTenVaiTro = new JTextField();
		txtTenVaiTro.setBounds(389, 80, 156, 20);
		getContentPane().add(txtTenVaiTro);
		txtTenVaiTro.setColumns(10);
		
		btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setKhoa(false);
				setNull();
			}
		});
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnThem.setBounds(10, 126, 89, 23);
		getContentPane().add(btnThem);
		
		btnSua = new JButton("Sửa");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ml=txtMaVaiTro.getText(); 
		        if(ml.length()==0) //Chua chon Ma loai              
		                JOptionPane.showMessageDialog(null,"Vui long chon loi can sua", "Thong bao",1); 
		        else 
		        { 
		            setKhoa(false);//Mo khoa cac TextField  
		            cothem=false; //Gan cothem=false de ghi nhan trang thai la sua 
		        }
			}
		});
		btnSua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSua.setBounds(109, 126, 89, 23);
		getContentPane().add(btnSua);
		
		btnXoa = new JButton("Xóa");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ma=txtMaVaiTro.getText(); 
		        try { 
		            if(ma.length()==0)              
		                JOptionPane.showMessageDialog(null,"Can chon 1 loại SP de xoa","Thong bao",1); 
		            else 
		            { 
		                if(JOptionPane.showConfirmDialog(null, "Ban muon xoa loại sp " + ma +  
		" này hay khong?","Thong bao",2)==0) 
		                {     
		                    vt.DeleteVaiTro(ma);;//goi ham xoa du lieu theo ma loai 
		                    ClearData();//Xoa du lieu trong tableModel 
		                    ShowData();//Do du lieu vao table Model 
		                    setNull();//Xoa trang Textfield 
		                } 
		             } 
		        }  
		        catch (SQLException ex) { 
		            Logger.getLogger(jfVaiTro.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnXoa.setBounds(208, 126, 89, 23);
		getContentPane().add(btnXoa);
		
		btnLuu = new JButton("Lưu");
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ma = Integer.parseInt(txtMaVaiTro.getText()); 
		        String ten=txtTenVaiTro.getText(); 
		         
		         if(ma==0 || ten.length()==0)              
		                JOptionPane.showMessageDialog(null,"Vui long nhap Ma vt va ten vt", "Thong bao",1); 
		         else
		              try { 
		                if(cothem==true) {   //Luu cho tthem moi    
		                    vt.InsertVaiTro(ma, ten); 
		                }
		                else                //Luu cho sua 
		                    vt.EditVaiTro(ma, ten); 
		                ClearData(); //goi ham xoa du lieu tron tableModel 
		                ShowData(); //Do lai du lieu vao Table Model 
		              } 
		              catch (SQLException ex) { 
		                   JOptionPane.showMessageDialog(null,"Cap nhat that bai","Thong bao",1); 
		              }             
		            setNull(); 
		            setKhoa(true);
			}
		});
		btnLuu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLuu.setBounds(307, 126, 89, 23);
		getContentPane().add(btnLuu);
		
		btnKluu = new JButton("K.Lưu");
		btnKluu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNull();
				setKhoa(true);
			}
		});
		btnKluu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnKluu.setBounds(412, 126, 89, 23);
		getContentPane().add(btnKluu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(130, 179, 342, 214);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		String []colsName = {"Mã Loại", "Tên Loại"}; 
        // đặt tiêu đề cột cho tableModel 
        tableModel.setColumnIdentifiers(colsName);   
        // kết nối jtable với tableModel   
        table.setModel(tableModel);
		
		JButton btnLamMoi = new JButton("Làm Mới");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ShowData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLamMoi.setBounds(511, 127, 96, 23);
		getContentPane().add(btnLamMoi);
		
		setNull();
		setKhoa(true);
		ShowData();
	}
	/* ===== HÀM XỬ LÝ SỰ KIỆN ===== */
	private void setKhoa(boolean a) {
		txtMaVaiTro.setEnabled(!a);
		txtTenVaiTro.setEnabled(!a);
		btnLuu.setEnabled(!a);
		btnKluu.setEnabled(!a);
		btnThem.setEnabled(a);
		btnSua.setEnabled(a);
		btnXoa.setEnabled(a);
	}
	
	private void setNull() {
		txtMaVaiTro.setText(null);
		txtTenVaiTro.setText(null);
	}
	
	//Ham xoa du lieu trong tableModel 
    public void ClearData() throws SQLException{ 
         //Lay chi so dong cuoi cung 
         int n=tableModel.getRowCount()-1; 
         for(int i=n;i>=0;i--) 
            tableModel.removeRow(i);//Remove tung dong          
    }
    
    public final void ShowData() throws SQLException {         
        ResultSet result=null;           
        result = vt.ShowVaiTro(); 
        try {   
            ClearData(); 
            while(result.next()){  
                String rows[] = new String[2]; 
                rows[0] = result.getString(1);  
                rows[1] = result.getString(2);                   
                tableModel.addRow(rows); 
            } 
        }  
        catch (SQLException e) { 
        }  
    }
    
    public final void ShowData(String ml) throws SQLException {         
		ResultSet result=null;           
		result = vt.ShowVTTheoten(ml);
		try {   
			ClearData(); 
			while(result.next()){  
				String rows[] = new String[2]; 
				rows[0] = result.getString(1);  
				rows[1] = result.getString(2);                 
			    tableModel.addRow(rows); 
			} 
		}  
		catch (SQLException e) { 
		}  
	}
}
