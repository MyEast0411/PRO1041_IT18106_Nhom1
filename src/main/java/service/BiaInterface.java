/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.ArrayList;
import model.Bia_;

/**
 *
 * @author Admin
 */
public interface BiaInterface {
    public ArrayList<Bia_> getAllBia();
    public Bia_ getBiaById(String id);
    public Boolean addBia(Bia_ b);
     public Boolean updateBia(Bia_ b);
     public Boolean deleteBia(String ma);
     public ArrayList<Bia_> TimKiemTheoTenSP(String tenSP);
}
