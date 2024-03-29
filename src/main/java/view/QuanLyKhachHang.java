/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import domainModel.KhachHang;
import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import repositories.KhachHangRepository;
import service.impl.ServiceKhachHangImpl;
import utilities.Ex_Im_ExcelKH;
import viewmodel.KhachHangView;


public class QuanLyKhachHang extends javax.swing.JPanel {

    private DefaultTableModel khachHangModel;
    private DefaultTableModel hD_KH_Model;
    private ServiceKhachHangImpl serviceKhachHangImpl;

    /**
     * Creates new form QuanLyKhachHang
     */
    public QuanLyKhachHang() {
        initComponents();
        khachHangModel = new DefaultTableModel();
        serviceKhachHangImpl = new ServiceKhachHangImpl();
        hD_KH_Model = new DefaultTableModel();
        loadTable(serviceKhachHangImpl.getLoad());
    }

    public void showForm(String ma, String hoTen, String diaChi, String SDT, String gioiTinh) {
        txtMa.setText(ma);
        txtHoTen.setText(hoTen);
        txtSDT.setText(SDT);
        txtDiaChi.setText(diaChi);
        if (gioiTinh.equalsIgnoreCase("Nữ")) {
//            rdoNam.setEnabled(false);
            rdoNu.setSelected(true);

        } else {
            rdoNam.setSelected(true);
//             rdoNu.setEnabled(false);
        }
    }

    public void loadTable(List<KhachHangView> list) {
        khachHangModel = (DefaultTableModel) tblKhachHang.getModel();
        khachHangModel.setRowCount(0);
        int index = 0;
        for (KhachHangView s : list) {
            index++;
            khachHangModel.addRow(s.getDataRow(index));
        }
    }


    public KhachHang getDataForm() {
        StringBuilder sb = new StringBuilder();

        if (txtDiaChi.getText().trim().length() == 0) {
            sb.append("Địa chỉ không để trống\n");
        }
        if (txtSDT.getText().trim().length() == 0) {
            sb.append("SDT không để trống\n");
        } else if (!txtSDT.getText().trim().matches("\\d{10}")) {
            sb.append("SDT phải 10 kí tự số \n");
        }

        if (txtHoTen.getText().trim().length() == 0) {
            sb.append("Họ tên không để trống\n");
        }

        if (sb.length() > 0) {
            JOptionPane.showMessageDialog(this, sb.toString());
            return null;
        }

        int gioiTinh = rdoNam.isSelected() ? 1 : 0;

        return new KhachHang(null, serviceKhachHangImpl.renderMa(),
                txtHoTen.getText().trim(),
                gioiTinh,
                txtSDT.getText().trim(),
                txtDiaChi.getText().trim(),
                0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        btnEdit = new swing.ButtonCustom();
        btnThem = new swing.ButtonCustom();
        btnReset = new swing.ButtonCustom();
        btnXuatDS = new swing.ButtonCustom();
        btnNhapDS = new swing.ButtonCustom();
        txtTimKiem = new javax.swing.JTextField();
        cbbTinhTien = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel9.setFont(new java.awt.Font("Nunito", 1, 18)); // NOI18N
        jLabel9.setText("Mã khách hàng");

        jLabel12.setFont(new java.awt.Font("Nunito", 1, 18)); // NOI18N
        jLabel12.setText("Địa chỉ");

        jLabel11.setFont(new java.awt.Font("Nunito", 1, 18)); // NOI18N
        jLabel11.setText("Họ tên");

        txtMa.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Nunito", 1, 18)); // NOI18N
        jLabel15.setText("Giới tính");

        jLabel13.setFont(new java.awt.Font("Nunito", 1, 18)); // NOI18N
        jLabel13.setText("SĐT");

        rdoNam.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        rdoNu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rdoNu.setText("Nữ");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnEdit.setBackground(new java.awt.Color(0, 204, 204));
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setText("Sửa");
        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(0, 204, 204));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(0, 204, 204));
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("Làm mới");
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnXuatDS.setBackground(new java.awt.Color(0, 204, 204));
        btnXuatDS.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatDS.setText("Xuất DS");
        btnXuatDS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXuatDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDSActionPerformed(evt);
            }
        });

        btnNhapDS.setBackground(new java.awt.Color(0, 204, 204));
        btnNhapDS.setForeground(new java.awt.Color(255, 255, 255));
        btnNhapDS.setText("Nhập DS");
        btnNhapDS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnNhapDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapDSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXuatDS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNhapDS, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(btnXuatDS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(btnNhapDS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel15))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(148, 148, 148)
                                        .addComponent(rdoNam)
                                        .addGap(54, 54, 54)
                                        .addComponent(rdoNu)
                                        .addGap(0, 62, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                                    .addComponent(txtSDT)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(rdoNu)
                            .addComponent(rdoNam)))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        txtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemCaretUpdate(evt);
            }
        });

        cbbTinhTien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sắp xếp tiền giảm", "Sắp xếp tiền tăng" }));
        cbbTinhTien.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbTinhTienItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(548, 548, 548)
                        .addComponent(cbbTinhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbbTinhTien)
                        .addGap(2, 2, 2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Nunito", 0, 16))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KH", "Họ tên", "Địa chỉ", "Giới tính", "SĐT", "Tổng Tiền Đã Mua"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.setRowHeight(40);
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 1040, 190));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 1068, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 32, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        KhachHang kh = this.getDataForm();
        int row = tblKhachHang.getSelectedRow();
        if (row == -1) {
            return;
        }
        if (kh == null) {
            return;
        }
        //        for (KhachHang s : serviceKhachHangImpl.getAll()) {
        //            if (s.getSdt().equalsIgnoreCase(kh.getSdt())) {
        //                JOptionPane.showMessageDialog(this, "SDT đã tồn tại" , "Cảnh báo" , JOptionPane.ERROR_MESSAGE);
        //                return;
        //            }
        //        }
        //

        int choice = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa khách hàng chứ ?",
                "Thông báo", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            String ma = tblKhachHang.getValueAt(row, 0).toString();
            boolean kq = serviceKhachHangImpl.update(ma, kh);
            if (kq) {
                JOptionPane.showMessageDialog(this, "Update successfull !!");
                loadTable(serviceKhachHangImpl.getLoad());
            } else {
                JOptionPane.showMessageDialog(this, "Update fail !!!");
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        KhachHang kh = this.getDataForm();
        if (kh == null) {
            return;
        }
        for (KhachHang s : serviceKhachHangImpl.getAll()) {
            if (s.getSdt().equalsIgnoreCase(kh.getSdt())) {
                JOptionPane.showMessageDialog(this, "SDT đã tồn tại", "Cảnh báo", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int choice = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm khách hàng chứ ?", "Thông báo", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            boolean kq = serviceKhachHangImpl.save(kh);
            if (kq) {
                JOptionPane.showMessageDialog(this, "Add successfull !!");
                loadTable(serviceKhachHangImpl.getLoad());
            } else {
                JOptionPane.showMessageDialog(this, "Add fail !! ");
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        this.showForm("", "", "", "", "Nam");
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnXuatDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDSActionPerformed
        // TODO add your handling code here:
        List<KhachHang> list = serviceKhachHangImpl.getAll();
        String[] nameColumn = new String[]{"STT", "Mã KH", "Họ Tên", "Địa Chỉ", "SDT", "Giới Tính", "Điểm Số"};
        String nameSheet = "KhachHang";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        String time = sdf.format(date);
        String ngay = date.getHours() + date.getMinutes() + date.getSeconds() + time;
        String nameFile = "D:\\excelKH\\DanhSachKh" + ngay + ".xlsx";
        Ex_Im_ExcelKH.export(list, nameColumn, nameSheet, nameFile);
        int choice = JOptionPane.showConfirmDialog(this, "Ban co muon mo file vua luu khong ?", "Title", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                File file = new File(nameFile);
                Desktop.getDesktop().open(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_btnXuatDSActionPerformed

    private void btnNhapDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapDSActionPerformed
        // TODO add your handling code here:
        List<KhachHang> list = serviceKhachHangImpl.importExcel();

        if (!list.isEmpty()) {
            new KhachHangRepository().insertAll(list);
        }
            loadTable(serviceKhachHangImpl.getLoad());
        
    }//GEN-LAST:event_btnNhapDSActionPerformed

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        // TODO add your handling code here:
         List<KhachHangView> listSDT = new ArrayList<>();
        if (txtTimKiem.getText().trim().length() == 0) {
            listSDT = serviceKhachHangImpl.getLoad();
        }else{
            listSDT = serviceKhachHangImpl.getKHBySDT(txtTimKiem.getText());
        }
        
       loadTable(listSDT);
    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        // TODO add your handling code here:
        int row = tblKhachHang.getSelectedRow();
        if (row == -1) {
            return;
        }
        this.showForm(
                tblKhachHang.getValueAt(row, 0).toString(),
                tblKhachHang.getValueAt(row, 1).toString(),
                tblKhachHang.getValueAt(row, 2).toString(),
                tblKhachHang.getValueAt(row, 4).toString(),
                tblKhachHang.getValueAt(row, 3).toString()
        );
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void cbbTinhTienItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbTinhTienItemStateChanged
        // TODO add your handling code here:
        List<KhachHangView> kh = new ArrayList<>();
        if (cbbTinhTien.getSelectedItem().equals("Sắp xếp tiền giảm")) {
            kh = serviceKhachHangImpl.getLoadCBB();
             
        }else{
           kh = serviceKhachHangImpl.getLoad();
        }
        loadTable(kh);
    }//GEN-LAST:event_cbbTinhTienItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ButtonCustom btnEdit;
    private swing.ButtonCustom btnNhapDS;
    private swing.ButtonCustom btnReset;
    private swing.ButtonCustom btnThem;
    private swing.ButtonCustom btnXuatDS;
    private javax.swing.JComboBox<String> cbbTinhTien;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
