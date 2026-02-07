package InternalPages;

import Config.config;
import java.awt.BasicStroke;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.io.*;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class registerForm extends javax.swing.JFrame {

    public registerForm() {
        initComponents();
    }

    Color hover = new Color(255,0,204);
    Color defbutton = new Color(153,255,255);
    Border empty = BorderFactory.createEmptyBorder();

    void buttonBorderAnimation(JPanel panel){
       panel.setBackground(hover);
       panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
       panel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2.0f)));
    }

    void buttonDefaultColor(JPanel panel){
        panel.setBackground(defbutton);
        panel.setBorder(empty);
    }

    private void initComponents(){
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        fullname = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        confirm = new javax.swing.JPasswordField();
        register = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cancel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Register");

        jPanel1.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(255, 204, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 16));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Create new account");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 290, 30));

        email.setFont(new java.awt.Font("Century Gothic", 1, 12));
        email.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Email (Username)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14)));
        jPanel3.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 260, 50));

        fullname.setFont(new java.awt.Font("Century Gothic", 1, 12));
        fullname.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Full Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14)));
        jPanel3.add(fullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 260, 50));

        location = new javax.swing.JTextField();
        location.setFont(new java.awt.Font("Century Gothic", 1, 12));
        location.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Location", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14)));
        jPanel3.add(location, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 260, 50));

        password.setFont(new java.awt.Font("Century Gothic", 1, 12));
        password.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14)));
        jPanel3.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 260, 50));

        confirm.setFont(new java.awt.Font("Century Gothic", 1, 12));
        confirm.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3), "Confirm Password", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 14)));
        jPanel3.add(confirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 260, 50));

        register.setBackground(new java.awt.Color(255, 204, 255));
        register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { registerMouseEntered(evt); }
            public void mouseExited(java.awt.event.MouseEvent evt) { registerMouseExited(evt); }
            public void mouseClicked(java.awt.event.MouseEvent evt) { registerMouseClicked(evt); }
        });
        register.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("REGISTER");
        register.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 80, -1));

        jPanel3.add(register, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, -1, 40));

        cancel.setBackground(new java.awt.Color(255, 204, 255));
        cancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { cancelMouseEntered(evt); }
            public void mouseExited(java.awt.event.MouseEvent evt) { cancelMouseExited(evt); }
            public void mouseClicked(java.awt.event.MouseEvent evt) { cancelMouseClicked(evt); }
        });
        cancel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("CANCEL");
        cancel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 80, -1));

        jPanel3.add(cancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 40));

        jPanel1.add(jPanel3);
        jPanel3.setBounds(10, 10, 310, 480);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void registerMouseEntered(java.awt.event.MouseEvent evt) { buttonBorderAnimation(register); }
    private void registerMouseExited(java.awt.event.MouseEvent evt) { buttonDefaultColor(register); }
    private void cancelMouseEntered(java.awt.event.MouseEvent evt) { buttonBorderAnimation(cancel); }
    private void cancelMouseExited(java.awt.event.MouseEvent evt) { buttonDefaultColor(cancel); }

    private void cancelMouseClicked(java.awt.event.MouseEvent evt) { this.dispose(); }

    private void registerMouseClicked(java.awt.event.MouseEvent evt) {
           String Email = email.getText().trim();
    String Fullname = fullname.getText().trim();
    String Location = location.getText().trim();
    String Password = new String(password.getPassword()).trim();
    String Confirm = new String(confirm.getPassword()).trim();

    // Validate fields
    if (Email.isEmpty() || Fullname.isEmpty() || Location.isEmpty() || Password.isEmpty() || Confirm.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!Password.equals(Confirm)) {
        JOptionPane.showMessageDialog(this, "Passwords do not match!", "Validation Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        config con = new config();
        String sql = "INSERT INTO name(name, email, password, location) VALUES (?,?,?,?)";
        con.addRecord(sql, Fullname, Email, Password, Location);

        JOptionPane.showMessageDialog(this, "Record added successfully!");

        // Clear fields
        fullname.setText("");
        email.setText("");
        location.setText("");
        password.setText("");
        confirm.setText("");
        
  this.dispose(); // close the register form
        loginForm login = new loginForm(); // create new login form instance
        login.setVisible(true); // s
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    
    }//GEN-LAST:event_registerbt
    }

 

  

    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField email;
    private javax.swing.JTextField fullname;
    private javax.swing.JTextField location;
    private javax.swing.JPasswordField password;
    private javax.swing.JPasswordField confirm;
    private javax.swing.JPanel register;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel cancel;
    private javax.swing.JLabel jLabel3;
}
