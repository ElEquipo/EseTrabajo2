package vista.gerente;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import vista.Empleado.EmpleadoController;

public class GerenteController implements Initializable {

    @FXML
    private Button bt_Incidencias;
    @FXML
    private Pane pn_Inicio;
    @FXML
    private Button bt_Productos;
    @FXML
    private Button bt_Tiendas;
    @FXML
    private Button bt_Trabajadores;
    @FXML
    private Button bt_TrabajadorMenos;
    @FXML
    private Button bt_TrabajadorMas;
    @FXML
    private Button bt_Atras;
    @FXML
    private Pane pn_Trabajadores;
    @FXML
    private AnchorPane ac_gerente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pn_Trabajadores.setVisible(false);
    }

    @FXML
    private void trabajadoresAction(ActionEvent event) {

        if (bt_Trabajadores.isFocused()) {            
            pn_Inicio.setVisible(false);            
            pn_Trabajadores.setVisible(true);
        }
        
        if(bt_Atras.isFocused()){            
            pn_Inicio.setVisible(true);            
            pn_Trabajadores.setVisible(false);
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

}
