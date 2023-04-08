/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;


import model.SanPhamChiTiet_;
import repositories.SanPhamChiTietResponsitory;
import service.ServiceSanPhamChiTiet;

/**
 *
 * @author dongl
 */
public class ServiceSanPhamChiTietImpl implements ServiceSanPhamChiTiet{
    private SanPhamChiTietResponsitory repo = new SanPhamChiTietResponsitory();
    @Override
    public SanPhamChiTiet_ getSPCTByMa(String ma) {
        return repo.getSPCTByMa(ma);
    }

    @Override
    public Boolean updateSoLuong(SanPhamChiTiet_ x) {
        return repo.updateSoLuong(x);
    }

    
    
}
