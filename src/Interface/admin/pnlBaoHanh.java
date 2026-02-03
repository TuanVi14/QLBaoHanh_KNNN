package Interface.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Process.BaoHanh;

public class pnlBaoHanh extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> jcomTrangThai;
    
    private final BaoHanh bh = new BaoHanh();

    /**
     * Create the panel.
     */
    public pnlBaoHanh() {
        // 1. Cài đặt Layout và Màu nền cho Panel chính
        setLayout(null);
        setBackground(new Color(240, 248, 255)); // Màu nền xanh nhạt dịu mắt (Alice Blue)
        
        // --- KHU VỰC TÌM KIẾM ---
        JPanel pnlControl = new JPanel();
        pnlControl.setLayout(null);
        pnlControl.setBackground(Color.WHITE); // Nền khu vực điều khiển là trắng
        pnlControl.setBounds(10, 11, 649, 100);
        pnlControl.setBorder(new LineBorder(new Color(200, 200, 200), 1, true)); // Viền bo nhẹ
        add(pnlControl);

        JLabel lblTenKH = new JLabel("Tên Khách Hàng:");
        lblTenKH.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTenKH.setForeground(Color.BLACK); // Ép màu đen
        lblTenKH.setBounds(20, 20, 120, 25);
        pnlControl.add(lblTenKH);
        
        txtTimKiem = new JTextField();
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtTimKiem.setForeground(Color.BLACK);
        txtTimKiem.setBounds(140, 20, 200, 25);
        pnlControl.add(txtTimKiem);
        txtTimKiem.setColumns(10);
        
        JButton btnTimKiem = createButton("Tìm Kiếm", new Color(70, 130, 180), Color.WHITE);
        btnTimKiem.setBounds(360, 20, 100, 25);
        pnlControl.add(btnTimKiem);
        
        JButton btnLamMoi = createButton("Làm Mới", new Color(119, 136, 153), Color.WHITE);
        btnLamMoi.setBounds(470, 20, 100, 25);
        pnlControl.add(btnLamMoi);

        JLabel lblTrangThai = new JLabel("Trạng Thái:");
        lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTrangThai.setForeground(Color.BLACK); // Ép màu đen
        lblTrangThai.setBounds(20, 60, 120, 25);
        pnlControl.add(lblTrangThai);
        
        jcomTrangThai = new JComboBox<>();
        jcomTrangThai.addItem("Tất cả");
        jcomTrangThai.addItem("Đang sửa");
        jcomTrangThai.addItem("Hoàn thành");
        jcomTrangThai.addItem("Đã trả khách");
        jcomTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jcomTrangThai.setBounds(140, 60, 150, 25);
        pnlControl.add(jcomTrangThai);
        
        JButton btnExcel = createButton("Xuất Excel", new Color(46, 139, 87), Color.WHITE);
        btnExcel.setBounds(360, 60, 100, 25);
        pnlControl.add(btnExcel);
        
        JButton btnXemChiTiet = createButton("Xem Chi Tiết", new Color(255, 140, 0), Color.WHITE);
        btnXemChiTiet.setBounds(470, 60, 120, 25);
        pnlControl.add(btnXemChiTiet);
        
        // --- KHU VỰC BẢNG DỮ LIỆU ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 122, 649, 278);
        scrollPane.getViewport().setBackground(Color.WHITE); // Nền trắng cho vùng chứa bảng
        add(scrollPane);
        
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(25); // Tăng chiều cao dòng cho dễ nhìn
        table.setSelectionBackground(new Color(173, 216, 230)); // Màu khi chọn dòng
        table.setSelectionForeground(Color.BLACK);
        
        // Cấu hình Header cho bảng
        String[] columns = {"Mã Phiếu", "Khách Hàng", "Sản Phẩm", "Ngày Nhận", "Trạng Thái", "Ghi Chú"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };
        table.setModel(tableModel);
        
        // Trang trí tiêu đề bảng
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(0, 102, 204)); // Nền xanh đậm
        header.setForeground(Color.WHITE); // Chữ trắng nổi bật trên nền xanh
        header.setOpaque(true);
        
        scrollPane.setViewportView(table);
        
        // Load dữ liệu mẫu (hoặc gọi hàm load từ DB của bạn)
        loadDataDemo();
    }
    
    /**
     * Hàm hỗ trợ tạo nút bấm nhanh với màu sắc tùy chọn
     */
    private JButton createButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false); // Bỏ viền lồi lõm cổ điển
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    // Hàm demo dữ liệu để bạn thấy bảng không bị trắng trơn
    private void loadDataDemo() {
        // Sau này bạn thay bằng code lấy từ CSDL: bh.LayDanhSach()...
        tableModel.addRow(new Object[]{"PB001", "Nguyễn Văn A", "Laptop Dell", "01/01/2025", "Đang sửa", "Lỗi màn hình"});
        tableModel.addRow(new Object[]{"PB002", "Trần Thị B", "iPhone 13", "02/01/2025", "Hoàn thành", "Thay pin"});
    }
}