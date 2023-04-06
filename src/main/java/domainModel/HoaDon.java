package domainModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hoa_don")
public class HoaDon implements Serializable{
    
    @Id   
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "ma")
    private String ma;
    
    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;
    
    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;
    
    @Column(name = "ngay_thanh_toan")
    private Date ngayThanhToan;
    
    @Column(name = "ngay_tao")
    private Date ngayTao;
    
    @Column(name = "hinh_thuc_thanh_toan")
    private Integer hinhThucThanhToan;
    
    @Column(name = "tong_tien")
    private BigDecimal tongTien;
    
    @Column(name = "tien_mat")
    private BigDecimal tienMat;
    
    @Column(name = "tien_chuyen_khoan")
    private BigDecimal tienChuyenKhoan;
    
    @Column(name = "tinh_trang")
    private Integer tinhTrang;
    
}
