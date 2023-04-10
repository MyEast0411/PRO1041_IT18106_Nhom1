package view;

import domainModel.Bia;
import domainModel.BiaKhuyenMai;
import domainModel.DotKhuyenMai;
import domainModel.KhachHangKhuyenMai;
import domainModel.NSXKhuyenMai;
import domainModel.SanPhamChiTiet;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.commons.lang3.time.DateUtils;
import service.impl.ServiceDotKhuyenMaiImpl;
import service.ServiceDotKhuyenMai;
import utilities.ThreadRunLoadTableBiaKM_Hoi;

public class viewQLKhuyenMai extends javax.swing.JPanel {

    String trangThaiFilter;
    private DefaultTableModel dtm = (DefaultTableModel) new DefaultTableModel();
    private ServiceDotKhuyenMai serviceDKM = new ServiceDotKhuyenMaiImpl();

    public viewQLKhuyenMai() {
        initComponents();
        this.loadTable();
        this.loadTableBiaKM();
        this.loadCbbTenBia();
        this.setDate();
        this.runThreadLoadTableBiaKM();
    }

    public void runThreadLoadTableBiaKM() {
        ThreadRunLoadTableBiaKM_Hoi thread = new ThreadRunLoadTableBiaKM_Hoi(this);
        Thread t = new Thread(thread);
        t.start();

    }

    public void setDate() {
        txtNgayBatDau.setDate(new Date());
        txtNgayBatDau.setMinSelectableDate(new Date());
        txtNgayKetThuc.setMinSelectableDate(new Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtNgayBatDau.setDateFormatString(dateFormat.toPattern());
        txtNgayKetThuc.setDateFormatString(dateFormat.toPattern());
        txtStartDate.setDateFormatString(dateFormat.toPattern());
        txtEndDate.setDateFormatString(dateFormat.toPattern());
    }

    public void loadCbbTenBia() {
        DefaultComboBoxModel dcb = new DefaultComboBoxModel();
        Set<String> tenBiaSet = new HashSet<>();
        for (Bia bia : serviceDKM.getListBia()) {
            String tenBia = bia.getTen();
            if (!tenBiaSet.contains(tenBia)) {
                dcb.addElement(bia);
                tenBiaSet.add(tenBia);
            }
        }
        this.cbbTenBia.setModel(dcb);
    }

    public void loadTable() {

        dtm = (DefaultTableModel) this.tblDotKhuyenMai.getModel();
        dtm.setRowCount(0);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        // Format decimal numbers to display without trailing zeros
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        int stt = 1;
        String[] columnNames = {"STT", "Mã khuyến mại", "Tên khuyến mại", "Phần trăm giảm", "Tiền mặt giảm", "Ngày bắt đầu", "Ngày kết thúc"};
        dtm.setColumnIdentifiers(columnNames);
        for (DotKhuyenMai x : serviceDKM.getList()) {
            dtm.addRow(new Object[]{
                stt++,
                x.getMa(),
                x.getTen(),
                decimalFormat.format(x.getGiaTriPhanTram()),
                decimalFormat.format(x.getGiaTriTienMat()),
                formatter.format(x.getNgayBatDau()),
                formatter.format(x.getNgayKetThuc())
            });
        }
        TableColumnModel tcm = tblDotKhuyenMai.getColumnModel();
        tcm.getColumn(tcm.getColumnIndex("STT")).setMaxWidth(50);
        tcm.getColumn(tcm.getColumnIndex("Tên khuyến mại")).setMinWidth(150);
    }

    public DotKhuyenMai getFormData() {
        String loaiKM = cbbLoaiKM.getSelectedItem().toString();
        String mucKM = txtMucKM.getText().trim();
        float GiaTriPhanTram = 0;
        BigDecimal GiaTriTienMat = null;
        if (loaiKM.equals("Theo %")) {
            GiaTriPhanTram = Float.parseFloat(mucKM);
            GiaTriTienMat = new BigDecimal(0);
        } else if (loaiKM.equals("Theo tiền mặt")) {
            GiaTriTienMat = new BigDecimal(mucKM);
        }

        return new DotKhuyenMai(null, txtMa.getText().trim(), txtTen.getText().trim(), txtNgayBatDau.getDate(), txtNgayKetThuc.getDate(), GiaTriPhanTram, GiaTriTienMat);

    }

    public void fillForm() {
        int row = tblDotKhuyenMai.getSelectedRow();
        if (row == -1) {
            return;
        }
        String ma = tblDotKhuyenMai.getValueAt(row, 1).toString();
        String ten = tblDotKhuyenMai.getValueAt(row, 2).toString();

        String ngayBatDauString = tblDotKhuyenMai.getValueAt(row, 5).toString();
        String ngayKetThucString = tblDotKhuyenMai.getValueAt(row, 6).toString();
        Date NBD = new Date();
        Date NKT = new Date();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            NBD = formatter.parse(ngayBatDauString);

        } catch (ParseException e) {
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            NKT = formatter.parse(ngayKetThucString);
        } catch (ParseException e) {
        }
        if (tblDotKhuyenMai.getValueAt(row, 4).toString().equals("0")) {
            txtMucKM.setText(tblDotKhuyenMai.getValueAt(row, 5).toString());
        } else {
            txtMucKM.setText(tblDotKhuyenMai.getValueAt(row, 4).toString());
        }

        txtMa.setText(ma);
        txtTen.setText(ten);
        txtNgayBatDau.setDate(NBD);
        txtNgayKetThuc.setDate(NKT);

    }

    public void clear() {
        txtStartDate.setDate(null);
        txtEndDate.setDate(null);
        txtMa.setText("");
        txtTen.setText("");
        txtMucKM.setText("");
    }

    public boolean validateForm() {
        if (txtMa.getText().trim().length() == 0 || txtTen.getText().trim().length() == 0 || txtMucKM.getText().trim().length() == 0
                || txtNgayBatDau.getDate() == null || txtNgayKetThuc.getDate() == null || txtMucKM.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }
        String mucKMString = txtMucKM.getText();
        try {
            double mucKM = Double.parseDouble(mucKMString);
            String selected = cbbLoaiKM.getSelectedItem().toString();
            Date ngayBatDau = txtNgayBatDau.getDate();
            Date ngayKetThuc = txtNgayKetThuc.getDate();

            if (ngayKetThuc != null && ngayKetThuc.before(ngayBatDau)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu không được lớn hơn ngày kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                ngayKetThuc = DateUtils.addDays(ngayBatDau, 1); // Thêm một ngày cho ngày kết thúc
                txtNgayKetThuc.setDate(ngayKetThuc);
                return false;
            }
            if (ngayKetThuc != null && ngayKetThuc.equals(ngayBatDau)) {
                JOptionPane.showMessageDialog(this, "Ngày kết thúc đang trùng với ngày bắt đầu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (selected.equals("Theo tiền mặt")) {
                if (mucKM < 1000) {
                    JOptionPane.showMessageDialog(this, "Tiền giảm giá phải lớn hơn hoặc bằng 1 nghìn VNĐ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (mucKM > 20000) {
                    JOptionPane.showMessageDialog(this, "Tiền giảm giá phải nhỏ hơn 20 nghìn VNĐ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            if (selected.equals("Theo %")) {
                if (mucKM < 0.1) {
                    JOptionPane.showMessageDialog(this, "Phần trăm giảm phải lớn hơn 0%.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (mucKM >= 51) {
                    JOptionPane.showMessageDialog(this, "Phần trăm giảm phải nhỏ hơn 51%.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Mức khuyến mại phải là một số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtMucKM = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNgayBatDau = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        cbbLoaiKM = new javax.swing.JComboBox<>();
        btnThem = new swing.ButtonCustom();
        tbnSua = new swing.ButtonCustom();
        btnClear = new swing.ButtonCustom();
        jPanel12 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtStartDate = new com.toedter.calendar.JDateChooser();
        btnXoa = new swing.ButtonCustom();
        txtEndDate = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        btnApDungKM = new swing.ButtonCustom();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDotKhuyenMai = new javax.swing.JTable();
        cbbDanhSachKM = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSanPhamKM = new javax.swing.JTable();
        cbbTenBia = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        rdoDaKetThuc = new javax.swing.JRadioButton();
        rdoDangDienRa = new javax.swing.JRadioButton();
        rdoTatCa = new javax.swing.JRadioButton();
        rdoChuaDienRa = new javax.swing.JRadioButton();

        setPreferredSize(new java.awt.Dimension(1140, 821));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1272, 748));
        jPanel1.setMinimumSize(new java.awt.Dimension(1272, 748));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(215, 208, 208)));
        jPanel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel3.setText("Mã KM");

        jLabel4.setText("Mức KM");

        jLabel5.setText("Tên KM");

        jLabel6.setText("Ngày bắt đầu");

        txtNgayBatDau.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtNgayBatDauPropertyChange(evt);
            }
        });

        jLabel7.setText("Ngày kết thúc");

        txtNgayKetThuc.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtNgayKetThucPropertyChange(evt);
            }
        });

        jLabel8.setText("Loại KM");

        cbbLoaiKM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo %", "Theo tiền mặt" }));

        btnThem.setBackground(new java.awt.Color(25, 167, 206));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        tbnSua.setBackground(new java.awt.Color(25, 167, 206));
        tbnSua.setForeground(new java.awt.Color(255, 255, 255));
        tbnSua.setText("Sửa");
        tbnSua.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tbnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnSuaActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(25, 167, 206));
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Làm mới");
        btnClear.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMucKM)
                            .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbLoaiKM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtMa, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                .addComponent(txtNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cbbLoaiKM, txtMa, txtNgayBatDau, txtNgayKetThuc, txtTen});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel6)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel7))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbbLoaiKM, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMucKM, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbLoaiKM, txtMa, txtNgayBatDau, txtNgayKetThuc, txtTen});

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách khuyến mại", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Nunito", 1, 14), new java.awt.Color(20, 108, 148))); // NOI18N

        txtSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm đợt khuyến mại theo tên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(65, 65, 65))); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(204, 204, 204)));

        jLabel2.setText("Start Date");

        txtStartDate.setBackground(new java.awt.Color(246, 241, 241));

        btnXoa.setBackground(new java.awt.Color(255, 0, 0));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa đợt KM");
        btnXoa.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        jLabel9.setText("End Date");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(21, 21, 21)
                                .addComponent(jLabel9))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(txtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnApDungKM.setBackground(new java.awt.Color(64, 142, 145));
        btnApDungKM.setForeground(new java.awt.Color(255, 255, 255));
        btnApDungKM.setText("Áp Dụng Khuyến Mại");
        btnApDungKM.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        btnApDungKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApDungKMActionPerformed(evt);
            }
        });

        tblDotKhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblDotKhuyenMai.setRowHeight(40);
        tblDotKhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDotKhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDotKhuyenMai);

        cbbDanhSachKM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sản phẩm" }));
        cbbDanhSachKM.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Các đợt khuyến mại đã áp dụng theo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        cbbDanhSachKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbDanhSachKMActionPerformed(evt);
            }
        });
        cbbDanhSachKM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbbDanhSachKMKeyReleased(evt);
            }
        });

        tblSanPhamKM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblSanPhamKM.setRowHeight(40);
        tblSanPhamKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamKMMouseClicked(evt);
            }
        });
        tblSanPhamKM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblSanPhamKMKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblSanPhamKM);

        cbbTenBia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbTenBia.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chọn hãng bia muốn áp dụng KM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(235, 235, 235));

        buttonGroup1.add(rdoDaKetThuc);
        rdoDaKetThuc.setText("Đã kết thúc");
        rdoDaKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDaKetThucActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoDangDienRa);
        rdoDangDienRa.setText("Đang diễn ra");
        rdoDangDienRa.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rdoDangDienRaStateChanged(evt);
            }
        });
        rdoDangDienRa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangDienRaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoTatCa);
        rdoTatCa.setSelected(true);
        rdoTatCa.setText("Tất cả");
        rdoTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoChuaDienRa);
        rdoChuaDienRa.setText("Chưa diễn ra");
        rdoChuaDienRa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoChuaDienRaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdoTatCa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoDangDienRa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoDaKetThuc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoChuaDienRa)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoDangDienRa)
                    .addComponent(rdoDaKetThuc)
                    .addComponent(rdoChuaDienRa)
                    .addComponent(rdoTatCa))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cbbDanhSachKM, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(cbbTenBia, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnApDungKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(512, 512, 512))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addComponent(btnApDungKM, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(cbbDanhSachKM)
                                        .addGap(6, 6, 6))
                                    .addComponent(cbbTenBia, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(11, 11, 11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void rdoChuaDienRaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoChuaDienRaActionPerformed
        // TODO add your handling code here:
        trangThaiFilter = "Chưa diễn ra";
        loadTableBiaKM2();
    }//GEN-LAST:event_rdoChuaDienRaActionPerformed

    private void rdoTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaActionPerformed
        // TODO add your handling code here:
        trangThaiFilter = "";
        loadTableBiaKM2();
    }//GEN-LAST:event_rdoTatCaActionPerformed

    private void rdoDangDienRaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangDienRaActionPerformed
        // TODO add your handling code here:
        trangThaiFilter = "Đang diễn ra";
        loadTableBiaKM2();
    }//GEN-LAST:event_rdoDangDienRaActionPerformed

    private void rdoDangDienRaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rdoDangDienRaStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDangDienRaStateChanged

    private void rdoDaKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDaKetThucActionPerformed
        // TODO add your handling code here:
        trangThaiFilter = "Đã kết thúc";
        loadTableBiaKM2();
    }//GEN-LAST:event_rdoDaKetThucActionPerformed

    private void tblSanPhamKMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblSanPhamKMKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamKMKeyReleased

    private void tblSanPhamKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamKMMouseClicked
        // TODO add your handling code here:
        int row = tblSanPhamKM.getSelectedRow();
        if (row == -1) {
            return;
        }
        String tenSanPham = (String) tblSanPhamKM.getValueAt(row, 2);
        String tenDKM = (String) tblSanPhamKM.getValueAt(row, 4);
        String maDKM = (String) tblSanPhamKM.getValueAt(row, 11);
        int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa tất cả " + tenSanPham + " ra khỏi đợt khuyến mại (" + tenDKM + ") không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) { // nếu người dùng đồng ý xóa
            serviceDKM.deleteBiaKM(tenSanPham, maDKM);
            loadTableBiaKM();
            JOptionPane.showMessageDialog(this, "Thành công");
        }
    }//GEN-LAST:event_tblSanPhamKMMouseClicked

    private void cbbDanhSachKMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbbDanhSachKMKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbDanhSachKMKeyReleased

    private void cbbDanhSachKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbDanhSachKMActionPerformed
        // TODO add your handling code here:
//        String selected = cbbDanhSachKM.getSelectedItem().toString();
        loadTableBiaKM();
//        if (selected.equals("Khách hàng")) {
//            loadTableKhachHangKM();
//        } else if (selected.equals("Nhà sản xuất")) {
//            loadTableNSXKM();
//        }
    }//GEN-LAST:event_cbbDanhSachKMActionPerformed

    private void tblDotKhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDotKhuyenMaiMouseClicked
        // TODO add your handling code here:
        int row = tblDotKhuyenMai.getSelectedRow();
        if (row == -1) {
            return;
        }
        String ma = tblDotKhuyenMai.getValueAt(row, 1).toString();
        String ten = tblDotKhuyenMai.getValueAt(row, 2).toString();
        String ngayBatDauString = tblDotKhuyenMai.getValueAt(row, 5).toString();
        String ngayKetThucString = tblDotKhuyenMai.getValueAt(row, 6).toString();
        Date NBD = new Date();
        Date NKT = new Date();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            NBD = formatter.parse(ngayBatDauString);

        } catch (ParseException e) {
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            NKT = formatter.parse(ngayKetThucString);
        } catch (ParseException e) {
        }
        String phanTram = tblDotKhuyenMai.getValueAt(row, 3).toString();
        String tienMat = tblDotKhuyenMai.getValueAt(row, 4).toString();
        if (phanTram.equals("0") && !(tienMat.equals("0"))) {
            cbbLoaiKM.setSelectedItem("Theo tiền mặt");
            txtMucKM.setText(tienMat);
        } else if (tienMat.equals("0") && !(phanTram.equals("0"))) {
            cbbLoaiKM.setSelectedItem("Theo %");
            txtMucKM.setText(phanTram);
        }
        txtMa.setText(ma);
        txtTen.setText(ten);
        txtNgayBatDau.setDate(NBD);
        txtNgayKetThuc.setDate(NKT);
    }//GEN-LAST:event_tblDotKhuyenMaiMouseClicked

    private void btnApDungKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApDungKMActionPerformed
        // TODO add your handling code here:
        int row = tblDotKhuyenMai.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn đợt khuyến mại để áp dụng!");
            return;
        }
        String maDKM = tblDotKhuyenMai.getValueAt(row, 1).toString();
        DotKhuyenMai dotKM = (DotKhuyenMai) serviceDKM.getDotKhuyenMaiByMaDKM(maDKM);
        Bia selectedBia = (Bia) cbbTenBia.getSelectedItem();
        String tenSanPham = selectedBia.getTen();
        List<SanPhamChiTiet> spctList = serviceDKM.findByTenSanPham(tenSanPham);
        Date currentDate = new Date();
        boolean isSuccess = false;
        boolean checkDate = false;
        if (spctList != null) {
            for (SanPhamChiTiet spct : spctList) {
                if (dotKM.getNgayKetThuc().after(currentDate)) {
                    checkDate = true;
                }
                if (checkDate == false) {
                    JOptionPane.showMessageDialog(this, "Đợt khuyến mại đã kết thúc.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (serviceDKM.apDungKM(spct, dotKM) && checkDate) {
                    isSuccess = true;
                }
            }
        } else {
            System.out.println(" List<SanPhamChiTiet> spctList = serviceDKM.findByTenSanPham(tenSanPham) is null");
        }
        if (isSuccess) {
            JOptionPane.showMessageDialog(this, "Áp dụng thành công");
            loadTableBiaKM();
        }
    }//GEN-LAST:event_btnApDungKMActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        Date startDate = txtStartDate.getDate();
        Date endDate = txtEndDate.getDate();

        // Kiểm tra nếu người dùng chưa chọn khoảng thời gian và cũng chưa chọn hàng nào trong bảng
        if (startDate == null && endDate == null && tblDotKhuyenMai.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Chọn một đợt khuyến mại hoặc nhập vào một khoảng thời gian để xóa.");
            return;
        }

        // Nếu người dùng chọn khoảng thời gian
        if (startDate != null && endDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
            try {
                dateFormat.format(startDate);
                dateFormat.format(endDate);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày tháng không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (serviceDKM.deleteMany(startDate, endDate)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                this.loadTable();
            }
            this.clear();
        } else if (startDate != null && endDate != null && tblDotKhuyenMai.getSelectedRow() != -1) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
            try {
                dateFormat.format(startDate);
                dateFormat.format(endDate);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày tháng không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (serviceDKM.deleteMany(startDate, endDate)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                this.loadTable();
                this.clear();
            }
        } else // Nếu người dùng chọn một hàng trong bảng
        if (tblDotKhuyenMai.getSelectedRow() != -1 && startDate == null && endDate == null) {
            int row = tblDotKhuyenMai.getSelectedRow();
            String maDKM = tblDotKhuyenMai.getValueAt(row, 1).toString();
            DotKhuyenMai dkm = serviceDKM.getDotKhuyenMaiByMaDKM(maDKM);
            if (serviceDKM.deleteOne(dkm)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                this.loadTable();
                this.clear();
            }
        }
    }

    public void deleteOne() {
        DotKhuyenMai dkm = new DotKhuyenMai();
        for (DotKhuyenMai x : serviceDKM.getList()) {
            dkm.setId(x.getId());
        }
        if (serviceDKM.deleteOne(dkm)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            this.loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        this.clear();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
        dtm = (DefaultTableModel) this.tblDotKhuyenMai.getModel();
        dtm.setRowCount(0);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        // Format decimal numbers to display without trailing zeros
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        int stt = 1;
        List<DotKhuyenMai> result = serviceDKM.getListByTen(txtSearch.getText());
        if (result.isEmpty()) {
            String[] columnNames = {"Không tồn tại đợt khuyến mại này"};
            dtm.setColumnIdentifiers(columnNames);
        } else {
            String[] columnNames = {"STT", "Mã khuyến mại", "Tên khuyến mại", "Phần trăm giảm", "Tiền mặt giảm", "Ngày bắt đầu", "Ngày kết thúc"};
            dtm.setColumnIdentifiers(columnNames);
            for (DotKhuyenMai x : serviceDKM.getListByTen(txtSearch.getText())) {
                dtm.addRow(new Object[]{
                    stt++,
                    x.getMa(),
                    x.getTen(),
                    decimalFormat.format(x.getGiaTriPhanTram()),
                    decimalFormat.format(x.getGiaTriTienMat()),
                    formatter.format(x.getNgayBatDau()),
                    formatter.format(x.getNgayKetThuc())
                });
            }
        }
        TableColumnModel tcm = tblDotKhuyenMai.getColumnModel();
        tcm.getColumn(tcm.getColumnIndex("STT")).setMaxWidth(50);
        tcm.getColumn(tcm.getColumnIndex("Tên khuyến mại")).setMinWidth(150);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tbnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnSuaActionPerformed
        // TODO add your handling code here:
        int row = this.tblDotKhuyenMai.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn một đợt khuyến mại để sửa");
            return;
        }
        if (validateForm()) {
            DotKhuyenMai dkm = this.getFormData();
            DotKhuyenMai selectedDkm = serviceDKM.getList().get(row);
            if (dkm.getMa().equals(selectedDkm.getMa())) { // mã khuyến mại không thay đổi
                dkm.setId(selectedDkm.getId());
                if (serviceDKM.update(dkm)) {
                    loadTableBiaKM();
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công");
                    this.loadTable();
                }
                this.clear();
            } else {
                JOptionPane.showMessageDialog(this, "Không được sửa mã!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_tbnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if (validateForm()) {
            DotKhuyenMai dkm = this.getFormData();
            if (serviceDKM.validateDuplicateMaDKM(dkm)) {
                if (serviceDKM.insert(dkm)) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    this.loadTable();
                    this.clear();
                } else {
                    JOptionPane.showMessageDialog(this, "Thời gian diễn ra đợt khuyến mại bị trùng với một đợt khuyến mại khác.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtNgayKetThucPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtNgayKetThucPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayKetThucPropertyChange

    private void txtNgayBatDauPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtNgayBatDauPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayBatDauPropertyChange

    public void loadTableBiaKM() {
        dtm = (DefaultTableModel) tblSanPhamKM.getModel();
        dtm.setRowCount(0);
        String[] columnNames = {"STT", "Mã bia", "Tên bia", "Loại bia", "Đợt KM được áp dụng", "Giá cũ", "Mức giảm", "Giá sau khi giảm", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Mã ĐKM"};
        dtm.setColumnIdentifiers(columnNames);
        int stt = 1;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<BiaKhuyenMai> list = serviceDKM.getListBiaKhuyenMai();
        for (BiaKhuyenMai spkm : list) {
            Date currentDate = new Date();
            String trangThai;
            if (currentDate.after(spkm.getKhuyenMai().getNgayBatDau()) && currentDate.before(spkm.getKhuyenMai().getNgayKetThuc())) {
                trangThai = "Đang diễn ra";
            } else if (currentDate.before(spkm.getKhuyenMai().getNgayBatDau())) {
                trangThai = "Chưa diễn ra";
            } else if (currentDate.after(spkm.getKhuyenMai().getNgayKetThuc())) {
                trangThai = "Đã kết thúc";
            } else {
                trangThai = "";
            }
            BigDecimal giaCu = spkm.getDonGia();
            float giaTriPhanTram = spkm.getKhuyenMai().getGiaTriPhanTram();
            BigDecimal giaTriTienMat = spkm.getKhuyenMai().getGiaTriTienMat();
            BigDecimal mucGiaSauKhiGiam = giaCu.subtract(giaCu.multiply(BigDecimal.valueOf(giaTriPhanTram / 100))).subtract(giaTriTienMat);
            if (trangThai.equals("Đang diễn ra")) {
                // Update cột giá còn lại khi đợt khuyến mại đang diễn ra
                if (serviceDKM.updateGiaConLaiBiaKM(spkm, mucGiaSauKhiGiam)) {
                    System.out.println("Updated gia_con_lai.");
                } else {
                    System.out.println("Failed updated gia_con_lai.");
                }
                spkm.setGiaConLai(mucGiaSauKhiGiam);
            } else {
                serviceDKM.updateGiaConLaiBiaKM(spkm, spkm.getChiTietSanPham().getGiaBan());
                spkm.setGiaConLai(spkm.getChiTietSanPham().getGiaBan());
            }
            dtm.addRow(new Object[]{
                stt++,
                spkm.getChiTietSanPham().getBia().getMa(),
                spkm.getChiTietSanPham().getBia().getTen(),
                spkm.getChiTietSanPham().getLoaiSP(),
                spkm.getKhuyenMai().getTen(),
                decimalFormat.format(giaCu),
                giaTriPhanTram > 0 ? (decimalFormat.format(giaTriPhanTram) + "%") : decimalFormat.format(giaTriTienMat),
                decimalFormat.format(mucGiaSauKhiGiam),
                formatter.format(spkm.getKhuyenMai().getNgayBatDau()),
                formatter.format(spkm.getKhuyenMai().getNgayKetThuc()),
                trangThai,
                spkm.getKhuyenMai().getMa()
            });
        }
        if (serviceDKM.updateTTBiaKM()) {//update trạng thái xuống database
            System.out.println("Update Trang thai thanh cong");
        }
        // ẩn cột Mã DKM
        TableColumnModel tcm = tblSanPhamKM.getColumnModel();
        tcm.getColumn(tcm.getColumnIndex("Mã ĐKM")).setMinWidth(0);
        tcm.getColumn(tcm.getColumnIndex("Mã ĐKM")).setMaxWidth(0);
        tcm.getColumn(tcm.getColumnIndex("Mã Bia")).setMinWidth(0);
        tcm.getColumn(tcm.getColumnIndex("Mã Bia")).setMaxWidth(0);
        //set độ rộng cột
        tcm.getColumn(tcm.getColumnIndex("Đợt KM được áp dụng")).setMinWidth(140);
        tcm.getColumn(tcm.getColumnIndex("STT")).setMaxWidth(35);
        tcm.getColumn(tcm.getColumnIndex("Trạng thái")).setMinWidth(100);
        tcm.getColumn(tcm.getColumnIndex("Loại bia")).setMinWidth(140);
        tcm.getColumn(tcm.getColumnIndex("Giá sau khi giảm")).setMinWidth(100);
        tcm.getColumn(tcm.getColumnIndex("Trạng thái")).setMinWidth(70);
    }

    public void loadTableBiaKM2() {
        dtm = (DefaultTableModel) tblSanPhamKM.getModel();
        dtm.setRowCount(0);
        String[] columnNames = {"STT", "Mã bia", "Tên bia", "Loại bia", "Đợt KM được áp dụng", "Giá cũ", "Mức giảm", "Giá sau khi giảm", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái", "Mã ĐKM"};
        dtm.setColumnIdentifiers(columnNames);
        int stt = 1;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<BiaKhuyenMai> list = serviceDKM.getListBiaKhuyenMai();
        for (BiaKhuyenMai spkm : list) {
            Date currentDate = new Date();
            String trangThai;
            if (currentDate.after(spkm.getKhuyenMai().getNgayBatDau()) && currentDate.before(spkm.getKhuyenMai().getNgayKetThuc())) {
                trangThai = "Đang diễn ra";
            } else if (currentDate.before(spkm.getKhuyenMai().getNgayBatDau())) {
                trangThai = "Chưa diễn ra";
            } else if (currentDate.after(spkm.getKhuyenMai().getNgayKetThuc())) {
                trangThai = "Đã kết thúc";
            } else {
                trangThai = "";
            }
            BigDecimal giaCu = spkm.getDonGia();
            float giaTriPhanTram = spkm.getKhuyenMai().getGiaTriPhanTram();
            BigDecimal giaTriTienMat = spkm.getKhuyenMai().getGiaTriTienMat();
            BigDecimal mucGiaSauKhiGiam = giaCu.subtract(giaCu.multiply(BigDecimal.valueOf(giaTriPhanTram / 100))).subtract(giaTriTienMat);
            if (trangThai.equals("Đang diễn ra")) {
                // Update cột giá còn lại khi đợt khuyến mại đang diễn ra
                if (serviceDKM.updateGiaConLaiBiaKM(spkm, mucGiaSauKhiGiam)) {
                    System.out.println("Updated gia_con_lai.");
                } else {
                    System.out.println("Failed updated gia_con_lai.");
                }
                spkm.setGiaConLai(mucGiaSauKhiGiam);
            } else {
                serviceDKM.updateGiaConLaiBiaKM(spkm, spkm.getChiTietSanPham().getGiaBan());
                spkm.setGiaConLai(spkm.getChiTietSanPham().getGiaBan());
            }
            if (trangThaiFilter.isEmpty() || trangThaiFilter.equals(trangThai)) {
                dtm.addRow(new Object[]{
                    stt++,
                    spkm.getChiTietSanPham().getBia().getMa(),
                    spkm.getChiTietSanPham().getBia().getTen(),
                    spkm.getChiTietSanPham().getLoaiSP(),
                    spkm.getKhuyenMai().getTen(),
                    decimalFormat.format(giaCu),
                    giaTriPhanTram > 0 ? (decimalFormat.format(giaTriPhanTram) + "%") : decimalFormat.format(giaTriTienMat),
                    decimalFormat.format(mucGiaSauKhiGiam),
                    formatter.format(spkm.getKhuyenMai().getNgayBatDau()),
                    formatter.format(spkm.getKhuyenMai().getNgayKetThuc()),
                    trangThai,
                    spkm.getKhuyenMai().getMa()
                });
            }
            if (serviceDKM.updateTTBiaKM()) {//update trạng thái xuống database
                System.out.println("Update Trang thai thanh cong");
            }
        }
        // ẩn cột Mã DKM
        TableColumnModel tcm = tblSanPhamKM.getColumnModel();
        tcm.getColumn(tcm.getColumnIndex("Mã ĐKM")).setMinWidth(0);
        tcm.getColumn(tcm.getColumnIndex("Mã ĐKM")).setMaxWidth(0);
        tcm.getColumn(tcm.getColumnIndex("Mã Bia")).setMinWidth(0);
        tcm.getColumn(tcm.getColumnIndex("Mã Bia")).setMaxWidth(0);
        //set độ rộng cột 
        tcm.getColumn(tcm.getColumnIndex("Đợt KM được áp dụng")).setMinWidth(140);
        tcm.getColumn(tcm.getColumnIndex("STT")).setMaxWidth(35);
        tcm.getColumn(tcm.getColumnIndex("Trạng thái")).setMinWidth(100);
        tcm.getColumn(tcm.getColumnIndex("Loại bia")).setMinWidth(140);
        tcm.getColumn(tcm.getColumnIndex("Giá sau khi giảm")).setMinWidth(100);
        tcm.getColumn(tcm.getColumnIndex("Trạng thái")).setMinWidth(70);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private swing.ButtonCustom btnApDungKM;
    private swing.ButtonCustom btnClear;
    private swing.ButtonCustom btnThem;
    private swing.ButtonCustom btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbbDanhSachKM;
    private javax.swing.JComboBox<String> cbbLoaiKM;
    private javax.swing.JComboBox<String> cbbTenBia;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoChuaDienRa;
    private javax.swing.JRadioButton rdoDaKetThuc;
    private javax.swing.JRadioButton rdoDangDienRa;
    public javax.swing.JRadioButton rdoTatCa;
    private javax.swing.JTable tblDotKhuyenMai;
    private javax.swing.JTable tblSanPhamKM;
    private swing.ButtonCustom tbnSua;
    private com.toedter.calendar.JDateChooser txtEndDate;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMucKM;
    private com.toedter.calendar.JDateChooser txtNgayBatDau;
    private com.toedter.calendar.JDateChooser txtNgayKetThuc;
    private javax.swing.JTextField txtSearch;
    private com.toedter.calendar.JDateChooser txtStartDate;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
