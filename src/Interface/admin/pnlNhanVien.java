package Interface.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Process.MD5Util;
import Process.NhanVien;

public class pnlNhanVien extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final NhanVien nv = new NhanVien();
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private boolean cothem = true;
	
	private JTextField txtTimKiem;
	private JTextField txtMaNV;
	private JTextField txtTenNV;
	private JTextField txtTenDN;
	private JTextField txtTenVT;
	private JTable table;
	private JComboBox<String> jcomMaVT;
	
	private JButton btnThem, btnXoa, btnSua, btnLuu, btnKluu, btnQLVaiTro;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public pnlNhanVien() throws SQLException {
		setLayout(null);
		
		// --- TÌM KIẾM ---
		JLabel lblNewLabel_2 = new JLabel("Tên nhân viên:");
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
        		String tsp = txtTimKiem.getText();
				try {
					ShowData(tsp);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
        	}
        });
        btnTimKiem.setBounds(539, 8, 89, 23);
        add(btnTimKiem);
        
        // --- FORM NHẬP LIỆU ---
        JLabel lblNewLabel_8 = new JLabel("Mã Nhân Viên:");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_8.setBounds(10, 75, 84, 14);
        add(lblNewLabel_8);
        
        txtMaNV = new JTextField();
        txtMaNV.setBounds(120, 73, 128, 20);
        add(txtMaNV);
        txtMaNV.setColumns(10);
        
        JLabel lblNewLabel_9 = new JLabel("Tên Nhân Viên:");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_9.setBounds(317, 76, 91, 14);
        add(lblNewLabel_9);
        
        txtTenNV = new JTextField();
        txtTenNV.setBounds(429, 73, 230, 20);
        add(txtTenNV);
        txtTenNV.setColumns(10);
        
        JLabel lblNewLabel_10 = new JLabel("Tên Đăng Nhập:");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_10.setBounds(10, 127, 94, 14);
        add(lblNewLabel_10);
        
        txtTenDN = new JTextField();
        txtTenDN.setBounds(120, 125, 128, 20);
        add(txtTenDN);
        txtTenDN.setColumns(10);
        
        JLabel lblNewLabel_11 = new JLabel("Vai Trò:");
        lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_11.setBounds(317, 128, 47, 14);
        add(lblNewLabel_11);
        
        jcomMaVT = new JComboBox<String>();
        jcomMaVT.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(jcomMaVT.getSelectedItem() != null) {
	        		String ml = jcomMaVT.getSelectedItem().toString();
					try {
						ShowTenvaitro(ml);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
        		}
        	}
        });
        jcomMaVT.setBounds(378, 124, 53, 22);
        add(jcomMaVT);
        
        txtTenVT = new JTextField();
        txtTenVT.setBounds(441, 125, 218, 20);
        txtTenVT.setEditable(false); // Không cho nhập tay tên vai trò
        add(txtTenVT);
        txtTenVT.setColumns(10);
        
        // --- CÁC NÚT CHỨC NĂNG ---
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
        		String ma = txtMaNV.getText(); 
		        try { 
		            if(ma.length() == 0)              
		                JOptionPane.showMessageDialog(null, "Cần chọn 1 NV để xóa", "Thông báo", 1); 
		            else { 
		                if(JOptionPane.showConfirmDialog(null, "Bạn muốn xóa nhân viên " + ma + " này hay không?", "Thông báo", 2) == 0) {     
		                    nv.DeleteNhanVien(ma);
		                    ClearData();
		                    ShowData();
		                    setNull();
		                } 
		             } 
		        } catch (SQLException ex) { 
		            Logger.getLogger(pnlNhanVien.class.getName()).log(Level.SEVERE, null, ex);
		        }
        	}
        });
        btnXoa.setBounds(159, 163, 89, 23);
        add(btnXoa);
        
        btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String ml = txtMaNV.getText(); 
		        if(ml.length() == 0) // Chưa chọn Mã
		                JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên cần sửa", "Thông báo", 1); 
		        else { 
		            setKhoa(false); 
		            cothem = false; 
		            txtMaNV.setEnabled(false); // Mã NV không được sửa
		        }
        	}
        });
        btnSua.setBounds(275, 163, 89, 23);
        add(btnSua);
        
        btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
	        		int ma = Integer.parseInt(txtMaNV.getText()); 
			        String ten = txtTenNV.getText(); 
			        String tendn = txtTenDN.getText();
			        
			        int vt = 0;
			        if(jcomMaVT.getSelectedItem() != null) {
			        	vt = Integer.parseInt(jcomMaVT.getSelectedItem().toString());
			        }
			        
			        if(ma == 0 || ten.length() == 0)              
			             JOptionPane.showMessageDialog(null, "Vui lòng nhập Mã NV và Tên NV", "Thông báo", 1); 
			        else
			             try { 
			                if(cothem == true) { // Lưu mới    
			                	// Mật khẩu mặc định là 123456
			                	String mkH = MD5Util.encryptMD5("123456");
			                    nv.InsertNhanVien(ma, ten, tendn, mkH, vt); 
			                } else { // Cập nhật
			                    nv.EditNhanVien(ma, ten, vt); 
			                }
			                ClearData();
			                ShowData();
			             } catch (SQLException ex) { 
			                   JOptionPane.showMessageDialog(null, "Cập nhật thất bại: " + ex.getMessage(), "Thông báo", 1); 
			             }             
			        setNull(); 
			        setKhoa(true); 
        		} catch (NumberFormatException ex) {
        			JOptionPane.showMessageDialog(null, "Mã nhân viên phải là số!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
        		}
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
        
        // --- BUTTON QUẢN LÝ VAI TRÒ ---
        btnQLVaiTro = new JButton("QL Vai Trò");
        btnQLVaiTro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lấy Frame cha (MainFrame) để làm nền cho Dialog
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(pnlNhanVien.this);
                
                // Mở Dialog QL Vai Trò
                new dlgQuanLyVaiTro(parent).setVisible(true);
                
                // Sau khi tắt Dialog thì reload lại Combobox Vai trò cho mới
                ShowDataCombo(); 
            }
        });
        btnQLVaiTro.setBounds(600, 125, 100, 23); 
        add(btnQLVaiTro);
        
        // --- BẢNG DỮ LIỆU ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 198, 649, 202);
        add(scrollPane);
        
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
		        try { 
		            int row = table.getSelectedRow(); 
		            String ma = (table.getModel().getValueAt(row,0)).toString(); 
		            ResultSet rs = nv.ShowNVTheoma(ma);
		            if(rs.next()) { 
		             txtMaNV.setText(rs.getString("MaNhanVien")); 
		             txtTenNV.setText(rs.getString("TenNhanVien")); 
		             txtTenDN.setText(rs.getString("TenDangNhap")); 
		             jcomMaVT.setSelectedItem(rs.getString("MaVaiTro")); 
		             txtTenVT.setText(rs.getString("TenVaiTro")); 
		            } 
		        } catch (SQLException e1) { 
		        	e1.printStackTrace();
		        }
			}
        });
        scrollPane.setViewportView(table);
        
        String []colsName = {"Mã NV", "Tên Nhân Viên", "Tên Đăng Nhập", "Vai Trò"}; 
        tableModel.setColumnIdentifiers(colsName);   
        table.setModel(tableModel);
        
        // --- KHỞI TẠO DỮ LIỆU ---
        ShowDataCombo(); // Load combobox trước
        ShowData();      // Load bảng sau
        setNull();
        setKhoa(true);
	}

	/* ===== HÀM XỬ LÝ SỰ KIỆN ===== */
	private void setKhoa(boolean a) {
		txtMaNV.setEnabled(!a);
		txtTenNV.setEnabled(!a);
		txtTenDN.setEnabled(!a);
		// txtTenVT luôn disable vì nó tự động theo combobox
		jcomMaVT.setEnabled(!a);
		
		btnLuu.setEnabled(!a);
		btnKluu.setEnabled(!a);
		btnThem.setEnabled(a);
		btnSua.setEnabled(a);
		btnXoa.setEnabled(a);
	}
	
	private void setNull() {
		txtMaNV.setText("");
		txtTenDN.setText("");
		txtTenNV.setText("");
		txtTenVT.setText("");
	}
	
    public void ClearData() { 
         int n = tableModel.getRowCount() - 1; 
         for(int i = n; i >= 0; i--) 
            tableModel.removeRow(i);          
    }
	
	public final void ShowData() throws SQLException {         
        ResultSet result = nv.ShowNhanVien(); 
        try {   
            ClearData(); 
            while(result.next()){  
                String rows[] = new String[4]; 
                rows[0] = result.getString(1);  
                rows[1] = result.getString(2);  
                rows[2] = result.getString(3);  
                rows[3] = result.getString(5); // Cột thứ 5 là TenVaiTro (do SQL join)
                tableModel.addRow(rows); 
            } 
        } catch (SQLException e) { 
        	e.printStackTrace();
        }  
    }
	
	public final void ShowData(String ml) throws SQLException {         
		ResultSet result = nv.ShowNVTheoten(ml);
		try {   
			ClearData(); 
			while(result.next()){  
				String rows[] = new String[4]; 
				rows[0] = result.getString(1);  
				rows[1] = result.getString(2);
				rows[2] = result.getString(3);  
			    rows[3] = result.getString(5);                 
			    tableModel.addRow(rows); 
			} 
		} catch (SQLException e) { 
			e.printStackTrace();
		}  
	}
	
	public void ShowTenvaitro(String ma) throws SQLException {         
        ResultSet result = nv.ShowVaitro(ma);           
        if(result.next()) { 
           txtTenVT.setText(result.getString("TenVaiTro")); 
        }
    }
	
	public final void ShowDataCombo() {         
        ResultSet result = null;           
        try { 
        	jcomMaVT.removeAllItems(); // Xóa dữ liệu cũ
            result = nv.ShowVaitro();            
            while(result.next()) {  
                jcomMaVT.addItem(result.getString("MaVaiTro"));
            } 
        } catch (SQLException e) { 
        	e.printStackTrace();
        }  
    }
}