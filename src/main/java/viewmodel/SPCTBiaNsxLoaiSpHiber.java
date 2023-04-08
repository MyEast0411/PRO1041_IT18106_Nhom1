/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import domainModel.Bia;
import domainModel.LoaiSP;
import domainModel.NSX;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Admin
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Data
public class SPCTBiaNsxLoaiSpHiber {
   private String maHDCT;
    private Bia bia;
    private LoaiSP loaiSP;
    private NSX nsx;
    private String theTich;
    private int trangThai;
    private BigDecimal giaBan;
    private int tinhTrang;
    private int soLuongTon;

    public Object[] getDataRow(int index) {
        DecimalFormat df = new DecimalFormat("#");
        BigDecimal giaThung = giaBan.multiply(new BigDecimal("24"));
        giaThung = giaThung.subtract(giaThung.multiply(new BigDecimal("0.05")));
         
        return new Object[]{
            index,
            maHDCT,
            bia,
            loaiSP,
            nsx,
            theTich,
            trangThai==1?"Thùng":"Lon",
            trangThai==1?df.format(giaThung):df.format(giaBan),
            tinhTrang == 1 ? "Còn":"Hết",
            soLuongTon== 0 ?"Hết hàng":soLuongTon
        };
    }

}
