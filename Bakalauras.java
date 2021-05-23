/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bakalauras;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author Robert
 */
public class Bakalauras {
    private ArrayList<Lenteles> table = new ArrayList();
    private String url, us, pas;
    private String[] lentpav;
    

    public boolean Prisijungimas(String DB,String DB_URL, String USER, String PASS){
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_CATALOG='"+DB+"'";
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
                  while (rs.next()) {
                      for (int i = 1; i <= columnsNumber; i++) {
                          String columnValue = rs.getString(i);
                          table.add(new Lenteles(columnValue));            
             }      
          }
            
                  
                  
            stmt.close();
            conn.close();
            
            
            return true;
            
        
    } catch (Exception e) {
            return false;
        }
        
        
        
        //return false;  
    }
     
    
    
    public String VarduPaieska(String DB,String DB_URL, String USER, String PASS){ 
        int count= 0;
        String result="\n";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%vard%') OR"
                    + "(TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%name%')";
            
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            
                  int columnsNumber = rsmd.getColumnCount();
                  while(rs.next()){
                      for (int i = 1; i <= columnsNumber; i++){
                  result+="Lentelės pavadinimas: "+l.getPavadinimas()+"       Stulpelio pavadinimas: "+rs.getString(i)+"\n";}
                      count++;
                  }}
                  result= " "+String.valueOf(count)+result;
            stmt.close();
            conn.close();
            
            
            
            
        
    } catch (Exception e) {
            
        }
    
    
    
    return result;
    }
    
    @SuppressWarnings("empty-statement")
    
    public String GiliVarduPaieska(String DB,String DB_URL, String USER, String PASS){
        String vard = "('";
        try {
      File myObj = new File("Vardai.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        vard+=data+"', '";
      }vard=vard.substring(0, vard.length() - 3) + ")";
      myReader.close();
    } catch (FileNotFoundException e) {
      
    } 
    
        String[] laikinas;
 String gr = "";
 String holder="";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+l.getPavadinimas()+"'";
            ResultSet rs = stmt.executeQuery(sql);
                 
                  while(rs.next()){
                    
                      
                      gr+=rs.getString(1)+" ";
                     
                  }laikinas = gr.split(" ");
                  for (int i=0; i< laikinas.length;i++){
                  sql="SELECT "+laikinas[i]+" FROM "+l.getPavadinimas()+" WHERE cast("+laikinas[i]+" as varchar) IN "+vard;
                  
                  rs = stmt.executeQuery(sql);
                  while(rs.next()){
                      holder+=("Vardas \""+rs.getString(1)+ "\" rastas lentelėje \"" + l.getPavadinimas()+ "\" stulpelyje \""+laikinas[i]+"\"\n");
                  }
                  } 
                  gr= "";
                  
            }
                  
            stmt.close();
            conn.close();
            
    } catch (Exception e) {
            
        }
    if(holder==""){holder="Vardų nerasta \n";}
    return holder;
    }
    
    public String GiliAdresuPaieska(String DB,String DB_URL, String USER, String PASS){
        String vard = "('";
        try {
      File myObj = new File("Miestai.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        vard+=data+"', '";
      }vard=vard.substring(0, vard.length() - 3) + ")";
      myReader.close();
    } catch (FileNotFoundException e) {
      
    } 
    
        String[] laikinas;
 String gr = "";
 String holder="";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+l.getPavadinimas()+"'";
            ResultSet rs = stmt.executeQuery(sql);
                 
                  while(rs.next()){
                    
                      
                      gr+=rs.getString(1)+" ";
                     
                  }laikinas = gr.split(" ");
                  for (int i=0; i< laikinas.length;i++){
                  sql="SELECT "+laikinas[i]+" FROM "+l.getPavadinimas()+" WHERE cast("+laikinas[i]+" as varchar) IN "+vard;
                  
                  rs = stmt.executeQuery(sql);
                  while(rs.next()){
                      holder+=("Miestas \""+rs.getString(1)+ "\" rastas lentelėje \"" + l.getPavadinimas()+ "\" stulpelyje \""+laikinas[i]+ "\"\n");
                  }
                  } 
                  gr= "";
                  
            }
                  
            stmt.close();
            conn.close();
            
    } catch (Exception e) {
            
        }
    if(holder==""){holder="Miestų nerasta \n";}
    return holder;
    }
    
    public String GiliPastuPaieska(String DB,String DB_URL, String USER, String PASS){
        
        String[] laikinas;
 String gr = "";
 String holder="";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+l.getPavadinimas()+"'";
            ResultSet rs = stmt.executeQuery(sql);
                 
                  while(rs.next()){
                    
                      
                      gr+=rs.getString(1)+" ";
                     
                  }laikinas = gr.split(" ");
                  for (int i=0; i< laikinas.length;i++){
                  sql="SELECT "+laikinas[i]+" FROM "+l.getPavadinimas()+" WHERE "+laikinas[i]+" LIKE '%@%.%'";
                  
                  rs = stmt.executeQuery(sql);
                  while(rs.next()){
                      holder+=("Paštas \""+rs.getString(1)+ "\" rastas lentelėje \"" + l.getPavadinimas()+ "\" stulpelyje \""+laikinas[i]+ "\"\n");
                  }
                  } 
                  gr= "";
                  
            }
                  
            stmt.close();
            conn.close();
            
    } catch (Exception e) {
            
        }
    if(holder==""){holder="Paštų nerasta \n";}
    return holder;
    }
    
    public String GiliSaskaituPaieska(String DB,String DB_URL, String USER, String PASS){
        
        String[] laikinas;
 String gr = "";
 String holder="";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+l.getPavadinimas()+"'";
            ResultSet rs = stmt.executeQuery(sql);
                 
                  while(rs.next()){
                    
                      
                      gr+=rs.getString(1)+" ";
                     
                  }laikinas = gr.split(" ");
                  for (int i=0; i< laikinas.length;i++){
                  sql="SELECT "+laikinas[i]+" FROM "+l.getPavadinimas()+" WHERE "+laikinas[i]+" LIKE 'LT__________________'";
                  
                  rs = stmt.executeQuery(sql);
                  while(rs.next()){
                      holder+=("Banko sąskaita \""+rs.getString(1)+ "\" rastas lentelėje \"" + l.getPavadinimas()+ "\" stulpelyje \""+laikinas[i]+ "\"\n");
                  }
                  } 
                  gr= "";
                  
            }
                  
            stmt.close();
            conn.close();
            
    } catch (Exception e) {
            
        }
    if(holder==""){holder="Banko sąskaitų nerasta\n";}
    return holder;
    }
    
    public String GiliKoordinaciuPaieska(String DB,String DB_URL, String USER, String PASS){
        
        String[] laikinas;
 String gr = "";
 String holder="";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+l.getPavadinimas()+"'";
            ResultSet rs = stmt.executeQuery(sql);
                 
                  while(rs.next()){
                    
                      
                      gr+=rs.getString(1)+" ";
                     
                  }laikinas = gr.split(" ");
                  for (int i=0; i< laikinas.length;i++){
                  sql="SELECT "+laikinas[i]+" FROM "+l.getPavadinimas()+" WHERE "+laikinas[i]+
                          " LIKE '__.______%___.______' OR "+ laikinas[i] +" LIKE '___.______%___.______' OR "+ laikinas[i] +" LIKE '__._______%___._______' OR "+ 
                          laikinas[i] +" LIKE '___._______%___._______'";
                  
                  rs = stmt.executeQuery(sql);
                  while(rs.next()){
                      holder+=("Koordinatės \""+rs.getString(1)+ "\" rastos lentelėje \"" + l.getPavadinimas()+ "\" stulpelyje \""+laikinas[i]+ "\"\n");
                  }
                  } 
                  gr= "";
                  
            }
                  
            stmt.close();
            conn.close();
            
    } catch (Exception e) {
            
        }
    if(holder==""){holder="Koordinčių nerasta\n";}
    return holder;
    }
    
    public String GiliIpPaieska(String DB,String DB_URL, String USER, String PASS){
        
        String[] laikinas;
 String gr = "";
 String holder="";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+l.getPavadinimas()+"'";
            ResultSet rs = stmt.executeQuery(sql);
                 
                  while(rs.next()){
                    
                      
                      gr+=rs.getString(1)+" ";
                     
                  }laikinas = gr.split(" ");
                  for (int i=0; i< laikinas.length;i++){
                  sql="SELECT "+laikinas[i]+" FROM "+l.getPavadinimas()+" WHERE "+laikinas[i]+" LIKE '_.%.%._%' OR "+ laikinas[i] +" LIKE '__.%.%.%' OR "+ 
                          laikinas[i] +" LIKE '___.%.%.%'";
                  
                  rs = stmt.executeQuery(sql);
                  while(rs.next()){
                      holder+=("IP \""+rs.getString(1)+ "\" rastas lentelėje \"" + l.getPavadinimas()+ "\" stulpelyje \""+laikinas[i]+ "\"\n");
                  }
                  } 
                  gr= "";
                  
            }
                  
            stmt.close();
            conn.close();
            
    } catch (Exception e) {
            
        }
    if(holder==""){holder="IP nerasta \n";}
    return holder;
    }
    
    public String GiliTapatybesPaieska(String DB,String DB_URL, String USER, String PASS){
        
        String[] laikinas;
 String gr = "";
 String holder="";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+l.getPavadinimas()+"'";
            ResultSet rs = stmt.executeQuery(sql);
                 
                  while(rs.next()){
                    
                      
                      gr+=rs.getString(1)+" ";
                     
                  }laikinas = gr.split(" ");
                  for (int i=0; i< laikinas.length;i++){
                  sql="SELECT "+laikinas[i]+" FROM "+l.getPavadinimas()+" WHERE "+laikinas[i]+" LIKE '3__________' OR "+ laikinas[i] +
                          " LIKE '4__________' OR "+ laikinas[i] +" LIKE '5__________' OR "+ laikinas[i] +" LIKE '6__________'";
                  
                  rs = stmt.executeQuery(sql);
                  while(rs.next()){
                      holder+=("Asmens kodas \""+rs.getString(1)+ "\" rastas lentelėje \"" + l.getPavadinimas()+ "\" stulpelyje \""+laikinas[i]+ "\"\n");
                  }
                  } 
                  gr= "";
                  
            }
                  
            stmt.close();
            conn.close();
            
    } catch (Exception e) {
            
        }
    if(holder==""){holder="Asmens kodų nerasta \n";}
    return holder;
    }
    
    public String AdresuPaieska(String DB,String DB_URL, String USER, String PASS){
    int count= 0;
        String result="\n";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%adresas%') OR"
                    + "(TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%miestas%')";
            
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            
                  int columnsNumber = rsmd.getColumnCount();
                  while(rs.next()){
                      for (int i = 1; i <= columnsNumber; i++){
                  result+="Lentelės pavadinimas: "+l.getPavadinimas()+"       Stulpelio pavadinimas: "+rs.getString(i)+"\n";}
                      count++;
                  }}
                  result= " "+String.valueOf(count)+result;
            stmt.close();
            conn.close();
  
    } catch (Exception e) {
            
        }
    return result;
    
    }
    public String PastuPaieska(String DB,String DB_URL, String USER, String PASS){
    int count= 0;
        String result="\n";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%pastas%') OR"
                    + "(TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%email%')";
            
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            
                  int columnsNumber = rsmd.getColumnCount();
                  while(rs.next()){
                      for (int i = 1; i <= columnsNumber; i++){
                  result+="Lentelės pavadinimas: "+l.getPavadinimas()+"       Stulpelio pavadinimas: "+rs.getString(i)+"\n";}
                      count++;
                  }}
                  result= " "+String.valueOf(count)+result;
            stmt.close();
            conn.close();
  
    } catch (Exception e) {
            
        }
    return result;
    
    }
    
    public String SaskaituPaieska(String DB,String DB_URL, String USER, String PASS){
    int count= 0;
        String result="\n";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%saskait%') OR"
                    + "(TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%bank%')";
            
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            
                  int columnsNumber = rsmd.getColumnCount();
                  while(rs.next()){
                      for (int i = 1; i <= columnsNumber; i++){
                  result+="Lentelės pavadinimas: "+l.getPavadinimas()+"       Stulpelio pavadinimas: "+rs.getString(i)+"\n";}
                      count++;
                  }}
                  result= " "+String.valueOf(count)+result;
            stmt.close();
            conn.close();
  
    } catch (Exception e) {
            
        }
    return result;
    
    }
    
    public String TapatybesPaieska(String DB,String DB_URL, String USER, String PASS){
    int count= 0;
        String result="\n";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%asmen%kod%') OR"
                    + "(TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%card%')";
            
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            
                  int columnsNumber = rsmd.getColumnCount();
                  while(rs.next()){
                      for (int i = 1; i <= columnsNumber; i++){
                  result+="Lentelės pavadinimas: "+l.getPavadinimas()+"       Stulpelio pavadinimas: "+rs.getString(i)+"\n";}
                      count++;
                  }}
                  result= " "+String.valueOf(count)+result;
            stmt.close();
            conn.close();
  
    } catch (Exception e) {
            
        }
    return result;
    
    }
    
    public String KoordinaciuPaieska(String DB,String DB_URL, String USER, String PASS){
    int count= 0;
        String result="\n";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%koord%') OR"
                    + "(TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%coord%')";
            
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            
                  int columnsNumber = rsmd.getColumnCount();
                  while(rs.next()){
                      for (int i = 1; i <= columnsNumber; i++){
                  result+="Lentelės pavadinimas: "+l.getPavadinimas()+"       Stulpelio pavadinimas: "+rs.getString(i)+"\n";}
                      count++;
                  }}
                  result= " "+String.valueOf(count)+result;
            stmt.close();
            conn.close();
  
    } catch (Exception e) {
            
        }
    return result;
    
    }
    
    public String IpPaieska(String DB,String DB_URL, String USER, String PASS){
    int count= 0;
        String result="\n";
    try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            for (Lenteles l:table){
            String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE (TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like 'IP') OR"
                    + "(TABLE_NAME LIKE '"+l.getPavadinimas()+"' AND COLUMN_NAME like '%IPadr%')";
            
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            
                  int columnsNumber = rsmd.getColumnCount();
                  while(rs.next()){
                      for (int i = 1; i <= columnsNumber; i++){
                  result+="Lentelės pavadinimas: "+l.getPavadinimas()+"       Stulpelio pavadinimas: "+rs.getString(i)+"\n";}
                      count++;
                  }}
                  result= " "+String.valueOf(count)+result;
            stmt.close();
            conn.close();
  
    } catch (Exception e) {
            
        }
    return result;
    
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
