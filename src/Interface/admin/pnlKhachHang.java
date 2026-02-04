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

import Process.KhachHang;

public class pnlKhachHang extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // Logic
    private final KhachHang kh = new KhachHang();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private boolean cothem = true;
    
    // Components
    private JTextField txtTimKiem;
    private JTextField txtMaKH;
    private JTextField txtTenKH;
    private JTextField txtSDT;
    private JTextArea txtDiaChi; // Dùng TextArea cho địa chỉ
    private JTable table;
    
    private JButton btnThem, btnXoa, btnSua, btnLuu, btnKluu;

    public pnlKhachHang() throws SQLException {
        setLayout(null);
        setBackground(new Color(240, 248, 255)); // Nền xanh nhạt
        
        // --- KHỞI TẠO 2 VÙNG CHÍNH ---
        initPanelTrai(); // Bảng + Tìm kiếm
        initPanelPhai(); // Form nhập liệu + Nút bấm
        
        // Init Data
        ShowData();
        setNull();
        setKhoa(true);
    }
    
    // ========================================================================
    // PHẦN 1: BÊN TRÁI (TÌM KIẾM + BẢNG) - Rộng 600px
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
        txtTimKiem.setBounds(100, 20, 350, 30);
        pnlLeft.add(txtTimKiem);
        
        JButton btnTim = new JButton("Tìm kiếm");
        btnTim.setBounds(460, 20, 110, 30);
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
                        ResultSet rs = kh.ShowKHTheoma(ma);
                        if(rs.next()) { 
                            txtMaKH.setText(rs.getString("MaKhachHang")); 
                            txtTenKH.setText(rs.getString("TenKhachHang")); 
                            txtSDT.setText(rs.getString("SoDienThoai")); 
                            txtDiaChi.setText(rs.getString("DiaChi"));
                        }
                    }
                } catch (SQLException e1) { e1.printStackTrace(); }
            }
        });
        scrollPane.setViewportView(table);
        
        String[] colsName = {"Mã KH", "Tên Khách Hàng", "SĐT", "Địa Chỉ"}; 
        tableModel.setColumnIdentifiers(colsName);   
        table.setModel(tableModel);
        
        // Chỉnh độ rộng cột
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
    }
    
    // ========================================================================
    // PHẦN 2: BÊN PHẢI (FORM CHI TIẾT + NÚT) - Rộng 380px, X=620
    // ========================================================================
    private void initPanelPhai() {
        JPanel pnlRight = new JPanel(null);
        pnlRight.setBackground(new Color(250, 250, 250));
        pnlRight.setBounds(620, 10, 380, 650); 
        pnlRight.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(0, 102, 204), 1),
                " THÔNG TIN KHÁCH HÀNG ",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 14), new Color(0, 102, 204)
        ));
        add(pnlRight);
        
        int xLabel = 20, xText = 120, widthText = 230, height = 30;
        int y = 50, gap = 50;
        
        // 1. Mã KH
        JLabel lblMa = new JLabel("Mã KH:");
        lblMa.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMa.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblMa);
        
        txtMaKH = new JTextField();
        txtMaKH.setBounds(xText, y, widthText, height);
        pnlRight.add(txtMaKH);
        
        y += gap;
        // 2. Tên KH
        JLabel lblTen = new JLabel("Tên KH:");
        lblTen.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTen.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblTen);
        
        txtTenKH = new JTextField();
        txtTenKH.setBounds(xText, y, widthText, height);
        pnlRight.add(txtTenKH);
        
        y += gap;
        // 3. SĐT
        JLabel lblSDT = new JLabel("Số ĐT:");
        lblSDT.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSDT.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblSDT);
        
        txtSDT = new JTextField();
        txtSDT.setBounds(xText, y, widthText, height);
        pnlRight.add(txtSDT);
        
        y += gap;
        // 4. Địa Chỉ (TextArea)
        JLabel lblDC = new JLabel("Địa Chỉ:");
        lblDC.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblDC.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblDC);
        
        txtDiaChi = new JTextArea();
        txtDiaChi.setLineWrap(true);
        txtDiaChi.setWrapStyleWord(true);
        txtDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        JScrollPane scrDC = new JScrollPane(txtDiaChi);
        scrDC.setBounds(xText, y, widthText, 80); // Cao hơn để nhập nhiều dòng
        pnlRight.add(scrDC);
        
        // --- CÁC NÚT CHỨC NĂNG ---
        int btnY = 320; // Dời xuống vì có TextArea
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
            txtMaKH.requestFocus();
        });
        
        btnXoa.addActionListener(e -> {
            String ma = txtMaKH.getText(); 
            try { 
                if(ma.length() == 0)              
                    JOptionPane.showMessageDialog(null, "Cần chọn 1 Khách hàng để xóa", "Thông báo", 1); 
                else if(JOptionPane.showConfirmDialog(null, "Bạn muốn xóa khách hàng " + ma + "?", "Thông báo", 2) == 0) {     
                    kh.DeleteKhachHang(ma);
                    ClearData(); ShowData(); setNull();
                } 
            } catch (SQLException ex) { ex.printStackTrace(); }
        });
        
        btnSua.addActionListener(e -> {
            String ml = txtMaKH.getText(); 
            if(ml.length() == 0) 
                JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần sửa", "Thông báo", 1); 
            else { 
                setKhoa(false); cothem = false; 
                txtMaKH.setEnabled(false); // Không sửa mã
                txtTenKH.requestFocus();
            }
        });
        
        btnLuu.addActionListener(e -> {
            try {
                if(txtMaKH.getText().isEmpty() || txtTenKH.getText().isEmpty()) {
                     JOptionPane.showMessageDialog(null, "Vui lòng nhập Mã và Tên KH", "Thông báo", 1);
                     return;
                }
                
                int ma = Integer.parseInt(txtMaKH.getText()); 
                String ten = txtTenKH.getText(); 
                String sdt = txtSDT.getText();
                String dc = txtDiaChi.getText();
                
                try { 
                    if(cothem) {    
                        kh.InsertKhachHang(ma, ten, sdt, dc); 
                    } else { 
                        kh.EditKhachHang(ma, ten, sdt, dc); 
                    }
                    ClearData(); ShowData(); 
                } catch (SQLException ex) { 
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại (Trùng mã)", "Lỗi", 1); 
                }             
                setNull(); setKhoa(true); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Mã khách hàng phải là số", "Lỗi", 1);
            }
        });
        
        btnKluu.addActionListener(e -> { setKhoa(true); setNull(); });
    }

    private void setKhoa(boolean a) {
        txtMaKH.setEnabled(!a);
        txtTenKH.setEnabled(!a);
        txtSDT.setEnabled(!a);
        txtDiaChi.setEnabled(!a);
        
        btnLuu.setEnabled(!a);
        btnKluu.setEnabled(!a);
        
        btnThem.setEnabled(a);
        btnSua.setEnabled(a);
        btnXoa.setEnabled(a);
    }
    
    private void setNull() {
        txtMaKH.setText("");
        txtSDT.setText("");
        txtTenKH.setText("");
        txtDiaChi.setText("");
        txtTimKiem.setText("");
    }
    
    public void ClearData() throws SQLException { 
         int n = tableModel.getRowCount() - 1; 
         for(int i = n; i >= 0; i--) tableModel.removeRow(i);      
    }
    
    public final void ShowData() throws SQLException {         
        ResultSet result = kh.ShowKhachHang(); 
        try {   
            ClearData(); 
            while(result.next()){  
                tableModel.addRow(new String[]{
                    result.getString(1), result.getString(2), result.getString(3), result.getString(4)
                }); 
            } 
        } catch (SQLException e) { e.printStackTrace(); }  
    }
    
    public final void ShowData(String ml) throws SQLException {         
        ResultSet result = kh.ShowSPTheoten(ml);
        try {   
            ClearData(); 
            while(result.next()){  
                tableModel.addRow(new String[]{
                    result.getString(1), result.getString(2), result.getString(3), result.getString(4)
                }); 
            } 
        } catch (SQLException e) { e.printStackTrace(); }  
    }
}