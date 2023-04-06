/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModel.Voucher;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import utilities.HibernateUtil;

/**
 *
 * @author SoiDiCode0
 */
public class VoucherRepository {
    public List<Voucher> getAll(){
        List<Voucher> list = new ArrayList<>();
        try (Session ss = HibernateUtil.getSession();){
             TypedQuery<Voucher> query = ss.createQuery("From Voucher");
             list = query.getResultList();
             return list;
        } catch (Exception e) {
        }
        return list;
    }
    
    public Voucher getVoucherByMa(String ma){
        Voucher voucher = new Voucher();
        try(Session ss = HibernateUtil.getSession();) {      
            TypedQuery<Voucher> query = ss.createQuery("From Voucher where ma = :ma");
             voucher = query.getSingleResult();
            return voucher;
        } catch (Exception e) {
        }
        return null;
    }
    
    public Boolean insert(Voucher voucher){
        try (Session ss = HibernateUtil.getSession();){
            ss.getTransaction().begin();
            ss.save(voucher);
            ss.getTransaction().commit();
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    
    public Boolean update(Voucher voucher , String id){
        Voucher vou = getVoucherByMa(id);
        if (vou==null) {
            return false;
        }
        try(Session ss = HibernateUtil.getSession();) {
            ss.getTransaction().begin();
            vou.setGiamGia(voucher.getGiamGia());
            vou.setHinhThuc(voucher.getHinhThuc());
            vou.setTenVoucher(voucher.getTenVoucher());
            vou.setNgayBatDau(voucher.getNgayBatDau());
            vou.setNgayKetThuc(voucher.getNgayKetThuc());
            ss.update(voucher);
            ss.getTransaction().commit();
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    
    public Boolean deleteById(String ma){
        Voucher vou = getVoucherByMa(ma);
        if (vou==null) {
            return false;
        }
        try(Session ss = HibernateUtil.getSession();) {
            ss.getTransaction().begin();
            ss.delete(vou);
            ss.getTransaction().commit();
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    
    public List<Voucher> getVoucherByDate(Date ngayBatDau , Date ngayKetThuc){
        List<Voucher> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String hql = "Select vh from Voucher vh where vh.ngayBatDau <= :ngayBatDau and vh.ngayKetThuc >= :ngayKetThuc";
        try {
            Session ss = HibernateUtil.getSession();
            TypedQuery<Voucher> query = ss.createQuery(hql);
            query.setParameter("ngayBatDau", ngayBatDau);
            query.setParameter("ngayKetThuc", ngayKetThuc);  
             list = query.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
     public List<Voucher> getVoucherUsing(){
        List<Voucher> list = new ArrayList<>();
        String hql = "Select vh from Voucher vh where vh.ngayKetThuc >= :ngayHienTai";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(format.format(new Date()));
            Session ss = HibernateUtil.getSession();
            TypedQuery<Voucher> query = ss.createQuery(hql);
            query.setParameter("ngayHienTai", date);  
             list = query.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     
     
     public List<Voucher> getVoucherNotUsing(){
         List<Voucher> list = new ArrayList<>();
        String hql = "Select vh from Voucher vh where vh.ngayKetThuc < :ngayHienTai";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(format.format(new Date()));
            Session ss = HibernateUtil.getSession();
            TypedQuery<Voucher> query = ss.createQuery(hql);
            query.setParameter("ngayHienTai", date);  
            list = query.getResultList();
            return  list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
     
     public List<Voucher> findByMa(String ma){
         List<Voucher> list = new ArrayList<>();
         try(Session ss = HibernateUtil.getSession();) {      
            Query query = ss.createQuery("From Voucher where maVoucher like :ma");
            query.setParameter("ma","%"+ ma+"%");
            list = query.getResultList();
           
        } catch (Exception e) {
        }
        return list;
     }
     
     
    public static void main(String[] args) throws ParseException {
        VoucherRepository repository = new  VoucherRepository();
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(format.format(new Date()));
//       
//        System.out.println(date);
//      ngayBatDau=2023-03-20 00:00:00.0, ngayKetThuc=2023-03-29 00:00:00.0)
//      ngayBatDau=2023-03-15 00:00:00.0, ngayKetThuc=2023-03-24 00:00:00.0)

//       Voucher voucher = repository.getVoucherByDate(format.parse("2023-03-20"), format.parse("2023-03-29")).get(0);
//         voucher.setMaVoucher("VOUCHER1");
//         
//         repository.insert(voucher);
         
         
//        Voucher voucher1=  repository.getVoucherNotUsing().get(0);
//        voucher1.setMaVoucher("VOUCHER2");
//        repository.insert(voucher1);
//         System.out.println(check1);

          System.out.println(repository.findByMa("1").size());
       
    }
}
