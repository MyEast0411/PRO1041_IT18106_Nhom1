/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domainModel;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "doi_tra_bia")

public class DoiTraBia {
    
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    
    @OneToOne
    @JoinColumn(name = "id_hoa_don")
    private HoaDon idHoaDon;
    
    @Column(name = "ngay_doi_tra")
    private Date ngayDoiTra;
    
    @Column(name = "hinh_thuc")
    private String hinhThuc;
    
    @Column(name = "ly_do")
    private String lyDo;
}
