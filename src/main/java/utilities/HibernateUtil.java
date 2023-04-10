/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import domainModel.Bia;
import domainModel.BiaKhuyenMai;
import domainModel.DoiTraBia;
import domainModel.DotKhuyenMai;
import domainModel.HoaDon;
import domainModel.HoaDonChiTiet;
import domainModel.KhachHang;
import domainModel.KhachHangKhuyenMai;
import domainModel.KhachHangVoucher;
import domainModel.LoaiSP;
import domainModel.NSX;
import domainModel.NSXKhuyenMai;
import domainModel.NhanVien;
import domainModel.SanPhamChiTiet;
import domainModel.Voucher;
import java.util.Properties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author dongl
 */
public class HibernateUtil {
    private static final SessionFactory FACTORY;
    private static Session SESSION;

    static {
        Configuration conf = new Configuration();

        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.SQLServerDialect");
        properties.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        properties.put(Environment.URL, "jdbc:sqlserver://localhost:1433;databaseName=DuAn1Nhom1;trustServerCertificate=true;");
        properties.put(Environment.USER, "sa");
        properties.put(Environment.PASS, "0123");
        properties.put(Environment.SHOW_SQL, "true");
        
        //gen db -- bỏ cmt rồi chạy 1 lần 
        properties.put(Environment.HBM2DDL_AUTO, "update");

        conf.addAnnotatedClass(Bia.class);
        conf.addAnnotatedClass(LoaiSP.class);
        conf.addAnnotatedClass(NSX.class);
        conf.addAnnotatedClass(SanPhamChiTiet.class);
        conf.addAnnotatedClass(DotKhuyenMai.class);
        conf.addAnnotatedClass(BiaKhuyenMai.class);
        conf.addAnnotatedClass(KhachHang.class);
        conf.addAnnotatedClass(NhanVien.class);
        conf.addAnnotatedClass(HoaDon.class);
        conf.addAnnotatedClass(HoaDonChiTiet.class);
        conf.addAnnotatedClass(Voucher.class);
        conf.addAnnotatedClass(KhachHangVoucher.class);
        conf.addAnnotatedClass(NSXKhuyenMai.class);
        conf.addAnnotatedClass(KhachHangKhuyenMai.class);
        conf.addAnnotatedClass(DoiTraBia.class);

        conf.setProperties(properties);

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(registry);

    }

    public static Session getSession() {
        if (SESSION == null || !SESSION.isConnected()) {
            SESSION = FACTORY.openSession();
        }
        return SESSION;
    }

    public static void main(String[] args) {
        SESSION = FACTORY.openSession();
        SESSION.close();
        System.out.println(SESSION.isConnected());
    }

}
