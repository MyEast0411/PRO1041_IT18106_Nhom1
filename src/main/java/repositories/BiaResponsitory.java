/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.util.ArrayList;
import java.sql.*;
import model.Bia_;
import utilities.JDBCUtils;

/**
 *
 * @author Admin
 */
public class BiaResponsitory {

    public ArrayList<Bia_> getAllBia() {
        String sql = "select * from Bia";
        ArrayList<Bia_> listBia = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Bia_ bia = new Bia_();
                bia.setId(rs.getString(1));
                bia.setMa(rs.getString(2));
                bia.setTen(rs.getString(3));
                listBia.add(bia);
            }
            c.close();
            ps.close();
            rs.close();
            return listBia;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean addBia(Bia_ b) {
        String sql = "insert into bia(id,ma,ten) values(newID(),?,?)";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, b.getMa());
            ps.setString(2, b.getTen());

            int kq = ps.executeUpdate();

            c.close();
            ps.close();
            return kq > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean updateBia(Bia_ b) {
        String sql = "update BIA set ten =? where ma=?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, b.getTen());
            ps.setString(2, b.getMa());

            int kq = ps.executeUpdate();

            c.close();
            ps.close();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
        public Boolean deleteBia(String ma) {
        String sql = "delete bia where ma=?";
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
        
        public ArrayList<Bia_> TimKiemTheoTenSP(String tenSP) {
        String sql = "select bia.ma, bia.ten from bia where bia.ten like ?";
        ArrayList<Bia_> list = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
              ps.setString(1, "%" + tenSP + "%");
            ResultSet rs = ps.executeQuery();
            

            while (rs.next()) {
                Bia_ s = new Bia_();
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
        BiaResponsitory biarp = new BiaResponsitory();
        System.out.println(biarp.getAllBia());
//        Bia_ b = new Bia_();
//        b.setMa("B1");
//        b.setTen("Bia HNoi");
//       System.out.println(biarp.addBia(b));
//      
//        System.out.println(biarp.updateBia(b));
//System.out.println(biarp.deleteBia("M1"));
    }
}
