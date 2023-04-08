/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import domainModel.Bia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import utilities.HibernateUtil;

/**
 *
 * @author SoiDiCode
 */
public class BiaRepo {
    public List<Bia> getAll(){
        List<Bia> list = new ArrayList<>();
        try (Session ss = HibernateUtil.getSession()){
            Query query = ss.createQuery("FROM Bia");
            list = query.getResultList();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
     public Bia findByMa(String ma){
        Bia bia = new Bia();
        try (Session ss = HibernateUtil.getSession()){
            Query query = ss.createQuery("FROM Bia where ma = :ma");
            query.setParameter("ma", ma);
            bia = (Bia) query.getSingleResult();  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bia;
    }
    
    public static void main(String[] args) {
        BiaRepo repo = new BiaRepo();
        System.out.println(repo.findByMa("BI004").toString());
    }
}
