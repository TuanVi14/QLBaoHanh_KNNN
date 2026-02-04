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

import Process.SanPham;

// Class hỗ trợ lưu cặp Key-Value cho ComboBox Loại SP
class LoaiSPItem {
    int maLoai;
    String tenLoai;

    public LoaiSPItem(int ma, String ten) {
        this.maLoai = ma;
        this.tenLoai = ten;
    }

    @Override
    public String toString() {
        return tenLoai; 
    }
}

public class pnlSanPham extends JPanel {

    private static final long serialVersionUID = 1L;
    
    // Components
    private JTextField txtTimKiem;
    private JTextField txtMaSP;
    private JTextField txtTenSP;
    private JTextField txtThoiHan;
    private JTextArea txtCauHinh;
    private JTable table;
    private JComboBox<LoaiSPItem> jcomLoaiSP;
    
    private JButton btnThem, btnXoa, btnSua, btnLuu, btnKluu, btnEditLoaiSP;
    
    // Logic
    private final SanPham sp = new SanPham();
    private final DefaultTableModel tableModel = new DefaultTableModel();
    private boolean cothem = true;

    public pnlSanPham() throws SQLException {
        setLayout(null);
        setBackground(new Color(240, 248, 255)); // Nền xanh nhạt
        
        // --- KHỞI TẠO 2 VÙNG CHÍNH ---
        initPanelTrai(); 
        initPanelPhai(); 
        
        // Init Data
        ShowDataCombo();
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
                        ResultSet rs = sp.ShowSPTheoma(ma);
                        if(rs.next()) { 
                            txtMaSP.setText(rs.getString("MaModel")); 
                            txtTenSP.setText(rs.getString("TenSanPham")); 
                            txtCauHinh.setText(rs.getString("CauHinh"));
                            txtThoiHan.setText(rs.getString("ThoiHanBaoHanh"));
                            
                            int maLoai = rs.getInt("MaLoai");
                            setSelectedLoai(maLoai);
                        }
                    }
                } catch (SQLException e1) { e1.printStackTrace(); }
            }
        });
        scrollPane.setViewportView(table);
        
        String[] colsName = {"Mã SP", "Tên Sản Phẩm", "Cấu Hình", "Bảo Hành", "Loại"}; 
        tableModel.setColumnIdentifiers(colsName);   
        table.setModel(tableModel);
        
        // Chỉnh cột
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
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
                " CHI TIẾT SẢN PHẨM ",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 14), new Color(0, 102, 204)
        ));
        add(pnlRight);
        
        int xLabel = 20, xText = 120, widthText = 230, height = 30;
        int y = 50, gap = 50;
        
        // 1. Mã SP
        JLabel lblMa = new JLabel("Mã SP:");
        lblMa.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMa.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblMa);
        
        txtMaSP = new JTextField();
        txtMaSP.setBounds(xText, y, widthText, height);
        txtMaSP.setEditable(false);
        txtMaSP.setBackground(new Color(230, 230, 230));
        pnlRight.add(txtMaSP);
        
        y += gap;
        // 2. Tên SP
        JLabel lblTen = new JLabel("Tên SP:");
        lblTen.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTen.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblTen);
        
        txtTenSP = new JTextField();
        txtTenSP.setBounds(xText, y, widthText, height);
        pnlRight.add(txtTenSP);
        
        y += gap;
        // 3. Thời Hạn BH
        JLabel lblBH = new JLabel("Bảo Hành (tháng):");
        lblBH.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblBH.setBounds(xLabel, y, 120, height); // Label dài hơn chút
        pnlRight.add(lblBH);
        
        txtThoiHan = new JTextField();
        txtThoiHan.setBounds(xText + 20, y, widthText - 20, height);
        pnlRight.add(txtThoiHan);
        
        y += gap;
        // 4. Loại SP
        JLabel lblLoai = new JLabel("Loại SP:");
        lblLoai.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblLoai.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblLoai);
        
        jcomLoaiSP = new JComboBox<>();
        jcomLoaiSP.setBounds(xText, y, 130, height);
        pnlRight.add(jcomLoaiSP);
        
        btnEditLoaiSP = new JButton("QL Loại");
        btnEditLoaiSP.setBounds(xText + 135, y, 95, height);
        btnEditLoaiSP.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnEditLoaiSP.setMargin(new Insets(0,0,0,0));
        btnEditLoaiSP.addActionListener(e -> {
            JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
            new dlgQuanLyLoaiSP(parent).setVisible(true);
            ShowDataCombo();
        });
        pnlRight.add(btnEditLoaiSP);
        
        y += gap;
        // 5. Cấu Hình (TextArea)
        JLabel lblCH = new JLabel("Cấu Hình:");
        lblCH.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblCH.setBounds(xLabel, y, 90, height);
        pnlRight.add(lblCH);
        
        txtCauHinh = new JTextArea();
        txtCauHinh.setLineWrap(true);
        txtCauHinh.setWrapStyleWord(true);
        txtCauHinh.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        JScrollPane scrCH = new JScrollPane(txtCauHinh);
        scrCH.setBounds(xText, y, widthText, 80); // Cao hơn để nhập nhiều dòng
        pnlRight.add(scrCH);
        
        // --- CÁC NÚT CHỨC NĂNG ---
        int btnY = 380; // Dời xuống vì có TextArea
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
            txtMaSP.setText("Tự động");
        });
        
        btnXoa.addActionListener(e -> {
            String ma = txtMaSP.getText(); 
            try { 
                if(ma.length() == 0 || ma.equals("Tự động"))              
                    JOptionPane.showMessageDialog(null, "Cần chọn 1 SP để xóa", "Thông báo", 1); 
                else if(JOptionPane.showConfirmDialog(null, "Xóa sản phẩm " + ma + "?", "Thông báo", 2) == 0) {     
                    sp.DeleteSanPham(ma);
                    ClearData(); ShowData(); setNull();
                } 
            } catch (SQLException ex) { ex.printStackTrace(); }
        });
        
        btnSua.addActionListener(e -> {
            String ml = txtMaSP.getText(); 
            if(ml.length() == 0 || ml.equals("Tự động")) 
                JOptionPane.showMessageDialog(null, "Chọn sản phẩm cần sửa", "Thông báo", 1); 
            else { 
                setKhoa(false); cothem = false; 
            }
        });
        
        btnLuu.addActionListener(e -> {
            try {
                int ma = 0;
                if(!cothem) ma = Integer.parseInt(txtMaSP.getText());
                
                String ten = txtTenSP.getText(); 
                String ch = txtCauHinh.getText();
                int tg = 0;
                if(!txtThoiHan.getText().isEmpty()) tg = Integer.parseInt(txtThoiHan.getText());
                
                LoaiSPItem item = (LoaiSPItem) jcomLoaiSP.getSelectedItem();
                int vt = (item != null) ? item.maLoai : 0;
                
                if(ten.length() == 0) {
                     JOptionPane.showMessageDialog(null, "Vui lòng nhập Tên SP", "Thông báo", 1);
                     return;
                }
                
                try { 
                    if(cothem) {    
                        sp.InsertSanPham(0, ten, ch, tg, vt); 
                    } else { 
                        sp.EditSanPham(ma, ten, ch, tg);
                    }
                    ClearData(); ShowData(); 
                } catch (SQLException ex) { 
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage(), "Lỗi", 1); 
                }             
                setNull(); setKhoa(true); 
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnKluu.addActionListener(e -> { setKhoa(true); setNull(); });
    }

    private void setSelectedLoai(int maLoai) {
        for(int i=0; i<jcomLoaiSP.getItemCount(); i++) {
            LoaiSPItem item = jcomLoaiSP.getItemAt(i);
            if(item.maLoai == maLoai) { jcomLoaiSP.setSelectedIndex(i); break; }
        }
    }

    private void setKhoa(boolean a) {
        txtMaSP.setEnabled(false); // Luôn khóa
        txtTenSP.setEnabled(!a);
        txtCauHinh.setEnabled(!a);
        txtThoiHan.setEnabled(!a);
        jcomLoaiSP.setEnabled(!a);
        
        btnLuu.setEnabled(!a);
        btnKluu.setEnabled(!a);
        
        btnThem.setEnabled(a);
        btnSua.setEnabled(a);
        btnXoa.setEnabled(a);
    }
    
    private void setNull() {
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtCauHinh.setText("");
        txtThoiHan.setText("");
        if(jcomLoaiSP.getItemCount() > 0) jcomLoaiSP.setSelectedIndex(0);
    }
    
    public void ClearData() { 
         int n = tableModel.getRowCount() - 1; 
         for(int i = n; i >= 0; i--) tableModel.removeRow(i);      
    }
    
    public final void ShowData() throws SQLException {         
        ResultSet result = sp.ShowSanPham(); 
        try {   
            ClearData(); 
            while(result.next()){  
                tableModel.addRow(new String[]{
                    result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(6)
                }); 
            } 
        } catch (SQLException e) { e.printStackTrace(); }  
    }
    
    public final void ShowData(String ml) throws SQLException {         
        ResultSet result = sp.ShowSPTheoten(ml);
        try {   
            ClearData(); 
            while(result.next()){  
                tableModel.addRow(new String[]{
                    result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(6)
                }); 
            } 
        } catch (SQLException e) { e.printStackTrace(); }  
    }
    
    public final void ShowDataCombo() {         
        ResultSet result = null;            
        try { 
            jcomLoaiSP.removeAllItems();
            result = sp.ShowLoaiSP();             
            while(result.next()) {  
                int ma = result.getInt("MaLoai");
                String ten = result.getString("TenLoai");
                jcomLoaiSP.addItem(new LoaiSPItem(ma, ten));
            } 
        } catch (SQLException e) { e.printStackTrace(); }  
    }
}