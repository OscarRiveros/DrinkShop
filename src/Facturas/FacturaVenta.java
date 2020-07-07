package Facturas;

import Metodos.abm;
import Jcombox.medida;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.io.IOException;
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
//import num_a_letras.n2t;
import bebidas.conexionBD;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.GraphicsEnvironment;

public class FacturaVenta extends javax.swing.JDialog implements Printable {

    conexionBD cn;
    private ResultSet rs, rscbo, rscboo;
    DefaultTableModel modelo;
    private Object[] filas;
    DefaultTableModel modeloo;
    private boolean v_control;
    private abm abm;
    private boolean vacio;
    Calendar FechaVence;

    public FacturaVenta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();

            abm = new abm();
            cn = new conexionBD();
            FechaVence = new GregorianCalendar();
            rs = abm.consulta("*", "cliente");
            cargarclientedefault();
            // this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
            //jcboxDetallePrecio();

            txtIDcodigo.setVisible(false);
            txtStock.setVisible(false);
            btnContado.setSelected(true);
            btnContado.setVisible(false);
            txtCategoria.setVisible(false);
            txtMarca.setVisible(false);
            txtCantidadUnico.setVisible(false);
            txtMulltipliacion.setVisible(false);

            tablaDetalleFactyra();

            cargarclientes("");
            obtenerfecha();
            //verclientes();
            cargarproducto("");
            // verproducto();
            txtIVA.setVisible(false);
            txtIdcliente.setVisible(false);
            txtidfac.setVisible(false);
            btnCredito.setVisible(false);
            btnImprimir.setVisible(false);
            rs = abm.nuevo("numerofactura", "FacturaVenta");
            try {
                rs.first();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtNumFac.setText(String.valueOf(rs.getInt("codigo") + 1));
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        idfactura();
        idfcobrocliente();

        // habilitarcliente();
        calendario();
        txtCodigo.requestFocus();

    }

    void tablaDetalleFactyra() {
        String[] titulos = {"Codigo", "Descripcion", "Cantidad", "Precio", "Exentas", "Iva 5", "Iva 10", "codigo", "cantidad"};

        modelo = new DefaultTableModel(null, titulos);

        tablafactura.setModel(modelo);
        tablafactura.removeColumn(tablafactura.getColumnModel().getColumn(8));
        tablafactura.removeColumn(tablafactura.getColumnModel().getColumn(7));

    }

    void comboyo() {
        medida medida = (medida) comboMedida.getSelectedItem();
        int med = medida.getCodigo();
        txtIDcodigo.setText(Integer.valueOf(med).toString());

    }

    private void jcboxDetallePrecioo() {
        try {
            String sql = "select m.descripcion,dp.idmedida, precioventa, cantidad from detalleprecio as dp "
                    + "inner join medida as m on m.idmedida=dp.idmedida "
                    + "where idproducto=" + txtCodigo.getText();
            cn = new conexionBD();
            Connection cnn = (Connection) cn.ConectarBD();
            Statement st;
            st = (Statement) cnn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                medida medida = new medida();
                medida.setCodigo(Integer.valueOf(rs.getString("idmedida")));
                medida.setDescripcion(rs.getString("descripcion"));
                comboMedida.addItem(medida);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void comboxMedida() {
        try {
            Statement consultamedida = (Statement) conexionBD.ConectarBD().createStatement();
            ResultSet sql = consultamedida.executeQuery("select m.descripcion,dp.idmedida, precioventa, cantidad from detalleprecio as dp "
                    + "inner join medida as m on m.idmedida=dp.idmedida "
                    + "where idproducto=" + txtCodigo.getText());

            while (sql.next()) {
                medida medida = new medida();
                medida.setCodigo(Integer.valueOf(sql.getString("idmedida")));
                medida.setDescripcion(sql.getString("descripcion"));
                comboMedida.addItem(medida);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void precioCategoria() {
        try {
            medida medida = (medida) comboMedida.getSelectedItem();
            int meidadd = medida.getCodigo();
            String sql = "select precioventa,cantidad from detalleprecio where idmedida=" + meidadd + " and idproducto=" + txtCodigo.getText();
            conexionBD cn = new conexionBD();
            Connection cnn = (Connection) cn.ConectarBD();
            Statement st;
            st = (Statement) cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                txtPrecioVenta.setText(rs.getString("precioventa"));
                txtCantidadUnico.setText(rs.getString("cantidad"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void calendario() {
        fecha1.setDate(FechaVence.getTime());

        FechaVence = fecha1.getCalendar();
        FechaVence.setTime(fecha1.getDate());
        String dia = String.valueOf(FechaVence.get(Calendar.DATE));
        String mes = String.valueOf(FechaVence.get(Calendar.MONTH) + 1);
        String anio = String.valueOf(FechaVence.get(Calendar.YEAR));
        String date = anio + "/" + mes + "/" + dia;
    }

    public void cargarclientedefault() {
        try {
            if (rs.getRow() != 0) { //devuelve numero de filas de una objeto de tipo resultset
                txtIdcliente.setText(rs.getString(1));
                txtnombre.setText(rs.getString(2));
                txtCin.setText(rs.getString(5));
                txtApellido.setText(rs.getString(3));
                txtTelef.setText(rs.getString(6));
                txtDireccion.setText(rs.getString(7));
            }

        } catch (Exception e) {
            System.out.println("error al mostrar resultados ooodcioj" + e.getMessage());
        }

    }

    public void habilitarbotones(boolean j) {
        buscarProdu.setEnabled(j);
    }

    public void habilitarbotonestg(boolean h) {
        AgregarProducto.setEnabled(h);
        //txtCantidad.setEnabled(h);

    }

    public void habilitarbotones2(boolean k) {
        btnGuardar.setEnabled(k);
        btnLimpiar.setEnabled(k);
        btnQuitar.setEnabled(k);
        btnCredito.setEnabled(k);
    }

    public void limpiarcamposcabezara() {
        txtnombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtCin.setText("");
        txtTelef.setText("");
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtPrecioVenta.setText("");
        txtTotal1.setText("");
        txtExentas.setText("");
        txtDies.setText("");
        txtTotal.setText("");
        txtIva5.setText("");
        txtIva10.setText("");
        txtTotalIva.setText("");
        txtCinco.setText("");
//        txtTotalLetras.setText("");
        txtTotalDos.setText("");
        txtEfectivo.setText("");
        txtVuelto.setText("");

        DefaultTableModel tablaborrar = (DefaultTableModel) this.tablafactura.getModel();
        while (tablaborrar.getRowCount() > 0) {
            tablaborrar.removeRow(0);
        }
        DefaultTableModel tablaborrarr = (DefaultTableModel) this.tablafacturaVista.getModel();
        while (tablaborrarr.getRowCount() > 0) {
            tablaborrarr.removeRow(0);
        }

    }

    void obtenerfecha() {
        Date fecha = new Date();
        SimpleDateFormat fe = new SimpleDateFormat("yyyy/MM/dd");
        this.fecha.setText(fe.format(fecha));
    }

    public void idfactura() {
        try {
            rs = abm.nuevo("idfacturaventa", "FacturaVenta");
            try {
                rs.first();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtidfac.setText(String.valueOf(rs.getInt("codigo") + 1));
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void idfcobrocliente() {
        try {
            rs = abm.nuevo("idEntregaInicial", "EntregaInicial");
            try {
                rs.first();
            } catch (SQLException ex) {
                Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
            txtIdFacCredito.setText(String.valueOf(rs.getInt("codigo") + 1));
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarclientes(String valor) {
        String[] titulos = {"Codigo", "Nombre", "Apellido", "C.I.N°", "Telefono", "Direccion"};
        String[] registros = new String[6];
        String sql = "select idcliente, nombre, apellido, cinro, telefono, direccion from cliente where CONCAT(idcliente,' ',nombre,' ',apellido,' ',cinro,' ',telefono,' ',direccion) LIKE '%" + valor + "%'";

        // sql+=" where idproducto="+txtIdProducto.getText();
        modelo = new DefaultTableModel(null, titulos);

        conexionBD cnx = new conexionBD();
        Connection cnn = (Connection) cnx.ConectarBD();
        com.mysql.jdbc.Statement st;
        try {

            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rss = st.executeQuery(sql);

            while (rss.next()) {
                registros[0] = rss.getString("idcliente");
                registros[1] = rss.getString("nombre");
                registros[2] = rss.getString("apellido");
                registros[3] = rss.getString("cinro");
                registros[4] = rss.getString("telefono");
                registros[5] = rss.getString("direccion");
                modelo.addRow(registros);
            }
            tablacliente.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cargarclinetesbuscar(String valor) {
        String[] titulos = {"Codigo", "Nombre", "Apellido", "C.I.N°", "Telefono", "Direccion"};
        String[] registros = new String[6];
        String sql = "select idcliente, nombre, apellido, cinro, telefono, direccion from cliente where cinro LIKE '%" + txtBuscarCliente.getText() + "%'";

        modelo = new DefaultTableModel(null, titulos);

        conexionBD cnp = new conexionBD();
        Connection cnn = (Connection) cnp.ConectarBD();
        com.mysql.jdbc.Statement st;
        try {

            st = (com.mysql.jdbc.Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);

            while (rst.next()) {
                registros[0] = rst.getString("idcliente");
                registros[1] = rst.getString("nombre");
                registros[2] = rst.getString("apellido");
                registros[3] = rst.getString("cinro");
                registros[4] = rst.getString("telefono");
                registros[5] = rst.getString("direccion");

                modelo.addRow(registros);
            }
            tablacliente.setModel(modelo);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void verclientes() {
        try {
            com.mysql.jdbc.Statement consultamarca = (com.mysql.jdbc.Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultamarca.executeQuery("select idcliente, nombre, apellido, cinro, telefono, direccion from cliente");

            modelo = new DefaultTableModel();
            tablacliente.setModel(modelo);

            modelo.addColumn("Codigo");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("C.I.N°");
            modelo.addColumn("Telefono");
            modelo.addColumn("Direccion");

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

    public void seleccionartablacliente() {

        // DefaultTableModel tabla = (DefaultTableModel) this.tablaCliente.getModel();
        int c = tablacliente.getSelectedRow();

        if (c == -1) {
            System.out.println("Seleccione un registro");
        } else {
            Integer id = Integer.parseInt(tablacliente.getValueAt(c, 0).toString());
            String nombre = (String) tablacliente.getValueAt(c, 1);
            String apellido = (String) tablacliente.getValueAt(c, 2);
            String cin = (String) tablacliente.getValueAt(c, 3);
            String telefono = (String) tablacliente.getValueAt(c, 4);
            String direccion = (String) tablacliente.getValueAt(c, 5);

            //  this.txtIdcliente.setText(id);
            txtIdcliente.setText(Integer.toString(id));

            this.txtnombre.setText(nombre);
            this.txtApellido.setText(apellido);
            this.txtCin.setText(cin);
            this.txtTelef.setText(telefono);
            this.txtDireccion.setText(direccion);

        }
    }

    public void cargarproductobuscar() {
        String[] titulos = {"Codigo", "Precio Venta", "Descripcion", "IVA", "Existencia", "categoria"};
        String[] registros = new String[6];
        String sql = null;
        sql = "select idproducto,preciodeventa,descripcion,stock, idcategoria";
        sql += ",iva";
        sql += " from productos";
        sql += " where descripcion LIKE '%" + txtBuscarProdu.getText() + "%'";
        //System.out.println(sql);

        DefaultTableModel tabla = new DefaultTableModel(null, titulos);
        //tabla = new DefaultTableModel(null, titulos);

        conexionBD cnb = new conexionBD();
        Connection cnn = (Connection) cnb.ConectarBD();
        Statement st;
        try {

            st = (Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);

            while (rst.next()) {
                registros[0] = rst.getString("idproducto");
                registros[1] = rst.getString("preciodeventa");
                registros[2] = rst.getString("descripcion");
                registros[3] = rst.getString("iva");
                registros[4] = rst.getString("stock");
                registros[4] = rst.getString("idcategoria");
                tabla.addRow(registros);
            }
            tablaProducto.setModel(tabla);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cargarproducto(String valor) {
        String[] titulos = {"Codigo", "Precio Venta", "Descripcion", "IVA", "Existencia", "Categoria", "Marca"};
        String[] registros = new String[7];
        String sql = null;
        sql = "select idproducto,preciodeventa,descripcion,stock, idcategoria, idmarca";
        sql += ",iva";
        sql += " from productos";
        sql += " where idproducto=idproducto";

        DefaultTableModel m = new DefaultTableModel(null, titulos);

        conexionBD cn = new conexionBD();
        Connection cnn = (Connection) cn.ConectarBD();
        Statement st;
        try {

            st = (Statement) cnn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                registros[0] = rs.getString("idproducto");
                registros[1] = rs.getString("preciodeventa");
                registros[2] = rs.getString("descripcion");
                registros[3] = rs.getString("iva");
                registros[4] = rs.getString("stock");
                registros[5] = rs.getString("idcategoria");
                registros[6] = rs.getString("idmarca");

                m.addRow(registros);
            }
            tablaProducto.setModel(m);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void seleccionartablaproducto() {

        //DefaultTableModel tabla = (DefaultTableModel) this.tablacliente.getModel();
        int c = tablaProducto.getSelectedRow();

        if (c == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un registro");
        } else {
            // String id = (String) tablaProducto.getValueAt(c, 0);
            Integer id = Integer.parseInt(tablaProducto.getValueAt(c, 0).toString());
            Integer precioventa = Integer.parseInt(tablaProducto.getValueAt(c, 1).toString());
            //String precioventa = (String) tablaProducto.getValueAt(c, 1);
            String descripcion = (String) tablaProducto.getValueAt(c, 2);
            // String marca = (String) tablaProducto.getValueAt(c, 3);
            Integer iva = Integer.parseInt(tablaProducto.getValueAt(c, 3).toString());
            Integer stock = Integer.parseInt(tablaProducto.getValueAt(c, 4).toString());
            Integer categoria = Integer.parseInt(tablaProducto.getValueAt(c, 5).toString());
            Integer marca = Integer.parseInt(tablaProducto.getValueAt(c, 6).toString());
            txtStock.setText(Integer.toString(stock));
            txtCodigo.setText(Integer.toString(id));
            // this.txtCodigo.setText(id);
            //this.txtPrecioVenta.setText(precioventa);
            txtPrecioVenta.setText(Integer.toString(precioventa));
            this.txtDescripcion.setText(descripcion);
            txtMarca.setText(Integer.toString(marca));
            txtIVA.setText(Integer.toString(iva));
            txtCategoria.setText(Integer.toString(categoria));
        }
    }

    public void cargarproductobuscarDoss() {

        String sql = null;
        sql = "select p.idproducto,preciodeventa,p.descripcion as producto,stock,iva,m.descripcion as descripcion,dp.idmedida,cantidad";
        sql += " from productos as p";
        sql += " inner join detalleprecio as dp on dp.idproducto=p.idproducto";
        sql += " inner join medida as m on m.idmedida=dp.idmedida";
        sql += " where p.idproducto='" + txtCodigo.getText() + "'";
         //System.out.println(sql);

        //tabla = new DefaultTableModel(null, titulos);
        conexionBD cnb = new conexionBD();
        Connection cnn = (Connection) cnb.ConectarBD();
        Statement st;
        try {

            st = (Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);

            while (rst.next()) {
                this.txtDescripcion.setText(rst.getString("producto"));
                //this.txtPrecioVenta.setText(rst.getString("preciodeventa")); 
                this.txtStock.setText(rst.getString("stock"));
                this.txtIVA.setText(rst.getString("iva"));

                medida medida = new medida();
                medida.setCodigo(Integer.valueOf(rst.getString("idmedida")));
                medida.setDescripcion(rst.getString("descripcion"));
                comboMedida.addItem(medida);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cargarproductobuscarDos() {

        String sql = null;
        sql = "select idproducto,preciodeventa,descripcion,stock";
        sql += ",iva";
        sql += " from productos";
        sql += " where idproducto='" + txtCodigo.getText() + "'";
         //System.out.println(sql);

        //tabla = new DefaultTableModel(null, titulos);
        conexionBD cnb = new conexionBD();
        Connection cnn = (Connection) cnb.ConectarBD();
        Statement st;
        try {

            st = (Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);

            while (rst.next()) {
                this.txtDescripcion.setText(rst.getString("descripcion"));
                //this.txtPrecioVenta.setText(rst.getString("preciodeventa")); 
                this.txtStock.setText(rst.getString("stock"));
                this.txtIVA.setText(rst.getString("iva"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cargarproductobuscardescripcion() {

        String sql = null;
        sql = "select idproducto,preciodeventa,descripcion,stock";
        sql += ",iva";
        sql += " from productos";
        sql += " where descripcion LIKE '%" + txtDescripcion.getText() + "%'";
         //System.out.println(sql);

        //tabla = new DefaultTableModel(null, titulos);
        conexionBD cnb = new conexionBD();
        Connection cnn = (Connection) cnb.ConectarBD();
        Statement st;
        try {

            st = (Statement) cnn.createStatement();
            ResultSet rst = st.executeQuery(sql);

            while (rst.next()) {
                this.txtCodigo.setText(rst.getString("idproducto"));
                this.txtDescripcion.setText(rst.getString("descripcion"));
                this.txtPrecioVenta.setText(rst.getString("preciodeventa"));
                this.txtStock.setText(rst.getString("stock"));
                this.txtIVA.setText(rst.getString("iva"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    /* void habilitarcliente(){
     if(txtnombre2.getText().isEmpty()){
     buscarProdu.setEnabled(false);
     }
     else{buscarProdu.setEnabled(true);
     }
     }*/
    public void habilitarguardar() {
        int x = tablafactura.getRowCount();

        if (tablafactura.getRowCount() != 0) {
            btnCredito.setEnabled(true);
            btnGuardar.setEnabled(true);
            btnQuitar.setEnabled(true);
        } else {
            btnCredito.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnQuitar.setEnabled(false);
        }
    }

    void verproducto() {
        try {
            Statement consultamarca = (Statement) conexionBD.ConectarBD().createStatement();
            ResultSet rs = consultamarca.executeQuery("select idproducto,preciodeventa,descripcion,iva,stock  from productos order by idproducto");

            modelo = new DefaultTableModel();
            tablaProducto.setModel(modelo);

            modelo.addColumn("Codigo");
            modelo.addColumn("Precio Venta");
            modelo.addColumn("Descripcion");
            modelo.addColumn("IVA");
            modelo.addColumn("Existencia");

            filas = new Object[modelo.getColumnCount()];

            while (rs.next()) {
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    filas[i] = rs.getObject(i + 1);
                }
                modelo.addRow(filas);
            }

            tablaProducto.setModel(modelo);

        } catch (Exception e) {
            System.out.println("Error al mostrar datos en la tabla" + e.getMessage());
        }
    }

    void agregartablafactura() {

        //DefaultTableModel tabla = (DefaultTableModel) this.tablafactura.getModel();
        modeloo = (DefaultTableModel) this.tablafactura.getModel();

        int iva = tablafactura.getSelectedRow();
        iva = Integer.parseInt(txtIVA.getText().toString());
        System.out.println("iva" + iva);
        if (iva == 0) {
            Object[] valor = new Object[7];
            valor[0] = (String) txtCodigo.getText();
            valor[1] = (String) txtDescripcion.getText();
            valor[2] = (String) txtCantidad.getText();
            valor[3] = (String) txtPrecioVenta.getText();
            valor[4] = (String) txtTotal1.getText();
            valor[5] = (String) "0";
            valor[6] = (String) "0";

            //  tabla.addRow(valor);
        } else {
            if (iva == 5) {
                Object[] valor = new Object[7];
                valor[0] = (String) txtCodigo.getText();
                valor[1] = (String) txtDescripcion.getText();
                valor[2] = (String) txtCantidad.getText();
                valor[3] = (String) txtPrecioVenta.getText();
                valor[4] = (String) "0";
                valor[5] = (String) txtTotal1.getText();
                valor[6] = (String) "0";

                // tabla.addRow(valor);
            } else {
                if (iva == 10) {

                    Object[] valor = new Object[9];
                    valor[0] = (String) txtCodigo.getText();
                    valor[1] = (String) txtDescripcion.getText();
                    valor[2] = (String) txtCantidad.getText();
                    valor[3] = (String) txtPrecioVenta.getText();
                    valor[4] = (String) "0";
                    valor[5] = (String) "0";
                    valor[6] = (String) txtTotal1.getText();
                    valor[7] = (String) txtIDcodigo.getText();
                    valor[8] = (String) txtMulltipliacion.getText();

                    modeloo.addRow(valor);
                    tablafactura.setModel(modeloo);
                    //  tablafactura.removeColumn(tablafactura.getColumnModel().getColumn(8));

                }
            }
        }
    }

    void sumadetalbla() {
        double sumatoriaTotal = 0;
        int totalRow = tablafactura.getRowCount();
        totalRow -= 1;
        double totalIva10 = 0, totalIva5 = 0, z = 0;
        for (int i = 0; i <= (totalRow); i++) {
            //SUMA TOTAL IVA 10%
            double sumatoria = Double.parseDouble(String.valueOf(tablafactura.getValueAt(i, 6)));
            totalIva10 += sumatoria;
            txtDies.setText(String.valueOf(totalIva10));
            double iva10 = (totalIva10 * 10) / 110;
            double redondeo = ((long) (iva10 * 100.0)) / 100.0;
            txtIva10.setText(String.valueOf(redondeo));

            //SUMA TOTAL IVA 5%
            double sumatoria1 = Double.parseDouble(String.valueOf(tablafactura.getValueAt(i, 5)));
            totalIva5 += sumatoria1;
            txtCinco.setText(String.valueOf(totalIva5));
            txtExentas.setText(String.valueOf(totalIva5));
            double iva5 = (totalIva5 * 5) / 105;
            double redondeo2 = ((long) (iva5 * 100.0)) / 100.0;
            txtIva5.setText(String.valueOf(redondeo2));
            sumatoriaTotal += (sumatoria + sumatoria1);

            //SUMA TOTAL EXENTAS
//            double sumatoria2 = Double.parseDouble(String.valueOf(tablafactura.getValueAt(i, 4)));
//            z += sumatoria2;
//            //txtExentas.setText(String.valueOf(z));
//            sumatoriaTotal += (sumatoria + sumatoria1 + z);
            txtTotal.setText(String.valueOf(sumatoriaTotal));
            txtTotalDos.setText(String.valueOf(sumatoriaTotal));

            //SUMA PARA EL TOTAL DE IVA
            double iva5p = Double.parseDouble(String.valueOf(txtIva5.getText()));
            double iva10p = Double.parseDouble(String.valueOf(txtIva10.getText()));
            double sumaivap = iva5p + iva10p;
            txtTotalIva.setText(String.valueOf(sumaivap));

        }

    }

    void multiplicacion() {

        int n = Integer.valueOf(txtCantidadUnico.getText().toString());
        int m = Integer.valueOf(txtCantidad.getText().toString());
        int mul = n * m;
        txtMulltipliacion.setText(String.valueOf(mul));
    }

    public void limpiar() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtPrecioVenta.setText("");
        txtTotal1.setText("");

    }

    public void cargarcredito() {
        double valor = Double.parseDouble(String.valueOf(txtTotal.getText()));
        txtMontoPagar.setText(String.valueOf(valor));
        txtSaldo.setText(String.valueOf(valor));
    }
    /*   public void interes(){
     int saldototal, saldo, interes;
        
     saldo = Integer.parseInt(txtSaldo.getText().toString());
     interes = Integer.parseInt(txtInteres.getText().toString());
     saldototal =  saldo+( saldo*interes/100);
     txtSaldo.setText(String.valueOf(saldototal));
     }*/

    public void calcularplazo() {

        double plazo, saldo, total;
        saldo = Double.parseDouble(String.valueOf(txtSaldo.getText()));
        plazo = Double.parseDouble(String.valueOf(txtPlazo.getText()));
        total = saldo / plazo;
        txtMontoCuota.setText(String.valueOf(total));
    }

    public boolean validardatos() {
        vacio = false;
        if (txtPlazo.getText().isEmpty()) {
            vacio = true;
        }
        if (txtEntrega.getText().isEmpty()) {
            vacio = true;
        }
        return vacio;
    }

    /*  public void num_letras() throws IOException{
     double numero1 = Double.parseDouble(String.valueOf(txtTotal.getText()));//agarramis el valor del objeto y lo convertimos en entero  
     System.out.println(numero1);
     n2t numero = new n2t((int) numero1); //instanciamos la clase n2t a lavariable numero
     txtTotalLetras.setText(numero.convertirLetras((int) numero1)); //luego nos devuelve el valor en letras del numero en res		
     //txtTotalLetras.setText(res);       
     }*/
    public void calcularvuelto() {
        double total, efectivo, vuelto;
        total = Double.parseDouble(String.valueOf(txtTotalDos.getText()));
        efectivo = Double.parseDouble(String.valueOf(txtEfectivo.getText()));
        vuelto = efectivo - total;
        txtVuelto.setText(String.valueOf(vuelto));

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
        buscarcliente = new javax.swing.JDialog();
        txtBuscarCliente = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablacliente = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        buscarproductos = new javax.swing.JDialog();
        txtBuscarProdu = new javax.swing.JTextField();
        btnAceotarProdu = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProducto = new javax.swing.JTable();
        jLabel42 = new javax.swing.JLabel();
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
        txtInteres = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        fecha1 = new com.toedter.calendar.JDateChooser();
        jLabel49 = new javax.swing.JLabel();
        txtMontoPagarInteres = new javax.swing.JTextField();
        ventanafinal = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        txtTotalDos = new javax.swing.JTextField();
        txtEfectivo = new javax.swing.JTextField();
        txtVuelto = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        btnGuardarDos = new javax.swing.JButton();
        btnSalirDos = new javax.swing.JButton();
        tablaDetalleFactura = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablafacturaVista = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        FacturaImprimir = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtNumFac = new javax.swing.JTextField();
        txtidfac = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTelef = new javax.swing.JTextField();
        txtCin = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        btnCredito = new javax.swing.JRadioButton();
        btnContado = new javax.swing.JRadioButton();
        txtExentas = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        txtDies = new javax.swing.JTextField();
        txtCinco = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtIva5 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtIva10 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtTotalIva = new javax.swing.JTextField();
        txtIVA = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtIdcliente = new javax.swing.JTextField();
        txtTotal1 = new javax.swing.JTextField();
        buscarProdu = new javax.swing.JButton();
        txtPrecioVenta = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JLabel();
        txtMarca = new javax.swing.JLabel();
        comboMedida = new javax.swing.JComboBox();
        txtIDcodigo = new javax.swing.JLabel();
        txtCantidadUnico = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablafactura = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnCheque = new javax.swing.JRadioButton();
        btnLimpiar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        AgregarProducto = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        txtMulltipliacion = new javax.swing.JLabel();

        buscarcliente.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscarCliente.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
        buscarcliente.getContentPane().add(txtBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 178, -1));

        jButton2.setText("Aceptar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        buscarcliente.getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, -1, -1));

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

        buscarcliente.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 465, 176));

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buscar.png"))); // NOI18N
        buscarcliente.getContentPane().add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 30, 50));

        jLabel18.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 255));
        jLabel18.setText("Introducir Nº C.I .");
        buscarcliente.getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        buscarproductos.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscarProdu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtBuscarProdu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarProduActionPerformed(evt);
            }
        });
        txtBuscarProdu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProduKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarProduKeyTyped(evt);
            }
        });
        buscarproductos.getContentPane().add(txtBuscarProdu, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 38, 197, 30));

        btnAceotarProdu.setText("Aceptar");
        btnAceotarProdu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceotarProduActionPerformed(evt);
            }
        });
        buscarproductos.getContentPane().add(btnAceotarProdu, new org.netbeans.lib.awtextra.AbsoluteConstraints(243, 42, -1, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/buscar.png"))); // NOI18N
        buscarproductos.getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 28, -1, 40));

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

        buscarproductos.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 79, -1, 278));

        jLabel42.setFont(new java.awt.Font("Times New Roman", 2, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 153, 255));
        jLabel42.setText("Introducir Precio de Venta");
        buscarproductos.getContentPane().add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, 20));

        credito.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("FreeMono", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 204, 0));
        jLabel30.setText("FACTURACREDITO");
        credito.getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));

        jLabel31.setText("PLAZO:");
        credito.getContentPane().add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        txtPlazo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPlazoFocusLost(evt);
            }
        });
        txtPlazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlazoActionPerformed(evt);
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
        credito.getContentPane().add(txtPlazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 60, -1));

        jLabel32.setText("Saldo:");
        credito.getContentPane().add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, -1, -1));

        txtSaldo.setText("0");
        txtSaldo.setEnabled(false);
        credito.getContentPane().add(txtSaldo, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 119, 30));

        jLabel33.setText("Monto de la Cuota:");
        credito.getContentPane().add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, -1));

        jLabel35.setText("Fecha Vence");
        credito.getContentPane().add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, -1, -1));

        jLabel36.setText("Entrega Inicial:");
        credito.getContentPane().add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        txtEntrega.setText("0");
        txtEntrega.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEntregaFocusLost(evt);
            }
        });
        txtEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEntregaActionPerformed(evt);
            }
        });
        txtEntrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEntregaKeyTyped(evt);
            }
        });
        credito.getContentPane().add(txtEntrega, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 120, -1));

        btnGuardarCredito.setText("Guardar");
        btnGuardarCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCreditoActionPerformed(evt);
            }
        });
        credito.getContentPane().add(btnGuardarCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 170, 90, -1));

        jLabel37.setFont(new java.awt.Font("DejaVu Sans", 2, 13)); // NOI18N
        jLabel37.setText("Monto a Pagar Sin Interes:");
        credito.getContentPane().add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtMontoPagar.setEnabled(false);
        credito.getContentPane().add(txtMontoPagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 100, 30));

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
        credito.getContentPane().add(txtMontoCuota, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 120, -1));
        credito.getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 400, 10));

        txtInteres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInteresActionPerformed(evt);
            }
        });
        credito.getContentPane().add(txtInteres, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 130, 50, 20));

        jLabel48.setText("Interers:");
        credito.getContentPane().add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, -1, -1));

        jLabel39.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        credito.getContentPane().add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 280, 210));

        jLabel40.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        credito.getContentPane().add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 90, 110));
        credito.getContentPane().add(fecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, -1, -1));

        jLabel49.setFont(new java.awt.Font("Tahoma", 2, 13)); // NOI18N
        jLabel49.setText("Monto a Pagar con Interes:");
        credito.getContentPane().add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 160, 20));

        txtMontoPagarInteres.setEnabled(false);
        credito.getContentPane().add(txtMontoPagarInteres, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 100, 30));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtTotalDos.setEditable(false);
        txtTotalDos.setBackground(new java.awt.Color(102, 255, 0));
        txtTotalDos.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtTotalDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalDosActionPerformed(evt);
            }
        });

        txtEfectivo.setBackground(new java.awt.Color(51, 255, 0));
        txtEfectivo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEfectivoActionPerformed(evt);
            }
        });

        txtVuelto.setEditable(false);
        txtVuelto.setBackground(new java.awt.Color(51, 255, 0));
        txtVuelto.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel54.setText("VUELTO:");

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel53.setText("EFECTIVO:");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel52.setText("TOTAL A PAGAR");

        btnGuardarDos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/articulos.png"))); // NOI18N
        btnGuardarDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarDosActionPerformed(evt);
            }
        });

        btnSalirDos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/equis.png"))); // NOI18N
        btnSalirDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirDosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 373, Short.MAX_VALUE)
                        .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSalirDos, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btnGuardarDos, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTotalDos, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel52)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jLabel53)))
                    .addContainerGap(689, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(txtTotalDos, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtEfectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(99, 99, 99))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtVuelto, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSalirDos)
                            .addComponent(btnGuardarDos))
                        .addContainerGap())))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addComponent(jLabel52)
                    .addGap(76, 76, 76)
                    .addComponent(jLabel53)
                    .addContainerGap(203, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout ventanafinalLayout = new javax.swing.GroupLayout(ventanafinal.getContentPane());
        ventanafinal.getContentPane().setLayout(ventanafinalLayout);
        ventanafinalLayout.setHorizontalGroup(
            ventanafinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanafinalLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );
        ventanafinalLayout.setVerticalGroup(
            ventanafinalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ventanafinalLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tablafacturaVista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Precio Unitario", "Excentas", "5%", "10%"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablafacturaVista.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tablafacturaVista);
        if (tablafacturaVista.getColumnModel().getColumnCount() > 0) {
            tablafacturaVista.getColumnModel().getColumn(0).setMaxWidth(50);
            tablafacturaVista.getColumnModel().getColumn(2).setMaxWidth(60);
        }

        javax.swing.GroupLayout tablaDetalleFacturaLayout = new javax.swing.GroupLayout(tablaDetalleFactura.getContentPane());
        tablaDetalleFactura.getContentPane().setLayout(tablaDetalleFacturaLayout);
        tablaDetalleFacturaLayout.setHorizontalGroup(
            tablaDetalleFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1159, Short.MAX_VALUE)
            .addGroup(tablaDetalleFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tablaDetalleFacturaLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        tablaDetalleFacturaLayout.setVerticalGroup(
            tablaDetalleFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 533, Short.MAX_VALUE)
            .addGroup(tablaDetalleFacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tablaDetalleFacturaLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/articulos.png"))); // NOI18N
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 520, 90, 50));

        FacturaImprimir.setBackground(new java.awt.Color(255, 255, 255));
        FacturaImprimir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FacturaImprimir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setText("Fecha de Emision:");
        FacturaImprimir.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        fecha.setText("dd/mm/yy");
        FacturaImprimir.add(fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 80, 30));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel1.setText("N°");
        FacturaImprimir.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, 30, 20));

        txtNumFac.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNumFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumFacActionPerformed(evt);
            }
        });
        txtNumFac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumFacKeyTyped(evt);
            }
        });
        FacturaImprimir.add(txtNumFac, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, 110, 30));
        FacturaImprimir.add(txtidfac, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 50, 30));

        jLabel14.setText("RUC o C.I.N.:");
        FacturaImprimir.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, -1, -1));

        jLabel8.setText("Telef:");
        FacturaImprimir.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, -1, -1));

        txtTelef.setEnabled(false);
        FacturaImprimir.add(txtTelef, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 130, 20));

        txtCin.setEnabled(false);
        FacturaImprimir.add(txtCin, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 110, -1));

        txtApellido.setEnabled(false);
        FacturaImprimir.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 360, -1));

        jLabel17.setText("Direccion:");
        FacturaImprimir.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, -1, -1));

        txtDireccion.setEnabled(false);
        FacturaImprimir.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 470, -1));

        jLabel19.setText("Forma de Pago");
        FacturaImprimir.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, -1, -1));

        btnCredito.setBackground(new java.awt.Color(255, 255, 255));
        Group1.add(btnCredito);
        btnCredito.setText("Credito");
        btnCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreditoActionPerformed(evt);
            }
        });
        FacturaImprimir.add(btnCredito, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, -1, -1));

        btnContado.setBackground(new java.awt.Color(255, 255, 255));
        Group1.add(btnContado);
        btnContado.setText("Contado");
        FacturaImprimir.add(btnContado, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 160, -1, -1));

        txtExentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtExentas.setEnabled(false);
        FacturaImprimir.add(txtExentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 590, 110, 30));

        jLabel12.setText("Excentas");
        FacturaImprimir.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 620, -1, -1));

        jLabel20.setText("I.V.A.5%");
        FacturaImprimir.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 620, 60, -1));

        jLabel9.setText("I.V.A.10%");
        FacturaImprimir.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 620, -1, -1));

        txtTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTotal.setEnabled(false);
        FacturaImprimir.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 640, 210, 30));

        txtDies.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDies.setEnabled(false);
        FacturaImprimir.add(txtDies, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 590, 110, 30));

        txtCinco.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtCinco.setEnabled(false);
        FacturaImprimir.add(txtCinco, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 590, 120, 30));

        jLabel13.setText("TOTAL A PAGAR:");
        FacturaImprimir.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 650, -1, -1));

        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FacturaImprimir.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 570, 360, 110));

        txtIva5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIva5.setEnabled(false);
        FacturaImprimir.add(txtIva5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 660, 70, -1));

        jLabel16.setText("(5%)");
        FacturaImprimir.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 660, -1, -1));

        jLabel10.setText("Liquidacion del IVA%:");
        FacturaImprimir.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 660, -1, -1));

        txtIva10.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtIva10.setEnabled(false);
        FacturaImprimir.add(txtIva10, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 660, 70, -1));

        jLabel11.setText("(10%)");
        FacturaImprimir.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 660, -1, -1));

        jLabel29.setText("Total IVA:");
        FacturaImprimir.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 660, 60, -1));

        txtTotalIva.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTotalIva.setEnabled(false);
        FacturaImprimir.add(txtTotalIva, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 660, 140, -1));

        txtIVA.setEnabled(false);
        txtIVA.setOpaque(true);
        FacturaImprimir.add(txtIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 670, 70, -1));

        jLabel43.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        FacturaImprimir.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 250, 140));

        jLabel22.setText("Cliente:");
        FacturaImprimir.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, -1));

        txtnombre.setEnabled(false);
        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });
        FacturaImprimir.add(txtnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 100, 20));
        FacturaImprimir.add(txtIdcliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 30, -1));

        txtTotal1.setEnabled(false);
        FacturaImprimir.add(txtTotal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 310, 120, -1));

        buscarProdu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/nuevacoompra.png"))); // NOI18N
        buscarProdu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarProduActionPerformed(evt);
            }
        });
        FacturaImprimir.add(buscarProdu, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 300, 40, 30));

        txtPrecioVenta.setEnabled(false);
        FacturaImprimir.add(txtPrecioVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 310, 120, 20));

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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        FacturaImprimir.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 310, 120, -1));

        txtDescripcion.setEnabled(false);
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        FacturaImprimir.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 130, 20));

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
        FacturaImprimir.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 120, -1));

        jLabel25.setText("Codigo");
        FacturaImprimir.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));

        jLabel26.setText("Descripcion");
        FacturaImprimir.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, -1, -1));

        jLabel27.setText("Cantidad");
        FacturaImprimir.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, -1, -1));

        jLabel28.setText("Precio Venta");
        FacturaImprimir.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 290, -1, -1));

        jLabel24.setText("Total");
        FacturaImprimir.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 290, -1, -1));
        FacturaImprimir.add(txtCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 200, 30, 20));
        FacturaImprimir.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 230, 60, 20));

        comboMedida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboMedidaMouseClicked(evt);
            }
        });
        comboMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMedidaActionPerformed(evt);
            }
        });
        FacturaImprimir.add(comboMedida, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, 110, -1));
        FacturaImprimir.add(txtIDcodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, 40, 20));
        FacturaImprimir.add(txtCantidadUnico, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, 90, 20));

        tablafactura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Precio Unitario", "Excentas", "5%", "10%", "codigopackun", "Cantidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablafactura.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tablafactura);
        if (tablafactura.getColumnModel().getColumnCount() > 0) {
            tablafactura.getColumnModel().getColumn(0).setMaxWidth(50);
            tablafactura.getColumnModel().getColumn(0).setHeaderValue("Codigo");
            tablafactura.getColumnModel().getColumn(1).setHeaderValue("Descripcion");
            tablafactura.getColumnModel().getColumn(2).setMaxWidth(60);
            tablafactura.getColumnModel().getColumn(2).setHeaderValue("Cantidad");
            tablafactura.getColumnModel().getColumn(3).setHeaderValue("Precio Unitario");
            tablafactura.getColumnModel().getColumn(4).setHeaderValue("Excentas");
            tablafactura.getColumnModel().getColumn(5).setHeaderValue("5%");
            tablafactura.getColumnModel().getColumn(6).setHeaderValue("10%");
            tablafactura.getColumnModel().getColumn(7).setHeaderValue("codigopackun");
            tablafactura.getColumnModel().getColumn(8).setHeaderValue("Cantidad");
        }

        FacturaImprimir.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 920, 230));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        FacturaImprimir.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 250, -1, -1));

        jLabel2.setText("Medida");
        FacturaImprimir.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 290, -1, -1));

        Group1.add(btnCheque);
        btnCheque.setText("cheuqe");
        FacturaImprimir.add(btnCheque, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 160, -1, -1));

        getContentPane().add(FacturaImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 920, 680));

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clear.png"))); // NOI18N
        btnLimpiar.setEnabled(false);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 580, 90, 40));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/equis.png"))); // NOI18N
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 630, 90, 40));

        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/basurero.png"))); // NOI18N
        btnQuitar.setEnabled(false);
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        getContentPane().add(btnQuitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 470, 90, 40));

        btnImprimir.setText("imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimir(evt);
            }
        });
        getContentPane().add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 320, -1, -1));

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/clientes.png"))); // NOI18N
        btnBuscar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 210, 50, 40));

        AgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/entrada.png"))); // NOI18N
        AgregarProducto.setEnabled(false);
        AgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarProductoActionPerformed(evt);
            }
        });
        getContentPane().add(AgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 420, 90, 40));

        jLabel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        getContentPane().add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 410, 120, 280));
        getContentPane().add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 0, 30, -1));
        getContentPane().add(txtMulltipliacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 110, 50, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarcliente.setModal(true);
        buscarcliente.setSize(450, 300);
        buscarcliente.setLocationRelativeTo(this);
        buscarcliente.setVisible(true);
        //   habilitarbotones(true);
        //   btnLimpiar.setEnabled(true);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtBuscarClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClienteKeyReleased
        if (txtBuscarCliente.getText().isEmpty()) {
            cargarclientes("");
        } else {
            cargarclinetesbuscar("");
        }
    }//GEN-LAST:event_txtBuscarClienteKeyReleased

    private void tablaclienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaclienteKeyReleased
        seleccionartablacliente();
    }//GEN-LAST:event_tablaclienteKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.buscarcliente.dispose();
        habilitarbotones(true);
        btnLimpiar.setEnabled(true);
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
        if (txtBuscarProdu.getText().isEmpty()) {
            cargarproducto("");
        } else {
            cargarproductobuscar();
        }
    }//GEN-LAST:event_txtBuscarProduKeyReleased

    private void btnAceotarProduActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceotarProduActionPerformed
        this.buscarproductos.dispose();
        txtCantidad.setText("1");
        int precioventa, cantidad, total;

        cantidad = Integer.parseInt(txtCantidad.getText().toString());
        precioventa = Integer.parseInt(txtPrecioVenta.getText().toString());
        total = cantidad * precioventa;
        txtTotal1.setText(String.valueOf(total));

    }//GEN-LAST:event_btnAceotarProduActionPerformed

    private void txtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusLost


    }//GEN-LAST:event_txtCantidadFocusLost

    private void AgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarProductoActionPerformed
        int stock, cantidad;
        stock = Integer.parseInt(txtStock.getText().toString());
        System.out.println(stock);
        cantidad = Integer.parseInt(txtCantidad.getText().toString());
        System.out.println(cantidad);
        if (cantidad <= stock) {
            agregartablafactura();
            sumadetalbla();
            limpiar();
            habilitarbotones2(true);
            habilitarbotonestg(false);
            btnQuitar.setEnabled(true);
            /*try {
             num_letras();
             } catch (IOException ex) {
             Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
             }*/
        } else {
            if (cantidad > stock) {
                JOptionPane.showMessageDialog(null, "Baja existencia del Producto");
            }
        }


    }//GEN-LAST:event_AgregarProductoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        ventanafinal.setModal(true);
        ventanafinal.setSize(925, 380);
        ventanafinal.setLocationRelativeTo(this);
        ventanafinal.setVisible(true);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreditoActionPerformed
        cargarcredito();
        credito.setSize(500, 350);
        credito.setLocationRelativeTo(this);
        credito.setModal(true);
        credito.setVisible(true);

    }//GEN-LAST:event_btnCreditoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarcamposcabezara();
        habilitarguardar();
        habilitarbotones(false);
        habilitarbotones2(false);
    }//GEN-LAST:event_btnLimpiarActionPerformed

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

        if (txtCantidad.getText().isEmpty() || txtPrecioVenta.getText().isEmpty()) {
            //txtCantidad.setText("0"); 
        } else {

            int precioventa, cantidad, total;
            cantidad = Integer.parseInt(txtCantidad.getText().toString());
            precioventa = Integer.parseInt(txtPrecioVenta.getText().toString());
            total = cantidad * precioventa;
            txtTotal1.setText(String.valueOf(total));
        }

    }//GEN-LAST:event_txtCantidadKeyReleased

    private void txtEntregaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEntregaFocusLost
        // TODO add your handling code here:
        double entrega, montoapagar, total;
        montoapagar = Double.parseDouble(String.valueOf(txtMontoPagarInteres.getText()));
        entrega = Double.parseDouble(String.valueOf(txtEntrega.getText()));
        total = montoapagar - entrega;
        txtSaldo.setText(String.valueOf(total));

        calcularplazo();


    }//GEN-LAST:event_txtEntregaFocusLost


    private void txtEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEntregaActionPerformed

    }//GEN-LAST:event_txtEntregaActionPerformed

    private void txtPlazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlazoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlazoActionPerformed

    private void txtPlazoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlazoFocusLost

    }//GEN-LAST:event_txtPlazoFocusLost

    private void btnGuardarCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCreditoActionPerformed

        try {
            if (btnCredito.isSelected()) {

                try {

                    abm.start();

                    int idempleado = abm.idUsuario;

                    rs = abm.nuevo("idfacturaventa", "FacturaVenta");
                    rs.first();
                    txtidfac.setText(String.valueOf(rs.getInt("codigo") + 1));

                    FechaVence = fecha1.getCalendar();
                    FechaVence.setTime(fecha1.getDate());
                    String dia = String.valueOf(FechaVence.get(Calendar.DATE));
                    String mes = String.valueOf(FechaVence.get(Calendar.MONTH) + 1);
                    String anio = String.valueOf(FechaVence.get(Calendar.YEAR));
                    String date = anio + "/" + mes + "/" + dia;

                    int iva;
                    iva = Integer.parseInt(txtIVA.getText().toString());
                    if ((iva == 10) || (iva == 5) || (iva == 0)) {
                        String anulado = "0", estado = "0", condicion = "Credito";

                        v_control = abm.insertar("FacturaVenta", txtidfac.getText() + "," + txtNumFac.getText() + ",'"
                                + fecha.getText() + "','" + estado + "','" + anulado + "'," + txtIdcliente.getText() + "," + txtTotalIva.getText()
                                + ",'" + condicion + "'," + idempleado + "," + txtDies.getText() + "," + txtCinco.getText() + "," + txtExentas.getText() + "," + txtPlazo.getText() + "," + txtSaldo.getText() + "," + txtMontoCuota.getText() + "," + txtEntrega.getText() + ",'" + anio + "/" + mes + "/" + dia + "'");
                        System.out.println("despues del print");
                    }

                    if (v_control == false) {
                        abm.roolback();
                    }

                    if (v_control == true) {

                        for (int i = 0; i < tablafactura.getRowCount(); i++) {
                            Statement consulta = (Statement) conexionBD.ConectarBD().createStatement();
                            consulta.execute("update productos set stock=stock-" + tablafactura.getValueAt(i, 2)
                                    + " where idproducto=" + tablafactura.getValueAt(i, 0));
                            if ((iva == 10) || (iva == 5) || (iva == 0)) {
                                v_control = abm.insertar("DetalleVenta", "'" + tablafactura.getValueAt(i, 0) + "','"
                                        + txtidfac.getText() + "','" + tablafactura.getValueAt(i, 3) + "','"
                                        + tablafactura.getValueAt(i, 2) + "','" + tablafactura.getValueAt(i, 6) + "','" + tablafactura.getValueAt(i, 5) + "','" + tablafactura.getValueAt(i, 4) + "'");
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
            rs = abm.nuevo("numerofactura", "FacturaVenta");
            rs.first();
            txtNumFac.setText(String.valueOf(rs.getInt("codigo") + 1));
            this.credito.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        //v_control = abm.insertar("EntregaInicial", txtIdFacCredito.getText+","+ txtPlazo.getText() + "," + txtSaldo.getText() +","+ txtMontoCuota.getText()+"," + txtEntrega.getText() + ",'" + fecha1.getText()+"',"+ txtidfac.getText());                   

    }//GEN-LAST:event_btnGuardarCreditoActionPerformed

    private void btnCancelarCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCreditoActionPerformed
        txtPlazo.setText("0");
        txtMontoCuota.setText("0");
        txtEntrega.setText("0");
        this.credito.dispose();
        btnContado.setSelected(true);
    }//GEN-LAST:event_btnCancelarCreditoActionPerformed

    private void txtPlazoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlazoKeyReleased
        double entrega, montoapagar, total;
        montoapagar = Double.parseDouble(String.valueOf(txtMontoPagarInteres.getText()));
        entrega = Double.parseDouble(String.valueOf(txtEntrega.getText()));
        total = montoapagar - entrega;
        txtSaldo.setText(String.valueOf(total));

        calcularplazo();

    }//GEN-LAST:event_txtPlazoKeyReleased

private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
    char caracter = evt.getKeyChar();
    if ((caracter < '0' || (caracter > '9')) && (caracter != '\b')) {
        evt.consume();
    }


}//GEN-LAST:event_txtCantidadKeyTyped

private void txtPlazoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlazoKeyTyped
    char caracter = evt.getKeyChar();
    if ((caracter < '0' || (caracter > '9')) && (caracter != '\b')) {
        evt.consume();
    }
}//GEN-LAST:event_txtPlazoKeyTyped

private void txtEntregaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEntregaKeyTyped
    char caracter = evt.getKeyChar();
    if ((caracter < '0' || (caracter > '9')) && (caracter != '\b')) {
        evt.consume();
    }
}//GEN-LAST:event_txtEntregaKeyTyped

    private void imprimir(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimir

//        impresion();
    }//GEN-LAST:event_imprimir

    /*public void impresion(){
     try{
     PrinterJob gap= PrinterJob.getPrinterJob();
     gap.setPrintable(this);
            
     boolean top= gap.printDialog();
     if(top){
     gap.print();
     }
     }
     catch(PrinterException print){
     JOptionPane.showMessageDialog(null,"Error de programa", "Error\n" + print, JOptionPane.INFORMATION_MESSAGE);
     }
     }*/

    private void txtNumFacKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumFacKeyTyped
        char caracter = evt.getKeyChar();
        if ((caracter < '0' || (caracter > '9')) && (caracter != '\b')) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumFacKeyTyped

    private void txtNumFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumFacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumFacActionPerformed

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreActionPerformed

    private void txtInteresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInteresActionPerformed
        // TODO add your handling code here:
        double totalfactura, totalfacturainteres, interes;
        //   double iva5p = Double.parseDouble(String.valueOf(txtIva5.getText()));
        totalfactura = Double.parseDouble(String.valueOf(txtMontoPagar.getText()));
        interes = Double.parseDouble(String.valueOf(txtInteres.getText()));
        totalfacturainteres = totalfactura + (totalfactura * interes / 100);
        txtMontoPagarInteres.setText(String.valueOf(totalfacturainteres));

    }//GEN-LAST:event_txtInteresActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
        if (txtCantidad.getText().isEmpty()) {
            txtCantidad.requestFocus();
            JOptionPane.showMessageDialog(null, "Ingrese la Cantidad");
        } else {
            if (txtPrecioVenta.getText().isEmpty() && txtTotal1.getText().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Debe elegir una Medida Y cargar la Cantidad");
                comboMedida.requestFocus();

            } else {

                if (txtTotal1.getText().isEmpty()) {
                    // JOptionPane.showMessageDialog(null, "Debe elegir una Medida Y cargar la Cantidad");
                    multiplicacion();
                } else {
                    int stock, cantidad;
                    stock = Integer.parseInt(txtStock.getText());
                    System.out.println(stock);
                    cantidad = Integer.parseInt(txtCantidad.getText());
                    System.out.println(cantidad);
                    multiplicacion();
                    if (cantidad <= stock) {
                        agregartablafactura();
                        sumadetalbla();
                        habilitarbotones2(true);
                        habilitarbotonestg(false);
                        btnQuitar.setEnabled(true);

                        limpiar();
                        /* try {
                         num_letras();
                         } catch (IOException ex) {
                         Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
                         }*/
                    } else {
                        if (cantidad > stock) {
                            JOptionPane.showMessageDialog(null, "Baja existencia del Producto");
                        }
                    }

                    txtCodigo.requestFocus();

                }

            }

        }


    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtBuscarProduKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProduKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarProduKeyTyped

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        // TODO add your handling code here:

        if (txtCodigo.getText().isEmpty()) {
            txtCodigo.requestFocus();
            JOptionPane.showMessageDialog(null, "Cargar Productos");
        } else {
//            cargarproductobuscarDos();
            //cargarproductobuscarDoss();
//            txtCantidad.requestFocus();
//            //  jcboxDetallePrecio();
//           // comboxMedida();
            if (txtDescripcion.getText().isEmpty()) {
                txtCodigo.requestFocus();
            } else {
                cargarproductobuscarDos();
                //cargarproductobuscarDoss();
                txtCantidad.requestFocus();
                comboxMedida();
            }

        }

    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
//        // TODO add your handling code here:
        cargarproductobuscarDos();

        if (txtDescripcion.getText().isEmpty()) {
            txtCodigo.requestFocus();
        } else {
            cargarproductobuscarDos();
            txtCantidad.requestFocus();
            //   jcboxDetallePrecio();
            comboxMedida();
        }
        //cargarproductobuscarDos();

    }//GEN-LAST:event_txtCodigoKeyReleased

    private void btnSalirDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirDosActionPerformed
        // TODO add your handling code here:
        ventanafinal.dispose();
    }//GEN-LAST:event_btnSalirDosActionPerformed

    private void btnGuardarDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarDosActionPerformed
        // TODO add your handling code here:
        try {
            if (btnContado.isSelected()) {

                try {

                    abm.start();

                    int idempleado = abm.idUsuario;

                    rs = abm.nuevo("idfacturaventa", "FacturaVenta");
                    rs.first();
                    txtidfac.setText(String.valueOf(rs.getInt("codigo") + 1));

                    int iva = Integer.parseInt(txtIVA.getText().toString());
                    if ((iva == 10) || (iva == 5) || (iva == 0)) {
                        String anulado = "0", estado = "0", condicion = "contado";
                        int plazo = 1, saldo = 0, montocuota = 0;
                        v_control = abm.insertar("FacturaVenta", txtidfac.getText() + "," + txtNumFac.getText() + ",'"
                                + fecha.getText() + "','" + estado + "','" + anulado + "'," + txtIdcliente.getText() + "," + txtTotalIva.getText()
                                + ",'" + condicion + "'," + idempleado + "," + txtDies.getText() + "," + txtCinco.getText() + "," + txtExentas.getText() + "," + plazo + "," + saldo + "," + txtTotal.getText() + ",'" + fecha.getText() + "'");
                        System.out.println("despues del print");
                    }

                    if (v_control == false) {
                        abm.roolback();
                    }

                    if (v_control == true) {

                        for (int i = 0; i < modeloo.getRowCount(); i++) {
                            //condicion si es pack de 12 o 6 o unidad

                            int medida = Integer.parseInt(modeloo.getValueAt(i, 7).toString());
                            System.out.println(medida + " categoria demierda");

                            if ((iva == 10) || (iva == 5) || (iva == 0)) {
                                Statement consulta = (Statement) conexionBD.ConectarBD().createStatement();
                                consulta.execute("update productos set stock=stock-" + modeloo.getValueAt(i, 8)
                                        + " where idproducto=" + modeloo.getValueAt(i, 0));
                                System.out.println(" categoria 1");
                            } else {
                                if (medida == 8) {

                                    int cantidad = Integer.parseInt(tablafactura.getValueAt(i, 2).toString());
                                    System.out.println(cantidad + " cantidad tabla ");
                                    int resta = cantidad * 12;
                                    System.out.println(resta + " cantidad a restar demierda");
                                    Statement question = (Statement) conexionBD.ConectarBD().createStatement();
                                    question.execute("update productos set stock=stock-" + resta
                                            + " where idproducto=" + tablafactura.getValueAt(i, 0));

                                    System.out.println(" categoria 2");
                                }
                            }

                            if ((iva == 10) || (iva == 5) || (iva == 0)) {
                                v_control = abm.insertar("DetalleVenta", "'" + tablafactura.getValueAt(i, 0) + "','"
                                        + txtidfac.getText() + "','" + tablafactura.getValueAt(i, 3) + "','"
                                        + tablafactura.getValueAt(i, 2) + "','" + tablafactura.getValueAt(i, 6) + "','" + tablafactura.getValueAt(i, 5) + "','" + tablafactura.getValueAt(i, 5) + "'");
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
//                                  impresion();
                } catch (Exception ex) {
                    System.out.println("Error al realizar la transacion " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Completar todos los campos para guardar");

                }

            }else{
                if(btnCheque.isSelected()){
                    System.out.println("hola mundo");
                }
            }
            habilitarbotones(true);
            habilitarbotonestg(false);
            habilitarbotones2(false);
            limpiarcamposcabezara();
            idfactura();
            rs = abm.nuevo("numerofactura", "FacturaVenta");
            rs.first();
            txtNumFac.setText(String.valueOf(rs.getInt("codigo") + 1));
            ventanafinal.dispose();
            rs = abm.consulta("*", "cliente");
            cargarclientedefault();
            txtCodigo.requestFocus();
            cargarproducto("");

        } catch (SQLException ex) {
            Logger.getLogger(FacturaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarDosActionPerformed

    private void txtEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEfectivoActionPerformed
        // TODO add your handling code here:
        calcularvuelto();
    }//GEN-LAST:event_txtEfectivoActionPerformed

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtCantidadKeyPressed

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
        cargarproductobuscardescripcion();
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void txtTotalDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalDosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalDosActionPerformed

    private void comboMedidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboMedidaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_comboMedidaMouseClicked

    private void comboMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMedidaActionPerformed
        // TODO add your handling code here:
//         precioCategoria();
        comboyo();
        precioCategoria();

    }//GEN-LAST:event_comboMedidaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(FacturaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturaVenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                FacturaVenta dialog = new FacturaVenta(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel FacturaImprimir;
    private javax.swing.ButtonGroup Group1;
    private javax.swing.JButton btnAceotarProdu;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelarCredito;
    private javax.swing.JRadioButton btnCheque;
    private javax.swing.JRadioButton btnContado;
    private javax.swing.JRadioButton btnCredito;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarCredito;
    private javax.swing.JButton btnGuardarDos;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalirDos;
    private javax.swing.JButton buscarProdu;
    private javax.swing.JDialog buscarcliente;
    private javax.swing.JDialog buscarproductos;
    private javax.swing.JComboBox comboMedida;
    private javax.swing.JDialog credito;
    private javax.swing.JLabel fecha;
    private com.toedter.calendar.JDateChooser fecha1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JDialog tablaDetalleFactura;
    private javax.swing.JTable tablaProducto;
    private javax.swing.JTable tablacliente;
    private javax.swing.JTable tablafactura;
    private javax.swing.JTable tablafacturaVista;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtBuscarCliente;
    private javax.swing.JTextField txtBuscarProdu;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JLabel txtCantidadUnico;
    private javax.swing.JLabel txtCategoria;
    private javax.swing.JTextField txtCin;
    private javax.swing.JTextField txtCinco;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDies;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEfectivo;
    private javax.swing.JTextField txtEntrega;
    private javax.swing.JTextField txtExentas;
    private javax.swing.JLabel txtIDcodigo;
    private javax.swing.JLabel txtIVA;
    private javax.swing.JLabel txtIdFacCredito;
    private javax.swing.JTextField txtIdcliente;
    private javax.swing.JTextField txtInteres;
    private javax.swing.JTextField txtIva10;
    private javax.swing.JTextField txtIva5;
    private javax.swing.JLabel txtMarca;
    private javax.swing.JTextField txtMontoCuota;
    private javax.swing.JTextField txtMontoPagar;
    private javax.swing.JTextField txtMontoPagarInteres;
    private javax.swing.JLabel txtMulltipliacion;
    private javax.swing.JTextField txtNumFac;
    private javax.swing.JTextField txtPlazo;
    private javax.swing.JTextField txtPrecioVenta;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtTelef;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotal1;
    private javax.swing.JTextField txtTotalDos;
    private javax.swing.JTextField txtTotalIva;
    private javax.swing.JTextField txtVuelto;
    private javax.swing.JTextField txtidfac;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JDialog ventanafinal;
    // End of variables declaration//GEN-END:variables

    @Override
    public int print(Graphics graf, PageFormat formatopagina, int numpag) throws PrinterException {

        if (numpag > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D hub = (Graphics2D) graf;
        hub.translate(formatopagina.getImageableX() + 8, formatopagina.getImageableY() + 20);
        hub.scale(0.6, 0.6);

        FacturaImprimir.printAll(graf);
        return PAGE_EXISTS;
    }
}
