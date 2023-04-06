/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainModel;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author dongl
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "khach_hang_voucher")
@IdClass(KhachHangVoucherId.class)

public class KhachHangVoucher implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_voucher")
    private Voucher voucher;

}
