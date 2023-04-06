/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModel.HoaDon;
import domainModel.HoaDonChiTiet;
import domainModel.SanPhamChiTiet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import utilities.HibernateUtil;

/**
 *
 * @author dongl
 */
public class HoaDonChiTietRepository {

    public List<HoaDonChiTiet> getList() {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try ( Session session = HibernateUtil.getSession()) {
            String hql = "FROM HoaDonChiTiet";
            Query q = session.createQuery(hql);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean insert(HoaDonChiTiet x) {
        try ( Session s = HibernateUtil.getSession()) {
            s.getTransaction().begin();
            s.save(x);
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Boolean update(HoaDonChiTiet x) {
        try ( Session s = HibernateUtil.getSession()) {
            String hql = "SELECT a FROM HoaDonChiTiet a where a.IdChiTietSanPham.id = :IdChiTietSP "
                    + "and a.IdHoaDon.id = :IdHoaDon";
            Query q = s.createQuery(hql);
            q.setParameter("IdChiTietSP", x.getIdChiTietSanPham().getId());
            q.setParameter("IdHoaDon", x.getIdHoaDon().getId());
            HoaDonChiTiet hdChiTiet = (HoaDonChiTiet) q.getSingleResult();

            hdChiTiet.setIdChiTietSanPham(x.getIdChiTietSanPham());
            hdChiTiet.setDonGia(x.getDonGia());
            hdChiTiet.setSoLuong(x.getSoLuong());

            s.getTransaction().begin();
            s.update(hdChiTiet);
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public Boolean delete(HoaDonChiTiet x) {
        try ( Session s = HibernateUtil.getSession()) {
            String hql = "SELECT a FROM HoaDonChiTiet a where a.IdHoaDon = :IdHoaDon";
            Query q = s.createQuery(hql);
            q.setParameter("IdHoaDon", x.getIdHoaDon());
            HoaDonChiTiet hdChiTiet = (HoaDonChiTiet) q.getSingleResult();

            s.getTransaction().begin();
            s.delete(hdChiTiet);
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public HoaDonChiTiet getHoaDonCTByMaHD(String maHD) {
        try ( Session session = HibernateUtil.getSession()) {
            String hql = "SELECT hd FROM HoaDonChiTiet hd where hd.IdHoaDon.ma = :ma";
            Query q = session.createQuery(hql);
            q.setParameter("ma", maHD);
            return (HoaDonChiTiet) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean deleteHoaDonCTByMaHD(HoaDon maHD) {
        try ( Session session = HibernateUtil.getSession()) {
            String hql = "SELECT hd FROM HoaDonChiTiet hd where hd.IdHoaDon.id = :ma";
            Query q = session.createQuery(hql);
            q.setParameter("ma", maHD.getId());
            List<HoaDonChiTiet> hdct = q.getResultList();
            for (HoaDonChiTiet hoaDonChiTiet : hdct) {
                session.getTransaction().begin();
                session.delete(hoaDonChiTiet);
                session.getTransaction().commit();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HoaDonChiTiet> getListByMaHD(String maHD) {
        try ( Session session = HibernateUtil.getSession()) {
            String hql = "SELECT hd FROM HoaDonChiTiet hd where hd.IdHoaDon.ma = :ma";
            Query q = session.createQuery(hql);
            q.setParameter("ma", maHD);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean deleteHoaDonCTByMaHD_MaSP(HoaDon maHD, SanPhamChiTiet maSP) {
        try ( Session session = HibernateUtil.getSession()) {
            String hql = "SELECT hd FROM HoaDonChiTiet hd where hd.IdHoaDon.id = :ma and hd.IdChiTietSanPham.id = :maSP";
            Query q = session.createQuery(hql);
            q.setParameter("ma", maHD.getId());
            q.setParameter("maSP", maSP.getId());
            HoaDonChiTiet hdct = (HoaDonChiTiet) q.getSingleResult();
            session.getTransaction().begin();
            session.delete(hdct);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(new HoaDonChiTietRepository().getHoaDonCTByMaHD("HD001").getSoLuong());
    }

}
