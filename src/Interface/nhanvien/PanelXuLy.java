package Interface.nhanvien;

import Process.NghiepVuBaoHanhBUS;
import Process.LichSuXuLy;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PanelXuLy extends JPanel {

    private static final long serialVersionUID = 1L;

    // Components
    private JTable tblDanhSach;
    private DefaultTableModel tableModel;
    
    // Components Form (Bên phải)
    private JLabel lblMaPhieuVal;
    private JLabel lblTenMayVal;
    private JComboBox<String> cboTrangThai;
    private JTextArea txtNoiDungXuLy;
    private JTextField txtLinhKien;
    private JButton btnCapNhat;
    private JButton btnXemLichSu;

    // Logic
    private NghiepVuBaoHanhBUS bus;
    private int maPhieuDangChon = -1; 

    public PanelXuLy() {
        bus = new NghiepVuBaoHanhBUS();
        
        setLayout(null);
        setBackground(new Color(240, 248, 255)); // Nền xanh nhạt
        
        // Khởi tạo 2 vùng giao diện
        initPanelTrai();
        initPanelPhai();
        
        // Logic dữ liệu
        taiDuLieuLenBang();
        ganSuKien();
    }

    // ========================================================================
    // PHẦN 1: BÊN TRÁI (BẢNG DANH SÁCH) - Rộng 600px
    // ========================================================================
    private void initPanelTrai() {
        JPanel pnlLeft = new JPanel(null);
        pnlLeft.setBackground(Color.WHITE);
        pnlLeft.setBounds(10, 10, 600, 650);
        pnlLeft.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        add(pnlLeft);

        // Label tiêu đề
        JLabel lblTitle = new JLabel("DANH SÁCH PHIẾU CẦN XỬ LÝ");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(20, 15, 400, 30);
        pnlLeft.add(lblTitle);

        // Bảng dữ liệu
        String[] cols = {"Mã Phiếu", "Serial", "Tên SP", "Ngày Nhận", "Lỗi", "Trạng Thái"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        tblDanhSach = new JTable(tableModel);
        tblDanhSach.setRowHeight(28);
        tblDanhSach.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        // Header bảng
        JTableHeader header = tblDanhSach.getTableHeader();
        header.setFont(new Font("Tahoma", Font.BOLD, 13));
        header.setBackground(new Color(230, 230, 230));
        
        // Chỉnh độ rộng cột
        tblDanhSach.getColumnModel().getColumn(0).setPreferredWidth(60);  // Mã
        tblDanhSach.getColumnModel().getColumn(2).setPreferredWidth(150); // Tên SP
        tblDanhSach.getColumnModel().getColumn(5).setPreferredWidth(100); // Trạng thái

        JScrollPane scrPane = new JScrollPane(tblDanhSach);
        scrPane.setBounds(10, 60, 580, 580);
        pnlLeft.add(scrPane);
    }

    // ========================================================================
    // PHẦN 2: BÊN PHẢI (FORM CẬP NHẬT) - Rộng 380px, X=620
    // ========================================================================
    private void initPanelPhai() {
        JPanel pnlRight = new JPanel(null);
        pnlRight.setBackground(new Color(250, 250, 250));
        pnlRight.setBounds(620, 10, 380, 650);
        pnlRight.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(0, 102, 204), 1),
                " CẬP NHẬT TIẾN ĐỘ ",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 14), new Color(0, 102, 204)
        ));
        add(pnlRight);

        int xLabel = 20, xVal = 130, width = 230, height = 30;
        int y = 50, gap = 50;

        // 1. Mã Phiếu
        JLabel lblMa = new JLabel("Mã Phiếu:");
        lblMa.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblMa.setBounds(xLabel, y, 100, height);
        pnlRight.add(lblMa);

        lblMaPhieuVal = new JLabel("...");
        lblMaPhieuVal.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblMaPhieuVal.setForeground(Color.BLUE);
        lblMaPhieuVal.setBounds(xVal, y, width, height);
        pnlRight.add(lblMaPhieuVal);

        y += gap;
        // 2. Sản phẩm
        JLabel lblTen = new JLabel("Sản phẩm:");
        lblTen.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTen.setBounds(xLabel, y, 100, height);
        pnlRight.add(lblTen);

        lblTenMayVal = new JLabel("...");
        lblTenMayVal.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTenMayVal.setBounds(xVal, y, width, height);
        pnlRight.add(lblTenMayVal);

        y += gap;
        // 3. Trạng thái mới
        JLabel lblTT = new JLabel("Trạng thái mới:");
        lblTT.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTT.setBounds(xLabel, y, 100, height);
        pnlRight.add(lblTT);

        String[] trangThaiItems = {"Đang kiểm tra", "Đang chờ linh kiện", "Đang sửa", "Hoàn thành", "Đã trả khách", "Hủy bỏ"};
        cboTrangThai = new JComboBox<>(trangThaiItems);
        cboTrangThai.setFont(new Font("Tahoma", Font.PLAIN, 13));
        cboTrangThai.setBounds(xVal, y, 220, height);
        pnlRight.add(cboTrangThai);

        y += gap;
        // 4. Nội dung xử lý
        JLabel lblND = new JLabel("Nội dung xử lý:");
        lblND.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblND.setBounds(xLabel, y, 100, height);
        pnlRight.add(lblND);

        txtNoiDungXuLy = new JTextArea();
        txtNoiDungXuLy.setLineWrap(true);
        txtNoiDungXuLy.setWrapStyleWord(true);
        txtNoiDungXuLy.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        JScrollPane scrGhiChu = new JScrollPane(txtNoiDungXuLy);
        scrGhiChu.setBounds(xLabel, y + 30, 330, 80); // TextArea nằm dưới label
        pnlRight.add(scrGhiChu);

        y += 130; // Nhảy qua TextArea
        // 5. Linh kiện
        JLabel lblLK = new JLabel("Linh kiện thay:");
        lblLK.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblLK.setBounds(xLabel, y, 100, height);
        pnlRight.add(lblLK);

        txtLinhKien = new JTextField();
        txtLinhKien.setFont(new Font("Tahoma", Font.PLAIN, 13));
        txtLinhKien.setBounds(xVal, y, 220, height);
        pnlRight.add(txtLinhKien);

        // --- BUTTONS ---
        int btnY = 380;
        
        btnCapNhat = new JButton("Cập Nhật");
        btnCapNhat.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnCapNhat.setBackground(new Color(135, 206, 250)); // LightSkyBlue
        btnCapNhat.setForeground(Color.BLACK); // Chữ Đen
        btnCapNhat.setBounds(40, btnY, 130, 40);
        pnlRight.add(btnCapNhat);

        btnXemLichSu = new JButton("Xem Lịch Sử");
        btnXemLichSu.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnXemLichSu.setBackground(new Color(255, 228, 181)); // Moccasin (Cam nhạt)
        btnXemLichSu.setForeground(Color.BLACK); // Chữ Đen
        btnXemLichSu.setBounds(200, btnY, 130, 40);
        pnlRight.add(btnXemLichSu);
    }

    // ========================================================================
    // LOGIC XỬ LÝ (GIỮ NGUYÊN)
    // ========================================================================
    
    public void taiDuLieuLenBang() {
        tableModel.setRowCount(0); 
        List<Object[]> list = bus.layDanhSachPhieuChoTable();
        for (Object[] row : list) {
            tableModel.addRow(row);
        }
    }

    private void ganSuKien() {
        // Click bảng
        tblDanhSach.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblDanhSach.getSelectedRow();
                if (selectedRow != -1) {
                    maPhieuDangChon = (int) tblDanhSach.getValueAt(selectedRow, 0);
                    String tenMay = (String) tblDanhSach.getValueAt(selectedRow, 2);
                    String trangThaiHienTai = (String) tblDanhSach.getValueAt(selectedRow, 5);

                    lblMaPhieuVal.setText(String.valueOf(maPhieuDangChon));
                    lblTenMayVal.setText(tenMay);
                    cboTrangThai.setSelectedItem(trangThaiHienTai);
                }
            }
        });

        // Nút Cập nhật
        btnCapNhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyCapNhat();
            }
        });

        // Nút Xem Lịch sử
        btnXemLichSu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hienThiLichSu();
            }
        });
    }

    private void xuLyCapNhat() {
        if (maPhieuDangChon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu bảo hành trong bảng!");
            return;
        }

        String noiDung = txtNoiDungXuLy.getText().trim();
        if (noiDung.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung xử lý (bạn đã làm gì?)");
            return;
        }

        String trangThaiMoi = cboTrangThai.getSelectedItem().toString();
        String linhKien = txtLinhKien.getText().trim();
        if (linhKien.isEmpty()) linhKien = "Không";

        LichSuXuLy lichSu = new LichSuXuLy();
        lichSu.setMaPhieu(maPhieuDangChon);
        lichSu.setMaNhanVien(3); // Tạm set cứng ID kỹ thuật
        lichSu.setNoiDungXuLy(noiDung);
        lichSu.setLinhKienThayThe(linhKien);
        lichSu.setNgayHoanThanh(new Date(System.currentTimeMillis()));

        boolean ketQua = bus.capNhatTienDo(maPhieuDangChon, trangThaiMoi, lichSu);

        if (ketQua) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            taiDuLieuLenBang();
            txtNoiDungXuLy.setText("");
            txtLinhKien.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra, vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hienThiLichSu() {
        int selectedRow = tblDanhSach.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng trong bảng để lấy Serial Number!");
            return;
        }

        String serial = (String) tblDanhSach.getValueAt(selectedRow, 1); 
        List<Object[]> listHistory = bus.layLichSuThietBi(serial);

        if (listHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thiết bị này chưa có lịch sử sửa chữa nào trước đây.");
            return;
        }

        // Tạo Dialog hiển thị danh sách
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Lịch sử sửa chữa - Serial: " + serial);
        dialog.setSize(700, 350);
        dialog.setLocationRelativeTo(this);

        String[] cols = {"Mã Phiếu", "Ngày Nhận", "Lỗi Báo Cáo", "Trạng Thái Cuối"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        
        for (Object[] row : listHistory) {
            model.addRow(row);
        }

        JTable tblHistory = new JTable(model);
        tblHistory.setRowHeight(25);
        dialog.add(new JScrollPane(tblHistory));

        dialog.setVisible(true);
    }
}