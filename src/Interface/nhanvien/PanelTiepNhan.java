package Interface.nhanvien;

import Process.NghiepVuBaoHanhBUS;
import Process.PhieuBaoHanh;
import Process.ThongTinBaoHanh;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date; 
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class PanelTiepNhan extends JPanel {

    private static final long serialVersionUID = 1L;

    // --- Components Giao diện ---
    private JTextField txtSerial;
    private JButton btnKiemTra;
    
    // Các Label hiển thị thông tin bên trái
    private JLabel lblTenMayVal;
    private JLabel lblKhachHangVal;
    private JLabel lblSdtVal;
    private JLabel lblNgayMuaVal;
    private JLabel lblHanBaoHanhVal;
    
    // Components bên phải
    private JLabel lblKetQuaCheck; 
    private JTextArea txtMoTaLoi;
    private JButton btnTaoPhieu;
    private JButton btnLamMoi;

    // --- Logic ---
    private NghiepVuBaoHanhBUS bus;
    private ThongTinBaoHanh currentInfo; 

    public PanelTiepNhan() {
        bus = new NghiepVuBaoHanhBUS();
        
        setLayout(null); 
        setBackground(new Color(240, 248, 255)); 
        
        // Khởi tạo 2 vùng giao diện
        initPanelTrai();
        initPanelPhai();
        
        // Gán sự kiện
        ganSuKien();
    }

    // ========================================================================
    // PHẦN 1: BÊN TRÁI (TRA CỨU & THÔNG TIN)
    // ========================================================================
    private void initPanelTrai() {
        JPanel pnlLeft = new JPanel(null);
        pnlLeft.setBackground(Color.WHITE);
        pnlLeft.setBounds(10, 10, 600, 650);
        pnlLeft.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        add(pnlLeft);

        // --- 1.1 THANH TÌM KIẾM ---
        // [CHỈNH SỬA]: Tăng chiều rộng label từ 100 lên 120 để không bị "Serial Numb..."
        JLabel lblSerial = new JLabel("Serial Number:");
        lblSerial.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblSerial.setBounds(20, 20, 120, 30); 
        pnlLeft.add(lblSerial);

        // Dời ô nhập sang phải (x=140)
        txtSerial = new JTextField();
        txtSerial.setFont(new Font("Tahoma", Font.PLAIN, 13));
        txtSerial.setBounds(140, 20, 300, 30);
        pnlLeft.add(txtSerial);

        btnKiemTra = new JButton("Kiểm tra");
        btnKiemTra.setBounds(450, 20, 100, 30);
        btnKiemTra.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnKiemTra.setBackground(new Color(220, 220, 220)); // Màu nền xám nhẹ
        btnKiemTra.setForeground(Color.BLACK); // [CHỈNH SỬA]: Chữ màu Đen
        pnlLeft.add(btnKiemTra);

        // --- 1.2 KHUNG HIỂN THỊ THÔNG TIN ---
        JPanel pnlInfo = new JPanel(null);
        pnlInfo.setBackground(Color.WHITE);
        pnlInfo.setBounds(20, 70, 560, 550);
        pnlInfo.setBorder(BorderFactory.createTitledBorder(
            new LineBorder(new Color(0, 102, 204), 1),
            " THÔNG TIN SẢN PHẨM ",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Tahoma", Font.BOLD, 13), new Color(0, 102, 204)
        ));
        pnlLeft.add(pnlInfo);

        int xLabel = 30, xVal = 180, gap = 50;
        int y = 50;

        // Tên máy
        pnlInfo.add(createLabel("Tên sản phẩm:", xLabel, y));
        lblTenMayVal = createValueLabel("", xVal, y); // Mặc định rỗng, không để "..."
        pnlInfo.add(lblTenMayVal);

        y += gap;
        // Khách hàng
        pnlInfo.add(createLabel("Tên khách hàng:", xLabel, y));
        lblKhachHangVal = createValueLabel("", xVal, y);
        pnlInfo.add(lblKhachHangVal);

        y += gap;
        // SĐT
        pnlInfo.add(createLabel("Số điện thoại:", xLabel, y));
        lblSdtVal = createValueLabel("", xVal, y);
        pnlInfo.add(lblSdtVal);

        y += gap;
        // Ngày mua
        pnlInfo.add(createLabel("Ngày mua:", xLabel, y));
        lblNgayMuaVal = createValueLabel("", xVal, y);
        pnlInfo.add(lblNgayMuaVal);

        y += gap;
        // Thời hạn
        pnlInfo.add(createLabel("Thời hạn bảo hành:", xLabel, y));
        lblHanBaoHanhVal = createValueLabel("", xVal, y);
        pnlInfo.add(lblHanBaoHanhVal);
    }

    // Helper tạo Label tiêu đề
    private JLabel createLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lbl.setForeground(Color.GRAY);
        lbl.setBounds(x, y, 140, 30);
        return lbl;
    }

    // Helper tạo Label giá trị (Đậm)
    private JLabel createValueLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        lbl.setForeground(Color.BLACK);
        lbl.setBounds(x, y, 350, 30);
        return lbl;
    }

    // ========================================================================
    // PHẦN 2: BÊN PHẢI (XỬ LÝ TIẾP NHẬN)
    // ========================================================================
    private void initPanelPhai() {
        JPanel pnlRight = new JPanel(null);
        pnlRight.setBackground(new Color(250, 250, 250));
        pnlRight.setBounds(620, 10, 380, 650);
        pnlRight.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(0, 102, 204), 1),
                " TIẾP NHẬN BẢO HÀNH ",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 14), new Color(0, 102, 204)
        ));
        add(pnlRight);

        int x = 20, width = 340;
        int y = 50;

        // 1. Trạng thái bảo hành
        JLabel lblSttTitle = new JLabel("TRẠNG THÁI:");
        lblSttTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSttTitle.setBounds(x, y, 150, 30);
        pnlRight.add(lblSttTitle);

        y += 30;
        lblKetQuaCheck = new JLabel(""); // Mặc định rỗng
        lblKetQuaCheck.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblKetQuaCheck.setHorizontalAlignment(SwingConstants.CENTER);
        lblKetQuaCheck.setBorder(new LineBorder(Color.LIGHT_GRAY));
        lblKetQuaCheck.setOpaque(true);
        lblKetQuaCheck.setBackground(Color.WHITE);
        lblKetQuaCheck.setBounds(x, y, width, 50);
        pnlRight.add(lblKetQuaCheck);

        y += 80;
        // 2. Mô tả lỗi
        JLabel lblLoi = new JLabel("Mô tả lỗi của khách hàng:");
        lblLoi.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblLoi.setBounds(x, y, 200, 30);
        pnlRight.add(lblLoi);

        y += 30;
        txtMoTaLoi = new JTextArea();
        txtMoTaLoi.setFont(new Font("Tahoma", Font.PLAIN, 13));
        txtMoTaLoi.setLineWrap(true);
        txtMoTaLoi.setWrapStyleWord(true);
        txtMoTaLoi.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JScrollPane scrLoi = new JScrollPane(txtMoTaLoi);
        scrLoi.setBounds(x, y, width, 150);
        pnlRight.add(scrLoi);

        y += 180;
        // 3. Nút chức năng
        btnTaoPhieu = new JButton("TẠO PHIẾU TIẾP NHẬN");
        btnTaoPhieu.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnTaoPhieu.setBackground(new Color(220, 220, 220)); // Nền xám nhạt
        btnTaoPhieu.setForeground(Color.BLACK); // [CHỈNH SỬA]: Chữ màu Đen
        btnTaoPhieu.setBounds(x, y, width, 40);
        btnTaoPhieu.setEnabled(false);
        btnTaoPhieu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pnlRight.add(btnTaoPhieu);
        
        y += 50;
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnLamMoi.setBackground(new Color(220, 220, 220));
        btnLamMoi.setForeground(Color.BLACK); // [CHỈNH SỬA]: Chữ màu Đen
        btnLamMoi.setBounds(x, y, width, 30);
        btnLamMoi.addActionListener(e -> resetForm());
        pnlRight.add(btnLamMoi);
    }

    // ========================================================================
    // LOGIC XỬ LÝ
    // ========================================================================
    private void ganSuKien() {
        btnKiemTra.addActionListener(e -> xuLyKiemTra());
        txtSerial.addActionListener(e -> btnKiemTra.doClick());
        btnTaoPhieu.addActionListener(e -> xuLyTaoPhieu());
    }

    private void xuLyKiemTra() {
        String serial = txtSerial.getText().trim();
        if (serial.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Serial Number!");
            return;
        }

        String ketQuaCheck = bus.kiemTraDieuKienBaoHanh(serial);

        if (ketQuaCheck.equals("KhongTimThay")) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm có Serial này!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            resetForm();
            return;
        }

        currentInfo = bus.layThongTinMay(serial);

        if (currentInfo != null) {
            lblTenMayVal.setText(currentInfo.getTenSanPham());
            lblKhachHangVal.setText(currentInfo.getTenKhachHang());
            lblSdtVal.setText(currentInfo.getSoDienThoai());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            lblNgayMuaVal.setText(sdf.format(currentInfo.getNgayMua()));
            
            lblHanBaoHanhVal.setText(currentInfo.getThoiHanBaoHanh() + " tháng");

            if (ketQuaCheck.equals("ConBaoHanh")) {
                lblKetQuaCheck.setText("CÒN BẢO HÀNH");
                lblKetQuaCheck.setForeground(new Color(0, 128, 0)); 
                lblKetQuaCheck.setBorder(new LineBorder(Color.GREEN, 2));
            } else {
                lblKetQuaCheck.setText("HẾT BẢO HÀNH (Tính phí)");
                lblKetQuaCheck.setForeground(Color.RED);
                lblKetQuaCheck.setBorder(new LineBorder(Color.RED, 2));
            }
            
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

        PhieuBaoHanh phieu = new PhieuBaoHanh();
        phieu.setMaSPDaBan(currentInfo.getMaSPDaBan());
        phieu.setNgayTiepNhan(new Date(System.currentTimeMillis()));
        phieu.setLoiBaoCao(moTaLoi);
        phieu.setTrangThai("Đang kiểm tra");

        boolean thanhCong = bus.taoPhieuMoi(phieu);

        if (thanhCong) {
            JOptionPane.showMessageDialog(this, "Tạo phiếu tiếp nhận thành công!");
            txtSerial.setText("");
            txtMoTaLoi.setText("");
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Tạo phiếu thất bại! Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        lblTenMayVal.setText("");
        lblKhachHangVal.setText("");
        lblSdtVal.setText("");
        lblNgayMuaVal.setText("");
        lblHanBaoHanhVal.setText("");
        
        lblKetQuaCheck.setText("");
        lblKetQuaCheck.setForeground(Color.BLACK);
        lblKetQuaCheck.setBorder(new LineBorder(Color.LIGHT_GRAY));
        
        txtMoTaLoi.setText("");
        btnTaoPhieu.setEnabled(false);
        currentInfo = null;
    }
}