/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.ArrayList;
import model.LoaiSanPham_;

/**
 *
 * @author Admin
 */
public interface LoaiSPInterface {

    public ArrayList<LoaiSanPham_> getAllLoaiSP();

    public Boolean addLSP(LoaiSanPham_ lsp);

    public Boolean updateLSP(LoaiSanPham_ lsp);

    public Boolean deleteLSP(String ma);
    public ArrayList<LoaiSanPham_> TimKiemTheoTenSP(String tenSP);
    ArrayList<LoaiSanPham_> getLoaiSP(String text);
}
