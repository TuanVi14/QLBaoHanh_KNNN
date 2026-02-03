package Interface;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Import các class xử lý logic và mã hóa
import Process.NhanVien;
import Process.MD5Util;

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
                    // Set giao diện đẹp theo hệ thống (Windows)
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
        setTitle("Đăng Nhập Hệ Thống");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 320);
        setLocationRelativeTo(null); // Ra giữa màn hình
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(240, 248, 255)); // Màu nền AliceBlue

        // --- PANEL TIÊU ĐỀ ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 484, 60);
        panelHeader.setBackground(new Color(176, 224, 230)); // PowderBlue
        contentPane.add(panelHeader);
        panelHeader.setLayout(null);

        JLabel lblTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(0, 51, 102));
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(0, 15, 484, 30);
        panelHeader.add(lblTitle);

        // --- PANEL NỘI DUNG ---
        JPanel panelBody = new JPanel();
        panelBody.setBounds(10, 70, 464, 200);
        panelBody.setBackground(new Color(240, 248, 255));
        panelBody.setLayout(null);
        contentPane.add(panelBody);

        // 1. Tài khoản
        JLabel lblUser = new JLabel("Tài khoản:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUser.setForeground(Color.BLACK);
        lblUser.setBounds(40, 24, 100, 29);
        panelBody.add(lblUser);

        tenDN = new JTextField();
        tenDN.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tenDN.setBounds(130, 24, 280, 30);
        panelBody.add(tenDN);
        
        // 2. Mật khẩu
        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(Color.BLACK);
        lblPass.setBounds(40, 80, 100, 29);
        panelBody.add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPass.setBounds(130, 80, 280, 30);
        panelBody.add(txtPass);

        // 3. Nút Đăng nhập
        JButton btnDangNhap = new JButton("ĐĂNG NHẬP");
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDangNhap.setBackground(new Color(70, 130, 180)); // SteelBlue
        btnDangNhap.setForeground(Color.BLACK); // Màu chữ đen cho dễ đọc
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setBounds(130, 140, 280, 40);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Sự kiện Click chuột
        btnDangNhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xuLyDangNhap();
            }
        });
        
        panelBody.add(btnDangNhap);

        // Tính năng: Bấm Enter ở ô mật khẩu cũng đăng nhập được
        txtPass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    xuLyDangNhap();
                }
            }
        });
        
        // Set nút mặc định khi bấm Enter
        this.getRootPane().setDefaultButton(btnDangNhap);
    }

    /**
     * Hàm xử lý đăng nhập chính
     */
    private void xuLyDangNhap() {
        String username = tenDN.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        // 1. Kiểm tra rỗng
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập đầy đủ Tài khoản và Mật khẩu!", 
                "Cảnh báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            NhanVien nv = new NhanVien();
            
            // 2. Mã hóa mật khẩu MD5 (Nếu CSDL lưu pass thường thì bỏ dòng này)
            // String mkHash = password; // Dùng dòng này nếu DB lưu pass thường
            String mkHash = MD5Util.encryptMD5(password); // Dùng dòng này nếu DB lưu MD5

            // 3. Gọi hàm kiểm tra đăng nhập
            ResultSet rs = nv.DangNhap(username, mkHash);
            
            if (rs.next()) {
                // Lấy thông tin từ CSDL
                String maVaiTro = rs.getString("MaVaiTro");
                String tenNV = rs.getString("TenNhanVien");
                int maNV = rs.getInt("MaNhanVien"); // Lấy ID để ghi Log

                // 4. Thông báo và Chuyển màn hình
                JOptionPane.showMessageDialog(this, 
                    "Đăng nhập thành công!\nXin chào: " + tenNV);
                
                this.dispose(); // Đóng form Login

                // --- ĐÂY LÀ PHẦN QUAN TRỌNG ĐÃ CẬP NHẬT ---
                // Không dùng switch case để mở các form khác nhau nữa
                // Mở duy nhất MainFrame và truyền tham số vào để nó tự phân quyền
                new MainFrame(tenNV, maVaiTro, maNV).setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Sai Tên đăng nhập hoặc Mật khẩu!", 
                    "Lỗi đăng nhập", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi kết nối CSDL hoặc lỗi hệ thống:\n" + e.getMessage(), 
                "Lỗi nghiêm trọng", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}