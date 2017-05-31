package Datos;

import Modelo.Incidencia;
import Modelo.Trabajador;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IncidenciaDAO {

    private Connection conexion;

    public IncidenciaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void crearIncidencia(Incidencia incidencia, Trabajador trabajador) throws SQLException {
        PreparedStatement psIncidencias;
        psIncidencias = conexion.prepareStatement("INSERT INTO incidencias "
                + "(idIncidencia,idTienda,idTrabajador,tipo,fecha,descripcion,leido) "
                + " VALUES(?,?,?,?,?,?,?);");

        psIncidencias.setNull(1, java.sql.Types.NULL);
        psIncidencias.setInt(2, trabajador.getIdTienda());
        psIncidencias.setInt(3, trabajador.getId());
        psIncidencias.setString(4, incidencia.getTipo());
        psIncidencias.setDate(5, Date.valueOf(incidencia.getFecha()));
        psIncidencias.setString(6, incidencia.getDescripcion());
        psIncidencias.setString(7, incidencia.getLeido());
        psIncidencias.executeUpdate();

    }
}
