/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package productos;

import Jcombox.categoria;
import Jcombox.marca;
import Jcombox.procedencia;
import Jcombox.proveedor;
import Jcombox.medida;
import Metodos.abm;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import bebidas.conexionBD;

/**
 *
 * @author oscar
 */
public class AgregarProducto extends javax.swing.JDialog {

    conexionBD cn;
    private abm abm;
    private ResultSet rs,rscbo;
    private boolean v_control;
    DefaultTableModel modelo;
    private Object[] filas;
    private static char opcion;
    private boolean vacio;
     
    
    
    public AgregarProducto() {
        
        initComponents();
        
         cn= new conexionBD();        
         
                
        abm= new abm();
        rs=abm.consulta("*", "productos");
        MostrarRegistro();
        comboxMarca();
        comboxCategoria();
        comboxProveedor();
        jcboxMedida();
        jcboMedida.setVisible(false);
        // TodaLaPantalla();
        
        tablaProducto.setSize(2500, 2800);
        
        verproducto();
        cargarproducto("");
       // cargarproductobuscar();       
       // buscarProducto("");
    }
    public  void TodaLaPantalla(){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(d);
		this.setResizable(false);
	}

       
    
    public void habilitarcampos(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
              txtPrecioCompra.setEnabled(h);
              txtPrecioVenta.setEnabled(h);
              txtStock.setEnabled(h);
              txtDescriProduc.setEnabled(h);
              comboMarca.setEnabled(h);
              comboCategoria.setEnabled(h);
              comboProveedor.setEnabled(h);
              txtIdProducto.setEnabled(h);
              txtCantidad.setEnabled(h);
    }
    public void habilitarbotones(boolean h){ //metodo encargado de habilitar o deshabilitar botonoes
                 btnEditar.setEnabled(h);
                 btnEliminar.setEnabled(h);
                 btnAgregar.setEnabled(h);
        }
    public void habilitarbotones2(boolean j){ //metodo encargado de habilitar o deshabilitar botonoes
                btnCancelar.setEnabled(j);
               // btnGuardar.setEnabled(j);
        }
    public void limpiar(){
            txtPrecioCompra.setText("");
            txtGanancia.setText("");
            txtPrecioVenta.setText("");
            txtStock.setText("");
            txtDescriProduc.setText("");
        }
    public  boolean validardatos(){
            vacio=false;
            if(txtPrecioCompra.getText().isEmpty()){
                vacio=true;
                 }
                return vacio;
            }
    
    
    private void comboxMarca(){
         try{
            rscbo = abm.consultasql("select * from marca order by idmarca");
            while(rscbo.next()){
                marca mar= new marca();
                mar.setCodigo(Integer.valueOf(rscbo.getString("idmarca")));
                mar.setDescripccion(rscbo.getString("descripcion"));
                comboMarca.addItem(mar);
                }
            }
        catch(Exception e){
            e.printStackTrace();
        } 
}
    
    private void comboxCategoria(){
         try{
            rscbo = abm.consultasql("select * from categoria order by idcategoria");
            while(rscbo.next()){
                categoria cat= new categoria();
                cat.setCodigo(Integer.valueOf(rscbo.getString("idcategoria")));
                cat.setDescripcion(rscbo.getString("descripcion"));
                comboCategoria.addItem(cat);
                }
            }
        catch(Exception e){
            e.printStackTrace();
        } 
}
    
     private void jcboxMedida(){
         try{
            rscbo = abm.consultasql("select * from medida order by idmedida");
            while(rscbo.next()){
                medida medida= new medida();
                medida.setCodigo(Integer.valueOf(rscbo.getString("idmedida")));
                medida.setDescripcion(rscbo.getString("descripcion"));
                jcboMedida.addItem(medida);
                }
            }
        catch(Exception e){
            e.printStackTrace();
        } 
}
    
   
      private void comboxProveedor(){
         try{
            rscbo = abm.consultasql("select * from proveedor order by idproveedor");
            while(rscbo.next()){
                proveedor pro= new proveedor();
                pro.setCodigo(Integer.valueOf(rscbo.getString("idproveedor")));
                pro.setNombre(rscbo.getString("nombre"));
                comboProveedor.addItem(pro);
                }
            }
        catch(Exception e){
            e.printStackTrace();
        } 
}    
    
    public void MostrarRegistro(){//metodo creado poara mostrar datos
    try{
       
        if(rs.getRow()!=0){ //devuelve numero de filas de una objeto de tipo resultset
            txtIdProducto.setText(rs.getString(1));
            txtPrecioCompra.setText(rs.getString(2));
            txtPrecioVenta.setText(rs.getString(3));
            txtStock.setText(rs.getString(4));
            txtDescriProduc.setText(rs.getString(5));             
            txtGanancia.setText(rs.getString(6));  
            // combo marca•••••
            marca m=new marca();
            m.setCodigo(Integer.valueOf(rs.getString("idmarca")));
            comboMarca.setSelectedItem(m);
            //combo categoria
            categoria c = new categoria();
            c.setCodigo(Integer.valueOf(rs.getString("idcategoria")));
           // System.out.println("que paso?1-3");
            comboCategoria.setSelectedItem(c);
            //combor Proveedor
            proveedor pro = new proveedor();
            pro.setCodigo(Integer.valueOf(rs.getString("idproveedor")));
            comboProveedor.setSelectedItem(pro);  
        }    
        }catch(Exception e){
            System.out.println("error al mostrar resultados este es "+e.getMessage());
            }
  }
    
void verproducto(){
        try{
            Statement consultamarca = (Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultamarca.executeQuery("select * from productos");
            
            modelo = new DefaultTableModel();
            tablaProducto.setModel(modelo);
            
            
            modelo.addColumn("Codigo");
            modelo.addColumn("Precio Compra");
            modelo.addColumn("Ganancia");
            modelo.addColumn("Precio Venta");
            modelo.addColumn("Stock");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Marca");
            modelo.addColumn("Categoria");
            modelo.addColumn("Proveedor");
            
            
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
    
     public void cargarproducto(String valor ){
        String [] titulos  = {"Codigo","Precio Compra","Precio Venta","Ganancia","Descripcion","Stock","Marca","Proveedor","Categoria"};
        String [] registros  = new String [9];
        String sql=null;
         sql = "select idproducto,preciodecompra,preciodeventa,stock,p.descripcion";
         sql+=",gananciaproducto,marca.descripcion as marca,nombre as proveedor,c.descripcion as categoria";
         sql+=" from productos as p inner join marca on(marca.idmarca=p.idmarca)";
         sql+=" inner join categoria as c on(c.idcategoria=p.idcategoria)";
        // sql+=" inner join medida as med on(med.idmedida=p.idmedida)";
         sql+=" inner join proveedor as pv on(pv.idproveedor=p.idproveedor) order by idproducto";
        // sql+=" where idproducto="+txtIdProducto.getText();
        
        modelo = new DefaultTableModel(null, titulos);
    
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
                registros[3] = rs.getString("gananciaproducto");
                registros[4] = rs.getString("descripcion");
                registros[5] = rs.getString("stock");
                registros[6] = rs.getString("marca");
                registros[7] = rs.getString("proveedor");
                registros[8] = rs.getString("categoria");
               // registros[9] = rs.getString("medida");
                modelo.addRow(registros);
                }
            tablaProducto.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
     public void cargarproductobuscar(){
        String [] titulos  = {"Codigo","Precio Compra","Precio Venta","Ganancia","Descripcion","Stock","Marca","Proveedor","Categoria","IVA"};
        String [] registros  = new String [11];
        String sql=null;
         sql = "select idproducto,preciodecompra,preciodeventa,stock,p.descripcion";
         sql+=",gananciaproducto,marca.descripcion as marca,nombre as proveedor,c.descripcion as categoria, p.iva";
         sql+=" from productos as p inner join marca on(marca.idmarca=p.idmarca)";
         sql+=" inner join categoria as c on(c.idcategoria=p.idcategoria)";
         sql+=" inner join proveedor as pv on(pv.idproveedor=p.idproveedor)";
         sql+=" where p.descripcion LIKE '%"+txtBuscar.getText()+"%'";
         //System.out.println(sql);
         
        modelo = new DefaultTableModel(null, titulos);
    
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
                registros[3] = rs.getString("gananciaproducto");
                registros[4] = rs.getString("descripcion");
                registros[5] = rs.getString("stock");
                registros[6] = rs.getString("marca");
                registros[7] = rs.getString("proveedor");
                registros[8] = rs.getString("categoria");
                registros[9] = rs.getString("iva");
                modelo.addRow(registros);
                }
            tablaProducto.setModel(modelo);
            
            } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, ex);
                            }   
    }
      public void seleccionartabla(){
        
        
      // DefaultTableModel tabla = (DefaultTableModel) this.tablaProducto.getModel();
       
       
       int c= tablaProducto.getSelectedRow();
       
       if(c==-1){
           System.out.println("Seleccione un registro");
            }
       else{
           String id = (String) tablaProducto.getValueAt(c, 0);
           String prcompra = (String) tablaProducto.getValueAt(c, 1);
           String preventa = (String) tablaProducto.getValueAt(c, 2);
           String ganancia = (String) tablaProducto.getValueAt(c, 3);
           String descripcion = (String) tablaProducto.getValueAt(c, 4);
           String stock = (String) tablaProducto.getValueAt(c, 5);
           String marca = (String) tablaProducto.getValueAt(c, 6);
           String proveedor = (String) tablaProducto.getValueAt(c, 7);
           String categoria = (String) tablaProducto.getValueAt(c, 8);
           //String iva = (String) tablaProducto.getValueAt(c, 9);
           
     
           this.txtIdProducto.setText(id);
           this.txtPrecioCompra.setText(prcompra);
           this.txtPrecioVenta.setText(preventa);
           this.txtGanancia.setText(ganancia);           
           this.txtStock.setText(stock);
           this.txtDescriProduc.setText(descripcion);  
           
        
           
           for(int i=0; i<this.comboMarca.getItemCount();i++){
               if(((marca)(this.comboMarca.getItemAt(i))).getDescripccion().equals(marca)){
                   this.comboMarca.setSelectedIndex(i);
                   this.comboMarca.repaint();
                 
                   break;
               }
           }
           
           for(int i=0; i<this.comboProveedor.getItemCount();i++){
               if(((proveedor)(this.comboProveedor.getItemAt(i))).getNombre().equals(proveedor)){
                   this.comboProveedor.setSelectedIndex(i);
                   this.comboProveedor.repaint();
                   break;
                   }
                }  
           
           
           
           for(int i=0; i<this.comboCategoria.getItemCount();i++){
               if(((categoria)(this.comboCategoria.getItemAt(i))).getDescripcion().equals(categoria)){
                   this.comboCategoria.setSelectedIndex(i);
                   this.comboCategoria.repaint();
                   break;
                   }
                }           
           
    }
}
      

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        preciodetalle = new javax.swing.JDialog();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDescriProduc = new javax.swing.JTextField();
        txtGanancia = new javax.swing.JTextField();
        txtPrecioCompra = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        txtIdProducto = new javax.swing.JTextField();
        comboCategoria = new javax.swing.JComboBox();
        comboMarca = new javax.swing.JComboBox();
        comboProveedor = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProducto = new javax.swing.JTable();
        btnSalir = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnBuscar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jcboMedida = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        detalleprecio = new javax.swing.JTable();
        txtCantidad = new javax.swing.JTextField();
        btnTabla = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();

        javax.swing.GroupLayout preciodetalleLayout = new javax.swing.GroupLayout(preciodetalle.getContentPane());
        preciodetalle.getContentPane().setLayout(preciodetalleLayout);
        preciodetalleLayout.setHorizontalGroup(
            preciodetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        preciodetalleLayout.setVerticalGroup(
            preciodetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel3.setFont(new java.awt.Font("Bell MT", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 204, 0));
        jLabel3.setText(" Productos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(jLabel3)
                .addContainerGap(508, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3))
        );

        jSplitPane1.setTopComponent(jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setText("Codigo:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        jLabel10.setText("Precio Compra:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        jLabel11.setText("Precio Venta:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 420, -1, 20));

        jLabel12.setText("Stock:");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 380, -1, -1));

        jLabel13.setText("Descripcion:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));

        jLabel14.setText("Cantidad:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 420, -1, -1));

        txtDescriProduc.setEnabled(false);
        jPanel2.add(txtDescriProduc, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 150, 50));

        txtGanancia.setEnabled(false);
        txtGanancia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGananciaFocusLost(evt);
            }
        });
        jPanel2.add(txtGanancia, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 430, 100, -1));

        txtPrecioCompra.setEnabled(false);
        txtPrecioCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioCompraKeyTyped(evt);
            }
        });
        jPanel2.add(txtPrecioCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 380, 100, -1));

        txtPrecioVenta.setEnabled(false);
        txtPrecioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioVentaActionPerformed(evt);
            }
        });
        jPanel2.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 100, -1));

        jLabel15.setText("Marca:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, -1, -1));

        jLabel16.setText("Proveedor:");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 340, -1, -1));

        txtStock.setEnabled(false);
        txtStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockKeyTyped(evt);
            }
        });
        jPanel2.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 380, 100, -1));

        txtIdProducto.setEnabled(false);
        jPanel2.add(txtIdProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 100, -1));

        comboCategoria.setEnabled(false);
        comboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoriaActionPerformed(evt);
            }
        });
        jPanel2.add(comboCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 70, -1));

        comboMarca.setEnabled(false);
        jPanel2.add(comboMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 260, 130, -1));

        comboProveedor.setEnabled(false);
        jPanel2.add(comboProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 340, 130, -1));

        tablaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Producto", "Precio Compra", "Precio Venta", "Stock", "Descripcion", "Ganancia", "Marca", "Proveedor", "Procedencia", "Categoria", "Medida"
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tablaProductoKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tablaProducto);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 960, 200));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/equis.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 420, 100, -1));

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/add.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 300, -1, -1));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/articulos.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 360, -1, 40));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/actualizar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 110, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/basureronegro.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 300, -1, -1));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 360, 100, -1));

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel2.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 200, 30));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 250, 20, 210));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buscar.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 110, 30));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 0));
        jLabel5.setText("Introduzca Descripcion del Producto");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 210, -1));

        jPanel2.add(jcboMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 380, 80, -1));

        jLabel17.setText("Medida:");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 420, -1, -1));

        detalleprecio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Medida", "Precio", "Cantdad", "codigo"
            }
        ));
        detalleprecio.getTableHeader().setReorderingAllowed(false);
        detalleprecio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detalleprecioMouseClicked(evt);
            }
        });
        detalleprecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                detalleprecioKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                detalleprecioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                detalleprecioKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(detalleprecio);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 400, 110));

        txtCantidad.setEnabled(false);
        jPanel2.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 60, -1));

        btnTabla.setText("+");
        btnTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTablaActionPerformed(evt);
            }
        });
        jPanel2.add(btnTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 450, -1, -1));

        jLabel18.setText("Ganancia:");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 430, -1, -1));

        jSplitPane1.setRightComponent(jPanel2);

        getContentPane().add(jSplitPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
                opcion='n';
                limpiar();
                habilitarbotones(false);
                habilitarcampos(true);
                habilitarbotones2(true);
                rs = abm.nuevo("idproducto", "productos");
                rs.first();
                txtIdProducto.setText(String.valueOf(rs.getInt("codigo")+1));
                txtDescriProduc.requestFocus();//mantiene el enfoque en un objeto
                rs.close();
                }catch(Exception e){
                JOptionPane.showMessageDialog(null,"error al generar el codigo "+e);          
                }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       vacio=validardatos();
        if(vacio==true){
            JOptionPane.showMessageDialog(null,"Completar los datos , no pueden quedar en blanco");
        }else{
            switch(opcion){
                case 'n':
                        conexionBD cn = new conexionBD();// se crea la conexion 
                        Connection cnn = (Connection) cn.ConectarBD();
                        Statement  st; 
                        try{
                            String consulta="";
                            consulta=("Select * from productos where descripcion='"+txtDescriProduc.getText()+"'"); //se traen todos los registros
                            st = (Statement) cnn.createStatement();
                            ResultSet rs = st.executeQuery(consulta);
                            rs.first();
                            if (rs.getRow() != 0){ // si es distinto a 0 ya existe y no agrega
                                            JOptionPane.showMessageDialog(null,"Este producto ya existe");                                           
                                            txtPrecioCompra.setText("");
                                            txtPrecioCompra.requestFocus();
                                            }
                            else{
                                    marca mar =  (marca) comboMarca.getSelectedItem();
                                    categoria cat =  (categoria) comboCategoria.getSelectedItem();
                                    proveedor prove =  (proveedor) comboProveedor.getSelectedItem();
                                    medida medida = (medida) jcboMedida.getSelectedItem();
                                    String iva="";
                                    int c=0;
                                    
                                    iva = "10";
                                    v_control=abm.insertar("productos",txtIdProducto.getText()+","+txtPrecioCompra.getText()+","+txtPrecioVenta.getText()+","+txtStock.getText()+",'"+txtDescriProduc.getText()+"',"+txtGanancia.getText()+","+mar.getCodigo()+","+cat.getCodigo()+","+prove.getCodigo()+","+iva);
                                    if (v_control==true){
                                                JOptionPane.showMessageDialog(null,"Se ha guardado los datos");              
                                                }
                                    for (int i = 0; i < detalleprecio.getRowCount(); i++) {
                                     
                                     v_control=abm.insertar("detalleprecio",txtIdProducto.getText()+","+detalleprecio.getValueAt(i, 3)+","+detalleprecio.getValueAt(i, 1)+","+detalleprecio.getValueAt(i, 2));
                                    
                                    }
                                    
                                   if (v_control==true){
                                                JOptionPane.showMessageDialog(null,"Se ha guardado los datos");              
                                                }
                                }
                        }catch(Exception e){
                                    System.out.println("Error al mostrar datos en la tabla"+e.getMessage());  
                                    }           
                break;
                case 'm':
                        marca mar =  (marca) comboMarca.getSelectedItem();
                        categoria cat =  (categoria) comboCategoria.getSelectedItem();
                        proveedor prove =  (proveedor) comboProveedor.getSelectedItem();
                        String iva="10";
                        v_control= abm.modificar("productos", " preciodecompra="+txtPrecioCompra.getText()+", "+" preciodeventa="+txtPrecioVenta.getText()+", "
                                +" stock="+txtStock.getText()+", "+" descripcion='"+txtDescriProduc.getText()+"', "+" gananciaproducto="+txtGanancia.getText()+", "+
                                " idmarca="+mar.getCodigo()+", "+" idcategoria="+cat.getCodigo()+", "+" idproveedor="+prove.getCodigo()+", "+" iva="+iva
                                , "idproducto="+txtIdProducto.getText());
                        if(v_control==true){
                          JOptionPane.showMessageDialog(null,"Datos actualizados congratuleishon");
                            }
                        break;
               }
        habilitarcampos(false);
        habilitarbotones(true);
        habilitarbotones2(false);
        DefaultTableModel tablaborrar = (DefaultTableModel) this.detalleprecio.getModel();
        while(tablaborrar.getRowCount()>0)tablaborrar.removeRow(0);
        rs=abm.consulta("*", "productos");        
        MostrarRegistro();
        verproducto();
        cargarproducto("");        
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void comboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboCategoriaActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
    this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        opcion='m';
        habilitarcampos(true);
        habilitarbotones(false);
        habilitarbotones2(true);
        txtPrecioCompra.requestFocus();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        habilitarbotones2(false);
        habilitarbotones(true);
        habilitarcampos(false);
        rs=abm.consulta("*", "productos");
        MostrarRegistro();
        verproducto();
        cargarproducto("");
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtGananciaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGananciaFocusLost
       
    }//GEN-LAST:event_txtGananciaFocusLost

    private void tablaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductoMouseClicked
       seleccionartabla();
       
    }//GEN-LAST:event_tablaProductoMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        v_control = abm.eliminar("productos", "idproducto="+txtIdProducto.getText());
        if(v_control==true){
            rs= abm.consulta("*", "productos");
            MostrarRegistro();
            JOptionPane.showMessageDialog(null,"Datos Eliminados congratuleishon");
            }
        verproducto();
        cargarproducto("");
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void tablaProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductoKeyPressed
      
    }//GEN-LAST:event_tablaProductoKeyPressed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:
        
       if(txtBuscar.getText().isEmpty()){
        cargarproducto("");
            }
       else{cargarproductobuscar();
            }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tablaProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductoKeyReleased
         seleccionartabla();
    }//GEN-LAST:event_tablaProductoKeyReleased

    private void tablaProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductoKeyTyped
       
    }//GEN-LAST:event_tablaProductoKeyTyped

    private void txtPrecioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioVentaActionPerformed
        // TODO add your handling code here:
         int precioventa, preciocompra, ganancia;
        
        preciocompra = Integer.parseInt(txtPrecioCompra.getText().toString());
        precioventa = Integer.parseInt(txtPrecioVenta.getText().toString());
        ganancia = precioventa-preciocompra;
        txtGanancia.setText(String.valueOf(ganancia));
    }//GEN-LAST:event_txtPrecioVentaActionPerformed

private void txtPrecioCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioCompraKeyTyped
        char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtPrecioCompraKeyTyped

private void txtStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockKeyTyped
char caracter=evt.getKeyChar();
        if((caracter<'0'||(caracter>'9'))&&(caracter!='\b')){
            evt.consume();
        }
}//GEN-LAST:event_txtStockKeyTyped

    private void detalleprecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_detalleprecioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_detalleprecioKeyTyped

    private void detalleprecioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_detalleprecioKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_detalleprecioKeyReleased

    private void detalleprecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_detalleprecioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_detalleprecioKeyPressed

    private void detalleprecioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detalleprecioMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_detalleprecioMouseClicked

    private void btnTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTablaActionPerformed
        // TODO add your handling code here:
         categoria cat =  (categoria) comboCategoria.getSelectedItem();
         
        
       DefaultTableModel tabla = (DefaultTableModel) this.detalleprecio.getModel();
        
       //int iva= tablafactura.getSelectedRow();
      
      
           Object[] valor = new Object[7];
           valor[0] =  cat.getDescripcion();
           valor[1] = (String) txtPrecioVenta.getText();
           valor[2] = (String) txtCantidad.getText();
           valor[3] =  cat.getCodigo();
           
           
           
           tabla.addRow(valor);
           
           btnGuardar.setEnabled(true);
       
    }//GEN-LAST:event_btnTablaActionPerformed

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
            java.util.logging.Logger.getLogger(AgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AgregarProducto().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTabla;
    private javax.swing.JComboBox comboCategoria;
    private javax.swing.JComboBox comboMarca;
    private javax.swing.JComboBox comboProveedor;
    private javax.swing.JTable detalleprecio;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JComboBox jcboMedida;
    private javax.swing.JDialog preciodetalle;
    private javax.swing.JTable tablaProducto;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescriProduc;
    private javax.swing.JTextField txtGanancia;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
