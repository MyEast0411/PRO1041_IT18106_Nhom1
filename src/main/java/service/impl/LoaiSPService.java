/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.ArrayList;
import model.LoaiSanPham_;
import repositories.LoaiSanPhamResponsitory;
import service.LoaiSPInterface;

/**
 *
 * @author Admin
 */
public class LoaiSPService implements LoaiSPInterface {

    public LoaiSanPhamResponsitory loaiRes;

    public LoaiSPService() {
        loaiRes = new LoaiSanPhamResponsitory();
    }

    @Override
    public ArrayList<LoaiSanPham_> getAllLoaiSP() {
        return loaiRes.getAllLoaiSP();
    }

    @Override
    public Boolean addLSP(LoaiSanPham_ lsp) {
        return loaiRes.addLSP(lsp);
    }

    @Override
    public Boolean updateLSP(LoaiSanPham_ lsp) {
        return loaiRes.updateLSP(lsp);
    }

    @Override
    public Boolean deleteLSP(String ma) {
        return loaiRes.deleteLSP(ma);
    }

    @Override
    public ArrayList<LoaiSanPham_> TimKiemTheoTenSP(String tenSP) {
        return loaiRes.TimKiemTheoTenSP(tenSP);
    }

}
