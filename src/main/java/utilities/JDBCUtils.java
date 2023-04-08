/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author SoiDiCode
 */
public class JDBCUtils {
    private static final String DRIVER ="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URLPATH = "jdbc:sqlserver://localhost:1433;databaseName=";
    private static final String USER ="sa";
    private static final String PASS ="123456789aA@";
    private static final String DATABASE ="DuAn1Nhom1";
    
    public static Connection getConnection(){
        try {
            Class.forName(DRIVER);
            String url = URLPATH + DATABASE + ";encrypt=true;trustServerCertificate=true;";
           Connection conn = DriverManager.getConnection(url, USER, PASS);
           return conn;
        } catch(Exception e){
           e.printStackTrace();
        }
       return null;
    }
    public static void main(String[] args) {
        System.out.println("Connection " + JDBCUtils.getConnection());
    }
}
