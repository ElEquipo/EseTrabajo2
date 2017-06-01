package Modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DetalleVenta {

    private final IntegerProperty idVenta;
    private final IntegerProperty referencia;
    private final IntegerProperty cantidad;
    private final DoubleProperty precio;

    public DetalleVenta(int idVenta, int referencia, int cantidad, Double precio) {
        this.idVenta = new SimpleIntegerProperty(idVenta);
        this.referencia = new SimpleIntegerProperty(referencia);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.precio = new SimpleDoubleProperty(precio);
    }

    /*--------------------------- GET AND SET ----------------------------- */
    public double getPrecio() {
        return precio.get();
    }

    public void setPrecio(double value) {
        precio.set(value);
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int value) {
        cantidad.set(value);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public int getReferencia() {
        return referencia.get();
    }

    public void setReferencia(int value) {
        referencia.set(value);
    }

    public IntegerProperty referenciaProperty() {
        return referencia;
    }

    public int getIdVenta() {
        return idVenta.get();
    }

    public void setIdVenta(int value) {
        idVenta.set(value);
    }

    public IntegerProperty idVentaProperty() {
        return idVenta;
    }
    
    @Override
    public String toString(){
        return this.idVenta.get() + " " + this.referencia.get() + " " 
                + this.cantidad.get() + " " + this.precio.get() + "\n" ;
    }

}
