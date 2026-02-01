package Interface.admin;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import Process.BaoHanh;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class pnlBaoHanh extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtTimKiem;
	private JTable table;
	
	private final BaoHanh bh = new BaoHanh();

	/**
	 * Create the panel.
	 */
	public pnlBaoHanh() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Trạng Thái:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(42, 68, 68, 14);
		add(lblNewLabel);
		
		JComboBox jcomTrangThai = new JComboBox();
		jcomTrangThai.setBounds(161, 65, 147, 22);
		add(jcomTrangThai);
		
		JLabel lblNewLabel_1 = new JLabel("Tên Khách Hàng:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(42, 28, 98, 14);
		add(lblNewLabel_1);
		
		txtTimKiem = new JTextField();
		txtTimKiem.setBounds(161, 26, 210, 20);
		add(txtTimKiem);
		txtTimKiem.setColumns(10);
		
		JButton btnTimKiem = new JButton("Tìm Kiếm");
		btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnTimKiem.setBounds(394, 24, 98, 23);
		add(btnTimKiem);
		
		JButton btnExcel = new JButton("Xuất Excel");
		btnExcel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcel.setBounds(332, 64, 98, 23);
		add(btnExcel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 118, 649, 282);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnLamMoi = new JButton("Làm Mới");
		btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLamMoi.setBounds(514, 25, 89, 23);
		add(btnLamMoi);
		
		JButton btnXemChiTiet = new JButton("Xem Chi Tiết");
		btnXemChiTiet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnXemChiTiet.setBounds(458, 64, 112, 23);
		add(btnXemChiTiet);

	}

}
