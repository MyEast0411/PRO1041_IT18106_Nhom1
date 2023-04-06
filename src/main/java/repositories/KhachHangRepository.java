/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModel.HoaDon;
import domainModel.HoaDonChiTiet;
import domainModel.KhachHang;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import utilities.HibernateUtil;
import viewmodel.HoaDonViewModel;
import viewmodel.SPHDViewModel;

/**
 *
 * @author SoiDiCode
 */
public class KhachHangRepository {
    public Boolean insert(KhachHang kh) {
        try ( Session ss = HibernateUtil.getSession();) {
            ss.getTransaction().begin();
            ss.save(kh);
            ss.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean insertAll(List<KhachHang> List) {
        try ( Session ss = HibernateUtil.getSession();) {
            for (KhachHang s : List) {
                ss.getTransaction().begin();
                ss.save(s);
                ss.getTransaction().commit();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Boolean update(String ma, KhachHang kh) {
        String hql = " Update KhachHang "
                + "set hoTen = :hoTen ,"
                + "diaChi = :diaChi ,"
                + "gioiTinh = : gioiTinh "
                + "where ma = :ma";
        try ( Session ss = HibernateUtil.getSession();) {
           


            ss.getTransaction().begin();
            Query query = ss.createQuery(hql);
            query.setParameter("hoTen", kh.getHoTen());
            query.setParameter("diaChi", kh.getDiaChi());
            query.setParameter("gioiTinh", kh.getGioiTinh());
            query.setParameter("ma", ma);
            
            int result = query.executeUpdate();

            ss.getTransaction().commit();

            return result >0 ;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("kh ");
        return false;
    }

    public Boolean deleteByMa(String ma) {
        String hql = "Delete from KhachHang "
                + "where ma = :ma";
        try ( Session ss = HibernateUtil.getSession();) {
            ss.getTransaction().begin();
           Query query = ss.createQuery(hql).setParameter("ma", ma);
           int kq = query.executeUpdate();
            ss.getTransaction().commit();
            return kq > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public KhachHang getKHByMa(String ma) {
        String hql = "select kh from KhachHang kh where kh.ma = :ma";
        try ( Session ss = HibernateUtil.getSession();) {
            Query qq = ss.createQuery(hql).setParameter("ma", ma);
            KhachHang hang = (KhachHang) qq.getSingleResult();
            return hang;
        } catch (Exception e) {
        }
        return null;
    }

    public List<KhachHang> getAll() {
        Session ss = HibernateUtil.getSession();
        TypedQuery<KhachHang> query = ss.createQuery("FROM KhachHang");
        List<KhachHang> List = query.getResultList();
        return List.isEmpty() ? new ArrayList<>() : List;
    }
     public String renderMa(){
        Date date = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
         String time = sdf.format(date);
        return "KH"+date.getHours()+date.getMinutes()+date.getSeconds()+time;
    }
     public KhachHang searchBySDT(String sdt){
         try {
             Session ss = HibernateUtil.getSession();
             TypedQuery<KhachHang> query = ss.createQuery("FROM KhachHang kh where kh.sdt like :sdt");
             query.setParameter("sdt", sdt);
             return query.getSingleResult();
         } catch (Exception e) {
             return null;
         }
        
     }
	 public List<HoaDonViewModel> getHDByMaKh(String ma){
//              maHD,
//                maNV,
//                hoTen,
//                ngayThanhToan,
//               hinhThucThanhToan,
//                tongTien,
//                tienMat
	  String hql = "Select new viewmodel.HoaDonViewModel( hd.ma , hd.nhanVien.ma , hd.nhanVien.hoTen , hd.ngayThanhToan,"
                  + "hd.hinhThucThanhToan , hd.tongTien , hd.tienMat)"
                  + " from HoaDon hd "
                + "where hd.khachHang.ma = :ma";
           Session ss = HibernateUtil.getSession();
            ss.getTransaction().begin();
           TypedQuery<HoaDonViewModel> query = ss.createQuery(hql).setParameter("ma", ma);
           List<HoaDonViewModel> list = query.getResultList();	 
          ss.close();
	  return list.isEmpty()?new ArrayList<>():list;	
      }
         
         public List<SPHDViewModel> getHDCTByMaHD(String maHD){
              String hql = "Select new viewmodel.SPHDViewModel( "
                      + "hd.IdChiTietSanPham.bia.ma ,"
                      + " hd.IdChiTietSanPham.bia.ten , "
                      + "hd.donGia , "
                      + "hd.soLuong,"
                      + "hd.IdChiTietSanPham.theTich,"
                      + "hd.IdChiTietSanPham.loaiSP.ten, "
                      + " hd.IdChiTietSanPham.nsx.ten)"
                  + " from HoaDonChiTiet hd "
                + "where hd.IdHoaDon.ma = :ma";
           Session ss = HibernateUtil.getSession();
            ss.getTransaction().begin();
           TypedQuery<SPHDViewModel> query = ss.createQuery(hql).setParameter("ma", maHD);
           List<SPHDViewModel> list = query.getResultList();	 
          ss.close();
	  return list.isEmpty()?new ArrayList<>():list;	
         }
         public static void main(String[] args) {
             KhachHangRepository khr = new KhachHangRepository();
             //HD001
//             String ma = "maHD ,maNV,Ten NV , ngayThanhToan=2023-03-12 00:00:00.0, hinhThucThanhToan=1, tongTien=20000.0000, tienMat=20000.0000, tienChuyenKhoan=0.0000)";
             khr.getHDCTByMaHD("HD001").forEach(s -> System.out.println(s.toString()));
    }
     
}
