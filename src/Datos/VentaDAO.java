/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Rasul
 */
public class VentaDAO {

    Connection conexion;

    public VentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public int insertarVenta(Integer idTienda, Integer idTrabajador, Date fechaVenta, Integer idVenta, Integer referencia, Integer cantidad) throws SQLException {
        java.sql.Date fechaSQL = new java.sql.Date(fechaVenta.getTime());

        PreparedStatement psInsertar;
        ResultSet rs;
        psInsertar = conexion.prepareStatement("SELECT insertarVenta(?,?,?,?,?,?) AS 'venta';");
        psInsertar.setInt(1, idTienda);
        psInsertar.setInt(2, idTrabajador);
        psInsertar.setDate(3, fechaSQL);
        psInsertar.setInt(4, idVenta);
        psInsertar.setInt(5, referencia);
        psInsertar.setInt(6, cantidad);
        rs = psInsertar.executeQuery();
        rs.next();
        return rs.getInt("venta");

    }

    public String mostrarSiguienteID() throws SQLException {
        int operacion = 0;

        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT idSiguienteVenta() AS 'id';");
        ResultSet resultado = psMostrar.executeQuery();
        resultado.next();

        operacion = resultado.getInt("id") + 1;

        return (operacion + "");
    }

    public void elimiarVenta(int idVenta) throws SQLException {
        PreparedStatement psEliminar;
        psEliminar = conexion.prepareStatement("CALL eliminarVenta(?);");
        psEliminar.setInt(1, idVenta);
        psEliminar.executeQuery();

    }

    public int idActual() throws SQLException {
        int operacion;

        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT MAX(idVenta) 'id' FROM ventas;");
        ResultSet resultado = psMostrar.executeQuery();
        resultado.next();

        operacion = resultado.getInt("id");

        return operacion;
    }

}
