package Datos;

import Modelo.DetalleVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DetallesVentaDAO {

    private Connection conexion;
    private Double totalPrecioDetalles;

    public DetallesVentaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void detallesVenta(List<DetalleVenta> listaDetalles) throws SQLException {
        PreparedStatement psDetalles;
        DetalleVenta detalle;
        Double precioTotal;
        totalPrecioDetalles = 0.0;

        psDetalles = conexion.prepareStatement("INSERT INTO detallesventa "
                + " (idVenta,referencia,cantidad,precio) "
                + "VALUES(?,?,?,?);");

        for (DetalleVenta listaDetalle : listaDetalles) {
            detalle = listaDetalle;
            precioTotal = detalle.getPrecio();

            psDetalles.setInt(1, detalle.getIdVenta());
            psDetalles.setInt(2, detalle.getReferencia());
            psDetalles.setInt(3, detalle.getCantidad());
            psDetalles.setDouble(4, precioTotal);

            totalPrecioDetalles = totalPrecioDetalles + precioTotal;
            psDetalles.executeUpdate();
        }

    }

}
