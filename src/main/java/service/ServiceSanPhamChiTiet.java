/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainModel.SanPhamChiTiet;

/**
 *
 * @author dongl
 */
public interface ServiceSanPhamChiTiet {
    SanPhamChiTiet getSPCTByMa(String ma);
    Boolean updateSoLuong(SanPhamChiTiet x);
}
