/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainModel;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author dongl
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonChiTietId implements Serializable{
    private UUID IdHoaDon;
    
    private UUID IdChiTietSanPham;

}
