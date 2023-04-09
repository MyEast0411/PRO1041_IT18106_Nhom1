/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import domainModel.NhanVien;
import java.util.List;
import repositories.NhanVienRepository;
import service.ServiceNhanVien;

/**
 *
 * @author dongl
 */
public class ServiceNhanVienImpl implements ServiceNhanVien{
    NhanVienRepository repo = new NhanVienRepository();

    @Override
    public NhanVien getNhanVienByMa(String maNV) {
        return repo.getNhanVienByMa(maNV);
    }

    @Override
    public List<NhanVien> getList() {
        return repo.getList();
    }

    @Override
    public Boolean insert(NhanVien nv) {
        for (NhanVien x : repo.getList()) {
            if(x.getMa().equals(nv.getMa())){
                return false;
            }
        }
        return repo.insert(nv);
    }

    @Override
    public Boolean update(NhanVien nv, String maNV) {
        return repo.update(nv, maNV);
    }

    @Override
    public List<NhanVien> getListByTT(Integer tt) {
        return repo.getListByTT(tt);
    }

    @Override
    public List<NhanVien> getListByTen(String ten) {
        return repo.getListByTen(ten);
    }

    @Override
    public NhanVien getNhanVienByTenDN(String text) {
        return repo.getNhanVienByTenDN(text);
    }

}
