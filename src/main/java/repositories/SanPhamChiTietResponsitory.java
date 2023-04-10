/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import model.SanPhamChiTiet_;
import utilities.JDBCUtils;
import viewmodel.SPCTBiaNsxLoaiSp;

/**
 *
 * @author Admin
 */
public class SanPhamChiTietResponsitory {

    public ArrayList<SanPhamChiTiet_> getAllSPCT() {
        String sql = "select * from san_pham_chi_tiet";
        ArrayList<SanPhamChiTiet_> listSPCT = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPhamChiTiet_ spct = new SanPhamChiTiet_();
                spct.setId(rs.getString(1));
                spct.setMaSPCT(rs.getString(2));

                spct.setGiaBan(rs.getBigDecimal(3));
                spct.setGiaNhap(rs.getBigDecimal(4));
                spct.setHanSD(rs.getString(5));
                spct.setNgaySX(rs.getDate(6));
                spct.setNongDoCon(rs.getString(7));
                spct.setSoLuongLonBia(rs.getInt(8));
                spct.setSoLuongTon(rs.getInt(9));
                spct.setThanhPhan(rs.getString(10));
                spct.setTheTich(rs.getString(11));
                spct.setTinhTrang(rs.getInt(12));
                spct.setTrangThai(rs.getInt(13));
                spct.setXuatXu(rs.getString(14));
                spct.setIdBia(rs.getString(15));
                spct.setIdLoaiSP(rs.getString(16));
                spct.setIdNSX(rs.getString(17));

                listSPCT.add(spct);

            }
            c.close();
            ps.close();
            rs.close();
            return listSPCT;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
      public SanPhamChiTiet_ getSPCTByMa(String ma) {
        String sql = "select * from san_pham_chi_tiet where ma = ?";
        ArrayList<SanPhamChiTiet_> listSPCT = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
               SanPhamChiTiet_ spct = new SanPhamChiTiet_();
            while (rs.next()) {
                spct.setId(rs.getString(1));
                spct.setMaSPCT(rs.getString(2));

                spct.setGiaBan(rs.getBigDecimal(3));
                spct.setGiaNhap(rs.getBigDecimal(4));
                spct.setHanSD(rs.getString(5));
                spct.setNgaySX(rs.getDate(6));
                spct.setNongDoCon(rs.getString(7));
                spct.setSoLuongLonBia(rs.getInt(8));
                spct.setSoLuongTon(rs.getInt(9));
                spct.setThanhPhan(rs.getString(10));
                spct.setTheTich(rs.getString(11));
                spct.setTinhTrang(rs.getInt(12));
                spct.setTrangThai(rs.getInt(13));
                spct.setXuatXu(rs.getString(14));
                spct.setIdBia(rs.getString(15));
                spct.setIdLoaiSP(rs.getString(16));
                spct.setIdNSX(rs.getString(17));
            }
            c.close();
            ps.close();
            rs.close();
            return spct;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean addSPChiTiet(SanPhamChiTiet_ spct) {
        String sql = "insert into san_pham_chi_tiet(id,ma,gia_ban,gia_nhap,han_sd,ngay_sx,nong_do_con,so_luong_lon_bia,so_luong_ton,thanh_phan,the_tich,tinh_trang,trang_thai,xuat_xu,id_bia,id_loai_san_pham,id_nsx) "
                + "values(newID(),?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?"
                + ",?,?)";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, spct.getMaSPCT());
            ps.setBigDecimal(2, spct.getGiaBan());
            ps.setBigDecimal(3, spct.getGiaNhap());
            ps.setString(4, spct.getHanSD());
            ps.setObject(5, spct.getNgaySX());
            ps.setString(6, spct.getNongDoCon());
            ps.setInt(7, spct.getSoLuongLonBia());
            ps.setInt(8, spct.getSoLuongTon());
            ps.setString(9, spct.getThanhPhan());
            ps.setString(10, spct.getTheTich());
            ps.setInt(11, spct.getTinhTrang());
            ps.setInt(12, spct.getTrangThai());
            ps.setString(13, spct.getXuatXu());
            ps.setString(14, spct.getIdBia());
            ps.setString(15, spct.getIdLoaiSP());
            ps.setString(16, spct.getIdNSX());

            int kq = ps.executeUpdate();
            c.close();
            ps.close();

            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean deleteSPCT(String id) {
        String sql = "delete san_pham_chi_tiet where id=?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, id);

            int kq = ps.executeUpdate();
            c.close();
            ps.close();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean updateSPCT(SanPhamChiTiet_ spct) {
        String sql = "update san_pham_chi_tiet set ma=?, gia_ban =? , gia_nhap=?,han_sd=?,ngay_sx =?, nong_do_con =?,so_luong_lon_bia=?,so_luong_ton=?, thanh_phan=?, the_tich=?,"
                + " tinh_trang=?, trang_thai=?,xuat_xu =?,id_bia=?,id_loai_san_pham=?,id_nsx=? where id=?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, spct.getMaSPCT());
            ps.setBigDecimal(2, spct.getGiaBan());
            ps.setBigDecimal(3, spct.getGiaNhap());
            ps.setString(4, spct.getHanSD());
            ps.setObject(5, spct.getNgaySX());
            ps.setString(6, spct.getNongDoCon());
            ps.setInt(7, spct.getSoLuongLonBia());
            ps.setInt(8, spct.getSoLuongTon());

            ps.setString(9, spct.getThanhPhan());
            ps.setString(10, spct.getTheTich());
            ps.setInt(11, spct.getTinhTrang());
            ps.setInt(12, spct.getTrangThai());
            ps.setString(13, spct.getXuatXu());
            ps.setString(14, spct.getIdBia());
            ps.setString(15, spct.getIdLoaiSP());
            ps.setString(16, spct.getIdNSX());
            ps.setString(17, spct.getId());

            int kq = ps.executeUpdate();
            c.close();
            ps.close();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Boolean updateSoLuong(SanPhamChiTiet_ spct) {
        String sql = "update san_pham_chi_tiet set so_luong_ton=? where id=?";
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
           
            ps.setInt(1, spct.getSoLuongTon());
            ps.setString(2, spct.getId());

            int kq = ps.executeUpdate();
            c.close();
            ps.close();
            return kq > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
// load table qlsp

    public ArrayList<SPCTBiaNsxLoaiSp> getAllSPCTBiaNsxLoaiSp() {
        String sql = "select san_pham_chi_tiet.ma,bia.ma, bia.ten,loai_san_pham.ten,nsx.ten,the_tich,nong_do_con,trang_thai,so_luong_lon_bia,so_luong_ton,gia_nhap,gia_ban,han_sd, xuat_xu,thanh_phan,tinh_trang from san_pham_chi_tiet "
                + "                 join bia on san_pham_chi_tiet.id_bia=bia.id \n"
                + "                join loai_san_pham on san_pham_chi_tiet.id_loai_san_pham = loai_san_pham.id\n"
                + "                join nsx on san_pham_chi_tiet.id_nsx=nsx.id";
        ArrayList<SPCTBiaNsxLoaiSp> list = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SPCTBiaNsxLoaiSp s = new SPCTBiaNsxLoaiSp();
                s.setMaSPCT(rs.getString(1));
                s.setMaSP(rs.getString(2));
                s.setTenSP(rs.getString(3));
                s.setLoaiSP(rs.getString(4));
                s.setNsx(rs.getString(5));
                s.setTheTich(rs.getString(6));
                s.setNongDoCon(rs.getString(7));
                s.setTrangThai(rs.getInt(8));
                s.setSoLuongLonBia(rs.getInt(9));
                s.setSoLuongLonTon(rs.getInt(10));

                s.setGiaNhap(rs.getBigDecimal(11));
                s.setGiaBan(rs.getBigDecimal(12));
                s.setHanSD(rs.getString(13));
                s.setXuatXu(rs.getString(14));
                s.setThanhPhan(rs.getString(15));
                s.setTinhTrang(rs.getInt(16));

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

    public ArrayList<SPCTBiaNsxLoaiSp> TimKiemSPCTBiaNsxLoaiSp(String tenSP) {
        String sql = "select san_pham_chi_tiet.ma,bia.ma, bia.ten,loai_san_pham.ten,nsx.ten,the_tich,nong_do_con,trang_thai,so_luong_lon_bia,so_luong_ton,gia_nhap,gia_ban,han_sd, xuat_xu,thanh_phan,tinh_trang from san_pham_chi_tiet "
                + "                 join bia on san_pham_chi_tiet.id_bia=bia.id \n"
                + "                join loai_san_pham on san_pham_chi_tiet.id_loai_san_pham = loai_san_pham.id\n"
                + "                join nsx on san_pham_chi_tiet.id_nsx=nsx.id where bia.ten like ?";
        ArrayList<SPCTBiaNsxLoaiSp> list = new ArrayList<>();
        try {
            Connection c = JDBCUtils.getConnection();
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, "%" + tenSP + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SPCTBiaNsxLoaiSp s = new SPCTBiaNsxLoaiSp();
                s.setMaSPCT(rs.getString(1));
                s.setMaSP(rs.getString(2));
                s.setTenSP(rs.getString(3));
                s.setLoaiSP(rs.getString(4));
                s.setNsx(rs.getString(5));
                s.setTheTich(rs.getString(6));
                s.setNongDoCon(rs.getString(7));
                s.setTrangThai(rs.getInt(8));
                s.setSoLuongLonBia(rs.getInt(9));
                s.setSoLuongLonTon(rs.getInt(10));

                s.setGiaNhap(rs.getBigDecimal(11));
                s.setGiaBan(rs.getBigDecimal(12));
                s.setHanSD(rs.getString(13));
                s.setXuatXu(rs.getString(14));
                s.setThanhPhan(rs.getString(15));
                s.setTinhTrang(rs.getInt(16));

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
        SanPhamChiTietResponsitory spctres = new SanPhamChiTietResponsitory();
        for (SanPhamChiTiet_ s : spctres.getAllSPCT()) {
            System.out.println(s.getMaSPCT());
        }
        System.out.println(spctres.getSPCTByMa("SPCT001").toString());
//        SanPhamChiTiet_ spct = new SanPhamChiTiet_(null, new BigDecimal(20), new BigDecimal(15), "2 năm", new Date(), "10%", "3 thùng", "lúa mạch", "330ml", 1, 1, "VN", null, null, null);
//        System.out.println(spctres.addSPChiTiet(spct));
//System.out.println(spctres.deleteSPCT("87C620CC-30E4-451D-A042-057F986E7937"));

//SanPhamChiTiet_ spct = new SanPhamChiTiet_();
//    spct.setHanSD("3 năm");
//    spct.setId("1e4b4c4e-6b11-416e-bef6-743ec6c9ad6f");
//    spct.setMaSPCT("SPCT001");
//        System.out.println(spctres.updateSPCT(spct));
        //System.out.println(spctres.getAllSPCTBiaNsxLoaiSp());
        
       
    }
}
