package Interface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.MatteBorder;

// Import các Panel Admin
import Interface.admin.pnlNhanVien;
import Interface.admin.pnlSanPham;
import Interface.admin.pnlKhachHang;
import Interface.admin.pnlBaoHanh; 
import Interface.admin.pnlThongKe; 

// Import các Panel Nghiệp vụ
import Interface.nhanvien.PanelTiepNhan;
import Interface.nhanvien.PanelXuLy;

public class MainFrame extends JFrame {

    private String tenNhanVien;
    private String maVaiTro; 
    private int maNhanVien;

    // Khai báo các nút menu
    private JButton btnKhachHang, btnSanPham, btnNhanVien, btnXuLyBaoHanh, btnTraCuu, btnTiepNhan, btnThongKeAdmin;
    
    private JPanel pnlContent;
    private CardLayout cardLayout;

    // Màu sắc giao diện
    private Color colorMenuBackground = new Color(44, 62, 80); 
    private Color colorMenuItem = new Color(44, 62, 80);        
    private Color colorMenuHover = new Color(52, 73, 94);       
    private Color colorText = new Color(220, 220, 220); 
    private Color colorHeaderBg = new Color(240, 240, 240);

    public MainFrame(String tenNV, String role, int maNV) {
        this.tenNhanVien = tenNV;
        this.maVaiTro = role;
        this.maNhanVien = maNV;
        
        khoiTaoGiaoDien();
        phanQuyen();
    }

    private void khoiTaoGiaoDien() {
        setTitle("HỆ THỐNG QUẢN LÝ - " + tenNhanVien.toUpperCase());
        setSize(1280, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- 1. HEADER ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(colorHeaderBg);
        pnlHeader.setPreferredSize(new Dimension(1200, 50));
        pnlHeader.setBorder(new MatteBorder(0, 0, 2, 0, new Color(200, 200, 200)));
        
        JLabel lblTitle = new JLabel("  PHẦN MỀM QUẢN LÝ BẢO HÀNH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(44, 62, 80));
        
        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setBackground(new Color(231, 76, 60)); 
        btnLogout.setForeground(new Color(254, 254, 254)); 
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                new Login().setVisible(true);
            }
        });
        
        JPanel pnlUser = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlUser.setOpaque(false);
        JLabel lblUser = new JLabel("Xin chào: " + tenNhanVien + "  ");
        lblUser.setForeground(new Color(50, 50, 50));
        
        pnlUser.add(lblUser);
        pnlUser.add(btnLogout);
        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(pnlUser, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // --- 2. SIDEBAR MENU ---
        JPanel pnlSidebar = new JPanel(new BorderLayout());
        pnlSidebar.setBackground(colorMenuBackground);
        pnlSidebar.setPreferredSize(new Dimension(230, 0));
        
        JPanel pnlMenuContainer = new JPanel(new GridBagLayout()); 
        pnlMenuContainer.setBackground(colorMenuBackground);
        pnlMenuContainer.setOpaque(false);
        
        // Cấu hình GridBag để nút neo lên trên
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH; 
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 5, 0); 
        
        // --- KHỞI TẠO CÁC NÚT MENU ---
        btnTraCuu = createMenuItem("TRA CỨU DANH SÁCH", "TRACUU_BH"); 
        btnTiepNhan = createMenuItem("TIẾP NHẬN MÁY", "TIEPNHAN");
        btnXuLyBaoHanh = createMenuItem("KỸ THUẬT SỬA CHỮA", "BAOHANH"); 
        
        btnKhachHang = createMenuItem("QUẢN LÝ KHÁCH HÀNG", "KHACHHANG");
        btnSanPham = createMenuItem("QUẢN LÝ SẢN PHẨM", "SANPHAM");
        btnNhanVien = createMenuItem("QUẢN LÝ NHÂN VIÊN", "NHANVIEN");
        
        // --- NÚT THỐNG KÊ CHO ADMIN ---
        btnThongKeAdmin = createMenuItem("THỐNG KÊ BÁO CÁO", "THONGKE_ADMIN");

        // --- ADD VÀO PANEL ---
        pnlMenuContainer.add(btnTraCuu, gbc);
        pnlMenuContainer.add(btnTiepNhan, gbc);
        pnlMenuContainer.add(btnXuLyBaoHanh, gbc);
        
        pnlMenuContainer.add(btnKhachHang, gbc);
        pnlMenuContainer.add(btnSanPham, gbc);
        pnlMenuContainer.add(btnNhanVien, gbc);
        
        pnlMenuContainer.add(btnThongKeAdmin, gbc); 

        // Panel đệm đẩy menu lên trên
        GridBagConstraints gbcSpace = new GridBagConstraints();
        gbcSpace.gridx = 0;
        gbcSpace.gridy = GridBagConstraints.RELATIVE;
        gbcSpace.weighty = 1.0; 
        pnlMenuContainer.add(new JPanel(null) {{ setOpaque(false); }}, gbcSpace);

        pnlSidebar.add(pnlMenuContainer, BorderLayout.CENTER);
        add(pnlSidebar, BorderLayout.WEST);

        // --- 3. CONTENT ---
        pnlContent = new JPanel();
        cardLayout = new CardLayout();
        pnlContent.setLayout(cardLayout);
        pnlContent.setBackground(new Color(245, 248, 250));
        
        try {
            // Add các Panel Admin
            pnlContent.add(new Interface.admin.pnlBaoHanh(this.tenNhanVien), "TRACUU_BH");
            
            pnlContent.add(new Interface.admin.pnlKhachHang(), "KHACHHANG");
            pnlContent.add(new Interface.admin.pnlSanPham(), "SANPHAM"); // Giữ nguyên khởi tạo mặc định
            pnlContent.add(new Interface.admin.pnlNhanVien(), "NHANVIEN");
            pnlContent.add(new Interface.admin.pnlThongKe(), "THONGKE_ADMIN"); 
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add các Panel Nghiệp vụ
        pnlContent.add(new Interface.nhanvien.PanelTiepNhan(), "TIEPNHAN"); 
        
        // Chỉ add PanelXuLy trực tiếp, không dùng TabbedPane
        pnlContent.add(new Interface.nhanvien.PanelXuLy(), "BAOHANH"); 

        add(pnlContent, BorderLayout.CENTER);
    }

    private JButton createMenuItem(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(colorText);
        btn.setBackground(colorMenuItem);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addActionListener(e -> {
            cardLayout.show(pnlContent, cardName);
            
            // Nếu là panel Xử lý -> Gọi hàm load lại dữ liệu để cập nhật mới nhất
            if(cardName.equals("BAOHANH")) {
                Component[] comps = pnlContent.getComponents();
                for (Component comp : comps) {
                    if (comp instanceof PanelXuLy && comp.isVisible()) {
                        ((PanelXuLy) comp).taiDuLieuLenBang();
                    }
                }
            }
        });

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(colorMenuHover); }
            public void mouseExited(MouseEvent e) { btn.setBackground(colorMenuItem); }
        });

        return btn;
    }

    private void phanQuyen() {
        // --- LOGIC PHÂN QUYỀN ---
        
        if ("1".equals(maVaiTro)) { // === ADMIN ===
            btnKhachHang.setVisible(true);
            btnSanPham.setVisible(true);
            btnNhanVien.setVisible(true);
            btnThongKeAdmin.setVisible(true);
            
            btnTraCuu.setVisible(false);
            btnTiepNhan.setVisible(false);
            btnXuLyBaoHanh.setVisible(false);
            
            cardLayout.show(pnlContent, "THONGKE_ADMIN");
        } 
        else if ("3".equals(maVaiTro)) { // === KỸ THUẬT VIÊN ===
            btnTiepNhan.setVisible(true);
            btnXuLyBaoHanh.setVisible(true);
            
            btnTraCuu.setVisible(false); 
            btnKhachHang.setVisible(false);
            btnSanPham.setVisible(false);
            btnNhanVien.setVisible(false);
            btnThongKeAdmin.setVisible(false);
            
            cardLayout.show(pnlContent, "BAOHANH");
        } 
        else if ("2".equals(maVaiTro)) { // === LỄ TÂN ===
            btnTraCuu.setVisible(true);
            btnTiepNhan.setVisible(true);
            btnKhachHang.setVisible(true);
            btnSanPham.setVisible(true);
            
            btnXuLyBaoHanh.setVisible(false); 
            btnNhanVien.setVisible(false); 
            btnThongKeAdmin.setVisible(false); 
            
            cardLayout.show(pnlContent, "TRACUU_BH");
        }
        
        revalidate();
        repaint();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new MainFrame("Admin Test", "1", 1).setVisible(true));
    }
}