
package productos;

import Metodos.abm;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import bebidas.conexionBD;
import java.util.logging.*;


public class AgreCategoria extends javax.swing.JDialog {
    
    
    conexionBD cn;
    private abm abm;
    private ResultSet rs;
    private boolean v_control;
    DefaultTableModel modelo;
    private Object[] filas;
    private static char opcion;
    private boolean vacio;

    
    public AgreCategoria() {
        initComponents();
        
        cn= new conexionBD();
        
        abm= new abm();
        rs=abm.consulta("*", "categoria");
        MostrarRegistro();  
        vercategoria();
        cargarcategoria("");
        
    }
    
    
    public void seleccionartabla(){
        
        
       DefaultTableModel tabla = (DefaultTableModel) this.tablaCategoria.getModel();
       
       
       int c= tablaCategoria.getSelectedRow();
       
       if(c==-1){
           System.out.println("Seleccione un registro");
            }
       else{
           String id = (String) tablaCategoria.getValueAt(c, 0);
           String categoria = (String) tablaCategoria.getValueAt(c, 1);
           this.txtIdCat.setText(id);
           this.txtDescriCat.setText(categoria);
           }       
    }
    
    public void cargarcategoriabuscar(String valor ){
        String [] titulos  = {"Codigo","Categoria"};
        String [] registros  = new String [2];
        String sql = "select idcategoria, descripcion from categoria where CONCAT(idcategoria,' ',descripcion) LIKE  '%"+txtBuscar.getText()+"%'";  
         
        
         
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cnp = new conexionBD();
        Connection cnn = (Connection) cnp.ConectarBD();
        Statement  st;
        try {
            
            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);
            
            while(rst.next()){
               registros[0] = rst.getString("idcategoria");
               registros[1] = rst.getString("descripcion");
                
                modelo.addRow(registros);
                }
            tablaCategoria.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    
     public void cargarcategoria(String valor ){
        String [] titulos  = {"Codigo","Categoria"};
        String [] registros  = new String [2];
        
        String sql = "select idcategoria, descripcion from categoria where CONCAT(idcategoria,' ',descripcion) LIKE '%"+valor+"%'";  
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cn = new conexionBD();
        Connection cnn = (Connection) cn.ConectarBD();
        Statement  st;
        try {
            
            st = (Statement) cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                registros[0] = rs.getString("idcategoria");
                registros[1] = rs.getString("descripcion");
                modelo.addRow(registros);
            }
            tablaCategoria.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null,"error wyryry gurara"+ ex.getMessage());
                            } 
    }
    
    
    
    
    public void habilitarcampos(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
                txtDescriCat.setEnabled(h);
    }
    public void habilitarbotones(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
                btnEditar.setEnabled(h);
                btnEliminar.setEnabled(h);
                btnAgregar.setEnabled(h);
        }
    
    public  boolean validardatos(){
   vacio=false;
   if(txtDescriCat.getText().isEmpty()){
       vacio=true;
        }
   return vacio;
}
    
    public void habilitarbotones2(boolean j){ //metodo encargado de habilitar o deshabilitar botonoes
                btnCancelar.setEnabled(j);
                btnGuardar.setEnabled(j);
        }
    
     public void MostrarRegistro(){//metodo creado poara mostrar datos
    try{
        if(rs.getRow()!=0){ //devuelve numero de filas de una objeto de tipo resultset
            txtIdCat.setText(rs.getString(1));
            txtDescriCat.setText(rs.getString(2));
            }
        
        }catch(Exception e){
        System.out.println("error al mostrar resultados"+e.getMessage());
        }
  }
        public void limpiar(){
            txtDescriCat.setText("");
        }
        
        void vercategoria(){
        try{
            Statement consultacategoria= (Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultacategoria.executeQuery("select idcategoria, descripcion from categoria");
            
            modelo = new DefaultTableModel();
            tablaCategoria.setModel(modelo);
            
            
            modelo.addColumn("Codigo");
            modelo.addColumn("Categoria");
            
            filas = new Object[modelo.getColumnCount()];
            
            while(rs.next()){
                for(int i=0;i<modelo.getColumnCount();i++){
                    filas[i]=rs.getObject(i+1);
                }                
                modelo.addRow(filas);
            }           
            tablaCategoria.setModel(modelo);   
        }catch(Exception e){
            System.out.println("Error al mostrar datos en la tabla"+e.getMessage());
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
        txtIdCat = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtDescriCat = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnSalir = new javax.swing.JToggleButton();
        btnCancelar = new javax.swing.JToggleButton();
        btnAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCategoria = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel3.setFont(new java.awt.Font("Bell MT", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 0));
        jLabel3.setText("Agregar Categoría");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(223, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(27, 27, 27))
        );

        jSplitPane1.setTopComponent(jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtIdCat.setEnabled(false);
        jPanel2.add(txtIdCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 40, -1));

        jLabel1.setText("Categoría:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, 18));

        txtDescriCat.setEnabled(false);
        jPanel2.add(txtDescriCat, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 110, 30));

        jLabel2.setText("Id Categoría: ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 18));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, 110, -1));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clear.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 310, 100, 41));

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 110, 40));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/articulos.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        tablaCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Categoria"
            }
        ));
        tablaCategoria.getTableHeader().setReorderingAllowed(false);
        tablaCategoria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCategoriaMouseClicked(evt);
            }
        });
        tablaCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaCategoriaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCategoria);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 350, 240));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/actualizar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, 120, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/basureronegro.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 120, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 120, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buscar.png"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, -1, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 0));
        jLabel5.setText("Introduzca Categoria o Codigo");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(357, 40, 150, -1));

        jSplitPane1.setBottomComponent(jPanel2);

        getContentPane().add(jSplitPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                            consulta=("Select * from categoria where descripcion='"+txtDescriCat.getText()+"'"); //se traen todos los registros
                            st = (Statement) cnn.createStatement();
                            ResultSet rs = st.executeQuery(consulta);
                            rs.first();
                            if (rs.getRow()!=0){ // si es distinto a 0 ya existe y no agrega
                                            JOptionPane.showMessageDialog(null,"Esta categoria ya existe");
                                            txtDescriCat.setText("");
                                            txtDescriCat.requestFocus();
                                            }
                            else{
                                v_control=abm.insertar("categoria",txtIdCat.getText()+",'"+txtDescriCat.getText()+"'");
                                if (v_control==true){
                                                    JOptionPane.showMessageDialog(null,"Se ha guardado los datos");              
                                                    }
                                    }
                            }catch(Exception e){
                                    System.out.println("Error al mostrar datos en la tabla"+e.getMessage());
                                    }    
                            break;
                case 'm':
                        v_control= abm.modificar("categoria", "descripcion='"+txtDescriCat.getText()+"'", "idcategoria="+txtIdCat.getText());
                        if(v_control==true){
                          JOptionPane.showMessageDialog(null,"Datos actualizados congratuleishon");
                            }
                        break;
        }
      habilitarcampos(false);
      habilitarbotones(true);
      habilitarbotones2(false);
      /*rs=abm.consulta("*", "categoria");
      MostrarRegistro();*/
      vercategoria();
      cargarcategoria("");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
       try {
                opcion='n';
                limpiar();
                habilitarbotones(false);
                habilitarcampos(true);
                habilitarbotones2(true);
                rs = abm.nuevo("idcategoria", "categoria");
                rs.first();
                txtIdCat.setText(String.valueOf(rs.getInt("codigo")+1));
                txtDescriCat.requestFocus();//mantiene el enfoque en un objeto
                }catch(Exception e){
                JOptionPane.showMessageDialog(null,"error al generar el codigo "+e);          
                }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        habilitarbotones2(false);
        habilitarbotones(true);
        habilitarcampos(false);
        rs=abm.consulta("*", "categoria");
        MostrarRegistro();
        vercategoria();
        cargarcategoria("");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        opcion='m';
        habilitarcampos(true);
        habilitarbotones(false);
        habilitarbotones2(true);
        txtDescriCat.requestFocus();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        v_control = abm.eliminar("categoria", "idcategoria="+txtIdCat.getText());
        if (v_control==true) {
            rs = abm.consulta("*", "categoria");
            MostrarRegistro();
            JOptionPane.showMessageDialog(null,"Datos Eliminados congratuleishon");
            }
        rs=abm.consulta("*", "categoria");
        MostrarRegistro();
        vercategoria();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tablaCategoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaCategoriaKeyPressed
       /* Integer id = (Integer) tablaCategoria.getValueAt(tablaCategoria.getSelectedRow(), 0);
        Integer categoria = (Integer) tablaCategoria.getValueAt(tablaCategoria.getSelectedRow(), 1);
        
        txtIdCat.setText(Integer.toString(id));
        txtDescriCat.setText(Integer.toString(categoria));*/
        seleccionartabla();
    }//GEN-LAST:event_tablaCategoriaKeyPressed

    private void tablaCategoriaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCategoriaMouseClicked
        seleccionartabla();
    }//GEN-LAST:event_tablaCategoriaMouseClicked

private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
// TODO add your handling code here:
    if(txtBuscar.getText().isEmpty()){
        cargarcategoria("");
            }
       else{cargarcategoriabuscar("");
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
            java.util.logging.Logger.getLogger(AgreCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgreCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgreCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgreCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AgreCategoria().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tablaCategoria;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDescriCat;
    private javax.swing.JTextField txtIdCat;
    // End of variables declaration//GEN-END:variables
}
