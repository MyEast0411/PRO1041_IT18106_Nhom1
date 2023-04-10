/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views;

import application.Main;
import domainModel.HoaDon;
import domainModel.HoaDonChiTiet;
import java.awt.Desktop;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import service.ServiceHoaDon;
import service.ServiceHoaDonChiTiet;
import service.impl.ServiceHoaDonChiTietImpl;
import service.impl.ServiceHoaDonImpl;
import swing.ThongBao;
import view.viewBanHang;

/**
 *
 * @author dongl
 */
public class viewLichSu extends javax.swing.JFrame {

    /**
     * Creates new form viewHoaDonCho
     */
    ServiceHoaDon ss = new ServiceHoaDonImpl();
    ServiceHoaDonChiTiet ssHDCT = new ServiceHoaDonChiTietImpl();
    public HoaDon hd = new HoaDon();
    Integer stt = 1;
    TrangChu trangChu = new TrangChu(null);
    DecimalFormat df = new DecimalFormat("#,###");
    DefaultTableModel dtm = new DefaultTableModel();

    public viewLichSu() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.loadTable();
        dtm = (DefaultTableModel) this.tblHoaDon.getModel();
        dtm.setRowCount(0);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        int stt = 1;
        String[] columnNames = {"STT", "Mã hóa đơn", "Ngày thanh toán", "Số lượng sản phẩm", "Tình trạng"};
        dtm.setColumnIdentifiers(columnNames);
        for (HoaDon hd : ss.getListHdDaTT()) {
            Integer soLuong = 0;
            for (HoaDonChiTiet x : ssHDCT.getListByMaHD(hd.getMa())) {
                soLuong += x.getSoLuong();
            }
            dtm.addRow(new Object[]{
                stt++, hd.getMa(), format.format(hd.getNgayThanhToan()), soLuong, "Đã thanh toán"
            });
        }
    }

    public void loadHoaDon(HoaDon hd) {
        viewBanHang x = new viewBanHang(null);
        int soThuTu = 1;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        x.lblMaHD.setText(hd.getMa());
        x.lblTongTien.setText(hd.getTongTien() + "");
        x.txtSDT.setText(hd.getKhachHang() == null ? "" : hd.getKhachHang().getSdt());
        x.txtTenKH.setText(hd.getKhachHang() == null ? "" : hd.getKhachHang().getHoTen());
        x.txtNgayTao.setText(format.format(hd.getNgayTao()));
        //x.txtNgayThanhToan.setText(format.format(hd.getNgayThanhToan()));

        if (hd.getHinhThucThanhToan() != null) {
            //0 là tiền mặt
            if (hd.getHinhThucThanhToan() == 0) {
                x.cboHTTT.setSelectedIndex(0);
                x.txtChuyenKhoan.setText("");
                x.txtTienMat.setText(df.format(hd.getTienMat() == null ? new BigDecimal(0) : hd.getTienMat()));
            } else if (hd.getHinhThucThanhToan() == 1) { //1 là chuyển khoản
                x.cboHTTT.setSelectedIndex(1);
                x.txtTienMat.setText("");
                x.txtChuyenKhoan.setText(df.format(hd.getTienChuyenKhoan() == null ? new BigDecimal(0) : hd.getTienChuyenKhoan()));
            } else if (hd.getHinhThucThanhToan() == 2) { //2 là kết hợp
                x.cboHTTT.setSelectedIndex(2);
                x.txtChuyenKhoan.setText(df.format(hd.getTienChuyenKhoan() == null ? new BigDecimal(0) : hd.getTienChuyenKhoan()));
                x.txtTienMat.setText(df.format(hd.getTienMat() == null ? new BigDecimal(0) : hd.getTienMat()));
            } else {
                x.txtTienMat.setText("");
                x.txtChuyenKhoan.setText("");
            }
        } else {

        }

        x.lblNhanVien.setText(hd.getNhanVien().getHoTen());

        x.dtm = (DefaultTableModel) x.tblHoaDon.getModel();
        x.dtm.setRowCount(0);
        BigDecimal donGia = new BigDecimal(0);
        for (HoaDonChiTiet hdct : ssHDCT.getListByMaHD(hd.getMa())) {
            if(hdct.getIdChiTietSanPham().getTrangThai() == 0){
                donGia = hdct.getDonGia();
            }else {
                donGia = hdct.getDonGia().multiply(new BigDecimal(24));
            }
            x.dtm.addRow(new Object[]{
                soThuTu++, hdct.getIdChiTietSanPham().getMa(), hdct.getIdChiTietSanPham().getBia().getTen(), hdct.getIdChiTietSanPham().getLoaiSP().getTen(),
                hdct.getIdChiTietSanPham().getTheTich(), hdct.getIdChiTietSanPham().getNongDoCon(), hdct.getIdChiTietSanPham().getTrangThai() == 0 ? "Lon" : "Thùng",
                hdct.getSoLuong(), df.format(donGia)
            });
        }
        x.loadTongTien();
        TrangChu.tc.pnlRight.removeAll();
        TrangChu.tc.pnlRight.add(x);
    }

    private void loadTable() {
        DefaultTableModel dtm = (DefaultTableModel) this.tblHoaDon.getModel();
        dtm.setRowCount(0);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        for (HoaDon hoaDon : ss.getListHDChuaTT()) {
            HoaDonChiTiet hdct = ssHDCT.getHoaDonCTByMaHD(hoaDon.getMa());
            dtm.addRow(new Object[]{
                stt++, hoaDon.getMa(), format.format(hoaDon.getNgayTao()), 1, hoaDon.getNhanVien().getHoTen()
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnXacNhan = new swing.ButtonCustom();
        jLabel1 = new javax.swing.JLabel();
        rdoDaTT = new javax.swing.JRadioButton();
        rdoDaHuy = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HĐ", "Ngày Thanh Toán", "Số lượng", "Nhân viên", "Tình Trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(40);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        btnXacNhan.setBackground(new java.awt.Color(255, 0, 0));
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Danh sách hóa đơn ");

        buttonGroup1.add(rdoDaTT);
        rdoDaTT.setSelected(true);
        rdoDaTT.setText("Đã thanh toán");
        rdoDaTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDaTTActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoDaHuy);
        rdoDaHuy.setText("Đã hủy");
        rdoDaHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDaHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoDaTT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoDaHuy)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoDaTT)
                    .addComponent(rdoDaHuy))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        // TODO add your handling code here:

        int row = this.tblHoaDon.getSelectedRow();
        if (row == -1) {
            ThongBao tb = new ThongBao(this, ThongBao.Type.WARNING, ThongBao.Location.TOP_CENTER, "Bạn chưa chọn hóa đơn");
            tb.showNotification();
            return;
        }
        String maHD = this.tblHoaDon.getValueAt(row, 1).toString();
        hd = ss.getHoaDonByMa(maHD);
        this.loadHoaDon(hd);

        this.setVisible(false);

//        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//        x.lblMaHD.setText(hd.getMa());
//        x.lblTongTien.setText(hd.getTongTien()+"");
//        x.txtTenKH.setText(hd.getKhachHang().getHoTen());
//        x.txtSDT.setText(hd.getKhachHang().getSdt());
//        x.txtNgayTao.setText(format.format(hd.getNgayTao()));
//        x.txtNgayThanhToan.setText(format.format(hd.getNgayThanhToan()));
//        x.lblNhanVien.setText(hd.getNhanVien().getHoTen());
//        
//        trangChu.pnlRight.removeAll();
//        trangChu.pnlRight.add(x);
//        trangChu.setVisible(true);
//        this.setVisible(false);
//        TrangChu.tc.setVisible(false);
        //System.exit(0);
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
//        viewBanHang x = new viewBanHang();
//        int row = this.tblHoaDon.getSelectedRow();
//        if(row == -1){
//            return;
//        }
//        String maHD = this.tblHoaDon.getValueAt(row, 1).toString();
//        HoaDon hd = ss.getHoaDonByMa(maHD);
//        x.loadHoaDon(hd);
//        this.setVisible(false);
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void rdoDaTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDaTTActionPerformed
        // TODO add your handling code here:
        dtm = (DefaultTableModel) this.tblHoaDon.getModel();
        dtm.setRowCount(0);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        int stt = 1;
        String[] columnNames = {"STT", "Mã hóa đơn", "Ngày thanh toán", "Số lượng sản phẩm", "Tình trạng"};
        dtm.setColumnIdentifiers(columnNames);
        for (HoaDon hd : ss.getListHdDaTT()) {
            Integer soLuong = 0;
            for (HoaDonChiTiet x : ssHDCT.getListByMaHD(hd.getMa())) {
                soLuong += x.getSoLuong();
            }
            dtm.addRow(new Object[]{
                stt++, hd.getMa(), format.format(hd.getNgayThanhToan()), soLuong, "Đã thanh toán"
            });
        }
    }//GEN-LAST:event_rdoDaTTActionPerformed

    private void rdoDaHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDaHuyActionPerformed
        // TODO add your handling code here:
        dtm = (DefaultTableModel) this.tblHoaDon.getModel();
        dtm.setRowCount(0);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        int stt = 1;
        String[] columnNames = {"STT", "Mã hóa đơn", "Ngày tạo", "Số lượng sản phẩm", "Tình trạng"};
        dtm.setColumnIdentifiers(columnNames);
        for (HoaDon hd : ss.getListHdDaHuy()) {
            Integer soLuong = 0;
            for (HoaDonChiTiet x : ssHDCT.getListByMaHD(hd.getMa())) {
                soLuong += x.getSoLuong();
            }
            dtm.addRow(new Object[]{
                stt++, hd.getMa(), format.format(hd.getNgayTao()), soLuong, "Đã hủy"
            });
        }
    }//GEN-LAST:event_rdoDaHuyActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(viewLichSu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewLichSu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewLichSu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewLichSu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewLichSu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ButtonCustom btnXacNhan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoDaHuy;
    private javax.swing.JRadioButton rdoDaTT;
    private javax.swing.JTable tblHoaDon;
    // End of variables declaration//GEN-END:variables
}
