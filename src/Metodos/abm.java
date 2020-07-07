
package Metodos;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import bebidas.conexionBD;


public class abm {
    private Connection conec;
    private Statement sentencia;
    public static String nomUsuario;
    public static String apeUsuario;
    public static int nivel;
    public static int idUsuario;//hoy
    public abm(){
        conec=conexionBD.ConectarBD();
    }
    
    //metodo para insertar datos
    public boolean insertar(String tabla, String valores){
        boolean v_control=false;//se declara una bandera para contorlar la ejecucion sql
        try{
            
           sentencia= (Statement)conec.createStatement();
          int registro= sentencia.executeUpdate("insert into "+tabla+" values("+valores+")");
          
          if (registro >0){
              v_control=true;
             
          }
          System.out.println(valores);
        }catch(Exception registro){
            JOptionPane.showMessageDialog(null, "errors al guardar datos El numero de C.I o Ruc ya fue ingresado");
        }
        return v_control;
    }
    
    //metodo para nuevo registro
    public ResultSet nuevo (String campo, String tabla){
        ResultSet rs =null;
        try{
            
            sentencia= (Statement)conec.createStatement();  
            rs= sentencia.executeQuery("select max("+campo+")as codigo from "+tabla);
        }
        catch(Exception e){
         JOptionPane.showMessageDialog(null, "errors maximo"+e.getMessage());   
        }
        return rs;
    }
    // select * from genero
    public ResultSet consulta(String campo, String tabla){
        ResultSet rs=null;
        try{
            sentencia=(Statement) conec.createStatement();
            rs= sentencia.executeQuery("select "+campo+ " from "+tabla);
            rs.last();
                    
                 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "error al realizar consulta"+e.getMessage());   
            System.out.println("Erroorrrrr");
        }
        return rs;
    } 
     // select * from genero
    public ResultSet consultaDos(String campo, String tabla, String condicion){
        ResultSet rs=null;
        try{
            sentencia=(Statement) conec.createStatement();
            rs= sentencia.executeQuery("select "+campo+ " from "+tabla+" where "+condicion);
            rs.last();
                    
                 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "error al realizar consulta"+e.getMessage());   
            System.out.println("Erroorrrrr");
        }
        return rs;
    } 
    //metodo de consulta
    public ResultSet consultasql(String sql){
        ResultSet rs=null;
        try{
            sentencia=(Statement) conec.createStatement();
            rs= sentencia.executeQuery(sql);
            //rs.last();
     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "error al realizar consulta"+e.getMessage());                
        }
        return rs;
    }
    
    //metodo para modificar
    public boolean modificar(String tabla, String campo, String condicion){
        boolean v_control= false ;
        try {
             sentencia= (Statement)conec.createStatement();
             System.out.println(campo);
          int registro= sentencia.executeUpdate("update "+tabla+" set "+campo+" where "+condicion);
          
          if (registro >0){
              v_control=true;
          }
          
        }catch(Exception e){
        JOptionPane.showMessageDialog(null, "errors al modificar glos datos"+e.getMessage());    
        }
    return v_control;
    }  
    
    //metodo para eliminar
    public boolean eliminar(String tabla, String condicion){
        boolean v_control=false;
        try{
            sentencia= (Statement)conec.createStatement();
            int res=JOptionPane.showConfirmDialog(null, "desea eliminar regiastro", "ATENCION",
            JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)  ;     
            
            if (res==JOptionPane.YES_OPTION){
                int registro= sentencia.executeUpdate("delete from "+tabla+" where "+condicion); 
                     if (registro >0){
                     v_control=true;
            }}
            
        }catch(Exception e){
         JOptionPane.showMessageDialog(null, "error al eliminar consulta"+e.getMessage());                   
        }
        return v_control;
    }
     public ResultSet consultaLogin(String campos,String tabla,String usuario,String contraseña){
     ResultSet rs=null;
     try{
         sentencia=(Statement) conec.createStatement();
         rs=sentencia.executeQuery("select "+campos+" from "+tabla+" where usuario='"+usuario+"' and contraseña='"+contraseña+"'");
         rs.first();
     }catch(Exception e){
         JOptionPane.showMessageDialog(null,"Error al realizar consulta"+e.getLocalizedMessage());
     }return rs;
 }
     
   public void start(){
       try{
        //this.conec.getAutoCommit();
        if(this.conec.getAutoCommit())
        {
        this.conec.setAutoCommit(false);
        }
    }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"error al iniciar la transanccion" +e.getMessage());
        } 
    }    
     
    public void roolback(){
        try{
            System.out.println("entro en el rol antes");
            this.conec.rollback();
            System.out.println("entro en el rol despues");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"error al revertir la transanccion" +e);
        }
    }
        
    public void comit(){
        try{
            this.conec.commit();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"error al confirmar  la transanccion" +e);
        }    
}
    
    public void end(){
        try{
            this.conec.setAutoCommit(true);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"error al finalizar  la transanccion" +e);
        }    
}  
          
}

