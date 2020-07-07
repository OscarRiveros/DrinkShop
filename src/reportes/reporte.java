/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Cris-Men
 */
public class reporte {
    private Connection conn;
    private final String login="root";
    private final String password="12345";
    private String url="jdbc:mysql://localhost:3306/bebidas";
    
    public reporte(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url,login,password);
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(reporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(SQLException e){
            System.out.println("Error al conectar por la BD"+e.getMessage());
        }
        
    }
    
    public void runReporte(String ubicacion){
        try{
          
            String master=System.getProperty("user.dir")+ubicacion;
            System.out.println("master "+master);
            if (master==null) {
                System.out.println("NO encuentro el archivo del reporte maestro");
                System.exit(2);
            }
            JasperReport masterReport =null;
            try{
                masterReport=(JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(ubicacion));
            }catch(JRException e){
                System.out.println("Error cargando el reporte maestro: "+e.getMessage());
                System.exit(3);
            }
            Map parametro= new HashMap();
            JasperPrint jasperPrint=JasperFillManager.fillReport(masterReport, parametro,conn);
            JasperViewer jviewer=new JasperViewer(jasperPrint,false);
            jviewer.setVisible(true);
        }catch(Exception j){
            System.out.println("Mensaje de Error:"+j.getMessage());
        }
    }
    
      public void runReporte_parametro(String ubicacion,String Nombrepar,Integer Valor_p1){
        try{
            String master=System.getProperty("user.dir")+ubicacion;
            System.out.println("master "+master);
            if (master==null) {
                System.out.println("NO encuentro el archivo del reporte maestro");
                System.exit(2);
            }
            JasperReport masterReport =null;
            try{
                 masterReport=(JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(ubicacion));
              //  masterReport=(JasperReport) JRLoader.loadObject(master);
            }catch(JRException e){
                System.out.println("Error cargando el reporte maestro: "+e.getMessage());
                System.exit(3);
            }
            Map parametro= new HashMap();
            parametro.put(Nombrepar, Valor_p1);
            //parametro.put("idpagos", p1);
            JasperPrint jasperPrint=JasperFillManager.fillReport(masterReport, parametro,conn);
            JasperViewer jviewer=new JasperViewer(jasperPrint,false);
            jviewer.setVisible(true);
        }catch(Exception j){
            System.out.println("Mensaje de Error:"+j.getMessage());
        }
    }
      public void runReporte_parametro2(String ubicacion,String Nombrepar,String Nombrepar2,String Valor_p1, String Valor_p2){
        try{
            String master=System.getProperty("user.dir")+ubicacion;
            System.out.println("master "+master);
            if (master==null) {
                System.out.println("NO encuentro el archivo del reporte maestro");
                System.exit(2);
            }
            JasperReport masterReport =null;
            try{
                 masterReport=(JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(ubicacion));
               // masterReport=(JasperReport) JRLoader.loadObject(master);
            }catch(JRException e){
                System.out.println("Error cargando el reporte maestro: "+e.getMessage());
                System.exit(3);
            }
            Map parametro= new HashMap();
            parametro.put(Nombrepar, Valor_p1);
            parametro.put(Nombrepar2, Valor_p2);
            JasperPrint jasperPrint=JasperFillManager.fillReport(masterReport, parametro,conn);
            JasperViewer jviewer=new JasperViewer(jasperPrint,false);
            jviewer.setVisible(true);
        }catch(Exception j){
            System.out.println("Mensaje de Error:"+j.getMessage());
        }
    }
      
       public void runReporte_parametrofecha(String ubicacion,String Nombrepar,String Nombrepar2,Date Valor_p1, Date Valor_p2){
        try{
            String master=System.getProperty("user.dir")+ubicacion;
            System.out.println("master "+master);
            if (master==null) {
                System.out.println("NO encuentro el archivo del reporte maestro");
                System.exit(2);
            }
            JasperReport masterReport =null;
            try{
                 masterReport= (JasperReport)JRLoader.loadObject(getClass().getResourceAsStream(ubicacion));
                //masterReport=(JasperReport) JRLoader.loadObject(master);
            }catch(JRException e){
                System.out.println("Error cargando el reporte maestro: "+e.getMessage());
                System.exit(3);
            }
            Map parametro= new HashMap();
            parametro.put(Nombrepar, Valor_p1);
            parametro.put(Nombrepar2, Valor_p2);
            JasperPrint jasperPrint=JasperFillManager.fillReport(masterReport, parametro,conn);
            JasperViewer jviewer=new JasperViewer(jasperPrint,false);
            jviewer.setVisible(true);
        }catch(Exception j){
            System.out.println("Mensaje de Error:"+j.getMessage());
        }
    }
    public void cerrar(){
    try{
        conn.close();
    }catch(SQLException ex){
        ex.printStackTrace();
    }
    }
    
}
