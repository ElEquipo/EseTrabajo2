package vista.gerente;

import Datos.ConexionBD;
import Datos.ProductoDAO;
import Datos.TiendaDAO;
import Datos.TrabajadorDAO;
import Modelo.Alerta.Alerta;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.LocalTimePicker;
import org.jasypt.util.password.StrongPasswordEncryptor;
import vista.Empleado.EmpleadoController;

public class GerenteController implements Initializable {

    private TrabajadorDAO trabajador;
    private TiendaDAO tienda;
    private ProductoDAO producto;
    private ObservableList<Tienda> listaTiendas;
    private ObservableList<Producto> listaProductos;
    private Alerta estiloAlerta;
    private Trabajador gerenteActual;
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
    /*IDEAL PARA EL GERENTE DE VARIAS TIENDAS
    private ComboBox<Tienda> cb_tiendas;*/
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
    private LocalTimePicker dp_horaEntrada;
    @FXML
    private Label lb_fondoHoraEntrada;
    @FXML
    private Label lb_fondoHoraSalida;
    @FXML
    private LocalTimePicker dp_horaSalida;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trabajador = new TrabajadorDAO(ConexionBD.conexion);
        tienda = new TiendaDAO(ConexionBD.conexion);
        producto = new ProductoDAO(ConexionBD.conexion);
        gerenteActual = ConexionBD.conectado;
        estiloAlerta = new Alerta();
        pn_menuTrabajadores.setVisible(false);
        pn_contratar.setVisible(false);
        pn_productos.setVisible(false);
        pn_despedir.setVisible(false);
        dp_horaEntrada.setLocalTime(LocalTime.MIDNIGHT);
        dp_horaSalida.setLocalTime(LocalTime.MIDNIGHT);
        Alert errorCarga;
        try {
            lb_id.setText(" " + String.valueOf(trabajador.mostrarSiguienteID()));
        } catch (SQLException ex) {
            errorCarga = new Alert(AlertType.ERROR);
            errorCarga.setTitle("Error Id");
            errorCarga.setHeaderText("Error al cargar el siguiente id \n" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }
        /*
        try {
            ESTO SERÍA IDEAL SI CONTEMPLASEMOS QUE UN GERENTE REGENTA VARIAS TIENDAS
            listaTiendas = FXCollections.observableArrayList(tienda.cargarDatos());
            cb_tiendas.setItems(listaTiendas);

        } catch (SQLException ex) {
            errorCarga = new Alert(AlertType.ERROR);
            errorCarga.setTitle("Error Carga Tiendas");
            errorCarga.setHeaderText("Error al cargar la lista de tiendas \n" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }*/

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
            errorCarga = new Alert(Alert.AlertType.ERROR);
            errorCarga.setTitle("Error Carga Productos");
            errorCarga.setHeaderText("Error al cargar la lista de productos \n" + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
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
            dp_fecha.setValue(LocalDate.now());
        }

        if (evento == bt_atrasContratar) {
            pn_menuTrabajadores.setVisible(true);
            pn_contratar.setVisible(false);
            limpiarCampos();
        }

        if (evento == bt_contratar) { // INSERTA EN LA BD
            contratar();
        }
    }

    public void contratar() {
        Alert camposRestantes, errorIngresar;
        StrongPasswordEncryptor passwordEncryptor;
        String passEncriptada = null;
        List<String> camposVacios = new ArrayList<>();
        String dni = tf_dni.getText(), nombre = tf_nombre.getText(), apellido1 = tf_apellido1.getText(),
                apellido2 = tf_apellido2.getText(), puesto = tf_puesto.getText(), nick = tf_nick.getText(),
                salariotext = tf_salario.getText();
        Integer idTienda = gerenteActual.getIdTienda();
        Double salario = null;
        LocalDate fecha;
        LocalTime horaEntrada = dp_horaEntrada.getLocalTime(), horaSalida = dp_horaSalida.getLocalTime();

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
            } else {
                passwordEncryptor = new StrongPasswordEncryptor();
                passEncriptada = passwordEncryptor.encryptPassword(dni);

            }

            /* IDEAL GERENTE VARIAS TIENDAS
            if (cb_tiendas.getSelectionModel().isEmpty()) {
                camposVacios.add("Tienda");
            } else {
                idTienda = cb_tiendas.getSelectionModel().getSelectedItem().getId();
            }*/
            

            if (!camposVacios.isEmpty()) {
                camposRestantes = new Alert(AlertType.WARNING);
                camposRestantes.setTitle("Error Ingresar");
                camposRestantes.setHeaderText("Rellene los campos obligatorios (naranja).");
                camposRestantes.setContentText("Campos Vacios: " + camposVacios.toString());
                estiloAlerta.darleEstiloAlPanel(camposRestantes);
                camposRestantes.showAndWait();

            } else if (horaEntrada.equals(LocalTime.MIDNIGHT) || horaSalida.equals(LocalTime.MIDNIGHT)) {
                camposRestantes = new Alert(AlertType.INFORMATION);
                camposRestantes.setTitle("Horarios");
                camposRestantes.setHeaderText("Recuerde revisar los horarios.");
                estiloAlerta.darleEstiloAlPanel(camposRestantes);
                camposRestantes.showAndWait();

            } else {
                Trabajador trabajador = new Trabajador(this.trabajador.mostrarSiguienteID(), dni, nombre, apellido1, apellido2, puesto, salario, fecha, nick, passEncriptada, horaEntrada, horaSalida, idTienda);
                this.trabajador.insertar(trabajador);
                lb_id.setText(" " + this.trabajador.mostrarSiguienteID());
            }

        } catch (NumberFormatException | NullPointerException e) {
            errorIngresar = new Alert(AlertType.ERROR);
            errorIngresar.setTitle("Error Tipo dato");
            errorIngresar.setContentText(e.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorIngresar);
            errorIngresar.showAndWait();

        } catch (SQLException ex) {
            errorIngresar = new Alert(AlertType.ERROR);
            errorIngresar.setTitle("Error Introducir");
            errorIngresar.setContentText(ex.getMessage() + " " + ex.getErrorCode());
            estiloAlerta.darleEstiloAlPanel(errorIngresar);
            errorIngresar.showAndWait();
        } catch (Exception e) {
            errorIngresar = new Alert(AlertType.ERROR);
            errorIngresar.setTitle("Error");
            errorIngresar.setContentText(e.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorIngresar);
            errorIngresar.showAndWait();
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
        dp_horaEntrada.setLocalTime(LocalTime.MIDNIGHT);
        dp_horaSalida.setLocalTime(LocalTime.MIDNIGHT);
        dp_fecha.setValue(LocalDate.now());

    }

}
