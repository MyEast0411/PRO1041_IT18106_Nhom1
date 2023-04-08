/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModel.HoaDon;
import domainModel.NhanVien;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import utilities.HibernateUtil;

/**
 *
 * @author dongl
 */
public class HoaDonRepository {

    public List<HoaDon> getList() {
        List<HoaDon> list = new ArrayList<>();
        try ( Session session = HibernateUtil.getSession()) {
            String hql = "FROM HoaDon";
            Query q = session.createQuery(hql);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean insert(HoaDon hd) {
        try ( Session session = HibernateUtil.getSession()) {
            NhanVien nv = session.get(NhanVien.class, hd.getNhanVien().getId());
            hd.setNhanVien(nv);
            session.getTransaction().begin();
            session.save(hd);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean update(HoaDon hd) {
        try ( Session session = HibernateUtil.getSession()) {
            String hql = "SELECT hd FROM HoaDon hd where hd.ma = :ma";
            Query q = session.createQuery(hql);
            q.setParameter("ma", hd.getMa());
            HoaDon hoaDon = (HoaDon) q.getSingleResult();

            hoaDon.setNhanVien(hd.getNhanVien());
            hoaDon.setKhachHang(hd.getKhachHang());
            hoaDon.setNgayThanhToan(hd.getNgayThanhToan());
            hoaDon.setTienChuyenKhoan(hd.getTienChuyenKhoan());
            hoaDon.setTienMat(hd.getTienMat());
            hoaDon.setTongTien(hd.getTongTien());
            hoaDon.setHinhThucThanhToan(hd.getHinhThucThanhToan());
            hoaDon.setTinhTrang(hd.getTinhTrang());

            session.getTransaction().begin();
            session.update(hoaDon);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    

    public HoaDon getHoaDonByMa(String maHD) {
        try ( Session session = HibernateUtil.getSession()) {
            String hql = "SELECT hd FROM HoaDon hd where hd.ma = :ma";
            Query q = session.createQuery(hql);
            q.setParameter("ma", maHD);
            HoaDon hoaDon = (HoaDon) q.getSingleResult();
            return hoaDon;
        } catch (Exception e) {
            return null;
        }
    }

    public List<HoaDon> getListByNgay(Integer ngay) {
        try ( Session session = HibernateUtil.getSession()) {
            List<HoaDon> list = new ArrayList<>();
            String hql = "SELECT hd FROM HoaDon hd where day(hd.ngayThanhToan) = :day";
            Query q = session.createQuery(hql);
            q.setParameter("day", ngay);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<HoaDon> getListByThang(Integer ngay) {
        try ( Session session = HibernateUtil.getSession()) {
            List<HoaDon> list = new ArrayList<>();
            String hql = "SELECT hd FROM HoaDon hd where month(hd.ngayThanhToan) = :day";
            Query q = session.createQuery(hql);
            q.setParameter("day", ngay);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<HoaDon> getListByNam(Integer ngay) {
        try ( Session session = HibernateUtil.getSession()) {
            List<HoaDon> list = new ArrayList<>();
            String hql = "SELECT hd FROM HoaDon hd where year(hd.ngayThanhToan) = :day";
            Query q = session.createQuery(hql);
            q.setParameter("day", ngay);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<HoaDon> getListByTime(Date to, Date from) {
        try ( Session session = HibernateUtil.getSession()) {
            Integer ngayTo = to.getDay();
            Integer ngayFrom = from.getDay();
            Integer thangTo = to.getMonth();
            Integer thangFrom = from.getMonth();
            Integer namTo = to.getYear();
            Integer namFrom = from.getYear();

            List<HoaDon> lst = new ArrayList<>();
//            String hql = "SELECT hd FROM HoaDon hd where (day(hd.ngayThanhToan) >= :ngayTo and day(hd.ngayThanhToan) <= :ngayFrom) "
//                    + "and (month(hd.ngayThanhToan) >= :thangTo and month(hd.ngayThanhToan) <= :thangFrom) "
//                    + "and (year(hd.ngayThanhToan) >= :namTo and year(hd.ngayThanhToan) <= :namFrom)";
            String hql = "SELECT hd FROM HoaDon hd where hd.ngayThanhToan >= :to and hd.ngayThanhToan <= :from";
            Query q = session.createQuery(hql);
//            q.setParameter("ngayTo", ngayTo);
//            q.setParameter("ngayFrom", ngayFrom);
//            q.setParameter("thangTo", thangTo);
//            q.setParameter("thangFrom", thangFrom);
//            q.setParameter("namTo", namTo);
//            q.setParameter("namFrom", namFrom);
            q.setParameter("to", to);
            q.setParameter("from", from);
            lst = q.getResultList();
            return lst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HoaDon> getListHDChuaTT() {
        try ( Session session = HibernateUtil.getSession()) {
            List<HoaDon> list = new ArrayList<>();
            String hql = "SELECT hd FROM HoaDon hd where hd.tinhTrang = 0";
            Query q = session.createQuery(hql);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<HoaDon> getListHD() {
        try ( Session session = HibernateUtil.getSession()) {
            List<HoaDon> list = new ArrayList<>();
            String hql = "SELECT hd FROM HoaDon hd where hd.tinhTrang = 1 or hd.tinhTrang = 2";
            Query q = session.createQuery(hql);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws ParseException {
        HoaDon hd = new HoaDonRepository().getHoaDonByMa("HD23480");
        hd.setTinhTrang(2);

        //System.out.println(new HoaDonRepository().updateTinhTrang(hd));

    }

}
