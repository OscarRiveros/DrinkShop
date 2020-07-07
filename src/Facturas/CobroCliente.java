package Facturas;

import Metodos.abm;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import bebidas.conexionBD;

public class CobroCliente extends javax.swing.JDialog {

    private ResultSet rs;
    private abm abm;
    Calendar miFecha;
    Calendar Fecha;
    private boolean v_control;
    conexionBD cn;
    DefaultTableModel modelo;
    DefaultTableModel modelo2;
    private Object[] filas;
    private ResultSet rst,rso;
    private boolean vacio,vacioo;
    DefaultTableModel modelo3;

    public CobroCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        abm = new abm();
        cn = new conexionBD();
        
        Fecha  = new GregorianCalendar();
        miFecha = new GregorianCalendar();
        btnImprimir.setVisible(false);
        idpagos();
        idrecibo();
        //cargarsaldo();
      // cargarclinetes("");
        verclintes();
        
     txtidfac.setVisible(false);
     //txtIdCuotas.setVisible(false);
     idpagocuta.setVisible(false);
     txtIdRecibo.setVisible(false);
     jLabel23.setVisible(false);
     jLabel25.setVisible(false);
     txtCuota.setEnabled(false);

        fechavence.setEnabled(false);
    }

    void idpagos() { //metodo para controla el pago de cuotas y emitir recibos
        try {
            rs = abm.nuevo("idpagocuota", "PagosCuotas");
            rs.first();
            txtIdCuotas.setText(String.valueOf(rs.getInt("codigo") + 1));
            idpagocuta.setText(String.valueOf(rs.getInt("codigo") + 1));
        } catch (SQLException ex) {
            Logger.getLogger(CobroCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void idrecibo() {
        try {
            rs = abm.nuevo("idreciboventa", "reciboventa");
            rs.first();
            txtIdRecibo.setText(String.valueOf(rs.getInt("codigo") + 1));
        } catch (SQLException ex) {
            Logger.getLogger(CobroCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
     * void cargarsaldo(){ try { Statement consulta = (Statement)
     * conexionBD.ConectarBD().createStatement(); ResultSet rss =
     * consulta.executeQuery("select saldo, montocuota from FacturaVenta ");
     * String saldo=rss.getString("saldo"); String
     * montocuota=rss.getString("montocuota"); System.out.println(saldo);
     * System.out.println(montocuota);
     *
     * txtSaldo.setText(rss.getString(saldo));
     * txtMonCuota.setText(rss.getString(montocuota)); }catch (SQLException ex)
     * { Logger.getLogger(CobroCliente.class.getName()).log(Level.SEVERE, null,
     * ex); }           
    }
     */
    /*
     * void fechavence(){ fechavence.setDate(miFecha.getTime());
     *
     * miFecha = fechavence.getCalendar();
     * miFecha.setTime(fechavence.getDate()); String dia
     * =String.valueOf(miFecha.get(Calendar.DATE)); String mes
     * =String.valueOf(miFecha.get(Calendar.MONTH)+1); String anio
     * =String.valueOf(miFecha.get(Calendar.YEAR)); String date =
     * anio+"/"+mes+"/"+dia;
    }
     */

   /* void fechapago() {
        fechapago.setDate(miFecha.getTime());

        miFecha = fechapago.getCalendar();
        miFecha.setTime(fechapago.getDate());
        String dia = String.valueOf(miFecha.get(Calendar.DATE));
        String mes = String.valueOf(miFecha.get(Calendar.MONTH) + 1);
        String anio = String.valueOf(miFecha.get(Calendar.YEAR));
        String date = anio + "/" + mes + "/" + dia;
    }*/

    public void cargarclinetes(String valor) {
        String[] titulos = {"Id Factura", "Codigo", "Nombre", "Apellido", "C.I.N°", "Saldo", "Monto de la Cuota", "Fecha Vence","Plazo","Monto Abonado","Cuota Pagado"};        
        String[] registros = new String[9];
        String sql = "select  f.idfacturaventa, f.idcliente, nombre, apellido, cinro, f.saldo, f.montocuota, f.fechavence, plazo from cliente as c inner join FacturaVenta as f on(f.idcliente=c.idcliente) and f.saldo >0";
       // String sql = "select f.idfacturaventa, f.idcliente, nombre, apellido, cinro, f.saldo, f.montocuota, f.fechavence, plazo, pg.montoabonado, cantcuotapagado, pg.saldo from cliente as c inner join PagosCuotas as pg inner join FacturaVenta as f on(f.idfacturaventa=pg.idfacturaventa)   where CONCAT(f.idcliente,' ',nombre,' ',apellido,' ',cinro,' ',f.saldo,' ',f.montocuota,' ',cantcuotapagado) LIKE '%" + valor + "%'"+"and f.saldo > 0";

        // sql+=" where idproducto="+txtIdProducto.getText();

        modelo = new DefaultTableModel(null, titulos);

        conexionBD cnx = new conexionBD();
        Connection cnn = (Connection) cnx.ConectarBD();
        com.mysql.jdbc.Statement st;
        try {

            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rss = st.executeQuery(sql);

            while (rss.next()) {
                registros[0] = rss.getString("f.idfacturaventa");
                registros[1] = rss.getString("f.idcliente");
                registros[2] = rss.getString("nombre");
                registros[3] = rss.getString("apellido");
                registros[4] = rss.getString("cinro");
                registros[5] = rss.getString("f.saldo");
                registros[6] = rss.getString("f.montocuota");
                registros[7] = rss.getString("f.fechavence");
                registros[8] = rss.getString("plazo");
                //registros[9] = rss.getString("pg.montoabonado");
                //registros[10] = rss.getString("cantcuotapagado");
                //registros[11] = rst.getString("pg.saldo");
                
                modelo.addRow(registros);
            }
            tablacliente.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
  public void cargarclinetesbuscar(String valor) {
        String[] titulos = {"Id Factura", "Codigo", "Nombre", "Apellido"};
        String[] registros = new String[9];
        String sql = "select  f.idfacturaventa, f.idcliente, nombre, apellido, cinro,f.saldo, f.montocuota,f.fechavence, plazo from cliente as c inner join FacturaVenta as f on(f.idcliente=c.idcliente)  where f.idfacturaventa LIKE '%"+txtBuscarCliente.getText()+"%' and f.saldo >0";
        //String sql = "select f.idfacturaventa, f.idcliente, nombre, apellido, cinro, f.saldo, f.montocuota, f.fechavence, plazo, pg.montoabonado, cantcuotapagado, pg.saldo from cliente as c inner join PagosCuotas as pg inner join FacturaVenta as f on(f.idcliente=c.idcliente)  where  f.idfacturaventa LIKE '%" + txtBuscarCliente.getText() + "%'"+"and f.saldo > 0";



        modelo2 = new DefaultTableModel(null, titulos);

        conexionBD cnp = new conexionBD();
        Connection cnn = (Connection) cnp.ConectarBD();
        com.mysql.jdbc.Statement stt;
        try {

            stt = (com.mysql.jdbc.Statement) cnn.createStatement();
           ResultSet rstt = stt.executeQuery(sql);

            while (rstt.next()) {
                registros[0] = rstt.getString("f.idfacturaventa");
                registros[1] = rstt.getString("f.idcliente");
                registros[2] = rstt.getString("nombre");
                registros[3] = rstt.getString("apellido");
                registros[4] = rstt.getString("cinro");
                registros[5] = rstt.getString("f.saldo");
                registros[6] = rstt.getString("f.montocuota");
                registros[7] = rstt.getString("f.fechavence");
                registros[8] = rstt.getString("plazo");
               // registros[9] = rst.getString("pg.montoabonado");
                //registros[10] = rst.getString("cantcuotapagado");
                //registros[11] = rst.getString("pg.saldo");

                modelo2.addRow(registros);
            }
            tablacliente.setModel(modelo2);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }


    public void seleccionartablacliente() {
        int c;
        c = tablacliente.getSelectedRow();
        if (c == -1) {
            System.out.println("Debe seleccionar un registro");
        } else {
            try {
                rs.absolute(tablacliente.getSelectedRow()+1);
                
                 Integer id = rs.getInt(1);
                
                  Integer saldo = rs.getInt(6);
                  
                  
                
                  Integer monto = rs.getInt(7);
                
                  String fecha = rs.getString(8);
                //String fecha = tablacliente.getValueAt(c, 7).toString();
                   String apellido = rs.getString(4);
                   
                   String nombre = rs.getString(3);
                   
                   String cin = rs.getString(5);
                //String apellido = tablacliente.getValueAt(c, 3).toString();
                   Integer plazo = rs.getInt(9);
                   //Integer auxx = rs.getInt(10);
                //Integer plazo = Integer.parseInt(tablacliente.getValueAt(c, 8).toString());
                   //Integer montobd = rs.getInt(10);
                //Integer montobd = Integer.parseInt(tablacliente.getValueAt(c, 9).toString());
                   // Integer cantcuotapagado = rs.getInt(11);
                    //Integer pgsaldo = rs.getInt(12);
                
                    
              
                    
            Statement consulta = (Statement) conexionBD.ConectarBD().createStatement();
            rst = consulta.executeQuery("select sum(montoabonado) as montototalabonado from pagoscuotas where idfacturaventa="+id);
            rst.first();
           // System.out.println(rst.getString("montototalabonado"));
            Integer valor = rst.getInt("montototalabonado");
            Integer montototalabonado=0;
            if( valor==null){
                 montototalabonado=0;
                 System.out.println("vacio");
                
            }
            else{
                montototalabonado= rst.getInt("montototalabonado"); 
               }
             
                    
               
                txtCliente.setText(cin);
                txtcin.setText(cin);
                txtidfac.setText(Integer.toString(id));
                txtIdfactura.setText(Integer.toString(id));
                txtSaldo.setText(Integer.toString(saldo));
                txtPlazo.setText(Integer.toString(plazo));
               // aux.setText(Integer.toString(auxx));
                //txtmontopagado.setText(Integer.toString(montototalabonado));
                txtmontopagado.setText(Integer.toString(montototalabonado));

                txtMonCuotax.setText(Integer.toString(monto));
                fechavence.setText(fecha);
                txtapellido.setText(apellido);
                txtApellido.setText(apellido);
                txtnombre.setText(nombre);
                //txtCuota.setText(Integer.toString(cantcuotapagado));
                //txtSaldo1.setText(Integer.toString(pgsaldo));
                
                 //calcularcuotas();
                // calcularsaldoanulado();
                
                
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        }
    }

    void verclintes(){
        try {
            Statement consulta = (Statement) conexionBD.ConectarBD().createStatement();
            rs = consulta.executeQuery("select  f.idfacturaventa, f.idcliente, nombre, apellido, cinro,f.saldo, f.montocuota, f.fechavence, plazo from cliente as c inner join FacturaVenta as f on(f.idcliente=c.idcliente) and f.saldo >0");
             //rs = consultamarca.executeQuery("select f.idfacturaventa, f.idcliente, nombre, apellido, cinro, f.saldo, f.montocuota, f.fechavence, plazo, pg.montoabonado, cantcuotapagado, pg.saldo from cliente as c inner join PagosCuotas as pg inner join FacturaVenta as f on(f.idcliente=c.idcliente)"+"and f.saldo > 0");
 
            modelo = new DefaultTableModel();
            tablacliente.setModel(modelo);

            modelo.addColumn("Id Factura");
            modelo.addColumn("Codigo");;
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
          //  modelo.addColumn("Saldo");
           // modelo.addColumn("Monto de la Cuota");
          //  modelo.addColumn("Fecha Vence");
           // modelo.addColumn("Plazo");
           // modelo.addColumn("Monto Abonado");
           // modelo.addColumn("Cuotas Ṕagadas");

            filas = new Object[modelo.getColumnCount()];

            while (rs.next()) {
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(filas);

            }

            tablacliente.setModel(modelo);

        } catch (Exception e) {
            System.out.println("Error al mostrar datos en la tabla" + e.getMessage());
        }
    }
    
    void verrecibo(){
        try {
            Statement consulta = (Statement) conexionBD.ConectarBD().createStatement();
            rso = consulta.executeQuery("select idreciboventa, numerofactura, montoabonado, saldo,p.idpagocuota from reciboventa, pagoscuotas as p where p.idpagocuota="+idpagocuta.getText());
            rso.last();
            modelo3 = new DefaultTableModel();
            tablarecibo.setModel(modelo3);

            modelo3.addColumn("Nº Recibo");
            modelo3.addColumn("Nº Factura");;
            modelo3.addColumn("Importe");
            modelo3.addColumn("Saldo a Pagar");
          

            filas = new Object[modelo3.getColumnCount()];

            while (rso.next()) {
                for (int i = 0; i < modelo3.getColumnCount(); i++) {
                    filas[i] = rso.getObject(i + 1);
                }

                modelo3.addRow(filas);

            }

            tablarecibo.setModel(modelo3);

        } catch (Exception e) {
            System.out.println("Error al mostrar datos en la tabla" + e.getMessage());
        }
    }
    
  public void cargarrecibo() {
        String[] titulos = {"Nº Recibo", "Nº Factura", "Importe", "Saldo a Pagar"};
        String[] registros = new String[4];
        String sql = "select idreciboventa, numerofactura, montoabonado, saldo,p.idpagocuota from reciboventa, pagoscuotas as p where p.idpagocuota="+idpagocuta.getText();
        //String sql = "select f.idfacturaventa, f.idcliente, nombre, apellido, cinro, f.saldo, f.montocuota, f.fechavence, plazo, pg.montoabonado, cantcuotapagado, pg.saldo from cliente as c inner join PagosCuotas as pg inner join FacturaVenta as f on(f.idcliente=c.idcliente)  where  f.idfacturaventa LIKE '%" + txtBuscarCliente.getText() + "%'"+"and f.saldo > 0";
        System.out.println(sql);


        modelo3 = new DefaultTableModel(null, titulos);

        conexionBD cno = new conexionBD();
        Connection cmn = (Connection) cno.ConectarBD();
        com.mysql.jdbc.Statement stt;
        try {

            stt = (com.mysql.jdbc.Statement) cmn.createStatement();
            ResultSet rstx = stt.executeQuery(sql);

            while (rstx.next()) {
                registros[0] = rstx.getString("idreciboventa");
                registros[1] = rstx.getString("numerofactura");
                registros[2] = rstx.getString("montoabonado");
                registros[3] = rstx.getString("saldo");
                //registros[4] = rstx.getString("p.idpagocuota");               
               // registros[9] = rst.getString("pg.montoabonado");
                //registros[10] = rst.getString("cantcuotapagado");
                //registros[11] = rst.getString("pg.saldo");

                modelo3.addRow(registros);
            }
            tablarecibo.setModel(modelo3);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }  
    
    
    public void limpiar(){
        txtCliente.setText("");
        txtApellido.setText("");
        txtCuota.setText("");
        txtSaldo.setText("");
        fechavence.setText("");
        txtMonCuotax.setText("");
        txtPlazo.setText("");
        txtNroCuota.setText("");
        txtAbono.setText("");
        txtmontopagado.setText("");
        txtSaldo12.setText("");
 
    }
    public void limpiar2(){
      //  txtImporte.setText("");
        txtSaldopagar.setText("");
    }
    public  boolean validardatos(){
   vacio=false;
   if(txtNroCuota.getText().isEmpty()){
       vacio=true;
        }
   if(txtAbono.getText().isEmpty()){
       vacio=true;
   }
   
   return vacio;
}
     public  boolean validardatos2(){
        vacio=false;
        if(txtImporte.getText().isEmpty()){
            vacioo=true;
        }
        return vacioo;
    }
   public void calcularsaldoanulado() {

        double montoabonado, saldo, saldototal;
        montoabonado = Double.parseDouble(String.valueOf(txtSaldo.getText()));
        saldototal= Double.parseDouble(String.valueOf(txtmontopagado.getText()));
        saldo = montoabonado - saldototal;
        txtSaldo12.setText(String.valueOf(saldo));
        txtSaldopagar.setText(String.valueOf(saldo));
        
       
            
    }
     /*public void calcularsaldo2() {

        double montoabonado, saldo, saldototal;
        montoabonado = Double.parseDouble(String.valueOf(txtAbono.getText()));
        saldototal= Double.parseDouble(String.valueOf(txtSaldo.getText()));
        saldo = saldototal - montoabonado;
        txtsaldorestar.setText(String.valueOf(saldo));
    }*/
public void calcularcuotas(){
        Integer montoaguardar, canticuota, montocuotasiempre;
        montoaguardar = (Integer.valueOf(txtmontopagado.getText()));
        montocuotasiempre =(Integer.valueOf(txtMonCuotax.getText()));
        canticuota = montoaguardar/montocuotasiempre ;
        txtCuota.setText(String.valueOf(canticuota));
         System.out.println("2016");
        //txtNroCuota.setText(String.valueOf(canticuota));
        /* double montoaguardar, canticuota, montocuotasiempre;
        montoaguardar = Double.parseDouble(String.valueOf(txtmontopagado.getText()));
        montocuotasiempre = Double.parseDouble(String.valueOf(txtMonCuotax.getText()));
        canticuota = montoaguardar/montocuotasiempre ;
        txtCuota.setText(String.valueOf(canticuota));*/
}
void agregartablafactura(){
    
    
    String[] titulos = {"Id Factura", "Codigo", "Nombre", "Apellido", "C.I.N°", "Saldo", "Monto de la Cuota", "Fecha Vence","Plazo","Monto Abonado","Cuota Pagado"};        
        String[] registros = new String[9];
        String sql = "select idreciboventa, numerofactura, montoabonado, saldo, p.idpagocuota from reciboventa   inner join pagoscuotas as p on(reciboventa.idpagocuota=p.idpagocuota)  where p.idpagocuota="+txtIdCuotas.getText();
       // String sql = "select f.idfacturaventa, f.idcliente, nombre, apellido, cinro, f.saldo, f.montocuota, f.fechavence, plazo, pg.montoabonado, cantcuotapagado, pg.saldo from cliente as c inner join PagosCuotas as pg inner join FacturaVenta as f on(f.idfacturaventa=pg.idfacturaventa)   where CONCAT(f.idcliente,' ',nombre,' ',apellido,' ',cinro,' ',f.saldo,' ',f.montocuota,' ',cantcuotapagado) LIKE '%" + valor + "%'"+"and f.saldo > 0";

        // sql+=" where idproducto="+txtIdProducto.getText();

        modelo = new DefaultTableModel(null, titulos);
       DefaultTableModel tabla = (DefaultTableModel) this.tablarecibo.getModel();

        conexionBD cnx = new conexionBD();
        Connection cnn = (Connection) cnx.ConectarBD();
        com.mysql.jdbc.Statement st;
        try {

            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rss = st.executeQuery(sql);

            while (rss.next()) {
                registros[0] = rss.getString("idreciboventa");
                registros[1] = rss.getString("numerofactura");
                registros[2] = rss.getString("montoabonado");
                registros[3] = rss.getString("saldo");
                registros[4] = rss.getString("p.idpagocuota");
                
                
                tabla.addRow(registros);
            }
            tablacliente.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }  
     //  DefaultTableModel tabla = (DefaultTableModel) this.tablarecibo.getModel();
        
      
      // iva = Integer.parseInt(txtIVA.getText().toString());
     
       
          /* Object[] valor = new Object[4];
           valor[0] = (String) txtIdRecibo.getText();
           valor[1] = (String) txtIdfactura.getText();
           valor[2] = (String) txtImporte.getText();
           valor[3] = (String) txtSaldopagar.getText();*/

          // tabla.addRow(valor);
       
       
       
    
 } 
void obtenerfecha(){
        Date fecha = new Date();
        SimpleDateFormat fe = new SimpleDateFormat("yyyy/MM/dd");
   //     this.fecha.setText(fe.format(fecha));   
    }


public void sumamonto(){
    double montoabonado, saldo, montocuota, montoabonadoresta;
        montoabonado = Double.parseDouble(String.valueOf(txtmontopagado.getText()));
        montocuota = Double.parseDouble(String.valueOf(txtAbono.getText()));
        saldo = montoabonado +montocuota ;
        txtmontopagado.setText(String.valueOf(saldo));
        
       // montoabonadoresta = Double.parseDouble(String.valueOf(txtmontopagado.getText()));
       
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
     //<editor-fold Defaultstate="collapsed" desc="Generated Code"> GEN-COMIENZO: initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated Code"> GEN-COMIENZO: initComponents                      
    private void initComponents() {

        buscarcliente = new javax.swing.JDialog();
        txtBuscarCliente = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        recibo = new javax.swing.JDialog();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        txtImporte = new javax.swing.JTextField();
        idpagocuta = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtObservacion = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablarecibo = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtapellido = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtcin = new javax.swing.JTextField();
        btnAceptarImpri = new javax.swing.JButton();
        txtIdfactura = new javax.swing.JTextField();
        txtIdRecibo = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        brnCancelarrecibo = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        txtSaldopagar = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        btnGuardarrecibo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txtIdCuotas = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNroCuota = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPlazo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        txtidfac = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtAbono = new javax.swing.JTextField();
        fechavence = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCuota = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtMonCuotax = new javax.swing.JTextField();
        txtSaldo = new javax.swing.JTextField();
        txtmontopagado = new javax.swing.JTextField();
        fechapago = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtSaldo12 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();

        txtBuscarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarClienteActionPerformed(evt);
            }
        });
        txtBuscarCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClienteKeyReleased(evt);
            }
        });

        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tablacliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellido", "C.I.N."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablacliente.getTableHeader().setReorderingAllowed(false);
        tablacliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaclienteMouseClicked(evt);
            }
        });
        tablacliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaclienteKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablacliente);

        javax.swing.GroupLayout buscarclienteLayout = new javax.swing.GroupLayout(buscarcliente.getContentPane());
        buscarcliente.getContentPane().setLayout(buscarclienteLayout);
        buscarclienteLayout.setHorizontalGroup(
            buscarclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscarclienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscarclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buscarclienteLayout.createSequentialGroup()
                        .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addContainerGap(287, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)))
        );
        buscarclienteLayout.setVerticalGroup(
            buscarclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscarclienteLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(buscarclienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jLabel13.setText("jLabel13");

        recibo.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        recibo.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Perpetua Titling MT", 3, 48));
        jLabel16.setForeground(new java.awt.Color(102, 204, 255));
        jLabel16.setText("RECIBO");
        recibo.getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 210, -1));

        jLabel17.setText("Nº Fac.");
        recibo.getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 201, -1, -1));
        recibo.getContentPane().add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, -1, -1));
        recibo.getContentPane().add(txtImporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 201, 87, -1));

        idpagocuta.setEnabled(false);
        recibo.getContentPane().add(idpagocuta, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 30, -1));

        jLabel18.setText("Importe:");
        recibo.getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 201, -1, -1));

        txtObservacion.setBorder(javax.swing.BorderFactory.createTitledBorder("Observaciones"));
        recibo.getContentPane().add(txtObservacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 340, 60));

        tablarecibo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nº Recibo", "Nº Factura", "Importe", "Saldo a Pagar"
            }
        ));
        tablarecibo.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tablarecibo);

        recibo.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 231, -1, 92));

        jLabel19.setText("Nombre:");
        recibo.getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        txtnombre.setEnabled(false);
        recibo.getContentPane().add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 110, -1));

        jLabel20.setText("Apellido");
        recibo.getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        txtapellido.setEnabled(false);
        recibo.getContentPane().add(txtapellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 100, -1));

        jLabel21.setText("C.I.Nº");
        recibo.getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 50, 20));

        txtcin.setEnabled(false);
        recibo.getContentPane().add(txtcin, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 110, -1));

        btnAceptarImpri.setText("Imprimir");
        recibo.getContentPane().add(btnAceptarImpri, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 370, -1, -1));

        txtIdfactura.setEnabled(false);
        txtIdfactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdfacturaActionPerformed(evt);
            }
        });
        recibo.getContentPane().add(txtIdfactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 29, -1));

        txtIdRecibo.setEnabled(false);
        recibo.getContentPane().add(txtIdRecibo, new org.netbeans.lib.awtextra.AbsoluteConstraints(117, 40, 27, -1));

        jLabel23.setText("Cuota");
        recibo.getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jLabel25.setText("rec");
        recibo.getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        brnCancelarrecibo.setText("Salir");
        brnCancelarrecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnCancelarreciboActionPerformed(evt);
            }
        });
        recibo.getContentPane().add(brnCancelarrecibo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, -1, -1));

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        recibo.getContentPane().add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, -1, -1));

        txtSaldopagar.setEnabled(false);
        recibo.getContentPane().add(txtSaldopagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, 110, -1));

        jLabel26.setText("Saldo:");
        recibo.getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, -1, -1));

        btnGuardarrecibo.setText("Guardar");
        btnGuardarrecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarreciboActionPerformed(evt);
            }
        });
        recibo.getContentPane().add(btnGuardarrecibo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 18));
        jLabel1.setForeground(new java.awt.Color(60, 139, 239));
        jLabel1.setText("Cobros de Cuotas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(173, 21, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 55, 480, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 190, 10));

        txtIdCuotas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(txtIdCuotas, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 80, 40));

        jLabel2.setText("N° Cuota:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 350, -1, -1));

        txtNroCuota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNroCuotaMouseClicked(evt);
            }
        });
        txtNroCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNroCuotaActionPerformed(evt);
            }
        });
        txtNroCuota.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNroCuotaFocusLost(evt);
            }
        });
        txtNroCuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNroCuotaKeyTyped(evt);
            }
        });
        getContentPane().add(txtNroCuota, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 70, -1));

        jLabel3.setText("Monto de La Cuota:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 190, -1, -1));

        txtPlazo.setEnabled(false);
        getContentPane().add(txtPlazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 220, 70, -1));

        jLabel5.setText("Fecha de Vencimiento:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 140, -1, -1));

        jLabel6.setText("Fecha de Vencimineto de la proxima cuota:");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, -1, -1));

        jLabel7.setText("Saldo Total de la Factura:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 360, -1, -1));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 420, -1, -1));

        btnImprimir.setText(" Recibo");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        getContentPane().add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 490, -1, -1));

        jLabel8.setText("CI N°:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

        txtCliente.setEnabled(false);
        getContentPane().add(txtCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 130, -1));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clientes.png"))); // NOI18N
        btnBuscar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, 40, 30));

        txtidfac.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(txtidfac, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 50, 30));

        jLabel9.setText("Monto Abonado:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, -1, -1));

        txtAbono.setText("0");
        txtAbono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAbonoFocusLost(evt);
            }
        });
        txtAbono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAbonoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAbonoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAbonoKeyTyped(evt);
            }
        });
        getContentPane().add(txtAbono, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 410, 100, -1));
        getContentPane().add(fechavence, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 100, -1));

        jLabel10.setText("Apellido:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, -1));

        txtApellido.setEnabled(false);
        getContentPane().add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 130, -1));

        jLabel11.setText("Cantidad de Cuota Pagado:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, -1));

        txtCuota.setText("0");
        txtCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCuotaActionPerformed(evt);
            }
        });
        txtCuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCuotaKeyTyped(evt);
            }
        });
        getContentPane().add(txtCuota, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 130, -1));

        jLabel12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 570, 200));

        jLabel14.setText("Plazo:");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, -1, -1));

        txtMonCuotax.setEnabled(false);
        getContentPane().add(txtMonCuotax, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 70, -1));

        txtSaldo.setEnabled(false);
        getContentPane().add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 130, -1));

        txtmontopagado.setText("0");
        txtmontopagado.setEnabled(false);
        txtmontopagado.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                txtmontopagadoCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txtmontopagado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtmontopagadoKeyReleased(evt);
            }
        });
        getContentPane().add(txtmontopagado, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, 130, -1));

        fechapago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fechapagoKeyPressed(evt);
            }
        });
        getContentPane().add(fechapago, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 490, -1, -1));

        jLabel4.setText("Total Pagado:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, -1, -1));

        jLabel15.setText("Saldo:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, -1, -1));

        txtSaldo12.setEnabled(false);
        getContentPane().add(txtSaldo12, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, 130, 30));

        jLabel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 74, 600, 260));

        pack();
    }// </editor-fold>/ / GEN-END: initComponents                    

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        vacio=validardatos();
        try {
            
            
            abm.start();
            
            

            //********************************pago fecha                
           

            miFecha = fechapago.getCalendar();
            miFecha.setTime(fechapago.getDate());
            String diaa = String.valueOf(miFecha.get(Calendar.DATE));
            String mess = String.valueOf(miFecha.get(Calendar.MONTH) + 1);
            String anioo = String.valueOf(miFecha.get(Calendar.YEAR));
         if(vacio==true){
             JOptionPane.showMessageDialog(null,"Completar los datos marcados en aterisco");
         } 
         else{
            
                v_control = abm.insertar("PagosCuotas", txtIdCuotas.getText() + "," + txtNroCuota.getText() + "," + txtMonCuotax.getText() +",'" +anioo + "/" + mess + "/" + diaa  + "','" + fechavence.getText() + "'," + txtmontopagado.getText() + "," + txtAbono.getText() + ","+txtCuota.getText()+"," + txtidfac.getText());
               // System.out.println("despues del print");

            if (v_control == false) {
               // System.out.println("8");
                abm.roolback();  
            }
            
            
           // fechapago.setDate(Fecha.getTime());

            Fecha = fechapago.getCalendar();
            Fecha.setTime(fechapago.getDate());
            String diaaa = String.valueOf(Fecha.get(Calendar.DATE));
            String messs = String.valueOf(Fecha.get(Calendar.MONTH) + 1);
            String aniooo = String.valueOf(Fecha.get(Calendar.YEAR));
            v_control=abm.insertar("reciboventa",txtIdRecibo.getText()+","+txtIdfactura.getText()+",'" + aniooo + "/" + messs + "/" + diaaa + "'," +txtAbono.getText()+","+idpagocuta.getText());
                                if (v_control==true){
                                JOptionPane.showMessageDialog(null,"Se ha guardado los datos para el recibo");   
                                
                                }                                
                                if (v_control == false) {
                                    //System.out.println("8");
                                    abm.roolback();  
                                }
            
           if(v_control==true){
               // System.out.println("968498486449 ants del upsate");
                     v_control= abm.modificar("FacturaVenta", "fechavence='"+aniooo + "/" + messs + "/" + diaaa +"'", " idfacturaventa="+txtidfac.getText());
                           // System.out.println("968498486449 despues del upsate");
                            
                    if (v_control == false) {
                            System.out.println("8");
                            abm.roolback();  
                        }
                }
            
            double montoabonado, saldo, saldototal;
            montoabonado = Double.parseDouble(String.valueOf(txtSaldo.getText()));
            saldototal= Double.parseDouble(String.valueOf(txtmontopagado.getText()));
            saldo = montoabonado - saldototal;
            
            //System.out.println("9");
            if(v_control==true){
                if (saldo==0){String estado="pagado", anulado="si";
                     v_control= abm.modificar("FacturaVenta", "estado='"+estado+"', "+"anulado='"+anulado+"', "+ "saldo=saldo-"+txtmontopagado.getText(), " idfacturaventa="+txtidfac.getText());
                           // System.out.println("10 ants del upsate");
                            /*Statement consulta = (Statement) conexionBD.ConectarBD().createStatement(); 
                            System.out.println("justito");
                            consulta.execute("update FacturaVenta set estado='"+estado+"', "+"anulado='"+anulado+"'"+" where idfacturaventa="+txtidfac.getText());  
                            */
                            System.out.println("si update");
                            }
                    if (v_control == false) {
                            System.out.println("8");
                            abm.roolback();  
                        }
                }
            
      
            if (v_control == false) {
                            System.out.println("8");
                            abm.roolback();  
                        }
             System.out.println("comit");
            
            if (v_control == true) {
                abm.comit();
            }
            abm.end();
            JOptionPane.showMessageDialog(null, "Los Datos se han guardado satisfactoriamente");
           // idpagos();
        this.dispose();
      /*  recibo.setModal(true);
        recibo.setSize(570, 448);
        recibo.setLocationRelativeTo(this);
        recibo.setVisible(true); */
        
        
        
        
       
       
        btnImprimir.setEnabled(true);
            limpiar();
           }
        } catch (Exception ex) {
            System.out.println("Error al realizar la transacion " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Completar todos los campos para guardar");
        }
       
         
    cargarrecibo();
    verrecibo();  
    }                                          

    private void txtBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {                                             
        if (txtBuscarCliente.getText().isEmpty()) {
            verclintes();
        } else {
            cargarclinetesbuscar("");
        }
    }                                            

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.buscarcliente.dispose();
        calcularcuotas();
//        calcularsaldoanulado();
    }                                        

    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {                                          
    
        seleccionartablacliente();
    }                                         

    private void tablaclienteKeyReleased(java.awt.event.KeyEvent evt) {                                         
        seleccionartablacliente();
    }                                        

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        buscarcliente.setModal(true);
        buscarcliente.setSize(500, 400);
        buscarcliente.setLocationRelativeTo(this);
        buscarcliente.setVisible(true);
        //habilitarbotones(true);
    }                                         

    private void txtAbonoKeyReleased(java.awt.event.KeyEvent evt) {                                     

        if (txtAbono.getText().isEmpty()){
            txtAbono.setText("0");
        }
        int saldo, importe;             
           saldo = Integer.parseInt(String.valueOf(txtSaldo.getText().toString()));
           importe= Integer.parseInt(String.valueOf(txtAbono.getText()).toString());
           
       if(importe <= saldo){
         //  sumamonto();
   
                       // calcularsaldo();

       }else{
           
           JOptionPane.showMessageDialog(null, "El importe no puede ser mayor al saldo");
           txtAbono.setText(String.valueOf(saldo));
       }
        
        
         //calcularcuotas();
        // sumamonto();
    }                                    

    private void txtAbonoKeyPressed(java.awt.event.KeyEvent evt) {                                    



    }                                   

    private void txtmontopagadoKeyReleased(java.awt.event.KeyEvent evt) {                                           
       
    }                                          

    private void txtmontopagadoCaretPositionChanged(java.awt.event.InputMethodEvent evt) {                                                    
     
    }                                                   

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:r()
        limpiar();
        this.dispose();
    }                                           

private void txtCuotaActionPerformed(java.awt.event.ActionEvent evt) {                                         
// TODO add your handling code here:
}                                        

private void txtNroCuotaActionPerformed(java.awt.event.ActionEvent evt) {                                            
// TODO add your handling code here:
}                                           

private void txtAbonoFocusLost(java.awt.event.FocusEvent evt) {                                   
// TODO add your handling code here:
    calcularcuotas();
    sumamonto();  
    calcularsaldoanulado();
    
}                                  
      
private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {                                            
   // TODO add your handling code here:
                 recibo.setModal(true);
                 recibo.setSize(570, 450);
                 recibo.setLocationRelativeTo(this);
                 recibo.setVisible(true);                 
                 
                 
                /* double importe;
                 importe = Double.parseDouble(String.valueOf(txtAbono.getText()));
                 System.out.println(importe);
                 txtImporte.setText(String.valueOf(importe));*/
                 
}                                           

private void brnCancelarreciboActionPerformed(java.awt.event.ActionEvent evt) {                                                  
// TODO add your handling code here:
    limpiar2();
    //idpagos();
    this.recibo.dispose();
    this.dispose();
    
}                                                 

private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {                                           
// TODO add your handling code here:
    agregartablafactura();
    limpiar2();
}                                          

private void txtIdfacturaActionPerformed(java.awt.event.ActionEvent evt) {                                             
// TODO add your handling code here:
}                                            

private void btnGuardarreciboActionPerformed(java.awt.event.ActionEvent evt) {                                                 
// TODO add your handling code here:
    
    vacioo=validardatos2();
        if(vacioo==true){
            JOptionPane.showMessageDialog(null,"Completar los datos marcados en aterisco");
            
        }else{
    fechapago.setDate(miFecha.getTime());

            miFecha = fechapago.getCalendar();
            miFecha.setTime(fechapago.getDate());
            String diaa = String.valueOf(miFecha.get(Calendar.DATE));
            String mess = String.valueOf(miFecha.get(Calendar.MONTH) + 1);
            String anioo = String.valueOf(miFecha.get(Calendar.YEAR));
            v_control=abm.insertar("reciboventa",txtIdRecibo.getText()+","+txtIdfactura.getText()+",'" + anioo + "/" + mess + "/" + diaa + "'," +txtImporte.getText()+","+idpagocuta.getText());
                                if (v_control==true){
                                JOptionPane.showMessageDialog(null,"Se ha guardado los datos");   
                                
                                }
              }
}                                                

private void txtNroCuotaMouseClicked(java.awt.event.MouseEvent evt) {                                         
// TODO add your handling code here:
   // calcularcuotas();
    //calcularsaldoanulado();
}                                        

private void fechapagoKeyPressed(java.awt.event.KeyEvent evt) {                                     
// TODO add your handling code here:
    

}                                    

private void txtAbonoKeyTyped(java.awt.event.KeyEvent evt) {                                  
char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}                                 

private void txtNroCuotaFocusLost(java.awt.event.FocusEvent evt) {                                      
// TODO add your handling code here:
     calcularsaldoanulado();
}                                     

private void txtCuotaKeyTyped(java.awt.event.KeyEvent evt) {                                  
char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}                                 

private void txtNroCuotaKeyTyped(java.awt.event.KeyEvent evt) {                                     
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}                                    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CobroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CobroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CobroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CobroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                CobroCliente dialog = new CobroCliente(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton brnCancelarrecibo;
    private javax.swing.JButton btnAceptarImpri;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarrecibo;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JDialog buscarcliente;
    private com.toedter.calendar.JDateChooser fecha;
    private com.toedter.calendar.JDateChooser fechapago;
    private javax.swing.JTextField fechavence;
    private javax.swing.JTextField idpagocuta;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
   
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JDialog recibo;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablarecibo;
    private javax.swing.JTextField txtAbono;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCuota;
    private javax.swing.JLabel txtIdCuotas;
    private javax.swing.JTextField txtIdRecibo;
    private javax.swing.JTextField txtIdfactura;
    private javax.swing.JTextField txtImporte;
    private javax.swing.JTextField txtMonCuotax;
    private javax.swing.JTextField txtNroCuota;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtPlazo;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtSaldo12;
    private javax.swing.JTextField txtSaldopagar;
    private javax.swing.JTextField txtapellido;
    private javax.swing.JTextField txtcin;
    private javax.swing.JLabel txtidfac;
    private javax.swing.JTextField txtmontopagado;
    private javax.swing.JTextField txtnombre;
    // End of variables declaration                   
}



