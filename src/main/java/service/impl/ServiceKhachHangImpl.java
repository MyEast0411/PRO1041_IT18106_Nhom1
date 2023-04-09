/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import domainModel.HoaDon;
import domainModel.KhachHang;
import java.util.List;
import repositories.KhachHangRepository;
import service.ServiceKhachHang;
import utilities.Ex_Im_ExcelKH;
import viewmodel.HoaDonViewModel;
import viewmodel.KhachHangView;
import viewmodel.SPHDViewModel;

/**
 *
 * @author SoiDiCode
 */
public class ServiceKhachHangImpl implements ServiceKhachHang<KhachHang>{
private KhachHangRepository hangRepository = new KhachHangRepository();
private Ex_Im_ExcelKH eekh;

    public ServiceKhachHangImpl() {
        hangRepository = new KhachHangRepository();
        eekh = new Ex_Im_ExcelKH();
    }
    
    @Override
    public Boolean save(KhachHang object) {
       return hangRepository.insert(object);
    }

    @Override
    public Boolean update(String ma, KhachHang object) {
         return hangRepository.update(ma,object);
    }

    @Override
    public KhachHang getBySDT(String sdt) {
        return hangRepository.searchBySDT(sdt);
    }

    @Override
    public List<KhachHang> getAll() {
         return hangRepository.getAll();
    }

    
    public String renderMa(){
        return hangRepository.renderMa();
    }
    
    @Override
    public void writeExcel(List<KhachHang> list, String[] nameColumn,String nameSheet,String nameFile) {
       eekh.export(list, nameColumn,nameSheet,nameFile);
    }

    @Override
    public List<KhachHang> importExcel() {
       return eekh.importExcel();
    }
    public List<HoaDonViewModel> getHDByMaKH(String ma){
        return hangRepository.getHDByMaKh(ma);
    }
    
    public List<SPHDViewModel> getSPCTByMaHD(String ma){
        return hangRepository.getHDCTByMaHD(ma);
    }
    
    public List<KhachHangView> getLoad(){
        return hangRepository.getLoadTableByMoneyASC();
    }
    public List<KhachHangView> getLoadCBB(){
        return hangRepository.getLoadTableByMoneyDESC();
    }
    public List<KhachHangView> getKHBySDT(String sdt){
        return hangRepository.getKHbySDT(sdt);
    }
    
    
    
}
