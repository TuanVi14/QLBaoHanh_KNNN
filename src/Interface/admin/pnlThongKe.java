package Interface.admin;

import Process.ExcelExport;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import Interface.MainFrame;
import Database.DBConnect; // <--- 1. Import thêm class kết nối

/**
 * Panel Thống Kê với nút xuất Excel
 */
public class pnlThongKe extends JPanel {
    
    private JTable tblDanhSach;
    private DefaultTableModel tableModel;
    private JLabel lblTongPhieu, lblDangXuLy, lblHoanThanh;
    private JButton btnXuatExcel;
    
    // <--- 2. Khai báo biến kết nối
    private Connection conn;
    
    public pnlThongKe() {
        // <--- 3. Khởi tạo kết nối
        try {
            DBConnect db = new DBConnect();
            this.conn = db.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
        
        // Chỉ load dữ liệu nếu có kết nối
        if (this.conn != null) {
            loadDuLieu();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu!");
        }
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel điều khiển phía trên
        JPanel pnlTop = taoPanelTop();
        add(pnlTop, BorderLayout.NORTH);
        
        // Panel thống kê giữa
        JPanel pnlThongKe = taoPanelThongKe();
        add(pnlThongKe, BorderLayout.CENTER);
        
        // Bảng danh sách
        JPanel pnlBang = taoPanelBang();
        add(pnlBang, BorderLayout.SOUTH);
    }
    
    private JPanel taoPanelTop() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panel.setBackground(new Color(52, 152, 219));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTieuDe = new JLabel("THỐNG KÊ BÁO CÁO");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 20));
        lblTieuDe.setForeground(Color.WHITE);
        panel.add(lblTieuDe);
        
        panel.add(Box.createHorizontalStrut(300));
        
        // Nút Xuất Excel
        btnXuatExcel = new JButton("📊 Xuất Excel");
        btnXuatExcel.setFont(new Font("Arial", Font.BOLD, 14));
        btnXuatExcel.setBackground(new Color(46, 204, 113));
        btnXuatExcel.setForeground(Color.WHITE);
        btnXuatExcel.setFocusPainted(false);
        btnXuatExcel.setBorderPainted(false);
        btnXuatExcel.setPreferredSize(new Dimension(150, 40));
        btnXuatExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXuatExcel.addActionListener(e -> xuatExcel());
        panel.add(btnXuatExcel);
        
        return panel;
    }
    
    private JPanel taoPanelThongKe() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 20, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        lblTongPhieu = new JLabel("0", SwingConstants.CENTER);
        panel.add(taoCard("Tổng Số Phiếu", lblTongPhieu, new Color(52, 152, 219)));
        
        lblDangXuLy = new JLabel("0", SwingConstants.CENTER);
        panel.add(taoCard("Đang Xử Lý", lblDangXuLy, new Color(241, 196, 15)));
        
        lblHoanThanh = new JLabel("0", SwingConstants.CENTER);
        panel.add(taoCard("Hoàn Thành", lblHoanThanh, new Color(46, 204, 113)));
        
        return panel;
    }
    
    private JPanel taoCard(String tieuDe, JLabel lblGiaTri, Color mau) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(mau);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(mau.darker(), 2),
            BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));
        
        JLabel lblTieuDe = new JLabel(tieuDe, SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 16));
        lblTieuDe.setForeground(Color.WHITE);
        
        lblGiaTri.setFont(new Font("Arial", Font.BOLD, 36));
        lblGiaTri.setForeground(Color.WHITE);
        
        card.add(lblTieuDe, BorderLayout.NORTH);
        card.add(lblGiaTri, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel taoPanelBang() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            "Danh Sách Phiếu Bảo Hành",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(52, 152, 219)
        ));
        panel.setPreferredSize(new Dimension(0, 300));
        
        String[] cot = {"Mã Phiếu", "Khách Hàng", "Sản Phẩm", "Ngày Tiếp Nhận", "Trạng Thái"};
        tableModel = new DefaultTableModel(cot, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblDanhSach = new JTable(tableModel);
        tblDanhSach.setFont(new Font("Arial", Font.PLAIN, 13));
        tblDanhSach.setRowHeight(25);
        tblDanhSach.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tblDanhSach.getTableHeader().setBackground(new Color(52, 152, 219));
        tblDanhSach.getTableHeader().setForeground(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(tblDanhSach);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Load dữ liệu thống kê
     */
    public void loadDuLieu() {
        // Kiểm tra kết nối trước khi dùng
        if (conn == null) return;

        try {
            // Tổng số phiếu
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM phieubaohanh");
            if (rs.next()) {
                lblTongPhieu.setText(String.valueOf(rs.getInt("total")));
            }
            
            // Đang xử lý
            rs = stmt.executeQuery("SELECT COUNT(*) as total FROM phieubaohanh WHERE TrangThai = 'Đang sửa'");
            if (rs.next()) {
                lblDangXuLy.setText(String.valueOf(rs.getInt("total")));
            }
            
            // Hoàn thành
            rs = stmt.executeQuery("SELECT COUNT(*) as total FROM phieubaohanh WHERE TrangThai = 'Hoàn thành'");
            if (rs.next()) {
                lblHoanThanh.setText(String.valueOf(rs.getInt("total")));
            }
            
            // Load bảng
            tableModel.setRowCount(0);
            String sql = "SELECT p.MaPhieu, k.TenKhachHang, m.TenSanPham, p.NgayTiepNhan, p.TrangThai " +
                        "FROM phieubaohanh p " +
                        "JOIN sanphamdaban s ON p.MaSPDaBan = s.MaSPDaBan " +
                        "JOIN sanphammodel m ON s.MaModel = m.MaModel " +
                        "JOIN hoadon h ON s.MaHoaDon = h.MaHoaDon " +
                        "JOIN khachhang k ON h.MaKhachHang = k.MaKhachHang " +
                        "ORDER BY p.NgayTiepNhan DESC LIMIT 50";
            
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("MaPhieu"),
                    rs.getString("TenKhachHang"),
                    rs.getString("TenSanPham"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("NgayTiepNhan")),
                    rs.getString("TrangThai")
                });
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Xuất Excel
     */
    private void xuatExcel() {
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Chưa kết nối CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ExcelExport excelExport = new ExcelExport(conn);
            excelExport.xuatExcel((JFrame) SwingUtilities.getWindowAncestor(this));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất Excel: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}