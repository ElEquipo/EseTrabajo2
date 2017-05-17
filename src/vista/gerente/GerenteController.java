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
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import vista.Empleado.EmpleadoController;

public class GerenteController implements Initializable {

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
    private Pane pn_personal;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pn_personal.setVisible(false);
        cargarTooltips();
    }
    
    public void cargarTooltips(){
        
    }

    @FXML
    private void trabajadoresAction(ActionEvent event) {

        if (bt_personal.isFocused()) {            
            pn_inicio.setVisible(false);            
            pn_personal.setVisible(true);
        }
        
        if(bt_atras.isFocused()){            
            pn_inicio.setVisible(true);            
            pn_personal.setVisible(false);
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
