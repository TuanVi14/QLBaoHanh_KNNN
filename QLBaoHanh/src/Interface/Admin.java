package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import Interface.admin.*;

public class Admin extends JFrame{
	
	public Admin(String tennv) throws SQLException {
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
		
		JButton btnDX = new JButton("Đăng xuất");
		btnDX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin.this.dispose();
				new Login().setVisible(true);
			}
		});
		btnDX.setBounds(730, 11, 102, 33);
		panel.add(btnDX);
		
		
		
		JPanel pnlContent = new JPanel();
		pnlContent.setBounds(183, 78, 669, 411);
		CardLayout cardLayout = new  CardLayout();
		pnlContent.setLayout(cardLayout);
		getContentPane().add(pnlContent);
		
		/* ===== CÁC PANEL CON ===== */
		JPanel pnlTrangChu = new JPanel();
        pnlContent.add(pnlTrangChu, "TRANGCHU");
        pnlTrangChu.setLayout(null);
        
        
        
        pnlKhachHang pnlKhachHang = new pnlKhachHang();
        pnlContent.add(pnlKhachHang, "KHACHHANG");
        
        pnlSanPham pnlSanPham = new pnlSanPham();
        pnlContent.add(pnlSanPham, "SANPHAM");
        
        pnlNhanVien pnlNhanVien = new pnlNhanVien();
        pnlContent.add(pnlNhanVien, "NHANVIEN");
        
        JPanel pnlBaoHanh = new JPanel();
        pnlContent.add(pnlBaoHanh, "BAOHANH");
        pnlBaoHanh.setLayout(null);
        
        JLabel lblNewLabel_6 = new JLabel("QL bh");
        lblNewLabel_6.setBounds(141, 222, 46, 14);
        pnlBaoHanh.add(lblNewLabel_6);
        
        pnlThongKe pnlThongKe = new pnlThongKe();
        pnlContent.add(pnlThongKe, "THONGKE");
        pnlThongKe.setLayout(null);
        
        JLabel lblNewLabel_7 = new JLabel("BC tk");
        lblNewLabel_7.setBounds(198, 194, 46, 14);
        pnlThongKe.add(lblNewLabel_7);
        

        /* ===== SỰ KIỆN MENU ===== */

        JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 78, 163, 411);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnTrangChu = new JButton("Trang Chủ");
		btnTrangChu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTrangChu.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnTrangChu);
		
		JButton btnKhachHang = new JButton("Khách Hàng");
		btnKhachHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(pnlContent, "KHACHHANG");
			}
		});
		btnKhachHang.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnKhachHang);
		
		JButton btnSanPham = new JButton("Sản Phảm");
		btnSanPham.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(pnlContent, "SANPHAM");
			}
		});
		btnSanPham.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnSanPham);
		
		JButton btnNhanVien = new JButton("Nhân Viên");
		btnNhanVien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(pnlContent, "NHANVIEN");
			}
		});
		btnNhanVien.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnNhanVien);
		
		JButton btnBaoHanh = new JButton("Bảo Hành");
		btnBaoHanh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBaoHanh.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnBaoHanh);
		
		JButton btnThongKe = new JButton("Thống Kê");
		btnThongKe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnThongKe.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel_1.add(btnThongKe);
            
	}
	
}
