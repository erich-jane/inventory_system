package InternalPages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class productsPage extends javax.swing.JInternalFrame {
    private javax.swing.JDesktopPane parentDesktop;
    
    public productsPage(){ 
        this(null);
    }
    
    public productsPage(javax.swing.JDesktopPane parentDesktop){ 
        this.parentDesktop = parentDesktop;
        initComponents(); 
        loadProducts(); 
    }

    private void initComponents(){
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jsp = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        refreshBtn = new javax.swing.JPanel();
        refreshLabel = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Products");

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.setBackground(new java.awt.Color(255,240,245)); // soft pink background

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 16));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PRODUCTS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 740, 30));

        productsTable.setModel(new DefaultTableModel(new String[]{"ID","Name","Quantity","Price"},0){ public boolean isCellEditable(int r,int c){return false;} });
        jsp.setViewportView(productsTable);
        jPanel1.add(jsp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 740, 310));

        refreshBtn.setBackground(new java.awt.Color(255, 204, 255)); // girly accent
        refreshBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        refreshLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        refreshLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        refreshLabel.setText("REFRESH");
        refreshBtn.add(refreshLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 80, -1));
        refreshBtn.addMouseListener(new java.awt.event.MouseAdapter(){ public void mouseClicked(java.awt.event.MouseEvent e){ loadProducts(); } });
        jPanel1.add(refreshBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 370, 80, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 420));

        // peso renderer
        javax.swing.table.DefaultTableCellRenderer priceRenderer = new javax.swing.table.DefaultTableCellRenderer(){
            @Override
            protected void setValue(Object value){
                if(value instanceof Number){ setText("₱" + String.format("%.2f", ((Number)value).doubleValue())); }
                else setText(value==null?"":value.toString());
            }
        };
        productsTable.getColumnModel().getColumn(3).setCellRenderer(priceRenderer);

        pack();
    }

    private void loadProducts(){
        DefaultTableModel m = (DefaultTableModel)productsTable.getModel(); m.setRowCount(0);
        try{
            File dir = new File("db"); if(!dir.exists()) dir.mkdirs();
            File f = new File(dir, "items.csv"); if(!f.exists()) return;
            try(BufferedReader br = new BufferedReader(new FileReader(f))){ String line; while((line=br.readLine())!=null){ String[] p = line.split(","); if(p.length>=4){ double price = Double.parseDouble(p[3].trim()); m.addRow(new Object[]{Integer.parseInt(p[0].trim()), p[1].trim(), Integer.parseInt(p[2].trim()), price}); } } }
        }catch(Exception e){ e.printStackTrace(); }
    }

    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jsp;
    private javax.swing.JTable productsTable;
    private javax.swing.JPanel refreshBtn;
    private javax.swing.JLabel refreshLabel;

    // Using JInternalFrame; no custom setLocationRelativeTo needed.

    void setLocationRelativeTo(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
