/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domainModel.KhachHang;
import java.util.List;

/**
 *
 * @author SoiDiCode
 */
public interface ServiceKhachHang<T> {
    Boolean save(T object);
    Boolean update(String ma , T object);
    KhachHang getBySDT(String sdt);
    List<T> getAll();
}
