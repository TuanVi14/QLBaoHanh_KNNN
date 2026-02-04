package Interface.admin;

import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Process.ThongKe;

public class pnlThongKe extends JPanel {

    private ThongKe tk = new ThongKe();

    private JTextField txtTuNgay, txtDenNgay;
    private JLabel lblHomNay, lblTuanNay, lblThangNay, lblTyLe;
    private JTable tblThongKe;
    private DefaultTableModel model;

    public pnlThongKe() {
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("BÁO CÁO & THỐNG KÊ");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setBounds(20, 15, 300, 30);
        add(lblTitle);

        // ===== THỐNG KÊ THEO THỜI GIAN =====
        JPanel pnlTime = new JPanel(null);
        pnlTime.setBounds(20, 60, 300, 150);
        pnlTime.setBorder(BorderFactory.createTitledBorder("THỐNG KÊ THEO THỜI GIAN"));
        add(pnlTime);

        pnlTime.add(new JLabel("Từ ngày:")).setBounds(15, 30, 70, 25);
        txtTuNgay = new JTextField("2025-01-01");
        txtTuNgay.setBounds(90, 30, 190, 25);
        pnlTime.add(txtTuNgay);

        pnlTime.add(new JLabel("Đến ngày:")).setBounds(15, 65, 70, 25);
        txtDenNgay = new JTextField("2025-12-31");
        txtDenNgay.setBounds(90, 65, 190, 25);
        pnlTime.add(txtDenNgay);

        JButton btnThongKe = new JButton("Thống kê");
        btnThongKe.setBounds(90, 105, 120, 30);
        pnlTime.add(btnThongKe);

        // ===== THỐNG KÊ NHANH =====
        JPanel pnlNhanh = new JPanel(null);
        pnlNhanh.setBounds(340, 60, 310, 150);
        pnlNhanh.setBorder(BorderFactory.createTitledBorder("THỐNG KÊ NHANH"));
        add(pnlNhanh);

        pnlNhanh.add(new JLabel("Bảo hành hôm nay:")).setBounds(15, 30, 150, 20);
        lblHomNay = new JLabel("0 phiếu");
        lblHomNay.setBounds(170, 30, 120, 20);
        pnlNhanh.add(lblHomNay);

        pnlNhanh.add(new JLabel("Bảo hành tuần này:")).setBounds(15, 60, 150, 20);
        lblTuanNay = new JLabel("0 phiếu");
        lblTuanNay.setBounds(170, 60, 120, 20);
        pnlNhanh.add(lblTuanNay);

        pnlNhanh.add(new JLabel("Bảo hành tháng này:")).setBounds(15, 90, 150, 20);
        lblThangNay = new JLabel("0 phiếu");
        lblThangNay.setBounds(170, 90, 120, 20);
        pnlNhanh.add(lblThangNay);

        pnlNhanh.add(new JLabel("Tỷ lệ hoàn thành:")).setBounds(15, 120, 150, 20);
        lblTyLe = new JLabel("0%");
        lblTyLe.setBounds(170, 120, 120, 20);
        pnlNhanh.add(lblTyLe);

        // ===== BẢNG CHI TIẾT =====
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBounds(20, 225, 630, 200);
        pnlTable.setBorder(BorderFactory.createTitledBorder("CHI TIẾT THỐNG KÊ"));
        add(pnlTable);

        model = new DefaultTableModel(
            new String[]{"Loại SP", "Tổng BH", "Đang xử lý", "Hoàn thành", "Tỷ lệ"}, 0
        );
        tblThongKe = new JTable(model);
        pnlTable.add(new JScrollPane(tblThongKe), BorderLayout.CENTER);

        // ===== LOAD BAN ĐẦU =====
        loadThongKeNhanh();
        loadThongKeLoaiSP();

        // ===== SỰ KIỆN =====
        btnThongKe.addActionListener(e -> loadThongKeTheoThoiGian());
    }

    // ================= LOAD THỐNG KÊ NHANH =================
    private void loadThongKeNhanh() {
        try {
            lblHomNay.setText(tk.ThongKeHomNay() + " phiếu");
            lblTuanNay.setText(tk.ThongKeTuanNay() + " phiếu");
            lblThangNay.setText(tk.ThongKeThangNay() + " phiếu");
            lblTyLe.setText(String.format("%.0f%%", tk.TyLeHoanThanh()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load thống kê nhanh");
        }
    }

    // ================= THEO LOẠI SP =================
    private void loadThongKeLoaiSP() {
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Loại SP", "Tổng BH", "Đang xử lý", "Hoàn thành", "Tỷ lệ"
        });

        try {
            ResultSet rs = tk.ThongKeTheoLoaiSP();
            while (rs.next()) {
                int tong = rs.getInt("TongBH");
                int hoanThanh = rs.getInt("HoanThanh");
                String tyLe = tong == 0 ? "0%" :
                        Math.round(hoanThanh * 100.0 / tong) + "%";

                model.addRow(new Object[]{
                    rs.getString("TenLoai"),
                    tong,
                    rs.getInt("DangXuLy"),
                    hoanThanh,
                    tyLe
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thống kê theo loại SP");
        }
    }

    // ================= THEO THỜI GIAN =================
    private void loadThongKeTheoThoiGian() {
        model.setRowCount(0);
        model.setColumnIdentifiers(new String[]{
            "Mã phiếu", "Sản phẩm", "Khách hàng", "Ngày tiếp nhận", "Trạng thái"
        });

        try {
            ResultSet rs = tk.ThongKeTheoThoiGian(
                txtTuNgay.getText(),
                txtDenNgay.getText()
            );

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("MaPhieu"),
                    rs.getString("TenSanPham"),
                    rs.getString("TenKhachHang"),
                    rs.getDate("NgayTiepNhan"),
                    rs.getString("TrangThai")
                });
            }

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu trong khoảng thời gian này");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thống kê theo thời gian");
        }
    }
}
