package utilities;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import domainModel.BiaKhuyenMai;
import domainModel.DotKhuyenMai;
import domainModel.HoaDon;
import domainModel.HoaDonChiTiet;
import domainModel.SanPhamChiTiet;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import repositories.SPCTRepo;
import service.ServiceDotKhuyenMai;
import service.ServiceHoaDon;
import service.ServiceHoaDonChiTiet;
import service.impl.ServiceDotKhuyenMaiImpl;
import service.impl.ServiceHoaDonChiTietImpl;
import service.impl.ServiceHoaDonImpl;

/**
 *
 * @author dongl
 */
public class ExportFilePdf {

    public static final String pathUnicode = "font\\unicode.ttf";
    public static String path = "";

    public static void exportBill(HoaDon hd) {
        ServiceHoaDon ss = new ServiceHoaDonImpl();
        ServiceHoaDonChiTiet ssHDCT = new ServiceHoaDonChiTietImpl();
        ServiceDotKhuyenMai ssDKM = new ServiceDotKhuyenMaiImpl();
        SPCTRepo ssSPCT = new SPCTRepo();
        DecimalFormat df = new DecimalFormat("#,###");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String date = sdf.format(new Date());
            path = hd.getMa().toLowerCase() + date + ".pdf";
            PdfWriter pdfWriter = new PdfWriter(path);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            float col = 280f;
            float columWidth[] = {col, col};

            PdfFont font = PdfFontFactory.createFont(pathUnicode, BaseFont.IDENTITY_H);

            Table table = new Table(columWidth);
            table.setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE);
            table.setFont(font);

            table.addCell(new Cell().add("Bill Bán Bia").setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setMarginTop(30f)
                    .setMarginBottom(30f)
                    .setFontSize(30f)
                    .setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Mã hóa đơn: \n" + hd.getMa()).setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(30f)
                    .setMarginBottom(30f)
                    .setBorder(Border.NO_BORDER)
                    .setMarginRight(10f));

            float colWidth[] = {80, 250, 200, 200};
            Table customerInforTable = new Table(colWidth);
            customerInforTable.setFont(font);
            customerInforTable.addCell(new Cell(0, 4)
                    .add("Thông tin khách hàng").setBold().setBorder(Border.NO_BORDER));

            customerInforTable.addCell(new Cell().add("Họ tên:").setBorder(Border.NO_BORDER));
            customerInforTable.addCell(new Cell().add(hd.getKhachHang() == null ? "Không có thông tin" : hd.getKhachHang().getHoTen()).setBorder(Border.NO_BORDER));
            customerInforTable.addCell(new Cell().add("Số điện thoại:").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
            customerInforTable.addCell(new Cell().add(hd.getKhachHang() == null ? "Không có thông tin" : hd.getKhachHang().getSdt()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
            customerInforTable.addCell(new Cell().add("Địa chỉ:").setBorder(Border.NO_BORDER));
            customerInforTable.addCell(new Cell().add(hd.getKhachHang() == null ? "Không có thông tin" : hd.getKhachHang().getDiaChi()).setBorder(Border.NO_BORDER));
            customerInforTable.addCell(new Cell().add("Ngày thanh toán:").setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
            customerInforTable.addCell(new Cell().add(hd.getNgayThanhToan().getHours() + ":" + hd.getNgayThanhToan().getMinutes()
                    + "  " + sdf.format(hd.getNgayThanhToan())).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));

            float itemColWidth[] = {15, 110, 110, 110, 110, 110};
            Table itemTable = new Table(itemColWidth);
            itemTable.setFont(font);
            itemTable.addCell(new Cell().add("STT").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
            itemTable.addCell(new Cell().add("Tên sản phẩm").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
            itemTable.addCell(new Cell().add("Thông tin SP").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
            itemTable.addCell(new Cell().add("Số lượng").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
            itemTable.addCell(new Cell().add("Giá bán").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
            itemTable.addCell(new Cell().add("Thành tiền").setBackgroundColor(new DeviceRgb(63, 169, 219)).setFontColor(Color.WHITE));
            int stt = 1;
            BigDecimal donGia = new BigDecimal(0);
            for (HoaDonChiTiet x : ssHDCT.getListByMaHD(hd.getMa())) {
                SanPhamChiTiet spct = ssSPCT.findByMa(x.getIdChiTietSanPham().getMa());
                BiaKhuyenMai biaKM = new BiaKhuyenMai();
                List<DotKhuyenMai> km = ssDKM.getDKMByDate(new Date());
                for (DotKhuyenMai dotKhuyenMai : km) {
                    System.out.println(dotKhuyenMai.getId());
                    if (ssDKM.getBiaKhuyenMai(spct, dotKhuyenMai) != null) {
                        biaKM = ssDKM.getBiaKhuyenMai(spct, dotKhuyenMai);
                        System.out.println(biaKM.getGiaConLai());
                        spct.setGiaBan(biaKM.getGiaConLai());
                    }
                }
                if (x.getIdChiTietSanPham().getTrangThai() == 0) {
                    donGia = x.getDonGia();
                } else {
                    donGia = x.getDonGia().multiply(new BigDecimal(24).multiply(new BigDecimal(0.9)));
                }
                itemTable.addCell(new Cell().add((stt++) + ""));
                itemTable.addCell(new Cell().add(x.getIdChiTietSanPham().getBia().getTen()));
                itemTable.addCell(new Cell().add(x.getIdChiTietSanPham().getLoaiSP().getTen()));
                itemTable.addCell(new Cell().add(x.getSoLuong() + " " + (x.getIdChiTietSanPham().getTrangThai() == 0 ? "Lon" : "Thùng")));
                itemTable.addCell(new Cell().add(df.format(spct.getGiaBan()) + ""));
                itemTable.addCell(new Cell().add(df.format(donGia) + ""));
                //itemTable.addCell(new Cell().add(df.format(x.getDonGia().multiply(BigDecimal.valueOf(spct.getSoLuongLonBia()))) + ""));
            }

            itemTable.addCell(new Cell().add("").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
            itemTable.addCell(new Cell().add("").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
            itemTable.addCell(new Cell().add("").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
            itemTable.addCell(new Cell().add("").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER));
            itemTable.addCell(new Cell().add("Tổng tiền").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER).setFontColor(Color.WHITE));
            itemTable.addCell(new Cell().add(df.format(hd.getTongTien()) + " Vnđ").setBackgroundColor(new DeviceRgb(63, 169, 219)).setBorder(Border.NO_BORDER).setFontColor(Color.WHITE));

            document.add(table);
            document.add(new Paragraph("\n"));
            document.add(customerInforTable);
            document.add(new Paragraph("\n"));
            document.add(itemTable);
            document.close();
            System.out.println("Xuất hóa đơn thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        new ExportFilePdf().exportBill(null);
        File file = new File(path);
        Desktop.getDesktop().open(file);
        //Document document = new Document(pdfDoc)

    }
}
