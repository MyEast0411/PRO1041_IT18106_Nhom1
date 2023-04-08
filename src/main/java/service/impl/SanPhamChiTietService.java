/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.ArrayList;
import model.SanPhamChiTiet_;
import repositories.SanPhamChiTietResponsitory;
import service.SanPhamChiTietInterface;
import viewmodel.SPCTBiaNsxLoaiSp;

/**
 *
 * @author Admin
 */
public class SanPhamChiTietService implements SanPhamChiTietInterface{
public SanPhamChiTietResponsitory spctRes;

    public SanPhamChiTietService() {
        spctRes = new SanPhamChiTietResponsitory();
    }

    @Override
    public ArrayList<SanPhamChiTiet_> getAllSPCT() {
      return spctRes.getAllSPCT();
    }

    @Override
    public Boolean addSPChiTiet(SanPhamChiTiet_ spct) {
       return spctRes.addSPChiTiet(spct);
    }

    @Override
    public Boolean deleteSPCT(String id) {
       return spctRes.deleteSPCT(id);
    }

    @Override
    public Boolean updateSPCT(SanPhamChiTiet_ spct) {
     return spctRes.updateSPCT(spct);
    }

    @Override
    public ArrayList<SPCTBiaNsxLoaiSp> getAllSPCTBiaNsxLoaiSp() {
      return spctRes.getAllSPCTBiaNsxLoaiSp();
    }

    @Override
    public ArrayList<SPCTBiaNsxLoaiSp> TimKiemSPCTBiaNsxLoaiSp(String tenSP) {
        return spctRes.TimKiemSPCTBiaNsxLoaiSp(tenSP);
    }
    
}
