package Interface;

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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import Interface.DialogChiTiet;

public class PanelXuLy extends JPanel {

    // Components cho Bảng danh sách
    private JTable tblDanhSach;
    private DefaultTableModel tableModel;
    
    // Components cho Form xử lý (Bên phải)
    private JLabel lblMaPhieuVal;
    private JLabel lblTenMayVal;
    private JComboBox<String> cboTrangThai;
    private JTextArea txtNoiDungXuLy;
    private JTextField txtLinhKien;
    private JButton btnCapNhat;
    private JButton btnXemLichSu;

    // Logic
    private NghiepVuBaoHanhBUS bus;
    private int maPhieuDangChon = -1; // -1 nghĩa là chưa chọn dòng nào

    public PanelXuLy() {
        bus = new NghiepVuBaoHanhBUS();
        khoiTaoGiaoDien();
        taiDuLieuLenBang();
        ganSuKien();
    }

    private void khoiTaoGiaoDien() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- 1. BẢNG DANH SÁCH (CENTER) ---
        // Tiêu đề cột khớp với câu SQL trong PhieuBaoHanhDAO
        String[] cols = {"Mã Phiếu", "Serial", "Tên Sản Phẩm", "Ngày Nhận", "Lỗi Đầu Vào", "Trạng Thái"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };
        tblDanhSach = new JTable(tableModel);
        tblDanhSach.setRowHeight(25);
        tblDanhSach.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrPane = new JScrollPane(tblDanhSach);
        scrPane.setBorder(new TitledBorder("Danh sách phiếu bảo hành"));
        add(scrPane, BorderLayout.CENTER);

        // --- 2. FORM XỬ LÝ (EAST) ---
        JPanel pnlRight = new JPanel();
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));
        pnlRight.setPreferredSize(new Dimension(320, 0));
        pnlRight.setBorder(new TitledBorder("Cập nhật tiến độ"));

        // Helper để tạo khoảng cách
        pnlRight.add(Box.createVerticalStrut(10));

        // Thông tin phiếu đang chọn
        JPanel pnlInfo = new JPanel(new GridLayout(2, 2, 5, 5));
        pnlInfo.setMaximumSize(new Dimension(300, 60));
        pnlInfo.add(new JLabel("Mã Phiếu:"));
        lblMaPhieuVal = new JLabel("...");
        lblMaPhieuVal.setFont(new Font("Arial", Font.BOLD, 14));
        lblMaPhieuVal.setForeground(Color.BLUE);
        pnlInfo.add(lblMaPhieuVal);
        
        pnlInfo.add(new JLabel("Sản phẩm:"));
        lblTenMayVal = new JLabel("...");
        pnlInfo.add(lblTenMayVal);
        pnlRight.add(pnlInfo);

        pnlRight.add(Box.createVerticalStrut(20));

        // Chọn trạng thái mới
        pnlRight.add(new JLabel("Trạng thái mới:"));
        String[] trangThaiItems = {"Đang kiểm tra", "Đang chờ linh kiện", "Đang sửa", "Hoàn thành", "Đã trả khách", "Hủy bỏ"};
        cboTrangThai = new JComboBox<>(trangThaiItems);
        cboTrangThai.setMaximumSize(new Dimension(300, 30));
        pnlRight.add(cboTrangThai);

        pnlRight.add(Box.createVerticalStrut(10));

        // Nhập nội dung công việc
        pnlRight.add(new JLabel("Nội dung xử lý (Ghi chú):"));
        txtNoiDungXuLy = new JTextArea(4, 20);
        txtNoiDungXuLy.setLineWrap(true);
        JScrollPane scrGhiChu = new JScrollPane(txtNoiDungXuLy);
        scrGhiChu.setMaximumSize(new Dimension(300, 80));
        scrGhiChu.setAlignmentX(Component.LEFT_ALIGNMENT); // Canh lề trái cho đẹp
        pnlRight.add(scrGhiChu);

        pnlRight.add(Box.createVerticalStrut(10));

        // Nhập linh kiện thay thế
        pnlRight.add(new JLabel("Linh kiện thay thế (nếu có):"));
        txtLinhKien = new JTextField();
        txtLinhKien.setMaximumSize(new Dimension(300, 30));
        pnlRight.add(txtLinhKien);

        pnlRight.add(Box.createVerticalStrut(20));

        // Nút bấm
        JPanel pnlButton = new JPanel(new FlowLayout());
        btnCapNhat = new JButton("Cập Nhật");
        btnCapNhat.setBackground(new Color(30, 144, 255)); // DodgerBlue
        btnCapNhat.setForeground(Color.BLACK);
        
        btnXemLichSu = new JButton("Xem Lịch Sử");
        
        pnlButton.add(btnCapNhat);
        pnlButton.add(btnXemLichSu);
        pnlButton.setMaximumSize(new Dimension(300, 50));
        
        pnlRight.add(pnlButton);

        add(pnlRight, BorderLayout.EAST);
    }

    // --- LOGIC: Tải dữ liệu từ DB lên bảng ---
    public void taiDuLieuLenBang() {
        tableModel.setRowCount(0); // Xóa bảng cũ
        List<Object[]> list = bus.layDanhSachPhieuChoTable();
        
        for (Object[] row : list) {
            // Row gồm: MaPhieu, Serial, TenSP, NgayNhan, Loi, TrangThai
            tableModel.addRow(row);
        }
    }

    private void ganSuKien() {
        // 1. Sự kiện click vào bảng -> Đổ dữ liệu sang Form bên phải
        tblDanhSach.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tblDanhSach.getSelectedRow();
                if (selectedRow != -1) {
                    maPhieuDangChon = (int) tblDanhSach.getValueAt(selectedRow, 0);
                    String tenMay = (String) tblDanhSach.getValueAt(selectedRow, 2);
                    String trangThaiHienTai = (String) tblDanhSach.getValueAt(selectedRow, 5);

                    // Hiển thị lên Label
                    lblMaPhieuVal.setText(String.valueOf(maPhieuDangChon));
                    lblTenMayVal.setText(tenMay);
                    
                    // Set combobox về đúng trạng thái hiện tại
                    cboTrangThai.setSelectedItem(trangThaiHienTai);
                }
            }
        });

        // 2. Sự kiện nút Cập nhật
        btnCapNhat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyCapNhat();
            }
        });

        // 3. Sự kiện nút Xem Lịch sử
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

        // Tạo đối tượng LichSuXuLy
        LichSuXuLy lichSu = new LichSuXuLy();
        lichSu.setMaPhieu(maPhieuDangChon);
        
        // LƯU Ý: Ở đây ta tạm set cứng Mã Nhân Viên = 3 (Kỹ thuật)
        // Khi ghép với code của bạn kia (có chức năng Login), bạn sẽ lấy ID từ biến Session
        lichSu.setMaNhanVien(3); 
        
        lichSu.setNoiDungXuLy(noiDung);
        lichSu.setLinhKienThayThe(linhKien);
        lichSu.setNgayHoanThanh(new Date(System.currentTimeMillis()));

        // Gọi BUS cập nhật (Transaction)
        boolean ketQua = bus.capNhatTienDo(maPhieuDangChon, trangThaiMoi, lichSu);

        if (ketQua) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            taiDuLieuLenBang(); // Refresh lại bảng
            
            // Clear form
            txtNoiDungXuLy.setText("");
            txtLinhKien.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra, vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hienThiLichSu() {
        // 1. Kiểm tra xem đã chọn dòng nào trên bảng chưa
        if (maPhieuDangChon == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu để xem lịch sử!");
            return;
        }

        // 2. Lấy cửa sổ cha (MainFrame) để làm owner cho Dialog
        // SwingUtilities.getWindowAncestor(this) giúp tìm ra Frame chứa Panel này
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        
        // 3. Khởi tạo và hiển thị DialogChiTiet
        // Truyền vào Frame cha và Mã phiếu đang chọn
        DialogChiTiet dialog = new DialogChiTiet(parentFrame, maPhieuDangChon);
        
        // 4. Hiển thị Dialog (Do setModal(true) nên code sẽ dừng tại đây cho đến khi tắt dialog)
        dialog.setVisible(true);
    }
}