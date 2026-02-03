package Interface.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

// Import class xử lý nghiệp vụ
import Process.VaiTro;

public class dlgQuanLyVaiTro extends JDialog {

    private static final long serialVersionUID = 1L;
    
    // Khai báo các component
    private JTextField txtTimKiem;
    private JTextField txtMaVaiTro;
    private JTextField txtTenVaiTro;
    private JTable table;
    private DefaultTableModel tableModel;
    
    // Các nút bấm
    private JButton btnThem, btnSua, btnXoa, btnLuu, btnKluu, btnTimKiem, btnLamMoi;
    
    // Biến xử lý logic
    private final VaiTro vt = new VaiTro();
    private boolean cothem = true; // true = đang thêm, false = đang sửa

    /**
     * Constructor nhận vào JFrame cha để làm Owner
     */
    public dlgQuanLyVaiTro(JFrame parent) {
        super(parent, "Quản Lý Vai Trò (Chức vụ)", true); // Modal = true: Chặn cửa sổ cha
        setSize(600, 450);
        setLocationRelativeTo(null); // Ra giữa màn hình
        setLayout(null);
        setResizable(false);

        initComponents();
        
        // Khởi tạo trạng thái ban đầu
        try {
            setNull();
            setKhoa(true);
            showData(); // Tải dữ liệu lên bảng
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        // --- PHẦN TÌM KIẾM ---
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

        // --- PHẦN NHẬP LIỆU ---
        JLabel lblMa = new JLabel("Mã Vai Trò:");
        lblMa.setBounds(30, 65, 100, 25);
        add(lblMa);

        txtMaVaiTro = new JTextField();
        txtMaVaiTro.setBounds(130, 65, 120, 25);
        add(txtMaVaiTro);

        JLabel lblTen = new JLabel("Tên Vai Trò:");
        lblTen.setBounds(270, 65, 80, 25);
        add(lblTen);

        txtTenVaiTro = new JTextField();
        txtTenVaiTro.setBounds(350, 65, 200, 25);
        add(txtTenVaiTro);

        // --- CÁC NÚT CHỨC NĂNG ---
        btnThem = createButton("Thêm", 30, 110);
        btnSua = createButton("Sửa", 120, 110);
        btnXoa = createButton("Xóa", 210, 110);
        btnLuu = createButton("Lưu", 300, 110);
        btnKluu = createButton("K.Lưu", 390, 110); // Không lưu (Hủy)
        
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

        // --- BẢNG DỮ LIỆU ---
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 160, 520, 230);
        add(scrollPane);

        table = new JTable();
        tableModel = new DefaultTableModel(new String[]{"Mã Vai Trò", "Tên Vai Trò"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };
        table.setModel(tableModel);
        scrollPane.setViewportView(table);

        // Sự kiện click bảng
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // Đổ dữ liệu từ bảng ngược lại TextFields
                    txtMaVaiTro.setText(table.getValueAt(row, 0).toString());
                    txtTenVaiTro.setText(table.getValueAt(row, 1).toString());
                }
            }
        });

        // --- GÁN SỰ KIỆN CHO CÁC NÚT ---
        btnThem.addActionListener(e -> {
            setKhoa(false); // Mở khóa nhập liệu
            setNull();      // Xóa trắng
            cothem = true;  // Đánh dấu là đang thêm
            txtMaVaiTro.requestFocus();
        });

        btnSua.addActionListener(e -> {
            if (txtMaVaiTro.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!");
            } else {
                setKhoa(false);
                cothem = false; // Đánh dấu là đang sửa
                txtMaVaiTro.setEnabled(false); // Mã không được sửa
                txtTenVaiTro.requestFocus();
            }
        });

        btnXoa.addActionListener(e -> {
            String ma = txtMaVaiTro.getText();
            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa vai trò này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    vt.DeleteVaiTro(ma);
                    showData();
                    setNull();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa: Có thể vai trò này đang được sử dụng bởi nhân viên!");
                }
            }
        });

        btnLuu.addActionListener(e -> {
            try {
                // Validate dữ liệu
                if (txtMaVaiTro.getText().isEmpty() || txtTenVaiTro.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã và Tên!");
                    return;
                }

                int ma = Integer.parseInt(txtMaVaiTro.getText());
                String ten = txtTenVaiTro.getText();

                if (cothem) {
                    vt.InsertVaiTro(ma, ten);
                } else {
                    vt.EditVaiTro(ma, ten);
                }
                
                showData(); // Tải lại bảng
                setNull();
                setKhoa(true); // Khóa lại
                
                JOptionPane.showMessageDialog(this, "Thực hiện thành công!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mã vai trò phải là số!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi CSDL: " + ex.getMessage());
            }
        });

        btnKluu.addActionListener(e -> {
            setNull();
            setKhoa(true);
        });
    }

    // Hàm tạo nút nhanh
    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 80, 25);
        btn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        add(btn);
        return btn;
    }

    /* ===== CÁC HÀM TIỆN ÍCH ===== */
    
    // Khóa/Mở khóa các nút điều khiển
    private void setKhoa(boolean a) {
        // Khi a = true (Khóa nhập liệu) -> Chỉ cho bấm Thêm/Sửa/Xóa
        txtMaVaiTro.setEnabled(!a);
        txtTenVaiTro.setEnabled(!a);
        
        btnLuu.setEnabled(!a);
        btnKluu.setEnabled(!a);
        
        btnThem.setEnabled(a);
        btnSua.setEnabled(a);
        btnXoa.setEnabled(a);
    }

    // Xóa trắng ô nhập
    private void setNull() {
        txtMaVaiTro.setText("");
        txtTenVaiTro.setText("");
        txtTimKiem.setText("");
    }

    // Hiển thị tất cả dữ liệu
    private void showData() throws SQLException {
        tableModel.setRowCount(0); // Xóa bảng cũ
        ResultSet rs = vt.ShowVaiTro();
        while (rs.next()) {
            tableModel.addRow(new Object[]{
                rs.getString("MaVaiTro"), 
                rs.getString("TenVaiTro")
            });
        }
    }

    // Hiển thị dữ liệu theo tìm kiếm
    private void showData(String ten) throws SQLException {
        tableModel.setRowCount(0);
        ResultSet rs = vt.ShowVTTheoten(ten);
        while (rs.next()) {
            tableModel.addRow(new Object[]{
                rs.getString("MaVaiTro"), 
                rs.getString("TenVaiTro")
            });
        }
    }
}