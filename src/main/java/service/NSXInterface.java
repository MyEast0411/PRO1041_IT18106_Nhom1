/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.ArrayList;
import model.NSX_;

/**
 *
 * @author Admin
 */
public interface NSXInterface {
    public ArrayList<NSX_> getAllNSX();
    public Boolean addNSX(NSX_ nsx);
    public Boolean updateNSX(NSX_ nsx);
    public Boolean deleteNSX(String ma);
     public ArrayList<NSX_> TimKiemTheoTenSP(String tenSP);
}
