/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.sql.*;
import java.util.ArrayList;

import model.NSX_;
import utilities.JDBCUtils;

/**
 *
 * @author Admin
 */
public class NSXResponsitory {

    public ArrayList<NSX_> getAllNSX() {
        String sql = "select * from nsx";
        ArrayList<NSX_> listNSX = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NSX_ n = new NSX_();
                n.setId(rs.getString(1));
                n.setMa(rs.getString(2));
                n.setTen(rs.getString(3));
                listNSX.add(n);
            }
            c.close();
            ps.close();
            rs.close();
            return listNSX;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean addNSX(NSX_ nsx) {
        String sql = "insert into nsx(id,ma,ten) values(newID(),?,?)";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, nsx.getMa());
            ps.setString(2, nsx.getTen());

            int kq = ps.executeUpdate();

            c.close();
            ps.close();
            return kq > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean updateNSX(NSX_ nsx) {
        String sql = "update nsx set ten =? where ma=?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, nsx.getTen());
            ps.setString(2, nsx.getMa());

            int kq = ps.executeUpdate();

            c.close();
            ps.close();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean deleteNSX(String ma) {
        String sql = "delete  nsx  where ma=?";
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

    public ArrayList<NSX_> TimKiemTheoTenSP(String tenSP) {
        String sql = "select ma, ten from nsx where ten like ?";
        ArrayList<NSX_> list = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + tenSP + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NSX_ s = new NSX_();
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
        NSXResponsitory nsxres = new NSXResponsitory();
        System.out.println(nsxres.getAllNSX());
        NSX_ nsx = new NSX_();
        nsx.setMa("NSX1");
        nsx.setTen("Nha may 0");
        // System.out.println(nsxres.addNSX(nsx));
        // System.out.println(nsxres.updateNSX(nsx));
        // System.out.println(nsxres.deleteNSX("NSX1"));
    }
}
