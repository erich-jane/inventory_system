package InternalPages;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class adminDashboard extends javax.swing.JFrame {

    private String currentUser = "";
    private String currentRole = "";

    public adminDashboard(){ this("",""); }

    public adminDashboard(String currentUser, String currentRole) {
        this.currentUser = currentUser;
        this.currentRole = currentRole;
        initComponents();
        loadItemsToTable();
        // Only admins can manage users
        if(manageUsersBtn != null) manageUsersBtn.setVisible("admin".equalsIgnoreCase(this.currentRole));
    }

    private void initComponents(){
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Admin Dashboard");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ADMIN DASHBOARD");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 740, 40));

        // Items table
        itemsTable = new javax.swing.JTable();
        itemsTable.setModel(new DefaultTableModel(new String[]{"ID","Name","Quantity","Price"},0) {
            public boolean isCellEditable(int row, int column){ return false; }
        });
        javax.swing.JScrollPane jsp = new javax.swing.JScrollPane(itemsTable);
        jPanel1.add(jsp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 740, 220));

        // Price renderer to show peso sign (₱) while keeping model values numeric
        javax.swing.table.DefaultTableCellRenderer priceRenderer = new javax.swing.table.DefaultTableCellRenderer(){
            @Override
            protected void setValue(Object value){
                if(value instanceof Number){
                    setText("₱" + String.format("%.2f", ((Number)value).doubleValue()));
                } else {
                    setText(value==null?"":value.toString());
                }
            }
        };
        itemsTable.getColumnModel().getColumn(3).setCellRenderer(priceRenderer);

        // Buttons: Add, Edit, Delete, Refresh
        addBtn = new javax.swing.JPanel();
        editBtn = new javax.swing.JPanel();
        deleteBtn = new javax.swing.JPanel();
        refreshBtn = new javax.swing.JPanel();

        addLabel = new javax.swing.JLabel();
        editLabel = new javax.swing.JLabel();
        deleteLabel = new javax.swing.JLabel();
        refreshLabel = new javax.swing.JLabel();

        addBtn.setBackground(new java.awt.Color(204, 255, 204));
        addBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        addLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        addLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addLabel.setText("ADD");
        addBtn.add(addLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 70, -1));
        addBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ onAdd(); } });
        jPanel1.add(addBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 80, 35));

        editBtn.setBackground(new java.awt.Color(255, 255, 204));
        editBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        editLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        editLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        editLabel.setText("EDIT");
        editBtn.add(editLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 70, -1));
        editBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ onEdit(); } });
        jPanel1.add(editBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 80, 35));

        deleteBtn.setBackground(new java.awt.Color(255, 204, 204));
        deleteBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        deleteLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        deleteLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        deleteLabel.setText("DELETE");
        deleteBtn.add(deleteLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 70, -1));
        deleteBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ onDelete(); } });
        jPanel1.add(deleteBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 90, 35));

        refreshBtn.setBackground(new java.awt.Color(204, 229, 255));
        refreshBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        refreshLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        refreshLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        refreshLabel.setText("REFRESH");
        refreshBtn.add(refreshLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 80, -1));
        refreshBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ loadItemsToTable(); } });
        jPanel1.add(refreshBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 290, 100, 35));

        // Manage Users and Reports buttons
        manageUsersBtn = new javax.swing.JPanel();
        manageUsersBtn.setBackground(new java.awt.Color(204, 255, 229));
        manageUsersBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        manageUsersLabel = new javax.swing.JLabel();
        manageUsersLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        manageUsersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        manageUsersLabel.setText("MANAGE USERS");
        manageUsersBtn.add(manageUsersLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 120, -1));
        manageUsersBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ manageUsers mu = new manageUsers(); mu.setLocationRelativeTo(adminDashboard.this); mu.setVisible(true); } });
        jPanel1.add(manageUsersBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 335, 130, 40));

        reportsBtn = new javax.swing.JPanel();
        reportsBtn.setBackground(new java.awt.Color(229, 229, 255));
        reportsBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        reportsLabel = new javax.swing.JLabel();
        reportsLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        reportsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportsLabel.setText("REPORTS");
        reportsBtn.add(reportsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 80, -1));
        reportsBtn.addMouseListener(new MouseAdapter(){ public void mouseClicked(MouseEvent e){ reports r = new reports(); r.setLocationRelativeTo(adminDashboard.this); r.setVisible(true); } });
        jPanel1.add(reportsBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 335, 100, 40));

        logout.setBackground(new java.awt.Color(255, 204, 255));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) { logoutMouseClicked(evt); }
        });
        logout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        // reposition logout to the right side
        jPanel1.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 335, 80, 40));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("LOGOUT");
        logout.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 8, 80, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );

        setSize(760, 420);
        setLocationRelativeTo(null);
    }

    private void logoutMouseClicked(java.awt.event.MouseEvent evt){
        // show login centered then close admin dashboard
        loginForm lf = new loginForm();
        lf.setLocationRelativeTo(null);
        lf.setVisible(true);
        this.dispose();
    }

    // ----- CRUD helpers -----
    private void onAdd(){
        try{
            String name = JOptionPane.showInputDialog(this, "Item name:");
            if(name==null || name.trim().isEmpty()) return;
            String qtyS = JOptionPane.showInputDialog(this, "Quantity:"); if(qtyS==null) return;
            int qty = Integer.parseInt(qtyS.trim());
            String priceS = JOptionPane.showInputDialog(this, "Price (₱):"); if(priceS==null) return;
            // allow user to paste or type with/without currency symbol (e.g., ₱9.99)
            priceS = priceS.replaceAll("[^0-9.\\-]", "");
            double price = Double.parseDouble(priceS.trim());
            List<String[]> items = readItemsFromFile();
            int maxId = 0; for(String[] r: items){ try{ maxId = Math.max(maxId, Integer.parseInt(r[0])); }catch(Exception ex){}
            }
            int newId = maxId+1;
            String date = java.time.LocalDate.now().toString();
            items.add(new String[]{String.valueOf(newId), name.trim(), String.valueOf(qty), String.valueOf(price), date});
            writeItemsToFile(items);
            loadItemsToTable();
            JOptionPane.showMessageDialog(this, "Item added.");
        }catch(Exception e){ JOptionPane.showMessageDialog(this, "Invalid input: "+e.getMessage()); }
    }

    private void onEdit(){
        int r = itemsTable.getSelectedRow();
        if(r<0){ JOptionPane.showMessageDialog(this, "Select an item to edit."); return; }
        DefaultTableModel m = (DefaultTableModel)itemsTable.getModel();
        try{
            String id = String.valueOf(m.getValueAt(r,0));
            String name = JOptionPane.showInputDialog(this, "Item name:", String.valueOf(m.getValueAt(r,1)));
            if(name==null) return;
            String qtyS = JOptionPane.showInputDialog(this, "Quantity:", String.valueOf(m.getValueAt(r,2))); if(qtyS==null) return;
            int qty = Integer.parseInt(qtyS.trim());
            String priceS = JOptionPane.showInputDialog(this, "Price (₱):", String.valueOf(m.getValueAt(r,3))); if(priceS==null) return;
            // strip currency symbols that user may include
            priceS = priceS.replaceAll("[^0-9.\\-]", "");
            double price = Double.parseDouble(priceS.trim());
            List<String[]> items = readItemsFromFile();
            boolean updated = false;
            for(String[] it: items){ if(it[0].equals(id)){ // preserve date if present
                    String date = (it.length>=5)? it[4] : java.time.LocalDate.now().toString();
                    it[1]=name.trim(); it[2]=String.valueOf(qty); it[3]=String.valueOf(price); if(it.length>=5) it[4]=date; else it = new String[]{it[0], it[1], it[2], it[3], date}; updated = true; break; } }
            if(updated){ writeItemsToFile(items); loadItemsToTable(); JOptionPane.showMessageDialog(this, "Item updated."); }
            else JOptionPane.showMessageDialog(this, "Item not found in file.");
        }catch(Exception e){ JOptionPane.showMessageDialog(this, "Invalid input: "+e.getMessage()); }
    }

    private void onDelete(){
        int r = itemsTable.getSelectedRow();
        if(r<0){ JOptionPane.showMessageDialog(this, "Select an item to delete."); return; }
        DefaultTableModel m = (DefaultTableModel)itemsTable.getModel();
        String id = String.valueOf(m.getValueAt(r,0));
        int conf = JOptionPane.showConfirmDialog(this, "Delete selected item?", "Confirm", JOptionPane.YES_NO_OPTION);
        if(conf!=JOptionPane.YES_OPTION) return;
        try{
            List<String[]> items = readItemsFromFile();
            boolean removed = items.removeIf(it -> it[0].equals(id));
            if(removed){ writeItemsToFile(items); loadItemsToTable(); JOptionPane.showMessageDialog(this, "Item deleted."); }
            else JOptionPane.showMessageDialog(this, "Item not found in file.");
        }catch(Exception e){ JOptionPane.showMessageDialog(this, "Error deleting item: "+e.getMessage()); }
    }

    private void loadItemsToTable(){
        DefaultTableModel model = (DefaultTableModel)itemsTable.getModel();
        model.setRowCount(0);
        try{
            List<String[]> items = readItemsFromFile();
            for(String[] it: items){
                // items may have format: id,name,qty,price,date
                double price = Double.parseDouble(it[3]);
                model.addRow(new Object[]{Integer.parseInt(it[0]), it[1], Integer.parseInt(it[2]), price});
            }
        }catch(Exception e){ e.printStackTrace(); }
    }

    private List<String[]> readItemsFromFile() throws Exception{
        List<String[]> items = new ArrayList<>();
        File dir = new File("db"); if(!dir.exists()) dir.mkdirs();
        File f = new File(dir, "items.csv");
        if(!f.exists()){ try(BufferedWriter bw = new BufferedWriter(new FileWriter(f,true))){ bw.write("1,Sample Item A,10,9.99"); bw.newLine(); bw.write("2,Sample Item B,5,19.99"); bw.newLine(); } }
        try(BufferedReader br = new BufferedReader(new FileReader(f))){ String line; while((line = br.readLine())!=null){ String[] p = line.split(","); if(p.length>=5){ items.add(new String[]{p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), p[4].trim()}); } else if(p.length==4){ // missing date -> set to today
                        items.add(new String[]{p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), java.time.LocalDate.now().toString()});
                    } else if(p.length==3){ items.add(new String[]{String.valueOf(items.size()+1), p[0].trim(), p[1].trim(), p[2].trim(), java.time.LocalDate.now().toString()}); } } }
        return items;
    }

    private void writeItemsToFile(List<String[]> items) throws Exception{
        File dir = new File("db"); if(!dir.exists()) dir.mkdirs();
        File f = new File(dir, "items.csv");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(f,false))){
            for(String[] it: items){
                // ensure we write date column (5th) even if missing
                String[] out = it.length>=5? it : new String[]{it[0], it[1], it[2], it[3], java.time.LocalDate.now().toString()};
                bw.write(String.join(",", out)); bw.newLine();
            }
        }
    }

    // Variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel logout;
    private javax.swing.JLabel jLabel2;

    // new UI vars
    private javax.swing.JTable itemsTable;
    private javax.swing.JPanel addBtn, editBtn, deleteBtn, refreshBtn;
    private javax.swing.JLabel addLabel, editLabel, deleteLabel, refreshLabel;

    // manage users & reports
    private javax.swing.JPanel manageUsersBtn, reportsBtn;
    private javax.swing.JLabel manageUsersLabel, reportsLabel;
    // End variables
}
