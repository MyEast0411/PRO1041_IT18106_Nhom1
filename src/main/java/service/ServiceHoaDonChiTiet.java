/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainModel.HoaDon;
import domainModel.HoaDonChiTiet;
import domainModel.SanPhamChiTiet;
import java.util.List;

/**
 *
 * @author dongl
 */
public interface ServiceHoaDonChiTiet {
    HoaDonChiTiet getHoaDonCTByMaHD(String maHD);
    List<HoaDonChiTiet> getList();
    Boolean insert(HoaDonChiTiet x);
    Boolean update(HoaDonChiTiet x);
    Boolean delete(HoaDonChiTiet x);
    List<HoaDonChiTiet> getListByMaHD(String maHD);
    Boolean deleteHoaDonCTByMaHD(HoaDon maHD);
    Boolean deleteHoaDonCTByMaHD_MaSP(HoaDon maHD,SanPhamChiTiet maSP);
}
