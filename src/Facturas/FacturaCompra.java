
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


public class FacturaCompra extends javax.swing.JDialog {
    
    conexionBD cn;
    private ResultSet rs;
    DefaultTableModel modelo;
    private Object[] filas;
    DefaultTableModel modeloo;
    private boolean v_control;
    private abm abm;
    private boolean vacio;
    Calendar FechaVence;
    
    
    
    public FacturaCompra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();
            abm=new abm();
            cn= new conexionBD();
            FechaVence = new GregorianCalendar();
            
            
            
            btnContado.setSelected(true);
            cargarclientes("");
            obtenerfecha();
            verclintes();
            cargarproducto("");
            verproducto();
            //txtIVA.setVisible(false);
            //txtIdproveedor.setVisible(false);
            txtidfac.setVisible(false);
            txtIdproveedor.setVisible(false);
             rs = abm.nuevo("numero", "FacturaCompra");
            try {
                rs.first();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
                txtNumFac.setText(String.valueOf(rs.getInt("codigo") + 1));
        } catch (SQLException ex) {
            Logger.getLogger(FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        idfactura();
        //cargarclientedefault();
        habilitarcliente();
        calendario();
        
    }
    void calendario(){
        fecha1.setDate(FechaVence.getTime());
        
        FechaVence = fecha1.getCalendar();
                FechaVence.setTime(fecha1.getDate());
                String dia =String.valueOf(FechaVence.get(Calendar.DATE));
                String mes =String.valueOf(FechaVence.get(Calendar.MONTH)+1);
                String anio =String.valueOf(FechaVence.get(Calendar.YEAR));
                String date = anio+"/"+mes+"/"+dia;
    }

    public void cargarclientedefault(){
        txtnombre.setText("Juan");
        txtApellido.setText("Perez");
        txtDireccion.setText("Direccion");
        txtCin.setText("12345");
        txtTelef.setText("202020");
    }

    public void habilitarbotones(boolean j){
        buscarProdu.setEnabled(j);
        
    }
    public void habilitarbotonestg(boolean h){
        AgregarProducto.setEnabled(h);
        txtCantidad.setEnabled(h);
        txtPrecioCompra.setEnabled(h);
    }
    public void habilitarbotones2(boolean k){
        btnGuardar.setEnabled(k);
        btnCancelar.setEnabled(k);
        btnQuitar.setEnabled(k);
        //btnCredito.setEnabled(k);
    }
    public void limpiarcamposcabezara(){
        txtnombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtCin.setText("");
        txtTelef.setText("");
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtPrecioCompra.setText("");
        txtTotal1.setText("");
        txtExentas.setText("");
        txtDies.setText("");
        txtTotal.setText("");
        txtIva5.setText("");
        txtIva10.setText("");
        txtTotalIva.setText("");
        txtCinco.setText("");
        txtObservacion.setText("");
        
        DefaultTableModel tablaborrar = (DefaultTableModel) this.tablafactura.getModel();
        while(tablaborrar.getRowCount()>0)tablaborrar.removeRow(0);
    }
     
    void obtenerfecha(){
        Date fecha = new Date();
        SimpleDateFormat fe = new SimpleDateFormat("yyyy/MM/dd");
        this.fecha.setText(fe.format(fecha));   
    }
    public void idfactura(){
        try {
            rs = abm.nuevo("idfacturaventa","FacturaVenta");
            try {
                rs.first();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtidfac.setText(String.valueOf(rs.getInt("codigo")+1));
        } catch (SQLException ex) {
            Logger.getLogger(FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cargarclientes(String valor ){
        String [] titulos  = {"Codigo","Nombre","R.U.C.","Telefono","Direccion"};
        String [] registros  = new String [5];
        String sql = "select idproveedor, nombre, telefono, direccion, RUC from proveedor where CONCAT(idproveedor,' ',nombre,' ',telefono,' ',direccion,' ',RUC) LIKE '%"+valor+"%'";   
         
        // sql+=" where idproducto="+txtIdProducto.getText();
         
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cnx = new conexionBD();
        Connection cnn = (Connection) cnx.ConectarBD();
        com.mysql.jdbc.Statement  st;
        try {
            
            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rss = st.executeQuery(sql);
            
            while(rss.next()){
                registros[0] = rss.getString("idproveedor");
                registros[1] = rss.getString("nombre");
                registros[2] = rss.getString("telefono");
                registros[3] = rss.getString("direccion");
                registros[4] = rss.getString("RUC");
                modelo.addRow(registros);
                }
            tablacliente.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
     public void cargarclinetesbuscar(String valor ){
        String [] titulos  = {"Codigo","Nombre","R.U.C.","Telefono","Direccion"};
        String [] registros  = new String [5];
        String sql = "select idproveedor, nombre, telefono, direccion, RUC from proveedor where CONCAT(nombre,' ',RUC) LIKE '%"+txtBuscarCliente.getText()+"%'";  
        
        
         
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cnp = new conexionBD();
        Connection cnn = (Connection) cnp.ConectarBD();
        com.mysql.jdbc.Statement  st;
        try {
            
            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);
             
            while(rst.next()){
                registros[0] = rst.getString("idproveedor");
                registros[1] = rst.getString("nombre");
                registros[2] = rst.getString("telefono");
                registros[3] = rst.getString("direccion");
                registros[4] = rst.getString("RUC");
                
                modelo.addRow(registros);
                }
            tablacliente.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    void verclintes(){
        try{
            com.mysql.jdbc.Statement consultamarca = (com.mysql.jdbc.Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultamarca.executeQuery("select idproveedor, nombre, telefono, direccion, RUC from proveedor");
            
            modelo = new DefaultTableModel();
            tablacliente.setModel(modelo);
            
            modelo.addColumn("Codigo");
            modelo.addColumn("Nombre");
            modelo.addColumn("Telefono");
            modelo.addColumn("Direccion");
            modelo.addColumn("R.U.C");
            
            filas = new Object[modelo.getColumnCount()];
            
            while(rs.next()){
                for(int i=0;i<modelo.getColumnCount();i++){
                    filas[i]=rs.getObject(i+1);
                }
                
                modelo.addRow(filas);
                
            }
            
            tablacliente.setModel(modelo);
    
        }catch(Exception e){
            System.out.println("Error al mostrar datos en la tabla"+e.getMessage());
        }
    }   
    public void seleccionartablacliente(){
        
        
      // DefaultTableModel tabla = (DefaultTableModel) this.tablaCliente.getModel();
       
       
       int c= tablacliente.getSelectedRow();
       
       if(c==-1){
           System.out.println("Seleccione un registro");
            }
       else{
           Integer id = Integer.parseInt(tablaProducto.getValueAt(c, 0).toString());
           String nombre = (String) tablacliente.getValueAt(c, 1);
           String telefono= (String) tablacliente.getValueAt(c, 2);;
           String direccion= (String) tablacliente.getValueAt(c, 3);
           String ruc = (String) tablacliente.getValueAt(c, 4);
           
           

         //  this.txtIdcliente.setText(id);
           txtIdproveedor.setText(Integer.toString(id));
           
           this.txtnombre.setText(nombre);
           this.txtCin.setText(ruc);        
           this.txtTelef.setText(telefono);
           this.txtDireccion.setText(direccion);
           
           }       
    }
    
    public void cargarproductobuscar(){
        String [] titulos  = {"Codigo","Precio Compra","Precio Venta","Descripcion","IVA"};
        String [] registros  = new String [5];
        String sql=null;
         sql = "select idproducto,preciodecompra,preciodeventa,descripcion";
         sql+=",iva";
         sql+=" from productos";
         sql+=" where preciodeventa LIKE '%"+txtBuscarProdu.getText()+"%'";
         //System.out.println(sql);
         
         
        DefaultTableModel tabla = new DefaultTableModel(null, titulos); 
        //tabla = new DefaultTableModel(null, titulos);
    
        conexionBD cnb = new conexionBD();
        Connection cnn = (Connection) cnb.ConectarBD();
        Statement  st;
        try {
            
            st = (Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);
            
            while(rst.next()){
                registros[0] = rst.getString("idproducto");
                registros[1] = rst.getString("preciodecompra");
                registros[2] = rst.getString("preciodeventa");
                registros[3] = rst.getString("descripcion");
                registros[4] = rst.getString("iva");
                tabla.addRow(registros);
                }
            tablaProducto.setModel(tabla);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    public void cargarproducto(String valor){
        String [] titulos  = {"Codigo","Precio Compra","Precio Venta","Descripcion","IVA"};
        String [] registros  = new String [5];
        String sql=null;
         sql = "select idproducto,preciodecompra,preciodeventa,descripcion";
         sql+=",iva";
         sql+=" from productos";
         sql+=" where idproducto=idproducto";
       
         
        DefaultTableModel m = new DefaultTableModel(null, titulos);
    
        conexionBD cn = new conexionBD();
        Connection cnn = (Connection) cn.ConectarBD();
        Statement  st;
        try {
            
            st = (Statement) cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                registros[0] = rs.getString("idproducto");
                registros[1] = rs.getString("preciodecompra");
                registros[2] = rs.getString("preciodeventa");
                registros[3] = rs.getString("descripcion");
                registros[4] = rs.getString("iva");
                m.addRow(registros);
                }
            tablaProducto.setModel(m);
            
            }catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    public void seleccionartablaproducto(){
        
        
       //DefaultTableModel tabla = (DefaultTableModel) this.tablacliente.getModel();
       
       
       int c= tablaProducto.getSelectedRow();
       
       if(c==-1){
           JOptionPane.showMessageDialog(null, "Seleccione un registro");
            }
       else{
          // String id = (String) tablaProducto.getValueAt(c, 0);
           Integer id = Integer.parseInt(tablaProducto.getValueAt(c, 0).toString());
           Integer preciocompra = Integer.parseInt(tablaProducto.getValueAt(c, 1).toString());
           //String precioventa = (String) tablaProducto.getValueAt(c, 1);
           String descripcion = (String) tablaProducto.getValueAt(c, 3);
           Integer precioventa = Integer.parseInt(tablaProducto.getValueAt(c, 2).toString());
           Integer iva = Integer.parseInt(tablaProducto.getValueAt(c, 4).toString());
           
           txtCodigo.setText(Integer.toString(id));
          // this.txtCodigo.setText(id);
           //this.txtPrecioVenta.setText(precioventa);
           txtPrecioCompra.setText(Integer.toString(preciocompra));
           this.txtDescripcion.setText(descripcion);
           txtPrecioVenta.setText(Integer.toString(precioventa));
           txtIVA.setText(Integer.toString(iva));
           }       
    }
    
    public void cargarproductoDos(){
        
        String sql=null;
         sql = "select idproducto,preciodecompra,preciodeventa,descripcion";
         sql+=",iva";
         sql+=" from productos";
          sql+=" where idproducto LIKE '"+txtCodigo.getText()+"'";
       
         
        
        conexionBD cn = new conexionBD();
        Connection cnn = (Connection) cn.ConectarBD();
        Statement  st;
        try {
            
            st = (Statement) cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){                
                this.txtPrecioCompra.setText(rs.getString("preciodecompra"));
                this.txtDescripcion.setText(rs.getString("descripcion"));
                this.txtPrecioVenta.setText(rs.getString("preciodeventa"));
                this.txtIVA.setText(rs.getString("iva"));
                }
            
            
            }catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    void habilitarcliente(){
        if(txtnombre.getText().isEmpty()){
            buscarProdu.setEnabled(false);
            }
       else{buscarProdu.setEnabled(true);
            }
    }
    public void habilitarguardar(){
        int x = tablafactura.getRowCount();
        
        if(tablafactura.getRowCount()!=0){

            btnGuardar.setEnabled(true);
        }
        else{
                btnGuardar.setEnabled(false);}
    }
    
    void verproducto(){
        try{
            Statement consultamarca = (Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultamarca.executeQuery("select idproducto,preciodecompra,preciodeventa,descripcion,iva  from productos order by idproducto");
            
            modelo = new DefaultTableModel();
            tablaProducto.setModel(modelo);
            
            
            modelo.addColumn("Codigo");
            modelo.addColumn("Precio CompraVenta");
            modelo.addColumn("Precio Venta");
            modelo.addColumn("Descripcion");
            modelo.addColumn("IVA");
            
            filas = new Object[modelo.getColumnCount()];
            
            while(rs.next()){
                for(int i=0;i<modelo.getColumnCount();i++){
                    filas[i]=rs.getObject(i+1);
                }                
                modelo.addRow(filas);               
            }
            
            tablaProducto.setModel(modelo);
    
        }catch(Exception e){
            System.out.println("Error al mostrar datos en la tabla"+e.getMessage());
        }
    }
    
    void agregartablafactura(){
        
       DefaultTableModel tabla = (DefaultTableModel) this.tablafactura.getModel();
        
       int iva= tablafactura.getSelectedRow();
       iva = Integer.parseInt(txtIVA.getText().toString());
     
       if(iva==0){
           Object[] valor = new Object[8];
           valor[0] = (String) txtCodigo.getText();
           valor[1] = (String) txtDescripcion.getText();
           valor[2] = (String) txtCantidad.getText();
           valor[3] = (String) txtPrecioCompra.getText();
           valor[4] = (String) txtPrecioVenta.getText();          
           valor[5] = (String) txtTotal1.getText();
           valor[6] = (String) "0";
           valor[7] = (String) "0";
           
           tabla.addRow(valor);
       }
       else{
           if(iva==5){
           Object[] valor = new Object[8];
           valor[0] = (String) txtCodigo.getText();
           valor[1] = (String) txtDescripcion.getText();
           valor[2] = (String) txtCantidad.getText();
           valor[3] = (String) txtPrecioCompra.getText();
           valor[4] = (String) txtPrecioVenta.getText(); 
           valor[5] = (String) "0";
           valor[6] = (String) txtTotal1.getText();
           valor[7] = (String) "0";
           
           tabla.addRow(valor);
           }
           else{
               if(iva==10){
                    Object[] valor = new Object[8];
                    valor[0] = (String) txtCodigo.getText();
                    valor[1] = (String) txtDescripcion.getText();
                    valor[2] = (String) txtCantidad.getText();
                    valor[3] = (String) txtPrecioCompra.getText();
                    valor[4] = (String) txtPrecioVenta.getText(); 
                    valor[5] = (String) "0";
                    valor[6] = (String) "0";
                    valor[7] = (String) txtTotal1.getText();

                    tabla.addRow(valor);
                }
       }
    }
 }  
    void sumadetalbla(){
        double sumatoriaTotal = 0;
        int totalRow = tablafactura.getRowCount();
        totalRow -= 1;
        double totalIva10 = 0, totalIva5 = 0, z = 0;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA TOTAL IVA 10%
            double sumatoria = Double.parseDouble(String.valueOf(tablafactura.getValueAt(i, 7)));
            totalIva10 += sumatoria;
            txtDies.setText(String.valueOf(totalIva10));
            double iva10 = (totalIva10 * 10) / 110;
            double redondeo = ((long) (iva10 * 100.0)) / 100.0;
            txtIva10.setText(String.valueOf(redondeo));
            

            //SUMA TOTAL IVA 5%
            double sumatoria1 = Double.parseDouble(String.valueOf(tablafactura.getValueAt(i, 6)));
            totalIva5 += sumatoria1;
            txtCinco.setText(String.valueOf(totalIva5));
            double iva5 = (totalIva5 * 5) / 105;
            double redondeo2 = ((long) (iva5 * 100.0)) / 100.0;
            txtIva5.setText(String.valueOf(redondeo2));
          
            //SUMA TOTAL EXENTAS
            double sumatoria2 = Double.parseDouble(String.valueOf(tablafactura.getValueAt(i, 5)));
            z += sumatoria2;
            txtExentas.setText(String.valueOf(z));
            sumatoriaTotal += (sumatoria + sumatoria1 + z);

            txtTotal.setText(String.valueOf(sumatoriaTotal));
            
            //SUMA PARA EL TOTAL DE IVA
            
            double iva5p = Double.parseDouble(String.valueOf(txtIva5.getText()));
            double iva10p = Double.parseDouble(String.valueOf(txtIva10.getText()));
            double sumaivap = iva5p+iva10p;
            txtTotalIva.setText(String.valueOf(sumaivap));
            
        }
    }
    public void limpiar(){
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtPrecioCompra.setText("");
        txtTotal1.setText("");
        txtPrecioVenta.setText("");
    }
    public void cargarcredito(){
       double valor = Double.parseDouble(String.valueOf(txtTotal.getText()));
       txtMontoPagar.setText(String.valueOf(valor));
       txtSaldo.setText(String.valueOf(valor));
    }
    public void calcularplazo(){
        
            double plazo, saldo, total;
            saldo =  Double.parseDouble(String.valueOf(txtSaldo.getText()));
            plazo = Double.parseDouble(String.valueOf(txtPlazo.getText()));
            total = saldo/plazo;
            txtMontoCuota.setText(String.valueOf(total));
    }
    public boolean validardatos(){
         vacio = false;
        if (txtPlazo.getText().isEmpty()) {
            vacio = true;
        }
        if(txtEntrega.getText().isEmpty()){
            vacio=true;
        }
        return vacio;
}
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Group1 = new javax.swing.ButtonGroup();
        buscarproveedor = new javax.swing.JDialog();
        txtBuscarCliente = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        buscarproductos = new javax.swing.JDialog();
        txtBuscarProdu = new javax.swing.JTextField();
        btnAceotarProdu = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProducto = new javax.swing.JTable();
        credito = new javax.swing.JDialog();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtPlazo = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtEntrega = new javax.swing.JTextField();
        btnGuardarCredito = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        txtMontoPagar = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtIdFacCredito = new javax.swing.JLabel();
        btnCancelarCredito = new javax.swing.JButton();
        txtMontoCuota = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        fecha1 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNumFac = new javax.swing.JTextField();
        txtidfac = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtTelef = new javax.swing.JTextField();
        txtCin = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        btnContado = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        txtIdproveedor = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablafactura = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        txtDies = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtIva10 = new javax.swing.JTextField();
        txtIva5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtExentas = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCinco = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtObservacion = new javax.swing.JTextField();
        buscarProdu = new javax.swing.JButton();
        txtTotal1 = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtPrecioCompra = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        AgregarProducto = new javax.swing.JButton();
        txtIVA = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        txtTotalIva = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtPrecioVenta = new javax.swing.JTextField();

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
                "Codigo", "Nombre", "Apellido", "C.I.N.", "Telefono", "Direccion"
            }
        ));
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

        jLabel41.setText("Introduzca Nombre del Proveedor");

        javax.swing.GroupLayout buscarproveedorLayout = new javax.swing.GroupLayout(buscarproveedor.getContentPane());
        buscarproveedor.getContentPane().setLayout(buscarproveedorLayout);
        buscarproveedorLayout.setHorizontalGroup(
            buscarproveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscarproveedorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buscarproveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(buscarproveedorLayout.createSequentialGroup()
                        .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addContainerGap(159, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
            .addGroup(buscarproveedorLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel41)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        buscarproveedorLayout.setVerticalGroup(
            buscarproveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscarproveedorLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(buscarproveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        buscarproductos.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        txtBuscarProdu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarProduActionPerformed(evt);
            }
        });
        txtBuscarProdu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProduKeyReleased(evt);
            }
        });

        btnAceotarProdu.setText("Aceptar");
        btnAceotarProdu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceotarProduActionPerformed(evt);
            }
        });

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buscar.png"))); // NOI18N

        tablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "PrecioVenta", "IVA%"
            }
        ));
        tablaProducto.getTableHeader().setReorderingAllowed(false);
        tablaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductoMouseClicked(evt);
            }
        });
        tablaProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaProductoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaProductoKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tablaProducto);

        javax.swing.GroupLayout buscarproductosLayout = new javax.swing.GroupLayout(buscarproductos.getContentPane());
        buscarproductos.getContentPane().setLayout(buscarproductosLayout);
        buscarproductosLayout.setHorizontalGroup(
            buscarproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscarproductosLayout.createSequentialGroup()
                .addComponent(jLabel23)
                .addGap(4, 4, 4)
                .addComponent(txtBuscarProdu, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAceotarProdu)
                .addGap(173, 173, 173))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buscarproductosLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        buscarproductosLayout.setVerticalGroup(
            buscarproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buscarproductosLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(buscarproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(buscarproductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtBuscarProdu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAceotarProdu)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        credito.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("FreeMono", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 204, 0));
        jLabel30.setText("FACTURACREDITO");
        credito.getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel31.setText("PLAZO:");
        credito.getContentPane().add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));

        txtPlazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlazoActionPerformed(evt);
            }
        });
        txtPlazo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPlazoFocusLost(evt);
            }
        });
        txtPlazo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPlazoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPlazoKeyTyped(evt);
            }
        });
        credito.getContentPane().add(txtPlazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 60, -1));

        jLabel32.setText("Saldo:");
        credito.getContentPane().add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, -1, -1));

        txtSaldo.setText("0");
        txtSaldo.setEnabled(false);
        credito.getContentPane().add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 260, 119, 30));

        jLabel33.setText("Monto de la Cuota:");
        credito.getContentPane().add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jLabel35.setText("Fecha:");
        credito.getContentPane().add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        jLabel36.setText("Entrega Inicial:");
        credito.getContentPane().add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        txtEntrega.setText("0");
        txtEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEntregaActionPerformed(evt);
            }
        });
        txtEntrega.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEntregaFocusLost(evt);
            }
        });
        txtEntrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEntregaKeyTyped(evt);
            }
        });
        credito.getContentPane().add(txtEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, 120, -1));

        btnGuardarCredito.setText("Guardar");
        btnGuardarCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCreditoActionPerformed(evt);
            }
        });
        credito.getContentPane().add(btnGuardarCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, 90, -1));

        jLabel37.setFont(new java.awt.Font("DejaVu Sans", 2, 13)); // NOI18N
        jLabel37.setText("Monto Total a Pagar:");
        credito.getContentPane().add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtMontoPagar.setEnabled(false);
        credito.getContentPane().add(txtMontoPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 100, -1));

        jLabel38.setText("N°");
        credito.getContentPane().add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        txtIdFacCredito.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        credito.getContentPane().add(txtIdFacCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 30, 20));

        btnCancelarCredito.setText("Cancelar");
        btnCancelarCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCreditoActionPerformed(evt);
            }
        });
        credito.getContentPane().add(btnCancelarCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 90, -1));

        txtMontoCuota.setText("0");
        txtMontoCuota.setEnabled(false);
        credito.getContentPane().add(txtMontoCuota, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 120, -1));
        credito.getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 40, 450, -1));

        jLabel39.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        credito.getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 270, 190));

        jLabel40.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        credito.getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 90, 110));
        credito.getContentPane().add(fecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(1, 1, 1));
        jLabel2.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 0));
        jLabel2.setText("RSystem");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 130, 50));

        jLabel3.setText("  Telefono (0971)431-770*(0971)777-177");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel5.setText("Coronel Ovieod-Paraguay");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jLabel6.setFont(new java.awt.Font("Courier New", 1, 13)); // NOI18N
        jLabel6.setText("La Mejor Tegnologia a su Alcance");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, -1));

        jLabel7.setText("Fecha de Emision:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, -1, -1));

        fecha.setText("dd/mm/yy");
        jPanel1.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 150, -1, -1));

        jLabel1.setFont(new java.awt.Font("Courier New", 2, 13)); // NOI18N
        jLabel1.setText("N° de Factura");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));

        txtNumFac.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNumFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumFacKeyTyped(evt);
            }
        });
        jPanel1.add(txtNumFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 30, 30));
        jPanel1.add(txtidfac, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 50, 30));

        jLabel4.setText("Tuyuti 160  c/ Blas A. Garay");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, 780, 170));

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setText("RUC o C.I.N.:");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jLabel15.setText("Proveedor:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel8.setText("Telef:");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, -1, -1));

        txtnombre.setEnabled(false);
        jPanel3.add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 100, -1));

        txtTelef.setEnabled(false);
        jPanel3.add(txtTelef, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 80, 120, -1));

        txtCin.setEnabled(false);
        jPanel3.add(txtCin, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 110, -1));

        txtApellido.setEnabled(false);
        jPanel3.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 360, -1));

        jLabel17.setText("Direccion:");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        txtDireccion.setEnabled(false);
        jPanel3.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 470, -1));

        jLabel19.setFont(new java.awt.Font("Gungsuh", 3, 12)); // NOI18N
        jLabel19.setText("Forma de Pago");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, -1, -1));

        Group1.add(btnContado);
        btnContado.setText("Contado");
        jPanel3.add(btnContado, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, -1, -1));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clientes.png"))); // NOI18N
        btnBuscar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel3.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 20, 80, 60));
        jPanel3.add(txtIdproveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 30, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 780, 160));

        tablafactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Precio Compra", "Precio Venta", "Excentas", "5%", "10%"
            }
        ));
        jScrollPane1.setViewportView(tablafactura);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 790, 100));

        jLabel12.setText("Excentas");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 580, -1, -1));

        txtDies.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDies.setEnabled(false);
        getContentPane().add(txtDies, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 550, 110, 30));

        jLabel13.setText("TOTAL A PAGAR:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 610, -1, -1));

        txtTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTotal.setEnabled(false);
        getContentPane().add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 600, 210, 30));

        jLabel10.setText("Liquidacion del IVA%:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, -1, 20));

        txtIva10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIva10.setEnabled(false);
        getContentPane().add(txtIva10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 640, 70, 30));

        txtIva5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIva5.setEnabled(false);
        getContentPane().add(txtIva5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 640, 70, 30));

        jLabel11.setText("(10%)");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 650, -1, 20));

        jLabel16.setText("(5%)");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 650, -1, 20));

        jLabel20.setText("I.V.A.5%");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 580, 60, -1));

        txtExentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtExentas.setEnabled(false);
        getContentPane().add(txtExentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 550, 110, 30));

        jLabel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Subtotal"));
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 530, 390, 110));

        jLabel9.setText("I.V.A.10%");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 580, -1, -1));

        txtCinco.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCinco.setEnabled(false);
        getContentPane().add(txtCinco, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 550, 120, 30));

        jLabel22.setText("Observacion:");
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, -1, -1));
        getContentPane().add(txtObservacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 400, 90));

        buscarProdu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/nuevacoompra.png"))); // NOI18N
        buscarProdu.setEnabled(false);
        buscarProdu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarProduActionPerformed(evt);
            }
        });
        getContentPane().add(buscarProdu, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 370, 80, 60));

        txtTotal1.setEnabled(false);
        getContentPane().add(txtTotal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 400, 120, -1));

        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 60, -1));

        txtDescripcion.setEnabled(false);
        getContentPane().add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 120, -1));

        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadFocusLost(evt);
            }
        });
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        getContentPane().add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 120, -1));

        txtPrecioCompra.setEnabled(false);
        txtPrecioCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecioCompraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioCompraKeyTyped(evt);
            }
        });
        getContentPane().add(txtPrecioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 400, 120, -1));

        jLabel24.setText("Total");
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 380, -1, -1));

        jLabel25.setText("Codigo");
        getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));

        jLabel26.setText("Descripcion");
        getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, -1, -1));

        jLabel27.setText("Cantidad");
        getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 380, -1, -1));

        jLabel28.setText("Precio Compra");
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 380, -1, -1));

        AgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/entrada.png"))); // NOI18N
        AgregarProducto.setEnabled(false);
        AgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarProductoActionPerformed(evt);
            }
        });
        getContentPane().add(AgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 370, 80, 60));

        txtIVA.setEnabled(false);
        txtIVA.setOpaque(true);
        getContentPane().add(txtIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 650, 100, 10));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/articulos.png"))); // NOI18N
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 490, 80, 60));

        jLabel29.setText("Total IVA:");
        getContentPane().add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 650, -1, -1));

        txtTotalIva.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTotalIva.setEnabled(false);
        getContentPane().add(txtTotalIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 640, 140, 30));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clear.png"))); // NOI18N
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 570, 80, 40));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/equis.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 630, 80, -1));

        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/basurero.png"))); // NOI18N
        btnQuitar.setEnabled(false);
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        getContentPane().add(btnQuitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 440, 80, 40));

        jLabel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 350, 110, 330));

        jLabel18.setText("Precio Venta");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 380, -1, -1));

        txtPrecioVenta.setEnabled(false);
        txtPrecioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioVentaActionPerformed(evt);
            }
        });
        getContentPane().add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 400, 100, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarproveedor.setModal(true);
        buscarproveedor.setSize(450, 300);
        buscarproveedor.setLocationRelativeTo(this);
        buscarproveedor.setVisible(true);
        habilitarbotones(true);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyReleased
        if(txtBuscarCliente.getText().isEmpty()){
        cargarclientes("");
            }
       else{cargarclinetesbuscar("");
            }
    }//GEN-LAST:event_txtBuscarClienteKeyReleased

    private void tablaclienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaclienteKeyReleased
        seleccionartablacliente();
    }//GEN-LAST:event_tablaclienteKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.buscarproveedor.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tablaclienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaclienteMouseClicked
          
        seleccionartablacliente();
    }//GEN-LAST:event_tablaclienteMouseClicked

    private void txtBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarClienteActionPerformed

    private void buscarProduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarProduActionPerformed
        buscarproductos.setModal(true);
        buscarproductos.setSize(500, 300);
        buscarproductos.setLocationRelativeTo(this);
        buscarproductos.setVisible(true);
        
        
        habilitarbotonestg(true);
    }//GEN-LAST:event_buscarProduActionPerformed

    private void txtBuscarProduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarProduActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarProduActionPerformed

    private void tablaProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductoKeyPressed
       
    }//GEN-LAST:event_tablaProductoKeyPressed

    private void tablaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductoMouseClicked
      seleccionartablaproducto();
    }//GEN-LAST:event_tablaProductoMouseClicked

    private void tablaProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductoKeyReleased
        seleccionartablaproducto();
    }//GEN-LAST:event_tablaProductoKeyReleased

    private void txtBuscarProduKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProduKeyReleased
        if(txtBuscarProdu.getText().isEmpty()){
        cargarproducto("");
            }
       else{
            cargarproductobuscar();
            }
    }//GEN-LAST:event_txtBuscarProduKeyReleased

    private void btnAceotarProduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceotarProduActionPerformed
       this.buscarproductos.dispose();
       txtCantidad.setText("1");
        int precioventa, cantidad, total;
        
        cantidad = Integer.parseInt(txtCantidad.getText().toString());
        precioventa = Integer.parseInt(txtPrecioCompra.getText().toString());
        total = cantidad*precioventa;
        txtTotal1.setText(String.valueOf(total));
       
    }//GEN-LAST:event_btnAceotarProduActionPerformed

    private void txtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusLost
       
    
    }//GEN-LAST:event_txtCantidadFocusLost

    private void AgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarProductoActionPerformed
       agregartablafactura();
       sumadetalbla();
       limpiar();
       habilitarbotones2(true);
       habilitarbotonestg(false);
    }//GEN-LAST:event_AgregarProductoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if (btnContado.isSelected()) {

               try {
                   
                   abm.start();
                   
                   int idempleado = abm.idUsuario;
                   
                   rs = abm.nuevo("idfacturacompra", "FacturaCompra");
                   rs.first();
                   txtidfac.setText(String.valueOf(rs.getInt("codigo")+1));
                    
                       int iva;
                       iva = Integer.parseInt(txtIVA.getText().toString());
                       if((iva==10)||(iva==5)||(iva==0)){
                            String pagado = "0", anulado = "0", estado = "0", condicion = "0";
                            int plazo=1, saldo=0, montocuota=0;
                             v_control = abm.insertar("FacturaCompra", txtidfac.getText()+","+txtNumFac.getText() +",'"
                                           + fecha.getText() + "','" + estado + "','" + anulado + "'," + txtIdproveedor.getText() + "," + txtTotalIva.getText()+
                                           ",'" + pagado + "','" +condicion+"',"+idempleado+","+ txtDies.getText() +"," + txtCinco.getText() + "," + txtExentas.getText()+","+ plazo + "," + saldo +","+ montocuota+"," + txtTotal.getText()+",'"+fecha.getText()+"'");
                       System.out.println("despues del print");
                       }           
                                  
                        if (v_control == false) {
                               abm.roolback();
                         }
  
                        
                          if (v_control == true) {

                             for (int i = 0; i < tablafactura.getRowCount(); i++) {
                                 Statement consulta = (Statement) conexionBD.ConectarBD().createStatement(); 
                                   consulta.execute("update productos set stock=stock+"+tablafactura.getValueAt(i, 2) +", preciodecompra="+tablafactura.getValueAt(i, 3)+
                                                    " where idproducto="+tablafactura.getValueAt(i, 0));  
                                   if((iva==10)||(iva==5)||(iva==0)){
                                                  v_control = abm.insertar("DetalleCompra","'"+ tablafactura.getValueAt(i, 0) + "','"
                                                   +  txtidfac.getText()  + "','" +tablafactura.getValueAt(i, 3) + "','"
                                                   +  tablafactura.getValueAt(i, 2)+ "','" + tablafactura.getValueAt(i, 6) + "','" + tablafactura.getValueAt(i, 5) + "','" + tablafactura.getValueAt(i, 4)+"'");   
                                   System.out.println("despues del print2 ");        
                                   }                                  
                                    }
                          if (v_control == false) {
                                           abm.roolback();
                                       }

                                   }
                          

                                   //confirmo la transacion
                                   if (v_control == true) {
                                       abm.comit();
                                   }
                                   
                                   abm.end();
                                   JOptionPane.showMessageDialog(null, "Los Datos se han guardado satisfactoriamente");
                               } catch (Exception ex) {
                                   System.out.println("Error al realizar la transacion " + ex.getMessage());
                                   JOptionPane.showMessageDialog(null, "Completar todos los campos para guardar");

                               }
              
               
               
               
               }
               habilitarbotones(false);
               habilitarbotonestg(false);
               habilitarbotones2(false);                  
               limpiarcamposcabezara();
               idfactura();                                
               rs = abm.nuevo("numero", "FacturaCompra");
               rs.first();      
               txtNumFac.setText(String.valueOf(rs.getInt("codigo") + 1));
        } catch (SQLException ex) {
            Logger.getLogger(FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarcamposcabezara();
        habilitarguardar();
        habilitarbotones2(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tablafactura.getModel();
        int i = tablafactura.getSelectedRow();

        //hacemos una condicion de que si la varialbe i es igual a - es que no se ha seleccionado ninguna fila
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Favor seleccione una fila");

        } else {
            txtTotalIva.setText("");
            txtIva5.setText("");
            txtIva10.setText("");
            txtExentas.setText("");
            txtIva5.setText("");
            txtDies.setText("");
            txtSaldo.setText("");
            txtTotalIva.setText("");
            txtCinco.setText("");
            txtTotal.setText("");
            
           
            
            
            dtm.removeRow(tablafactura.getSelectedRow());
            sumadetalbla();
            habilitarguardar();
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
       
        if(txtCantidad.getText().isEmpty()){
            txtCantidad.setText("1"); 
        }else{
            int precioventa, cantidad, total;
            cantidad = Integer.parseInt(txtCantidad.getText().toString());
            precioventa = Integer.parseInt(txtPrecioCompra.getText().toString());
            total = cantidad*precioventa;
            txtTotal1.setText(String.valueOf(total));
        }
        
    }//GEN-LAST:event_txtCantidadKeyReleased

private void txtNumFacKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFacKeyTyped
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtNumFacKeyTyped

private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtCantidadKeyTyped

private void btnCancelarCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCreditoActionPerformed
        txtPlazo.setText("0");
        txtMontoCuota.setText("0");
        txtEntrega.setText("0");
        this.credito.dispose();
        btnContado.setSelected(true);
}//GEN-LAST:event_btnCancelarCreditoActionPerformed

private void btnGuardarCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCreditoActionPerformed
       
            try {
            if (btnContado.isSelected()) {

               try {
                   
                   abm.start();
                   
                   int idempleado = abm.idUsuario;
                   
                   rs = abm.nuevo("idfacturaventa", "FacturaVenta");
                   rs.first();
                   txtidfac.setText(String.valueOf(rs.getInt("codigo") + 1));
                    
                   /* FechaVence = fecha1.getCalendar();
                    FechaVence.setTime(fecha1.getDate());
                    String dia =String.valueOf(FechaVence.get(Calendar.DATE));
                    String mes =String.valueOf(FechaVence.get(Calendar.MONTH)+1);
                    String anio =String.valueOf(FechaVence.get(Calendar.YEAR));
                    String date = anio+"/"+mes+"/"+dia;
                   
                   
                       int iva;
                       iva = Integer.parseInt(txtIVA.getText().toString());
                       if((iva==10)||(iva==5)||(iva==0)){
                            String pagado = "0", anulado = "0", estado = "0", condicion = "Credito";
                           
                             v_control = abm.insertar("FacturaVenta", txtidfac.getText()+","+txtNumFac.getText() +",'"
                                           + fecha.getText() + "','" + estado + "','" + anulado + "'," + txtIdproveedor.getText() + "," + txtTotalIva.getText()+
                                           ",'" + pagado + "','" +condicion+"',"+idempleado+","+ txtDies.getText() +"," + txtCinco.getText() + "," + txtExentas.getText()+","+ txtPlazo.getText() + "," + txtSaldo.getText() +","+ txtMontoCuota.getText()+"," + txtEntrega.getText()+",'"+anio+"/"+mes+"/"+dia+"'");
                       System.out.println("despues del print");
                       }       */    
                                  
                        if (v_control == false) {
                               abm.roolback();
                         }
  
                        
                          if (v_control == true) {

                             for (int i = 0; i < tablafactura.getRowCount(); i++) {
                                 Statement consulta = (Statement) conexionBD.ConectarBD().createStatement(); 
                                   consulta.execute("update productos set stock=stock-"+tablafactura.getValueAt(i, 2) + 
                                                    " where idproducto="+tablafactura.getValueAt(i, 0));  
                                   /*if((iva==10)||(iva==5)||(iva==0)){
                                                  v_control = abm.insertar("DetalleVenta","'"+ tablafactura.getValueAt(i, 0) + "','"
                                                   +  txtidfac.getText()  + "','" +tablafactura.getValueAt(i, 3) + "','"
                                                   +  tablafactura.getValueAt(i, 2)+ "','" + tablafactura.getValueAt(i, 6) + "','" + tablafactura.getValueAt(i, 5) + "','" + tablafactura.getValueAt(i, 4)+"'");   
                                   System.out.println("despues del print2 ");        
                                   }*/                                  
                                    }
                          if (v_control == false) {
                                           abm.roolback();
                                       }

                                   }
                          

                                   //confirmo la transacion
                                   if (v_control == true) {
                                       abm.comit();
                                   }
                                   
                                   abm.end();
                                   JOptionPane.showMessageDialog(null, "Los Datos se han guardado satisfactoriamente");
                               } catch (Exception ex) {
                                   System.out.println("Error al realizar la transacion " + ex.getMessage());
                                   JOptionPane.showMessageDialog(null, "Completar todos los campos para guardar");

                               }
              
               
               
               
               }
               habilitarbotones(false);
               habilitarbotonestg(false);
               habilitarbotones2(false);                  
               limpiarcamposcabezara();
               idfactura();                                
               rs = abm.nuevo("numerofactura", "FacturaVenta");
               rs.first();      
               txtNumFac.setText(String.valueOf(rs.getInt("codigo") + 1));
        } catch (SQLException ex) {
            Logger.getLogger(FacturaCompra.class.getName()).log(Level.SEVERE, null, ex);
        }   
    //v_control = abm.insertar("EntregaInicial", txtIdFacCredito.getText+","+ txtPlazo.getText() + "," + txtSaldo.getText() +","+ txtMontoCuota.getText()+"," + txtEntrega.getText() + ",'" + fecha1.getText()+"',"+ txtidfac.getText());                      
    //v_control = abm.insertar("EntregaInicial", txtIdFacCredito.getText+","+ txtPlazo.getText() + "," + txtSaldo.getText() +","+ txtMontoCuota.getText()+"," + txtEntrega.getText() + ",'" + fecha1.getText()+"',"+ txtidfac.getText());                   
                                      
}//GEN-LAST:event_btnGuardarCreditoActionPerformed

private void txtEntregaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntregaKeyTyped
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtEntregaKeyTyped

private void txtEntregaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEntregaFocusLost
        // TODO add your handling code here:
       double entrega, montoapagar, total;
            montoapagar =  Double.parseDouble(String.valueOf(txtMontoPagar.getText()));
            entrega = Double.parseDouble(String.valueOf(txtEntrega.getText()));
            total = montoapagar-entrega;
            txtSaldo.setText(String.valueOf(total));
            
            calcularplazo(); 
            
                  
}//GEN-LAST:event_txtEntregaFocusLost

private void txtEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEntregaActionPerformed
        
}//GEN-LAST:event_txtEntregaActionPerformed

private void txtPlazoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlazoKeyTyped
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtPlazoKeyTyped

private void txtPlazoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlazoKeyReleased
        double entrega, montoapagar, total;
            montoapagar =  Double.parseDouble(String.valueOf(txtMontoPagar.getText()));
            entrega = Double.parseDouble(String.valueOf(txtEntrega.getText()));
            total = montoapagar-entrega;
            txtSaldo.setText(String.valueOf(total));
            
            calcularplazo(); 
            
}//GEN-LAST:event_txtPlazoKeyReleased

private void txtPlazoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlazoFocusLost
              
}//GEN-LAST:event_txtPlazoFocusLost

private void txtPlazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlazoActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_txtPlazoActionPerformed

private void txtPrecioCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCompraKeyTyped
// TODO add your handling code here:
    char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtPrecioCompraKeyTyped

private void txtPrecioCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCompraKeyReleased
// TODO add your handling code here:
    int precioventa, cantidad, total;
            cantidad = Integer.parseInt(txtCantidad.getText().toString());
            precioventa = Integer.parseInt(txtPrecioCompra.getText().toString());
            total = cantidad*precioventa;
            txtTotal1.setText(String.valueOf(total));
}//GEN-LAST:event_txtPrecioCompraKeyReleased

private void txtPrecioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioVentaActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtPrecioVentaActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:
        cargarproductoDos();
        txtCantidad.requestFocus();
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        // TODO add your handling code here:
        cargarproductoDos();
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
          agregartablafactura();
        sumadetalbla();
        limpiar();
        habilitarbotones2(true);
        habilitarbotonestg(false);
    }//GEN-LAST:event_txtCantidadActionPerformed

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
            java.util.logging.Logger.getLogger(FacturaCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturaCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturaCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturaCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                FacturaCompra dialog = new FacturaCompra(new javax.swing.JFrame(), true);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarProducto;
    private javax.swing.ButtonGroup Group1;
    private javax.swing.JButton btnAceotarProdu;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarCredito;
    private javax.swing.JRadioButton btnContado;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarCredito;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton buscarProdu;
    private javax.swing.JDialog buscarproductos;
    private javax.swing.JDialog buscarproveedor;
    private javax.swing.JDialog credito;
    private javax.swing.JLabel fecha;
    private com.toedter.calendar.JDateChooser fecha1;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tablaProducto;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablafactura;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtBuscarProdu;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCin;
    private javax.swing.JTextField txtCinco;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDies;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEntrega;
    private javax.swing.JTextField txtExentas;
    private javax.swing.JLabel txtIVA;
    private javax.swing.JLabel txtIdFacCredito;
    private javax.swing.JTextField txtIdproveedor;
    private javax.swing.JTextField txtIva10;
    private javax.swing.JTextField txtIva5;
    private javax.swing.JTextField txtMontoCuota;
    private javax.swing.JTextField txtMontoPagar;
    private javax.swing.JTextField txtNumFac;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtPlazo;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtTelef;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotal1;
    private javax.swing.JTextField txtTotalIva;
    private javax.swing.JTextField txtidfac;
    private javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables
}
