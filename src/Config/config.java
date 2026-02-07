/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author canaz
 */
public class config {
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:inventory.db"); // Establish connection
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }
    
    public void addRecord(String sql, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        pstmt.executeUpdate();
        System.out.println("Record added successfully!");
    } catch (SQLException e) {
        System.out.println("Error adding record: " + e.getMessage());
    }
}
    
    public String authenticate(String sql, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("type");
            }
        }
    } catch (SQLException e) {
        System.out.println("Login Error: " + e.getMessage());
    }
    return null;
}
    public void displayData(String sql, javax.swing.JTable table) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        // This line automatically maps the Resultset to your JTable
        table.setModel(DbUtils.resultSetToTableModel(rs));
        
    } catch (SQLException e) {
        System.out.println("Error displaying data: " + e.getMessage());
    }
}
      private Map<String,String[]> readUsers(){
        Map<String,String[]> map = new HashMap<>();
        File f = new File("users.txt");
        if(!f.exists()) return map;
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String line;
            while((line=br.readLine())!=null){
                String[] parts = line.split(":");
                if(parts.length>=2){
                    String username = parts[0];
                    String hash = parts[1];
                    String role = (parts.length>=3)?parts[2]:"user";
                    String fullname = (parts.length>=4)?parts[3]:"";
                    String location = (parts.length>=5)?parts[4]:"";
                    map.put(username, new String[]{hash, role, fullname, location});
                }
            }
        } catch(IOException e){ e.printStackTrace(); }
        return map;
    }
         private String hash(String s){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] b = md.digest(s.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for(byte by: b) sb.append(String.format("%02x", by));
            return sb.toString();
        } catch(Exception e){ return s; }
    }
            private static final String DB_FILE = "inventory.db";
    private static boolean driverAvailable = false;
    static {
        try{
            Class.forName("org.sqlite.JDBC");
            driverAvailable = true;
            System.out.println("[Database] SQLite JDBC driver found.");
            
            try{
                initializeSchema();
            }catch(Exception ex){
                System.err.println("[Database] Schema initialization failed in static init: "+ex.getMessage());
            }
        }catch(Exception e){
            System.err.println("[Database] SQLite JDBC driver not found. DB operations will be skipped.");
            driverAvailable = false;
        }
    }

    private static Connection connect(){
        if(!driverAvailable){
            System.err.println("[Database] SQLite JDBC driver unavailable.");
            return null;
        }
        try{
            File f = new File(DB_FILE);
            // Ensure parent directories exist (if any)
            if(f.getParentFile()!=null && !f.getParentFile().exists()){
                f.getParentFile().mkdirs();
            }
            // DriverManager will create the database file if it does not exist.
            Connection c = DriverManager.getConnection("jdbc:sqlite:"+f.getAbsolutePath());
            if(!f.exists()){
                System.out.println("[Database] Created new DB file: "+f.getAbsolutePath());
            }
            System.out.println("[Database] Connected to "+f.getAbsolutePath());
            return c;
        }catch(Exception e){
            System.err.println("[Database] Connection failed: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static boolean available(){
        // Availability depends on driver presence. Database file will be created on demand.
        return driverAvailable;
    }

    public static void initializeSchema(){
        if(!available()) return;
        Connection c = connect();
        if(c==null) return;
        try{
            // Check if users table exists
            try(PreparedStatement ps = c.prepareStatement("SELECT name FROM sqlite_master WHERE type='table' AND name='name'")){
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()) {
                        System.out.println("[Database] Table 'users' already exists.");
                        return;
                    }
                }
            }
            // Create table
            try(Statement stmt = c.createStatement()){
                stmt.execute("CREATE TABLE users ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "email TEXT UNIQUE NOT NULL,"
                    + "fullname TEXT,"
                    + "location TEXT,"
                    + "role TEXT,"
                    + "status TEXT,"
                    + "created_at DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")");
                System.out.println("[Database] Created 'users' table.");
            }
        }catch(Exception e){
            System.err.println("[Database] Schema init failed: "+e.getMessage());
            e.printStackTrace();
        }finally{
            try{ c.close(); }catch(Exception ex){}
        }
    }

    public static Map<String,String> getUserByEmail(String email){
        Connection c = connect();
        if(c==null) return null;
        try(PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE email=?")){
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                if(!rs.next()) {
                    System.out.println("[Database] No user found for email: "+email);
                    return null;
                }
                ResultSetMetaData md = rs.getMetaData();
                Map<String,String> out = new HashMap<>();
                for(int i=1;i<=md.getColumnCount();i++){
                    String col = md.getColumnName(i);
                    out.put(col, rs.getString(i));
                }
                System.out.println("[Database] Loaded user: "+email+" from DB");
                return out;
            }
        }catch(Exception e){
            System.err.println("[Database] getUserByEmail failed: "+e.getMessage());
            e.printStackTrace();
            return null;
        }finally{
            try{ c.close(); }catch(Exception ex){}
        }
    }

    public static boolean updateUserProfile(String email, String fullname, String location){
        Connection c = connect();
        if(c==null) return false;
        try{
            // find which columns exist
            boolean hasFull=false, hasLocation=false;
            try(PreparedStatement ps = c.prepareStatement("PRAGMA table_info(users)")){
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        String col = rs.getString("name");
                        if("fullname".equalsIgnoreCase(col)) hasFull = true;
                        if("location".equalsIgnoreCase(col)) hasLocation = true;
                    }
                }
            }
            if(!hasFull && !hasLocation){
                System.err.println("[Database] Neither 'fullname' nor 'location' columns exist in users table.");
                return false;
            }
            StringBuilder sb = new StringBuilder("UPDATE users SET ");
            boolean first=true;
            if(hasFull){ sb.append("fullname=?"); first=false; }
            if(hasLocation){ if(!first) sb.append(","); sb.append("location=?"); }
            sb.append(" WHERE email=?");
            try(PreparedStatement ps = c.prepareStatement(sb.toString())){
                int idx=1;
                if(hasFull) ps.setString(idx++, fullname);
                if(hasLocation) ps.setString(idx++, location);
                ps.setString(idx++, email);
                int updated = ps.executeUpdate();
                if(updated>0) System.out.println("[Database] Updated "+email+" in DB");
                return updated>0;
            }
        }catch(Exception e){ 
            System.err.println("[Database] updateUserProfile failed: "+e.getMessage());
            e.printStackTrace(); 
            return false; 
        }
        finally{ try{ c.close(); }catch(Exception ex){} }
    }

    public static boolean createUserIfMissing(String email, String fullname){
        Connection c = connect();
        if(c==null) return false;
        try{
            // check exists
            try(PreparedStatement ps = c.prepareStatement("SELECT COUNT(1) FROM users WHERE email=?")){
                ps.setString(1, email);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next() && rs.getInt(1)>0) {
                        System.out.println("[Database] User "+email+" already exists in DB");
                        return true;
                    }
                }
            }
            // insert available columns
            // discover columns
            java.util.List<String> cols = new java.util.ArrayList<>();
            try(PreparedStatement ps = c.prepareStatement("PRAGMA table_info(name)")){
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        String col = rs.getString("name");
                        cols.add(col);
                    }
                }
            }
            System.out.println("[Database] users table columns: " + cols);
            java.util.List<String> use = new java.util.ArrayList<>();
            if(cols.contains("email")) use.add("email");
            if(cols.contains("fullname")) use.add("fullname");
            if(cols.contains("status")) use.add("status");
            if(use.isEmpty()){
                System.err.println("[Database] No usable columns found for insert.");
                return false;
            }
            StringBuilder sb = new StringBuilder("INSERT INTO name (");
            for(int i=0;i<use.size();i++){ if(i>0) sb.append(","); sb.append(use.get(i)); }
            sb.append(") VALUES (");
            for(int i=0;i<use.size();i++){ if(i>0) sb.append(","); sb.append("?"); }
            sb.append(")");
            String sql = sb.toString();
            System.out.println("[Database] Prepared INSERT SQL: " + sql);
            try(PreparedStatement ps = c.prepareStatement(sb.toString())){
                int idx=1;
                for(String col : use){
                    if("email".equalsIgnoreCase(col)) ps.setString(idx++, email);
                    else if("fullname".equalsIgnoreCase(col)) ps.setString(idx++, fullname);
                    else if("status".equalsIgnoreCase(col)) ps.setString(idx++, "active");
                    else ps.setString(idx++, null);
                }
                ps.executeUpdate();
                System.out.println("[Database] Created user "+email+" in DB");
                return true;
            }
        }catch(Exception e){ 
            System.err.println("[Database] createUserIfMissing failed: "+e.getMessage());
            e.printStackTrace(); 
            return false; 
        }
        finally{ try{ c.close(); }catch(Exception ex){} }
    }

    // Helper for debugging: print all rows in users table
  
}
