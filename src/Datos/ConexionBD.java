package Datos;

import Modelo.Trabajador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class ConexionBD {

    public static Connection actualUser;
    public static Trabajador conectado;
    private String mensajeErrorConexion;
    private TrabajadorDAO trabajadorDAO;

    public ConexionBD() {
    }
    
    public ConexionBD(Connection conn){
        actualUser = conn;
    }
    

    public boolean conectar(String bd, String user, String pwd, String AppUser) {
        boolean conectado = false;
        String mensaje = "";
        try {
            actualUser = DriverManager.getConnection(bd, user, pwd);
            if (actualUser != null) {
                conectado = true;
            }
            trabajadorDAO = new TrabajadorDAO(actualUser);
            this.conectado =  trabajadorDAO.cargarTrabajador(AppUser,0);
            
            
            mensaje = "Conexión establecida con la Base de Datos " + bd;
            this.mensajeErrorConexion = mensaje;

        } catch (SQLException ex) {
            
        }
        return conectado;
    }

    public boolean desconectar() {
        boolean desconectado = false;

        try {
            actualUser.close();
            desconectado = true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return desconectado;

    }

    public boolean existe(String user, String pass) { // BORRAR

        PreparedStatement psExiste;
        ResultSet resultado;
        boolean existe = false;

        try {
            psExiste = actualUser.prepareStatement("SELECT existeTrabajador(?,?) AS 'existe';");
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
            psPuesto = actualUser.prepareStatement("SELECT puestoTrabajador(?) AS 'puesto';");
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
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String  dni;

        try {
            psExiste = actualUser.prepareStatement("SELECT dni FROM trabajadores WHERE nick=?;");
            psExiste.setString(1, user);
            resultado = psExiste.executeQuery();
            resultado.next();
            dni = resultado.getString("dni");
            if(passwordEncryptor.checkPassword(dni, contraseña(user))){
                existe = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return existe;
    }

    public String contraseña(String user) {
        PreparedStatement psContraseña;
        ResultSet resultado;
        String contraseña = null;

        try {
            psContraseña = actualUser.prepareStatement("SELECT contraseña(?) AS 'pass';");
            psContraseña.setString(1, user);

            resultado = psContraseña.executeQuery();
            resultado.next();
            contraseña = resultado.getString("pass");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return contraseña;
    }

    // * * * * * * * * * * GET AND SET * * * * * * * * * * 
    public String getMsgErrorConexion() {
        return mensajeErrorConexion;
    }

    public Connection getConexion() {
        return actualUser;
    }

    public Trabajador getConectado() {
        return conectado;
    }

    public void setConectado(Trabajador conectado) {
        this.conectado = conectado;
    }
    
    
}
