/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class RegistrarEmpleado extends javax.swing.JDialog {

    conexionBD cn;
    private abm abm;
    private boolean vacio;
    private static char opcion;
    private ResultSet rs; // devolver una variable cpon los resultados de la BD
    private boolean v_control;//si guardo bien o mal
    DefaultTableModel modelo;
    private Object[] filas;

    public RegistrarEmpleado() {

        initComponents();

        cn = new conexionBD();

        abm = new abm();
        rs = abm.consulta("*", "empleado");
        MostrarRegistro();
        cargarempleados("");
        verempleados();
        //this.setTitle("genero :Usuario" +abm.nomUsuario +"nivel: "+ abm.nivel);      
    }

    public void cargarempleados(String valor) {
        String[] titulos = {"Codigo", "Nombre", "Apellido", "Cargo", "Sueldo", "Nivel", "Usuario", "CIN°"};
        String[] registros = new String[8];
        String sql = "select idempleado, nombre, apellido, cargo, sueldo, nivel, usuario, cinro from empleado where CONCAT(idempleado,' ',nombre,' ',apellido,' ',cargo,' ',sueldo,' ',nivel,' ',usuario,' ',cinro) LIKE '%" + valor + "%'";

        // sql+=" where idproducto="+txtIdProducto.getText();

        modelo = new DefaultTableModel(null, titulos);

        conexionBD cn = new conexionBD();
        Connection cnn = (Connection) cn.ConectarBD();
        Statement st;
        try {

            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("idempleado");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("apellido");
                registros[3] = rs.getString("cargo");
                registros[4] = rs.getString("sueldo");
                registros[5] = rs.getString("nivel");
                registros[6] = rs.getString("usuario");
                registros[7] = rs.getString("cinro");

                modelo.addRow(registros);
            }
            tablaEmpleado.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void verempleados() {
        try {
            com.mysql.jdbc.Statement consultamarca = (com.mysql.jdbc.Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultamarca.executeQuery("select idempleado, nombre, apellido, cargo, sueldo, nivel, usuario, cinro from empleado");

            modelo = new DefaultTableModel();
            tablaEmpleado.setModel(modelo);


            modelo.addColumn("Codigo");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Cargo");
            modelo.addColumn("Sueldo");
            modelo.addColumn("Nivel");
            modelo.addColumn("Usuario");
            modelo.addColumn("C.I.N°");


            filas = new Object[modelo.getColumnCount()];

            while (rs.next()) {
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(filas);

            }

            tablaEmpleado.setModel(modelo);

        } catch (Exception e) {
            System.out.println("Error al mostrar datos en la tabla" + e.getMessage());
        }
    }

     void seleccionartabla() {
        //creamos una variable que va a contener la fila seleccionada de la tabla
        int i = tablaEmpleado.getSelectedRow();
        //hacemos una condicion de que si la varialbe i es igual a - es que no se ha seleccionado ninguna fila
        if (i == -1) {

            JOptionPane.showMessageDialog(null, "Favor seleccione una fila");

        } else { //de lo contrario si se selecciono la fila     
//creamos nuestras varialbes que va a contener los datos de la fila seleccionado en este caso
//nom va a tener el dato que se encuentra en la fila (i),recuerda que esa variable es la fila 
//seleccionada,columna 0,que tiene el campo nombre me imagino,y hacemos lo mismo con los otros.getValueAt
            //Obetemos el valor que se encuentra en la tabla indicando la fila y columna.
//haciendo un Casting a String ese valor.
            Integer id = (Integer) tablaEmpleado.getValueAt(i, 0);
            String nombre = (String) tablaEmpleado.getValueAt(i, 1);
            String apellido =(String)tablaEmpleado.getValueAt(i, 2);
            String cargo =(String)tablaEmpleado.getValueAt(i, 3);
            Integer sueldo = (Integer) tablaEmpleado.getValueAt(i, 4);
            Integer nivel = (Integer) tablaEmpleado.getValueAt(i, 5);
            String usuario =(String)tablaEmpleado.getValueAt(i, 6);
          //  String contra =(String)tablaEmpleado.getValueAt(i, 7);
            String cin =(String)tablaEmpleado.getValueAt(i, 7);
            
            txtId.setText(Integer.toString(id));
            this.txtNombre.setText(nombre);
            this.txtApellido.setText(apellido);
            this.txtCargo.setText(cargo);
            txtSueldo.setText(Integer.toString(sueldo));
            txtNivel.setText(Integer.toString(nivel));
            this.txtUsuario.setText(usuario);
            //this.txtContraseña.setText(contra);
            //txtCin.setText(Integer.toString(cin));
            this.txtCin.setText(cin);
        }

    }
      public void cargarempleadosbuscar(String valor ){
        String [] titulos  = {"Codigo", "Nombre", "Apellido", "Cargo", "Sueldo", "Nivel", "Usuario", "C.I.N°"};
        String [] registros  = new String [8];
        String sql = "select idempleado, nombre, apellido, cargo, sueldo, nivel, usuario, cinro from empleado where CONCAT(usuario,' ',cinro) LIKE  '%"+txtBuscar.getText()+"%'";  
         
        
         
        modelo = new DefaultTableModel(null, titulos);
    
        conexionBD cnp = new conexionBD();
        Connection cnn = (Connection) cnp.ConectarBD();
        com.mysql.jdbc.Statement  st;
        try {
            
            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);
            
            while(rst.next()){
                registros[0] = rst.getString("idempleado");
                registros[1] = rst.getString("nombre");
                registros[2] = rst.getString("apellido");
                registros[3] = rst.getString("cargo");
                registros[4] = rst.getString("sueldo");
                registros[5] = rst.getString("nivel");
                registros[6] = rst.getString("usuario");
                registros[7] = rst.getString("cinro");
                
                modelo.addRow(registros);
                }
            tablaEmpleado.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }

    public void habilitarbotones(boolean h) { //metodo encargado de habilitar o deshabilitar botonoes
        
        btnGuardar.setEnabled(h);
        btnCancelar.setEnabled(h);
    }

    public void habilitarbotonedi(boolean h) { //metodo encargado de habilitar o deshabilitar botonoes
        btnEditar.setEnabled(h);
        btnEliminar.setEnabled(h);
        btnNuevo.setEnabled(h);
    }

    public void habilitarcampos(boolean h) { //metodo encargado de habilitar o deshabilitar botonoes
        txtNombre.setEnabled(h);
        txtApellido.setEnabled(h);
        txtSueldo.setEnabled(h);
        txtCargo.setEnabled(h);
        txtNivel.setEnabled(h);
        txtUsuario.setEnabled(h);
        txtCin.setEnabled(h);
        txtContraseña.setEnabled(h);
    }

    public void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtSueldo.setText("");
        txtCargo.setText("");
        txtNivel.setText("");
        txtUsuario.setText("");
        txtCin.setText("");
    }

    public boolean validardatos() {
        vacio = false;
        if (txtNombre.getText().isEmpty()) {
            vacio = true;
        }
        if (txtApellido.getText().isEmpty()) {
            vacio = true;
        }
        if (txtSueldo.getText().isEmpty()) {
            vacio = true;
        }
        if (txtCargo.getText().isEmpty()) {
            vacio = true;
        }
        if (txtNivel.getText().isEmpty()) {
            vacio = true;
        }
        if (txtUsuario.getText().isEmpty()) {
            vacio = true;
        }
        if (txtCin.getText().isEmpty()) {
            vacio = true;
        }
        return vacio;
    }

    public void MostrarRegistro() {//metodo creado poara mostrar datos
        try {
            if (rs.getRow() != 0) { //devuelve numero de filas de una objeto de tipo resultset
                txtId.setText(rs.getString(1));
                txtNombre.setText(rs.getString(2));
                txtApellido.setText(rs.getString(3));
                txtCargo.setText(rs.getString(4));
                txtSueldo.setText(rs.getString(5));
                txtNivel.setText(rs.getString(6));
                txtUsuario.setText(rs.getString(7));
            }

        } catch (Exception e) {
            System.out.println("error al mostrar resultados" + e.getMessage());
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
        txtSueldo = new javax.swing.JTextField();
        txtCargo = new javax.swing.JTextField();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        Nivel = new javax.swing.JLabel();
        txtNivel = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCin = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEmpleado = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnResetear = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Registrar Empleado", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Bell MT", 0, 18), new java.awt.Color(0, 153, 0))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Nombre.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Nombre.setText("Nombre:");
        jPanel1.add(Nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

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
        Sueldo.setText("Sueldo:");
        jPanel1.add(Sueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, -1, -1));

        Cargo.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Cargo.setText("Cargo:");
        jPanel1.add(Cargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, -1, 28));

        txtSueldo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtSueldo.setEnabled(false);
        txtSueldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSueldoKeyTyped(evt);
            }
        });
        jPanel1.add(txtSueldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 140, 28));

        txtCargo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCargo.setEnabled(false);
        jPanel1.add(txtCargo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 140, 28));

        btnCancelar.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clear.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 360, 130, 40));

        btnSalir.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 430, 130, 40));

        Nivel.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        Nivel.setText("Nivel:");
        jPanel1.add(Nivel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, -1, -1));

        txtNivel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNivel.setEnabled(false);
        txtNivel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNivelKeyTyped(evt);
            }
        });
        jPanel1.add(txtNivel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 460, 140, 30));

        btnNuevo.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/adduser.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 130, 40));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clear.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 430, 130, 40));

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
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 300, 130, -1));

        jLabel1.setText("*");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 460, 20, 20));

        jLabel2.setText("*");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 560, 20, 20));

        jLabel3.setText("*");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 310, 20, 20));

        jLabel4.setText("*");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 360, 20, 20));

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
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 360, 130, 40));

        jLabel6.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        jLabel6.setText("Usuario");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 520, 60, 20));

        txtCin.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCin.setEnabled(false);
        txtCin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCinKeyTyped(evt);
            }
        });
        jPanel1.add(txtCin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 600, 130, 20));

        jLabel7.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        jLabel7.setText("C.I. N°");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 610, -1, -1));

        txtUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtUsuario.setEnabled(false);
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 510, 130, 20));

        jLabel8.setText("*");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 600, 20, 20));

        jLabel9.setText("*");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 510, 20, 20));

        tablaEmpleado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Apellido", "Cargo", "Sueldo", "Nivel", "Usuario", "CIN"
            }
        ));
        tablaEmpleado.getTableHeader().setReorderingAllowed(false);
        tablaEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaEmpleadoMouseClicked(evt);
            }
        });
        tablaEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablaEmpleadoKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablaEmpleado);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 820, 100));

        jLabel11.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        jLabel11.setText("Contraseña");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, -1, -1));

        jLabel12.setText("*");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 260, 20, 20));

        txtContraseña.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtContraseña.setEnabled(false);
        jPanel1.add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 560, 130, 20));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buscar.png"))); // NOI18N
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 150, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 160, 210, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/3.png"))); // NOI18N
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 360, 160, -1));

        btnResetear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/actualizar.png"))); // NOI18N
        btnResetear.setText("Resetear Contraseña");
        btnResetear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetearActionPerformed(evt);
            }
        });
        jPanel1.add(btnResetear, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 510, -1, -1));

        jLabel13.setFont(new java.awt.Font("Times New Roman", 2, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 204, 0));
        jLabel13.setText("Introduzca Usuario o Nro de C.I.");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 900, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        opcion = 'm';
        habilitarcampos(true);
        habilitarbotones(true);
        txtNombre.requestFocus();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        vacio = validardatos();
        if (vacio == true) {
            JOptionPane.showMessageDialog(null, "Complete los campos marcados con * ");
        } else {
            switch (opcion) {
                case 'n':
                    v_control = abm.insertar("empleado", txtId.getText() + ",'" + txtNombre.getText() + "','" + txtApellido.getText() + "','" + txtCargo.getText() + "'," + txtSueldo.getText() + "," + txtNivel.getText() + ",'" + txtUsuario.getText() +"','"+txtContraseña.getText()+ "','" + txtCin.getText() + "'");
                    if (v_control == true) {
                        JOptionPane.showMessageDialog(null, "Se ha guardado los datos");
                    }
                    break;
                case 'm':
                    v_control = abm.modificar("empleado", "nombre='" + txtNombre.getText() + "', " + "apellido='" + txtApellido.getText() + "', " + "cargo='" + txtCargo.getText() + "', "
                            + "sueldo=" + txtSueldo.getText() + ", nivel=" + txtNivel.getText() + "", " idempleado=" + txtId.getText());
                    if (v_control == true) {
                        JOptionPane.showMessageDialog(null, "Datos actualizados");

                        break;
                    }


            }
        }
        habilitarcampos(false);
        habilitarbotones(false);
        rs = abm.consulta("*", "empleado");
        MostrarRegistro();
        cargarempleados("");
        verempleados();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        v_control = abm.eliminar("empleado", " idempleado=" + txtId.getText());
        if (v_control == true) {
            rs = abm.consulta("*", "empleado");
            MostrarRegistro();
            cargarempleados("");
            verempleados();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        opcion = 'n';
        try {
            habilitarbotones(true);
            limpiar();
            habilitarcampos(true);
            rs = abm.nuevo("idempleado", "empleado");
            rs.first();
            txtId.setText(String.valueOf(rs.getInt("codigo") + 1));
            txtNombre.requestFocus();//mantiene el enfoque en un objeto
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error al generar el codigo " + e);
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        habilitarcampos(false);
        habilitarbotones(false);
        habilitarbotonedi(true);
        rs = abm.consulta("*", "empleado");
//MostrarRegistro(); 
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void tablaEmpleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaEmpleadoKeyReleased
        seleccionartabla();
    }//GEN-LAST:event_tablaEmpleadoKeyReleased

    private void tablaEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaEmpleadoMouseClicked
        
        if(txtBuscar.getText().isEmpty()){
        seleccionartabla();
            }
       else{int i = tablaEmpleado.getSelectedRow();
        //hacemos una condicion de que si la varialbe i es igual a - es que no se ha seleccionado ninguna fila
        if (i == -1) {

            JOptionPane.showMessageDialog(null, "Favor seleccione una fila");

        } else { //de lo contrario si se selecciono la fila     
//creamos nuestras varialbes que va a contener los datos de la fila seleccionado en este caso
//nom va a tener el dato que se encuentra en la fila (i),recuerda que esa variable es la fila //seleccionada,columna 0,que tiene el campo nombre me imagino,y hacemos lo mismo con los otros.getValueAt Obetemos el valor que se encuentra en la tabla indicando la fila y columna.
//haciendo un Casting a String ese valor.
            String id = (String) tablaEmpleado.getValueAt(i, 0);
            String nombre = (String) tablaEmpleado.getValueAt(i, 1);
            String apellido =(String)tablaEmpleado.getValueAt(i, 2);
            String cargo =(String)tablaEmpleado.getValueAt(i, 3);
            String  sueldo = (String ) tablaEmpleado.getValueAt(i, 4);
            String  nivel = (String ) tablaEmpleado.getValueAt(i, 5);
            String usuario =(String)tablaEmpleado.getValueAt(i, 6);
          //  String contra =(String)tablaEmpleado.getValueAt(i, 7);
            String cin =(String)tablaEmpleado.getValueAt(i, 7);
            
            txtId.setText((id));
            this.txtNombre.setText(nombre);
            this.txtApellido.setText(apellido);
            this.txtCargo.setText(cargo);
            txtSueldo.setText((sueldo));
            txtNivel.setText((nivel));
            this.txtUsuario.setText(usuario);
            //this.txtContraseña.setText(contra);
            this.txtCin.setText(cin);
        }
       
            }
        
    }//GEN-LAST:event_tablaEmpleadoMouseClicked

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        if(txtBuscar.getText().isEmpty()){
        cargarempleados("");
            }
       else{cargarempleadosbuscar("");
       
            }
    }//GEN-LAST:event_txtBuscarKeyReleased

private void txtSueldoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSueldoKeyTyped
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtSueldoKeyTyped

private void txtNivelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNivelKeyTyped
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtNivelKeyTyped

private void txtCinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCinKeyTyped
    char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtCinKeyTyped

private void btnResetearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetearActionPerformed
// TODO add your handling code here:
    
     try {
          Statement stm = (Statement) cn.ConectarBD().createStatement();
                    String consulta="";
                    consulta = "update empleado set ";
                    consulta +="contraseña='"+12345+"' where idempleado="+txtId.getText();
                    int executeUpdate = stm.executeUpdate(consulta);
                    stm.close();
                    JOptionPane.showMessageDialog(null, "Operacion exitosa contraseña reseteada");
                    
                } catch (Exception e) {
                }
     
}//GEN-LAST:event_btnResetearActionPerformed

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
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new RegistrarEmpleado().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Apellido;
    private javax.swing.JLabel Cargo;
    private javax.swing.JLabel ID;
    private javax.swing.JLabel Nivel;
    private javax.swing.JLabel Nombre;
    private javax.swing.JLabel Sueldo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnResetear;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaEmpleado;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCargo;
    private javax.swing.JTextField txtCin;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNivel;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtSueldo;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
