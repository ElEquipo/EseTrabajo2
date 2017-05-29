package vista.gerente;

import Datos.ConexionBD;
import Datos.ProductoDAO;
import Datos.TiendaDAO;
import Datos.TrabajadorDAO;
import Modelo.Alerta.Alerta;
import Modelo.Producto;
import Modelo.Tienda;
import Modelo.Trabajador;
import Modelo.ValidadorDNI;
import impl.com.calendarfx.view.NumericTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jfxtras.scene.control.LocalTimePicker;
import org.jasypt.util.password.StrongPasswordEncryptor;
import vista.Empleado.EmpleadoController;

public class GerenteController implements Initializable {

    private TrabajadorDAO trabajador;

    private TiendaDAO tienda;
    private ProductoDAO producto;
    /*IDEAL PARA GERENTE DE VARIAS TIENDAS
    private ObservableList<Tienda> listaTiendas;*/
    private ObservableList<Trabajador> listaObservableTrabajadores;
    private ObservableList<Producto> listaProductos;
    private Alerta estiloAlerta;
    private Trabajador gerenteActual;
    private ValidadorDNI validadorDni;
    private String eleccion;
    private boolean campoDespedirVacio;
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
    @FXML
    private NumericTextField nf_salario;
    @FXML
    private TextField tf_busquedaTexto;
    @FXML
    private ComboBox<String> cb_busquedaDespedir;
    @FXML
    private Button bt_buscar;
    @FXML
    private TableView<Trabajador> tv_empleado;
    @FXML
    private TableColumn<Trabajador, Integer> tc_id;
    @FXML
    private TableColumn<Trabajador, String> tc_dni;
    @FXML
    private TableColumn<Trabajador, String> tc_nombre;
    @FXML
    private TableColumn<Trabajador, String> tc_apellido1;
    @FXML
    private TableColumn<Trabajador, String> tc_apellido2;
    @FXML
    private TableColumn<Trabajador, String> tc_puesto;
    @FXML
    private TableColumn<Trabajador, Double> tc_salario;
    @FXML
    private TableColumn<Trabajador, LocalDate> tc_fechaAlta;
    @FXML
    private TableColumn<Trabajador, String> tc_nick;
    @FXML
    private TableColumn<Trabajador, TableColumn> tc_horario;
    @FXML
    private TableColumn<Trabajador, LocalTime> tc_horaEntrada;
    @FXML
    private TableColumn<Trabajador, LocalTime> tc_horaSalida;
    @FXML
    private TableColumn<Trabajador, Integer> tc_idTienda;
    @FXML
    private TextArea ta_datosTrabajador;
    @FXML
    private NumericTextField nf_busquedaNumerica;
    @FXML
    private Pane pn_tienda;
    @FXML
    private Button bt_atrasVerTrabajadores;
    @FXML
    private Button bt_irPaneDespedir;
    @FXML
    private Button bt_irVerTrabajadores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Alert errorCarga;
        ObservableList<String> tiposBusqueda = FXCollections.observableArrayList("ID", "DNI");
        trabajador = new TrabajadorDAO(ConexionBD.actualUser);
        tienda = new TiendaDAO(ConexionBD.actualUser);
        producto = new ProductoDAO(ConexionBD.actualUser);
        validadorDni = new ValidadorDNI();
        gerenteActual = ConexionBD.conectado;
        estiloAlerta = new Alerta();
        pn_menuTrabajadores.setVisible(false);
        pn_contratar.setVisible(false);
        pn_productos.setVisible(false);
        pn_despedir.setVisible(false);
        pn_tienda.setVisible(false);
        dp_horaEntrada.setLocalTime(LocalTime.MIDNIGHT);
        dp_horaSalida.setLocalTime(LocalTime.MIDNIGHT);
        cb_busquedaDespedir.setItems(tiposBusqueda);
        ta_datosTrabajador.setVisible(false);
        ta_datosTrabajador.setDisable(true);
        ta_datosTrabajador.setVisible(false);
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

        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tc_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tc_apellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        tc_apellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        tc_puesto.setCellValueFactory(new PropertyValueFactory<>("puesto"));
        tc_salario.setCellValueFactory(new PropertyValueFactory<>("salarioBrutoAnual"));
        tc_fechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        tc_nick.setCellValueFactory(new PropertyValueFactory<>("nick"));
        tc_horaEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        tc_horaSalida.setCellValueFactory(new PropertyValueFactory<>("horaSalida"));
        tc_idTienda.setCellValueFactory(new PropertyValueFactory<>("idTienda"));

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

        tt_tienda = new Tooltip("Tienda");
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
            limpiarCamposContratar();
        }

        if (evento == bt_contratar) { // INSERTA EN LA BD
            contratar();
        }
    }

    public void contratar() {
        Alert camposRestantes, errorIngresar, contratoCorrecto, dniIncorrecto;
        StrongPasswordEncryptor passwordEncryptor;
        String passEncriptada = null;
        List<String> camposVacios = new ArrayList<>();
        String dni = tf_dni.getText(), nombre = tf_nombre.getText(), apellido1 = tf_apellido1.getText(),
                apellido2 = tf_apellido2.getText(), puesto = tf_puesto.getText(), nick = tf_nick.getText(),
                salariotext = nf_salario.getText();
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
                camposRestantes.setHeaderText("Revise los horarios.");
                camposRestantes.setContentText("No pueden estar a 00:00.");
                estiloAlerta.darleEstiloAlPanel(camposRestantes);
                camposRestantes.showAndWait();

            } else if (!validadorDni.validar(dni)) {
                dniIncorrecto = new Alert(AlertType.ERROR);
                dniIncorrecto.setTitle("DNI ERRROR");
                dniIncorrecto.setHeaderText("DNI incorrecto");
                dniIncorrecto.setContentText("Por favor, introduzca un dni con "
                        + "formato correcto, ej;(12345678P).\n"
                        + "Además debe de ser un DNI válido.");
                estiloAlerta.darleEstiloAlPanel(dniIncorrecto);
                dniIncorrecto.showAndWait();

            } else {
                Trabajador trabajador = new Trabajador(this.trabajador.mostrarSiguienteID(),
                        dni,
                        nombre,
                        apellido1,
                        apellido2,
                        puesto,
                        salario,
                        fecha,
                        nick,
                        passEncriptada,
                        horaEntrada,
                        horaSalida,
                        idTienda);
                this.trabajador.insertar(trabajador);
                contratoCorrecto = new Alert(AlertType.INFORMATION);
                contratoCorrecto.setTitle("Contratado");
                contratoCorrecto.setHeaderText("Ingresado correctamente.");
                contratoCorrecto.setContentText(trabajador.getNombre() + " " + trabajador.getApellido1() + " "
                        + trabajador.getApellido2() + " ahora pertence a nuestra plantilla"
                        + " desenpeñando el cargo de " + trabajador.getPuesto() + ".\n Le deseamos"
                        + " suerte en su nueva etapa.");
                estiloAlerta.darleEstiloAlPanel(contratoCorrecto);
                contratoCorrecto.showAndWait();
                lb_id.setText(" " + this.trabajador.mostrarSiguienteID());
            }

        } catch (NumberFormatException e) {
            errorIngresar = new Alert(AlertType.ERROR);
            errorIngresar.setTitle("Error Tipo dato");
            errorIngresar.setContentText(e.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorIngresar);
            errorIngresar.showAndWait();

        } catch (NullPointerException e) {
            errorIngresar = new Alert(AlertType.ERROR);
            errorIngresar.setTitle("Vacio");
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
            cb_busquedaDespedir.setValue("ID");
            elegirDespedir();
        }

        if (evento == bt_atrasDespedir) {
            pn_menuTrabajadores.setVisible(true);
            pn_despedir.setVisible(false);
            limpiarCamposDespedir();
        }

        if (evento == bt_despedir) { // ELIMINA EN LA BD
            despedir();
        }

    }

    @FXML
    private void elegirDespedirAction(ActionEvent event) {
        elegirDespedir();
    }

    public void elegirDespedir() {
        /* PARECE QUE ESTE METODOD ESTA REPETIDO CON EL "BuscarDespedirAction"
        , PERO ESTA HECHO ASÍ PORQUE ESTE QUIERO QUE SOLO SE USE AL ENTRAR EN EL 
        MENU O AL CAMBIAR DE ELECCIÓN Y ASÍ HABLITAR O DESHABILITAR EL 
        TEXTFIELD CORRESPONDIENTE*/
        eleccion = cb_busquedaDespedir.getValue();

        if (eleccion.equalsIgnoreCase("ID")) {
            nf_busquedaNumerica.setVisible(true);
            nf_busquedaNumerica.setDisable(false);
            tf_busquedaTexto.setVisible(false);
            tf_busquedaTexto.setDisable(true);
            nf_busquedaNumerica.setPromptText(eleccion);
            limpiarCamposDespedir();
        } else {
            nf_busquedaNumerica.setVisible(false);
            nf_busquedaNumerica.setDisable(true);
            tf_busquedaTexto.setVisible(true);
            tf_busquedaTexto.setDisable(false);
            tf_busquedaTexto.setPromptText(eleccion);
            limpiarCamposDespedir();
        }

    }

    @FXML
    private void buscarDespedirAction(ActionEvent event) {
        Object evento = event.getSource();
        if (evento == bt_despedir) {
            buscarDespedir();
        }

        if (evento == bt_irVerTrabajadores) {
            pn_despedir.setVisible(false);
            pn_tienda.setVisible(true);
            verTrajadores();
        }
    }

    private void buscarDespedir() {
        Trabajador trabajador;
        String busqueda = "";
        campoDespedirVacio = false;
        Alert campoVacio, errorBusqueda;

        try {
            if (eleccion.equalsIgnoreCase("ID")) {
                busqueda = nf_busquedaNumerica.getText();
                if (!busqueda.isEmpty()) {
                    trabajador = this.trabajador.cargarTrabajador(busqueda, 1);
                    ta_datosTrabajador.setText(trabajador.verDatos());
                    ta_datosTrabajador.setVisible(true);
                    ta_datosTrabajador.setDisable(false);
                } else {
                    campoDespedirVacio = true;
                }
            } else {
                busqueda = tf_busquedaTexto.getText();
                if (!busqueda.isEmpty()) {
                    trabajador = this.trabajador.cargarTrabajador(busqueda, 2);
                    ta_datosTrabajador.setText(trabajador.verDatos());
                    ta_datosTrabajador.setVisible(true);
                    ta_datosTrabajador.setDisable(false);
                } else {
                    campoDespedirVacio = true;
                }
            }

        } catch (SQLException e) {
            errorBusqueda = new Alert(Alert.AlertType.ERROR);
            errorBusqueda.setTitle("Busqueda");
            errorBusqueda.setHeaderText("Error en la busqueda");
            errorBusqueda.setContentText("El trabajador con " + eleccion + ": "
                    + busqueda + " no existe.");
            estiloAlerta.darleEstiloAlPanel(errorBusqueda);
            errorBusqueda.showAndWait();
            ta_datosTrabajador.setVisible(false);
            ta_datosTrabajador.setDisable(true);
        }

        if (campoDespedirVacio) {
            campoVacio = new Alert(AlertType.ERROR);
            campoVacio.setTitle("Busqueda");
            campoVacio.setHeaderText("Error en la busqueda");
            campoVacio.setContentText("Por favor rellene el campo de busqueda.");
            estiloAlerta.darleEstiloAlPanel(campoVacio);
            campoVacio.showAndWait();
            ta_datosTrabajador.setVisible(false);
            ta_datosTrabajador.setDisable(true);
        }
    }

    private void despedir() {
        Alert sinBuscar, errorDespedir, confirmacionBorrado, despedirCorrecto;
        String buscando;
        int modo;

        if (eleccion.equalsIgnoreCase("ID")) {
            buscando = nf_busquedaNumerica.getText();
            modo = 0;
        } else {
            buscando = tf_busquedaTexto.getText();
            modo = 1;
        }

        if (ta_datosTrabajador.isVisible()) {
            try {
                confirmacionBorrado = new Alert(AlertType.CONFIRMATION);
                confirmacionBorrado.setTitle("Despedir");
                confirmacionBorrado.setHeaderText("¿Esta seguro?");
                confirmacionBorrado.setContentText("¿Quiere despedir al trabajor con "
                        + eleccion + ": " + buscando + " y por consiguiente borrarlo"
                        + " de la base de datos?");
                estiloAlerta.darleEstiloAlPanel(confirmacionBorrado);
                Optional<ButtonType> resultado = confirmacionBorrado.showAndWait();

                if (resultado.get() == ButtonType.OK) {
                    trabajador.despedir(buscando, modo);
                    despedirCorrecto = new Alert(AlertType.INFORMATION);
                    despedirCorrecto.setTitle("Despedir");
                    despedirCorrecto.setHeaderText("Despido efectuado");
                    despedirCorrecto.setContentText(null);
                    estiloAlerta.darleEstiloAlPanel(despedirCorrecto);
                    despedirCorrecto.showAndWait();
                    limpiarCamposDespedir();

                } else if (resultado.get() == ButtonType.CANCEL) {
                    confirmacionBorrado.close();
                }

            } catch (SQLException ex) {
                errorDespedir = new Alert(AlertType.ERROR);
                errorDespedir.setTitle("Despedir");
                errorDespedir.setHeaderText("Error al despedir");
                errorDespedir.setContentText("No se ha podido borrar el trabajador de la "
                        + "base de datos.\n"
                        + "Error: " + ex.getMessage());
                estiloAlerta.darleEstiloAlPanel(errorDespedir);
                errorDespedir.showAndWait();
            }
        } else {
            sinBuscar = new Alert(AlertType.ERROR);
            sinBuscar.setTitle("Busqueda");
            sinBuscar.setHeaderText("Error en la busqueda");
            sinBuscar.setContentText("Primero debe buscar el trabajador.");
            estiloAlerta.darleEstiloAlPanel(sinBuscar);
            sinBuscar.showAndWait();
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
        pn_despedir.setVisible(false);
        limpiarCamposContratar();
        limpiarCamposDespedir();
        salirVerTrabajadores();
    }

    public void limpiarCamposContratar() {
        tf_dni.clear();
        tf_nombre.clear();
        tf_apellido1.clear();
        tf_apellido2.clear();
        tf_puesto.clear();
        nf_salario.clear();
        tf_nick.clear();
        dp_horaEntrada.setLocalTime(LocalTime.MIDNIGHT);
        dp_horaSalida.setLocalTime(LocalTime.MIDNIGHT);
        dp_fecha.setValue(LocalDate.now());

    }

    public void limpiarCamposDespedir() {
        tf_busquedaTexto.clear();
        nf_busquedaNumerica.clear();
        ta_datosTrabajador.setVisible(false);
        ta_datosTrabajador.setDisable(true);
    }

    @FXML
    private void teclaBuscarDespedirAction(KeyEvent event) {
        String campo = tf_busquedaTexto.getText(), campoNumerico = nf_busquedaNumerica.getText();
        if (event.getCode() == KeyCode.ENTER) {
            buscarDespedir();
        }

        if (campo.isEmpty() && campoNumerico.isEmpty()) {
            ta_datosTrabajador.setVisible(false);
            ta_datosTrabajador.setDisable(true);

        }

    }

    @FXML
    private void verTrabajadoresAction(ActionEvent event) {
        Object evento = event.getSource();
        verTrajadores();

        if (evento == bt_atrasVerTrabajadores) {
            salirVerTrabajadores();

        }

        if (evento == bt_tienda) {
            pn_tienda.setVisible(true);
            pn_inicio.setVisible(false);
        }

        if (evento == bt_irPaneDespedir) {
            pn_tienda.setVisible(false);
            pn_despedir.setVisible(true);
        }

    }

    public void verTrajadores() {
        List<Trabajador> listaTrabajadores;
        Alert errorCarga;

        try {
            listaTrabajadores = trabajador.cargarTrabajadoresTienda(gerenteActual.getIdTienda());
            listaObservableTrabajadores = FXCollections.observableArrayList(listaTrabajadores);
            tv_empleado.setItems(listaObservableTrabajadores);
            tv_empleado.setVisible(true);
        } catch (SQLException ex) {
            errorCarga = new Alert(AlertType.ERROR);
            errorCarga.setTitle("Error Carga");
            errorCarga.setHeaderText("Error en la carga de los trabajadores");
            errorCarga.setContentText("Error: " + ex.getMessage());
            estiloAlerta.darleEstiloAlPanel(errorCarga);
            errorCarga.showAndWait();
        }
    }

    public void salirVerTrabajadores() {
        tv_empleado.setVisible(false);
        pn_inicio.setVisible(true);
        pn_tienda.setVisible(false);
    }

}
