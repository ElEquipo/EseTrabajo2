package vista.Empleado;

import Datos.ConexionBD;
import Datos.TiendaDAO;
import Modelo.Producto;
import Modelo.Tienda;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    private ObservableList<Producto> listaProductos;
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
    private ComboBox<?> cb_idTienda;
    @FXML
    private ComboBox<?> cb_idTrabajador;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tienda = new TiendaDAO(ConexionBD.conexion);
        pn_productos.setVisible(false);
        pn_ventas.setVisible(false);
        
        try {            
            listaProductos = FXCollections.observableArrayList(tienda.cargarProductos());
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
