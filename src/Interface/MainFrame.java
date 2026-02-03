package Interface;

import Interface.PanelTiepNhan;
import Interface.PanelThongKe;
import Interface.PanelXuLy;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;
    
    // Khai báo các Panel thuộc Nhiệm vụ 2 (Của bạn)
    private PanelTiepNhan tabTiepNhan;
    private PanelXuLy tabXuLy;
    private PanelThongKe tabThongKe;
    
    // Sau này bạn kia làm xong Admin thì khai báo thêm ở đây
    // private PanelQuanLyNhanVien tabNhanVien;
    // private PanelQuanLySanPham tabSanPham;

    public MainFrame() {
        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        setTitle("HỆ THỐNG QUẢN LÝ BẢO HÀNH");
        setSize(1200, 750); // Kích thước chuẩn cho màn hình Laptop
        setLocationRelativeTo(null); // Ra giữa màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 1. Khởi tạo các Panel con
        tabTiepNhan = new PanelTiepNhan();
        tabXuLy = new PanelXuLy();
        tabThongKe = new PanelThongKe();

        // 2. Tạo TabbedPane (Thanh chứa các Tab)
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        // --- Thêm các Tab của bạn vào ---
        // Icon để null, nếu muốn đẹp bạn có thể thêm ImageIcon vào
        tabbedPane.addTab("Tiếp Nhận Bảo Hành", null, tabTiepNhan, "Tra cứu và nhận máy");
        tabbedPane.addTab("Xử Lý Kỹ Thuật", null, tabXuLy, "Cập nhật tiến độ sửa chữa");
        tabbedPane.addTab("Thống Kê & Báo Cáo", null, tabThongKe, "Dashboard tổng quan");

        // --- Chỗ này để dành cho bạn làm Admin (Nhiệm vụ 1) ---
        // tabbedPane.addTab("Quản Trị Hệ Thống", null, new JPanel(), "Chức năng Admin");

        // 3. Xử lý sự kiện chuyển Tab (Quan trọng)
        // Khi bấm sang tab khác thì dữ liệu phải tự động load lại mới nhất
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = tabbedPane.getSelectedIndex();
                
                switch (index) {
                    case 0: // Tab Tiếp nhận
                        // Có thể reset form nếu muốn
                        break;
                    case 1: // Tab Xử lý
                        // Khi click vào đây, bảng danh sách phải load lại ngay
                        // để thấy được phiếu vừa tạo bên tab Tiếp nhận
                        tabXuLy.taiDuLieuLenBang();
                        break;
                    case 2: // Tab Thống kê
                        // Load lại số liệu mới nhất
                        tabThongKe.taiDuLieu();
                        break;
                }
            }
        });

        // 4. Thêm vào JFrame
        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        
        // Footer (Tuỳ chọn)
        JLabel lblFooter = new JLabel("Hệ thống quản lý bảo hành v1.0 | Connected to MySQL", SwingConstants.CENTER);
        lblFooter.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        this.add(lblFooter, BorderLayout.SOUTH);
    }

    // Hàm main để chạy chương trình
    public static void main(String[] args) {
        // Set giao diện cho giống Windows (nhìn đẹp hơn mặc định của Java)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}