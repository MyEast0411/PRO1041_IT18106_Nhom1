/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import domainModel.SanPhamChiTiet;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Bia_;
import model.LoaiSanPham_;
import model.NSX_;
import model.SanPhamChiTiet_;
import repositories.SPCTRepo;
import service.impl.BiaService;
import service.impl.LoaiSPService;
import service.impl.NSXService;
import service.impl.SanPhamChiTietService;
import viewmodel.SPCTBiaNsxLoaiSp;
import viewmodel.SPCTBiaNsxLoaiSpHiber;

/**
 * @author dongl
 */
public class viewQLSanPham extends javax.swing.JPanel {

    public SanPhamChiTietService spctSer;
    public BiaService biaSer;
    public LoaiSPService loaiSPSer;
    public NSXService nsxSer;
    public SPCTRepo cTRepo;

    public DefaultTableModel modelTable = new DefaultTableModel();
    public SanPhamChiTiet_ spct;
    int stt = 0;

    /**
     * Creates new form viewQLSanPham
     */
    public viewQLSanPham() {
        initComponents();
        spctSer = new SanPhamChiTietService();
        biaSer = new BiaService();
        loaiSPSer = new LoaiSPService();
        nsxSer = new NSXService();
        cTRepo = new SPCTRepo();
        loadCbbBia();
        loadCbbLoaiSP();
        loadCbbNSX();
        loadTable(spctSer.getAllSPCTBiaNsxLoaiSp());
    }

    public void loadCbbBia() {
        DefaultComboBoxModel modelCombo = new DefaultComboBoxModel();
        ArrayList<Bia_> liB = biaSer.getAllBia();
        for (Bia_ bia_ : liB) {
            modelCombo.addElement(bia_);
        }
        this.cbbBia.setModel(modelCombo);
    }

    public void loadCbbLoaiSP() {
        DefaultComboBoxModel modelCombo = new DefaultComboBoxModel();

        ArrayList<LoaiSanPham_> liB = loaiSPSer.getAllLoaiSP();

        for (LoaiSanPham_ loaiSanPham_ : liB) {
            modelCombo.addElement(loaiSanPham_);
        }
        this.cbbLoaiSP.setModel(modelCombo);
    }

    public void loadCbbNSX() {
        DefaultComboBoxModel modelCombo = new DefaultComboBoxModel();

        ArrayList<NSX_> liNSX = nsxSer.getAllNSX();
        for (NSX_ nsx_ : liNSX) {
            modelCombo.addElement(nsx_);
        }
        this.cbbNSX.setModel(modelCombo);

    }

    public void loadTable(ArrayList<SPCTBiaNsxLoaiSp> list) {
        // ArrayList<SPCTBiaNsxLoaiSp> li = spctSer.getAllSPCTBiaNsxLoaiSp();
        modelTable = (DefaultTableModel) tblQLSanPham.getModel();
        modelTable.setRowCount(0);
        stt = 1;
        for (SPCTBiaNsxLoaiSp s : list) {
            modelTable.addRow(new Object[]{
                stt,
                s.getMaSPCT(),
                s.getTenSP(),
                s.getLoaiSP(),
                s.getNsx(),
                s.getTheTich(),
                s.getNongDoCon(),
                s.getTrangThai() == 1 ? "Thùng" : "Lon",
                s.getSoLuongLonBia(),
                s.getSoLuongLonTon(),
                s.getGiaNhap(),
                s.getGiaBan(),
                s.getHanSD(),
                s.getTinhTrang() == 1 ? "Đang bán" : "Đã bán",});
            stt++;
        }
    }

    public SanPhamChiTiet_ getDataForm() throws ParseException {
        //  String maSP = txtMaSP.getText();
        String theTich = txtTheTich.getText();
        String nongDoCon = txtNongDoCon.getText();
        int sl = Integer.parseInt(txtSoLuong.getText());
        int slt = Integer.parseInt(txtSoLuongTon1.getText());
        String hanSD = txtHanSD.getText();
        String maSPCT = txtMaSPCT.getText();
        // String maBia = txtMaBia.getText();

        BigDecimal giaNhap = new BigDecimal(txtGiaNhap.getText());
        BigDecimal giaBan = new BigDecimal(txtGiaBan.getText());

        Bia_ idBia = (Bia_) this.cbbBia.getSelectedItem();
        LoaiSanPham_ idLoaisp = (LoaiSanPham_) this.cbbLoaiSP.getSelectedItem();
        NSX_ idNsx = (NSX_) this.cbbNSX.getSelectedItem();

        String bia = idBia.getId();
        String loaisp = idLoaisp.getId();
        String nsx = idNsx.getId();
        //System.out.println(bia);

        int tt = 1;
        if (this.rdoThung.isSelected() == true) {
            tt = 1;
        } else if (this.rdoLon.isSelected() == true) {
            tt = 0;
        }

        int tthai = 1;
        if (this.rdoDangBan.isSelected() == true) {
            tthai = 1;
        } else if (this.rdoDaBan.isSelected() == true) {
            tthai = 0;
        } else {
            tthai = 2;
        }
        if (theTich.trim().length() == 0
                | nongDoCon.trim().length() == 0
                | hanSD.trim().length() == 0
                | txtGiaBan.getText().trim().length() == 0
                | txtGiaNhap.getText().trim().length() == 0
                | theTich.trim().length() == 0
                | maSPCT.trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Không được để trống !");
            return null;
        }

        // SanPhamChiTiet_ sp = new SanPhamChiTiet_(spct.getId(), giaBan, giaNhap, hanSD, new Date(), nongDoCon, sl,  theTich, tt, tthai,  bia, loaisp, nsx);
        SanPhamChiTiet_ sp = new SanPhamChiTiet_(spct.getId(), maSPCT, giaBan, giaNhap, hanSD, new Date(), nongDoCon, sl, slt, hanSD, theTich, tt, tthai, nsx, bia, loaisp, nsx);
        return sp;

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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new swing.ButtonCustom();
        btnSua = new swing.ButtonCustom();
        btnClear = new swing.ButtonCustom();
        btnTach = new swing.ButtonCustom();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQLSanPham = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        rdoThung = new javax.swing.JRadioButton();
        rdoLon = new javax.swing.JRadioButton();
        rdoSPLoi = new javax.swing.JRadioButton();
        jLabel19 = new javax.swing.JLabel();
        rdoDangBan = new javax.swing.JRadioButton();
        rdoDaBan = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new swing.ButtonCustom();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtTheTich = new javax.swing.JTextField();
        txtNongDoCon = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtHanSD = new javax.swing.JTextField();
        txtGiaBan = new javax.swing.JTextField();
        txtGiaNhap = new javax.swing.JTextField();
        cbbBia = new javax.swing.JComboBox<>();
        cbbLoaiSP = new javax.swing.JComboBox<>();
        cbbNSX = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtSoLuongTon1 = new javax.swing.JTextField();
        txtMaSPCT = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1140, 821));

        jPanel3.setBackground(new java.awt.Color(252, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        jPanel3.setForeground(new java.awt.Color(153, 153, 153));

        btnThem.setBackground(new java.awt.Color(0, 204, 204));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 204, 204));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 204, 204));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear");
        btnClear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnTach.setBackground(new java.awt.Color(0, 204, 204));
        btnTach.setForeground(new java.awt.Color(255, 255, 255));
        btnTach.setText("Tách Thùng");
        btnTach.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTach, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnClear, btnSua, btnTach, btnThem});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(108, 108, 108)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(273, Short.MAX_VALUE)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnClear, btnSua, btnTach, btnThem});

        tblQLSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblQLSanPham.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblQLSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SPCT", "Tên SP", "Loại SP", "NSX", "Thể tích", "Nồng Độ Cồn", "Trạng thái", "Số lượng", "Số lượng tồn", "Giá nhập", "Giá bán", "Hạn SD", "Tình trạng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLSanPham.setRowHeight(40);
        tblQLSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblQLSanPham);

        jPanel1.setBackground(new java.awt.Color(252, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chi tiết bia", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 14))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1295, 709));

        jLabel21.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel21.setText("NSX");

        jLabel17.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel17.setText("Trạng thái");

        buttonGroup1.add(rdoThung);
        rdoThung.setFont(new java.awt.Font("Nunito", 1, 13)); // NOI18N
        rdoThung.setText("Thùng");
        rdoThung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoThungActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoLon);
        rdoLon.setFont(new java.awt.Font("Nunito", 1, 13)); // NOI18N
        rdoLon.setText("Lon");
        rdoLon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoLonActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoSPLoi);
        rdoSPLoi.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rdoSPLoi.setText("Sản phẩm lỗi");

        jLabel19.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel19.setText("Tình trạng");

        buttonGroup2.add(rdoDangBan);
        rdoDangBan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rdoDangBan.setText("Đang bán");
        rdoDangBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangBanActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoDaBan);
        rdoDaBan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        rdoDaBan.setText("Đã bán");
        rdoDaBan.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel13.setText("Loại SP");

        jLabel18.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel18.setText("Bia");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel10.setText("Giá bán");

        jLabel15.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel15.setText("Số lượng");

        jLabel11.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel11.setText("Thể tích");

        jLabel12.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel12.setText("Nồng Độ Cồn");

        jLabel16.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel16.setText("Hạn SD");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel20.setText("VND");

        txtTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        btnTimKiem.setBackground(new java.awt.Color(0, 204, 204));
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel22.setText("Giá nhập");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel23.setText("VND");

        jLabel24.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel24.setText("Mã SPCT");

        jLabel25.setFont(new java.awt.Font("Nunito", 1, 14)); // NOI18N
        jLabel25.setText("Số lượng tồn");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtHanSD, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(188, 188, 188)
                                .addComponent(jLabel8))
                            .addComponent(txtTheTich, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNongDoCon, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(1, 1, 1))
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(rdoThung))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rdoDangBan)))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rdoDaBan)
                                        .addGap(26, 26, 26)
                                        .addComponent(rdoSPLoi))
                                    .addComponent(rdoLon))
                                .addGap(210, 210, 210))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel21)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cbbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel10)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel18)
                                                        .addGap(58, 58, 58)))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cbbBia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel22)
                                                .addGap(207, 207, 207))
                                            .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel20))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(117, 117, 117)
                    .addComponent(txtSoLuongTon1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(782, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(7, 7, 7)
                                    .addComponent(jLabel22))
                                .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbBia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbbLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel20))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(cbbNSX, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoThung, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rdoLon)
                                    .addComponent(jLabel19))
                                .addGap(4, 4, 4))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTheTich, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtNongDoCon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtHanSD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(jLabel25)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17)
                                .addComponent(rdoDangBan))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rdoDaBan)
                                .addComponent(rdoSPLoi)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtMaSPCT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(7, 7, 7))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(269, Short.MAX_VALUE)
                    .addComponent(txtSoLuongTon1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(138, 138, 138)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbBia, txtGiaBan});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 951, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1054, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        SanPhamChiTiet_ sp = new SanPhamChiTiet_();
        try {
            sp = getDataForm();
        } catch (ParseException ex) {
            Logger.getLogger(viewQLSanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (sp == null) {
            return;
        }
        if (spctSer.addSPChiTiet(sp) != null) {
            JOptionPane.showMessageDialog(this, "Them thanh cong ");
            loadTable(spctSer.getAllSPCTBiaNsxLoaiSp());
        } else {
            JOptionPane.showMessageDialog(this, "Them that bai ");
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        int row = tblQLSanPham.getSelectedRow();
        if (row < 0) {
            return;
        }
        SanPhamChiTiet_ nv = new SanPhamChiTiet_();

        System.out.println(spct.getId());
        try {
            nv = getDataForm();
            nv.setId(spct.getId());
        } catch (ParseException ex) {
            Logger.getLogger(viewQLSanPham.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nv == null) {
            return;
        }
        if (spctSer.updateSPCT(nv) != null) {
            JOptionPane.showMessageDialog(this, "update thanh cong ");
            loadTable(spctSer.getAllSPCTBiaNsxLoaiSp());
        } else {
            JOptionPane.showMessageDialog(this, "update that bai ");
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        String tenSP = txtTimKiem.getText();
        ArrayList<SPCTBiaNsxLoaiSp> listSP = spctSer.TimKiemSPCTBiaNsxLoaiSp(tenSP);
        loadTable(spctSer.TimKiemSPCTBiaNsxLoaiSp(tenSP));
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void rdoDangBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDangBanActionPerformed

    private void rdoLonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoLonActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_rdoLonActionPerformed

    private void rdoThungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoThungActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_rdoThungActionPerformed

    private void tblQLSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLSanPhamMouseClicked
        // TODO add your handling code here:
        int row = tblQLSanPham.getSelectedRow();
        if (row < 0) {
            return;
        }
        String maspct = tblQLSanPham.getValueAt(row, 1).toString();
        // String maBia = tblQLSanPham.getValueAt(row, 2).toString();
        String tensp = tblQLSanPham.getValueAt(row, 2).toString();
        String loaisp = tblQLSanPham.getValueAt(row, 3).toString();
        String tenNSX = tblQLSanPham.getValueAt(row, 4).toString();
        String theTich = tblQLSanPham.getValueAt(row, 5).toString();
        String nongDoCon = tblQLSanPham.getValueAt(row, 6).toString();
        String trangThai = tblQLSanPham.getValueAt(row, 7).toString();
        String soLuongLonBia = tblQLSanPham.getValueAt(row, 8).toString();
        String soLuongTon = tblQLSanPham.getValueAt(row, 9).toString();
        String giaNhap = tblQLSanPham.getValueAt(row, 10).toString();
        String giaBan = tblQLSanPham.getValueAt(row, 11).toString();

        String hanSD = tblQLSanPham.getValueAt(row, 12).toString();

        String tt = tblQLSanPham.getValueAt(row, 13).toString();

        for (SanPhamChiTiet_ sanPhamChiTiet_ : spctSer.getAllSPCT()) {
            if (sanPhamChiTiet_.getMaSPCT().equals(maspct)) {
                spct = sanPhamChiTiet_;
                // System.out.println(spct.getId());
            }
        }
        //txtMaSP.setText(masp);
        txtTheTich.setText(theTich);
        txtNongDoCon.setText(nongDoCon);
        txtSoLuong.setText(soLuongLonBia);
        txtHanSD.setText(hanSD);

        txtGiaBan.setText(giaBan + "");
        txtGiaNhap.setText(giaNhap + "");
        // txtMaBia.setText(maBia);
        txtMaSPCT.setText(maspct);
        txtSoLuongTon1.setText(soLuongTon);

        if (tt.equals("Đang bán")) {
            this.rdoDangBan.setSelected(true);
        }
        if (tt.equals("Đã bán")) {
            this.rdoDaBan.setSelected(true);
        }
        if (tt.equals("Sản phẩm lỗi")) {
            this.rdoSPLoi.setSelected(true);
        }

        //
        if (trangThai.equals("Thùng")) {
            this.rdoThung.setSelected(true);
        }
        if (trangThai.equals("Lon")) {
            this.rdoLon.setSelected(true);
        }
        for (Bia_ bia_ : biaSer.getAllBia()) {
            if (bia_.getTen().equals(tensp)) {
                this.cbbBia.getModel().setSelectedItem(bia_);
            }
        }
        for (LoaiSanPham_ loaiSanPham_ : loaiSPSer.getAllLoaiSP()) {
            if (loaiSanPham_.getTen().equals(loaisp)) {
                cbbLoaiSP.getModel().setSelectedItem(loaiSanPham_);
            }
        }
        for (NSX_ n : nsxSer.getAllNSX()) {
            if (n.getTen().equals(tenNSX)) {
                cbbNSX.getModel().setSelectedItem(n);
            }
        }
        //this.cbbBia.getModel().setSelectedItem(tensp);
        //   cbbLoaiSP.getModel().setSelectedItem(loaisp);
        //  cbbNSX.getModel().setSelectedItem(tenNSX);


    }//GEN-LAST:event_tblQLSanPhamMouseClicked

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        txtGiaBan.setText("");
        txtGiaNhap.setText("");
        txtHanSD.setText("");
        txtNongDoCon.setText("");
        txtTheTich.setText("");

        txtSoLuong.setText("");
        txtSoLuongTon1.setText("");
        txtMaSPCT.setText("");
//        txtMaBia.setText("");

    }//GEN-LAST:event_btnClearActionPerformed

    private void btnTachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTachActionPerformed
        // Tach thung va chuyen so luon ton
        int row = tblQLSanPham.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "bạn chưa chọn thùng để tách !!!");
            return;
        }
        if (tblQLSanPham.getValueAt(row, 7).toString().equalsIgnoreCase("lon")) {
            JOptionPane.showMessageDialog(this, "Khong the tach lon");
            return;
        }

        String ma = tblQLSanPham.getValueAt(row, 1).toString();
        SanPhamChiTiet ctspExist = cTRepo.findByMa(ma);
        if (ctspExist == null) {
            return;
        }else if (ctspExist.getSoLuongTon() <= 0) {
            JOptionPane.showMessageDialog(this, "San pham da het hang , khoong the tach");
            return;
        }
        SanPhamChiTiet spctLon = cTRepo.findByMa(ctspExist.getMa()+"_lon");
        if (spctLon == null) {
            SanPhamChiTiet chiTiet = new SanPhamChiTiet();
            chiTiet.setMa(ctspExist.getMa()+"_lon");
            chiTiet.setBia(ctspExist.getBia());
            chiTiet.setGiaBan(ctspExist.getGiaBan());
            chiTiet.setGiaNhap(ctspExist.getGiaNhap());
            chiTiet.setHanSD(ctspExist.getHanSD());
            chiTiet.setLoaiSP(ctspExist.getLoaiSP());
            chiTiet.setNgaySanXuat(ctspExist.getNgaySanXuat());
            chiTiet.setNongDoCon(ctspExist.getNongDoCon());
            chiTiet.setNsx(ctspExist.getNsx());
            chiTiet.setSoLuongLonBia(1);
            chiTiet.setThanhPhan(ctspExist.getThanhPhan());
            chiTiet.setTheTich(ctspExist.getTheTich());
            chiTiet.setTinhTrang(1);
            chiTiet.setTrangThai(0);
            chiTiet.setXuatXu(ctspExist.getXuatXu());
            chiTiet.setSoLuongTon(24);
            System.out.println("insert");
            int choice = JOptionPane.showConfirmDialog(this, "Ban chac chan muon tach khong ?", "Warning",
                JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                boolean kq = cTRepo.insert(chiTiet);
                if (kq) {
                    ctspExist.setSoLuongTon(ctspExist.getSoLuongTon()-1);
                    boolean kqDelete = cTRepo.updateSL(ctspExist);
                    System.out.println(kqDelete);
                     loadTable(spctSer.getAllSPCTBiaNsxLoaiSp());
                }
            }
        }else{
            System.out.println("update");
            int choice = JOptionPane.showConfirmDialog(this, "Ban chac chan muon tach khong ?", "Warning",
                JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                spctLon.setSoLuongTon(spctLon.getSoLuongTon() + 24 );
                boolean kqUpdate = cTRepo.updateSL(spctLon);
                ctspExist.setSoLuongTon(ctspExist.getSoLuongTon()-1);
                boolean kqDelete = cTRepo.updateSL(ctspExist);

                 loadTable(spctSer.getAllSPCTBiaNsxLoaiSp());
            }

        }

        //        int row = tblCTSP.getSelectedRow();
        //        int sl;
        //        if (row == -1) {
            //            JOptionPane.showMessageDialog(this, "bạn chưa chọn thùng để tách !!!");
            //            return;
            //        }
        //        String ma = tblCTSP.getValueAt(row, 1).toString();
        //        SanPhamChiTiet ctspExist = cTRepo.findByMa(ma);
        //        if (ctspExist == null) {
            //            return;
            //        }

        //        String input = JOptionPane.showInputDialog("Moi ban nhap so luong muon lay tu thung(Sl <24 va sl >0) : ");
        //        if (input == null) {
            //            JOptionPane.showMessageDialog(this, "Ban chua nhap so luong");
            //            return;
            //        }
        //
        //        try {
            //            sl = Integer.parseInt(input.trim());
            //            if (sl >= 24 || sl <= 0) {
                //                JOptionPane.showMessageDialog(this, "So luong phai > 0 va < 24");
                //                return;
                //            }
            //        } catch (Exception e) {
            //            JOptionPane.showMessageDialog(this, "Ban phai nhap so ");
            //            return;
            //        }

        //        List<SanPhamChiTiet> list = new ArrayList<>();
        //        for (int i = 0; i < 24; i++) {
            //            SanPhamChiTiet chiTiet = new SanPhamChiTiet();
            //            chiTiet.setMa(tblCTSP.getValueAt(row, 1).toString() + "_" + (i + 1));

            //        // lay bia
            //        Bia bia = (Bia) tblCTSP.getValueAt(row, 2);
            //        chiTiet.setBia(bia);
            ////        System.out.println("Bia " + bia.toString());
            // //       chiTiet.setBia();
            //
            //       // lay laoi sp
            //        LoaiSP loaiSP =  (LoaiSP) tblCTSP.getValueAt(row, 3);
            //        chiTiet.setLoaiSP(loaiSP);
            ////        System.out.println("Loai sp " + loaiSP.toString());
            //        // lay NSX
            //            NSX nsx = (NSX) tblCTSP.getValueAt(row, 4);
            //        chiTiet.setNsx(nsx);
            //        // lay the tich
            //        chiTiet.setTheTich(tblCTSP.getValueAt(row, 5).toString());
            //
            //        // lay nong do con
            //        chiTiet.setNongDoCon(tblCTSP.getValueAt(row,6).toString());
            //
            //        // lay trangThai
            //        chiTiet.setTrangThai(0);
            //
            //        // lay soLuongLonBia
            //        chiTiet.setSoLuongLonBia("1 lon");
            //
            //        // lay giaNhap
            //
            //        chiTiet.setGiaNhap(new BigDecimal(tblCTSP.getValueAt(row, 9).toString()));
            //        // lay giaBan
            //
            //        chiTiet.setGiaBan(new BigDecimal(tblCTSP.getValueAt(row, 10).toString()));
            //        // lay hanSD
            //
            //        chiTiet.setHanSD(tblCTSP.getValueAt(row, 11).toString());
            //        // lay xuatXu
            //
            //        chiTiet.setXuatXu(tblCTSP.getValueAt(row, 12).toString());
            //        // lay thanhPhan
            //
            //        chiTiet.setThanhPhan(tblCTSP.getValueAt(row, 13).toString());
            //        // lay tinhTrang
            //
            //        chiTiet.setTinhTrang(1);
            //            chiTiet.setBia(ctspExist.getBia());
            //            chiTiet.setGiaBan(ctspExist.getGiaBan());
            //            chiTiet.setGiaNhap(ctspExist.getGiaNhap());
            //            chiTiet.setHanSD(ctspExist.getHanSD());
            //            chiTiet.setLoaiSP(ctspExist.getLoaiSP());
            //            chiTiet.setNgaySanXuat(ctspExist.getNgaySanXuat());
            //            chiTiet.setNongDoCon(ctspExist.getNongDoCon());
            //            chiTiet.setNsx(ctspExist.getNsx());
            //            chiTiet.setSoLuongLonBia(1);
            //            chiTiet.setThanhPhan(ctspExist.getThanhPhan());
            //            chiTiet.setTheTich(ctspExist.getTheTich());
            //            chiTiet.setTinhTrang(0);
            //            chiTiet.setTrangThai(ctspExist.getTrangThai());
            //            chiTiet.setXuatXu(ctspExist.getXuatXu());
            //            list.add(chiTiet);
            //        }
        //        for (SanPhamChiTiet s : list) {
            //            System.out.println(s.toString());
            //        }
        //        Bia bia = (Bia) tblCTSP.getValueAt(row, 2);
        //        LoaiSP loaiSP = (LoaiSP) tblCTSP.getValueAt(row, 3);
        //        String tinhTrang = tblCTSP.getValueAt(row, 7).toString();
        //        int soLuongSp = cTRepo.getSlLon(bia, loaiSP, tinhTrang);
        //        JOptionPane.showMessageDialog(this, "So luong " + soLuongSp);
        //        int choice = JOptionPane.showConfirmDialog(this, "Ban chac chan muon tach khong ?", "Warning",
            //                JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        //        if (choice == JOptionPane.YES_OPTION) {
            //            boolean kq = cTRepo.insertList(list);
            //            if (kq) {
                //                 boolean kqDelete = cTRepo.delete(ctspExist);
                //                                System.out.println(kqDelete);
                //                                loadTable(cTRepo.getAll());
                //            }
            //        }

    }//GEN-LAST:event_btnTachActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ButtonCustom btnClear;
    private swing.ButtonCustom btnSua;
    private swing.ButtonCustom btnTach;
    private swing.ButtonCustom btnThem;
    private swing.ButtonCustom btnTimKiem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbbBia;
    private javax.swing.JComboBox<String> cbbLoaiSP;
    private javax.swing.JComboBox<String> cbbNSX;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoDaBan;
    private javax.swing.JRadioButton rdoDangBan;
    private javax.swing.JRadioButton rdoLon;
    private javax.swing.JRadioButton rdoSPLoi;
    private javax.swing.JRadioButton rdoThung;
    private javax.swing.JTable tblQLSanPham;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtHanSD;
    private javax.swing.JTextField txtMaSPCT;
    private javax.swing.JTextField txtNongDoCon;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtSoLuongTon1;
    private javax.swing.JTextField txtTheTich;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
