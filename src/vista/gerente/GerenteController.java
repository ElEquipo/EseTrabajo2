package vista.gerente;

import Datos.ConexionBD;
import Datos.ProductoDAO;
import Datos.TiendaDAO;
import Datos.TrabajadorDAO;
import Modelo.Producto;
import Modelo.Tienda;
import Modelo.Trabajador;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import vista.Empleado.EmpleadoController;

public class GerenteController implements Initializable {

    private TrabajadorDAO trabajador;
    private TiendaDAO tienda;
    private ProductoDAO producto;
    private ObservableList<Tienda> listaTiendas;
    private ObservableList<Producto> listaProductos;
    /*ATRIBUTOS FXML*/
    @FXML
    private AnchorPane ac_gerente;
    @FXML
    private Pane pn_general;
    @FXML
    private Pane pn_titulo;
    @FXML
    private Button bt_home;
    @FXML
    private Button bt_perfil;
    @FXML
    private Button bt_ayuda;
    @FXML
    private Button bt_cerrarSesion;
    @FXML
    private Button bt_despedirPersonal;
    @FXML
    private Button bt_contratarPersonal;
    @FXML
    private Button bt_atras;
    @FXML
    private Button bt_incidencias;
    @FXML
    private Button bt_productos;
    @FXML
    private Button bt_tienda;
    @FXML
    private Button bt_personal;
    @FXML
    private Pane pn_inicio;
    @FXML
    private TextField tf_nombre;
    @FXML
    private TextField tf_apellido1;
    @FXML
    private TextField tf_apellido2;
    @FXML
    private TextField tf_puesto;
    @FXML
    private TextField tf_salario;
    @FXML
    private TextField tf_nick;
    @FXML
    private TextField tf_horaEntrada;
    @FXML
    private TextField tf_horaSalida;
    @FXML
    private TextField tf_dni;
    @FXML
    private Button bt_contratar;
    @FXML
    private Pane pn_menuTrabajadores;
    @FXML
    private Pane pn_contratar;
    @FXML
    private Pane pn_productos;
    @FXML
    private TableView<Producto> tv_productos;
    @FXML
    private Button bt_atrasContratar;
    @FXML
    private Button bt_atrasProductos;
    @FXML
    private Pane pn_despedir;
    @FXML
    private Button bt_atrasDespedir;
    @FXML
    private Button bt_despedir;
    @FXML
    private TextField tf_apellido2Despedir;
    @FXML
    private TextField tf_apellido1Despedir;
    @FXML
    private TextField tf_nombreDespedir;
    @FXML
    private TextField tf_dniDespedir;
    @FXML
    private TextField tf_idDespedir;
    @FXML
    private DatePicker dp_fecha;
    @FXML
    private Label lb_id;
    @FXML
    private ComboBox<Tienda> cb_tiendas;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trabajador = new TrabajadorDAO(ConexionBD.conexion);
        tienda = new TiendaDAO(ConexionBD.conexion);
        producto = new ProductoDAO(ConexionBD.conexion);
        pn_menuTrabajadores.setVisible(false);
        pn_contratar.setVisible(false);
        pn_productos.setVisible(false);
        pn_despedir.setVisible(false);
        try {
            lb_id.setText(" " + String.valueOf(trabajador.mostrarSiguienteID()));
        } catch (SQLException ex) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Id");
            alerta.setHeaderText("Error al cargar el siguiente id \n" + ex.getMessage());
            darleEstiloAlPanel(alerta);
            alerta.showAndWait();
        }
        try {
            listaTiendas = FXCollections.observableArrayList(tienda.cargarDatos());
            cb_tiendas.setItems(listaTiendas);

        } catch (SQLException ex) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Carga Tiendas");
            alerta.setHeaderText("Error al cargar la lista de tiendas \n" + ex.getMessage());
            darleEstiloAlPanel(alerta);
            alerta.showAndWait();
        }
        
        try {            
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
            alerta.setHeaderText("Error al cargar la lista de productos \n" + ex.getMessage());
            darleEstiloAlPanel(alerta);
            alerta.showAndWait();
        }
        
        try {
            listaTiendas = FXCollections.observableArrayList(tienda.cargarDatos());
            cb_tiendas.setItems(listaTiendas);

        } catch (SQLException ex) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Carga Tiendas");
            alerta.setHeaderText("Error al cargar la lista de tiendas \n" + ex.getMessage());
            darleEstiloAlPanel(alerta);
            alerta.showAndWait();
        }

        cargarTooltips();
    }

    public void cargarTooltips() {
        Tooltip tt_personal, tt_contratar, tt_despedir, tt_atras, tt_incidencias, tt_productos, tt_tienda;

        tt_personal = new Tooltip("Personal");
        tt_personal.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_personal, tt_personal);

        tt_incidencias = new Tooltip("Incidencias");
        tt_incidencias.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_incidencias, tt_incidencias);

        tt_productos = new Tooltip("Productos");
        tt_productos.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_productos, tt_productos);

        tt_tienda = new Tooltip("Tiendas");
        tt_tienda.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_tienda, tt_tienda);

        tt_contratar = new Tooltip("Contratar trabajador");
        tt_contratar.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_contratarPersonal, tt_contratar);

        tt_despedir = new Tooltip("Despedir trabajador");
        tt_despedir.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_despedirPersonal, tt_despedir);

        tt_atras = new Tooltip("Volver");
        tt_atras.setStyle("-fx-background-color:rgba(153, 153, 153,0.5);"
                + " -fx-text-fill:orange; -fx-font-size:16px;");
        Tooltip.install(bt_atras, tt_atras);
        Tooltip.install(bt_atrasDespedir, tt_atras);
        Tooltip.install(bt_atrasContratar, tt_atras);
        Tooltip.install(bt_atrasProductos, tt_atras);

    }

    @FXML
    private void trabajadoresAction(ActionEvent event) {

        if (bt_personal.isFocused()) {
            pn_inicio.setVisible(false);
            pn_menuTrabajadores.setVisible(true);
        }

        if (bt_atras.isFocused()) {
            pn_inicio.setVisible(true);
            pn_menuTrabajadores.setVisible(false);
        }

    }

    @FXML
    private void CloseAction(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/login/LoginFXML.fxml"));
            ac_gerente.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void contratarAction(ActionEvent event) {
        Object evento = event.getSource();
        if (evento == bt_contratarPersonal) { // ACCEDE AL MENU DE INTRODUCCION DE DATOS
            pn_menuTrabajadores.setVisible(false);
            pn_contratar.setVisible(true);
        }

        if (evento == bt_atrasContratar) {
            pn_menuTrabajadores.setVisible(true);
            pn_contratar.setVisible(false);
        }

        if (evento == bt_contratar) { // INSERTA EN LA BD
            contratar();
        }
    }

    public void contratar() {
        Alert alerta;
        String pass = null;
        List<String> camposVacios = new ArrayList<>();
        String dni = tf_dni.getText(), nombre = tf_nombre.getText(), apellido1 = tf_apellido1.getText(),
                apellido2 = tf_apellido2.getText(), puesto = tf_puesto.getText(), nick = tf_nick.getText(),
                salariotext = tf_salario.getText(),
                horaEntradaText = tf_horaEntrada.getText(), horaSalidatext = tf_horaSalida.getText();
        Integer idTienda = null;
        Double salario = null;
        LocalDate fecha;
        LocalTime horaEntrada = null, horaSalida = null;

        try {

            if (nombre.isEmpty()) {
                camposVacios.add("Nombre");
            }

            if (dni.isEmpty()) {
                camposVacios.add("DNI");
            }

            if (puesto.isEmpty()) {
                camposVacios.add("Puesto");
            }

            if (dp_fecha.getValue() == null) {
                fecha = LocalDate.now();
            } else {
                fecha = dp_fecha.getValue();
            }

            if (salariotext.isEmpty()) {
                camposVacios.add("Salario");
            } else {
                salario = Double.parseDouble(salariotext);
            }

            if (nick.isEmpty()) {
                camposVacios.add("Nick");
            }else{
                pass = dni;
            }

            if (cb_tiendas.getSelectionModel().isEmpty()) {
                camposVacios.add("Tienda");
            } else {
                idTienda = cb_tiendas.getSelectionModel().getSelectedItem().getId();
            }

            if (horaEntradaText.isEmpty()) {
                camposVacios.add("Hora entrada");
            } else {
                horaEntrada = LocalTime.parse(horaEntradaText);
            }

            if (horaSalidatext.isEmpty()) {
                camposVacios.add("Hora salida");
            } else {
                horaSalida = LocalTime.parse(horaSalidatext);
            }

            if (!camposVacios.isEmpty()) {
                alerta = new Alert(AlertType.WARNING);
                alerta.setTitle("Error Ingresar");
                alerta.setHeaderText("Rellene los campos obligatorios (naranja).");
                alerta.setContentText("Campos Vacios: " + camposVacios.toString());
                darleEstiloAlPanel(alerta);
                alerta.showAndWait();
            } else {
                Trabajador trabajador = new Trabajador(this.trabajador.mostrarSiguienteID(), dni, nombre, apellido1, apellido2, puesto, salario, fecha, nick, pass, horaEntrada, horaSalida, idTienda);
                this.trabajador.insertar(trabajador);
                lb_id.setText(" " + this.trabajador.mostrarSiguienteID());
            }

        } catch (NumberFormatException | NullPointerException e) {
            alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Tipo dato");
            alerta.setContentText(e.getMessage());
            darleEstiloAlPanel(alerta);
            alerta.showAndWait();

        } catch (SQLException ex) {
            alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error Introducir");
            alerta.setContentText(ex.getMessage() + " " + ex.getErrorCode());
            darleEstiloAlPanel(alerta);
            alerta.showAndWait();
        } catch (Exception e) {
            alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setContentText(e.getMessage());
            darleEstiloAlPanel(alerta);
            alerta.showAndWait();
        }
    }

    @FXML
    private void despedirAction(ActionEvent event) {
        Object evento = event.getSource();

        if (evento == bt_despedirPersonal) { // ACCEDE AL MENU DE INTRODUCCION DE DATOS
            pn_menuTrabajadores.setVisible(false);
            pn_despedir.setVisible(true);
        }

        if (evento == bt_atrasDespedir) {
            pn_menuTrabajadores.setVisible(true);
            pn_despedir.setVisible(false);
        }

        if (evento == bt_despedir) { // INSERTA EN LA BD

        }

    }

    @FXML
    private void productosAction(ActionEvent event) {

        pn_productos.setVisible(true);
        pn_inicio.setVisible(false);

        if (bt_atrasProductos.isFocused()) {
            pn_productos.setVisible(false);
            pn_inicio.setVisible(true);
        }
    }

    @FXML
    private void inicioAction(ActionEvent event) { // VUELVE AL INICIO
        pn_inicio.setVisible(true);
        pn_contratar.setVisible(false);
        pn_menuTrabajadores.setVisible(false);
        pn_productos.setVisible(false);
        limpiarCampos();
    }

    public void limpiarCampos() {
        tf_dni.clear();
        tf_nombre.clear();
        tf_apellido1.clear();
        tf_apellido2.clear();
        tf_puesto.clear();
        tf_salario.clear();
        tf_nick.clear();
        tf_horaEntrada.clear();
        tf_horaSalida.clear();
    }
    
    
    private void darleEstiloAlPanel(Alert panel) {
        DialogPane dialogPane;
        Stage alertaStage;

        dialogPane = panel.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("gerente.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        alertaStage = (Stage) panel.getDialogPane().getScene().getWindow();
        // COGER LA RUTA DEL ICONO
        // alertaStage.getIcons().add(new Image("file:/images/icon.png"));
    }

}
