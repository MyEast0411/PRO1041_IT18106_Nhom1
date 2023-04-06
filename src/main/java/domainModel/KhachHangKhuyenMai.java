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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "khach_hang_khuyen_mai")

public class KhachHangKhuyenMai implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "id_dot_khuyen_mai")
    private DotKhuyenMai khuyenMai;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "gia_con_lai")
    private BigDecimal giaConLai;

    @Column(name = "trang_thai")
    private Integer trangThai;
}
