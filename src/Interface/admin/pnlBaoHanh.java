package Interface.admin;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Process.BaoHanh; 

public class pnlBaoHanh extends JPanel {

    private static final long serialVersionUID = 1L;

    // Components
    private JTextField txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> jcomTrangThai;

    // Components Chi tiết
    private JTextField txtMaPhieu, txtKhachHang, txtSanPham, txtNgayNhan, txtTrangThai;
    private JTextArea txtGhiChu;

    // Logic & Data
    private final BaoHanh bh = new BaoHanh();
    
    // Biến lưu họ tên người đang đăng nhập để in vào phiếu
    private String tenNhanVienLapPhieu = "Admin"; 

    /**
     * Constructor 1: Nhận họ tên nhân viên từ MainFrame (Dùng cái này)
     */
    public pnlBaoHanh(String tenNV) {
        if (tenNV != null && !tenNV.isEmpty()) {
            this.tenNhanVienLapPhieu = tenNV;
        }
        initUI();
    }
    
    /**
     * Constructor 2: Mặc định (Tránh lỗi code cũ)
     */
    public pnlBaoHanh() {
        initUI();
    }

    private void initUI() {
        setLayout(null); 
        setBackground(new Color(240, 248, 255)); 
        setSize(1100, 650); 

        khoiTaoGiaoDienTrai(); 
        khoiTaoGiaoDienPhai(); 
        
        try {
            ShowData();         
            ShowDataCombo();    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- PHẦN 1: GIAO DIỆN TRÁI ---
    private void khoiTaoGiaoDienTrai() {
        JPanel pnlControl = new JPanel(null);
        pnlControl.setBackground(Color.WHITE);
        pnlControl.setBounds(10, 10, 700, 100);
        pnlControl.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        add(pnlControl);

        // Tìm kiếm
        JLabel lblKeyword = new JLabel("Từ khóa:");
        lblKeyword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblKeyword.setForeground(Color.BLACK); 
        lblKeyword.setBounds(20, 15, 80, 25);
        pnlControl.add(lblKeyword);
        
        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTimKiem.setForeground(Color.BLACK);
        txtTimKiem.setBounds(100, 15, 250, 25);
        txtTimKiem.setToolTipText("Nhập Tên Khách, Tên Máy, Serial hoặc Ngày...");
        txtTimKiem.addActionListener(e -> xuLyTimKiem());
        pnlControl.add(txtTimKiem);
        
        JButton btnTimKiem = createButton("Tìm Kiếm", new Color(70, 130, 180), Color.WHITE);
        btnTimKiem.setBounds(370, 15, 100, 25);
        btnTimKiem.addActionListener(e -> xuLyTimKiem());
        pnlControl.add(btnTimKiem);
        
        JButton btnLamMoi = createButton("Làm Mới", new Color(119, 136, 153), Color.WHITE);
        btnLamMoi.setBounds(480, 15, 100, 25);
        btnLamMoi.addActionListener(e -> {
            try {
                txtTimKiem.setText("");
                jcomTrangThai.setSelectedIndex(0);
                ShowData();
                clearPanelPhai();
            } catch (SQLException ex) { ex.printStackTrace(); }
        });
        pnlControl.add(btnLamMoi);

        // Lọc trạng thái
        JLabel lblTrangThai = new JLabel("Trạng Thái:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTrangThai.setForeground(Color.BLACK);
        lblTrangThai.setBounds(20, 55, 80, 25);
        pnlControl.add(lblTrangThai);
        
        jcomTrangThai = new JComboBox<>();
        jcomTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jcomTrangThai.setForeground(Color.BLACK);
        jcomTrangThai.setBounds(100, 55, 150, 25);
        jcomTrangThai.addActionListener(e -> {
            try {
                String selected = jcomTrangThai.getSelectedItem() != null ? jcomTrangThai.getSelectedItem().toString() : "Tất cả";
                if (selected.equals("Tất cả")) ShowData();
                else ShowDataTheoTrangThai(selected);
            } catch (SQLException ex) { ex.printStackTrace(); }
        });
        pnlControl.add(jcomTrangThai);
        
        // --- NÚT IN PHIẾU BẢO HÀNH ---
        JButton btnInPhieu = createButton("In Phiếu BH", new Color(255, 140, 0), Color.WHITE); // Màu Cam
        btnInPhieu.setBounds(370, 55, 100, 25);
        btnInPhieu.addActionListener(e -> xuLyInPhieu());
        pnlControl.add(btnInPhieu);

        // Bảng
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 120, 700, 480);
        add(scrollPane);
        
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25);
        table.setForeground(Color.BLACK); 
        
        String[] columns = {"Mã Phiếu", "Khách Hàng", "Sản Phẩm", "Ngày Nhận", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table.setModel(tableModel);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.BLACK);
        
        scrollPane.setViewportView(table);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    try {
                        int maPhieu = Integer.parseInt(table.getValueAt(row, 0).toString());
                        hienThiChiTietSangPhai(maPhieu);
                    } catch (Exception ex) { ex.printStackTrace(); }
                }
            }
        });
    }

    // --- PHẦN 2: GIAO DIỆN PHẢI ---
    private void khoiTaoGiaoDienPhai() {
        JPanel pnlChiTiet = new JPanel(null);
        pnlChiTiet.setBackground(Color.WHITE);
        pnlChiTiet.setBounds(720, 10, 320, 590);
        pnlChiTiet.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(new Color(0, 102, 204), 1), " CHI TIẾT PHIẾU ",
            TitledBorder.CENTER, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14), new Color(0, 102, 204)
        ));
        add(pnlChiTiet);

        int y = 40; int gap = 50;
        
        pnlChiTiet.add(createLabel("Mã Phiếu:", 20, y));
        txtMaPhieu = createTextField(110, y, 180);
        
        y += gap;
        pnlChiTiet.add(createLabel("Khách Hàng:", 20, y));
        txtKhachHang = createTextField(110, y, 180);
        
        y += gap;
        pnlChiTiet.add(createLabel("Sản Phẩm:", 20, y));
        txtSanPham = createTextField(110, y, 180);
        
        y += gap;
        pnlChiTiet.add(createLabel("Ngày Nhận:", 20, y));
        txtNgayNhan = createTextField(110, y, 180);
        
        y += gap;
        pnlChiTiet.add(createLabel("Trạng Thái:", 20, y));
        txtTrangThai = createTextField(110, y, 180);
        txtTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtTrangThai.setForeground(Color.RED);
        
        y += gap;
        pnlChiTiet.add(createLabel("Mô tả lỗi:", 20, y));
        txtGhiChu = new JTextArea();
        txtGhiChu.setLineWrap(true);
        txtGhiChu.setWrapStyleWord(true);
        txtGhiChu.setEditable(false);
        txtGhiChu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        JScrollPane scrGhiChu = new JScrollPane(txtGhiChu);
        scrGhiChu.setBounds(110, y, 180, 80);
        pnlChiTiet.add(scrGhiChu);
        
        pnlChiTiet.add(txtMaPhieu);
        pnlChiTiet.add(txtKhachHang);
        pnlChiTiet.add(txtSanPham);
        pnlChiTiet.add(txtNgayNhan);
        pnlChiTiet.add(txtTrangThai);
    }

    // --- LOGIC XỬ LÝ DỮ LIỆU ---

    public void ShowData() throws SQLException {
        ResultSet rs = bh.ShowPhieuBH();
        doDuLieuVaoBang(rs);
    }

    private void xuLyTimKiem() {
        try {
            ResultSet rs = bh.ShowPhieuBHTheoTK(txtTimKiem.getText().trim());
            doDuLieuVaoBang(rs);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
    
    public void ShowDataTheoTrangThai(String trangThai) throws SQLException {
        ResultSet rs = bh.locPhieuBaoHanhTheoTrangThai(trangThai);
        doDuLieuVaoBang(rs);
    }
    
    private void doDuLieuVaoBang(ResultSet rs) throws SQLException {
        tableModel.setRowCount(0);
        while (rs.next()) {
            String[] row = new String[5];
            row[0] = rs.getString("MaPhieu");
            row[1] = rs.getString("TenKhachHang");
            row[2] = rs.getString("TenSanPham");
            row[3] = rs.getString("NgayTiepNhan");
            row[4] = rs.getString("TrangThai");
            tableModel.addRow(row);
        }
    }

    public void ShowDataCombo() throws SQLException {
        jcomTrangThai.removeAllItems();
        jcomTrangThai.addItem("Tất cả");
        ResultSet rs = bh.ShowTrangThai();
        while (rs.next()) {
            jcomTrangThai.addItem(rs.getString("TrangThai"));
        }
    }

    private void hienThiChiTietSangPhai(int maPhieu) {
        try {
            ResultSet rs = bh.ShowPhieuTheoMa(maPhieu);
            if (rs.next()) {
                txtMaPhieu.setText(rs.getString("MaPhieu"));
                txtKhachHang.setText(rs.getString("TenKhachHang"));
                txtSanPham.setText(rs.getString("TenSanPham"));
                txtNgayNhan.setText(rs.getString("NgayTiepNhan"));
                txtTrangThai.setText(rs.getString("TrangThai"));
                String loi = rs.getString("LoiBaoCao");
                txtGhiChu.setText(loi != null ? loi : "Không có mô tả");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void clearPanelPhai() {
        txtMaPhieu.setText("");
        txtKhachHang.setText("");
        txtSanPham.setText("");
        txtNgayNhan.setText("");
        txtTrangThai.setText("");
        txtGhiChu.setText("");
    }

    // ========================================================================
    // PHẦN QUAN TRỌNG: XUẤT PHIẾU BẢO HÀNH (HTML -> PDF Style)
    // ========================================================================
    private void xuLyInPhieu() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu để in!");
            return;
        }
        
        try {
            int maPhieu = Integer.parseInt(table.getValueAt(row, 0).toString());
            // Lấy thông tin chi tiết
            ResultSet rs = bh.ShowPhieuTheoMa(maPhieu);
            
            if (rs.next()) {
                // Lấy dữ liệu
                String tenKhach = rs.getString("TenKhachHang");
                String sdt = rs.getString("SoDienThoai"); // Lấy từ DB
                String tenMay = rs.getString("TenSanPham");
                String serial = rs.getString("SerialNumber"); // Lấy từ DB
                String ngayNhan = rs.getString("NgayTiepNhan");
                String loi = rs.getString("LoiBaoCao");
                String trangThai = rs.getString("TrangThai");
                
                // Format lại nội dung null
                if (sdt == null) sdt = "(Chưa cập nhật)";
                if (serial == null) serial = "(Không có)";
                if (loi == null) loi = "Không có mô tả";

                // Thời gian xuất phiếu
                String thoiGian = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy"));
                
                // Chọn nơi lưu
                JFileChooser chooser = new JFileChooser();
                chooser.setSelectedFile(new File("PhieuBaoHanh_" + maPhieu + ".html"));
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    
                    // Tạo nội dung HTML
                    StringBuilder html = new StringBuilder();
                    html.append("<html><head><style>");
                    html.append("body { font-family: 'Segoe UI', Arial, sans-serif; padding: 40px; }");
                    html.append(".header { text-align: center; margin-bottom: 20px; border-bottom: 2px solid #003366; padding-bottom: 10px; }");
                    html.append("h2 { color: #003366; margin: 0; text-transform: uppercase; }");
                    html.append("h4 { margin: 5px 0; color: #555; }");
                    html.append(".info-section { width: 100%; margin-top: 20px; border-collapse: collapse; }");
                    html.append(".info-section td { padding: 10px; border-bottom: 1px solid #eee; vertical-align: top; }");
                    html.append(".label { font-weight: bold; width: 150px; color: #333; }");
                    html.append(".content { color: #000; }");
                    html.append(".footer { margin-top: 50px; text-align: right; }");
                    html.append(".sign-box { display: inline-block; text-align: center; margin-left: 50px; }");
                    html.append("</style></head><body>");
                    
                    // Header
                    html.append("<div class='header'>");
                    html.append("<h2>PHIẾU TIẾP NHẬN BẢO HÀNH</h2>");
                    html.append("<h4>CỬA HÀNG THIẾT BỊ ĐIỆN TỬ XYZ</h4>");
                    html.append("<p>Địa chỉ: 123 Đường ABC, Quận Ninh Kiều, Cần Thơ</p>");
                    html.append("<p>Hotline: 1800-xxxx</p>");
                    html.append("</div>");
                    
                    // Body - Thông tin
                    html.append("<table class='info-section'>");
                    html.append("<tr><td class='label'>Mã Phiếu:</td><td class='content'><strong>#" + maPhieu + "</strong></td></tr>");
                    html.append("<tr><td class='label'>Ngày Tiếp Nhận:</td><td class='content'>" + ngayNhan + "</td></tr>");
                    html.append("<tr><td class='label'>Khách Hàng:</td><td class='content'>" + tenKhach + "</td></tr>");
                    html.append("<tr><td class='label'>Số Điện Thoại:</td><td class='content'>" + sdt + "</td></tr>");
                    html.append("<tr><td class='label'>Sản Phẩm:</td><td class='content'>" + tenMay + "</td></tr>");
                    html.append("<tr><td class='label'>Số Serial (SN):</td><td class='content'>" + serial + "</td></tr>");
                    html.append("<tr><td class='label'>Mô Tả Lỗi:</td><td class='content'>" + loi + "</td></tr>");
                    html.append("<tr><td class='label'>Trạng Thái:</td><td class='content'>" + trangThai + "</td></tr>");
                    html.append("</table>");
                    
                    // Footer - Chữ ký
                    html.append("<div class='footer'>");
                    html.append("<p><em>Cần Thơ, " + thoiGian + "</em></p>");
                    
                    html.append("<div class='sign-box'>");
                    html.append("<p><strong>Khách Hàng</strong></p>");
                    html.append("<br><br><br>");
                    html.append("<p>(Ký, ghi rõ họ tên)</p>");
                    html.append("</div>");
                    
                    html.append("<div class='sign-box'>");
                    html.append("<p><strong>Người Lập Phiếu</strong></p>");
                    html.append("<br><br><br>");
                    html.append("<p><strong>" + this.tenNhanVienLapPhieu + "</strong></p>"); // Tên Đầy Đủ
                    html.append("</div>");
                    
                    html.append("</div>");
                    html.append("</body></html>");
                    
                    // Ghi ra file
                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                    writer.write("\uFEFF"); // BOM cho tiếng Việt
                    writer.write(html.toString());
                    writer.close();
                    
                    JOptionPane.showMessageDialog(this, "Xuất phiếu thành công!\nHãy mở file bằng trình duyệt và chọn In -> Lưu dưới dạng PDF.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất phiếu: " + e.getMessage());
        }
    }

    // --- UI HELPERS ---
    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private JLabel createLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(Color.BLACK); 
        lbl.setBounds(x, y, 100, 25);
        return lbl;
    }
    
    private JTextField createTextField(int x, int y, int width) {
        JTextField txt = new JTextField();
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txt.setForeground(Color.BLACK);
        txt.setEditable(false);
        txt.setBackground(Color.WHITE);
        txt.setBounds(x, y, width, 25);
        txt.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        return txt;
    }
}