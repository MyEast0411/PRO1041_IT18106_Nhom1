/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.ArrayList;
import model.SanPhamChiTiet_;
import viewmodel.SPCTBiaNsxLoaiSp;

/**
 *
 * @author Admin
 */
public interface SanPhamChiTietInterface {
   public ArrayList<SanPhamChiTiet_> getAllSPCT();
   public Boolean addSPChiTiet(SanPhamChiTiet_ spct);
   public Boolean deleteSPCT(String id);
   public Boolean updateSPCT(SanPhamChiTiet_ spct) ;
   public ArrayList<SPCTBiaNsxLoaiSp> getAllSPCTBiaNsxLoaiSp();
   public ArrayList<SPCTBiaNsxLoaiSp> TimKiemSPCTBiaNsxLoaiSp(String tenSP);
}
