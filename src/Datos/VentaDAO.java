/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

/**
 *
 * @author Rasul
 */
public class VentaDAO {

    Connection conexion;

    public VentaDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void insertarVenta(Integer idTienda, Integer idTrabajador, Date fechaVenta, Integer referencia, Integer cantidad) throws SQLException{

        PreparedStatement psInsertar;
        psInsertar = conexion.prepareStatement("CALL insertarVenta(?,?,?,?,?);");
        psInsertar.setInt(1, idTienda);
        psInsertar.setInt(2, idTrabajador);
        psInsertar.setDate(3, (java.sql.Date) fechaVenta);
        psInsertar.setInt(4, referencia);
        psInsertar.setInt(5, cantidad);
       
        psInsertar.executeQuery();
        
            
        
    }

}
