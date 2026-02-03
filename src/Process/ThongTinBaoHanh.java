package Process;

import java.sql.Date;

public class ThongTinBaoHanh {
    private int maSPDaBan;        // Để dùng khi tạo phiếu bảo hành
    private String serialNumber;
    private String tenSanPham;    // Từ bảng SanPhamModel
    private String tenKhachHang;  // Từ bảng KhachHang
    private String soDienThoai;   // Từ bảng KhachHang
    private Date ngayMua;         // Từ bảng HoaDon (NgayLap)
    private int thoiHanBaoHanh;   // Số tháng bảo hành (Từ SanPhamModel)

    public ThongTinBaoHanh() {
    }

    public ThongTinBaoHanh(int maSPDaBan, String serialNumber, String tenSanPham, String tenKhachHang, String soDienThoai, Date ngayMua, int thoiHanBaoHanh) {
        this.maSPDaBan = maSPDaBan;
        this.serialNumber = serialNumber;
        this.tenSanPham = tenSanPham;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.ngayMua = ngayMua;
        this.thoiHanBaoHanh = thoiHanBaoHanh;
    }

    // Getters và Setters
    public int getMaSPDaBan() { return maSPDaBan; }
    public void setMaSPDaBan(int maSPDaBan) { this.maSPDaBan = maSPDaBan; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public Date getNgayMua() { return ngayMua; }
    public void setNgayMua(Date ngayMua) { this.ngayMua = ngayMua; }

    public int getThoiHanBaoHanh() { return thoiHanBaoHanh; }
    public void setThoiHanBaoHanh(int thoiHanBaoHanh) { this.thoiHanBaoHanh = thoiHanBaoHanh; }
}