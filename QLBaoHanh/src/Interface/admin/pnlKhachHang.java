package Interface.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Interface.Admin;
import Process.KhachHang;
import Process.MD5Util;
import Process.NhanVien;
import javax.swing.JTextArea;

public class pnlKhachHang extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final KhachHang kh = new KhachHang();
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private boolean cothem=true;
	
	private JTextField txtTimKiem;
	private JTextField txtMaKH;
	private JTextField txtTenKH;
	private JTextField txtSDT;
	private JTable table;
	
	private JButton btnThem, btnXoa,btnSua,btnLuu,btnKluu;
	private JTextArea txtDiaChi;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public pnlKhachHang() throws SQLException {
		setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Tên Khách Hàng:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_2.setBounds(44, 11, 91, 14);
        add(lblNewLabel_2);
        
        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(162, 9, 348, 20);
        add(txtTimKiem);
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
        btnTimKiem.setBounds(539, 8, 89, 23);
        add(btnTimKiem);
        
        JLabel lblNewLabel_8 = new JLabel("Mã Khách Hàng:");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_8.setBounds(10, 75, 100, 14);
        add(lblNewLabel_8);
        
        txtMaKH = new JTextField();
        txtMaKH.setBounds(120, 73, 128, 20);
        add(txtMaKH);
        txtMaKH.setColumns(10);
        
        JLabel lblNewLabel_9 = new JLabel("Tên Khách Hàng:");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_9.setBounds(317, 76, 100, 14);
        add(lblNewLabel_9);
        
        txtTenKH = new JTextField();
        txtTenKH.setBounds(429, 73, 230, 20);
        add(txtTenKH);
        txtTenKH.setColumns(10);
        
        JLabel lblNewLabel_10 = new JLabel("Số Điện Thoại:");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_10.setBounds(10, 127, 94, 14);
        add(lblNewLabel_10);
        
        txtSDT = new JTextField();
        txtSDT.setBounds(120, 125, 128, 20);
        add(txtSDT);
        txtSDT.setColumns(10);
        
        JLabel lblNewLabel_11 = new JLabel("Địa Chỉ:");
        lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_11.setBounds(317, 128, 47, 14);
        add(lblNewLabel_11);
        
        btnThem = new JButton("Thêm");
        btnThem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setKhoa(false);
        		setNull();
        		cothem = true;
        	}
        });
        btnThem.setBounds(46, 163, 89, 23);
        add(btnThem);
        
        btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String ma=txtMaKH.getText(); 
		        try { 
		            if(ma.length()==0)              
		                JOptionPane.showMessageDialog(null,"Can chon 1 SP de xoa","Thong bao",1); 
		            else 
		            { 
		                if(JOptionPane.showConfirmDialog(null, "Ban muon xoa sp " + ma +  
		" này hay khong?","Thong bao",2)==0) 
		                {     
		                    kh.DeleteKhachHang(ma);;//goi ham xoa du lieu theo ma loai 
		                    ClearData();//Xoa du lieu trong tableModel 
		                    ShowData();//Do du lieu vao table Model 
		                    setNull();//Xoa trang Textfield 
		                } 
		             } 
		        }  
		        catch (SQLException ex) { 
		            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
		        }
        	}
        });
        btnXoa.setBounds(159, 163, 89, 23);
        add(btnXoa);
        
        btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String ml=txtMaKH.getText(); 
		        if(ml.length()==0) //Chua chon Ma loai              
		                JOptionPane.showMessageDialog(null,"Vui long chon loi can sua", "Thong bao",1); 
		        else 
		        { 
		            setKhoa(false);//Mo khoa cac TextField  
		            cothem=false; //Gan cothem=false de ghi nhan trang thai la sua 
		        }
        	}
        });
        btnSua.setBounds(275, 163, 89, 23);
        add(btnSua);
        
        btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int ma = Integer.parseInt(txtMaKH.getText()); 
		        String ten=txtTenKH.getText(); 
		        String sdt = txtSDT.getText();
		        String dc = txtDiaChi.getText();
		         if(ma==0 || ten.length()==0)              
		                JOptionPane.showMessageDialog(null,"Vui long nhap Ma kh va ten kh", "Thong bao",1); 
		         else
		              try { 
		                if(cothem==true) {   //Luu cho tthem moi    
		                    kh.InsertKhachHang(ma, ten, sdt, dc); 
		                }
		                else                //Luu cho sua 
		                    kh.EditKhachHang(ma, ten, sdt, ma); 
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
        btnLuu.setBounds(388, 163, 89, 23);
        add(btnLuu);
        
        btnKluu = new JButton("K.Lưu");
        btnKluu.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		setKhoa(true);
        		setNull();
        	}
        });
        btnKluu.setBounds(496, 163, 89, 23);
        add(btnKluu);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 198, 649, 202);
        add(scrollPane);
        
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		//Hien thi du lieu len cac JTextField khi Click chuot vao JTable 
		        try{ 
		            //Lay chi so dong dang chon 
		            int row =table.getSelectedRow(); 
		            String ma=(table.getModel().getValueAt(row,0)).toString(); 
		            ResultSet rs= kh.ShowKHTheoma(ma);//Goi ham lay du lieu theo ma loai 
		            if(rs.next())//Neu co du lieu 
		            { 
		             txtMaKH.setText(rs.getString("MaKhachHang")); 
		             txtTenKH.setText(rs.getString("TenKhachHang")); 
		             txtSDT.setText(rs.getString("SoDienThoai"));  
		             txtDiaChi.setText(rs.getString("DiaChi")); 
		            } 
		        }
		        catch (SQLException e1) { 
		        }
			}
        });
        scrollPane.setViewportView(table);
        String []colsName = {"Mã Khách Hàng", "Tên Khách Hàng","Số Điện Thoại","Địa Chỉ"}; 
        // đặt tiêu đề cột cho tableModel 
        tableModel.setColumnIdentifiers(colsName);   
        // kết nối jtable với tableModel   
        table.setModel(tableModel);
        
        txtDiaChi = new JTextArea();
        txtDiaChi.setBounds(388, 111, 271, 41);
        add(txtDiaChi);
        
        setNull();
        setKhoa(true);
        ShowData();
	}
	/* ===== HÀM XỬ LÝ SỰ KIỆN ===== */
	private void setKhoa(boolean a) {
		txtMaKH.setEnabled(!a);
		txtTenKH.setEnabled(!a);
		txtSDT.setEnabled(!a);
		btnLuu.setEnabled(!a);
		btnKluu.setEnabled(!a);
		btnThem.setEnabled(a);
		btnSua.setEnabled(a);
		btnXoa.setEnabled(a);
	}
	
	private void setNull() {
		txtMaKH.setText(null);
		txtSDT.setText(null);
		txtTenKH.setText(null);
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
        result = kh.ShowKhachHang(); 
        try {   
            ClearData(); 
            while(result.next()){  
                String rows[] = new String[4]; 
                rows[0] = result.getString(1);  
                rows[1] = result.getString(2);  
                rows[2] = result.getString(3);  
                rows[3] = result.getString(4);                 
                tableModel.addRow(rows); 
            } 
        }  
        catch (SQLException e) { 
        }  
    }
	
	public final void ShowData(String ml) throws SQLException {         
		ResultSet result=null;           
		result = kh.ShowSPTheoten(ml);
		try {   
			ClearData(); 
			while(result.next()){  
				String rows[] = new String[4]; 
				rows[0] = result.getString(1);  
				rows[1] = result.getString(2);
				rows[2] = result.getString(3);  
			    rows[3] = result.getString(4);                 
			    tableModel.addRow(rows); 
			} 
		}  
		catch (SQLException e) { 
		}  
	}
}
