package Process;

import java.sql.Date;

public class PhieuBaoHanh {
    private int maPhieu;
    private int maSPDaBan;      // Khóa ngoại trỏ đến sản phẩm cụ thể
    private Date ngayTiepNhan;
    private String loiBaoCao;
    private String trangThai;   // Ví dụ: "Đang sửa", "Hoàn thành"

    // Constructor không tham số
    public PhieuBaoHanh() {
    }

    // Constructor đầy đủ tham số
    public PhieuBaoHanh(int maPhieu, int maSPDaBan, Date ngayTiepNhan, String loiBaoCao, String trangThai) {
        this.maPhieu = maPhieu;
        this.maSPDaBan = maSPDaBan;
        this.ngayTiepNhan = ngayTiepNhan;
        this.loiBaoCao = loiBaoCao;
        this.trangThai = trangThai;
    }

    // Getters và Setters
    public int getMaPhieu() { return maPhieu; }
    public void setMaPhieu(int maPhieu) { this.maPhieu = maPhieu; }

    public int getMaSPDaBan() { return maSPDaBan; }
    public void setMaSPDaBan(int maSPDaBan) { this.maSPDaBan = maSPDaBan; }

    public Date getNgayTiepNhan() { return ngayTiepNhan; }
    public void setNgayTiepNhan(Date ngayTiepNhan) { this.ngayTiepNhan = ngayTiepNhan; }

    public String getLoiBaoCao() { return loiBaoCao; }
    public void setLoiBaoCao(String loiBaoCao) { this.loiBaoCao = loiBaoCao; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}