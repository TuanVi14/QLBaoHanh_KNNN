package com.quanlybaohanh.dao;

import com.quanlybaohanh.dto.LichSuXuLy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LichSuXuLyDAO {

    // 1. Thêm một dòng lịch sử xử lý
    public boolean themLichSu(LichSuXuLy ls) {
        Connection conn = KetNoiCSDL.getConnection();
        String sql = "INSERT INTO lichsuxuly (MaPhieu, MaNhanVien, NoiDungXuLy, LinhKienThayThe, NgayHoanThanh) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ls.getMaPhieu());
            ps.setInt(2, ls.getMaNhanVien());
            ps.setString(3, ls.getNoiDungXuLy());
            ps.setString(4, ls.getLinhKienThayThe());
            ps.setDate(5, ls.getNgayHoanThanh());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            KetNoiCSDL.closeConnection(conn);
        }
        return false;
    }

    // 2. Lấy danh sách lịch sử của 1 phiếu cụ thể
    public List<LichSuXuLy> layLichSuTheoPhieu(int maPhieuInput) {
        List<LichSuXuLy> list = new ArrayList<>();
        Connection conn = KetNoiCSDL.getConnection();
        String sql = "SELECT * FROM lichsuxuly WHERE MaPhieu = ? ORDER BY MaXuLy ASC";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maPhieuInput);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                LichSuXuLy ls = new LichSuXuLy();
                ls.setMaXuLy(rs.getInt("MaXuLy"));
                ls.setMaPhieu(rs.getInt("MaPhieu"));
                ls.setMaNhanVien(rs.getInt("MaNhanVien"));
                ls.setNoiDungXuLy(rs.getString("NoiDungXuLy"));
                ls.setLinhKienThayThe(rs.getString("LinhKienThayThe"));
                ls.setNgayHoanThanh(rs.getDate("NgayHoanThanh"));
                list.add(ls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            KetNoiCSDL.closeConnection(conn);
        }
        return list;
    }
}