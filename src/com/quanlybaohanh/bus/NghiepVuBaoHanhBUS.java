package com.quanlybaohanh.bus;

import com.quanlybaohanh.dao.BaoHanhDAO;
import com.quanlybaohanh.dao.LichSuXuLyDAO;
import com.quanlybaohanh.dao.PhieuBaoHanhDAO;
import com.quanlybaohanh.dto.LichSuXuLy;
import com.quanlybaohanh.dto.PhieuBaoHanh;
import com.quanlybaohanh.dto.ThongTinBaoHanh;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class NghiepVuBaoHanhBUS {

    private BaoHanhDAO baoHanhDAO = new BaoHanhDAO();
    private PhieuBaoHanhDAO phieuDAO = new PhieuBaoHanhDAO();
    private LichSuXuLyDAO lichSuDAO = new LichSuXuLyDAO();

    // =========================================================
    // 1. LOGIC KIỂM TRA BẢO HÀNH (AUTO CHECK)
    // =========================================================
    
    /**
     * Hàm này nhận vào Serial, gọi DAO lấy thông tin, 
     * sau đó tự tính toán xem còn hạn hay không.
     */
    public String kiemTraDieuKienBaoHanh(String serialInput) {
        // Bước 1: Lấy dữ liệu từ DAO
        ThongTinBaoHanh info = baoHanhDAO.traCuuBaoHanh(serialInput);

        if (info == null) {
            return "KhongTimThay"; // Mã lỗi để GUI xử lý (hiện Popup báo lỗi)
        }

        // Bước 2: Tính toán ngày
        // Chuyển đổi java.sql.Date sang java.time.LocalDate để dễ cộng trừ
        LocalDate ngayMua = info.getNgayMua().toLocalDate();
        int soThangBaoHanh = info.getThoiHanBaoHanh();

        // Cộng số tháng bảo hành vào ngày mua để ra ngày hết hạn
        LocalDate ngayHetHan = ngayMua.plusMonths(soThangBaoHanh);
        LocalDate homNay = LocalDate.now();

        // Bước 3: So sánh
        if (homNay.isBefore(ngayHetHan) || homNay.isEqual(ngayHetHan)) {
            // Còn hạn
            return "ConBaoHanh"; 
        } else {
            // Đã hết hạn
            return "HetBaoHanh";
        }
    }

    // Hàm phụ trợ để lấy thông tin chi tiết hiển thị lên GUI
    public ThongTinBaoHanh layThongTinMay(String serialInput) {
        return baoHanhDAO.traCuuBaoHanh(serialInput);
    }
    
    // =========================================================
    // 2. LOGIC QUẢN LÝ PHIẾU VÀ TIẾN ĐỘ
    // =========================================================

    public boolean taoPhieuMoi(PhieuBaoHanh pbh) {
        // Có thể thêm logic kiểm tra dữ liệu đầu vào ở đây
        if (pbh.getLoiBaoCao() == null || pbh.getLoiBaoCao().isEmpty()) {
            return false; // Bắt buộc phải có mô tả lỗi
        }
        return phieuDAO.taoPhieuMoi(pbh);
    }

    public List<Object[]> layDanhSachPhieuChoTable() {
        return phieuDAO.layDanhSachPhieuDayDu();
    }

    /**
     * Hàm cập nhật tiến độ: Vừa update trạng thái phiếu, vừa ghi nhật ký
     * Đây là Transaction đơn giản
     */
    public boolean capNhatTienDo(int maPhieu, String trangThaiMoi, LichSuXuLy lichSu) {
        boolean updatePhieu = phieuDAO.capNhatTrangThai(maPhieu, trangThaiMoi);
        boolean insertLichSu = lichSuDAO.themLichSu(lichSu);
        
        return updatePhieu && insertLichSu;
    }
    
    // Lấy lịch sử để hiển thị chi tiết
    public List<LichSuXuLy> xemLichSuSuaChua(int maPhieu) {
        return lichSuDAO.layLichSuTheoPhieu(maPhieu);
    }
}