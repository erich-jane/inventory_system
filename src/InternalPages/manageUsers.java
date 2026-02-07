package InternalPages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class manageUsers extends javax.swing.JFrame {

    public manageUsers() {
        initComponents();
        loadUsersToTable();
    }

    private void initComponents(){
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jsp = new javax.swing.JScrollPane();
        usersTable = new javax.swing.JTable();
        addBtn = new javax.swing.JPanel();
        addLabel = new javax.swing.JLabel();
        deleteBtn = new javax.swing.JPanel();
        deleteLabel = new javax.swing.JLabel();
        changeRoleBtn = new javax.swing.JPanel();
        changeRoleLabel = new javax.swing.JLabel();
        refreshBtn = new javax.swing.JPanel();
        refreshLabel = new javax.swing.JLabel();
        editBtn = new javax.swing.JPanel();
        editLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Users");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.setBackground(new java.awt.Color(255,240,245)); // soft overall background

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MANAGE USERS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, 40));

        usersTable.setModel(new DefaultTableModel(new String[]{"Username","Role"},0){ public boolean isCellEditable(int r,int c){return false;} });
        jsp.setViewportView(usersTable);
        jPanel1.add(jsp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 360, 180));

        addBtn = new javax.swing.JPanel();
        addBtn.setBackground(new java.awt.Color(255, 204, 255));
        addBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        addLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        addLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addLabel.setText("ADD");
        addBtn.add(addLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 70, -1));
        addBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ onAdd(); } });
        jPanel1.add(addBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 70, 30));

        deleteBtn = new javax.swing.JPanel();
        deleteBtn.setBackground(new java.awt.Color(255, 204, 204)); // keep subtle red
        deleteBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        deleteLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        deleteLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteLabel.setText("DELETE");
        deleteBtn.add(deleteLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 70, -1));
        deleteBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ onDelete(); } });
        jPanel1.add(deleteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, 70, 30));

        // Edit user button
        editBtn = new javax.swing.JPanel();
        editLabel = new javax.swing.JLabel();
        editBtn.setBackground(new java.awt.Color(204, 255, 229));
        editBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        editLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        editLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editLabel.setText("EDIT");
        editBtn.add(editLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 70, -1));
        editBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ onEditUser(); } });
        jPanel1.add(editBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 70, 30));

        changeRoleBtn.setBackground(new java.awt.Color(255, 255, 204));
        changeRoleBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        changeRoleLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        changeRoleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        changeRoleLabel.setText("TOGGLE ROLE");
        changeRoleBtn.add(changeRoleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 100, -1));
        changeRoleBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ onToggleRole(); } });
        jPanel1.add(changeRoleBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 250, 100, 30));

        refreshBtn.setBackground(new java.awt.Color(204, 229, 255));
        refreshBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        refreshLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        refreshLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        refreshLabel.setText("REFRESH");
        refreshBtn.add(refreshLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 80, -1));
        refreshBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ loadUsersToTable(); } });
        jPanel1.add(refreshBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 250, 80, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void onAdd(){
        // reuse existing registerForm
        new registerForm().setVisible(true);
    }

    private void onDelete(){
        int r = usersTable.getSelectedRow();
        if(r<0){ JOptionPane.showMessageDialog(this, "Select a user to delete."); return; }
        String username = String.valueOf(usersTable.getValueAt(r,0));
        int conf = JOptionPane.showConfirmDialog(this, "Delete user '"+username+"'?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(conf!=JOptionPane.YES_OPTION) return;
        try{
            List<String[]> users = readUsersList();
            boolean removed = users.removeIf(u -> u[0].equals(username));
            if(removed){ writeUsersList(users); loadUsersToTable(); JOptionPane.showMessageDialog(this, "User deleted."); }
            else JOptionPane.showMessageDialog(this, "User not found.");
        }catch(Exception e){ JOptionPane.showMessageDialog(this, "Error: "+e.getMessage()); }
    }

    private void onToggleRole(){
        int r = usersTable.getSelectedRow();
        if(r<0){ JOptionPane.showMessageDialog(this, "Select a user to change role."); return; }
        String username = String.valueOf(usersTable.getValueAt(r,0));
        try{
            List<String[]> users = readUsersList();
            boolean found=false;
            for(String[] u: users){ if(u[0].equals(username)){ u[2] = u[2].equals("admin")?"user":"admin"; found=true; break; } }
            if(found){ writeUsersList(users); loadUsersToTable(); JOptionPane.showMessageDialog(this, "Role toggled."); }
            else JOptionPane.showMessageDialog(this, "User not found.");
        }catch(Exception e){ JOptionPane.showMessageDialog(this, "Error: "+e.getMessage()); }
    }

    private void onEditUser(){
        int r = usersTable.getSelectedRow();
        if(r<0){ JOptionPane.showMessageDialog(this, "Select a user to edit."); return; }
        String username = String.valueOf(usersTable.getValueAt(r,0));
        try{
            List<String[]> users = readUsersList();
            for(int i=0;i<users.size();i++){
                String[] u = users.get(i);
                if(u[0].equals(username)){
                    String newUser = JOptionPane.showInputDialog(this, "New username:", u[0]); if(newUser==null || newUser.trim().isEmpty()) return;
                    // check duplicate
                    boolean dup=false; for(String[] x: users){ if(!x[0].equals(u[0]) && x[0].equals(newUser.trim())){ dup=true; break; } }
                    if(dup){ JOptionPane.showMessageDialog(this, "Username already exists."); return; }
                    javax.swing.JPasswordField pf = new javax.swing.JPasswordField();
                    int ok = JOptionPane.showConfirmDialog(this, pf, "New password (leave blank to keep):", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if(ok != JOptionPane.OK_OPTION) return;
                    char[] arr = pf.getPassword();
                    String newPass = new String(arr).trim();
                    java.util.Arrays.fill(arr, '\0');
                    if(!newPass.isEmpty()){
                        u[1] = hash(newPass);
                    }
                    u[0] = newUser.trim();
                    users.set(i, u);
                    writeUsersList(users); loadUsersToTable(); JOptionPane.showMessageDialog(this, "User updated.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "User not found.");
        }catch(Exception e){ JOptionPane.showMessageDialog(this, "Error: "+e.getMessage()); }
    }

    private String hash(String s){
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] b = md.digest(s.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(byte by: b) sb.append(String.format("%02x", by));
            return sb.toString();
        } catch(Exception e){ return s; }
    }

    private void loadUsersToTable(){
        DefaultTableModel m = (DefaultTableModel)usersTable.getModel(); m.setRowCount(0);
        try{
            List<String[]> users = readUsersList();
            for(String[] u: users){ m.addRow(new Object[]{u[0], u[2]}); }
        }catch(Exception e){ e.printStackTrace(); }
    }

    private List<String[]> readUsersList() throws Exception{
        List<String[]> list = new ArrayList<>();
        File f = new File("users.txt"); if(!f.exists()) return list;
        try(BufferedReader br = new BufferedReader(new FileReader(f))){ String line; while((line=br.readLine())!=null){ String[] p = line.split(":"); if(p.length>=2){ String role=(p.length>=3)?p[2]:"user"; list.add(new String[]{p[0], p[1], role}); } } }
        return list;
    }

    private void writeUsersList(List<String[]> users) throws Exception{
        File f = new File("users.txt"); try(BufferedWriter bw = new BufferedWriter(new FileWriter(f,false))){ for(String[] u: users){ bw.write(u[0]+":"+u[1]+":"+u[2]); bw.newLine(); } }
    }

    // Variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jsp;
    private javax.swing.JTable usersTable;
    private javax.swing.JPanel addBtn, deleteBtn, changeRoleBtn, refreshBtn, editBtn;
    private javax.swing.JLabel addLabel, deleteLabel, changeRoleLabel, refreshLabel, editLabel;
}
