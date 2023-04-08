/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import java.util.ArrayList;
import model.NSX_;
import repositories.NSXResponsitory;
import service.NSXInterface;

/**
 *
 * @author Admin
 */
public class NSXService implements NSXInterface {

    public NSXResponsitory nsxRes;

    public NSXService() {
        nsxRes = new NSXResponsitory();
    }

    @Override
    public ArrayList<NSX_> getAllNSX() {
        return nsxRes.getAllNSX();
    }

    @Override
    public Boolean addNSX(NSX_ nsx) {
       return nsxRes.addNSX(nsx);
    }

    @Override
    public Boolean updateNSX(NSX_ nsx) {
       return nsxRes.updateNSX(nsx);
    }

    @Override
    public Boolean deleteNSX(String ma) {
       return nsxRes.deleteNSX(ma);
    }

    @Override
    public ArrayList<NSX_> TimKiemTheoTenSP(String tenSP) {
       return nsxRes.TimKiemTheoTenSP(tenSP);
    }

}
