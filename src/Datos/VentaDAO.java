/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Modelo.DetalleVenta;
import Modelo.Venta;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Rasul
 */
public class VentaDAO {

    Connection conexion;

    public VentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarVenta(Venta venta, List<DetalleVenta> listaDetalles) throws SQLException {

        PreparedStatement psInsertar;

        psInsertar = conexion.prepareStatement("INSERT INTO ventas "
                + "(idVenta,idTienda,idTrabajador,fechaVenta) "
                + "VALUES(?,?,?,now());");
        psInsertar.setInt(1, venta.getIdVenta());
        psInsertar.setInt(2, venta.getIdTienda());
        psInsertar.setInt(3, venta.getIdTrabajador());
        psInsertar.executeUpdate();

    }

    public int mostrarSiguienteID() throws SQLException {
        PreparedStatement psMostrar;
        psMostrar = conexion.prepareStatement("SELECT MAX(idVenta) AS 'siguienteVenta' FROM ventas;");
        ResultSet resultado = psMostrar.executeQuery();
        resultado.next();
        return resultado.getInt("siguienteVenta") + 1;
    }

    public void elimiarVenta(int idVenta) throws SQLException {
        PreparedStatement psEliminar;
        psEliminar = conexion.prepareStatement("CALL eliminarVenta(?);");
        psEliminar.setInt(1, idVenta);
        psEliminar.executeQuery();

    }

    public Double caluclarTotal(int idVenta) throws SQLException {
        PreparedStatement psCalculo;
        psCalculo = conexion.prepareStatement("SELECT calcularTotal(?) AS 'total';");
        psCalculo.setInt(1, idVenta);
        ResultSet resultado = psCalculo.executeQuery();
        resultado.next();
        return resultado.getDouble("total");
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
//        idTicket++;

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

        Path fichero = Paths.get(dia + "-" + "numticket" + "-ticket.txt");
        String destino = ".\\src\\vista\\Empleado\\Tickets\\" + fichero;
        Path directorio = Paths.get(destino);

        try (BufferedWriter salida = Files.newBufferedWriter(directorio.toAbsolutePath(), StandardOpenOption.CREATE)) {

            salida.write(texto + "╚═══════════════════════════════════════════╝");

        }

    }

}
