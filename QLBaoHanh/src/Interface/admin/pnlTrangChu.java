package Interface.admin;

import java.awt.*;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Process.ThongKe;

public class pnlTrangChu extends JPanel {

    private static final long serialVersionUID = 1L;

    // ===== COMPONENT DB =====
    private JLabel lblCountKH, lblCountSP, lblCountBH;
    private JTable tblDangXuLy;
    private DefaultTableModel modelDangXuLy;

    private ThongKe thongKe = new ThongKe();

    // ===== CONSTRUCTOR =====
    public pnlTrangChu() {
        setLayout(null);
        setBackground(new Color(236, 240, 241));

        initUI();
        loadThongKe();
        loadPhieuDangXuLy();
    }

    // ===== UI =====
    private void initUI() {

        JLabel lblTitle = new JLabel("TRANG CHỦ - DASHBOARD");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(20, 15, 300, 30);
        add(lblTitle);

        // ===== KHÁCH HÀNG =====
        lblCountKH = createStatPanel(
                20, 60,
                new Color(52, 152, 219),
                "KHÁCH HÀNG",
                "👥"
        );

        // ===== SẢN PHẨM =====
        lblCountSP = createStatPanel(
                235, 60,
                new Color(46, 204, 113),
                "SẢN PHẨM",
                "📦"
        );

        // ===== BẢO HÀNH =====
        lblCountBH = createStatPanel(
                450, 60,
                new Color(241, 196, 15),
                "BẢO HÀNH",
                "🔧"
        );

        // ===== TABLE PHIẾU ĐANG XỬ LÝ =====
        JPanel pnlDangXuLy = new JPanel(new BorderLayout());
        pnlDangXuLy.setBounds(20, 200, 410, 200);
        pnlDangXuLy.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "PHIẾU BẢO HÀNH ĐANG XỬ LÝ"
        ));
        add(pnlDangXuLy);

        String[] cols = {"Mã Phiếu", "Khách Hàng", "Sản Phẩm", "Ngày Nhận"};
        modelDangXuLy = new DefaultTableModel(cols, 0);
        tblDangXuLy = new JTable(modelDangXuLy);
        tblDangXuLy.setRowHeight(25);

        pnlDangXuLy.add(new JScrollPane(tblDangXuLy), BorderLayout.CENTER);

        // ===== THÔNG BÁO =====
        JPanel pnlThongBao = new JPanel(new BorderLayout());
        pnlThongBao.setBounds(450, 200, 200, 200);
        pnlThongBao.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(231, 76, 60), 2),
                "THÔNG BÁO"
        ));
        add(pnlThongBao);

        JTextArea txtThongBao = new JTextArea();
        txtThongBao.setEditable(false);
        txtThongBao.setFont(new Font("Tahoma", Font.PLAIN, 11));
        txtThongBao.setText("• Dashboard load dữ liệu trực tiếp từ CSDL");
        pnlThongBao.add(new JScrollPane(txtThongBao), BorderLayout.CENTER);
    }

    // ===== TẠO PANEL THỐNG KÊ =====
    private JLabel createStatPanel(int x, int y, Color bg, String title, String icon) {

        JPanel pnl = new JPanel(null);
        pnl.setBounds(x, y, 200, 120);
        pnl.setBackground(bg);
        add(pnl);

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblIcon.setForeground(Color.WHITE);
        lblIcon.setBounds(15, 10, 60, 50);
        pnl.add(lblIcon);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(15, 65, 170, 20);
        pnl.add(lblTitle);

        JLabel lblCount = new JLabel("0");
        lblCount.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblCount.setForeground(Color.WHITE);
        lblCount.setBounds(15, 85, 170, 30);
        pnl.add(lblCount);

        return lblCount;
    }

    // ===== LOAD THỐNG KÊ =====
    private void loadThongKe() {
        try {
            lblCountKH.setText(String.valueOf(thongKe.ThongKeHomNay()));
            lblCountSP.setText(String.valueOf(thongKe.ThongKeTuanNay()));
            lblCountBH.setText(String.valueOf(thongKe.ThongKeThangNay()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load thống kê!");
        }
    }

    // ===== LOAD TABLE =====
    private void loadPhieuDangXuLy() {
        try {
            modelDangXuLy.setRowCount(0);
            ResultSet rs = thongKe.PhieuDangXuLy();

            while (rs.next()) {
                modelDangXuLy.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4)
                });
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load phiếu đang xử lý!");
        }
    }
}
