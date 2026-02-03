package Interface.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import Process.LoaiSP;

public class dlgQuanLyLoaiSP extends JDialog {

    private static final long serialVersionUID = 1L;
    
    private JTextField txtTimKiem;
    private JTextField txtMaLoai;
    private JTextField txtTenLoai;
    private JTable table;
    private DefaultTableModel tableModel;
    
    private final LoaiSP ls = new LoaiSP();
    private boolean cothem = true;
    
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnKluu, btnTimKiem, btnLamMoi;

    /**
     * Constructor
     */
    public dlgQuanLyLoaiSP(JFrame parent) {
        super(parent, "Quản Lý Loại Sản Phẩm", true); // Modal = true
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        initComponents();
        
        try {
            setNull();
            setKhoa(true);
            showData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        // --- TÌM KIẾM ---
        JLabel lblTim = new JLabel("Tìm kiếm (Tên):");
        lblTim.setBounds(30, 20, 100, 25);
        add(lblTim);

        txtTimKiem = new JTextField();
        txtTimKiem.setBounds(130, 20, 300, 25);
        add(txtTimKiem);

        btnTimKiem = new JButton("Tìm");
        btnTimKiem.setBounds(440, 20, 80, 25);
        btnTimKiem.addActionListener(e -> {
            try {
                showData(txtTimKiem.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        add(btnTimKiem);

        // --- NHẬP LIỆU ---
        JLabel lblMa = new JLabel("Mã Loại:");
        lblMa.setBounds(30, 65, 100, 25);
        add(lblMa);

        txtMaLoai = new JTextField();
        txtMaLoai.setBounds(130, 65, 120, 25);
        add(txtMaLoai);

        JLabel lblTen = new JLabel("Tên Loại:");
        lblTen.setBounds(270, 65, 80, 25);
        add(lblTen);

        txtTenLoai = new JTextField();
        txtTenLoai.setBounds(350, 65, 200, 25);
        add(txtTenLoai);

        // --- BUTTONS ---
        btnThem = createButton("Thêm", 30, 110);
        btnSua = createButton("Sửa", 120, 110);
        btnXoa = createButton("Xóa", 210, 110);
        btnLuu = createButton("Lưu", 300, 110);
        btnKluu = createButton("K.Lưu", 390, 110);
        
        btnLamMoi = createButton("Refresh", 480, 110);
        btnLamMoi.addActionListener(e -> {
            try {
                setNull();
                setKhoa(true);
                showData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // --- BẢNG ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 160, 520, 230);
        add(scrollPane);

        table = new JTable();
        tableModel = new DefaultTableModel(new String[]{"Mã Loại", "Tên Loại"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setModel(tableModel);
        scrollPane.setViewportView(table);

        // Sự kiện Click bảng
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaLoai.setText(table.getValueAt(row, 0).toString());
                    txtTenLoai.setText(table.getValueAt(row, 1).toString());
                }
            }
        });

        // --- SỰ KIỆN NÚT ---
        btnThem.addActionListener(e -> {
            setKhoa(false);
            setNull();
            cothem = true;
            txtMaLoai.requestFocus();
        });

        btnSua.addActionListener(e -> {
            if (txtMaLoai.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại cần sửa!");
            } else {
                setKhoa(false);
                cothem = false;
                txtMaLoai.setEnabled(false);
                txtTenLoai.requestFocus();
            }
        });

        btnXoa.addActionListener(e -> {
            String ma = txtMaLoai.getText();
            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn loại cần xóa!");
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    // --- ĐÃ SỬA: Gọi đúng tên hàm DeleteLoaiSP ---
                    ls.DeleteLoaiSP(ma); 
                    
                    showData();
                    setNull();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa: Loại này đang được sử dụng hoặc lỗi CSDL!");
                }
            }
        });

        btnLuu.addActionListener(e -> {
            try {
                if (txtMaLoai.getText().isEmpty() || txtTenLoai.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
                    return;
                }

                int ma = Integer.parseInt(txtMaLoai.getText());
                String ten = txtTenLoai.getText();

                if (cothem) {
                    ls.InsertLoaiSP(ma, ten);
                } else {
                    ls.EditLoaiSP(ma, ten);
                }
                
                showData();
                setNull();
                setKhoa(true);
                JOptionPane.showMessageDialog(this, "Thành công!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mã loại phải là số!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi CSDL: " + ex.getMessage());
            }
        });

        btnKluu.addActionListener(e -> {
            setNull();
            setKhoa(true);
        });
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 80, 25);
        btn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(btn);
        return btn;
    }

    // --- HÀM TIỆN ÍCH ---
    private void setKhoa(boolean a) {
        txtMaLoai.setEnabled(!a);
        txtTenLoai.setEnabled(!a);
        btnLuu.setEnabled(!a);
        btnKluu.setEnabled(!a);
        btnThem.setEnabled(a);
        btnSua.setEnabled(a);
        btnXoa.setEnabled(a);
    }

    private void setNull() {
        txtMaLoai.setText("");
        txtTenLoai.setText("");
        txtTimKiem.setText("");
    }

    private void showData() throws SQLException {
        tableModel.setRowCount(0);
        ResultSet rs = ls.ShowLoaiSP();
        while (rs.next()) {
            tableModel.addRow(new Object[]{rs.getString("MaLoai"), rs.getString("TenLoai")});
        }
    }

    private void showData(String ten) throws SQLException {
        tableModel.setRowCount(0);
        // --- ĐÃ SỬA: Gọi đúng tên hàm ShowSPTheoten (khớp với LoaiSP.java) ---
        ResultSet rs = ls.ShowLoaiTheoten(ten); 
        while (rs.next()) {
            tableModel.addRow(new Object[]{rs.getString("MaLoai"), rs.getString("TenLoai")});
        }
    }
}