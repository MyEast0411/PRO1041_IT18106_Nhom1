/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author SoiDiCode
 */
public class HoaDonViewModel {
    
    private String maHD;
    private String maNV;
    private String hoTen;
    private Date ngayThanhToan;
    private int hinhThucThanhToan;
    private BigDecimal tongTien;
    private BigDecimal tienMat;

    public HoaDonViewModel() {
    }

    public HoaDonViewModel(String maHD, String maNV, String hoTen, Date ngayThanhToan, int hinhThucThanhToan, BigDecimal tongTien, BigDecimal tienMat) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.ngayThanhToan = ngayThanhToan;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.tongTien = tongTien;
        this.tienMat = tienMat;
    }

   

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public int getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(int hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public BigDecimal getTienMat() {
        return tienMat;
    }

    public void setTienMat(BigDecimal tienMat) {
        this.tienMat = tienMat;
    }

   

    
    
    public Object[] getRowData(){
        return new Object[]{
                maHD,
                maNV,
                hoTen,
               hinhThucThanhToan == 0?"Đưa tiền mặt":"Chuyển khoản",
               new SimpleDateFormat("dd/MM/yyyy").format(ngayThanhToan),
                tongTien
                
        };
    }

    @Override
    public String toString() {
        return "HoaDonViewModel{" + "maHD=" + maHD + ", maNV=" + maNV + ", hoTen=" + hoTen + ", ngayThanhToan=" + ngayThanhToan + ", hinhThucThanhToan=" + hinhThucThanhToan + ", tongTien=" + tongTien + ", tienMat=" + tienMat + '}';
    }
    
    
}
