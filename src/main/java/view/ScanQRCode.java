package view;

import com.github.sarxos.webcam.*;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import org.jboss.logging.Logger;

/**
 *
 * @author NGUYEN VAN HOI
 */
public class ScanQRCode extends javax.swing.JFrame implements Runnable, ThreadFactory {

    /**
     * Creates new form TestQRCode
     */
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    public ScanQRCode() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Scan Barcode");
        setSize(430, 350);
        setResizable(false);
        initWebcam();

    }

    private void initWebcam() {

        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);
        jPanel1.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, size.width, size.height));
        executor.execute(this);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 29, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ScanQRCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScanQRCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScanQRCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScanQRCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ScanQRCode().setVisible(true);
            }
        });
    }

    Result result = null;

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (IncompatibleClassChangeError e) {
                Logger.getLogger(ScanQRCode.class.getName()).log(Logger.Level.FATAL, null, e);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(ScanQRCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedImage image = null;
            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    continue;
                }
            }

            try {
                result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image))));
                System.out.println("result " + result.getText());
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(ScanQRCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result != null) {
                System.out.println(result.getText());
                webcam.close();
                this.setVisible(false);
                if (callback != null) {
                    callback.onScanCompleted(result.getText());
                }
                Thread.currentThread().stop();
            }

        } while (true);
    }

    public String getText() {
        return result.getText();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t1 = new Thread(r, "MyThread");
        return t1;
    }

    public interface QRCodeScanCallback {

        void onScanCompleted(String result);
    }
    private QRCodeScanCallback callback;

    public void setCallback(QRCodeScanCallback callback) {
        this.callback = callback;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
