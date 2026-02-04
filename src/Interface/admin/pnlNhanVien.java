package Interface.admin;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Process.MD5Util;
import Process.NhanVien;

// Class hỗ trợ lưu cặp Key-Value cho ComboBox
class VaiTroItem {
    int maVT;
    String tenVT;

    public VaiTroItem(int ma, String ten) {
        this.maVT = ma;
        this.tenVT = ten;
    }

    @Override
    public String toString() {
        return tenVT; 
    }
}

public class pnlNhanVien extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    private final NhanVien nv = new NhanVien();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private boolean cothem = true;
    
    // Components
    private JTextField txtTimKiem;
    private JTextField txtMaNV;
    private JTextField txtTenNV;
    private JTextField txtTenDN;
    private JPasswordField txtMatKhau;
    
    private JTable table;
    private JComboBox<VaiTroItem> jcomVaiTro; 
    
    private JButton btnThem, btnXoa, btnSua, btnLuu, btnKluu, btnQLVaiTro;

    public pnlNhanVien() throws SQLException {
        setLayout(null);
        setBackground(new Color(240, 248, 255)); // Nền xanh nhạt
        
        // --- KHỞI TẠO 2 VÙNG CHÍNH ---
        initPanelTrai(); // Bảng + Tìm kiếm
        initPanelPhai(); // Form nhập liệu + Nút bấm
        
        // Init Data
        ShowDataCombo();
        ShowData();
        setNull();
        setKhoa(true);
    }
    
    // ========================================================================
    // PHẦN 1: BÊN TRÁI (TÌM KIẾM + BẢNG) 
    // Rộng 600px
    // ========================================================================
    private void initPanelTrai() {
        JPanel pnlLeft = new JPanel(null);
        pnlLeft.setBackground(Color.WHITE);
        pnlLeft.setBounds(10, 10, 600, 650); 
        pnlLeft.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        add(pnlLeft);
        
        // --- 1.1 THANH TÌM KIẾM ---
        JLabel lblTim = new JLabel("Tìm kiếm:");
        lblTim.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblTim.setBounds(20, 20, 80, 30);
        pnlLeft.add(lblTim);
        
        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 13));
        // Thu ngắn ô nhập liệu lại một chút để nhường chỗ cho nút bấm to hơn
        txtTimKiem.setBounds(100, 20, 350, 30); 
        pnlLeft.add(txtTimKiem);
        
        JButton btnTim = new JButton("Tìm kiếm"); // Hiển thị đầy đủ chữ
        btnTim.setBounds(460, 20, 110, 30); // Rộng 110px cho thoải mái
        btnTim.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnTim.addActionListener(e -> {
            try { ShowData(txtTimKiem.getText()); } catch (SQLException ex) { ex.printStackTrace(); }
        });
        pnlLeft.add(btnTim);
        
        // --- 1.2 BẢNG DỮ LIỆU ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 70, 580, 560); 
        pnlLeft.add(scrollPane);
        
        table = new JTable();
        table.setRowHeight(28);
        table.setFont(new Font("Tahoma", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try { 
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        String ma = table.getModel().getValueAt(row, 0).toString();
                        ResultSet rs = nv.ShowNVTheoma(ma);
                        if(rs.next()) { 
                            txtMaNV.setText(rs.getString("MaNhanVien")); 
                            txtTenNV.setText(rs.getString("TenNhanVien")); 
                            txtTenDN.setText(rs.getString("TenDangNhap")); 
                            txtMatKhau.setText(""); 
                            
                            int maVT = rs.getInt("MaVaiTro");
                            setSelectedVaiTro(maVT);
                        }
                    }
                } catch (SQLException e1) { e1.printStackTrace(); }
            }
        });
        scrollPane.setViewportView(table);
        
        String []colsName = {"Mã NV", "Tên Nhân Viên", "Tài Khoản", "Vai Trò"}; 
        tableModel.setColumnIdentifiers(colsName);   
        table.setModel(tableModel);
        
        // Chỉnh độ rộng cột cho cân đối trong khung 580px
        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // Mã
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Tên
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // User
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Vai trò
    }
    
    // ========================================================================
    // PHẦN 2: BÊN PHẢI (FORM CHI TIẾT + NÚT)
    // ========================================================================
    private void initPanelPhai() {
        JPanel pnlRight = new JPanel(null);
        pnlRight.setBackground(new Color(250, 250, 250));
        pnlRight.setBounds(620, 10, 380, 650); 
        pnlRight.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(0, 102, 204), 1),
                " THÔNG TIN CHI TIẾT ",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 14), new Color(0, 102, 204)
        ));
        add(pnlRight);
        
        int xLabel = 20, xText = 120, widthText = 230, height = 30;
        int y = 50, gap = 50;
        
        // 1. Mã NV
        JLabel lblMa = new JLabel("Mã NV:");
        lblMa.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMa.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblMa);
        
        txtMaNV = new JTextField();
        txtMaNV.setBounds(xText, y, widthText, height);
        txtMaNV.setEditable(false); 
        txtMaNV.setBackground(new Color(230, 230, 230));
        pnlRight.add(txtMaNV);
        
        y += gap;
        // 2. Tên NV
        JLabel lblTen = new JLabel("Họ Tên:");
        lblTen.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTen.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblTen);
        
        txtTenNV = new JTextField();
        txtTenNV.setBounds(xText, y, widthText, height);
        pnlRight.add(txtTenNV);
        
        y += gap;
        // 3. Tên Đăng Nhập
        JLabel lblUser = new JLabel("Tài Khoản:");
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblUser.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblUser);
        
        txtTenDN = new JTextField();
        txtTenDN.setBounds(xText, y, widthText, height);
        pnlRight.add(txtTenDN);
        
        y += gap;
        // 4. Mật Khẩu
        JLabel lblPass = new JLabel("Mật Khẩu:");
        lblPass.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblPass.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblPass);
        
        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(xText, y, widthText, height);
        pnlRight.add(txtMatKhau);
        
        y += gap;
        // 5. Vai Trò
        JLabel lblRole = new JLabel("Vai Trò:");
        lblRole.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblRole.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblRole);
        
        jcomVaiTro = new JComboBox<>();
        jcomVaiTro.setBounds(xText, y, 130, height); // Thu hẹp ComboBox
        pnlRight.add(jcomVaiTro);
        
        btnQLVaiTro = new JButton("QL Vai Trò"); // Hiển thị đầy đủ
        btnQLVaiTro.setBounds(xText + 135, y, 95, height);
        btnQLVaiTro.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnQLVaiTro.setMargin(new Insets(0,0,0,0));
        btnQLVaiTro.addActionListener(e -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            new dlgQuanLyVaiTro(parent).setVisible(true);
            ShowDataCombo();
        });
        pnlRight.add(btnQLVaiTro);
        
        // --- CÁC NÚT CHỨC NĂNG ---
        int btnY = 350;
        int btnW = 90;
        int btnH = 35;
        int startXBtn = 40; 
        
        // Hàng 1
        btnThem = new JButton("Thêm");
        btnThem.setBounds(startXBtn, btnY, btnW, btnH);
        pnlRight.add(btnThem);
        
        btnSua = new JButton("Sửa");
        btnSua.setBounds(startXBtn + 100, btnY, btnW, btnH);
        pnlRight.add(btnSua);
        
        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(startXBtn + 200, btnY, btnW, btnH);
        pnlRight.add(btnXoa);
        
        // Hàng 2
        btnLuu = new JButton("Lưu");
        btnLuu.setBounds(80, btnY + 50, 100, btnH);
        pnlRight.add(btnLuu);
        
        btnKluu = new JButton("Hủy");
        btnKluu.setBounds(200, btnY + 50, 100, btnH);
        pnlRight.add(btnKluu);
        
        ganSuKienCacNut();
    }
    
    // ========================================================================
    // LOGIC XỬ LÝ
    // ========================================================================
    
    private void ganSuKienCacNut() {
        btnThem.addActionListener(e -> {
            setKhoa(false); setNull(); cothem = true;
            txtMaNV.setText("Tự động");
            txtMatKhau.setEnabled(true);
            txtMatKhau.setText("");
        });
        
        btnXoa.addActionListener(e -> {
            String ma = txtMaNV.getText(); 
            try { 
                if(ma.length() == 0 || ma.equals("Tự động"))              
                    JOptionPane.showMessageDialog(null, "Cần chọn 1 NV để xóa", "Thông báo", 1); 
                else if(JOptionPane.showConfirmDialog(null, "Xóa nhân viên " + ma + "?", "Thông báo", 2) == 0) {     
                    nv.DeleteNhanVien(ma);
                    ClearData(); ShowData(); setNull();
                } 
            } catch (SQLException ex) { ex.printStackTrace(); }
        });
        
        btnSua.addActionListener(e -> {
            String ml = txtMaNV.getText(); 
            if(ml.length() == 0 || ml.equals("Tự động")) 
                JOptionPane.showMessageDialog(null, "Chọn nhân viên cần sửa", "Thông báo", 1); 
            else { 
                setKhoa(false); cothem = false; 
                txtMatKhau.setEnabled(true);
            }
        });
        
        btnLuu.addActionListener(e -> {
            try {
                int ma = 0;
                if(!cothem) ma = Integer.parseInt(txtMaNV.getText());
                
                String ten = txtTenNV.getText(); 
                String tendn = txtTenDN.getText();
                String pass = new String(txtMatKhau.getPassword()); 
                
                VaiTroItem item = (VaiTroItem) jcomVaiTro.getSelectedItem();
                int vt = (item != null) ? item.maVT : 0;
                
                if(ten.length() == 0) {
                     JOptionPane.showMessageDialog(null, "Vui lòng nhập Tên NV", "Thông báo", 1);
                     return;
                }
                
                try { 
                    if(cothem) {    
                        if(pass.isEmpty()) pass = "123456"; 
                        String mkH = MD5Util.encryptMD5(pass);
                        nv.InsertNhanVien(0, ten, tendn, mkH, vt); 
                    } else { 
                        if(!pass.isEmpty()) {
                             nv.EditNhanVien(ma, ten, vt); 
                        } else {
                            nv.EditNhanVien(ma, ten, vt); 
                        }
                    }
                    ClearData(); ShowData(); 
                } catch (SQLException ex) { 
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Lỗi", 1); 
                }             
                setNull(); setKhoa(true); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi định dạng mã!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnKluu.addActionListener(e -> { setKhoa(true); setNull(); });
    }

    private void setSelectedVaiTro(int maVT) {
        for(int i=0; i<jcomVaiTro.getItemCount(); i++) {
            VaiTroItem item = jcomVaiTro.getItemAt(i);
            if(item.maVT == maVT) { jcomVaiTro.setSelectedIndex(i); break; }
        }
    }

    private void setKhoa(boolean a) {
        txtMaNV.setEnabled(false); 
        txtTenNV.setEnabled(!a);
        txtTenDN.setEnabled(!a);
        txtMatKhau.setEnabled(!a);
        jcomVaiTro.setEnabled(!a);
        
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
        txtMatKhau.setText("");
        if(jcomVaiTro.getItemCount() > 0) jcomVaiTro.setSelectedIndex(0);
    }
    
    public void ClearData() { 
         int n = tableModel.getRowCount() - 1; 
         for(int i = n; i >= 0; i--) tableModel.removeRow(i);      
    }
    
    public final void ShowData() throws SQLException {         
        ResultSet result = nv.ShowNhanVien(); 
        try {   
            ClearData(); 
            while(result.next()){  
                tableModel.addRow(new String[]{
                    result.getString(1), result.getString(2), result.getString(3), result.getString(5)
                }); 
            } 
        } catch (SQLException e) { e.printStackTrace(); }  
    }
    
    public final void ShowData(String ml) throws SQLException {         
        ResultSet result = nv.ShowNVTheoten(ml);
        try {   
            ClearData(); 
            while(result.next()){  
                tableModel.addRow(new String[]{
                    result.getString(1), result.getString(2), result.getString(3), result.getString(5)
                }); 
            } 
        } catch (SQLException e) { e.printStackTrace(); }  
    }
    
    public final void ShowDataCombo() {         
        ResultSet result = null;            
        try { 
            jcomVaiTro.removeAllItems();
            result = nv.ShowVaitro();             
            while(result.next()) {  
                int ma = result.getInt("MaVaiTro");
                String ten = result.getString("TenVaiTro");
                jcomVaiTro.addItem(new VaiTroItem(ma, ten));
            } 
        } catch (SQLException e) { e.printStackTrace(); }  
    }
}