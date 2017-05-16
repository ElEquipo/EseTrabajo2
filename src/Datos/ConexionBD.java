package Datos;

import inicio.FXMLDocumentController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {
    
    private Connection conexion;
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
            this.conexion.close();
            desconectado = true;
        } catch (SQLException ex) {
        } catch (Exception e) {
        }
        return desconectado;
        
    }
    
    public boolean existe(String user, String pass) {
        
        PreparedStatement ps;
        ResultSet resultado = null;
        boolean existe = false;
        
        try {
            ps = conexion.prepareStatement("SELECT idTrabajador FROM trabajadores WHERE nick=? AND password=?;");
            ps.setString(1, user);
            ps.setString(2, pass);
            resultado = ps.executeQuery();
            resultado.next();
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
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
