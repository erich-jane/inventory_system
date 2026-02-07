package InternalPages;

import java.awt.HeadlessException;
import java.awt.print.PrinterException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class reports extends javax.swing.JFrame {
    public reports(){ 
        initComponents();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        loadReport();
    }

    private void initComponents(){
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        totalItemsLabel = new javax.swing.JLabel();
        totalQtyLabel = new javax.swing.JLabel();
        totalValueLabel = new javax.swing.JLabel();
        jsp = new javax.swing.JScrollPane();
        itemsTable = new javax.swing.JTable();

        setTitle("Reports");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SHOP REPORTS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, 40));

        totalItemsLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        totalItemsLabel.setText("Total distinct items: 0");
        jPanel1.add(totalItemsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 360, 20));

        totalQtyLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        totalQtyLabel.setText("Total quantity: 0");
        jPanel1.add(totalQtyLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, 360, 20));

        totalValueLabel.setFont(new java.awt.Font("Century Gothic", 1, 12));
        totalValueLabel.setText("Total inventory value: ₱0.00");
        jPanel1.add(totalValueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 360, 20));

        // page background
        jPanel1.setBackground(new java.awt.Color(255, 240, 245));

        // Date filter inputs
        startDateField = new javax.swing.JTextField();
        endDateField = new javax.swing.JTextField();
        startDateField.setBorder(javax.swing.BorderFactory.createTitledBorder("Start (yyyy-MM-dd)"));
        endDateField.setBorder(javax.swing.BorderFactory.createTitledBorder("End (yyyy-MM-dd)"));
        jPanel1.add(startDateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 135, 160, 40));
        jPanel1.add(endDateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 135, 160, 40));

        // Filter & export buttons
        filterBtn = new javax.swing.JPanel(); filterBtn.setBackground(new java.awt.Color(204, 255, 204)); filterBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        javax.swing.JLabel filterLabel = new javax.swing.JLabel(); filterLabel.setFont(new java.awt.Font("Century Gothic", 1, 12)); filterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); filterLabel.setText("FILTER");
        filterBtn.add(filterLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,5,70,-1)); filterBtn.addMouseListener(new java.awt.event.MouseAdapter(){@Override
 public void mouseClicked(java.awt.event.MouseEvent e){ loadReport(startDateField.getText().trim(), endDateField.getText().trim()); } });
        jPanel1.add(filterBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 70, 30));

        exportCsvBtn = new javax.swing.JPanel(); exportCsvBtn.setBackground(new java.awt.Color(204, 229, 255)); exportCsvBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        javax.swing.JLabel exportCsvLabel = new javax.swing.JLabel(); exportCsvLabel.setFont(new java.awt.Font("Century Gothic", 1, 12)); exportCsvLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); exportCsvLabel.setText("EXPORT CSV");
        exportCsvBtn.add(exportCsvLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,5,100,-1)); exportCsvBtn.addMouseListener(new java.awt.event.MouseAdapter(){ public void mouseClicked(java.awt.event.MouseEvent e){ exportCsv(); } });
        jPanel1.add(exportCsvBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 100, 30));

        printBtn = new javax.swing.JPanel(); printBtn.setBackground(new java.awt.Color(255, 229, 204)); printBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        javax.swing.JLabel printLabel = new javax.swing.JLabel(); printLabel.setFont(new java.awt.Font("Century Gothic", 1, 12)); printLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); printLabel.setText("PRINT/PDF");
        printBtn.add(printLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,5,100,-1)); printBtn.addMouseListener(new java.awt.event.MouseAdapter(){ public void mouseClicked(java.awt.event.MouseEvent e){ printReport(); } });
        jPanel1.add(printBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 100, 30));

        itemsTable.setModel(new DefaultTableModel(new String[]{"ID","Name","Qty","Available","Price","Subtotal"},0){ public boolean isCellEditable(int r,int c){return false;} });
        jsp.setViewportView(itemsTable);
        jPanel1.add(jsp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 360, 120));

        getContentPane().setLayout(new javax.swing.BorderLayout());
        getContentPane().add(jPanel1, javax.swing.BorderLayout.CENTER);
javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE));
        
        setLocation(50, 50);
    }

    private void loadReport(){ loadReport(null, null); }

    private void loadReport(String startDate, String endDate){
        try{
            java.time.LocalDate sd = null, ed = null;
            if(startDate!=null && !startDate.isEmpty()) sd = java.time.LocalDate.parse(startDate);
            if(endDate!=null && !endDate.isEmpty()) ed = java.time.LocalDate.parse(endDate);

            File f = Storage.getItemsFile(); if(!f.exists()) return;
            List<String[]> rows = new ArrayList<>();
            try(BufferedReader br = new BufferedReader(new FileReader(f))){
                String line;
                while((line = br.readLine()) != null){
                    String[] p = line.split(",");
                    if(p.length>=4){
                        String date = (p.length>=5)?p[4].trim():java.time.LocalDate.now().toString();
                        rows.add(new String[]{p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), date});
                    }
                }
            }

            int totalItems = 0;
            int totalQty = 0; double totalValue = 0;
            DefaultTableModel m = (DefaultTableModel)itemsTable.getModel(); m.setRowCount(0);
            for(String[] r: rows){
                java.time.LocalDate d = java.time.LocalDate.parse(r[4]);
                if(sd!=null && d.isBefore(sd)) continue;
                if(ed!=null && d.isAfter(ed)) continue;
                int qty = Integer.parseInt(r[2]);
                double price = Double.parseDouble(r[3]);
                double sub = qty*price;
                m.addRow(new Object[]{Integer.parseInt(r[0]), r[1], qty, qty, String.format("₱%.2f", price), String.format("₱%.2f", sub)});
                totalQty += qty; totalValue += sub; totalItems++;
            }
            totalItemsLabel.setText("Total distinct items: "+totalItems);
            totalQtyLabel.setText("Total quantity: "+totalQty);
            totalValueLabel.setText(String.format("Total inventory value: ₱%.2f", totalValue));
        }catch(Exception e){ e.printStackTrace(); }
    }

    private void exportCsv(){
        try{
            DefaultTableModel m = (DefaultTableModel)itemsTable.getModel();
            File out = new File(Storage.getReportsDir(), "report_"+System.currentTimeMillis()+".csv");
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(out))){
                // header
                bw.write("ID,Name,Qty,Available,Price,Subtotal"); bw.newLine();
                for(int i=0;i<m.getRowCount();i++){
                    Object id = m.getValueAt(i,0);
                    Object name = m.getValueAt(i,1);
                    Object qty = m.getValueAt(i,2);
                    Object avail = m.getValueAt(i,3);
                    Object price = m.getValueAt(i,4);
                    Object sub = m.getValueAt(i,5);
                    // strip peso for price/sub
                    String priceS = price==null?"":price.toString().replaceAll("[^0-9.\\-]", "");
                    String subS = sub==null?"":sub.toString().replaceAll("[^0-9.\\-]", "");
                    bw.write(String.format("%s,%s,%s,%s,%s,%s", id, name, qty, avail, priceS, subS)); bw.newLine();
                }
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Report exported to: "+out.getAbsolutePath());
        }catch(HeadlessException | IOException e){javax.swing.JOptionPane.showMessageDialog(this, "Could not export: "+e.getMessage()); }
    }

    private void printReport(){
        try{
            itemsTable.print();
        }catch(PrinterException e){javax.swing.JOptionPane.showMessageDialog(this, "Print failed: "+e.getMessage()); }
    }

    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel totalItemsLabel;
    private javax.swing.JLabel totalQtyLabel;
    private javax.swing.JLabel totalValueLabel;
    private javax.swing.JScrollPane jsp;
    private javax.swing.JTable itemsTable;

    private javax.swing.JTextField startDateField, endDateField;
    private javax.swing.JPanel filterBtn, exportCsvBtn, printBtn;

    // No-op: using JFrame (not JInternalFrame), so setClosable is not applicable.
}
