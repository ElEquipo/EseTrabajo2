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

    public List cargarProductos() throws SQLException {
        Producto producto;
        PreparedStatement psProductos;
        ResultSet rsProductos;
        List<Producto> listaProductos = new ArrayList<>();
        psProductos = conexion.prepareStatement("SELECT * FROM productos;");
        rsProductos = psProductos.executeQuery();
        while (rsProductos.next()) {
            producto = new Producto(rsProductos.getInt("referencia"),
                    rsProductos.getString("nombre"), rsProductos.getString("categoria"),
                    rsProductos.getString("descripcion"), rsProductos.getDouble("precioCompra"),
                    rsProductos.getDouble("precioVenta"), rsProductos.getDouble("IVA"));
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

}
