/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class SanPhamChiTiet_ {

    private String id;
    private String maSPCT;
    private BigDecimal giaBan;
    private BigDecimal giaNhap;
    private String hanSD;
    private Date ngaySX;
    private String nongDoCon;
    private int soLuongLonBia;
    private int soLuongTon;
    private String thanhPhan;
    private String theTich;
    private int tinhTrang;
    private int trangThai;
    private String xuatXu;
    private String idBia;
    private String idLoaiSP;
    private String idNSX;

    public SanPhamChiTiet_() {
    }

    public SanPhamChiTiet_(String id, String maSPCT, BigDecimal giaBan, BigDecimal giaNhap, String hanSD, Date ngaySX, String nongDoCon, int soLuongLonBia, int soLuongTon, String thanhPhan, String theTich, int tinhTrang, int trangThai, String xuatXu, String idBia, String idLoaiSP, String idNSX) {
        this.id = id;
        this.maSPCT = maSPCT;
        this.giaBan = giaBan;
        this.giaNhap = giaNhap;
        this.hanSD = hanSD;
        this.ngaySX = ngaySX;
        this.nongDoCon = nongDoCon;
        this.soLuongLonBia = soLuongLonBia;
        this.soLuongTon = soLuongTon;
        this.thanhPhan = thanhPhan;
        this.theTich = theTich;
        this.tinhTrang = tinhTrang;
        this.trangThai = trangThai;
        this.xuatXu = xuatXu;
        this.idBia = idBia;
        this.idLoaiSP = idLoaiSP;
        this.idNSX = idNSX;
    }



    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public String getHanSD() {
        return hanSD;
    }

    public void setHanSD(String hanSD) {
        this.hanSD = hanSD;
    }

    public Date getNgaySX() {
        return ngaySX;
    }

    public void setNgaySX(Date ngaySX) {
        this.ngaySX = ngaySX;
    }

    public String getNongDoCon() {
        return nongDoCon;
    }

    public void setNongDoCon(String nongDoCon) {
        this.nongDoCon = nongDoCon;
    }

    public int getSoLuongLonBia() {
        return soLuongLonBia;
    }

    public void setSoLuongLonBia(int soLuongLonBia) {
        this.soLuongLonBia = soLuongLonBia;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getThanhPhan() {
        return thanhPhan;
    }

    public void setThanhPhan(String thanhPhan) {
        this.thanhPhan = thanhPhan;
    }

    public String getTheTich() {
        return theTich;
    }

    public void setTheTich(String theTich) {
        this.theTich = theTich;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getIdBia() {
        return idBia;
    }

    public void setIdBia(String idBia) {
        this.idBia = idBia;
    }

    public String getIdLoaiSP() {
        return idLoaiSP;
    }

    public void setIdLoaiSP(String idLoaiSP) {
        this.idLoaiSP = idLoaiSP;
    }

    public String getIdNSX() {
        return idNSX;
    }

    public void setIdNSX(String idNSX) {
        this.idNSX = idNSX;
    }

    @Override
    public String toString() {
        return "SanPhamChiTiet_{" + "id=" + id + ", giaBan=" + giaBan + ", giaNhap=" + giaNhap + ", hanSD=" + hanSD + ", ngaySX=" + ngaySX + ", nongDoCon=" + nongDoCon + ", soLuongLonBia=" + soLuongLonBia + ", soLuongTon=" + soLuongTon + ", thanhPhan=" + thanhPhan + ", theTich=" + theTich + ", tinhTrang=" + tinhTrang + ", trangThai=" + trangThai + ", xuatXu=" + xuatXu + ", idBia=" + idBia + ", idLoaiSP=" + idLoaiSP + ", idNSX=" + idNSX + '}';
    }
    
    
   

}
