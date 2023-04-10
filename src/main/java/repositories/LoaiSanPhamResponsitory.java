/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.sql.*;
import java.util.ArrayList;
import model.LoaiSanPham_;

import utilities.JDBCUtils;

/**
 *
 * @author Admin
 */
public class LoaiSanPhamResponsitory {

    public ArrayList<LoaiSanPham_> getAllLoaiSP() {
        String sql = "select * from loai_san_pham";
        ArrayList<LoaiSanPham_> listLSP = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LoaiSanPham_ l = new LoaiSanPham_();
                l.setId(rs.getString(1));
                l.setMa(rs.getString(2));
                l.setTen(rs.getString(3));
                listLSP.add(l);
            }
            c.close();
            ps.close();
            rs.close();
            return listLSP;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<LoaiSanPham_> getLoaiSP(String text) {
        String sql = "select * from loai_san_pham where ten like ?";
        ArrayList<LoaiSanPham_> listLSP = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%"+text+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LoaiSanPham_ l = new LoaiSanPham_();
                l.setId(rs.getString(1));
                l.setMa(rs.getString(2));
                l.setTen(rs.getString(3));
                listLSP.add(l);
            }
            c.close();
            ps.close();
            rs.close();
            return listLSP;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean addLSP(LoaiSanPham_ lsp) {
        String sql = "insert into loai_san_pham(id,ma,ten) values(newID(),?,?)";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, lsp.getMa());
            ps.setString(2, lsp.getTen());

            int kq = ps.executeUpdate();

            c.close();
            ps.close();
            return kq > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean updateLSP(LoaiSanPham_ lsp) {
        String sql = "update loai_san_pham set ten =? where ma=?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, lsp.getTen());
            ps.setString(2, lsp.getMa());

            int kq = ps.executeUpdate();

            c.close();
            ps.close();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean deleteLSP(String ma) {
        String sql = "delete  loai_san_pham   where ma=?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, ma);

            int kq = ps.executeUpdate();

            c.close();
            ps.close();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<LoaiSanPham_> TimKiemTheoTenSP(String tenSP) {
        String sql = "select ma, ten from loai_san_pham where ten like ?";
        ArrayList<LoaiSanPham_> list = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + tenSP + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LoaiSanPham_ s = new LoaiSanPham_();
                s.setMa(rs.getString(1));
                s.setTen(rs.getString(2));

                list.add(s);
            }
            c.close();
            ps.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        LoaiSanPhamResponsitory lres = new LoaiSanPhamResponsitory();
        System.out.println(lres.getAllLoaiSP());
        LoaiSanPham_ l1 = new LoaiSanPham_();
        l1.setMa("M1");
        l1.setTen("Bia Ha Noi");
//        System.out.println(lres.addLSP(l1));
//System.out.println(lres.updateLSP(l1));
        System.out.println(lres.deleteLSP("M1"));
    }

}
