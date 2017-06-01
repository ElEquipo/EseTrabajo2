/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Modelo.Trabajador;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 *
 * @author Rasul
 */
public class VentaDAO {

    Connection conexion;
    private static int idTicket = 0;

    public VentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public int insertarVenta(Integer idTienda, Integer idTrabajador, String fechaVenta, Integer idVenta, Integer referencia, Integer cantidad) throws SQLException {
        
        PreparedStatement psInsertar;
        ResultSet rs;
        psInsertar = conexion.prepareStatement("SELECT insertarVenta(?,?,?,?,?,?) AS 'venta';");
        psInsertar.setInt(1, idTienda);
        psInsertar.setInt(2, idTrabajador);
        psInsertar.setString(3, fechaVenta);
        psInsertar.setInt(4, idVenta);
        psInsertar.setInt(5, referencia);
        psInsertar.setInt(6, cantidad);
        rs = psInsertar.executeQuery();
        rs.next();
        return rs.getInt("venta");

    }

    public String mostrarSiguienteID() throws SQLException {
        int operacion = 0;

        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT idSiguienteVenta() AS 'id';");
        ResultSet resultado = psMostrar.executeQuery();
        resultado.next();

        operacion = resultado.getInt("id") + 1;

        return (operacion + "");
    }

    public void elimiarVenta(int idVenta) throws SQLException {
        PreparedStatement psEliminar;
        psEliminar = conexion.prepareStatement("CALL eliminarVenta(?);");
        psEliminar.setInt(1, idVenta);
        psEliminar.executeQuery();

    }

    public int idActual() throws SQLException {
        int operacion;

        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT MAX(idVenta) 'id' FROM ventas;");
        ResultSet resultado = psMostrar.executeQuery();
        resultado.next();

        operacion = resultado.getInt("id");

        return operacion;
    }

    public void generarTicket(int idVenta) throws IOException, SQLException {
        String texto = "";
        PreparedStatement ps;
        ResultSet rs;
        int referencia, cantidad;
        double precio, total = 0.0, totalRound = 0.0;
        String nombre, dia = null, hora;
        String fecha = null;
        idTicket++;

        ps = conexion.prepareStatement("CALL listaProducto(?);");
        ps.setInt(1, idVenta);
        rs = ps.executeQuery();

        texto += "╔═══════════════════════════════════════════╗ \n";
        texto += "║                                                                      ║ \n";
        texto += "║                            JustComerce                               ║ \n";
        texto += "║                           ¯¯¯¯¯¯¯¯¯¯¯¯¯                              ║ \n";
        texto += "║                                                    c/ Colon 16       ║ \n";
        texto += "║                                                    46080 Valencia    ║ \n";
        texto += "║                                                    Valencia, España  ║ \n";
        texto += "║                                                    Tel.: 962336111   ║ \n";
        texto += "║                                                    CIF: E-48250773   ║ \n";
        texto += "║¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯║ \n";
        texto += "║  REFERENCIA   NOMBRE        CANTIDAD       PRECIO      FECHA VENTA   ║ \n";
        texto += "║¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯║ \n";

        while (rs.next()) {
            referencia = rs.getInt("referencia");
            nombre = rs.getString("nombre");
            cantidad = rs.getInt("cantidad");
            precio = rs.getDouble("precio");
            fecha = rs.getString("fechaVenta");

            StringTokenizer st = new StringTokenizer(fecha, " ");

            while (st.hasMoreTokens()) {
                dia = st.nextToken();
                hora = st.nextToken();
            }

                texto += String.format("║%-13s | %-10s | %-12s | %-10s | %-11s  ║\n", referencia, nombre, cantidad, precio + "€", dia);

                total = total + precio;
                totalRound = Math.rint(total * 100) / 100;
            }
            texto += "║¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯║ \n";
            texto += String.format("║                                     TOTAL: %-25s ║ \n", totalRound + "€");

            Path fichero = Paths.get(dia + "-" + idTicket + "-ticket.txt");
            String destino = ".\\src\\vista\\Empleado\\Tickets\\" + fichero;
            Path directorio = Paths.get(destino);

            try (BufferedWriter salida = Files.newBufferedWriter(directorio.toAbsolutePath(), StandardOpenOption.CREATE)) {

                salida.write(texto + "╚═══════════════════════════════════════════╝");

            }

        }

    }
