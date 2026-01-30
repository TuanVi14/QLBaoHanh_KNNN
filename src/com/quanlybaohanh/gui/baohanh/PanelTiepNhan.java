package com.quanlybaohanh.gui.baohanh;

import com.quanlybaohanh.bus.NghiepVuBaoHanhBUS;
import com.quanlybaohanh.dto.PhieuBaoHanh;
import com.quanlybaohanh.dto.ThongTinBaoHanh;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date; // Lưu ý dùng java.sql.Date
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelTiepNhan extends JPanel {

    // Khai báo các component
    private JTextField txtSerial;
    private JButton btnKiemTra;
    
    // Label hiển thị thông tin (Dùng để set text)
    private JLabel lblTenMayVal;
    private JLabel lblKhachHangVal;
    private JLabel lblSdtVal;
    private JLabel lblNgayMuaVal;
    private JLabel lblHanBaoHanhVal;
    private JLabel lblKetQuaCheck; // Quan trọng: Hiện Xanh/Đỏ
    
    // Phần nhập lỗi
    private JTextArea txtMoTaLoi;
    private JButton btnTaoPhieu;

    // Các biến xử lý logic
    private NghiepVuBaoHanhBUS bus;
    private ThongTinBaoHanh currentInfo; // Lưu tạm thông tin máy vừa tìm được

    public PanelTiepNhan() {
        bus = new NghiepVuBaoHanhBUS();
        khoiTaoGiaoDien();
        ganSuKien();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. PANEL TÌM KIẾM (NORTH) ---
        JPanel pnlTimKiem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTimKiem.setBorder(new TitledBorder("Tra cứu sản phẩm"));
        
        JLabel lblSerial = new JLabel("Nhập Serial Number:");
        lblSerial.setFont(new Font("Arial", Font.BOLD, 14));
        txtSerial = new JTextField(20);
        txtSerial.setFont(new Font("Arial", Font.PLAIN, 14));
        btnKiemTra = new JButton("Kiểm tra bảo hành");
        btnKiemTra.setFont(new Font("Arial", Font.BOLD, 12));
        btnKiemTra.setBackground(new Color(70, 130, 180)); // Màu xanh SteelBlue
        btnKiemTra.setForeground(Color.WHITE);

        pnlTimKiem.add(lblSerial);
        pnlTimKiem.add(txtSerial);
        pnlTimKiem.add(btnKiemTra);

        add(pnlTimKiem, BorderLayout.NORTH);

        // --- 2. PANEL THÔNG TIN (CENTER) ---
        JPanel pnlThongTin = new JPanel(new GridLayout(6, 2, 10, 10)); // 6 dòng, 2 cột
        pnlThongTin.setBorder(new TitledBorder("Thông tin chi tiết"));
        
        // Font chữ
        Font fontLabel = new Font("Arial", Font.PLAIN, 14);
        Font fontValue = new Font("Arial", Font.BOLD, 14);

        // Tạo các label
        pnlThongTin.add(new JLabel("Tên sản phẩm:")).setFont(fontLabel);
        lblTenMayVal = new JLabel("..."); 
        lblTenMayVal.setFont(fontValue);
        pnlThongTin.add(lblTenMayVal);

        pnlThongTin.add(new JLabel("Tên khách hàng:")).setFont(fontLabel);
        lblKhachHangVal = new JLabel("...");
        lblKhachHangVal.setFont(fontValue);
        pnlThongTin.add(lblKhachHangVal);

        pnlThongTin.add(new JLabel("Số điện thoại:")).setFont(fontLabel);
        lblSdtVal = new JLabel("...");
        lblSdtVal.setFont(fontValue);
        pnlThongTin.add(lblSdtVal);

        pnlThongTin.add(new JLabel("Ngày mua:")).setFont(fontLabel);
        lblNgayMuaVal = new JLabel("...");
        lblNgayMuaVal.setFont(fontValue);
        pnlThongTin.add(lblNgayMuaVal);

        pnlThongTin.add(new JLabel("Thời hạn bảo hành:")).setFont(fontLabel);
        lblHanBaoHanhVal = new JLabel("...");
        lblHanBaoHanhVal.setFont(fontValue);
        pnlThongTin.add(lblHanBaoHanhVal);

        pnlThongTin.add(new JLabel("TRẠNG THÁI:")).setFont(new Font("Arial", Font.BOLD, 14));
        lblKetQuaCheck = new JLabel("...");
        lblKetQuaCheck.setFont(new Font("Arial", Font.BOLD, 18));
        pnlThongTin.add(lblKetQuaCheck);

        add(pnlThongTin, BorderLayout.CENTER);

        // --- 3. PANEL TẠO PHIẾU (SOUTH) ---
        JPanel pnlTaoPhieu = new JPanel(new BorderLayout(5, 5));
        pnlTaoPhieu.setBorder(new TitledBorder("Tiếp nhận sửa chữa"));
        pnlTaoPhieu.setPreferredSize(new Dimension(800, 150));

        JPanel pnlInputLoi = new JPanel(new BorderLayout());
        pnlInputLoi.add(new JLabel("Mô tả lỗi của khách hàng: "), BorderLayout.NORTH);
        txtMoTaLoi = new JTextArea(3, 50);
        txtMoTaLoi.setLineWrap(true);
        pnlInputLoi.add(new JScrollPane(txtMoTaLoi), BorderLayout.CENTER);

        btnTaoPhieu = new JButton("TẠO PHIẾU TIẾP NHẬN");
        btnTaoPhieu.setFont(new Font("Arial", Font.BOLD, 16));
        btnTaoPhieu.setBackground(new Color(34, 139, 34)); // Màu xanh lá ForestGreen
        btnTaoPhieu.setForeground(Color.WHITE);
        btnTaoPhieu.setEnabled(false); // Chưa check thì chưa được tạo phiếu

        pnlTaoPhieu.add(pnlInputLoi, BorderLayout.CENTER);
        pnlTaoPhieu.add(btnTaoPhieu, BorderLayout.EAST);

        add(pnlTaoPhieu, BorderLayout.SOUTH);
    }

    private void ganSuKien() {
        // Sự kiện nút Kiểm Tra
        btnKiemTra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyKiemTra();
            }
        });

        // Sự kiện nút Tạo Phiếu
        btnTaoPhieu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyTaoPhieu();
            }
        });
    }

    // --- LOGIC XỬ LÝ ---

    private void xuLyKiemTra() {
        String serial = txtSerial.getText().trim();
        if (serial.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Serial Number!");
            return;
        }

        // 1. Gọi BUS để kiểm tra trạng thái
        String ketQuaCheck = bus.kiemTraDieuKienBaoHanh(serial);

        if (ketQuaCheck.equals("KhongTimThay")) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm có Serial này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            resetForm();
            return;
        }

        // 2. Gọi BUS lấy thông tin chi tiết
        currentInfo = bus.layThongTinMay(serial);

        // 3. Hiển thị lên giao diện
        if (currentInfo != null) {
            lblTenMayVal.setText(currentInfo.getTenSanPham());
            lblKhachHangVal.setText(currentInfo.getTenKhachHang());
            lblSdtVal.setText(currentInfo.getSoDienThoai());
            
            // Format ngày tháng đẹp (dd/MM/yyyy)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            lblNgayMuaVal.setText(sdf.format(currentInfo.getNgayMua()));
            
            lblHanBaoHanhVal.setText(currentInfo.getThoiHanBaoHanh() + " tháng");

            // Xử lý hiển thị màu sắc dựa trên kết quả check
            if (ketQuaCheck.equals("ConBaoHanh")) {
                lblKetQuaCheck.setText("CÒN BẢO HÀNH (Miễn phí)");
                lblKetQuaCheck.setForeground(Color.GREEN);
            } else {
                lblKetQuaCheck.setText("HẾT BẢO HÀNH (Tính phí)");
                lblKetQuaCheck.setForeground(Color.RED);
            }
            
            // Mở khóa nút tạo phiếu
            btnTaoPhieu.setEnabled(true);
            txtMoTaLoi.requestFocus();
        }
    }

    private void xuLyTaoPhieu() {
        if (currentInfo == null) return;

        String moTaLoi = txtMoTaLoi.getText().trim();
        if (moTaLoi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả lỗi của khách!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tạo đối tượng PhieuBaoHanh
        PhieuBaoHanh phieu = new PhieuBaoHanh();
        phieu.setMaSPDaBan(currentInfo.getMaSPDaBan());
        phieu.setNgayTiepNhan(new Date(System.currentTimeMillis())); // Ngày hiện tại
        phieu.setLoiBaoCao(moTaLoi);
        phieu.setTrangThai("Đang kiểm tra"); // Trạng thái mặc định ban đầu

        // Gọi BUS lưu xuống DB
        boolean thanhCong = bus.taoPhieuMoi(phieu);

        if (thanhCong) {
            JOptionPane.showMessageDialog(this, "Tạo phiếu tiếp nhận thành công!");
            // Reset form để làm phiếu mới
            txtSerial.setText("");
            txtMoTaLoi.setText("");
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Tạo phiếu thất bại! Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        lblTenMayVal.setText("...");
        lblKhachHangVal.setText("...");
        lblSdtVal.setText("...");
        lblNgayMuaVal.setText("...");
        lblHanBaoHanhVal.setText("...");
        lblKetQuaCheck.setText("...");
        lblKetQuaCheck.setForeground(Color.BLACK);
        btnTaoPhieu.setEnabled(false);
        currentInfo = null;
    }
}