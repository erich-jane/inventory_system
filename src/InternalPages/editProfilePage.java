package InternalPages;

import Config.config;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author canaz
 */
public class editProfilePage extends javax.swing.JFrame {

    private String userEmail = "";
    private settingspage parentPage = null;

    public editProfilePage(String userEmail, settingspage parent) {
        this.userEmail = userEmail;
        this.parentPage = parent;
        initComponents();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        loadCurrentData();
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        locationLabel = new javax.swing.JLabel();
        locationField = new javax.swing.JTextField();
        saveBtn = new javax.swing.JPanel();
        saveLabel = new javax.swing.JLabel();
        cancelBtn = new javax.swing.JPanel();
        cancelLabel = new javax.swing.JLabel();

        setTitle("Edit Profile");

        jPanel1.setBackground(new java.awt.Color(255, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 20));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("EDIT PROFILE");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 340, 40));

        nameLabel.setFont(new java.awt.Font("Century Gothic", 1, 14));
        nameLabel.setText("Full Name:");
        jPanel1.add(nameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 150, 25));

        nameField.setFont(new java.awt.Font("Century Gothic", 0, 12));
        nameField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel1.add(nameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 300, 40));

        locationLabel.setFont(new java.awt.Font("Century Gothic", 1, 14));
        locationLabel.setText("Location:");
        jPanel1.add(locationLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 150, 25));

        locationField.setFont(new java.awt.Font("Century Gothic", 0, 12));
        locationField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel1.add(locationField, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 300, 40));

        saveBtn.setBackground(new java.awt.Color(153, 255, 255));
        saveBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        saveBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        saveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveBtnMouseClicked(evt);
            }
        });

        saveLabel.setFont(new java.awt.Font("Century Gothic", 1, 14));
        saveLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        saveLabel.setText("SAVE");
        saveBtn.add(saveLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 8, 80, 25));

        jPanel1.add(saveBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, 80, 40));

        cancelBtn.setBackground(new java.awt.Color(255, 204, 255));
        cancelBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        cancelBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        cancelBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelBtnMouseClicked(evt);
            }
        });

        cancelLabel.setFont(new java.awt.Font("Century Gothic", 1, 14));
        cancelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cancelLabel.setText("CANCEL");
        cancelBtn.add(cancelLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 8, 80, 25));

        jPanel1.add(cancelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 80, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
        );

        setSize(360, 320);
        setLocation(100, 100);
    }

    private void loadCurrentData() {
        if (userEmail == null || userEmail.isEmpty()) return;
        File f = Storage.getUsersFile();
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 1 && parts[0].equalsIgnoreCase(userEmail)) {
                    String fullname = (parts.length >= 4) ? parts[3] : "";
                    String location = (parts.length >= 5) ? parts[4] : "";
                    nameField.setText(fullname);
                    locationField.setText(location);
                    return;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveBtnMouseClicked(java.awt.event.MouseEvent evt) {
        String newName = nameField.getText().trim();
        String newLocation = locationField.getText().trim();

        if (newName.isEmpty() || newLocation.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (newName.length() < 3) {
            JOptionPane.showMessageDialog(this, "Full name must be at least 3 characters", "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (newLocation.length() < 3) {
            JOptionPane.showMessageDialog(this, "Location must be at least 3 characters", "Invalid Location", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Update users (file and DB)
        boolean okFile = updateUserData(userEmail, newName, newLocation);
        boolean okDb = true;
        try{ okDb = config.updateUserProfile(userEmail, newName, newLocation); }catch(Exception ex){ okDb = false; }
        if (okFile || okDb) {
            JOptionPane.showMessageDialog(this, "Profile updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            if (this.parentPage != null) {
                this.parentPage.refreshData();
            }
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update profile", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelBtnMouseClicked(java.awt.event.MouseEvent evt) {
        this.dispose();
    }

    private boolean updateUserData(String email, String fullname, String location) {
        File f = Storage.getUsersFile();
        if (!f.exists()) return false;

        try {
            // Read all users
            Map<String, String[]> users = new HashMap<>();
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length >= 2) {
                        String uname = parts[0];
                        String hash = parts.length>=2?parts[1]:"";
                        String role = parts.length>=3?parts[2]:"user";
                        String oldFullname = parts.length>=4?parts[3]:"";
                        String oldLocation = parts.length>=5?parts[4]:"";
                        String uid = parts.length>=6?parts[5]:"";
                        users.put(uname, new String[]{hash, role, oldFullname, oldLocation, uid});
                    }
                }
            }

            // Update the specific user
            if (users.containsKey(email)) {
                String[] userData = users.get(email);
                // preserve uid if present
                String uid = userData.length>=5?userData[4]:"";
                userData = new String[]{userData[0], userData[1], fullname, location, uid};
                users.put(email, userData);
            } else {
                return false;
            }

            // Write back to file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, false))) {
                for (String key : users.keySet()) {
                    String[] data = users.get(key);
                    String uid = data.length>=5?data[4]:"";
                    bw.write(key + ":" + data[0] + ":" + data[1] + ":" + data[2] + ":" + data[3] + (uid.isEmpty()?"":":" + uid));
                    bw.newLine();
                }
            }

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JTextField locationField;
    private javax.swing.JPanel saveBtn;
    private javax.swing.JLabel saveLabel;
    private javax.swing.JPanel cancelBtn;
    private javax.swing.JLabel cancelLabel;

    // No-op placeholder removed. JFrame is used; setClosable not applicable.
}
