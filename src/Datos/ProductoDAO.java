/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rasul
 */
public class ProductoDAO {

    Connection conexion;

    public ProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List cargarProductos(int idTienda) throws SQLException {
        Producto producto;
        PreparedStatement psProductos;
        ResultSet rsProductos;
        List<Producto> listaProductos = new ArrayList<>();
        psProductos = conexion.prepareStatement("SELECT * "
                + "FROM productos p INNER JOIN tiendas_productos tp INNER JOIN tiendas t "
                + "ON p.referencia=tp.idProducto AND tp.idTienda=t.idTienda "
                + "WHERE t.idTienda =?;");
        psProductos.setInt(1, idTienda);
        rsProductos = psProductos.executeQuery();
        while (rsProductos.next()) {
            producto = new Producto(rsProductos.getInt("referencia"),
                    rsProductos.getString("nombre"),
                    rsProductos.getString("categoria"),
                    rsProductos.getString("descripcion"),
                    rsProductos.getDouble("precioCompra"),
                    rsProductos.getDouble("precioVenta"),
                    rsProductos.getDouble("IVA"),
                    rsProductos.getInt("stock"));

            listaProductos.add(producto);
        }
        return listaProductos;

    }

    public int mostrarSiguienteReferencia() throws SQLException {
        String respuesta = "";

        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT MAX(referencia) AS 'referencia' FROM productos;");
        ResultSet resultado = psMostrar.executeQuery();
        resultado.next();
        return (resultado.getInt("referencia") + 1);
    }

    public List<String> categoriasExistentes() throws SQLException {
        String respuesta = "";
        List<String> categorias = new ArrayList<>();

        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT distinct(categoria) FROM productos;");
        ResultSet resultado = psMostrar.executeQuery();
        while (resultado.next()) {
            categorias.add(resultado.getString("categoria"));
        }
        return categorias;
    }

    public int idProducto(String nombreProducto) throws SQLException {
        int resultado = 0;

        PreparedStatement psTrabajador;
        ResultSet rsTrabajador;
        psTrabajador = conexion.prepareStatement("SELECT referencia FROM productos WHERE nombre = ?;");
        psTrabajador.setString(1, nombreProducto);
        rsTrabajador = psTrabajador.executeQuery();
        while (rsTrabajador.next()) {
            resultado = rsTrabajador.getInt("referencia");
        }

        return resultado;
    }

    public int cantidad(int idTienda, Producto producto) throws SQLException {
        PreparedStatement psCantidad;
        ResultSet rsCantidad;
        psCantidad = conexion.prepareStatement("SELECT tp.stock "
                + "FROM productos p INNER JOIN tiendas_productos tp "
                + "ON p.referencia=tp.idProducto "
                + "WHERE tp.idTienda=? AND p.referencia=?;");
        psCantidad.setInt(1, idTienda);
        psCantidad.setInt(2, producto.getReferencia());
        rsCantidad = psCantidad.executeQuery();
        rsCantidad.next();
        
        return rsCantidad.getInt("stock");
    }
    
    public void anadirProducto(Producto producto) throws SQLException {

        ResultSet rsProducto;
        PreparedStatement psProducto;

        psProducto = conexion.prepareStatement("INSERT INTO productos "
                + "(referencia, nombre, categoria, descripcion, precioCompra, precioVenta, iva) "
                + "VALUES(?,?,?,?,?,?,?);");
        
        psProducto.setInt(1, producto.getReferencia());
        psProducto.setString(2, producto.getNombre());
        psProducto.setString(3, producto.getCategoria());
        psProducto.setString(4, producto.getDescripcion());
        psProducto.setDouble(5, producto.getPrecioCompra());
        psProducto.setDouble(6, producto.getPrecioVenta());
        psProducto.setDouble(7, producto.getIva());
        psProducto.executeUpdate();
        
    }

}
