package Interface.admin;

import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Process.KhachHang;

public class pnlKhachHang extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private final KhachHang kh = new KhachHang();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private boolean cothem = true;
    
    private JTextField txtTimKiem;
    private JTextField txtMaKH;
    private JTextField txtTenKH;
    private JTextField txtSDT;
    private JTextArea txtDiaChi;
    private JTable table;
    
    private JButton btnThem, btnXoa, btnSua, btnLuu, btnKluu;

    /**
     * Create the panel.
     * @throws SQLException 
     */
    public pnlKhachHang() throws SQLException {
        // Sử dụng Layout tự do (Truyền thống)
        setLayout(null);
        setBackground(new Color(240, 248, 255)); // Nền xanh nhạt dịu mắt
        
        // --- 1. KHU VỰC TÌM KIẾM (Trên cùng) ---
        // Kéo dài thanh tìm kiếm ra giữa màn hình
        JLabel lblTimKiem = new JLabel("Tìm kiếm Khách hàng:");
        lblTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTimKiem.setBounds(50, 20, 160, 30);
        add(lblTimKiem);
        
        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBounds(210, 20, 600, 30); // Rộng hơn
        add(txtTimKiem);
        
        JButton btnTimKiem = createSimpleButton("Tìm", new Color(70, 130, 180));
        btnTimKiem.setBounds(820, 20, 100, 30);
        btnTimKiem.addActionListener(e -> {
            try {
                ShowData(txtTimKiem.getText().trim());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        add(btnTimKiem);
        
        // --- 2. KHU VỰC NHẬP LIỆU (Giãn cách rộng rãi) ---
        int yRow1 = 70;
        int yRow2 = 120;
        int col1Label = 50, col1Text = 170;
        int col2Label = 550, col2Text = 670;
        
        // Dòng 1: Mã KH & Tên KH
        JLabel lblMa = new JLabel("Mã Khách Hàng:");
        lblMa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMa.setBounds(col1Label, yRow1, 120, 30);
        add(lblMa);
        
        txtMaKH = new JTextField();
        txtMaKH.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaKH.setBounds(col1Text, yRow1, 300, 30); // Rộng 300
        add(txtMaKH);
        
        JLabel lblTen = new JLabel("Tên Khách Hàng:");
        lblTen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTen.setBounds(col2Label, yRow1, 120, 30);
        add(lblTen);
        
        txtTenKH = new JTextField();
        txtTenKH.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTenKH.setBounds(col2Text, yRow1, 350, 30); // Rộng 350
        add(txtTenKH);
        
        // Dòng 2: SĐT & Địa chỉ
        JLabel lblSDT = new JLabel("Số Điện Thoại:");
        lblSDT.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSDT.setBounds(col1Label, yRow2, 120, 30);
        add(lblSDT);
        
        txtSDT = new JTextField();
        txtSDT.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSDT.setBounds(col1Text, yRow2, 300, 30);
        add(txtSDT);
        
        JLabel lblDiaChi = new JLabel("Địa Chỉ:");
        lblDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDiaChi.setBounds(col2Label, yRow2, 100, 30);
        add(lblDiaChi);
        
        txtDiaChi = new JTextArea();
        txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDiaChi.setLineWrap(true);
        txtDiaChi.setWrapStyleWord(true);
        
        JScrollPane scrDiaChi = new JScrollPane(txtDiaChi);
        scrDiaChi.setBounds(col2Text, yRow2, 350, 60); // Cao hơn chút để nhập địa chỉ
        add(scrDiaChi);
        
        // --- 3. CÁC NÚT CHỨC NĂNG (Căn giữa, to rõ) ---
        int btnY = 200;
        int btnW = 110;
        int btnH = 35;
        int startX = 250; // Điểm bắt đầu để căn giữa
        int gap = 30;     // Khoảng cách giữa các nút
        
        btnThem = createSimpleButton("Thêm", new Color(46, 139, 87)); // Xanh lá
        btnThem.setBounds(startX, btnY, btnW, btnH);
        add(btnThem);
        
        btnSua = createSimpleButton("Sửa", new Color(255, 165, 0)); // Cam
        btnSua.setBounds(startX + (btnW + gap) * 1, btnY, btnW, btnH);
        add(btnSua);
        
        btnXoa = createSimpleButton("Xóa", new Color(220, 53, 69)); // Đỏ
        btnXoa.setBounds(startX + (btnW + gap) * 2, btnY, btnW, btnH);
        add(btnXoa);
        
        btnLuu = createSimpleButton("Lưu", new Color(0, 123, 255)); // Xanh dương
        btnLuu.setBounds(startX + (btnW + gap) * 3, btnY, btnW, btnH);
        add(btnLuu);
        
        btnKluu = createSimpleButton("Hủy", new Color(108, 117, 125)); // Xám
        btnKluu.setBounds(startX + (btnW + gap) * 4, btnY, btnW, btnH);
        add(btnKluu);
        
        ganSuKienCacNut(); // Tách logic ra hàm riêng cho gọn code
        
        // --- 4. BẢNG DỮ LIỆU (Mở rộng tối đa) ---
        JScrollPane scrollPane = new JScrollPane();
        // Tọa độ mới: Rộng 1030px, Cao 350px -> Phù hợp màn hình 1280
        scrollPane.setBounds(20, 260, 1030, 350); 
        add(scrollPane);
        
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(26);
        
        // Header bảng đẹp hơn
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.BLACK); // Chữ đen theo yêu cầu
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try { 
                    int row = table.getSelectedRow(); 
                    String ma = (table.getModel().getValueAt(row, 0)).toString(); 
                    ResultSet rs = kh.ShowKHTheoma(ma); 
                    if(rs.next()) { 
                     txtMaKH.setText(rs.getString("MaKhachHang")); 
                     txtTenKH.setText(rs.getString("TenKhachHang")); 
                     txtSDT.setText(rs.getString("SoDienThoai"));  
                     txtDiaChi.setText(rs.getString("DiaChi")); 
                    } 
                } catch (SQLException e1) { e1.printStackTrace(); }
            }
        });
        scrollPane.setViewportView(table);
        
        String []colsName = {"Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Địa Chỉ"}; 
        tableModel.setColumnIdentifiers(colsName);   
        table.setModel(tableModel);
        
        // Khởi tạo ban đầu
        setNull();
        setKhoa(true);
        ShowData();
    }

    // --- HÀM TẠO NÚT ĐƠN GIẢN (TRUYỀN THỐNG) ---
    private JButton createSimpleButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.BLACK); 
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        // Không Override UI -> Để Java tự xử lý màu xám khi Disable
        return btn;
    }

    // --- LOGIC SỰ KIỆN ---
    private void ganSuKienCacNut() {
        btnThem.addActionListener(e -> {
            setKhoa(false);
            setNull();
            cothem = true;
            txtTenKH.requestFocus();
        });

        btnXoa.addActionListener(e -> {
            String ma = txtMaKH.getText(); 
            try { 
                if(ma.length() == 0)              
                    JOptionPane.showMessageDialog(null, "Cần chọn 1 Khách hàng để xóa", "Thông báo", 1); 
                else { 
                    if(JOptionPane.showConfirmDialog(null, "Bạn muốn xóa khách hàng " + ma + " này hay không?", "Thông báo", 2) == 0) {     
                        kh.DeleteKhachHang(ma);
                        ClearData(); ShowData(); setNull();
                    } 
                 } 
            } catch (SQLException ex) { Logger.getLogger(pnlKhachHang.class.getName()).log(Level.SEVERE, null, ex); }
        });

        btnSua.addActionListener(e -> {
            String ml = txtMaKH.getText(); 
            if(ml.length() == 0) 
                JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần sửa", "Thông báo", 1); 
            else { 
                setKhoa(false);
                cothem = false;
                txtMaKH.setEnabled(false);
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
                    if(cothem) kh.InsertKhachHang(ma, ten, sdt, dc); 
                    else kh.EditKhachHang(ma, ten, sdt, dc); 
                    ClearData(); ShowData(); 
                } catch (SQLException ex) { 
                   JOptionPane.showMessageDialog(null, "Cập nhật thất bại (Trùng mã)", "Lỗi", 1); 
                }             
                setNull(); setKhoa(true); 
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Mã khách hàng phải là số", "Lỗi", 1);
            }
        });

        btnKluu.addActionListener(e -> {
            setKhoa(true);
            setNull();
        });
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