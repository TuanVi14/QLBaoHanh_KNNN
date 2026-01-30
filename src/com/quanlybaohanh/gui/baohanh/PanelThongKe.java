package com.quanlybaohanh.gui.baohanh;

import com.quanlybaohanh.bus.ThongKeBUS;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PanelThongKe extends JPanel {

    private JLabel lblMayDangSua;
    private JLabel lblMayDaXong;
    private ThongKeBUS bus;

    public PanelThongKe() {
        bus = new ThongKeBUS();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Tiêu đề
        JLabel lblTitle = new JLabel("TỔNG QUAN TÌNH HÌNH BẢO HÀNH", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, BorderLayout.NORTH);

        // Panel chứa các Card số liệu
        JPanel pnlCards = new JPanel(new GridLayout(1, 2, 20, 0)); // 1 dòng, 2 cột

        // --- Card 1: Máy đang sửa ---
        JPanel card1 = taoTheThongKe("ĐANG SỬA CHỮA", new Color(255, 140, 0)); // Màu Cam
        lblMayDangSua = (JLabel) card1.getClientProperty("valueLabel"); // Lấy lại label để set text sau này
        pnlCards.add(card1);

        // --- Card 2: Máy đã xong ---
        JPanel card2 = taoTheThongKe("ĐÃ HOÀN THÀNH", new Color(46, 139, 87)); // Màu Xanh lá biển
        lblMayDaXong = (JLabel) card2.getClientProperty("valueLabel");
        pnlCards.add(card2);

        add(pnlCards, BorderLayout.CENTER);

        // Nút làm mới dữ liệu
        JButton btnRefresh = new JButton("Cập nhật số liệu mới nhất");
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));
        btnRefresh.setPreferredSize(new Dimension(200, 40));
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taiDuLieu();
            }
        });
        
        JPanel pnlButton = new JPanel();
        pnlButton.add(btnRefresh);
        add(pnlButton, BorderLayout.SOUTH);

        // Tải dữ liệu lần đầu
        taiDuLieu();
    }

    // Hàm tạo giao diện cho 1 thẻ thống kê (Card)
    private JPanel taoTheThongKe(String title, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel lblHeader = new JLabel(title, SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel lblValue = new JLabel("0", SwingConstants.CENTER);
        lblValue.setFont(new Font("Arial", Font.BOLD, 80));
        lblValue.setForeground(Color.WHITE);

        panel.add(lblHeader, BorderLayout.NORTH);
        panel.add(lblValue, BorderLayout.CENTER);
        
        // Lưu tham chiếu label vào property để lấy ra dùng sau
        panel.putClientProperty("valueLabel", lblValue);

        return panel;
    }

    // Gọi BUS lấy số liệu mới nhất
    public void taiDuLieu() {
        int dangSua = bus.demMayDangSua();
        int daXong = bus.demMayDaXong();

        lblMayDangSua.setText(String.valueOf(dangSua));
        lblMayDaXong.setText(String.valueOf(daXong));
    }
}