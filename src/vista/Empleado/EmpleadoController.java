package vista.Empleado;

import Datos.ConexionBD;
import Datos.ProductoDAO;
import Datos.TiendaDAO;
import Datos.TrabajadorDAO;
import Modelo.Producto;
import Modelo.Tienda;
import Modelo.Trabajador;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class EmpleadoController implements Initializable {

    TiendaDAO tienda;
    ProductoDAO producto;
    TrabajadorDAO trabajador;
    private ObservableList<Producto> listaProductos;
    private ObservableList<Tienda> listaTiendas;
    private ObservableList<Trabajador> listaTrabajadores;
    LocalDateTime date = LocalDateTime.now();
    String formato;

    /*ATRIBUTOS FXML*/
    @FXML
    private Button bt_Inicio;
    @FXML
    private Button bt_Perfil;
    @FXML
    private Button bt_Ayuda;
    @FXML
    private Button bt_LogOut;
    @FXML
    private AnchorPane ac_empleado;
    @FXML
    private Pane pn_titulo;
    @FXML
    private Pane pn_productos;
    @FXML
    private TableView<Producto> tv_productos;
    @FXML
    private Button bt_atrasProductos;
    @FXML
    private Label lb_idVenta;
    @FXML
    private TextField tf_fechaVenta;
    @FXML
    private Button bt_atrasVentas;
    @FXML
    private Pane pn_ventas;
    @FXML
    private Button bt_regVenta;
    @FXML
    private Pane pn_fondoIconos;
    @FXML
    private Button bt_ventas;
    @FXML
    private Button bt_productos;
    @FXML
    private TableColumn<Producto, Integer> tb_referencia;
    @FXML
    private TableColumn<Producto, String> tb_nombre;
    @FXML
    private TableColumn<Producto, String> tb_categoria;
    @FXML
    private TableColumn<Producto, String> tb_descripcion;
    @FXML
    private TableColumn<Producto, Double> tb_precioCompra;
    @FXML
    private TableColumn<Producto, Double> tb_precioVenta;
    @FXML
    private TableColumn<Producto, Double> tb_iva;
    @FXML
    private ComboBox<Producto> cb_referencia;
    @FXML
    private TextField tf_cantidad;
    @FXML
    private ComboBox<Tienda> cb_tiendas;
    @FXML
    private ComboBox<Trabajador> cb_trabajadores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        producto = new ProductoDAO(ConexionBD.conexion);
        tienda = new TiendaDAO(ConexionBD.conexion);
        trabajador = new TrabajadorDAO(ConexionBD.conexion);
        pn_productos.setVisible(false);
        pn_ventas.setVisible(false);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH':'mm':'ss");
        formato = date.format(formatter);
        
        tf_fechaVenta.setText(formato);
        try {
            lb_idVenta.setText(producto.mostrarSiguienteID());
            System.out.println(producto.mostrarSiguienteID());
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error Carga Id");
            alerta.setHeaderText("Error al cargar el id de la venta \n" + ex.getMessage());
            alerta.showAndWait();
        }

        try {
            /* TABLE VIEW PRODUCTOS */
            listaProductos = FXCollections.observableArrayList(producto.cargarProductos());
            tv_productos.setItems(listaProductos);
            tb_referencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
            tb_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tb_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            tb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tb_precioCompra.setCellValueFactory(new PropertyValueFactory<>("precioCompra"));
            tb_precioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
            tb_iva.setCellValueFactory(new PropertyValueFactory<>("iva"));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error Carga Productos");
            alerta.setHeaderText("Error al cargar la lista de productos");
            alerta.showAndWait();
        }

        try {
            /* COMBO BOX TIENDAS*/
            listaTiendas = FXCollections.observableArrayList(tienda.cargarDatos());
            cb_tiendas.setItems(listaTiendas);
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error Carga Tiendas");
            alerta.setHeaderText("Error al cargar la lista de tiendas");
            alerta.showAndWait();
        }

        try {
            /* COMBO BOX TRABAJADORES */
            listaTrabajadores = FXCollections.observableArrayList(trabajador.cargarDatos());
            cb_trabajadores.setItems(listaTrabajadores);
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error Carga Trabajdores");
            alerta.setHeaderText("Error al cargar la lista de trabajadores \n" + ex.getMessage());
            alerta.showAndWait();
        }

        try {
            /* COMBO BOX PRODUCTOS */
            listaProductos = FXCollections.observableArrayList(producto.cargarProductos());
            cb_referencia.setItems(listaProductos);
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error Carga Productos");
            alerta.setHeaderText("Error al cargar la lista de productos \n" + ex.getMessage());
            alerta.showAndWait();
        }

    }

    @FXML
    private void CloseAction(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/login/LoginFXML.fxml"));
            ac_empleado.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void inicioAction(ActionEvent event) {
        pn_fondoIconos.setVisible(true);
        pn_productos.setVisible(false);
        pn_ventas.setVisible(false);
    }

    @FXML
    private void productosAction(ActionEvent event) {

        pn_fondoIconos.setVisible(false);
        pn_productos.setVisible(true);

        if (bt_atrasProductos.isFocused()) {
            pn_fondoIconos.setVisible(true);
            pn_productos.setVisible(false);
        }

    }

    @FXML
    private void ventasAction(ActionEvent event) {

        pn_fondoIconos.setVisible(false);
        pn_ventas.setVisible(true);

        if (bt_regVenta.isFocused()) {

        }

        if (bt_atrasVentas.isFocused()) {
            pn_fondoIconos.setVisible(true);
            pn_ventas.setVisible(false);
        }

    }

}
