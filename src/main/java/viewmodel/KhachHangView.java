/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author SoiDiCode
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KhachHangView {
    private String ma;
    private String hoTen;
    private String diaChi;
    private int gioiTinh;
    private String SDT;
    private BigDecimal tongTien;
    
    public Object[] getDataRow(int index){
        DecimalFormat df = new DecimalFormat("###");
        return new Object[]{
            index , ma , hoTen , diaChi , gioiTinh == 0?"Nữ":"Nam" , SDT , df.format(tongTien)
        };
    }
    
}
