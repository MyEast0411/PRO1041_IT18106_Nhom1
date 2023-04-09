/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainModel.NhanVien;
import java.util.List;

/**
 *
 * @author dongl
 */
public interface ServiceNhanVien {
    NhanVien getNhanVienByMa(String maNV);
    List<NhanVien> getList();
    Boolean insert(NhanVien nv);
    Boolean update(NhanVien nv,String maNV);
    List<NhanVien> getListByTT(Integer tt);
    List<NhanVien> getListByTen(String ten);
    NhanVien getNhanVienByTenDN(String text);
}
