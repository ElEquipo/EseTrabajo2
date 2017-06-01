package vista.Empleado;

import Datos.ConexionBD;
import Datos.ProductoDAO;
import Datos.TiendaDAO;
import Datos.TrabajadorDAO;
import Datos.VentaDAO;
import Modelo.Alerta.Alerta;
import Modelo.Producto;
import Modelo.Tienda;
import Modelo.Trabajador;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.io.IOException;
import static java.lang.String.format;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import static java.lang.String.format;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

public class EmpleadoController implements Initializable {

    private TiendaDAO tienda;
    private ProductoDAO producto;
    private TrabajadorDAO trabajador;
    private VentaDAO venta;
    private Alerta estiloAlerta;
    private ObservableList<Producto> listaProductos;
    private ObservableList<Tienda> listaTiendas;
    private ObservableList<Trabajador> listaTrabajadores;
    private LocalDateTime date;
    private String formato;
    private Trabajador empleadoActual;
    private Tienda tiendaActual;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH':'mm':'ss");
    private boolean compraFin = true;
    private boolean productoAnadido = false;

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
    private TableColumn<Producto, Integer> tb_stock;
    @FXML
    private ComboBox<Producto> cb_referencia;
    @FXML
    private TextField tf_cantidad;
    @FXML
    private TextField tf_idVenta;
    @FXML
    private TextField tf_tienda;
    @FXML
    private TextField tf_trabajador;
    @FXML
    private Button bt_anadirProducto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        producto = new ProductoDAO(ConexionBD.conexion);
        tienda = new TiendaDAO(ConexionBD.conexion);
        venta = new VentaDAO(ConexionBD.conexion);
        trabajador = new TrabajadorDAO(ConexionBD.conexion);
        empleadoActual = ConexionBD.actualUser;
        tiendaActual = ConexionBD.actualShop;
//        try {
//            tf_tienda.setText(tienda.nombreTienda(empleadoActual.getIdTienda()));
//        } catch (SQLException ex) {
//            alertIdTienda(ex);
//        }
        tf_tienda.setText(tiendaActual.getNombre());
        tf_trabajador.setText(empleadoActual.getNombre());
        estiloAlerta = new Alerta();
        pn_productos.setVisible(false);
        pn_ventas.setVisible(false);
        tf_tienda.setEditable(false);
        tf_trabajador.setEditable(false);
        tf_fechaVenta.setEditable(false);
        tf_idVenta.setEditable(false);
        Alert errorCarga;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH':'mm':'ss");

        try {
            tf_idVenta.setText(venta.mostrarSiguienteID());
        } catch (SQLException ex) {
            errorCarga = new Alert(Alert.AlertType.ERROR);
            errorCarga.setTitle("Error Carga Id");
            errorCarga.setHeaderText("Error al cargar el id de la venta \n" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }

        try {
            /* TABLE VIEW PRODUCTOS */
            listaProductos = FXCollections.observableArrayList(producto.cargarProductos(empleadoActual.getIdTienda()));
            tv_productos.setItems(listaProductos);
            tb_referencia.setCellValueFactory(new PropertyValueFactory<>("referencia"));
            tb_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tb_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
            tb_descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            tb_precioCompra.setCellValueFactory(new PropertyValueFactory<>("precioCompra"));
            tb_precioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
            tb_iva.setCellValueFactory(new PropertyValueFactory<>("iva"));
            tb_stock.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        } catch (SQLException ex) {
            errorCarga = new Alert(Alert.AlertType.ERROR);
            errorCarga.setTitle("Error Carga Productos");
            errorCarga.setHeaderText("Error al cargar la lista de productos");
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }

        try {
            /* COMBO BOX PRODUCTOS */
            listaProductos = FXCollections.observableArrayList(producto.cargarProductos(empleadoActual.getIdTienda()));
            cb_referencia.setItems(listaProductos);
        } catch (SQLException ex) {
            errorCarga = new Alert(Alert.AlertType.ERROR);
            errorCarga.setTitle("Error Carga Productos");
            errorCarga.setHeaderText("Error al cargar la lista de productos \n" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }

    }

    @FXML
    private void CloseAction(ActionEvent event) throws SQLException {
        try {
            if (pn_ventas.isVisible() && !compraFin) {
                alertSalirVenta(venta.idActual());
            } else {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/login/LoginFXML.fxml"));
                ac_empleado.getChildren().setAll(pane);
            }
        } catch (IOException ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void inicioAction(ActionEvent event) throws SQLException {
        if (pn_ventas.isVisible() && !compraFin) {
            alertSalirVenta(venta.idActual());
        } else {
            pn_fondoIconos.setVisible(true);
            pn_productos.setVisible(false);
            pn_ventas.setVisible(false);
        }
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
    private void ventasAction(ActionEvent event) throws SQLException, ParseException, IOException {
        venta = new VentaDAO(ConexionBD.conexion);
        pn_fondoIconos.setVisible(false);
        pn_ventas.setVisible(true);
        Date fecha;
        date = LocalDateTime.now();
        formato = date.format(formatter);
        productoAnadido = false;

        SimpleDateFormat dt = new SimpleDateFormat("y-M-d h:m:s");

        if (bt_ventas.isFocused()) {
            /* ENTRAR EN VENTAS */
            tf_fechaVenta.setText(formato);
            tf_idVenta.setText(venta.mostrarSiguienteID());
        }

        /* AÑADIR PRODUCTO */
        if (bt_anadirProducto.isFocused() && tf_cantidad.getLength() != 0 && cb_referencia.getValue() != null) {
            /* AÑADIR PRODUCTO A LA VENTA SI LOS CAMPOS ESTAN LLENOS */
            fecha = dt.parse(tf_fechaVenta.getText());
            if (anadirProducto(fecha)) {
                compraFin = false;
            }
        } else if (bt_anadirProducto.isFocused() && (tf_cantidad.getLength() == 0 || cb_referencia.getValue() == null)) {
            /* ALERTA DE CAMPOS VACIOS AL AÑADIR PRODUCTO */
            alertCamposVacios();
        }

        /* REGISRAR UNA VENTA */
        if (bt_regVenta.isFocused()
                && (tf_cantidad.getLength() == 0 || cb_referencia.getValue() == null) && compraFin) {
            /* ENTRA SI LOS CAMPOS ESTAN VACIOS Y NO HEMOS AÑADIDO NINGUN PRODUCTO A LA VENTA */

            alertCamposVacios();
        } else if (bt_regVenta.isFocused() && tf_cantidad.getLength() != 0 && cb_referencia.getValue() != null && compraFin) {
            /* ENTRA SI LOS CAMPOS ESTAN LLENOS Y NO HEMOS AÑADIDO NINGUN PRODUCTO A LA VENTA*/

            fecha = dt.parse(tf_fechaVenta.getText());
            anadirProducto(fecha);
            venta.generarTicket(Integer.parseInt(tf_idVenta.getText()), fecha);
            siguienteCompra();
            alertFinCompra();
            compraFin = true;
        } else if (bt_regVenta.isFocused() && !compraFin && ((tf_cantidad.getLength() == 0 || cb_referencia.getValue() == null))) {
            /* ENTRA SI LOS CAMPOS ESTAN VACIOS PERO SE HAN INTRODUCIDO PRODUCTOS. REGISTRAR COMPRA. SIGUIENTE COMPRA */

            fecha = dt.parse(tf_fechaVenta.getText());
            venta.generarTicket(Integer.parseInt(tf_idVenta.getText()), fecha);
            siguienteCompra();
            alertFinCompra();
            compraFin = true;
        } else if (bt_regVenta.isFocused() && productoAnadido && !compraFin) {
            /* ENTRA SI SE HAN AÑADIDO PRODUCTOS, REGISTRA LA COMPRA. SIGUIENTE COMPRA */

            fecha = dt.parse(tf_fechaVenta.getText());
            venta.generarTicket(Integer.parseInt(tf_idVenta.getText()), fecha);
            siguienteCompra();
            alertFinCompra();
            compraFin = true;
        }

        /* SALIR DE VENTAS */
        if (bt_atrasVentas.isFocused() && compraFin) {
            pn_fondoIconos.setVisible(true);
            pn_ventas.setVisible(false);
            cb_referencia.setValue(null);
            tf_cantidad.clear();

        } else if (bt_atrasVentas.isFocused() && !compraFin) {
            alertSalirVenta(venta.idActual());
        }

    }

    public void alertSalirVenta(int idVenta) throws SQLException {
        Alert saliendo;
        saliendo = new Alert(Alert.AlertType.CONFIRMATION);
        saliendo.setTitle("Saliendo de ventas");
        saliendo.setHeaderText("No ha terminado la compra.");
        saliendo.setContentText("¿Seguro que desea salir?");
        estiloAlerta.darleEstiloAlPanel(saliendo);

        ButtonType botonCancelar = new ButtonType("Cancelar");
        ButtonType botonAceptar = new ButtonType("Aceptar");

        saliendo.getButtonTypes().setAll(botonCancelar, botonAceptar);

        Optional<ButtonType> result = saliendo.showAndWait();

        if (result.get() == botonAceptar) {
            pn_fondoIconos.setVisible(true);
            pn_ventas.setVisible(false);
            cb_referencia.setValue(null);
            tf_cantidad.clear();

            if (!compraFin) {
                venta.elimiarVenta(idVenta);
            }
            compraFin = true;
        }

    }

    public void alertCamposVacios() {
        Alert faltaCampos;
        faltaCampos = new Alert(Alert.AlertType.ERROR);
        faltaCampos.setTitle("Error Añadir");
        faltaCampos.setHeaderText("Por favor rellene todos los campos.");
        estiloAlerta.darleEstiloAlPanel(faltaCampos);
        faltaCampos.showAndWait();

    }

    public void alertFinCompra() {
        Alert finalizarCompra;
        finalizarCompra = new Alert(Alert.AlertType.INFORMATION);
        finalizarCompra.setTitle("Compra");
        finalizarCompra.setHeaderText("¡Gracias por su compra!.");
        estiloAlerta.darleEstiloAlPanel(finalizarCompra);
        finalizarCompra.show();

    }

    public void alertFaltaStock() {
        Alert faltaStock;
        faltaStock = new Alert(Alert.AlertType.ERROR);
        faltaStock.setTitle("Error Añadir");
        faltaStock.setHeaderText("Error al añadir el producto");
        faltaStock.setContentText("Lo sentimos, no quedan suficientes existencias de este producto.");
        estiloAlerta.darleEstiloAlPanel(faltaStock);
        faltaStock.showAndWait();
    }

    public void alertClaveDuplicada(SQLException sqlDuplicate) {
        Alert claveDuplicada;

        claveDuplicada = new Alert(Alert.AlertType.ERROR);
        claveDuplicada.setTitle("Error Añadir");
        claveDuplicada.setHeaderText("No se puede insertar el mismo producto otra vez.");
        claveDuplicada.setContentText("Has intentado añadir el mismo producto en el mismo pedido" + sqlDuplicate.getMessage());
        estiloAlerta.darleEstiloAlPanel(claveDuplicada);
        claveDuplicada.showAndWait();
    }

    public void alertIdTienda(SQLException sqlDuplicate) {
        Alert idTiendaNotFound;

        idTiendaNotFound = new Alert(Alert.AlertType.ERROR);
        idTiendaNotFound.setTitle("Error Añadir");
        idTiendaNotFound.setHeaderText("No se encuentra el nombre de la tienda.");
        idTiendaNotFound.setContentText("No se ha encontrado la tienda relacionada a este trabajador" + sqlDuplicate.getMessage());
        estiloAlerta.darleEstiloAlPanel(idTiendaNotFound);
        idTiendaNotFound.showAndWait();
    }

    public boolean anadirProducto(Date fecha) throws SQLException {

        try {
            if (venta.insertarVenta(tienda.idTienda(tf_tienda.getText()), trabajador.idTrabajador(tf_trabajador.getText()), fecha,
                    Integer.parseInt(tf_idVenta.getText()), producto.idProducto(cb_referencia.getValue().getNombre()),
                    Integer.parseInt(tf_cantidad.getText())) == 1) {
                productoAnadido = true;
            } else {
                alertFaltaStock();
                productoAnadido = false;
            }
            cb_referencia.setValue(null);
            tf_cantidad.clear();
        } catch (SQLException sqlDuplicate) {
            alertClaveDuplicada(sqlDuplicate);
        }
        return productoAnadido;
    }

    public void siguienteCompra() throws SQLException {
        tf_idVenta.setText(venta.mostrarSiguienteID());
        tf_fechaVenta.setText(formato);
        cb_referencia.setValue(null);
        tf_cantidad.clear();

    }

}
