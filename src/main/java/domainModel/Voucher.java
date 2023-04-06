/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainModel;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author dongl
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="voucher")
public class Voucher implements Serializable{
    @Id
    @GeneratedValue
    @Column(name="id")
    private UUID id;
    
    @Column(name = "ma_voucher")
    private String maVoucher;
    
    @Column(name = "ten_voucher")
    private String tenVoucher;
    
    @Column(name = "hinh_thuc")
    private int hinhThuc;
    
    @Column(name = "giamGia")
    private String giamGia;
    
    @Column(name = "ngay_bat_dau")
    private Date ngayBatDau;
    
    @Column(name = "ngay_ket_thuc")
    private Date ngayKetThuc;
    
}
