/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AgreMarca.java
 *
 * Created on 30-oct-2012, 22:45:59
 */
package productos;

import Metodos.abm;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import bebidas.conexionBD;




public class AgreMarca extends javax.swing.JDialog{
    
    conexionBD cn;
    private abm abm;
    private ResultSet rs;
    private boolean v_control;//si guardo bien o mal
    DefaultTableModel modelo;
    private Object[] filas;
    private static char opcion;
    private boolean vacio;

    /** Creates new form AgreMarca */
    public AgreMarca() {
        initComponents();
        
        cn= new conexionBD();
        
        abm= new abm();
        rs=abm.consulta("*", "marca");
        MostrarRegistro();
        vermarca();
        cargarmarca("");
        
    }
    
    
    public void seleccionartabla(){
        
        
       DefaultTableModel tabla = (DefaultTableModel) this.tablaMarca.getModel();
       
       
       int c= tablaMarca.getSelectedRow();
       
       if(c==-1){
           System.out.println("Seleccione un registro");
            }
       else{
           String id = (String) tablaMarca.getValueAt(c, 0);       
           String marca = (String) tablaMarca.getValueAt(c, 1);
           this.txtIdMarca.setText(id);
           this.txtDescriMarca.setText(marca);
           
            
           }       
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
     public void habilitarcampos(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
     txtDescriMarca.setEnabled(h);
}
    
    public void MostrarRegistro(){//metodo creado poara mostrar datos
    try{
        if(rs.getRow()!=0){ //devuelve numero de filas de una objeto de tipo resultset
            txtIdMarca.setText(rs.getString(1));
            txtDescriMarca.setText(rs.getString(2));
        }
        
    }catch(Exception e){
        System.out.println("error al mostrar resultados"+e.getMessage());
    }
}    
    void vermarca(){
        try{
            Statement consultamarca = (Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultamarca.executeQuery("select idmarca, descripcion from marca");
            
            modelo = new DefaultTableModel();
            tablaMarca.setModel(modelo);
            
            
            modelo.addColumn("Codigo");
            modelo.addColumn("Marca");
            
            filas = new Object[modelo.getColumnCount()];
            
            while(rs.next()){
                for(int i=0;i<modelo.getColumnCount();i++){
                    filas[i]=rs.getObject(i+1);
                }                
                modelo.addRow(filas);
            }           
            tablaMarca.setModel(modelo);   
        }catch(Exception e){
            System.out.println("Error al mostrar datos en la tabla"+e.getMessage());
        }
    }
    public void limpiar(){
    txtIdMarca.setText("");
    txtDescriMarca.setText("");
    }
    
    
    public  boolean validardatos(){
   vacio=false;
   if(txtDescriMarca.getText().isEmpty()){
       vacio=true;
        }
   return vacio;
}
    
    
    public void cargarmarca(String valor ){
        String [] titulos  = {"Codigo","Marca"};
        String [] registros  = new String [2];
        
        String sql = "select idmarca, descripcion from marca where CONCAT(idmarca,' ',descripcion) LIKE '%"+valor+"%'";  
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cn = new conexionBD();
        Connection cnn = (Connection) cn.ConectarBD();
        Statement  st;
        try {
            
            st = (Statement) cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                registros[0] = rs.getString("idmarca");
                registros[1] = rs.getString("descripcion");
                modelo.addRow(registros);
                }
            tablaMarca.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    
    public void cargarmarcabuscar(){
        String [] titulos  = {"Codigo","Marca"};
        String [] registros  = new String [2];
        String sql = "select idmarca, descripcion from marca where CONCAT(idmarca,' ',descripcion) LIKE  '%"+txtBuscar.getText()+"%'";  
         
        
         
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cnp = new conexionBD();
        Connection cnn = (Connection) cnp.ConectarBD();
        Statement  st;
        try {
            
            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);
            
            while(rst.next()){
                registros[0] = rst.getString("idmarca");
                registros[1] = rst.getString("descripcion");
                
                modelo.addRow(registros);
                }
            tablaMarca.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    
      public void cargarmarcabuscardos(){
        String sql = "select descripcion from marca where idmarca LIKE '"+txtIdMarca.getText()+"'";  
         

        conexionBD cnp = new conexionBD();
        Connection cnn = (Connection) cnp.ConectarBD();
        Statement  st;
        try {
            
            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);
           
                while(rst.next()){
               // registros[0] = rst.getString("idmarca");
               // this.txtDescriMarca.setText(rst.getString("descripcion")); 
                 
                     
                this.txtDescriMarca.setText(rst.getString("descripcion")); 
                
               // modelo.addRow(registros);
                
            }
            
           
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
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
        txtIdMarca = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtDescriMarca = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JToggleButton();
        btnAgregar = new javax.swing.JToggleButton();
        btnCancelar = new javax.swing.JToggleButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMarca = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel3.setFont(new java.awt.Font("Bell MT", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 0));
        jLabel3.setText("Agregar Marca");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel3)
                .addContainerGap(201, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jSplitPane1.setTopComponent(jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtIdMarca.setEnabled(false);
        txtIdMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdMarcaActionPerformed(evt);
            }
        });
        txtIdMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdMarcaKeyReleased(evt);
            }
        });
        jPanel2.add(txtIdMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, 50, 30));

        jLabel1.setText("Marca:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, 18));

        txtDescriMarca.setEnabled(false);
        jPanel2.add(txtDescriMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 120, 30));

        jLabel2.setText("Id Marca: ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 18));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 270, 100, 50));

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 110, 50));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clear.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 110, 50));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/articulos.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, 50));

        tablaMarca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Marca"
            }
        ));
        tablaMarca.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablaMarca.getTableHeader().setReorderingAllowed(false);
        tablaMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMarcaMouseClicked(evt);
            }
        });
        tablaMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaMarcaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaMarca);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 330, 200));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/actualizar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, 120, -1));

        jLabel4.setText("*");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 20, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/basureronegro.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 120, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 120, 30));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buscar.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 204, 0));
        jLabel6.setText("Introduzca Marca o Codigo");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 150, -1));

        jSplitPane1.setBottomComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
       this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:   
        habilitarbotones2(false);
        habilitarbotones(true);
        habilitarcampos(false);
        rs=abm.consulta("*", "marca");
        MostrarRegistro();
        vermarca();
        cargarmarca("");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
            try {
                opcion='n';
                limpiar();
                habilitarbotones(false);
                habilitarbotones2(true);
                habilitarcampos(true);
                rs = abm.nuevo("idmarca", "marca");
                rs.first();
                txtIdMarca.setText(String.valueOf(rs.getInt("codigo")+1));
                txtDescriMarca.requestFocus();//mantiene el enfoque en un objeto
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
                      try {
                          String consulta="";
                          consulta=("Select * from marca where descripcion='"+txtDescriMarca.getText()+"'");
                          st= (Statement) cnn.createStatement();
                          ResultSet rs= st.executeQuery(consulta);
                          rs.first();
                          if(rs.getRow()!= 0){
                                JOptionPane.showMessageDialog(null, "Esta marca ya existe");
                                txtDescriMarca.setText("");
                                txtDescriMarca.requestFocus();
                          }else{
                                v_control=abm.insertar("marca",txtIdMarca.getText()+",'"+txtDescriMarca.getText()+"'");
                                if (v_control==true){
                                JOptionPane.showMessageDialog(null,"Se ha guardado los datos");              
                                 }
                          }
                              
                          
                      }catch(Exception e){
                        System.out.println("Error al mostrar datos en la tabla"+e.getMessage());   
                      }
                      
                    
                    
            break;
            case 'm':
                        v_control= abm.modificar("marca", "descripcion='"+txtDescriMarca.getText()+"'", "idmarca="+txtIdMarca.getText());
                        if(v_control==true){
                          JOptionPane.showMessageDialog(null,"Datos actualizados congratuleishon");
                            }
                        break;
        }
      habilitarcampos(false);
      habilitarbotones(true);
      habilitarbotones2(false);
     /* rs=abm.consulta("*", "marca");
      MostrarRegistro();*/
      vermarca();
      cargarmarca("");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tablaMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMarcaMouseClicked
       seleccionartabla();
    }//GEN-LAST:event_tablaMarcaMouseClicked

    private void tablaMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaMarcaKeyPressed
      seleccionartabla();
    }//GEN-LAST:event_tablaMarcaKeyPressed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        opcion='m';
        habilitarcampos(true);
        habilitarbotones(false);
        habilitarbotones2(true);
        txtDescriMarca.requestFocus();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
       v_control = abm.eliminar("marca", "idmarca="+txtIdMarca.getText());
        if(v_control==true){
            rs= abm.consulta("*", "marca");
            MostrarRegistro();
            JOptionPane.showMessageDialog(null,"Datos Eliminados congratuleishon");
            }
        /*rs=abm.consulta("*", "marca");
        MostrarRegistro();*/
        vermarca();
        cargarmarca("");
    }//GEN-LAST:event_btnEliminarActionPerformed

private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
// TODO add your handling code here:
    if(txtBuscar.getText().isEmpty()){
        cargarmarca("");
            }
       else{cargarmarcabuscar();
            }
}//GEN-LAST:event_txtBuscarKeyReleased

    private void txtIdMarcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdMarcaKeyReleased
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtIdMarcaKeyReleased

    private void txtIdMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdMarcaActionPerformed
        // TODO add your handling code here:
         cargarmarcabuscardos();
    }//GEN-LAST:event_txtIdMarcaActionPerformed

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
            java.util.logging.Logger.getLogger(AgreMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgreMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgreMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgreMarca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                
                    new AgreMarca().setVisible(true);
               
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tablaMarca;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDescriMarca;
    private javax.swing.JTextField txtIdMarca;
    // End of variables declaration//GEN-END:variables
}
