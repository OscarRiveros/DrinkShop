/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AgreProcedencia.java
 *
 * Created on 30-oct-2012, 22:46:37
 */
package productos;

import Metodos.abm;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import bebidas.conexionBD;
import java.util.logging.*;
import bebidas.Principal;




/**
 *
 * @author Ivan
 */
public class AgregarProveedor extends javax.swing.JDialog {
    
    
    conexionBD cn;
    private abm abm;
    private ResultSet rs;
    private boolean v_control;
    DefaultTableModel modelo;
    private Object[] filas;
    private static char opcion;
    private boolean vacio;

    /** Creates new form AgreProveedor */
    public AgregarProveedor() {
        initComponents();
        
        cn= new conexionBD();
        
        abm= new abm();
        rs=abm.consulta("*", "proveedor");
        MostrarRegistro();
        verproveedor();
        cargarproveedor("");
    }

   
    
    void verproveedor(){
        try{
            Statement consultamarca = (Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultamarca.executeQuery("select * from proveedor");
            
            modelo = new DefaultTableModel();
            tablaProveedor.setModel(modelo);
            
            
            modelo.addColumn("Codigo");
            modelo.addColumn("Nombre");
            modelo.addColumn("Telefono");
            modelo.addColumn("Direccion");
            modelo.addColumn("RUC");
            
            filas = new Object[modelo.getColumnCount()];
            
            while(rs.next()){
                for(int i=0;i<modelo.getColumnCount();i++){
                    filas[i]=rs.getObject(i+1);
                }
                
                modelo.addRow(filas);
                
            }
            
            tablaProveedor.setModel(modelo);
    
        }catch(Exception e){
            System.out.println("Error al mostrar datos en la tabla"+e.getMessage());
        }
    }
    
    
    public void cargarproveedor(String valor ){
        String [] titulos  = {"Codigo","Nombre","Telefono","Direccion","RUC"};
        String [] registros  = new String [5];
        
        String sql = "select idproveedor, nombre, telefono, direccion, RUC  from proveedor where CONCAT(idproveedor,' ',nombre,' ',telefono,' ',direccion,' ',RUC) LIKE '%"+valor+"%'";  
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cn = new conexionBD();
        Connection cnn = (Connection) cn.ConectarBD();
        Statement  st;
        try {
            
            st = (Statement) cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                registros[0] = rs.getString("idproveedor");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("telefono");
                registros[3] = rs.getString("direccion");
                registros[4] = rs.getString("RUC");
                modelo.addRow(registros);
                }
            tablaProveedor.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    public void cargarprovebuscar(){
         String [] titulos  = {"Codigo","Nombre","Telefono","Direccion","RUC"};
        String [] registros  = new String [5];
        
        String sql = "select idproveedor, nombre, telefono, direccion, RUC  from proveedor where CONCAT(idproveedor,' ',nombre,' ',telefono,' ',direccion,' ',RUC) LIKE  '%"+txtBuscar.getText()+"%'";  
         
        
         
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cnp = new conexionBD();
        Connection cnn = (Connection) cnp.ConectarBD();
        Statement  st;
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
            tablaProveedor.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    public void seleccionartabla(){
        
        
       DefaultTableModel tabla = (DefaultTableModel) this.tablaProveedor.getModel();
       
       
       int c= tablaProveedor.getSelectedRow();
       
       if(c==-1){
           System.out.println("Seleccione un registro");
            }
       else{
           String id = (String) tablaProveedor.getValueAt(c, 0);
           String proveedor = (String) tablaProveedor.getValueAt(c, 1);
           String telefono = (String) tablaProveedor.getValueAt(c, 2);
           String direccion = (String) tablaProveedor.getValueAt(c, 3);
           String RUC = (String) tablaProveedor.getValueAt(c, 4);
           
           this.txtIdProve.setText(id);
           this.txtNombre.setText(proveedor);
           this.txtTelef.setText(telefono);
           this.txtDirec.setText(direccion);
           this.txtRUC.setText(RUC);
           }       
    }
    
    
    
     public void habilitarcampos(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
                txtNombre.setEnabled(h);      
                txtNombre.setEnabled(h);
                txtTelef.setEnabled(h);
                txtDirec.setEnabled(h);
                txtRUC.setEnabled(h);
    }
    public void habilitarbotones(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
                 btnEditar.setEnabled(h);
                 btnEliminar.setEnabled(h);
                 btnAgregar.setEnabled(h);
        }
    
    public void habilitarbotones2(boolean j){ //metodo encargado de habilitar o deshabilitar botonoes
                btnCancelar.setEnabled(j);
                btnGuardar.setEnabled(j);
        }
     public void MostrarRegistro(){//metodo creado poara mostrar datos
    try{
        if(rs.getRow()!=0){ //devuelve numero de filas de una objeto de tipo resultset
            txtIdProve.setText(rs.getString(1));
            txtNombre.setText(rs.getString(2));
            txtTelef.setText(rs.getString(3));
            txtDirec.setText(rs.getString(4));
            txtRUC.setText(rs.getString(5));
            
            }
        
        }catch(Exception e){
        System.out.println("error al mostrar resultados"+e.getMessage());
        }
  }
        public void limpiar(){
            txtIdProve.setText("");
            txtNombre.setText("");
            txtTelef.setText("");
            txtDirec.setText("");
            txtRUC.setText("");
            
        }
        public  boolean validardatos(){
            vacio=false;
            if(txtNombre.getText().isEmpty()){
                vacio=true;
                 }
                return vacio;
            }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtIdProve = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JToggleButton();
        btnAgregar = new javax.swing.JToggleButton();
        btnCancelar = new javax.swing.JToggleButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProveedor = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtTelef = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDirec = new javax.swing.JTextField();
        txtRUC = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel3.setFont(new java.awt.Font("Bell MT", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 0));
        jLabel3.setText("Agregar Proveedor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(372, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(254, 254, 254))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(28, 28, 28))
        );

        jSplitPane1.setTopComponent(jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtIdProve.setEnabled(false);
        jPanel2.add(txtIdProve, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 40, 30));

        jLabel1.setText("Nombre:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 18));

        txtNombre.setEnabled(false);
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 110, 30));

        jLabel2.setText("Codigo Proveedor: ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 20));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 310, 110, -1));

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 110, 41));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clear.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, 100, 41));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/articulos.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        tablaProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Telefono", "Direccion", "RUC"
            }
        ));
        tablaProveedor.getTableHeader().setReorderingAllowed(false);
        tablaProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProveedorMouseClicked(evt);
            }
        });
        tablaProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaProveedorKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProveedor);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 460, 320));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/actualizar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 40, 120, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/basureronegro.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 160, 120, -1));

        jLabel4.setText("Telefono:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        txtTelef.setEnabled(false);
        txtTelef.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefKeyTyped(evt);
            }
        });
        jPanel2.add(txtTelef, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 110, -1));

        jLabel5.setText("Direccion:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

        txtDirec.setEnabled(false);
        jPanel2.add(txtDirec, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 110, -1));

        txtRUC.setEnabled(false);
        jPanel2.add(txtRUC, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 110, -1));

        jLabel6.setText("RUC:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 130, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buscar.png"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 204, 0));
        jLabel8.setText("Introduzca RUC o Nombre del Proveedor");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 210, 20));

        jSplitPane1.setBottomComponent(jPanel2);

        getContentPane().add(jSplitPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 477));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
                opcion='n';
                limpiar();
                habilitarbotones(false);
                habilitarcampos(true);
                habilitarbotones2(true);
                rs = abm.nuevo("idproveedor", "proveedor");
                rs.first();
                txtIdProve.setText(String.valueOf(rs.getInt("codigo")+1));
                txtNombre.requestFocus();//mantiene el enfoque en un objeto
                }catch(Exception e){
                JOptionPane.showMessageDialog(null,"error al generar el codigo "+e);          
                }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        vacio=validardatos();
        if(vacio==true){
            JOptionPane.showMessageDialog(null,"Completar los datos marcados en aterisco");
        }else{
            switch(opcion){
                case 'n':
                        conexionBD cn = new conexionBD();// se crea la conexion 
                        Connection cnn = (Connection) cn.ConectarBD();
                        Statement  st; 
                        try{
                            String consulta="";
                            consulta=("Select * from proveedor where nombre='"+txtNombre.getText()+"'"); //se traen todos los registros
                            st = (Statement) cnn.createStatement();
                            ResultSet rs = st.executeQuery(consulta);
                            rs.first();
                            if (rs.getRow() != 0){ // si es distinto a 0 ya existe y no agrega
                                            JOptionPane.showMessageDialog(null,"Este proveedor ya existe");                                           
                                            txtNombre.setText("");
                                            txtNombre.requestFocus();
                                            }
                            else{
                                    v_control=abm.insertar("proveedor",txtIdProve.getText()+",'"+txtNombre.getText()+"','"+txtTelef.getText()+"','"+txtDirec.getText()+"','"+txtRUC.getText()+"'");
                                    if (v_control==true){
                                                JOptionPane.showMessageDialog(null,"Se ha guardado los datos");              
                                                }
                                }
                        }catch(Exception e){
                                    System.out.println("Error al mostrar datos en la tabla"+e.getMessage());  
                                    }           
                break;
                case 'm':
                        v_control= abm.modificar("proveedor", "nombre='"+txtNombre.getText()+"',"+"telefono='"+txtTelef.getText()+"',"+"direccion='"+txtDirec.getText()+"',"+"RUC='"+txtRUC.getText()+"'", "idproveedor="+txtIdProve.getText());
                        if(v_control==true){
                          JOptionPane.showMessageDialog(null,"Datos actualizados congratuleishon");
                            }
                        break;
               }
        habilitarcampos(false);
        habilitarbotones(true);
        habilitarbotones2(false);
        rs=abm.consulta("*", "proveedor");
        MostrarRegistro();
        verproveedor();
        cargarproveedor("");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        habilitarbotones2(false);
        habilitarbotones(true);
        habilitarcampos(false);
        rs=abm.consulta("*", "proveedor");
        MostrarRegistro();
        verproveedor();
        cargarproveedor("");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
      this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
       opcion='m';
        habilitarcampos(true);
        habilitarbotones(false);
        habilitarbotones2(true);
        txtNombre.requestFocus();                                    
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        v_control = abm.eliminar("proveedor", "idproveedor="+txtIdProve.getText());
        if(v_control==true){
            //rs= abm.consulta("*", "procedencia");
            //MostrarRegistro();
            JOptionPane.showMessageDialog(null,"Datos Eliminados congratuleishon");
            }
        rs=abm.consulta("*", "proveedor");
        MostrarRegistro();
        verproveedor();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tablaProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProveedorKeyPressed
        seleccionartabla();
    }//GEN-LAST:event_tablaProveedorKeyPressed

    private void tablaProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProveedorMouseClicked
        seleccionartabla();
    }//GEN-LAST:event_tablaProveedorMouseClicked

private void txtTelefKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefKeyTyped
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtTelefKeyTyped

private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
// TODO add your handling code here:
    if(txtBuscar.getText().isEmpty()){
        cargarproveedor("");
            }
       else{cargarprovebuscar();
            }
}//GEN-LAST:event_txtBuscarKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgregarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AgregarProveedor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAgregar;
    private javax.swing.JToggleButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JToggleButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tablaProveedor;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDirec;
    private javax.swing.JTextField txtIdProve;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtRUC;
    private javax.swing.JTextField txtTelef;
    // End of variables declaration//GEN-END:variables
}
