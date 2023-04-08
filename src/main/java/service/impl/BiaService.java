/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.ArrayList;
import model.Bia_;
import repositories.BiaResponsitory;
import service.BiaInterface;

/**
 *
 * @author Admin
 */
public class BiaService implements BiaInterface {

   public BiaResponsitory biaRes;

    public BiaService() {
        biaRes = new BiaResponsitory();
    }

    @Override
    public ArrayList<Bia_> getAllBia() {
        return biaRes.getAllBia();
    }

    @Override
    public Boolean addBia(Bia_ b) {
        return biaRes.addBia(b);
    }

    @Override
    public Boolean updateBia(Bia_ b) {
        return biaRes.updateBia(b);
    }

    @Override
    public Boolean deleteBia(String ma) {
        return biaRes.deleteBia(ma);
    }

    @Override
    public ArrayList<Bia_> TimKiemTheoTenSP(String tenSP) {
       return biaRes.TimKiemTheoTenSP(tenSP);
    }

}
