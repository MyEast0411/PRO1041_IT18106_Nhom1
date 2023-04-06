package domainModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "san_pham_chi_tiet")
public class SanPhamChiTiet implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "ma")
    private String ma;

    @ManyToOne
    @JoinColumn(name = "id_nsx")
    private NSX nsx;

    @ManyToOne
    @JoinColumn(name = "id_bia")
    private Bia bia;

    @ManyToOne
    @JoinColumn(name = "id_loai_san_pham")
    private LoaiSP loaiSP;

    @Column(name = "tinh_trang")
    private Integer tinhTrang;

    @Column(name = "gia_nhap")
    private BigDecimal giaNhap;

    @Column(name = "gia_ban")
    private BigDecimal giaBan;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "so_luong_lon_bia")
    private Integer soLuongLonBia;

    @Column(name = "xuat_xu")
    private String xuatXu;

    @Column(name = "the_tich")
    private String theTich;

    @Column(name = "nong_do_con")
    private String nongDoCon;

    @Column(name = "ngay_sx")
    private Date ngaySanXuat;

    @Column(name = "han_sd")
    private String hanSD;
    
    @Column(name="thanh_phan")
    private String thanhPhan;
    
    @Column(name="so_luong_ton")
    private Integer soLuongTon;

}
