package Interface.admin;

import javax.swing.JPanel;
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
import Process.MD5Util;
import Process.SanPham;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class pnlSanPham extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtTimKiem;
	private JTextField txtMaSP;
	private JTextField txtTenSP;
	private JTextField txtThoiHan;
	private JTextField txtTenLoai;
	private JTable table;
	private JButton btnThem, btnXoa,btnSua,btnLuu,btnKluu,btnEditloaisp;
	private JTextArea txtCauHinh;
	private JComboBox jcomMaLoai;
	
	private final SanPham sp =new SanPham();
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private boolean cothem=true;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public pnlSanPham() throws SQLException {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tên Sản Phẩm:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(35, 30, 89, 14);
		add(lblNewLabel);
		
		txtTimKiem = new JTextField();
		txtTimKiem.setBounds(142, 28, 370, 20);
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
		btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnTimKiem.setBounds(538, 27, 89, 23);
		add(btnTimKiem);
		
		JLabel lblNewLabel_1 = new JLabel("Mã Sản Phẩm:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setBounds(35, 84, 89, 14);
		add(lblNewLabel_1);
		
		txtMaSP = new JTextField();
		txtMaSP.setBounds(142, 82, 95, 20);
		add(txtMaSP);
		txtMaSP.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Tên Sản Phẩm:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(306, 85, 95, 14);
		add(lblNewLabel_2);
		
		txtTenSP = new JTextField();
		txtTenSP.setBounds(411, 82, 216, 20);
		add(txtTenSP);
		txtTenSP.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Cấu Hình:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(35, 136, 63, 14);
		add(lblNewLabel_3);
		
		txtCauHinh = new JTextArea();
		txtCauHinh.setBounds(142, 120, 485, 50);
		add(txtCauHinh);
		
		JLabel lblNewLabel_4 = new JLabel("Thời Hạn Bảo Hành:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(35, 192, 114, 14);
		add(lblNewLabel_4);
		
		txtThoiHan = new JTextField();
		txtThoiHan.setBounds(168, 190, 86, 20);
		add(txtThoiHan);
		txtThoiHan.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Mã Loại:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(306, 193, 49, 14);
		add(lblNewLabel_5);
		
		jcomMaLoai = new JComboBox();
		jcomMaLoai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ml=jcomMaLoai.getSelectedItem().toString();
				try {
					ShowTenvaitro(ml);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		jcomMaLoai.setBounds(371, 189, 57, 22);
		add(jcomMaLoai);
		
		txtTenLoai = new JTextField();
		txtTenLoai.setBounds(450, 190, 177, 20);
		add(txtTenLoai);
		txtTenLoai.setColumns(10);
		
		btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setKhoa(false);
				setNull();
			}
		});
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnThem.setBounds(18, 217, 89, 23);
		add(btnThem);
		
		btnSua = new JButton("Sửa");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ml=txtMaSP.getText(); 
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
		btnSua.setBounds(117, 217, 89, 23);
		add(btnSua);
		
		btnXoa = new JButton("Xóa");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ma=txtMaSP.getText(); 
		        try { 
		            if(ma.length()==0)              
		                JOptionPane.showMessageDialog(null,"Can chon 1 SP de xoa","Thong bao",1); 
		            else 
		            { 
		                if(JOptionPane.showConfirmDialog(null, "Ban muon xoa sp " + ma +  
		" này hay khong?","Thong bao",2)==0) 
		                {     
		                    sp.DeleteSanPham(ma);;//goi ham xoa du lieu theo ma loai 
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
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnXoa.setBounds(216, 217, 89, 23);
		add(btnXoa);
		
		btnLuu = new JButton("Lưu");
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ma = Integer.parseInt(txtMaSP.getText()); 
		        String ten=txtTenSP.getText(); 
		        String ch = txtCauHinh.getText();
		        int vt,tg; 
		        tg = Integer.parseInt(txtThoiHan.getText());
		        vt = Integer.parseInt(jcomMaLoai.getSelectedItem().toString()); 
		         if(ma==0 || ten.length()==0)              
		                JOptionPane.showMessageDialog(null,"Vui long nhap Ma sp va ten sp", "Thong bao",1); 
		         else
		              try { 
		                if(cothem==true) {   //Luu cho tthem moi    
		                    sp.InsertSanPham(ma, ten, ch, tg, vt); 
		                }
		                else                //Luu cho sua 
		                    sp.EditSanPham(ma, ten, ch, tg); 
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
		btnLuu.setBounds(316, 218, 89, 23);
		add(btnLuu);
		
		btnKluu = new JButton("K.Lưu");
		btnKluu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setKhoa(true);
				setNull();
			}
		});
		btnKluu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnKluu.setBounds(415, 217, 89, 23);
		add(btnKluu);
		
		btnEditloaisp = new JButton("EditLoaiSP");
		btnEditloaisp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new pnlLoaiSP().setVisible(true);
			}
		});
		btnEditloaisp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEditloaisp.setBounds(524, 218, 103, 23);
		add(btnEditloaisp);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 251, 592, 149);
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
		            ResultSet rs= sp.ShowSPTheoma(ma);//Goi ham lay du lieu theo ma loai 
		            if(rs.next())//Neu co du lieu 
		            { 
		             txtMaSP.setText(rs.getString("MaModel")); 
		             txtTenSP.setText(rs.getString("TenSanPham")); 
		             txtCauHinh.setText(rs.getString("CauHinh"));
		             txtThoiHan.setText(rs.getString("ThoiHanBaoHanh"));
		             jcomMaLoai.setSelectedItem(rs.getString("MaLoai")); 
		             txtTenLoai.setText(rs.getString("TenLoai")); 
		            } 
		        }
		        catch (SQLException e1) { 
		        }
			}
		});
		scrollPane.setViewportView(table);
		String []colsName = {"Mã SP", "Tên SP","Cấu hình","Thời Hạn BH","Loại SP"}; 
        // đặt tiêu đề cột cho tableModel 
        tableModel.setColumnIdentifiers(colsName);   
        // kết nối jtable với tableModel   
        table.setModel(tableModel);
        
        ShowData();
        setKhoa(true);
        setNull();
	}
	/* ===== HÀM XỬ LÝ SỰ KIỆN ===== */
	private void setKhoa(boolean a) {
		txtMaSP.setEnabled(!a);
		txtTenSP.setEnabled(!a);
		txtCauHinh.setEnabled(!a);
		txtThoiHan.setEnabled(!a);
		txtTenLoai.setEnabled(!a);
		btnLuu.setEnabled(!a);
		btnKluu.setEnabled(!a);
		btnThem.setEnabled(a);
		btnSua.setEnabled(a);
		btnXoa.setEnabled(a);
	}
	
	private void setNull() {
		txtMaSP.setText(null);
		txtTenSP.setText(null);
		txtCauHinh.setText(null);
		txtThoiHan.setText(null);
		txtTenLoai.setText(null);
	}
	public final void ShowDataCombo() {         
        ResultSet result=null;           
        try { 
            result = sp.ShowLoaiSP();            
            while(result.next()){  
                jcomMaLoai.addItem(result.getString("MaLoai"));
            } 
        }  
        catch (SQLException e) { 
        }  
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
        result = sp.ShowSanPham(); 
        try {   
            ClearData(); 
            while(result.next()){  
                String rows[] = new String[5]; 
                rows[0] = result.getString(1);  
                rows[1] = result.getString(2);  
                rows[2] = result.getString(3);  
                rows[3] = result.getString(4);   
                rows[4] = result.getString(6);
                tableModel.addRow(rows); 
            } 
        }  
        catch (SQLException e) { 
        }  
    }
	
	public final void ShowData(String ml) throws SQLException {         
		ResultSet result=null;           
		result = sp.ShowSPTheoten(ml);
		try {   
			ClearData(); 
			while(result.next()){  
				String rows[] = new String[5]; 
				rows[0] = result.getString(1);  
				rows[1] = result.getString(2);
				rows[2] = result.getString(3);  
			    rows[3] = result.getString(4);  
			    rows[4] = result.getString(6);
			    tableModel.addRow(rows); 
			} 
		}  
		catch (SQLException e) { 
		}  
	}
	
	public void ShowTenvaitro(String ma) throws SQLException{         
        ResultSet result=  sp.ShowLoaiSP(ma);           
        if(result.next()){ // nếu còn đọc tiếp được một dòng dữ liệu 
           txtTenLoai.setText(result.getString("TenLoai")); 
        }
    }
}
