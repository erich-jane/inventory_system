package InternalPages;

import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.Color;

/**
 *
 * @author canaz
 */
public class aboutPage extends JInternalFrame {

    public aboutPage() {
        initComponents();
        
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,0,0,0));
        BasicInternalFrameUI bi;
        bi = (BasicInternalFrameUI)this.getUI();
        bi.setNorthPane(null);
    }

    Color navcolor = new Color(255,0,255);
    Color headcolor = new Color(255,0,255);
    Color bodycolor = new Color(255,153,255);

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        closeBtn = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 0, 255));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 153, 255));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 24));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ABOUT");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(10, 10, 750, 50);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 780, 80);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Century Gothic", 0, 14));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setText("Ejane Shoes Inventory Management System\n\nEjane Shoes Inventory Management System is built to make stock control simple, accurate, and reliable for shoe retail operations. The system helps Ejane Shoes organize products by size, style, and category while maintaining clear visibility of available and low-stock items.\n\nBy automating inventory records and updates, the system reduces manual work, improves accuracy, and supports better planning for restocking and sales. It ensures that the right products are available when needed, helping the business operate smoothly and efficiently.\n\nWith a user-friendly interface and dependable inventory records, Ejane Shoes Inventory Management System enhances daily operations, supports informed decision-making, and contributes to improved customer satisfaction. It provides a structured and efficient way to manage shoe inventory while allowing the business to focus on growth and quality service.");
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(20, 100, 740, 280);

        closeBtn.setBackground(new java.awt.Color(153, 255, 255));
        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeBtnMouseExited(evt);
            }
        });
        closeBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("CLOSE");
        closeBtn.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 80, -1));

        jPanel1.add(closeBtn);
        closeBtn.setBounds(350, 400, 80, 40);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 450));

        pack();
    }

    private void closeBtnMouseClicked(java.awt.event.MouseEvent evt) {
        this.dispose();
    }

    private void closeBtnMouseEntered(java.awt.event.MouseEvent evt) {
        closeBtn.setBackground(navcolor);
    }

    private void closeBtnMouseExited(java.awt.event.MouseEvent evt) {
        closeBtn.setBackground(headcolor);
    }

    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel closeBtn;
    private javax.swing.JLabel jLabel2;
}
