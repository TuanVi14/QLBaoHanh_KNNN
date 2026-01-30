package com.quanlybaohanh.bus;

import com.quanlybaohanh.dao.PhieuBaoHanhDAO;
import java.util.List;

public class ThongKeBUS {
    
    private PhieuBaoHanhDAO phieuDAO = new PhieuBaoHanhDAO();

    // Đếm số lượng máy đang sửa (Trạng thái != 'Hoàn thành')
    public int demMayDangSua() {
        List<Object[]> list = phieuDAO.layDanhSachPhieuDayDu();
        int count = 0;
        for (Object[] row : list) {
            String trangThai = (String) row[5]; // Cột thứ 6 là trạng thái
            if (!trangThai.equalsIgnoreCase("Hoàn thành")) {
                count++;
            }
        }
        return count;
    }

    // Đếm số lượng máy đã xong
    public int demMayDaXong() {
        List<Object[]> list = phieuDAO.layDanhSachPhieuDayDu();
        int count = 0;
        for (Object[] row : list) {
            String trangThai = (String) row[5];
            if (trangThai.equalsIgnoreCase("Hoàn thành")) {
                count++;
            }
        }
        return count;
    }
}