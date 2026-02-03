package Interface;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

// Import các class xử lý của bạn
import Process.*; 

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tenDN;
    private JPasswordField txtPass;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set giao diện giống hệ điều hành (Windows) cho đẹp
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        setTitle("Đăng Nhập Hệ Thống"); // Thêm tiêu đề cho cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 320); // Tăng chiều cao một chút để thoáng
        setLocationRelativeTo(null); // Căn giữa màn hình
        setResizable(false); // Không cho kéo giãn cửa sổ

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        // Set màu nền sáng nhẹ cho toàn bộ form
        contentPane.setBackground(new Color(240, 248, 255)); 

        // --- PANEL TIÊU ĐỀ ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 484, 60);
        panelHeader.setBackground(new Color(176, 224, 230)); // Màu xanh nhạt (PowderBlue)
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(0, 51, 102)); // Màu xanh đậm (Dark Blue) - KHÔNG PHẢI TRẮNG
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(0, 15, 484, 30);
        panelHeader.add(lblTitle);

        // --- PANEL NỘI DUNG ---
        JPanel panelBody = new JPanel();
        panelBody.setBounds(10, 70, 464, 200);
        panelBody.setBackground(new Color(240, 248, 255)); // Đồng bộ màu nền
        panelBody.setLayout(null);
        contentPane.add(panelBody);

        // 1. Tài khoản
        JLabel lblUser = new JLabel("Tài khoản:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(Color.BLACK); // Chữ đen
        lblUser.setBounds(40, 24, 100, 29);
        panelBody.add(lblUser);

        tenDN = new JTextField();
        tenDN.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tenDN.setBounds(130, 24, 280, 30);
        panelBody.add(tenDN);
        tenDN.setColumns(10);

        // 2. Mật khẩu
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(Color.BLACK); // Chữ đen
        lblPass.setBounds(40, 80, 100, 29);
        panelBody.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setBounds(130, 80, 280, 30);
        panelBody.add(txtPass);

        // 3. Nút Đăng nhập
        JButton btnDangNhap = new JButton("ĐĂNG NHẬP");
        // Logic xử lý sự kiện giữ nguyên
        btnDangNhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xuLyDangNhap();
            }
        });
        
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangNhap.setBackground(new Color(70, 130, 180)); // SteelBlue
        btnDangNhap.setForeground(Color.BLACK); // Chữ ĐEN theo yêu cầu (hoặc để mặc định)
        btnDangNhap.setFocusPainted(false); // Bỏ viền focus khi click
        btnDangNhap.setBounds(130, 140, 280, 40); // Nút to hơn cho dễ bấm
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Đổi chuột thành bàn tay
        panelBody.add(btnDangNhap);

        // Tính năng: Bấm Enter cũng kích hoạt nút Đăng nhập
        this.getRootPane().setDefaultButton(btnDangNhap);
    }

    /**
     * Tách logic xử lý ra hàm riêng cho gọn
     */
    private void xuLyDangNhap() {
        String username = tenDN.getText();
        String password = new String(txtPass.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(Login.this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            NhanVien nv = new NhanVien();
            String mkHash = MD5Util.encryptMD5(password);

            ResultSet rs = nv.DangNhap(username, mkHash);
            if (rs.next()) {
                String mavaitro = rs.getString("MaVaiTro");
                String tennv = rs.getString("TenNhanVien");
                // Lấy thêm mã nhân viên nếu cần cho MainFrame
                int maNV = rs.getInt("MaNhanVien"); 

                JOptionPane.showMessageDialog(Login.this, "Đăng nhập thành công!\nXin chào: " + tennv);
                Login.this.dispose();

                switch (mavaitro) {
                    case "1": { // Admin
                        new Admin(tennv).setVisible(true);
                        break;
                    }
                    case "3": { // Kỹ thuật viên (Thêm case này cho MainFrame)
                         // Giả sử MainFrame của bạn có constructor nhận tên và mã NV
                         // Nếu MainFrame chưa update constructor, hãy dùng new MainFrame()
                         // new MainFrame(tennv, maNV).setVisible(true); 
                         new MainFrame().setVisible(true); // Dùng tạm cái này nếu chưa sửa MainFrame
                         break;
                    }
                    default:
                        JOptionPane.showMessageDialog(Login.this, "Vai trò không hợp lệ hoặc chưa được cấp quyền!");
                        // System.exit(0); // Không nên exit hẳn, chỉ không mở form thôi
                        new Login().setVisible(true); // Mở lại login
                }
            } else {
                JOptionPane.showMessageDialog(Login.this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(Login.this, "Lỗi kết nối cơ sở dữ liệu!");
        }
    }
}