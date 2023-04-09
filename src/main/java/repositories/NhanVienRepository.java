/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModel.NhanVien;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import utilities.HibernateUtil;

/**
 *
 * @author dongl
 */
public class NhanVienRepository {

    private HibernateUtil ss = new HibernateUtil();

    public List<NhanVien> getList() {
        try {
            String sql = "SELECT nv FROM NhanVien nv";
            Session session = ss.getSession();
            Query q = session.createQuery(sql);
            List<NhanVien> list = new ArrayList<>();
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean insert(NhanVien nv) {
        try {
            Session session = ss.getSession();
            session.getTransaction().begin();
            session.save(nv);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean update(NhanVien nv, String maNV) {
        try {
            Session session = ss.getSession();
            String hql = "SELECT nv FROM NhanVien nv where nv.ma = :ma";
            Query q = session.createQuery(hql);
            q.setParameter("ma", maNV);
            NhanVien nhanVien = (NhanVien) q.getSingleResult();

            nhanVien.setChucVu(nv.getChucVu());
            nhanVien.setDiaChi(nv.getDiaChi());
            nhanVien.setEmail(nv.getEmail());
            nhanVien.setGioiTinh(nv.getGioiTinh());
            nhanVien.setHoTen(nv.getHoTen());
            nhanVien.setMa(nv.getMa());
            nhanVien.setMatKhau(nv.getMatKhau());
            nhanVien.setSdt(nv.getSdt());
            nhanVien.setTenDangNhap(nv.getTenDangNhap());
            nhanVien.setTrangThaiLamViec(nv.getTrangThaiLamViec());

            session.getTransaction().begin();
            session.update(nhanVien);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public NhanVien getNhanVienByMa(String maNV) {
        try {
            Session session = ss.getSession();
            String hql = "SELECT nv FROM NhanVien nv where nv.ma = :ma";
            Query q = session.createQuery(hql);
            q.setParameter("ma", maNV);
            NhanVien nv = (NhanVien) q.getSingleResult();
            session.close();
            return nv;
        } catch (Exception e) {
            System.out.println("hi");
            return null;
        }
    }

    public NhanVien getNhanVienByTenDN(String text) {
        try {
            Session session = ss.getSession();
            String hql = "SELECT nv FROM NhanVien nv where nv.tenDangNhap = :ma";
            Query q = session.createQuery(hql);
            q.setParameter("ma", text);
            NhanVien nv = (NhanVien) q.getSingleResult();
            session.close();
            return nv;
        } catch (Exception e) {
            System.out.println("hi");
            return null;
        }
    }

    public List<NhanVien> getListByTT(Integer tt) {
        try {
            List<NhanVien> list = new ArrayList<>();
            Session session = ss.getSession();
            String hql = "SELECT nv FROM NhanVien nv where nv.trangThaiLamViec = :tt";
            Query q = session.createQuery(hql);
            q.setParameter("tt", tt);
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public List<NhanVien> getListByTen(String ten) {
        try {
            List<NhanVien> list = new ArrayList<>();
            Session session = ss.getSession();
            String hql = "SELECT nv FROM NhanVien nv where nv.hoTen like :ten";
            Query q = session.createQuery(hql);
            q.setParameter("ten", "%" + ten + "%");
            list = q.getResultList();
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        NhanVienRepository repo = new NhanVienRepository();
//        NhanVien nv = new NhanVien(null, "NV001", "quachdong", "Quách Đông", 1, "0964457125", "Đan Phượng", "donglun0411@gmail.com", "donglun", 1, 1);
//        repo.insert(nv);
        NhanVien nv = repo.getNhanVienByMa("NV001");
        System.out.println(nv.getHoTen());
        //System.out.println(new NhanVienRepository().getList().size());
    }
}
