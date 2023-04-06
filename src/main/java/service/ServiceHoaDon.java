/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainModel.HoaDon;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dongl
 */
public interface ServiceHoaDon {
    List<HoaDon> getList();
    Boolean insert(HoaDon hd);
    Boolean update(HoaDon hd);
    HoaDon getHoaDonByMa(String maHD);
    List<HoaDon> getListByNgay(Integer ngay);
    List<HoaDon> getListByThang(Integer ngay);
    List<HoaDon> getListByNam(Integer ngay);
    List<HoaDon> getListByTime(Date to,Date from);
    List<HoaDon> getListHDChuaTT();
    List<HoaDon> getListHD();
}
