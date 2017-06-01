package vista.Empleado;

import Datos.ConexionBD;
import Datos.DetallesVentaDAO;
import Datos.IncidenciaDAO;
import Datos.ProductoDAO;
import Datos.TiendaDAO;
import Datos.TrabajadorDAO;
import Datos.VentaDAO;
import Modelo.Alerta.Alerta;
import Modelo.DetalleVenta;
import Modelo.Incidencia;
import Modelo.Producto;
import Modelo.Tienda;
import Modelo.Trabajador;
import Modelo.Venta;
import impl.com.calendarfx.view.NumericTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class EmpleadoController implements Initializable {

    private TiendaDAO tienda;
    private ProductoDAO producto;
    private TrabajadorDAO trabajador;
    private VentaDAO venta;
    private IncidenciaDAO incidencia;
    private DetallesVentaDAO detallesVenta;
    private Alerta estiloAlerta;
    private ObservableList<Producto> listaProductos;
    private ObservableList<Tienda> listaTiendas;
    private ObservableList<Trabajador> listaTrabajadores;
    private List<DetalleVenta> listaDetalles = new ArrayList<>();
    private LocalDateTime date;
    private String formato;
    private Integer idSiguienteVenta = null;
    private Trabajador empleadoActual;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH':'mm':'ss");
    private ObservableList<String> tiposInciencias = FXCollections.observableArrayList("Robo", "Cliente", "Otros");


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
    private Button bt_anadirProducto;
    @FXML
    private Button bt_incidencias;
    @FXML
    private Pane pn_incidencias;
    @FXML
    private Button bt_añadirIncidencia;
    @FXML
    private Button bt_atrasIncidencias;
    @FXML
    private ComboBox<String> cb_tipoIncidencia;
    @FXML
    private TextArea ta_descripcionIncidencia;
    @FXML
    private DatePicker dp_fechaInciendia;
    @FXML
    private TextField tf_especificarTipoIncidencia;
    @FXML
    private TableView<Producto> tv_productosVenta;
    @FXML
    private TableColumn<Producto, String> tb_nombreVenta;
    @FXML
    private TableColumn<Producto, String> tb_categoriaVenta;
    @FXML
    private TableColumn<Producto, Double> tb_precioVentaVenta;
    @FXML
    private TableColumn<Producto, Integer> tb_stockVenta;
    @FXML
    private Label lb_total;
    @FXML
    private Label lb_TotalTicket;
    @FXML
    private TextArea ta_ticketVenta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        producto = new ProductoDAO(ConexionBD.conexion);
        tienda = new TiendaDAO(ConexionBD.conexion);
        venta = new VentaDAO(ConexionBD.conexion);
        incidencia = new IncidenciaDAO(ConexionBD.conexion);
        trabajador = new TrabajadorDAO(ConexionBD.conexion);
        detallesVenta = new DetallesVentaDAO(ConexionBD.conexion);
        empleadoActual = ConexionBD.actualUser;

        estiloAlerta = new Alerta();
        pn_productos.setVisible(false);
        pn_ventas.setVisible(false);
        pn_incidencias.setVisible(false);
        cb_tipoIncidencia.setItems(tiposInciencias);
        cb_tipoIncidencia.setPromptText("Tipos de Incidencia");
        cb_tipoIncidencia.setValue("Tipos de Incidencia");
        dp_fechaInciendia.setValue(LocalDate.now());
        tf_especificarTipoIncidencia.setVisible(false);
        ta_ticketVenta.setText("NOMBRE          CANTIDAD              PRECIO\n");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH':'mm':'ss");

        cargarProductos();

        tb_nombreVenta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tb_categoriaVenta.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        tb_precioVentaVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        tb_stockVenta.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

    }

    @FXML
    private void CloseAction(ActionEvent event) throws SQLException {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/login/LoginFXML.fxml"));
            ac_empleado.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void inicioAction(ActionEvent event) throws SQLException {

        pn_fondoIconos.setVisible(true);
        pn_productos.setVisible(false);
        pn_ventas.setVisible(false);
        pn_incidencias.setVisible(false);
        limpiarIncidencias();

    }

    @FXML
    private void productosAction(ActionEvent event) {
        Object evento = event.getSource();
        pn_fondoIconos.setVisible(false);
        pn_productos.setVisible(true);

        if (evento == bt_productos) {
            cargarProductos();
        }

        if (evento == bt_atrasProductos) {
            pn_fondoIconos.setVisible(true);
            pn_productos.setVisible(false);
        }

    }

    public void cargarProductos() {
        Alert errorCarga;
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
    }

    public void alertCamposVacios() {
        Alert faltaCampos;
        faltaCampos = new Alert(Alert.AlertType.ERROR);
        faltaCampos.setTitle("Error Añadir");
        faltaCampos.setHeaderText("Por favor rellene todos los campos.");
        estiloAlerta.darleEstiloAlPanel(faltaCampos);
        faltaCampos.showAndWait();

    }

    @FXML
    private void IncidenciasAction(ActionEvent event) {
        Object evento = event.getSource();

        pn_fondoIconos.setVisible(false);
        pn_incidencias.setVisible(true);

        if (evento == bt_atrasIncidencias) {
            pn_fondoIconos.setVisible(true);
            pn_incidencias.setVisible(false);
            limpiarIncidencias();
        }
    }

    @FXML
    private void añadirIncidenciaAction(ActionEvent event) {
        Object evento = event.getSource();
        Incidencia incidencia;
        String tipo = cb_tipoIncidencia.getValue(), especifico = tf_especificarTipoIncidencia.getText(),
                descripcion = ta_descripcionIncidencia.getText();
        Alert errorTipo, errorInsertar, incidenciaCreada;

        if (tipo.equalsIgnoreCase("Otros")) {
            ta_descripcionIncidencia.setLayoutY(148);
            tf_especificarTipoIncidencia.setVisible(true);

        } else if (!tipo.equalsIgnoreCase("Otros")) {
            ta_descripcionIncidencia.setLayoutY(107);
            tf_especificarTipoIncidencia.setVisible(false);
        }

        if (evento == bt_añadirIncidencia) {

            if (tipo.equalsIgnoreCase("Tipos de Incidencia")) {

                errorTipo = new Alert(AlertType.ERROR);
                errorTipo.setTitle("Incidencias Error");
                errorTipo.setHeaderText("Por favor, eliga el tipo de inciencia.");
                estiloAlerta.darleEstiloAlPanel(errorTipo);
                errorTipo.showAndWait();

            } else if (tipo.equalsIgnoreCase("Otros") && especifico.isEmpty()) {

                errorTipo = new Alert(AlertType.ERROR);
                errorTipo.setTitle("Incidencias Error");
                errorTipo.setHeaderText("Por favor, especifique el tipo de "
                        + "incidencia ocurrido.");
                errorTipo.setContentText("Normalmente con una o dos palabras basta");
                estiloAlerta.darleEstiloAlPanel(errorTipo);
                errorTipo.showAndWait();

            } else {
                /*(idIncidencia,idTienda,idTrabajador,tipo,fecha,descripcion,leido)*/
                if (tf_especificarTipoIncidencia.isVisible()) {
                    descripcion = especifico + ": " + descripcion;

                }

                incidencia = new Incidencia(java.sql.Types.NULL,
                        empleadoActual.getIdTienda(),
                        empleadoActual.getId(),
                        tipo,
                        dp_fechaInciendia.getValue(),
                        descripcion,
                        "No leido");

                try {
                    this.incidencia.crearIncidencia(incidencia, empleadoActual);
                    incidenciaCreada = new Alert(AlertType.INFORMATION);
                    incidenciaCreada.setTitle("Incidencias");
                    incidenciaCreada.setHeaderText("Incidencia creada con exito.");
                    incidenciaCreada.setContentText(empleadoActual.getNombre()
                            + ", gracias por informar de la incidencia");
                    estiloAlerta.darleEstiloAlPanel(incidenciaCreada);
                    incidenciaCreada.showAndWait();

                } catch (SQLException ex) {
                    errorInsertar = new Alert(AlertType.ERROR);
                    errorInsertar.setTitle("Error Insertar");
                    errorInsertar.setHeaderText("No se ha podido insertar la incidencia.");
                    errorInsertar.setContentText("Error: " + ex.getMessage());
                    estiloAlerta.darleEstiloAlPanel(errorInsertar);
                    errorInsertar.showAndWait();
                }
            }
        }

    }

    public void limpiarIncidencias() {
        ta_descripcionIncidencia.clear();
        dp_fechaInciendia.setValue(LocalDate.now());
        ta_descripcionIncidencia.setLayoutY(107);
        tf_especificarTipoIncidencia.setVisible(false);
        cb_tipoIncidencia.setPromptText("Tipos de Incidencia");
        cb_tipoIncidencia.setValue("Tipos de Incidencia");
        tf_especificarTipoIncidencia.clear();
    }

    @FXML
    private void perfilAction(ActionEvent event) {

    }

    @FXML
    private void ventasAction(ActionEvent event) {
        Object evento = event.getSource();

        if (evento == bt_ventas) {
            pn_fondoIconos.setVisible(false);
            pn_ventas.setVisible(true);
            cargarProductosParaVenta();
            idSiguienteVenta = siguienteIdVenta();
        } else if (evento == bt_atrasVentas) {
            pn_fondoIconos.setVisible(true);
            pn_ventas.setVisible(false);
            limpiarVentas();
        } else if (evento == bt_Inicio) {
            pn_fondoIconos.setVisible(true);
            pn_ventas.setVisible(false);
            limpiarVentas();
        }

    }

    public void cargarProductosParaVenta() {
        Alert errorCarga;
        try {
            listaProductos = FXCollections.observableArrayList(producto.cargarProductos(empleadoActual.getIdTienda()));
            tv_productosVenta.setItems(listaProductos);
        } catch (SQLException ex) {
            errorCarga = new Alert(AlertType.ERROR);
            errorCarga.setTitle("Error");
            errorCarga.setHeaderText("No se ha podido cargar los productos de tienda.");
            errorCarga.setContentText("Error: " + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }

    }

    public void limpiarVentas() {
        listaDetalles.clear();
        ta_ticketVenta.setText("NOMBRE          CANTIDAD              PRECIO\n");
    }

    @FXML
    private void añadirProductoAction(MouseEvent event) {
        Object evento = event.getSource();
        Producto seleccionado = tv_productosVenta.getFocusModel().getFocusedItem();
        MouseButton click = event.getButton();
        Alert elegirCantidad, errorCantidad;
        DetalleVenta detalle;
        int cantidad;
        Double precioTotal;

        if (click.equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                elegirCantidad = new Alert(AlertType.CONFIRMATION);
                elegirCantidad.setTitle("Cantidad");
                elegirCantidad.setHeaderText("Producto selecciondo : " + seleccionado.getNombre());

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                NumericTextField nf_cantidad = new NumericTextField();
                nf_cantidad.setPromptText("Cantidad");
                grid.add(nf_cantidad, 1, 0);
                elegirCantidad.getDialogPane().setContent(grid);
                estiloAlerta.darleEstiloAlPanel(elegirCantidad);
                Optional<ButtonType> resultado;
                resultado = elegirCantidad.showAndWait();

                if (resultado.get() == ButtonType.OK) {
                    cantidad = Integer.valueOf(nf_cantidad.getText());
                    try {
                        if (cantidad <= producto.cantidad(empleadoActual.getIdTienda(), seleccionado)) {

                            precioTotal = seleccionado.getPrecioVenta() * cantidad;

                            /*(int idVenta, int referencia, int cantidad, Double precio)*/
                            detalle = new DetalleVenta(idSiguienteVenta,
                                    seleccionado.getReferencia(),
                                    cantidad,
                                    precioTotal);

                            listaDetalles.add(detalle);

                            ta_ticketVenta.appendText(String.format("%-25s  %15s  %15s",
                                    seleccionado.getNombre(), nf_cantidad.getText(),
                                    (seleccionado.getPrecioVenta() * cantidad)) + "\n");
                        } else {
                            errorCantidad = new Alert(AlertType.WARNING);
                            errorCantidad.setTitle("Cantidad");
                            errorCantidad.setHeaderText("Cantidad mayor a la disponible"
                                    + " en el almacen.");
                            errorCantidad.setContentText(empleadoActual.getNombre()
                                    + ", diculpa las molestias.");
                            estiloAlerta.darleEstiloAlPanel(errorCantidad);
                            errorCantidad.showAndWait();
                        }
                    } catch (SQLException ex) {
                        errorCantidad = new Alert(AlertType.ERROR);
                        errorCantidad.setTitle("Error");
                        errorCantidad.setHeaderText("No se ha podido comprobar las cantidades.");
                        errorCantidad.setContentText("Error: " + ex.getMessage());
                        estiloAlerta.darleEstiloAlPanel(errorCantidad);
                        errorCantidad.showAndWait();
                    }

                }

                if (resultado.get() == ButtonType.CANCEL) {

                }
            }
        }
    }

    public Integer siguienteIdVenta() {
        Alert errorCargaId;
        Integer idVenta = null;

        try {
            idVenta = this.venta.mostrarSiguienteID();
        } catch (SQLException ex) {
            errorCargaId = new Alert(AlertType.ERROR);
            errorCargaId.setTitle("Error");
            errorCargaId.setHeaderText("No se ha podido cargar el id de la siguiente venta");
            errorCargaId.setContentText("Error: " + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCargaId);
            errorCargaId.showAndWait();
        }

        return idVenta;
    }

    @FXML
    private void generarVentaAction(ActionEvent event) {
        Alert sinProductos, errorVenta, ventaRealizada;
        Venta venta;

        if (!listaDetalles.isEmpty()) {
            /*(int idVenta, int idTienda, int idtrabajdor, LocalDate fecha, List<DetalleVenta> detalle)*/
            venta = new Venta(idSiguienteVenta,
                    empleadoActual.getIdTienda(),
                    empleadoActual.getId(),
                    LocalDate.now(),
                    listaDetalles);

            try {
                this.venta.insertarVenta(venta, listaDetalles);
                detallesVenta.detallesVenta(listaDetalles,empleadoActual.getIdTienda());
                
                
                
                
                ventaRealizada = new Alert(AlertType.INFORMATION);
                ventaRealizada.setTitle("Venta");
                ventaRealizada.setHeaderText("La venta con id: " + venta.getIdVenta()
                        + " se ha realizado con exito.");
                estiloAlerta.darleEstiloAlPanel(ventaRealizada);
                ventaRealizada.showAndWait();

            } catch (SQLException ex) {
                errorVenta = new Alert(AlertType.ERROR);
                errorVenta.setTitle("Error Venta");
                errorVenta.setHeaderText("No se ha podido generar la venta.");
                errorVenta.setContentText("Error: " + ex.getMessage());
                estiloAlerta.darleEstiloAlPanel(errorVenta);
                errorVenta.showAndWait();
            }

        } else {
            sinProductos = new Alert(AlertType.ERROR);
            sinProductos.setTitle("Error");
            sinProductos.setHeaderText("Seleccione un producto antes.");
            estiloAlerta.darleEstiloAlPanel(sinProductos);
            sinProductos.showAndWait();
        }

    }

}
