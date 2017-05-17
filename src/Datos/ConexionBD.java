package Datos;

import vista.login.LoginController;
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

        } catch (SQLException ex) {
            mensaje = "Hubo un problema al intentar conectarse con la base de datos " + bd + "\n"
                    + "Error: " + ex.getMessage();
        } catch (Exception e) {
            mensaje = e.getMessage();
        }

        this.mensajeErrorConexion = mensaje;

        return conectado;
    }

    public boolean desconectar() {
        boolean desconectado = false;
        try {
            conexion.close();
            desconectado = true;
        } catch (SQLException ex) {
        } catch (Exception e) {
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
            System.out.println(ex.getMessage());
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
            System.out.println(ex.getMessage());
        }
        
        return puesto;
    }

    // * * * * * * * * * * GET AND SET * * * * * * * * * * 
    public String getMsgErrorConexion() {
        return mensajeErrorConexion;
    }

    public Connection getConexion() {
        return conexion;
    }
}
