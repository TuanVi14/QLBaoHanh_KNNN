package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class Admin extends JFrame{
	
	public Admin(String tennv) {
		setTitle("Admin - Quản Lý Bảo Hành");
        setSize(880, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 842, 56);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("PHẦN MỀM QUẢN LÝ BẢO HÀNH");
		lblNewLabel.setBounds(51, 11, 396, 31);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Xin chào: "+tennv);
		lblNewLabel_1.setBounds(515, 21, 205, 19);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Đăng xuất");
		btnNewButton.setBounds(730, 11, 102, 33);
		panel.add(btnNewButton);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 78, 163, 411);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnTrangChu = new JButton("Trang Chủ");
		btnTrangChu.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnTrangChu);
		
		JButton btnKhachHang = new JButton("Khách Hàng");
		btnKhachHang.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnKhachHang);
		
		JButton btnSanPham = new JButton("Sản Phảm");
		btnSanPham.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnSanPham);
		
		JButton btnNhanVien = new JButton("Nhân Viên");
		btnNhanVien.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnNhanVien);
		
		JButton btnBaoHanh = new JButton("Bảo Hành");
		btnBaoHanh.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnBaoHanh);
		
		JButton btnThongKe = new JButton("Thóng Kê");
		btnThongKe.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnThongKe);
		
		JPanel pnlContent = new JPanel();
		pnlContent.setBounds(183, 78, 669, 411);
		CardLayout cardLayout = new  CardLayout();
		pnlContent.setLayout(cardLayout);
		getContentPane().add(pnlContent);
		
		/* ===== CÁC PANEL CON ===== */
		JPanel pnlTrangChu = new JPanel();
        pnlContent.add(pnlTrangChu, "TRANGCHU");
        pnlTrangChu.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("trng chu");
        lblNewLabel_2.setBounds(243, 92, 76, 37);
        pnlTrangChu.add(lblNewLabel_2);
        
        JPanel pnlKhachHang = new JPanel();
        pnlContent.add(pnlKhachHang, "KHACHHANG");
        pnlKhachHang.setLayout(null);
        
        JLabel lblNewLabel_3 = new JLabel("QL kh");
        lblNewLabel_3.setBounds(207, 121, 46, 14);
        pnlKhachHang.add(lblNewLabel_3);
        
        JPanel pnlSanPham = new JPanel();
        pnlContent.add(pnlSanPham, "SANPHAM");
        pnlSanPham.setLayout(null);
        
        JLabel lblNewLabel_4 = new JLabel("QL sp");
        lblNewLabel_4.setBounds(257, 130, 46, 14);
        pnlSanPham.add(lblNewLabel_4);
        
        JPanel pnlNhanVien = new JPanel();
        pnlContent.add(pnlNhanVien, "NHANVIEN");
        pnlNhanVien.setLayout(null);
        
        JLabel lblNewLabel_5 = new JLabel("QL nv");
        lblNewLabel_5.setBounds(266, 120, 46, 14);
        pnlNhanVien.add(lblNewLabel_5);
        
        JPanel pnlBaoHanh = new JPanel();
        pnlContent.add(pnlBaoHanh, "BAOHANH");
        pnlBaoHanh.setLayout(null);
        
        JLabel lblNewLabel_6 = new JLabel("QL bh");
        lblNewLabel_6.setBounds(141, 222, 46, 14);
        pnlBaoHanh.add(lblNewLabel_6);
        
        JPanel pnlThongKe = new JPanel();
        pnlContent.add(pnlThongKe, "THONGKE");
        pnlThongKe.setLayout(null);
        
        JLabel lblNewLabel_7 = new JLabel("BC tk");
        lblNewLabel_7.setBounds(198, 194, 46, 14);
        pnlThongKe.add(lblNewLabel_7);
        

        /* ===== SỰ KIỆN MENU ===== */
        btnTrangChu.addActionListener(e -> cardLayout.show(pnlContent, "TRANGCHU"));
        btnKhachHang.addActionListener(e -> cardLayout.show(pnlContent, "KHACHHANG"));
        btnSanPham.addActionListener(e -> cardLayout.show(pnlContent, "SANPHAM"));
        btnNhanVien.addActionListener(e -> cardLayout.show(pnlContent, "NHANVIEN"));
        btnBaoHanh.addActionListener(e -> cardLayout.show(pnlContent, "BAOHANH"));
        btnThongKe.addActionListener(e -> cardLayout.show(pnlContent, "THONGKE"));
        
        

        // Mặc định
        //cardLayout.show(pnlContent, "TRANGCHU");
	}
}