package Datos;

import Modelo.Incidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IncidenciaDAO {

    private Connection conexion;

    public IncidenciaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void crearIncidencia(Incidencia incidencia) throws SQLException {
        PreparedStatement psIncidencias;
        ResultSet rsIncidencias;
        psIncidencias = conexion.prepareStatement("INSERT INTO incidencias (idIncidencia,idTienda,idTrabajador,tipo,fecha,descripcion,leido) "
                + " VALUES(?,?,?,?,?,?,?);");

        

    }
}
