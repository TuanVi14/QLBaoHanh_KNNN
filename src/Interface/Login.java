package Interface;

import java.awt.EventQueue;

import javax.swing.*;

import java.awt.*;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Process.*;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 298);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 464, 55);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ĐĂNG NHẬP");
		lblNewLabel.setForeground(new Color(0, 0, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(167, 11, 128, 33);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 77, 464, 177);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		tenDN = new JTextField();
		tenDN.setText("");
		tenDN.setBounds(101, 24, 257, 29);
		panel_1.add(tenDN);
		tenDN.setColumns(10);
		
		JButton btnDangNhap = new JButton("ĐĂNG NHẬP");
		btnDangNhap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = tenDN.getText();
				String password = new String(txtPass.getPassword());

				if (username.isEmpty() || password.isEmpty()) {
				    JOptionPane.showMessageDialog(Login.this,
				        "Vui lòng nhập đầy đủ thông tin!");
				    return;
				}
				
				try {
					NhanVien nv = new NhanVien();
					String mkHash = MD5Util.encryptMD5(password);
					
					ResultSet rs = nv.DangNhap(username, mkHash);
					if(rs.next()) {
						String mavaitro = rs.getString("MaVaiTro");
						String tennv = rs.getString("TenNhanVien");
						
						JOptionPane.showMessageDialog(Login.this, "Đăng nhập thành công!");
						Login.this.dispose();
						
						switch (mavaitro) {
						case "1": {
							new Admin(tennv).setVisible(true);
							break;							
						}
						default:
							JOptionPane.showMessageDialog(Login.this,
			                        "Vai trò không hợp lệ!");
			                    System.exit(0);
						}
					}
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDangNhap.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDangNhap.setBounds(171, 131, 112, 29);
		panel_1.add(btnDangNhap);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(101, 77, 257, 29);
		panel_1.add(txtPass);

	}
}