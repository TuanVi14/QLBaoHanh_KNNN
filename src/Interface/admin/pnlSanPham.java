package Interface.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Process.SanPham;

public class pnlSanPham extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // Components
    private JTextField txtTimKiem;
    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtThoiHan;
    private JTextField txtTenLoai;
    private JTable table;
    private JButton btnThem, btnXoa, btnSua, btnLuu, btnKluu, btnEditloaisp;
    private JTextArea txtCauHinh;
    private JComboBox<String> jcomMaLoai;
    
    // Logic
    private final SanPham sp = new SanPham();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private boolean cothem = true;

    /**
     * Create the panel.
     * @throws SQLException 
     */
    public pnlSanPham() throws SQLException {
        setLayout(null);
        
        // --- TÌM KIẾM ---
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
                String tsp = txtTimKiem.getText();
                try {
                    ShowData(tsp);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnTimKiem.setBounds(538, 27, 89, 23);
        add(btnTimKiem);
        
        // --- FORM NHẬP LIỆU ---
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
        txtCauHinh.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm viền cho dễ nhìn
        add(txtCauHinh);
        
        JLabel lblNewLabel_4 = new JLabel("Thời Hạn BH:");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_4.setBounds(35, 192, 114, 14);
        add(lblNewLabel_4);
        
        txtThoiHan = new JTextField();
        txtThoiHan.setBounds(142, 190, 86, 20);
        add(txtThoiHan);
        txtThoiHan.setColumns(10);
        
        JLabel lblNewLabel_5 = new JLabel("Mã Loại:");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_5.setBounds(306, 193, 49, 14);
        add(lblNewLabel_5);
        
        jcomMaLoai = new JComboBox<>();
        jcomMaLoai.setBounds(371, 189, 57, 22);
        jcomMaLoai.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(jcomMaLoai.getSelectedItem() != null) {
                    String ml = jcomMaLoai.getSelectedItem().toString();
                    try {
                        ShowTenvaitro(ml); // Hàm hiển thị tên loại khi chọn mã
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        add(jcomMaLoai);
        
        txtTenLoai = new JTextField();
        txtTenLoai.setBounds(450, 190, 177, 20);
        txtTenLoai.setEditable(false); // Không cho nhập tay tên loại
        add(txtTenLoai);
        txtTenLoai.setColumns(10);
        
        // --- CÁC NÚT CHỨC NĂNG ---
        btnThem = new JButton("Thêm");
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setKhoa(false);
                setNull();
                cothem = true;
            }
        });
        btnThem.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnThem.setBounds(18, 217, 89, 23);
        add(btnThem);
        
        btnSua = new JButton("Sửa");
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ml = txtMaSP.getText(); 
                if(ml.length() == 0) // Chưa chọn Mã loại              
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần sửa", "Thông báo", 1); 
                else { 
                    setKhoa(false); // Mở khóa các TextField  
                    cothem = false; // Gán cothem=false để ghi nhận trạng thái là sửa 
                    txtMaSP.setEnabled(false); // Mã không được sửa
                }
            }
        });
        btnSua.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnSua.setBounds(117, 217, 89, 23);
        add(btnSua);
        
        btnXoa = new JButton("Xóa");
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ma = txtMaSP.getText(); 
                try { 
                    if(ma.length() == 0)              
                        JOptionPane.showMessageDialog(null, "Cần chọn 1 SP để xóa", "Thông báo", 1); 
                    else { 
                        if(JOptionPane.showConfirmDialog(null, "Bạn muốn xóa SP " + ma + " này hay không?", "Thông báo", 2) == 0) {     
                            sp.DeleteSanPham(ma); // Gọi hàm xóa
                            ClearData();
                            ShowData();
                            setNull();
                        } 
                     } 
                } catch (SQLException ex) { 
                    Logger.getLogger(pnlSanPham.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnXoa.setBounds(216, 217, 89, 23);
        add(btnXoa);
        
        btnLuu = new JButton("Lưu");
        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Kiểm tra dữ liệu đầu vào
                    if(txtMaSP.getText().isEmpty() || txtTenSP.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập Mã SP và Tên SP", "Thông báo", 1);
                        return;
                    }

                    int ma = Integer.parseInt(txtMaSP.getText()); 
                    String ten = txtTenSP.getText(); 
                    String ch = txtCauHinh.getText();
                    int tg = 0, vt = 0;
                    
                    if(!txtThoiHan.getText().isEmpty()) 
                        tg = Integer.parseInt(txtThoiHan.getText());
                    
                    if(jcomMaLoai.getSelectedItem() != null)
                        vt = Integer.parseInt(jcomMaLoai.getSelectedItem().toString()); 
                     
                    if(cothem == true) { // Lưu cho thêm mới    
                        sp.InsertSanPham(ma, ten, ch, tg, vt); 
                    } else { // Lưu cho sửa 
                        sp.EditSanPham(ma, ten, ch, tg); 
                    }
                    
                    ClearData();
                    ShowData();
                    setNull(); 
                    setKhoa(true);
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Mã SP và Thời hạn BH phải là số!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) { 
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại: " + ex.getMessage(), "Thông báo", 1); 
                }             
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
        
        // --- BUTTON GỌI DIALOG LOẠI SẢN PHẨM ---
        btnEditloaisp = new JButton("QL Loại SP");
        btnEditloaisp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lấy Frame cha để mở Dialog
                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(pnlSanPham.this);
                new dlgQuanLyLoaiSP(parent).setVisible(true);
                
                // Sau khi đóng dialog thì load lại combobox
                ShowDataCombo();
            }
        });
        btnEditloaisp.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnEditloaisp.setBounds(524, 218, 103, 23);
        add(btnEditloaisp);
        
        // --- BẢNG DỮ LIỆU ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(35, 251, 592, 149);
        add(scrollPane);
        
        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try { 
                    int row = table.getSelectedRow(); 
                    String ma = (table.getModel().getValueAt(row,0)).toString(); 
                    ResultSet rs = sp.ShowSPTheoma(ma);
                    if(rs.next()) { 
                        txtMaSP.setText(rs.getString("MaModel")); 
                        txtTenSP.setText(rs.getString("TenSanPham")); 
                        txtCauHinh.setText(rs.getString("CauHinh"));
                        txtThoiHan.setText(rs.getString("ThoiHanBaoHanh"));
                        jcomMaLoai.setSelectedItem(rs.getString("MaLoai")); 
                        txtTenLoai.setText(rs.getString("TenLoai")); 
                    } 
                } catch (SQLException e1) { 
                    e1.printStackTrace();
                }
            }
        });
        scrollPane.setViewportView(table);
        
        String[] colsName = {"Mã SP", "Tên SP", "Cấu hình", "Thời Hạn BH", "Loại SP"}; 
        tableModel.setColumnIdentifiers(colsName);   
        table.setModel(tableModel);
        
        // --- KHỞI TẠO DỮ LIỆU ---
        ShowDataCombo(); // Load combobox trước
        ShowData();      // Load bảng sau
        setKhoa(true);
        setNull();
    }

    /* ===== CÁC HÀM HỖ TRỢ ===== */
    
    private void setKhoa(boolean a) {
        txtMaSP.setEnabled(!a);
        txtTenSP.setEnabled(!a);
        txtCauHinh.setEnabled(!a);
        txtThoiHan.setEnabled(!a);
        // txtTenLoai luôn disable vì nó tự động theo combobox
        jcomMaLoai.setEnabled(!a); 
        
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
        ResultSet result = null;           
        try { 
            jcomMaLoai.removeAllItems(); // Xóa cũ trước khi load mới
            result = sp.ShowLoaiSP();            
            while(result.next()){  
                jcomMaLoai.addItem(result.getString("MaLoai"));
            } 
        } catch (SQLException e) { 
            e.printStackTrace();
        }  
    }
    
    public void ClearData() { 
         int n = tableModel.getRowCount() - 1; 
         for(int i = n; i >= 0; i--) 
            tableModel.removeRow(i);          
    }
    
    public final void ShowData() throws SQLException {         
        ResultSet result = sp.ShowSanPham(); 
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
        } catch (SQLException e) { 
            e.printStackTrace();
        }  
    }
    
    public final void ShowData(String ml) throws SQLException {         
        ResultSet result = sp.ShowSPTheoten(ml);
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
        } catch (SQLException e) { 
            e.printStackTrace();
        }  
    }
    
    public void ShowTenvaitro(String ma) throws SQLException {         
        ResultSet result = sp.ShowLoaiSP(ma);           
        if(result.next()){ 
           txtTenLoai.setText(result.getString("TenLoai")); 
        }
    }
}