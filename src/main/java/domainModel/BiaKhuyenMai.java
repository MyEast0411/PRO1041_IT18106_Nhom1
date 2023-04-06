/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainModel;

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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "bia_khuyen_mai")
@IdClass(BiaKhuyenMaiId.class)
public class BiaKhuyenMai {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_dot_khuyen_mai")
    private DotKhuyenMai khuyenMai;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_san_pham_chi_tiet")
    private SanPhamChiTiet chiTietSanPham;

    @Column(name = "don_gia")
    private BigDecimal donGia;

    @Column(name = "gia_con_lai")
    private BigDecimal giaConLai;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
