/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class SPCTBiaNsxLoaiSp {
    private String maSP;
    private String tenSP;
    //
    private String loaiSP;
    //
    private String nsx;
    //
    private String maSPCT;
    private String theTich;
     private String nongDoCon;
      private int trangThai;
      private int soLuongLonBia;
      private int soLuongLonTon;
      private BigDecimal giaNhap;
      private BigDecimal giaBan;
      private String hanSD;
      private String xuatXu;
      private String thanhPhan;
      private int tinhTrang;

    public SPCTBiaNsxLoaiSp() {
    }

    public SPCTBiaNsxLoaiSp(String maSP, String tenSP, String loaiSP, String nsx, String maSPCT, String theTich, String nongDoCon, int trangThai, int soLuongLonBia, int soLuongLonTon, BigDecimal giaNhap, BigDecimal giaBan, String hanSD, String xuatXu, String thanhPhan, int tinhTrang) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.nsx = nsx;
        this.maSPCT = maSPCT;
        this.theTich = theTich;
        this.nongDoCon = nongDoCon;
        this.trangThai = trangThai;
        this.soLuongLonBia = soLuongLonBia;
        this.soLuongLonTon = soLuongLonTon;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.hanSD = hanSD;
        this.xuatXu = xuatXu;
        this.thanhPhan = thanhPhan;
        this.tinhTrang = tinhTrang;
    }


    public String getNsx() {
        return nsx;
    }

    public void setNsx(String nsx) {
        this.nsx = nsx;
    }



    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

 

 

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public String getTheTich() {
        return theTich;
    }

    public void setTheTich(String theTich) {
        this.theTich = theTich;
    }

    public String getNongDoCon() {
        return nongDoCon;
    }

    public void setNongDoCon(String nongDoCon) {
        this.nongDoCon = nongDoCon;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getSoLuongLonBia() {
        return soLuongLonBia;
    }

    public void setSoLuongLonBia(int soLuongLonBia) {
        this.soLuongLonBia = soLuongLonBia;
    }

    public int getSoLuongLonTon() {
        return soLuongLonTon;
    }

    public void setSoLuongLonTon(int soLuongLonTon) {
        this.soLuongLonTon = soLuongLonTon;
    }



    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public String getHanSD() {
        return hanSD;
    }

    public void setHanSD(String hanSD) {
        this.hanSD = hanSD;
    }

 

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getThanhPhan() {
        return thanhPhan;
    }

    public void setThanhPhan(String thanhPhan) {
        this.thanhPhan = thanhPhan;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    @Override
    public String toString() {
        return "SPCTBiaNsxLoaiSp{" + "maSP=" + maSP + ", tenSP=" + tenSP + ", loaiSP=" + loaiSP + ", maSPCT=" + maSPCT + ", theTich=" + theTich + ", nongDoCon=" + nongDoCon + ", trangThai=" + trangThai + ", soLuongLonBia=" + soLuongLonBia + ", soLuongLonTon=" + soLuongLonTon + ", giaNhap=" + giaNhap + ", giaBan=" + giaBan + ", hanSD=" + hanSD + ", xuatXu=" + xuatXu + ", thanhPhan=" + thanhPhan + ", tinhTrang=" + tinhTrang + '}';
    }

   






  
  



   
      
      
      
    
}
