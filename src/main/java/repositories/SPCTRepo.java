/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModel.Bia;
import domainModel.LoaiSP;
import domainModel.SanPhamChiTiet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utilities.HibernateUtil;
import viewmodel.SPCTBiaNsxLoaiSpHiber;


/**
 *
 * @author SoiDiCode
 */
public class SPCTRepo {
    public List<SPCTBiaNsxLoaiSpHiber> getAll(){
        String view = " new viewmodel.SPCTBiaNsxLoaiSp("
                + "spct.ma ,"
                + "spct.bia ,"
                + " spct.loaiSP ,"
                + " spct.nsx ,"
                + " spct.theTich ,"
                + " spct.trangThai ,"
                + " spct.giaBan ,"
                + " spct.tinhTrang , "
                + " spct.soLuongTon "
                + ")";
        
        List<SPCTBiaNsxLoaiSpHiber> list = new ArrayList<>();
        try(Session ss = HibernateUtil.getSession()) {
            Query query = ss.createQuery("Select " + view + "From SanPhamChiTiet spct");
           list = query.getResultList();
        } catch (Exception e) {
        e.printStackTrace();
        }
        return list;
    }
    
     public List<SPCTBiaNsxLoaiSpHiber> getListThung(){
        String view = " new viewmodel.SPCTBiaNsxLoaiSp("
                + "spct.ma ,"
                + "spct.bia ,"
                + " spct.loaiSP ,"
                + " spct.nsx ,"
                + " spct.theTich ,"
                + " spct.trangThai ,"
                + " spct.giaBan ,"
                + " spct.tinhTrang , "
                + " spct.soLuongTon "
                + ")";
        
        List<SPCTBiaNsxLoaiSpHiber> list = new ArrayList<>();
        try(Session ss = HibernateUtil.getSession()) {
            Query query = ss.createQuery("Select " + view + "From SanPhamChiTiet spct "
                    + "where spct.trangThai = 1 ");
           list = query.getResultList();
        } catch (Exception e) {
        e.printStackTrace();
        }
        return list;
    }
     
     public static void main(String[] args) {
        SPCTRepo cTRepo = new SPCTRepo();
         System.out.println( cTRepo.getListThung().size());
    }
    
    
    
    
    public boolean insertList(List<SanPhamChiTiet> list){
        Transaction tran = null;
        try (Session ss = HibernateUtil.getSession()){
            for (SanPhamChiTiet s : list) {
             tran = ss.beginTransaction();
             ss.save(s);
             tran.commit();
            }
            return true;
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
        }
        return false;
    }
    
    
    public boolean insert(SanPhamChiTiet spct){
        Transaction tran = null;
        try (Session ss = HibernateUtil.getSession()){
             tran = ss.beginTransaction();
             ss.save(spct);
             tran.commit();
            return true;
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
        }
        return false;
    }
    public boolean delete(SanPhamChiTiet  chiTiet){
//         String hql = "delete from SanPhamChiTiet where ma = :ma";
            Transaction transaction = null;
        try (Session ss = HibernateUtil.getSession()){
           transaction = ss.beginTransaction();
           ss.delete(chiTiet);
           transaction.commit();
           return true;
        } catch (Exception e) {
           e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return false;
    }
    public SanPhamChiTiet findByMa(String ma){
       SanPhamChiTiet spct = new SanPhamChiTiet();
        try(Session ss = HibernateUtil.getSession()) {
            Query query = ss.createQuery("From SanPhamChiTiet where ma = :ma");
            query.setParameter("ma", ma);
            spct = (SanPhamChiTiet) query.getSingleResult();
            return spct;
        } catch (Exception e) {
//        e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateSL(SanPhamChiTiet chiTiet){
        Transaction tran = null;
       try(Session ss = HibernateUtil.getSession()){
          
            tran = ss.beginTransaction();
            ss.update(chiTiet);
            tran.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (tran != null) {
               tran.rollback();
           }
        }
       return false;
    }
    
//    public int getSlLon(Bia bia , LoaiSP loaiSP , String tinhTrang){
//        List<SanPhamChiTiet> list = new ArrayList<>();
//        String hql = "select spct "
//                + "from SanPhamChiTiet spct "
//                + "where spct.bia = :bia and "
//                + " spct.loaiSP = :loaiSP "
//                + " and spct.tinhTrang = :sl ";
//       try(Session ss = HibernateUtil.getSession()) {
//            Query query = ss.createQuery(hql);
//            query.setParameter("bia", bia);
//            query.setParameter("loaiSP", loaiSP);
//            query.setParameter("sl",tinhTrang.equalsIgnoreCase("lon")?0:1);
//            list = query.getResultList();
////            for (SanPhamChiTiet s : list) {
////                System.out.println(s.toString());
////           }
//            int kq = list.size();
//            return kq;
//        } catch (Exception e) {
//        e.printStackTrace();
//        }
//        
//        return 0;
//    }
    
    public List<SanPhamChiTiet> getMaSPCTByThung(Bia bia , LoaiSP loaiSP , int tinhTrang){
        List<SanPhamChiTiet> list = new ArrayList<>();
        String hql = " select spct from SanPhamChiTiet spct"
              +  "  where spct.bia = :bia and "
                + " spct.loaiSP = :loaiSP "
                + " and spct.tinhTrang = :tt";
        try (Session ss = HibernateUtil.getSession())
        {
            Query query = ss.createQuery(hql);
            query.setParameter("bia", bia);
            query.setParameter("loaiSP", loaiSP);
            query.setParameter("tt",tinhTrang);
            list = query.getResultList();
         Collections.sort(list, Comparator.comparing( SanPhamChiTiet :: getNgaySanXuat));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
//    public static void main(String[] args) {
//        SPCTRepo biaRepo = new SPCTRepo();
//        for (SPCTBiaNsxLoaiSp s : biaRepo.getAll()) {
//            System.out.println(s.toString());
//        }
//        for (SanPhamChiTiet s : biaRepo.getMaSPCTByThung()) {
//            System.out.println(s.getMa());
//        }
        
        

//        Bia bia = new Bia();
//        bia.setId(UUID.fromString("d20a8c99-bf02-43b2-b114-0418d289ba90"));
//      
//        LoaiSP loaiSP = new LoaiSP();
//        loaiSP.setId(UUID.fromString("ff19969f-868b-11d0-b42d-00c04fc964ff"));
//        int kq = biaRepo.getSlLon(bia, loaiSP);
//        System.out.println("Kq " + kq);
//        for (SPCTBiaNsxLoaiSp s : biaRepo.getAll()) {
//            System.out.println(s.getBia().getId()+" - " + s.getBia().getTen());
//            System.out.println(s.getLoaiSP().getId()+" - " + s.getLoaiSP().getTen());
//        }
    }
//}
