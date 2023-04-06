/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

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

/**
 *
 * @author NGUYEN VAN HOI
 */
public interface ServiceDotKhuyenMai {

    List<DotKhuyenMai> getList();

    List<DotKhuyenMai> getListByDate(Date startDate, Date endDate);

    List<SanPhamChiTiet> getListSPCT();

    List<BiaKhuyenMai> getListBiaKhuyenMai();

    List<KhachHangKhuyenMai> getListKhachHangKhuyenMai();

    List<NSXKhuyenMai> getListNSXKhuyenMai();

    List<SanPhamChiTiet> findByTenSanPham(String tenSanPham);

    List<Bia> getListBia();

    List<DotKhuyenMai> getListByTen(String tenDKM);

    DotKhuyenMai getDotKhuyenMaiByMaDKM(String maDKM);

    boolean apDungKM(SanPhamChiTiet sp, DotKhuyenMai km);

    boolean checkBiaKhuyenMai(SanPhamChiTiet spct, DotKhuyenMai dotKM);

    boolean insert(DotKhuyenMai x);

    boolean validateDuplicateMaDKM(DotKhuyenMai x);

    boolean update(DotKhuyenMai x);

    boolean deleteMany(Date startDate, Date endDate);

    boolean deleteOne(DotKhuyenMai x);

    boolean deleteBiaKM(String tenSanPham, String maDotKhuyenMai);

    boolean updateTTBiaKM(SanPhamChiTiet sp, DotKhuyenMai km);

    boolean updateGiaConLaiBiaKM(BiaKhuyenMai biaKhuyenMai, BigDecimal giaConLai);

}
