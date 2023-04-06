/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModel.SanPhamChiTiet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Query;
import org.hibernate.Session;
import utilities.HibernateUtil;

/**
 *
 * @author Admin
 */
public class SanPhamChiTietResponsitory {
    public List<SanPhamChiTiet> getList(){
        List<SanPhamChiTiet> list = new ArrayList<>();
        Session s = HibernateUtil.getSession();
        Query q = s.createQuery("FROM SanPhamChiTiet");
        list = q.getResultList();
        s.close();
        return list;
    }
    
    public Boolean insert(SanPhamChiTiet x){
        try(Session s = HibernateUtil.getSession()) {
            s.getTransaction().begin();
            x.setId(UUID.randomUUID());
            s.save(x);
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    
     public Boolean updateSoLuong(SanPhamChiTiet x){
        try(Session s = HibernateUtil.getSession()) {
            SanPhamChiTiet chiTiet = s.get(SanPhamChiTiet.class, x.getId());
            
            chiTiet.setSoLuongTon(x.getSoLuongTon());
            
            s.getTransaction().begin();
            s.update(chiTiet);
            s.getTransaction().commit();
            return true;
        } catch (Exception e) {
            
        }
        return false;
    }
    
    public SanPhamChiTiet getSPCTByMa(String ma){
        try(Session s = HibernateUtil.getSession()) {
            String hql = "SELECT x FROM SanPhamChiTiet x where x.ma like :ma";
            Query q = s.createQuery(hql);
            q.setParameter("ma", ma);
            return (SanPhamChiTiet) q.getSingleResult();
        } catch (Exception e) {
        }
        return null;
    }

}
