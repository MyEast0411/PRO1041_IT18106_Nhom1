/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import domainModel.Bia;
import domainModel.BiaKhuyenMai;
import domainModel.DotKhuyenMai;
import domainModel.KhachHangKhuyenMai;
import domainModel.NSXKhuyenMai;
import domainModel.SanPhamChiTiet;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import repositories.DotKhuyenMaiRepository;
import service.ServiceDotKhuyenMai;

/**
 *
 * @author NGUYEN VAN HOI
 */
public class ServiceDotKhuyenMaiImpl implements ServiceDotKhuyenMai {

    private final DotKhuyenMaiRepository DKMRepo = new DotKhuyenMaiRepository();

    @Override
    public List<DotKhuyenMai> getList() {
        return DKMRepo.getListt();
    }

    @Override
    public boolean insert(DotKhuyenMai x) {
        return DKMRepo.insert(x);
    }

    @Override
    public boolean update(DotKhuyenMai x) {
        return DKMRepo.update(x);
    }

    @Override
    public List<BiaKhuyenMai> getListBiaKhuyenMai() {
        return DKMRepo.getListBiaKhuyenMai();
    }

    @Override
    public boolean deleteMany(Date startDate, Date endDate) {
        return DKMRepo.deleteMany(startDate, endDate);
    }

    @Override
    public boolean deleteOne(DotKhuyenMai x) {
        return DKMRepo.deleteOne(x);
    }

    @Override
    public List<Bia> getListBia() {
        return DKMRepo.getListBia();
    }

    @Override
    public List<SanPhamChiTiet> findByTenSanPham(String tenSanPham) {
        return DKMRepo.findByTenSanPham(tenSanPham);
    }

    @Override
    public List<DotKhuyenMai> getListByTen(String tenDKM) {
        return DKMRepo.getListByTen(tenDKM);
    }

    @Override
    public DotKhuyenMai getDotKhuyenMaiByMaDKM(String maDKM) {
        return DKMRepo.getDotKhuyenMaiByMaDKM(maDKM);
    }

    @Override
    public boolean checkBiaKhuyenMai(SanPhamChiTiet spct, DotKhuyenMai dotKM) {
        return DKMRepo.checkBiaKhuyenMai(spct, dotKM);
    }

    @Override
    public boolean validateDuplicateMaDKM(DotKhuyenMai x) {
        return DKMRepo.validateDuplicateMaDKM(x);
    }

    @Override
    public boolean apDungKM(SanPhamChiTiet sp, DotKhuyenMai km) {
        return DKMRepo.apDungKM(sp, km);
    }

    @Override
    public List<SanPhamChiTiet> getListSPCT() {
        return DKMRepo.getListSPCT();
    }

    @Override
    public boolean deleteBiaKM(String tenSanPham, String maDotKhuyenMai) {
        return DKMRepo.deleteBiaKM(tenSanPham, maDotKhuyenMai);
    }

    @Override
    public List<DotKhuyenMai> getListByDate(Date startDate, Date endDate) {
        return DKMRepo.getListByDate(startDate, endDate);
    }

    @Override
    public boolean updateTTBiaKM() {
        return DKMRepo.updateTTBiaKM();
    }

    @Override
    public boolean updateGiaConLaiBiaKM(BiaKhuyenMai biaKhuyenMai, BigDecimal giaConLai) {
        return DKMRepo.updateGiaConLaiBiaKM(biaKhuyenMai, giaConLai);
    }

    @Override
<<<<<<< HEAD
    public SanPhamChiTiet findByMa(String maSP) {
        return DKMRepo.findByMa(maSP);
    }

  
=======
    public BiaKhuyenMai getBiaKhuyenMai(SanPhamChiTiet spct, DotKhuyenMai km) {
        return DKMRepo.getBiaKhuyenMai(spct, km);
    }

    @Override
    public List<DotKhuyenMai> getDKMByDate(Date date) {
        return DKMRepo.getDKMByDate(date);
    }

>>>>>>> 6c0494633b591e5051084673d6ca73b74826d515

}
