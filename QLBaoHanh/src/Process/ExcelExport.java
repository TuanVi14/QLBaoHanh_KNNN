package Process;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class xuất Excel đơn giản - phù hợp với cấu trúc project
 */
public class ExcelExport {
    
    private Connection conn;
    
    public ExcelExport(Connection connection) {
        this.conn = connection;
    }
    
    /**
     * Xuất tất cả dữ liệu ra Excel
     */
    public void xuatExcel(JFrame parentFrame) {
        // Chọn nơi lưu file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu báo cáo Excel");
        
        String fileName = "BaoCao_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()) + ".xlsx";
        fileChooser.setSelectedFile(new java.io.File(fileName));
        
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            
            if (!fileToSave.getName().endsWith(".xlsx")) {
                fileToSave = new java.io.File(fileToSave.getAbsolutePath() + ".xlsx");
            }
            
            try {
                Workbook workbook = new XSSFWorkbook();
                
                // Tạo các sheet
                taoSheetPhieuBaoHanh(workbook);
                taoSheetKhachHang(workbook);
                taoSheetSanPham(workbook);
                taoSheetLichSuXuLy(workbook);
                
                // Lưu file
                FileOutputStream outputStream = new FileOutputStream(fileToSave);
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();
                
                // Thông báo thành công
                int choice = JOptionPane.showConfirmDialog(parentFrame,
                    "Xuất Excel thành công!\nBạn có muốn mở file không?",
                    "Thành công",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
                
                if (choice == JOptionPane.YES_OPTION) {
                    java.awt.Desktop.getDesktop().open(fileToSave);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(parentFrame,
                    "Lỗi khi xuất Excel: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Tạo sheet Phiếu Bảo Hành
     */
    private void taoSheetPhieuBaoHanh(Workbook workbook) throws SQLException {
        Sheet sheet = workbook.createSheet("Phiếu Bảo Hành");
        
        // Header
        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT", "Mã Phiếu", "Serial Number", "Tên Sản Phẩm", 
                           "Khách Hàng", "SĐT", "Ngày Tiếp Nhận", "Lỗi", "Trạng Thái"};
        
        CellStyle headerStyle = taoStyleHeader(workbook);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Dữ liệu
        String sql = "SELECT p.MaPhieu, s.SerialNumber, m.TenSanPham, " +
                     "k.TenKhachHang, k.SoDienThoai, p.NgayTiepNhan, p.LoiBaoCao, p.TrangThai " +
                     "FROM phieubaohanh p " +
                     "JOIN sanphamdaban s ON p.MaSPDaBan = s.MaSPDaBan " +
                     "JOIN sanphammodel m ON s.MaModel = m.MaModel " +
                     "JOIN hoadon h ON s.MaHoaDon = h.MaHoaDon " +
                     "JOIN khachhang k ON h.MaKhachHang = k.MaKhachHang " +
                     "ORDER BY p.NgayTiepNhan DESC";
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        int rowNum = 1;
        int stt = 1;
        
        while (rs.next()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue(rs.getInt("MaPhieu"));
            row.createCell(2).setCellValue(rs.getString("SerialNumber"));
            row.createCell(3).setCellValue(rs.getString("TenSanPham"));
            row.createCell(4).setCellValue(rs.getString("TenKhachHang"));
            row.createCell(5).setCellValue(rs.getString("SoDienThoai"));
            row.createCell(6).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("NgayTiepNhan")));
            row.createCell(7).setCellValue(rs.getString("LoiBaoCao"));
            row.createCell(8).setCellValue(rs.getString("TrangThai"));
        }
        
        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        rs.close();
        stmt.close();
    }
    
    /**
     * Tạo sheet Khách Hàng
     */
    private void taoSheetKhachHang(Workbook workbook) throws SQLException {
        Sheet sheet = workbook.createSheet("Khách Hàng");
        
        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT", "Mã KH", "Tên Khách Hàng", "Số Điện Thoại", "Địa Chỉ"};
        
        CellStyle headerStyle = taoStyleHeader(workbook);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        String sql = "SELECT * FROM khachhang ORDER BY MaKhachHang";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        int rowNum = 1;
        int stt = 1;
        
        while (rs.next()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue(rs.getInt("MaKhachHang"));
            row.createCell(2).setCellValue(rs.getString("TenKhachHang"));
            row.createCell(3).setCellValue(rs.getString("SoDienThoai"));
            row.createCell(4).setCellValue(rs.getString("DiaChi"));
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        rs.close();
        stmt.close();
    }
    
    /**
     * Tạo sheet Sản Phẩm
     */
    private void taoSheetSanPham(Workbook workbook) throws SQLException {
        Sheet sheet = workbook.createSheet("Sản Phẩm");
        
        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT", "Mã Model", "Tên Sản Phẩm", "Cấu Hình", 
                           "Bảo Hành (tháng)", "Loại"};
        
        CellStyle headerStyle = taoStyleHeader(workbook);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        String sql = "SELECT m.*, l.TenLoai " +
                     "FROM sanphammodel m " +
                     "JOIN loaisanpham l ON m.MaLoai = l.MaLoai " +
                     "ORDER BY m.MaModel";
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        int rowNum = 1;
        int stt = 1;
        
        while (rs.next()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue(rs.getInt("MaModel"));
            row.createCell(2).setCellValue(rs.getString("TenSanPham"));
            row.createCell(3).setCellValue(rs.getString("CauHinh"));
            row.createCell(4).setCellValue(rs.getInt("ThoiHanBaoHanh"));
            row.createCell(5).setCellValue(rs.getString("TenLoai"));
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        rs.close();
        stmt.close();
    }
    
    /**
     * Tạo sheet Lịch Sử Xử Lý
     */
    private void taoSheetLichSuXuLy(Workbook workbook) throws SQLException {
        Sheet sheet = workbook.createSheet("Lịch Sử Xử Lý");
        
        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT", "Mã Phiếu", "Nhân Viên", "Nội Dung", 
                           "Linh Kiện Thay Thế", "Ngày Hoàn Thành"};
        
        CellStyle headerStyle = taoStyleHeader(workbook);
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        String sql = "SELECT l.*, n.TenNhanVien " +
                     "FROM lichsuxuly l " +
                     "JOIN nhanvien n ON l.MaNhanVien = n.MaNhanVien " +
                     "ORDER BY l.NgayHoanThanh DESC";
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        int rowNum = 1;
        int stt = 1;
        
        while (rs.next()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt++);
            row.createCell(1).setCellValue(rs.getInt("MaPhieu"));
            row.createCell(2).setCellValue(rs.getString("TenNhanVien"));
            row.createCell(3).setCellValue(rs.getString("NoiDungXuLy"));
            row.createCell(4).setCellValue(rs.getString("LinhKienThayThe"));
            
            Date ngay = rs.getDate("NgayHoanThanh");
            if (ngay != null) {
                row.createCell(5).setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(ngay));
            }
        }
        
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        rs.close();
        stmt.close();
    }
    
    /**
     * Tạo style cho header
     */
    private CellStyle taoStyleHeader(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        return style;
    }
}