/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import view.viewQLKhuyenMai;

/**
 *
 * @author NGUYEN VAN HOI
 */
public class ThreadRunLoadTableBiaKM_Hoi implements Runnable {

    private viewQLKhuyenMai view;

    public ThreadRunLoadTableBiaKM_Hoi(viewQLKhuyenMai view) {
        this.view = view;
    }

    @Override
    public void run() {
        while (true) {
           
            try {
                // Gọi lại phương thức loadTableBiaKM để cập nhật bảng
                view.loadTableBiaKM();
                Thread.sleep(60000); 
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
