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
        Double precioTotal, precioProducto;
        int cantidad;

        psDetalles = conexion.prepareStatement("INSERT INTO detallesventa "
                + " (idVenta,referencia,cantidad,precio) "
                + "VALUES(?,?,?,?);");

        for (DetalleVenta listaDetalle : listaDetalles) {
            detalle = listaDetalle;
            cantidad = detalle.getCantidad();
            precioProducto = detalle.getPrecio();
            precioTotal = precioProducto * cantidad;

            psDetalles.setInt(1, detalle.getIdVenta());
            psDetalles.setInt(2, detalle.getReferencia());
            psDetalles.setInt(3, cantidad);
            psDetalles.setDouble(4, precioTotal);

            totalPrecioDetalles = totalPrecioDetalles + precioTotal;
        }
        psDetalles.executeUpdate();

    }

}
