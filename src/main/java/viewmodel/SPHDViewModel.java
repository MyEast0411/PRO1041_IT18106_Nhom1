/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.math.BigDecimal;

/**
 *
 * @author SoiDiCode
 */
public class SPHDViewModel {
    private String maSP;
    private String tenSP;
    private BigDecimal donGia;
    private int soLuong;
    private String theTich;
    private String theLoai;
    private String nhaSX;

    public SPHDViewModel() {
    }

    public SPHDViewModel(String maSP, String tenSP, BigDecimal donGia, int soLuong, String theTich, String theLoai, String nhaSX) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.theTich = theTich;
        this.theLoai = theLoai;
        this.nhaSX = nhaSX;
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

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getNhaSX() {
        return nhaSX;
    }

    public void setNhaSX(String nhaSX) {
        this.nhaSX = nhaSX;
    }

    public String getTheTich() {
        return theTich;
    }

    public void setTheTich(String theTich) {
        this.theTich = theTich;
    }
    
    public Object[] getData(int index){
        return new Object[]{
            index, maSP,
     tenSP,
    donGia,
     soLuong,
     theTich,
     theLoai,
    nhaSX
        };
    }

    @Override
    public String toString() {
        return "SPHDViewModel{" + "maSP=" + maSP + ", tenSP=" + tenSP + ", donGia=" + donGia + ", soLuong=" + soLuong + ", theTich=" + theTich + ", theLoai=" + theLoai + ", nhaSX=" + nhaSX + '}';
    }

    
    
}
