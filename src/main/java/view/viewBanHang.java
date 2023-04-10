/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import com.github.sarxos.webcam.Webcam;
import domainModel.HoaDon;
import domainModel.HoaDonChiTiet;
import domainModel.KhachHang;
import domainModel.NhanVien;
import domainModel.SanPhamChiTiet;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import repositories.SPCTRepo;
import service.ServiceHoaDon;
import service.ServiceHoaDonChiTiet;
import service.ServiceKhachHang;
import service.ServiceNhanVien;
import service.ServiceSanPhamChiTiet;
import service.impl.ServiceHoaDonChiTietImpl;
import service.impl.ServiceHoaDonImpl;
import service.impl.ServiceKhachHangImpl;
import service.impl.ServiceNhanVienImpl;
import service.impl.ServiceSanPhamChiTietImpl;
import utilities.ExportFilePdf;
import views.viewHoaDonCho;
import views.viewLichSu;
import views.viewLogin;
import com.google.zxing.MultiFormatReader;
import javax.swing.JFrame;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import service.ServiceDotKhuyenMai;
import service.impl.ServiceDotKhuyenMaiImpl;

/**
 *
 * @author dongl
 */
public class viewBanHang extends javax.swing.JPanel {

    /**
     * Creates new form viewBanHang
     */
    private ServiceDotKhuyenMai serviceDKM = new ServiceDotKhuyenMaiImpl();
    private ServiceHoaDon ssHD = new ServiceHoaDonImpl();
    private ServiceHoaDonChiTiet ssHDCT = new ServiceHoaDonChiTietImpl();
    // private ServiceSanPhamChiTiet ssSPCT = new ServiceSanPhamChiTietImpl();
    private SPCTRepo ssSPCT = new SPCTRepo();
    public DefaultTableModel dtm = new DefaultTableModel();
    private Integer stt = 1;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    NhanVien nv = new NhanVien();
    DecimalFormat df = new DecimalFormat("#,###");
    DecimalFormat df1 = new DecimalFormat();
    BigDecimal tt = new BigDecimal(0);

    public viewBanHang(NhanVien nhanVien) {
        initComponents();
        tblHoaDon.getTableHeader().setBackground(Color.WHITE);
        nv = nhanVien;
        this.txtChuyenKhoan.setEnabled(false);
    }

    public void loadTable(List<HoaDonChiTiet> list) {
        dtm = (DefaultTableModel) this.tblHoaDon.getModel();
        dtm.setRowCount(0);
        for (HoaDonChiTiet x : list) {
            dtm.addRow(new Object[]{
                stt++, x.getIdChiTietSanPham().getMa(), x.getIdChiTietSanPham().getBia().getTen(), x.getIdChiTietSanPham().getLoaiSP().getTen(),
                x.getIdChiTietSanPham().getTheTich(), x.getIdChiTietSanPham().getNongDoCon(), x.getIdChiTietSanPham().getTrangThai() == 0 ? "Lon" : "Thùng",
                x.getSoLuong(), df.format(x.getDonGia())
            });
        }
    }

    private HoaDon getFormHoaDon() throws ParseException {
        ServiceKhachHangImpl ss = new ServiceKhachHangImpl();
        KhachHang khachHang = new ServiceKhachHangImpl().getBySDT(txtSDT.getText().trim());
        KhachHang kh = new KhachHang();
        if (khachHang == null) {
            kh.setSdt(txtSDT.getText().trim());
            kh.setHoTen(txtTenKH.getText().trim());
            ss.save(kh);
            NhanVien nvien = viewLogin.nv;
            return new HoaDon(ssHD.getHoaDonByMa(lblMaHD.getText().trim()).getId(), lblMaHD.getText(), ss.getBySDT(kh.getSdt()), nvien, null,
                    null, null, null, null, null, 0);
        }
        khachHang.setSdt(txtSDT.getText().trim());
        khachHang.setHoTen(txtTenKH.getText().trim());

        NhanVien nvien = viewLogin.nv;
        return new HoaDon(ssHD.getHoaDonByMa(lblMaHD.getText().trim()).getId(), lblMaHD.getText(), khachHang, nvien, null,
                null, null, null, null, null, 0);
    }

    private void clearHoaDon() {
        lblMaHD.setText("");
        lblTongTien.setText("");
        txtSDT.setText("");
        txtTenKH.setText("");
        txtNgayTao.setText("");
        txtTienMat.setText("");
        lblNhanVien.setText("");
        txtTienMat.setText("");
        txtChuyenKhoan.setText("");
        txtTienThua.setText("");
        cboHTTT.setSelectedIndex(0);
    }

    private void clearSanPham() {
        this.txtMa.setText("");
        this.txtTenSP.setText("");
        this.txtLoaiSP.setText("");
        this.txtTheTich.setText("");
        this.txtTrangThai.setText("");
        this.txtNongDo.setText("");
        this.txtGiaBan.setText("");
        this.txtSoLuong.setText("");
        this.txtTimKiem.setText("");
        this.btnThemVaoHD.setText("Thêm vào HĐ");
    }

    public void loadTongTien() {
        tt = new BigDecimal(0);
        if (lblMaHD.getText().trim().length() == 0) {
            return;
        }

        for (HoaDonChiTiet x : ssHDCT.getListByMaHD(lblMaHD.getText())) {
            tt = tt.add(x.getDonGia().multiply(BigDecimal.valueOf(x.getSoLuong())));
        }
        this.lblTongTien.setText(df.format(tt) + " VNĐ");
    }

    private void tienThua() {
        String text = this.cboHTTT.getSelectedItem().toString();
        if (text.trim().length() == 0) {
            return;
        }

        Double tienThua = null;
        Double tienChuyenKhoan = null;
        Double tienMat = null;
        Double tienKhachDua = null;

        if (text.equals("Chuyển khoản")) {
            try {
                tienChuyenKhoan = Double.valueOf(txtChuyenKhoan.getText().trim());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            this.txtTienThua.setText(df.format(BigDecimal.valueOf(tienChuyenKhoan).subtract(tt)) + "");
        } else if (text.equals("Tiền mặt")) {
            try {
                tienMat = Double.valueOf(txtTienMat.getText().trim());
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            this.txtTienThua.setText(df.format(BigDecimal.valueOf(tienMat).subtract(tt)) + "");
        } else {
            try {
                tienChuyenKhoan = Double.valueOf(txtChuyenKhoan.getText().trim());
                tienMat = Double.valueOf(txtTienMat.getText().trim());

            } catch (Exception e) {

                return;
            }
            tienKhachDua = tienMat + tienChuyenKhoan;
            this.txtTienThua.setText(df.format(BigDecimal.valueOf(tienKhachDua).subtract(tt)) + "");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cboHTTT = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTienMat = new javax.swing.JTextField();
        lblMaHD = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtChuyenKhoan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTienThua = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblNhanVien = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnTaoHD = new swing.ButtonCustom();
        btnTreoHD = new swing.ButtonCustom();
        btnHDCho = new swing.ButtonCustom();
        btnThanhToan = new swing.ButtonCustom();
        btnXoa = new swing.ButtonCustom();
        btnXoaAll = new swing.ButtonCustom();
        btnLichSu = new swing.ButtonCustom();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnScanLineCode = new swing.ButtonCustom();
        btnTimKiem = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtLoaiSP = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTheTich = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtTrangThai = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtNongDo = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        btnThemVaoHD = new swing.ButtonCustom();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1140, 799));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã", "Tên SP", "Loại SP", "Thể tích", "Nồng Độ Cồn", "Trạng thái", "Số lượng", "Giá bán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 473, 1140, 354));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Hóa Đơn"));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Mã HĐ :");

        txtSDT.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtSDTCaretUpdate(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel3.setText("Tiền mặt");

        txtNgayTao.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setText("Số điện thoại");

        txtTenKH.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTenKHCaretUpdate(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel5.setText("HT Thanh Toán");

        cboHTTT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản", "Kết hợp" }));
        cboHTTT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboHTTTItemStateChanged(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel6.setText("Tổng tiền :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel7.setText("Ngày tạo");

        txtTienMat.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTienMatCaretUpdate(evt);
            }
        });

        lblMaHD.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblMaHD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTongTien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongTien.setText("...");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel10.setText("Chuyển khoản");

        txtChuyenKhoan.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtChuyenKhoanCaretUpdate(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel8.setText("Tiền thừa");

        txtTienThua.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTienThua.setForeground(new java.awt.Color(255, 0, 0));
        txtTienThua.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel9.setText("Nhân Viên :");

        lblNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel15.setText("Tên KH");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSDT))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addGap(21, 21, 21)
                                .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 1, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addGap(42, 42, 42)
                        .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblNhanVien, txtNgayTao, txtSDT, txtTienMat});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtChuyenKhoan, txtTenKH});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboHTTT, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTienMat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtChuyenKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTienThua, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNhanVien)))
                .addContainerGap(101, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblNhanVien, txtTienMat});

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(473, 0, -1, 467));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnTaoHD.setBackground(new java.awt.Color(255, 0, 0));
        btnTaoHD.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHD.setText("Tạo Hóa Đơn");
        btnTaoHD.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        btnTreoHD.setBackground(new java.awt.Color(255, 0, 0));
        btnTreoHD.setForeground(new java.awt.Color(255, 255, 255));
        btnTreoHD.setText("Treo Hóa Đơn");
        btnTreoHD.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnTreoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTreoHDActionPerformed(evt);
            }
        });

        btnHDCho.setBackground(new java.awt.Color(255, 0, 0));
        btnHDCho.setForeground(new java.awt.Color(255, 255, 255));
        btnHDCho.setText("Hóa Đơn Chờ");
        btnHDCho.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnHDCho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHDChoActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(255, 0, 0));
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 153, 102));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa bia");
        btnXoa.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnXoaAll.setBackground(new java.awt.Color(0, 153, 102));
        btnXoaAll.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaAll.setText("Xóa tất cả");
        btnXoaAll.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnXoaAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaAllActionPerformed(evt);
            }
        });

        btnLichSu.setBackground(new java.awt.Color(255, 0, 0));
        btnLichSu.setForeground(new java.awt.Color(255, 255, 255));
        btnLichSu.setText("Lịch sử");
        btnLichSu.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnLichSu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLichSuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHDCho, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTreoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaAll, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHDCho, btnLichSu, btnTaoHD, btnThanhToan, btnTreoHD, btnXoa, btnXoaAll});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTreoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHDCho, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLichSu, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaAll, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnHDCho, btnTaoHD, btnThanhToan, btnTreoHD, btnXoa, btnXoaAll});

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Sản Phẩm"));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel12.setText("Mã");

        txtMa.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txtMa.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel13.setText("Tên SP");

        txtTenSP.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txtTenSP.setEnabled(false);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tìm kiếm"));

        btnScanLineCode.setBackground(new java.awt.Color(0, 204, 204));
        btnScanLineCode.setForeground(new java.awt.Color(255, 255, 255));
        btnScanLineCode.setText("Scan Barcode");
        btnScanLineCode.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnScanLineCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanLineCodeActionPerformed(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(0, 0, 0));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnTimKiem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/customSearch.png"))); // NOI18N
        btnTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiemMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnScanLineCode, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimKiem)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnScanLineCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnTimKiem, txtTimKiem});

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel14.setText("Loại SP");

        txtLoaiSP.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txtLoaiSP.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel16.setText("Thể tích");

        txtTheTich.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txtTheTich.setEnabled(false);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel18.setText("Trạng thái");

        txtTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txtTrangThai.setEnabled(false);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel19.setText("Nồng độ");

        txtNongDo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txtNongDo.setEnabled(false);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel21.setText("Số lượng");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        jLabel22.setText("Giá bán");

        txtGiaBan.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        txtGiaBan.setEnabled(false);

        btnThemVaoHD.setBackground(new java.awt.Color(255, 0, 0));
        btnThemVaoHD.setForeground(new java.awt.Color(255, 255, 255));
        btnThemVaoHD.setText("Thêm vào HĐ");
        btnThemVaoHD.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnThemVaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVaoHDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(31, 31, 31)
                                .addComponent(txtLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel21)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(7, 7, 7)
                                            .addComponent(jLabel12)))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(8, 8, 8)
                                            .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txtNongDo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel22)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addGap(27, 27, 27)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel16)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtTheTich, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(btnThemVaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtLoaiSP, txtMa, txtSoLuong, txtTrangThai});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTheTich, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNongDo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(btnThemVaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtLoaiSP, txtMa, txtSoLuong, txtTrangThai});

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDActionPerformed
        // TODO add your handling code here:
        ServiceNhanVien ssNV = new ServiceNhanVienImpl();
        HoaDon hd = new HoaDon();
        LocalDateTime time = LocalDateTime.now();
        String maHd = "HD" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getSecond();
        hd.setMa(maHd);
        hd.setNgayTao(new java.sql.Date(new java.util.Date().getTime()));
        hd.setTinhTrang(0);
        hd.setNhanVien(viewLogin.nv);
        //System.out.println(ssNV.getNhanVienByMa(nv.getMa()).getId());
        if (ssHD.insert(hd)) {
            this.clearHoaDon();
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công");
            lblMaHD.setText(hd.getMa());
            txtNgayTao.setText(hd.getNgayTao() + "");
            lblNhanVien.setText(viewLogin.nv.getHoTen());
            dtm.setRowCount(0);
            return;
        }
        JOptionPane.showMessageDialog(this, "Tạo hóa đơn thất bại");
    }//GEN-LAST:event_btnTaoHDActionPerformed

    private void btnHDChoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHDChoActionPerformed
        // TODO add your handling code here:
        viewHoaDonCho x = new viewHoaDonCho();
        x.setVisible(true);

    }//GEN-LAST:event_btnHDChoActionPerformed

    private void btnTreoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTreoHDActionPerformed
        if (this.lblMaHD.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn nào");
            return;
        }
        String maHD = this.lblMaHD.getText().trim();
        if (ssHD.getHoaDonByMa(maHD).getTinhTrang() == 1) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã thanh toán");
            return;
        }
        if (ssHD.getHoaDonByMa(maHD).getTinhTrang() == 2) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã hủy");
            return;
        }
        if (txtSDT.getText().trim().length() == 0) {
            this.clearHoaDon();
            return;
        }
        ServiceKhachHangImpl ss = new ServiceKhachHangImpl();
        try {
            // TODO add your handling code here:
            HoaDon hd = this.getFormHoaDon();
            ssHD.update(hd);
            this.clearHoaDon();
            dtm.setRowCount(0);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnTreoHDActionPerformed

    private void txtSDTCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtSDTCaretUpdate
        // TODO add your handling code here:
        if (txtSDT.getText().trim().length() == 0) {
            //JOptionPane.showMessageDialog(this, "Số điện thoại trống");
            this.txtTenKH.setText("");
            return;
        }
        ServiceKhachHang ss = new ServiceKhachHangImpl();
        KhachHang kh = (KhachHang) ss.getBySDT(txtSDT.getText().trim());
        if (kh == null) {
            this.txtTenKH.setText("Khách hàng mới");
            return;
        }
        this.txtTenKH.setText(kh.getHoTen());
    }//GEN-LAST:event_txtSDTCaretUpdate

    private void btnTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseClicked
        // TODO add your handling code here:

        ServiceSanPhamChiTiet ss = new ServiceSanPhamChiTietImpl();
        if (txtTimKiem.getText().trim().length() == 0) {
            return;
        }
        String maSP = txtTimKiem.getText().trim();
        SanPhamChiTiet spct = ssSPCT.findByMa(maSP);
        if (spct == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã sản phẩm này");
            return;
        }

        this.txtMa.setText(spct.getMa());
        this.txtTenSP.setText(spct.getBia().getTen());
        this.txtLoaiSP.setText(spct.getLoaiSP().getTen());
        this.txtTheTich.setText(spct.getTheTich());
        this.txtTrangThai.setText(spct.getTrangThai() == 1 ? "Thùng" : "Lon");
        this.txtNongDo.setText(spct.getNongDoCon());
        this.txtGiaBan.setText(df.format(spct.getGiaBan()) + "");

    }//GEN-LAST:event_btnTimKiemMouseClicked

    private void btnThemVaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVaoHDActionPerformed
        // TODO add your handling code here:
        if (lblMaHD.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn");
            return;
        }
        if (ssHD.getHoaDonByMa(lblMaHD.getText()).getTinhTrang() == 1) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã thanh toán");
            return;
        }
        if (ssHD.getHoaDonByMa(lblMaHD.getText()).getTinhTrang() == 2) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã hủy");
            return;
        }
        stt = 1;
        ServiceSanPhamChiTiet ss = new ServiceSanPhamChiTietImpl();
        String maSP = txtMa.getText().trim();
        String maHD = lblMaHD.getText().trim();
        Integer soLuong = 0;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải nhập số");
            return;
        }
        SanPhamChiTiet spct = ssSPCT.findByMa(maSP);
        HoaDon hd = ssHD.getHoaDonByMa(maHD);
        HoaDonChiTiet hdct = new HoaDonChiTiet();
        hdct.setSoLuong(soLuong);
        hdct.setIdHoaDon(hd);
        hdct.setIdChiTietSanPham(spct);
        hdct.setDonGia(spct.getGiaBan().multiply(new BigDecimal(soLuong)));
        if (btnThemVaoHD.getText().trim().equals("Cập nhật")) {
            for (HoaDonChiTiet hoaDonChiTiet : ssHDCT.getListByMaHD(maHD)) {
                if (hoaDonChiTiet.getIdChiTietSanPham().getId().equals(hdct.getIdChiTietSanPham().getId())) {
                    ssHDCT.update(hdct);
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    this.loadTable(ssHDCT.getListByMaHD(maHD));
                    this.btnThemVaoHD.setText("Thêm vào HĐ");
                    this.loadTable(ssHDCT.getListByMaHD(maHD));
                    this.clearSanPham();
                    this.loadTongTien();
                    return;
                }
            }
        }
        for (HoaDonChiTiet hoaDonChiTiet : ssHDCT.getListByMaHD(maHD)) {
            if (hoaDonChiTiet.getIdChiTietSanPham().getId().equals(hdct.getIdChiTietSanPham().getId())) {
                hdct.setSoLuong(hdct.getSoLuong() + hoaDonChiTiet.getSoLuong());
                ssHDCT.update(hdct);
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                this.loadTable(ssHDCT.getListByMaHD(maHD));
                this.btnThemVaoHD.setText("Thêm vào HĐ");
                this.loadTable(ssHDCT.getListByMaHD(maHD));
                this.clearSanPham();
                this.loadTongTien();
                return;
            }
        }
        if (ssHDCT.insert(hdct)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            this.clearSanPham();
            this.loadTable(ssHDCT.getListByMaHD(maHD));
            this.loadTongTien();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }//GEN-LAST:event_btnThemVaoHDActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        if (lblMaHD.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn nào");
            return;
        }
        int row = this.tblHoaDon.getSelectedRow();
        System.out.println(row);
        if (row == -1) {
            return;
        }
        String ma = this.tblHoaDon.getValueAt(row, 1).toString();
        SanPhamChiTiet spct = ssSPCT.findByMa(ma);
        this.txtMa.setText(spct.getMa());
        this.txtTenSP.setText(spct.getBia().getTen());
        this.txtLoaiSP.setText(spct.getLoaiSP().getTen());
        this.txtTheTich.setText(spct.getTheTich());
        this.txtTrangThai.setText(spct.getTrangThai() == 1 ? "Thùng" : "Lon");
        this.txtNongDo.setText(spct.getNongDoCon());
        this.txtGiaBan.setText(df.format(spct.getGiaBan()) + "");
        this.txtSoLuong.setText(tblHoaDon.getValueAt(row, 7).toString());
        this.btnThemVaoHD.setText("Cập nhật");
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        if (lblMaHD.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn");
            return;
        }

        if (ssHD.getHoaDonByMa(lblMaHD.getText()).getTinhTrang() == 1) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã thanh toán");
            return;
        }
        if (ssHD.getHoaDonByMa(lblMaHD.getText()).getTinhTrang() == 2) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã hủy");
            return;
        }
        if (txtMa.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn sản phẩm muốn xóa");
            return;
        }

        stt = 1;
        String maSP = txtMa.getText().trim();
        String maHD = lblMaHD.getText().trim();
        HoaDon hd = ssHD.getHoaDonByMa(maHD);
        SanPhamChiTiet spct = ssSPCT.findByMa(maSP);
        System.out.println(hd.getId());
        System.out.println(spct.getId());
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không", "Xóa bia", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION) {
            return;
        }
        if (ssHDCT.deleteHoaDonCTByMaHD_MaSP(hd, spct)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            this.clearSanPham();
            this.loadTable(ssHDCT.getListByMaHD(maHD));
            this.loadTongTien();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnXoaAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaAllActionPerformed
        // TODO add your handling code here:
        if (lblMaHD.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn");
            return;
        }

        if (ssHD.getHoaDonByMa(lblMaHD.getText()).getTinhTrang() == 1) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã thanh toán");
            return;
        }
        if (ssHD.getHoaDonByMa(lblMaHD.getText()).getTinhTrang() == 2) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã hủy");
            return;
        }
        if (lblMaHD.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn muốn xóa");
            return;
        }
        Boolean check = false;
        String maHD = this.lblMaHD.getText();
        HoaDon hd = ssHD.getHoaDonByMa(maHD);
        int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không", "Xóa tất cả", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.NO_OPTION) {
            return;
        }
//        for (HoaDonChiTiet hoaDonChiTiet : ssHDCT.getListByMaHD(maHD)) {
        check = ssHDCT.deleteHoaDonCTByMaHD(hd);
//        }
        if (check) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            this.clearSanPham();
            this.loadTable(ssHDCT.getListByMaHD(maHD));
            this.lblTongTien.setText(0 + " VNĐ");
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }
    }//GEN-LAST:event_btnXoaAllActionPerformed

    private void txtChuyenKhoanCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtChuyenKhoanCaretUpdate
        // TODO add your handling code here:
        if (this.txtChuyenKhoan.getText().trim().length() == 0) {
            this.txtTienThua.setText("");
            return;
        }
        Double tienKhachDua = null;
        try {
            tienKhachDua = Double.valueOf(txtChuyenKhoan.getText().trim());
        } catch (Exception e) {
            this.txtChuyenKhoan.requestFocus();
            //JOptionPane.showMessageDialog(this, "Tiền chuyển khoản phải là số");
            return;
        }
//        BigDecimal tienThua = new BigDecimal(tienKhachDua).subtract(tt);
//        this.txtTienThua.setText(df.format(tienThua));
        this.tienThua();
    }//GEN-LAST:event_txtChuyenKhoanCaretUpdate

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        if (lblMaHD.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hóa đơn muốn thanh toán!");
            return;
        }

        if (ssHD.getHoaDonByMa(lblMaHD.getText()).getTinhTrang() == 1) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã thanh toán");
            return;
        }
        if (ssHD.getHoaDonByMa(lblMaHD.getText()).getTinhTrang() == 2) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã hủy");
            return;
        }
        String maHD = this.lblMaHD.getText().trim();
        HoaDon hd = ssHD.getHoaDonByMa(maHD);
        Integer httt = 0;
        Double tienThua = null;
        Double tienChuyenKhoan = null;
        Double tienMat = null;
//        try {
//            tienThua = Double.valueOf(this.txtTienThua.getText().trim().replace(",", ""));
//            tienChuyenKhoan = Double.valueOf(txtChuyenKhoan.getText().trim());
//            tienMat = Double.valueOf(txtTienMat.getText().trim());
//            if (tienThua < 0) {
//                JOptionPane.showMessageDialog(this, "Khách hàng chưa trả đủ tiền");
//                return;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số");
//            return;
//        }
        //Set hình thức thanh toán
        if (this.cboHTTT.getSelectedItem().toString().equals("Tiền mặt")) {
            httt = 0;
            try {
                tienThua = Double.valueOf(this.txtTienThua.getText().trim().replace(",", ""));
                tienMat = Double.valueOf(txtTienMat.getText().trim());
                if (tienThua < 0) {
                    JOptionPane.showMessageDialog(this, "Khách hàng chưa trả đủ tiền");
                    return;
                }
                hd.setTienMat(BigDecimal.valueOf(tienMat));
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Tiền mặt phải là số");
                return;
            }
        } else if (this.cboHTTT.getSelectedItem().toString().equals("Chuyển khoản")) {
            httt = 1;
            try {
                tienThua = Double.valueOf(this.txtTienThua.getText().trim().replace(",", ""));
                tienChuyenKhoan = Double.valueOf(txtChuyenKhoan.getText().trim());
                if (tienThua < 0) {
                    JOptionPane.showMessageDialog(this, "Khách hàng chưa trả đủ tiền");
                    return;
                }
                hd.setTienChuyenKhoan(BigDecimal.valueOf(tienChuyenKhoan));
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Tiền chuyển khoản phải là số");
                return;
            }
        } else {
            httt = 2;
            try {
                tienThua = Double.valueOf(this.txtTienThua.getText().trim().replace(",", ""));
                tienChuyenKhoan = Double.valueOf(txtChuyenKhoan.getText().trim());
                tienMat = Double.valueOf(txtTienMat.getText().trim());
                if (tienThua < 0) {
                    JOptionPane.showMessageDialog(this, "Khách hàng chưa trả đủ tiền");
                    return;
                }
                hd.setTienMat(BigDecimal.valueOf(tienMat));
                hd.setTienChuyenKhoan(BigDecimal.valueOf(tienChuyenKhoan));
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số");
                return;
            }
        }
        //đổi trạng thái hóa đơn thành đã thanh toán 
        //trừ số lượng tồn trong spct
        try {
            BigDecimal tongTien = new BigDecimal(0);

            hd.setTinhTrang(1);
            for (HoaDonChiTiet x : ssHDCT.getListByMaHD(maHD)) {
                SanPhamChiTiet spct = x.getIdChiTietSanPham();
                Integer soLuong = x.getSoLuong();
                spct.setSoLuongTon(spct.getSoLuongTon() - soLuong);
                //Viết hàm giúp tao phát
                ssSPCT.updateSL(spct);
            }
            hd.setTongTien(tt);
            hd.setNgayThanhToan(new Date());
//            hd.setTienMat(BigDecimal.valueOf(tienMat));
//            hd.setTienChuyenKhoan(BigDecimal.valueOf(tienChuyenKhoan));
            hd.setHinhThucThanhToan(httt);
            ssHD.update(hd);

            JOptionPane.showMessageDialog(this, "Thanh toán thành công");
            this.clearHoaDon();
            this.clearSanPham();
            this.dtm.setRowCount(0);
            ExportFilePdf.exportBill(hd);
            File file = new File(ExportFilePdf.path);
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi");
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void btnLichSuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLichSuActionPerformed
        // TODO add your handling code here:
        new viewLichSu().setVisible(true);
    }//GEN-LAST:event_btnLichSuActionPerformed

    private void txtTenKHCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTenKHCaretUpdate
        // TODO add your handling code here:
//        if(this.txtSDT.getText().trim().length()==0){
//            return;
//        }
//        this.txtSDTCaretUpdate(evt);
    }//GEN-LAST:event_txtTenKHCaretUpdate

    private void txtTienMatCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTienMatCaretUpdate
        // TODO add your handling code here:
        if (this.txtTienMat.getText().trim().length() == 0) {
            this.txtTienMat.setText("");
            return;
        }
        Double tienKhachDua = null;
        try {
            tienKhachDua = Double.valueOf(txtTienMat.getText().trim());
        } catch (Exception e) {
            this.txtTienMat.requestFocus();
            //JOptionPane.showMessageDialog(this, "Tiền mặt phải là số");
            return;
        }
//        BigDecimal tienThua = new BigDecimal(tienKhachDua).subtract(tt);
//        this.txtTienThua.setText(df.format(tienThua));
        this.tienThua();
    }//GEN-LAST:event_txtTienMatCaretUpdate

    private void cboHTTTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboHTTTItemStateChanged
        // TODO add your handling code here:
        String text = this.cboHTTT.getSelectedItem().toString();
        System.out.println(text);
        if (text.equals("Tiền mặt")) {
            this.txtTienMat.setEnabled(true);
            this.txtChuyenKhoan.setEnabled(false);
            this.txtChuyenKhoan.setText("");
        } else if (text.equals("Chuyển khoản")) {
            this.txtTienMat.setEnabled(false);
            this.txtChuyenKhoan.setEnabled(true);
            this.txtTienMat.setText("");
        } else {
            this.txtChuyenKhoan.setEnabled(true);
            this.txtTienMat.setEnabled(true);
            this.txtTienMat.setText("");
            this.txtChuyenKhoan.setText("");
        }


    }//GEN-LAST:event_cboHTTTItemStateChanged

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        String maHD = this.lblMaHD.getText().trim();
        HoaDon hd = ssHD.getHoaDonByMa(maHD);
        if (hd.getTinhTrang() != 0) {
            return;
        }
    }//GEN-LAST:event_jPanel1MouseClicked
    private void btnScanLineCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanLineCodeActionPerformed
        // TODO add your handling code here:
        ScanQRCode qrCodeScanner = new ScanQRCode();
        qrCodeScanner.setVisible(true);
        
        String maSP = qrCodeScanner.getText();

        System.out.println("Print o view-maSP: " + maSP);
        SanPhamChiTiet spct = serviceDKM.findByMa(maSP);
        if (spct == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy mã sản phẩm này");
            return;
        }

        this.txtMa.setText(spct.getMa());
        this.txtTenSP.setText(spct.getBia().getTen());
        this.txtLoaiSP.setText(spct.getLoaiSP().getTen());
        this.txtTheTich.setText(spct.getTheTich());
        this.txtTrangThai.setText(spct.getTrangThai() == 1 ? "Thùng" : "Lon");
        this.txtNongDo.setText(spct.getNongDoCon());
        this.txtGiaBan.setText(df.format(spct.getGiaBan()) + "");
    }//GEN-LAST:event_btnScanLineCodeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ButtonCustom btnHDCho;
    private swing.ButtonCustom btnLichSu;
    private swing.ButtonCustom btnScanLineCode;
    private swing.ButtonCustom btnTaoHD;
    private swing.ButtonCustom btnThanhToan;
    private swing.ButtonCustom btnThemVaoHD;
    private javax.swing.JLabel btnTimKiem;
    private swing.ButtonCustom btnTreoHD;
    private swing.ButtonCustom btnXoa;
    private swing.ButtonCustom btnXoaAll;
    public javax.swing.JComboBox<String> cboHTTT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblMaHD;
    public javax.swing.JLabel lblNhanVien;
    public javax.swing.JLabel lblTongTien;
    public javax.swing.JTable tblHoaDon;
    public javax.swing.JTextField txtChuyenKhoan;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtLoaiSP;
    private javax.swing.JTextField txtMa;
    public javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNongDo;
    public javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoLuong;
    public javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTheTich;
    public javax.swing.JTextField txtTienMat;
    private javax.swing.JTextField txtTienThua;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTrangThai;
    // End of variables declaration//GEN-END:variables
}
