/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Modelo.Producto;
import Modelo.Tienda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class TiendaDAO {

    Connection conexion;

    public TiendaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List cargarDatos() throws SQLException {

        PreparedStatement psTiendas;
        ResultSet rsTiendas;
        Tienda tienda;
        List<Tienda> listaTiendas = new ArrayList<>();
        psTiendas = conexion.prepareStatement("SELECT * FROM tiendas;");
        rsTiendas = psTiendas.executeQuery();
        while (rsTiendas.next()) {
            tienda = new Tienda(rsTiendas.getInt("idTienda"),
                    rsTiendas.getString("nombre"),
                    rsTiendas.getString("direccion"),
                    rsTiendas.getString("ciudad"));
            listaTiendas.add(tienda);
        }
        return listaTiendas;
    }

}
