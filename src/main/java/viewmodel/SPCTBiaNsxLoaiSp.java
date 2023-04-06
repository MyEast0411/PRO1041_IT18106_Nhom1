/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import domainModel.Bia;
import domainModel.LoaiSP;
import domainModel.NSX;
import java.math.BigDecimal;
import java.util.Date;
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
public class SPCTBiaNsxLoaiSp {
   private String maHDCT;
    private Bia bia;
    private LoaiSP loaiSP;
    private NSX nsx;
    private String theTich;
    private String nongDoCon;
    private int trangThai;
    private String soLuongLonBia;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private String hanSD;
    private String xuatXu;
    private String thanhPhan;
    private int tinhTrang;

    public Object[] getDataRow(int index) {
        return new Object[]{
            index,
            maHDCT,
            bia,
            loaiSP,
            nsx,
            theTich,
            nongDoCon,
            tinhTrang==1?"Thùng":"Lon",
            soLuongLonBia.equalsIgnoreCase("24 Lon")?"1":"1",
            giaNhap,
            giaBan,
            hanSD,
            xuatXu,
            thanhPhan,
            trangThai == 1 ? "Còn":"Hết"
        };
    }

}
