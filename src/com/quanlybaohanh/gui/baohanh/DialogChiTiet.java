package com.quanlybaohanh.gui.baohanh;

import com.quanlybaohanh.bus.NghiepVuBaoHanhBUS;
import com.quanlybaohanh.dto.LichSuXuLy;
import com.quanlybaohanh.util.DinhDang;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DialogChiTiet extends JDialog {

    private JTable tblLichSu;
    private DefaultTableModel tableModel;
    private JButton btnDong;
    private NghiepVuBaoHanhBUS bus;
    private int maPhieu;

    // Constructor nhận vào Frame cha và Mã phiếu cần xem
    public DialogChiTiet(JFrame parent, int maPhieuInput) {
        super(parent, "Chi tiết lịch sử xử lý", true); // true = Modal (phải đóng dialog mới thao tác tiếp được)
        this.maPhieu = maPhieuInput;
        this.bus = new NghiepVuBaoHanhBUS();
        
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        khoiTaoGiaoDien();
        taiDuLieu();
    }

    private void khoiTaoGiaoDien() {
        // Tiêu đề
        JLabel lblTitle = new JLabel("LỊCH SỬ XỬ LÝ PHIẾU SỐ: " + maPhieu, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setForeground(Color.BLUE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng dữ liệu
        String[] cols = {"Ngày Xử Lý", "NV Thực Hiện", "Nội Dung", "Linh Kiện Thay"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblLichSu = new JTable(tableModel);
        tblLichSu.setRowHeight(25);
        
        // Chỉnh độ rộng cột cho đẹp
        tblLichSu.getColumnModel().getColumn(0).setPreferredWidth(100); // Ngày
        tblLichSu.getColumnModel().getColumn(1).setPreferredWidth(80);  // NV
        tblLichSu.getColumnModel().getColumn(2).setPreferredWidth(250); // Nội dung
        
        add(new JScrollPane(tblLichSu), BorderLayout.CENTER);

        // Nút Đóng
        JPanel pnlSouth = new JPanel();
        btnDong = new JButton("Đóng");
        btnDong.setPreferredSize(new Dimension(100, 35));
        btnDong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Tắt Dialog
            }
        });
        pnlSouth.add(btnDong);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void taiDuLieu() {
        List<LichSuXuLy> list = bus.xemLichSuSuaChua(maPhieu);
        tableModel.setRowCount(0);
        
        for (LichSuXuLy ls : list) {
            tableModel.addRow(new Object[]{
                DinhDang.formatNgay(ls.getNgayHoanThanh()), // Dùng file DinhDang vừa tạo
                ls.getMaNhanVien(), // Sau này có thể join để lấy tên nhân viên
                ls.getNoiDungXuLy(),
                ls.getLinhKienThayThe()
            });
        }
    }
}