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

    public String mostrarSiguienteID() throws SQLException {
        String respuesta = "";

        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT idSiguienteVenta() AS 'id';");
        ResultSet resultado = psMostrar.executeQuery();
        resultado.next();
        return (" " + resultado.getInt("id"));
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

}
