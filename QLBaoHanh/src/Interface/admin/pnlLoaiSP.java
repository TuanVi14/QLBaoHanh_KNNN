package Interface.admin;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Interface.Admin;
import Process.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class pnlLoaiSP extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtTimKiem;
	private JTextField txtML;
	private JTextField txtTL;
	private JTable table;
	
	private final LoaiSP ls = new LoaiSP();
	private final DefaultTableModel tableModel = new DefaultTableModel();
	private boolean cothem=true;
	
	private JButton btnThem, btnXoa,btnSua,btnLuu,btnKluu;
	/**
	 * Create the panel.
	 */
	public pnlLoaiSP() {
		setTitle(" Quản Lý Loại Sản Phẩm");
        setSize(669, 411);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tên Loại SP:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(56, 24, 73, 14);
		getContentPane().add(lblNewLabel);
		
		txtTimKiem = new JTextField();
		txtTimKiem.setBounds(152, 22, 356, 20);
		getContentPane().add(txtTimKiem);
		txtTimKiem.setColumns(10);
		
		JButton btnSearch = new JButton("Tìm Kiếm");
		btnSearch.addActionListener(new ActionListener() {
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
		btnSearch.setBounds(518, 21, 89, 23);
		getContentPane().add(btnSearch);
		
		JLabel lblNewLabel_1 = new JLabel("Mã Loại:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(56, 75, 49, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtML = new JTextField();
		txtML.setBounds(152, 73, 135, 20);
		getContentPane().add(txtML);
		txtML.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Tên Loại:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(327, 75, 59, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtTL = new JTextField();
		txtTL.setBounds(396, 73, 191, 20);
		getContentPane().add(txtTL);
		txtTL.setColumns(10);
		
		btnThem = new JButton("Thêm");
		btnThem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setKhoa(false);
				setNull();
			}
		});
		btnThem.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnThem.setBounds(56, 122, 89, 23);
		getContentPane().add(btnThem);
		
		btnSua = new JButton("Sửa");
		btnSua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ml=txtML.getText(); 
		        if(ml.length()==0) //Chua chon Ma loai              
		                JOptionPane.showMessageDialog(null,"Vui long chon loi can sua", "Thong bao",1); 
		        else 
		        { 
		            setKhoa(false);//Mo khoa cac TextField  
		            cothem=false; //Gan cothem=false de ghi nhan trang thai la sua 
		        }
			}
		});
		btnSua.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSua.setBounds(169, 122, 89, 23);
		getContentPane().add(btnSua);
		
		btnXoa = new JButton("Xóa");
		btnXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ma=txtML.getText(); 
		        try { 
		            if(ma.length()==0)              
		                JOptionPane.showMessageDialog(null,"Can chon 1 loại SP de xoa","Thong bao",1); 
		            else 
		            { 
		                if(JOptionPane.showConfirmDialog(null, "Ban muon xoa loại sp " + ma +  
		" này hay khong?","Thong bao",2)==0) 
		                {     
		                    ls.DeleteNhanVien(ma);//goi ham xoa du lieu theo ma loai 
		                    ClearData();//Xoa du lieu trong tableModel 
		                    ShowData();//Do du lieu vao table Model 
		                    setNull();//Xoa trang Textfield 
		                } 
		             } 
		        }  
		        catch (SQLException ex) { 
		            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
		        }
			}
		});
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnXoa.setBounds(282, 122, 89, 23);
		getContentPane().add(btnXoa);
		
		btnLuu = new JButton("Lưu");
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ma = Integer.parseInt(txtML.getText()); 
		        String ten=txtTL.getText(); 
		         
		         if(ma==0 || ten.length()==0)              
		                JOptionPane.showMessageDialog(null,"Vui long nhap Ma NV va ten nv", "Thong bao",1); 
		         else
		              try { 
		                if(cothem==true) {   //Luu cho tthem moi    
		                    ls.InsertLoaiSP(ma, ten); 
		                }
		                else                //Luu cho sua 
		                    ls.EditLoaiSP(ma, ten); 
		                ClearData(); //goi ham xoa du lieu tron tableModel 
		                ShowData(); //Do lai du lieu vao Table Model 
		              } 
		              catch (SQLException ex) { 
		                   JOptionPane.showMessageDialog(null,"Cap nhat that bai","Thong bao",1); 
		              }             
		            setNull(); 
		            setKhoa(true);
			}
		});
		btnLuu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLuu.setBounds(396, 122, 89, 23);
		getContentPane().add(btnLuu);
		
		btnKluu = new JButton("K.Lưu");
		btnKluu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNull();
				setKhoa(true);
			}
		});
		btnKluu.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnKluu.setBounds(507, 122, 89, 23);
		getContentPane().add(btnKluu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(127, 162, 420, 199);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Hien thi du lieu len cac JTextField khi Click chuot vao JTable 
		        try{ 
		            //Lay chi so dong dang chon 
		            int row =table.getSelectedRow(); 
		            String ma=(table.getModel().getValueAt(row,0)).toString(); 
		            ResultSet rs= ls.ShowLoaiSP(ma);//Goi ham lay du lieu theo ma loai 
		            if(rs.next())//Neu co du lieu 
		            { 
		             txtML.setText(rs.getString("MaLoai")); 
		             txtTL.setText(rs.getString("TenLoai")); 
		            } 
		        }
		        catch (SQLException e1) { 
		        }
			}
		});
		scrollPane.setViewportView(table);
		String []colsName = {"Mã Loại", "Tên Loại"}; 
        // đặt tiêu đề cột cho tableModel 
        tableModel.setColumnIdentifiers(colsName);   
        // kết nối jtable với tableModel   
        table.setModel(tableModel);
	}
	/* ===== HÀM XỬ LÝ SỰ KIỆN ===== */
	private void setKhoa(boolean a) {
		txtML.setEnabled(!a);
		txtTL.setEnabled(!a);
		btnLuu.setEnabled(!a);
		btnKluu.setEnabled(!a);
		btnThem.setEnabled(a);
		btnSua.setEnabled(a);
		btnXoa.setEnabled(a);
	}
	
	private void setNull() {
		txtML.setText(null);
		txtTL.setText(null);
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
        result = ls.ShowLoaiSP(); 
        try {   
            ClearData(); 
            while(result.next()){  
                String rows[] = new String[2]; 
                rows[0] = result.getString(1);  
                rows[1] = result.getString(2);                   
                tableModel.addRow(rows); 
            } 
        }  
        catch (SQLException e) { 
        }  
    }
    
    public final void ShowData(String ml) throws SQLException {         
		ResultSet result=null;           
		result = ls.ShowLoaiSP(ml);
		try {   
			ClearData(); 
			while(result.next()){  
				String rows[] = new String[2]; 
				rows[0] = result.getString(1);  
				rows[1] = result.getString(2);                 
			    tableModel.addRow(rows); 
			} 
		}  
		catch (SQLException e) { 
		}  
	}
}
