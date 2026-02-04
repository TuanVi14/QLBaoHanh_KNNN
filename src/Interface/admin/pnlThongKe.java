package Interface.admin;

import javax.swing.*;
import java.awt.*;
import Process.ThongKe;

public class pnlThongKe extends JPanel {

    private static final long serialVersionUID = 1L;
    private ThongKe tk = new ThongKe();

    // dữ liệu biểu đồ
    private int homNay = 0;
    private int tuanNay = 0;
    private int thangNay = 0;
    private double tyLeHoanThanh = 0;

    public pnlThongKe() {
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        JLabel lblTitle = new JLabel("BÁO CÁO & THỐNG KÊ");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setBounds(20, 10, 300, 30);
        add(lblTitle);

        // ===== BIỂU ĐỒ CỘT =====
        JPanel pnlBar = new BarChartPanel();
        pnlBar.setBounds(20, 50, 300, 330);
        pnlBar.setBackground(Color.WHITE);
        pnlBar.setBorder(BorderFactory.createTitledBorder("PHIẾU BẢO HÀNH THEO THỜI GIAN"));
        add(pnlBar);

        // ===== BIỂU ĐỒ TRÒN =====
        JPanel pnlPie = new PieChartPanel();
        pnlPie.setBounds(340, 50, 300, 330);
        pnlPie.setBackground(Color.WHITE);
        pnlPie.setBorder(BorderFactory.createTitledBorder("TỶ LỆ HOÀN THÀNH"));
        add(pnlPie);

        loadData();
    }

    // ================= LOAD DATA =================
    private void loadData() {
        try {
            homNay = tk.ThongKeHomNay();
            tuanNay = tk.ThongKeTuanNay();
            thangNay = tk.ThongKeThangNay();
            tyLeHoanThanh = tk.TyLeHoanThanh();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load dữ liệu thống kê");
        }
    }

    // ================= BIỂU ĐỒ CỘT =================
    class BarChartPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            int[] values = { homNay, tuanNay, thangNay };
            String[] labels = { "Hôm nay", "Tuần này", "Tháng này" };
            Color[] colors = {
                new Color(52, 152, 219),
                new Color(241, 196, 15),
                new Color(46, 204, 113)
            };

            int max = Math.max(values[0], Math.max(values[1], values[2]));
            if (max == 0) max = 1;

            int baseY = 250;
            int barWidth = 50;
            int gap = 40;
            int startX = 40;

            for (int i = 0; i < values.length; i++) {
                int barHeight = values[i] * 180 / max;

                g2.setColor(colors[i]);
                g2.fillRect(startX + i * (barWidth + gap),
                        baseY - barHeight,
                        barWidth,
                        barHeight);

                g2.setColor(Color.BLACK);
                g2.drawString(values[i] + " phiếu",
                        startX + i * (barWidth + gap),
                        baseY - barHeight - 5);
                g2.drawString(labels[i],
                        startX + i * (barWidth + gap),
                        baseY + 20);
            }
        }
    }

    // ================= BIỂU ĐỒ TRÒN =================
    class PieChartPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            int hoanThanh = (int) Math.round(tyLeHoanThanh);
            int dangXuLy = 100 - hoanThanh;

            // Hoàn thành
            g2.setColor(new Color(46, 204, 113));
            g2.fillArc(50, 60, 200, 200, 0, hoanThanh * 360 / 100);

            // Đang xử lý
            g2.setColor(new Color(231, 76, 60));
            g2.fillArc(50, 60, 200, 200,
                    hoanThanh * 360 / 100,
                    dangXuLy * 360 / 100);

            // chú thích
            g2.setColor(Color.BLACK);
            g2.drawString("Hoàn thành: " + hoanThanh + "%", 70, 280);
            g2.drawString("Đang xử lý: " + dangXuLy + "%", 70, 300);
        }
    }
}