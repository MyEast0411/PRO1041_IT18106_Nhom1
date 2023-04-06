/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import domainModel.HoaDon;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import repositories.HoaDonRepository;
import service.ServiceHoaDon;

/**
 *
 * @author dongl
 */
public class ServiceHoaDonImpl implements ServiceHoaDon{
    private HoaDonRepository repo = new HoaDonRepository();
    @Override
    public List<HoaDon> getList() {
        return repo.getList();
    }

    @Override
    public Boolean insert(HoaDon hd) {
//        LocalDateTime time = LocalDateTime.now();
//        String maHd = "HD" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
//                + time.getDayOfMonth() + time.getSecond();
//        hd.setMa(maHd);
//        hd.setNgayTao(new java.sql.Date(new java.util.Date().getTime()));
//        hd.setTinhTrang(0);
        return repo.insert(hd);
    }

    @Override
    public Boolean update(HoaDon hd) {
        return repo.update(hd);
    }

    @Override
    public HoaDon getHoaDonByMa(String maHD) {
        return repo.getHoaDonByMa(maHD);
    }

    @Override
    public List<HoaDon> getListByNgay(Integer ngay) {
        return repo.getListByNgay(ngay);
    }

    @Override
    public List<HoaDon> getListByThang(Integer ngay) {
        return repo.getListByThang(ngay);
    }

    @Override
    public List<HoaDon> getListByNam(Integer ngay) {
        return repo.getListByNam(ngay);
    }

    @Override
    public List<HoaDon> getListByTime(Date to, Date from) {
        return repo.getListByTime(to, from);
    }

    @Override
    public List<HoaDon> getListHDChuaTT() {
        return repo.getListHDChuaTT();
    }

    @Override
    public List<HoaDon> getListHD() {
        return repo.getListHD();
    }
    
}
