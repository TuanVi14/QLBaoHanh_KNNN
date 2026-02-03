package Interface.admin;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import Database.DBConnect;
import Process.BaoHanh;

public class dlgChiTietBaoHanh extends JDialog {

    private JLabel lblMaPhieu, lblKhachHang, lblSanPham, lblSerial;
    private JLabel lblNgayMua, lblHanBaoHanh, lblNgayNhan, lblTrangThai;

    private final BaoHanh bh = new BaoHanh();

    public dlgChiTietBaoHanh(int maPhieu) {
        setTitle("Chi tiết phiếu bảo hành");
        setSize(480, 380);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(null);

        // ===== Thông tin phiếu =====
        addLabel("Mã phiếu:", 30, 30);
        lblMaPhieu = addValue(180, 30);

        addLabel("Khách hàng:", 30, 65);
        lblKhachHang = addValue(180, 65);

        addLabel("Sản phẩm:", 30, 100);
        lblSanPham = addValue(180, 100);

        addLabel("Serial:", 30, 135);
        lblSerial = addValue(180, 135);

        // ===== Thông tin bảo hành =====
        addLabel("Ngày mua:", 30, 170);
        lblNgayMua = addValue(180, 170);

        addLabel("Hạn bảo hành:", 30, 205);
        lblHanBaoHanh = addValue(180, 205);

        addLabel("Ngày tiếp nhận:", 30, 240);
        lblNgayNhan = addValue(180, 240);

        addLabel("Trạng thái:", 30, 275);
        lblTrangThai = addValue(180, 275);

        JButton btnDong = new JButton("Đóng");
        btnDong.setBounds(180, 310, 100, 30);
        btnDong.addActionListener(e -> dispose());
        add(btnDong);

        loadData(maPhieu);
    }

    private void loadData(int maPhieu) {
        try {
            ResultSet rs = bh.ShowPhieuTheoMa(maPhieu);
            if (rs != null && rs.next()) {
                lblMaPhieu.setText(rs.getString("MaPhieu"));
                lblKhachHang.setText(rs.getString("TenKhachHang"));
                lblSanPham.setText(rs.getString("TenSanPham"));
                lblSerial.setText(rs.getString("SerialNumber"));
                lblNgayMua.setText(rs.getString("NgayMua"));
                lblHanBaoHanh.setText(rs.getString("HanBaoHanh"));
                lblNgayNhan.setText(rs.getString("NgayTiepNhan"));
                lblTrangThai.setText(rs.getString("TrangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không tải được dữ liệu!");
        }
    }

    private void addLabel(String text, int x, int y) {
        JLabel lb = new JLabel(text);
        lb.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lb.setBounds(x, y, 140, 22);
        add(lb);
    }

    private JLabel addValue(int x, int y) {
        JLabel lb = new JLabel();
        lb.setFont(new Font("Tahoma", Font.BOLD, 13));
        lb.setBounds(x, y, 260, 22);
        add(lb);
        return lb;
    }
}