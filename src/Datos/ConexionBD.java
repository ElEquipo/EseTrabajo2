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

    public boolean conectar(String bd, String user, String pwd) {
        boolean conectado = false;
        String mensaje = "";
        try {

            conexion = DriverManager.getConnection(bd, user, pwd);
            if (conexion != null) {
                conectado = true;
            }
            mensaje = "Conexión establecida con la Base de Datos " + bd;

            this.mensajeErrorConexion = mensaje;

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conectado;
    }

    public boolean desconectar() {
        boolean desconectado = false;

        try {
            conexion.close();
            desconectado = true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return desconectado;

    }

    public boolean existe(String user, String pass) {

        PreparedStatement psExiste;
        ResultSet resultado;
        boolean existe = false;

        try {
            psExiste = conexion.prepareStatement("SELECT existeTrabajador(?,?) AS 'existe';");
            psExiste.setString(1, user);
            psExiste.setString(2, pass);
            resultado = psExiste.executeQuery();
            resultado.next();
            existe = resultado.getBoolean("existe");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existe;
    }

    public String puesto(String user) {
        PreparedStatement psPuesto;
        ResultSet resultado;
        String puesto = null;

        try {
            psPuesto = conexion.prepareStatement("SELECT puestoTrabajador(?) AS 'puesto';");
            psPuesto.setString(1, user);

            resultado = psPuesto.executeQuery();
            resultado.next();
            puesto = resultado.getString("puesto");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return puesto;
    }

    public boolean cambiarContraseña(String user) {

        PreparedStatement psExiste;
        ResultSet resultado;
        boolean existe = false;

        try {
            psExiste = conexion.prepareStatement("SELECT contraseñaParaCambiar(?) AS 'cambiar';");
            psExiste.setString(1, user);
            resultado = psExiste.executeQuery();
            resultado.next();
            existe = resultado.getBoolean("cambiar");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

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
