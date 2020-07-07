
package bebidas;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Metodos.abm;
import com.mysql.jdbc.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ivan
 */
public class RegistrarCliente extends javax.swing.JDialog {
        conexionBD cn;
        private abm abm;
        private boolean vacio;
        private static char opcion;
        private ResultSet rs; // devolver una variable cpon los resultados de la BD
        private boolean v_control;//si guardo bien o mal
        DefaultTableModel modelo;
        private Object[] filas;
    
    public RegistrarCliente() {
        
        initComponents(); 
        
        cn= new conexionBD();
                
        abm= new abm();
        rs=abm.consulta("*", "cliente");
        MostrarRegistro();
        cargarcliente("");
        //this.setTitle("genero :Usuario" +abm.nomUsuario +"nivel: "+ abm.nivel);      
    }

   
void verclientes(){
        try{
            com.mysql.jdbc.Statement consultamarca = (com.mysql.jdbc.Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultamarca.executeQuery("select * from cliente");
            
            modelo = new DefaultTableModel();
            tablaCliente.setModel(modelo);
            
            
            modelo.addColumn("Codigo");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Edad");
            modelo.addColumn("C.I.N");
            modelo.addColumn("Telefono");
            modelo.addColumn("Direccion");
            modelo.addColumn("Email");           
            
            
            filas = new Object[modelo.getColumnCount()];
            
            while(rs.next()){
                for(int i=0;i<modelo.getColumnCount();i++){
                    filas[i]=rs.getObject(i+1);
                }
                
                modelo.addRow(filas);
                
            }
            
            tablaCliente.setModel(modelo);
    
        }catch(Exception e){
            System.out.println("Error al mostrar datos en la tabla"+e.getMessage());
        }
    }
    public void cargarcliente(String valor ){
        String [] titulos  = {"Codigo","Nombre","Apellido","Edad","CIN","Telefono","Direccionn","E-MAIL"};
        String [] registros  = new String [8];
        String sql = "select idcliente, nombre, apellido, edad, cinro, telefono, direccion, email from cliente where CONCAT(idcliente,' ',nombre,' ',apellido,' ',edad,' ',cinro,' ',telefono,' ',direccion,' ',email) LIKE '%"+valor+"%'";  
         
        // sql+=" where idproducto="+txtIdProducto.getText();
         
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cn = new conexionBD();
        Connection cnn = (Connection) cn.ConectarBD();
        com.mysql.jdbc.Statement  st;
        try {
            
            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                registros[0] = rs.getString("idcliente");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("apellido");
                registros[3] = rs.getString("edad");
                registros[4] = rs.getString("cinro");
                registros[5] = rs.getString("telefono");
                registros[6] = rs.getString("direccion");
                registros[7] = rs.getString("email");
                modelo.addRow(registros);
                }
            tablaCliente.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
    
    
    public void seleccionartabla(){
        
        
      // DefaultTableModel tabla = (DefaultTableModel) this.tablaCliente.getModel();
       
       
       int c= tablaCliente.getSelectedRow();
       
       if(c==-1){
           System.out.println("Seleccione un registro");
            }
       else{
           String id = (String) tablaCliente.getValueAt(c, 0);
           String nombre = (String) tablaCliente.getValueAt(c, 1);
           String apellido= (String) tablaCliente.getValueAt(c, 2);
           String edad= (String) tablaCliente.getValueAt(c, 3);
           String cin= (String) tablaCliente.getValueAt(c, 4);
           String telefono= (String) tablaCliente.getValueAt(c, 5);
           String direccion= (String) tablaCliente.getValueAt(c, 6);
           String email= (String) tablaCliente.getValueAt(c, 7);
           
           this.txtId.setText(id);
           this.txtNombre.setText(nombre);
           this.txtApellido.setText(apellido);
           this.txtEdad.setText(edad);
           this.txtCin.setText(cin);
           this.txtTelf.setText(telefono);
           this.txtDirec.setText(direccion);
           this.txtEmail.setText(email);
           
           
           }       
    }
    public void cargarclientesbuscar(String valor ){
        String [] titulos  = {"Codigo","Nombre","Apellido","Edad","CIN","Telefono","Direccion","E-MAIL"};
        String [] registros  = new String [8];
        String sql = "select idcliente, nombre, apellido, edad, cinro, telefono, direccion, email from cliente where CONCAT(nombre,' ',cinro) LIKE  '%"+txtBuscar.getText()+"%'";  
         
        
         
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cnp = new conexionBD();
        Connection cnn = (Connection) cnp.ConectarBD();
        com.mysql.jdbc.Statement  st;
        try {
            
            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);
            
            while(rst.next()){
                registros[0] = rst.getString("idcliente");
                registros[1] = rst.getString("nombre");
                registros[2] = rst.getString("apellido");
                registros[3] = rst.getString("edad");
                registros[4] = rst.getString("cinro");
                registros[5] = rst.getString("telefono");
                registros[6] = rst.getString("direccion");
                registros[7] = rst.getString("email");
                
                modelo.addRow(registros);
                }
            tablaCliente.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
   
    public void habilitarbotones(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
    btnLimpiar.setEnabled(h);
    btnGuardar.setEnabled(h);
    btnCancelar.setEnabled(h);
        }
    public void habilitarbotonedi(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
    btnEditar.setEnabled(h);
    btnNuevo.setEnabled(h);
    btnEliminar1.setEnabled(h);
   
        }
    public void habilitarcampos(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
     txtNombre.setEnabled(h);
     txtApellido.setEnabled(h);
     txtCin.setEnabled(h);
     txtEdad.setEnabled(h);
     txtTelf.setEnabled(h);
     txtEmail.setEnabled(h);
     txtDirec.setEnabled(h);
}
public void limpiar(){
    txtId.setText("");
    txtNombre.setText("");
    txtApellido.setText("");
    txtCin.setText("");
    txtEdad.setText("");
    txtTelf.setText("");
    txtEmail.setText("");
    txtDirec.setText("");
    
} 
public  boolean validardatos(){
   vacio=false;
   if(txtNombre.getText().isEmpty()){
       vacio=true;
        }
   /*if(txtApellido.getText().isEmpty()){
       vacio=true;
        }*/
   if(txtCin.getText().isEmpty()){
       vacio=true;
        }
   if(txtTelf.getText().isEmpty()){
       vacio=true;
        }   
    
   return vacio;
}
public void MostrarRegistro(){//metodo creado poara mostrar datos
    try{
        if(rs.getRow()!=0){ //devuelve numero de filas de una objeto de tipo resultset
            txtId.setText(rs.getString(1));
            txtNombre.setText(rs.getString(2));
            txtApellido.setText(rs.getString(3));
            txtEdad.setText(rs.getString(4));
            txtCin.setText(rs.getString(5));  
            txtTelf.setText(rs.getString(6));
            txtDirec.setText(rs.getString(7));
            txtEmail.setText(rs.getString(8));    
        }
        
    }catch(Exception e){
        System.out.println("error al mostrar resultados"+e.getMessage());
    }
}    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Nombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        Apellido = new javax.swing.JLabel();
        ID = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtId = new javax.swing.JTextField();
        Sueldo = new javax.swing.JLabel();
        Cargo = new javax.swing.JLabel();
        txtCin = new javax.swing.JTextField();
        txtEdad = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        Nivel = new javax.swing.JLabel();
        txtTelf = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        Nivel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDirec = new javax.swing.JTextField();
        Nivel3 = new javax.swing.JLabel();
        btnEliminar1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCliente = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registrar Cliente", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bell MT", 0, 18), new java.awt.Color(0, 204, 0))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Nombre.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Nombre.setText("Nombre:");
        jPanel1.add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, -1, -1));

        txtNombre.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNombre.setEnabled(false);
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 140, 28));

        Apellido.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Apellido.setText("Apellido:");
        jPanel1.add(Apellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, -1, 28));

        ID.setFont(new java.awt.Font("Bell MT", 1, 11)); // NOI18N
        ID.setText("ID:");
        jPanel1.add(ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, -1, -1));

        txtApellido.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtApellido.setEnabled(false);
        jPanel1.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 140, 28));

        txtId.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtId.setEnabled(false);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });
        jPanel1.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 37, -1));

        Sueldo.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Sueldo.setText("C.I.N o RUC:");
        jPanel1.add(Sueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, -1, -1));

        Cargo.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Cargo.setText("Edad:");
        jPanel1.add(Cargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, 28));

        txtCin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCin.setEnabled(false);
        txtCin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCinActionPerformed(evt);
            }
        });
        txtCin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCinKeyTyped(evt);
            }
        });
        jPanel1.add(txtCin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 140, 28));

        txtEdad.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtEdad.setEnabled(false);
        txtEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEdadActionPerformed(evt);
            }
        });
        txtEdad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEdadKeyTyped(evt);
            }
        });
        jPanel1.add(txtEdad, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 140, 28));

        btnCancelar.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 450, 130, 40));

        btnSalir.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 510, 130, 40));

        Nivel.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Nivel.setText("E-mail:");
        jPanel1.add(Nivel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 570, -1, 20));

        txtTelf.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtTelf.setEnabled(false);
        txtTelf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelfKeyTyped(evt);
            }
        });
        jPanel1.add(txtTelf, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 450, 140, 30));

        btnNuevo.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/adduser.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 390, 130, 40));

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clear.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 560, 130, 40));

        btnGuardar.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ok.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 390, 130, -1));

        jLabel5.setText("*");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 410, 20, 20));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/factura.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 450, 130, 40));

        jLabel8.setText("*");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 20, 20));

        txtEmail.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtEmail.setEnabled(false);
        jPanel1.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 560, 270, 30));

        Nivel1.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Nivel1.setText("Teléfono:");
        jPanel1.add(Nivel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, -1, -1));

        jLabel2.setText("*");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 460, 20, 20));

        txtDirec.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtDirec.setEnabled(false);
        jPanel1.add(txtDirec, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 500, 210, 30));

        Nivel3.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Nivel3.setText("Dirección:");
        jPanel1.add(Nivel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 510, -1, -1));

        btnEliminar1.setText("Eliminar");
        btnEliminar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 510, 130, 40));

        jLabel1.setText("opcional");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 510, -1, -1));

        tablaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Apellido", "Edad", "C.I.N°", "Telefono", "Direccion", "E-MAIL"
            }
        ));
        tablaCliente.getTableHeader().setReorderingAllowed(false);
        tablaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaClienteMouseClicked(evt);
            }
        });
        tablaCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaClienteKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCliente);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 850, 130));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 180, -1, -1));

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 190, 130, -1));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 204, 0));
        jLabel9.setText("Introduzca   C.I. o Nombre del Cliente");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 220, 20));
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 230, 140));

        jLabel10.setText("opcional");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 570, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 880, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
 opcion='n';
        try {
   habilitarbotones(true);
   habilitarbotonedi(false);
   limpiar();
   habilitarcampos(true);
   rs = abm.nuevo("idcliente", "cliente");
   rs.first();
   txtId.setText(String.valueOf(rs.getInt("codigo")+1));
   txtNombre.requestFocus();//mantiene el enfoque en un objeto
}catch(Exception e){
   JOptionPane.showMessageDialog(null,"error al generar el codigo "+e);          
}
}//GEN-LAST:event_btnNuevoActionPerformed

private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
habilitarcampos(false);
habilitarbotones(true);
rs=abm.consulta("*", "cliente");
MostrarRegistro(); 
}//GEN-LAST:event_btnCancelarActionPerformed

private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
this.dispose();
}//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    vacio=validardatos();
    if (vacio==true){
        JOptionPane.showMessageDialog(null,"Complete los campos marcados con * ");
    }else{
        switch(opcion){
            case'n':
                v_control=abm.insertar ("cliente",txtId.getText()+",'"+txtNombre.getText()+"','"+txtApellido.getText()+"','"+txtEdad.getText()+"','"+txtCin.getText()+"','"+txtTelf.getText()+"','"+txtDirec.getText()+"','" +txtEmail.getText()+"'");
                if (v_control==true){
                JOptionPane.showMessageDialog(null,"Se ha guardado los datos");              
        }
       break;
             case'm':
                 v_control= abm.modificar ("cliente", "nombre='"+txtNombre.getText()+"', "+ "apellido='"+txtApellido.getText()+"', "+ "edad='"+txtEdad.getText()+"', "+
                        "cinro='"+txtCin.getText()+"', "+"telefono='"+txtTelf.getText()+"'," +"direccion='"+txtDirec.getText()+"', "+ "email='"+txtEmail.getText()+ "'" , " idcliente="+txtId.getText());
                if (v_control==true){
                JOptionPane.showMessageDialog(null,"Datos actualizados"); 
               
                break;
                }         
        }
        }
      habilitarcampos(false);
      habilitarbotones(false);
      habilitarbotonedi(true);
      rs=abm.consulta("*", "cliente");
      MostrarRegistro();
      cargarcliente("");
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
 limpiar();      
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        opcion='m';
        habilitarcampos(true);
        habilitarbotones(true);
        txtNombre.requestFocus();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
  v_control=abm.eliminar("cliente"," idcliente="+txtId.getText() );
        if (v_control==true){
            rs=abm.consulta("*", "cliente");
            MostrarRegistro();
            cargarcliente("");
        }
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void tablaClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaClienteKeyPressed
       seleccionartabla();
    }//GEN-LAST:event_tablaClienteKeyPressed

    private void tablaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaClienteMouseClicked
        seleccionartabla();
    }//GEN-LAST:event_tablaClienteMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        if(txtBuscar.getText().isEmpty()){
        cargarcliente("");
            }
       else{cargarclientesbuscar("");
            }
    }//GEN-LAST:event_txtBuscarKeyReleased

private void txtCinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCinKeyTyped
    /*char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }*/
}//GEN-LAST:event_txtCinKeyTyped

private void txtTelfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelfKeyTyped
char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtTelfKeyTyped

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtEdadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEdadKeyTyped
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
    }//GEN-LAST:event_txtEdadKeyTyped

    private void txtEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEdadActionPerformed

    }//GEN-LAST:event_txtEdadActionPerformed

    private void txtCinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCinActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(RegistrarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new RegistrarCliente().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Apellido;
    private javax.swing.JLabel Cargo;
    private javax.swing.JLabel ID;
    private javax.swing.JLabel Nivel;
    private javax.swing.JLabel Nivel1;
    private javax.swing.JLabel Nivel3;
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel Sueldo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaCliente;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCin;
    private javax.swing.JTextField txtDirec;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelf;
    // End of variables declaration//GEN-END:variables
}
