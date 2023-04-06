/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import domainModel.HoaDon;
import domainModel.HoaDonChiTiet;
import domainModel.SanPhamChiTiet;
import java.util.List;
import repositories.HoaDonChiTietRepository;
import service.ServiceHoaDonChiTiet;

/**
 *
 * @author dongl
 */
public class ServiceHoaDonChiTietImpl implements ServiceHoaDonChiTiet{
    private HoaDonChiTietRepository repo = new HoaDonChiTietRepository();

    @Override
    public HoaDonChiTiet getHoaDonCTByMaHD(String maHD) {
        return repo.getHoaDonCTByMaHD(maHD);
    }

    @Override
    public List<HoaDonChiTiet> getList() {
        return repo.getList();
    }

    @Override
    public Boolean insert(HoaDonChiTiet x) {
        return repo.insert(x);
    }

    @Override
    public Boolean update(HoaDonChiTiet x) {
        return repo.update(x);
    }

    @Override
    public Boolean delete(HoaDonChiTiet x) {
        return repo.delete(x);
    }

    @Override
    public List<HoaDonChiTiet> getListByMaHD(String maHD) {
        return repo.getListByMaHD(maHD);
    }

    @Override
    public Boolean deleteHoaDonCTByMaHD(HoaDon maHD) {
        return repo.deleteHoaDonCTByMaHD(maHD);
    }

    @Override
    public Boolean deleteHoaDonCTByMaHD_MaSP(HoaDon maHD, SanPhamChiTiet maSP) {
        return repo.deleteHoaDonCTByMaHD_MaSP(maHD, maSP);
    }

    
    
   
    
}
