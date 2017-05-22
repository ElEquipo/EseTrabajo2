/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Daniel
 */
public class TrabajadorDAO {
    
    
    ConexionBD conexion;

    public TrabajadorDAO(ConexionBD conexion) {
        this.conexion = conexion;
    }
    
    public void insertar(int id, String dni, String nombre, String apellido1, String apellido2,
            String puesto, Double salario, LocalDate fecha, String nick, String pass,
            LocalTime horaEntrada,LocalTime horaSalida, int idTienda){
        
    }
    
}
