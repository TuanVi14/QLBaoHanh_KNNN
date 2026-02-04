package Interface.admin;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import Process.BaoHanh;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.awt.event.ActionEvent;

public class pnlBaoHanh extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtTimKiem;
	private JTable table;
	
	private final BaoHanh bh = new BaoHanh();
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private JComboBox jcomTrangThai;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public pnlBaoHanh() throws SQLException {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Trạng Thái:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(42, 68, 68, 14);
		add(lblNewLabel);
		
		jcomTrangThai = new JComboBox();
		jcomTrangThai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String trangThai = jcomTrangThai.getSelectedItem().toString();
			    try {
			        if (trangThai.equals("Tất cả")) {
			            ShowData();
			        } else {
			            ResultSet rs = bh.locPhieuBaoHanhTheoTrangThai(trangThai);
			            ClearData();
			            while (rs.next()) {
			                tableModel.addRow(new Object[]{
			                    rs.getString(1),
			                    rs.getString(2),
			                    rs.getString(3),
			                    rs.getString(4),
			                    rs.getString(5)
			                });
			            }
			        }
			    } catch (SQLException ex) {
			        ex.printStackTrace();
			    }
			}
		});
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
		btnTimKiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tsp = txtTimKiem.getText().toString();
				try {
					ShowData(tsp);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnTimKiem.setBounds(394, 24, 98, 23);
		add(btnTimKiem);
		
		JButton btnExcel = new JButton("Xuất File");
		btnExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportToHTML(table);
			}
		});
		btnExcel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcel.setBounds(332, 64, 98, 23);
		add(btnExcel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 118, 649, 282);
		add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		String []colsName = {"Mã BH", "Khách Hàng","Sản Phẩm","Ngày Nhận","Trạng Thái"}; 
        // đặt tiêu đề cột cho tableModel 
        tableModel.setColumnIdentifiers(colsName);   
        // kết nối jtable với tableModel   
        table.setModel(tableModel);
		
		JButton btnLamMoi = new JButton("Làm Mới");
		btnLamMoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ShowData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLamMoi.setBounds(514, 25, 89, 23);
		add(btnLamMoi);
		
		JButton btnXemChiTiet = new JButton("Xem Chi Tiết");
		btnXemChiTiet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();

			    if (row == -1) {
			        JOptionPane.showMessageDialog(
			                null,
			                "Vui lòng chọn một phiếu bảo hành!",
			                "Thông báo",
			                JOptionPane.WARNING_MESSAGE
			        );
			        return;
			    }

			    int maPhieu = Integer.parseInt(table.getValueAt(row, 0).toString());

			    dlgChiTietBaoHanh dialog = new dlgChiTietBaoHanh(maPhieu);
			    dialog.setVisible(true);
			}
		});
		btnXemChiTiet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnXemChiTiet.setBounds(458, 64, 112, 23);
		add(btnXemChiTiet);
		
		
		ShowData();
		ShowDataCombo();
	}
	//Ham xoa du lieu trong tableModel 
    public void ClearData() throws SQLException{ 
         //Lay chi so dong cuoi cung 
         int n=tableModel.getRowCount()-1; 
         for(int i=n;i>=0;i--) 
            tableModel.removeRow(i);//Remove tung dong          
    }
	
	public final void ShowData() throws SQLException {         
        ResultSet result=null;           
        result = bh.ShowPhieuBH(); 
        try {   
            ClearData(); 
            while(result.next()){  
                String rows[] = new String[5]; 
                rows[0] = result.getString(1);  
                rows[1] = result.getString(2);  
                rows[2] = result.getString(3);  
                rows[3] = result.getString(4);   
                rows[4] = result.getString(5); 
                tableModel.addRow(rows); 
            } 
        }  
        catch (SQLException e) { 
        }  
    }
	
	public final void ShowData(String ml) throws SQLException {         
		ResultSet result=null;           
		result = bh.ShowPhieuBHTheoTK(ml);
		try {   
			ClearData(); 
			while(result.next()){  
				String rows[] = new String[5]; 
				rows[0] = result.getString(1);  
				rows[1] = result.getString(4);
				rows[2] = result.getString(2);  
			    rows[3] = result.getString(5);      
			    rows[4] = result.getString(6);
			    tableModel.addRow(rows); 
			} 
		}  
		catch (SQLException e) { 
		}  
	}
	
	public final void ShowDataCombo() { 
		jcomTrangThai.removeAllItems();
		jcomTrangThai.addItem("Tất cả");
        ResultSet result=null;           
        try { 
            result = bh.ShowTrangThai();            
            while(result.next()){  
                jcomTrangThai.addItem(result.getString("TrangThai"));
            } 
        }  
        catch (SQLException e) { 
        }  
    }
	
	private void exportToHTML(JTable table) {
	    try {
	        JFileChooser chooser = new JFileChooser();
	        chooser.setSelectedFile(new File("DanhSachBaoHanh.html"));

	        if (chooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION)
	            return;

	        File file = chooser.getSelectedFile();

	        OutputStreamWriter writer = new OutputStreamWriter(
	                new FileOutputStream(file),
	                "UTF-8"
	        );

	        writer.write("\uFEFF"); // BOM để Excel đọc đúng tiếng Việt

	        writer.write("<html><head><meta charset='UTF-8'></head><body>");
	        writer.write("<table border='1' style='border-collapse:collapse'>");

	        // ===== Header =====
	        writer.write("<tr>");
	        for (int i = 0; i < table.getColumnCount(); i++) {
	            writer.write("<th>" + table.getColumnName(i) + "</th>");
	        }
	        writer.write("</tr>");

	        // ===== Data =====
	        for (int i = 0; i < table.getRowCount(); i++) {
	            writer.write("<tr>");
	            for (int j = 0; j < table.getColumnCount(); j++) {
	                Object val = table.getValueAt(i, j);
	                writer.write("<td>" + (val == null ? "" : val.toString()) + "</td>");
	            }
	            writer.write("</tr>");
	        }

	        writer.write("</table></body></html>");
	        writer.close();

	        JOptionPane.showMessageDialog(
	                null,
	                "Xuất HTML thành công!\n(Mở file bằng Excel)",
	                "Thông báo",
	                JOptionPane.INFORMATION_MESSAGE
	        );

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
