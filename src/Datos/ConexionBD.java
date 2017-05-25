package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    public static Connection conexion;
    private String mensajeErrorConexion;

    public ConexionBD() {
    }

    public boolean conectar(String bd, String user, String pwd) throws SQLException {
        boolean conectado = false;
        String mensaje = "";

        conexion = DriverManager.getConnection(bd, user, pwd);
        if (conexion != null) {
            conectado = true;
        }
        mensaje = "Conexión establecida con la Base de Datos " + bd;

        this.mensajeErrorConexion = mensaje;

        return conectado;
    }

    public boolean desconectar() throws SQLException {
        boolean desconectado = false;

        conexion.close();
        desconectado = true;

        return desconectado;

    }

    public boolean existe(String user, String pass) throws SQLException {

        PreparedStatement psExiste;
        ResultSet resultado;
        boolean existe = false;

        psExiste = conexion.prepareStatement("SELECT existeTrabajador(?,?) AS 'existe';");
        psExiste.setString(1, user);
        psExiste.setString(2, pass);
        resultado = psExiste.executeQuery();
        resultado.next();
        existe = resultado.getBoolean("existe");

        return existe;
    }

    public String puesto(String user) throws SQLException {
        PreparedStatement psPuesto;
        ResultSet resultado;
        String puesto = null;

        psPuesto = conexion.prepareStatement("SELECT puestoTrabajador(?) AS 'puesto';");
        psPuesto.setString(1, user);

        resultado = psPuesto.executeQuery();
        resultado.next();
        puesto = resultado.getString("puesto");

        return puesto;
    }

    public boolean cambiarContraseña(String user) throws SQLException {

        PreparedStatement psExiste;
        ResultSet resultado;
        boolean existe = false;

        psExiste = conexion.prepareStatement("SELECT cambiarContraseña(?) AS 'cambiar';");
        psExiste.setString(1, user);
        resultado = psExiste.executeQuery();
        resultado.next();
        existe = resultado.getBoolean("cambiar");

        return existe;
    }

    // * * * * * * * * * * GET AND SET * * * * * * * * * * 
    public String getMsgErrorConexion() {
        return mensajeErrorConexion;
    }

    public Connection getConexion() {
        return conexion;
    }
}
