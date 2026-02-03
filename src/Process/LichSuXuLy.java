package Process;

import java.sql.Date;

public class LichSuXuLy {
    private int maXuLy;
    private int maPhieu;
    private int maNhanVien;
    private String noiDungXuLy;
    private String linhKienThayThe;
    private Date ngayHoanThanh;

    public LichSuXuLy() {
    }

    public LichSuXuLy(int maXuLy, int maPhieu, int maNhanVien, String noiDungXuLy, String linhKienThayThe, Date ngayHoanThanh) {
        this.maXuLy = maXuLy;
        this.maPhieu = maPhieu;
        this.maNhanVien = maNhanVien;
        this.noiDungXuLy = noiDungXuLy;
        this.linhKienThayThe = linhKienThayThe;
        this.ngayHoanThanh = ngayHoanThanh;
    }

    // Getters và Setters
    public int getMaXuLy() { return maXuLy; }
    public void setMaXuLy(int maXuLy) { this.maXuLy = maXuLy; }

    public int getMaPhieu() { return maPhieu; }
    public void setMaPhieu(int maPhieu) { this.maPhieu = maPhieu; }

    public int getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(int maNhanVien) { this.maNhanVien = maNhanVien; }

    public String getNoiDungXuLy() { return noiDungXuLy; }
    public void setNoiDungXuLy(String noiDungXuLy) { this.noiDungXuLy = noiDungXuLy; }

    public String getLinhKienThayThe() { return linhKienThayThe; }
    public void setLinhKienThayThe(String linhKienThayThe) { this.linhKienThayThe = linhKienThayThe; }

    public Date getNgayHoanThanh() { return ngayHoanThanh; }
    public void setNgayHoanThanh(Date ngayHoanThanh) { this.ngayHoanThanh = ngayHoanThanh; }
}