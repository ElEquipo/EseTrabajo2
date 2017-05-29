/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author daw
 */
public class Producto {

    private final IntegerProperty referencia;
    private final StringProperty nombre;
    private final StringProperty categoria;
    private final StringProperty descripcion;
    private final DoubleProperty precioCompra;
    private final DoubleProperty precioVenta;
    private final DoubleProperty iva;
    private final IntegerProperty cantidad;

    public Producto(Integer referencia, String nombre, String categoria, String descripcion, Double precioCompra, Double precioVenta, Double iva) {
        this.referencia = new SimpleIntegerProperty(referencia);
        this.nombre = new SimpleStringProperty(nombre);
        this.categoria = new SimpleStringProperty(categoria);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precioCompra = new SimpleDoubleProperty(precioCompra);
        this.precioVenta = new SimpleDoubleProperty(precioVenta);
        this.iva = new SimpleDoubleProperty(iva);
        this.cantidad = null;
    }
    
    public Producto(Integer referencia, String nombre, String categoria, String descripcion, Double precioCompra, Double precioVenta, Double iva, Integer cantidad) {
        this.referencia = new SimpleIntegerProperty(referencia);
        this.nombre = new SimpleStringProperty(nombre);
        this.categoria = new SimpleStringProperty(categoria);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precioCompra = new SimpleDoubleProperty(precioCompra);
        this.precioVenta = new SimpleDoubleProperty(precioVenta);
        this.iva = new SimpleDoubleProperty(iva);
        this.cantidad = new SimpleIntegerProperty(cantidad);
    }

    /*---------------------- GET AND SET ----------------------------*/
    public double getIva() {
        return iva.get();
    }

    public double getPrecioVenta() {
        return precioVenta.get();
    }

    public double getPrecioCompra() {
        return precioCompra.get();
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public String getCategoria() {
        return categoria.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public int getReferencia() {
        return referencia.get();
    }

    @Override
    public String toString() {
        return this.nombre.get();
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

}
