/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import domainModel.KhachHang;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import repositories.KhachHangRepository;

/**
 *
 * @author SoiDiCode
 */
public class Ex_Im_ExcelKH {
    public static void export(List<KhachHang> list, String[] nameColumn , String nameSheet,String fileName){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(nameSheet);
            XSSFRow row = null;
            XSSFCell cell = null;
            //create row white
            row = sheet.createRow(0);
            
            
            for (int i = 0; i < nameColumn.length; i++) {
                cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                cell.setCellValue(nameColumn[i]);
            }
            
            for (int i = 0; i < list.size(); i++) {
                row = sheet.createRow(i);
                
                cell = row.createCell(0, Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(i + 1);
                
                cell = row.createCell(1, Cell.CELL_TYPE_STRING);
                cell.setCellValue(list.get(i).getMa());
                
                cell = row.createCell(2, Cell.CELL_TYPE_STRING);
                cell.setCellValue(list.get(i).getHoTen());
                
                cell = row.createCell(3, Cell.CELL_TYPE_STRING);
                cell.setCellValue(list.get(i).getDiaChi());
                
                cell = row.createCell(4, Cell.CELL_TYPE_STRING);
                cell.setCellValue(list.get(i).getSdt());
                
                cell = row.createCell(5, Cell.CELL_TYPE_STRING);
                cell.setCellValue(list.get(i).getGioiTinh() == 0 ? "Nữ" : "Nam");
                
                cell = row.createCell(6, Cell.CELL_TYPE_NUMERIC);
                cell.setCellValue(list.get(i).getTichDiem());                
                
            }
            
            File f = new File(fileName);
            
            try {
                FileOutputStream fos = new FileOutputStream(f);
                workbook.write(fos);
                fos.close();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
            JOptionPane.showMessageDialog(null, "Xuat thanh cong");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Xuat that bai");
        }
    }
    
    public List<KhachHang> importExcel(){
         List<KhachHang> list = new ArrayList<>();
        try {
           
            File file;
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            String defaultPath = "D:\\excelKH";
            JFileChooser chooser = new JFileChooser(defaultPath);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", "xls","xlsx","xlsm");
            chooser.setFileFilter(filter);
            int kq = chooser.showOpenDialog(null);
            if (kq == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                XSSFWorkbook workbook = new XSSFWorkbook(bis);
                
                XSSFSheet sheet = workbook.getSheetAt(0);
                for (int row = 0; row < sheet.getLastRowNum(); row++) {
                   XSSFRow excelRow = sheet.getRow(row);
                    
                   XSSFCell cellSTT = excelRow.getCell(0);
                   XSSFCell cellMa = excelRow.getCell(1);
                   XSSFCell cellHoTen = excelRow.getCell(2);
                   XSSFCell cellDiaChi = excelRow.getCell(3);
                   XSSFCell cellSDT = excelRow.getCell(4);
                   XSSFCell cellGioiTinh = excelRow.getCell(5);
                   XSSFCell cellDiem = excelRow.getCell(6);
                   
                   int diem = (int)Double.parseDouble(cellDiem + "");
//                    DecimalFormat format = new DecimalFormat("#");
//                    BigDecimal bd = new BigDecimal(cellDiem.toString());
////                    System.out.println(String.format("%0f",diem));
//                      System.out.println(diem);

                 
                   list.add(
                           new KhachHang(null,cellMa+"" , cellHoTen+"",(cellGioiTinh+"").equalsIgnoreCase("Nữ")?0:1,cellSDT+"",
                                    cellDiaChi+"",diem)
                   ) ;
                   
                }
                JOptionPane.showMessageDialog(null,"Import successfull !!!");
            }else{
                JOptionPane.showMessageDialog(null,"Import failed !!!");
            }
             
         }catch (FileNotFoundException ex) {
           
          } catch (IOException ex) {
           
        }
        return list;
    }
    public static void main(String[] args) {
        Ex_Im_ExcelKH exportExcel = new Ex_Im_ExcelKH();
//        exportExcel.importExcel().forEach(s -> System.out.println(s.toString()));
         List<KhachHang> hangs = new KhachHangRepository().getAll();
         String[] tieuDe = new String[]{
             "STT","Mã KH","Họ Tên","Địa Chỉ","SDT","Giới Tính","Số Điểm"
         };
        
    
    }
}
