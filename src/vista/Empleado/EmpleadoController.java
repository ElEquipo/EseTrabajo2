package vista.Empleado;

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

public class EmpleadoController implements Initializable {

    @FXML
    private Button bt_Inicio;
    @FXML
    private Button bt_Perfil;
    @FXML
    private Button bt_Ayuda;
    @FXML
    private Button bt_LogOut;
    @FXML
    private Button bt_Ventas;
    @FXML
    private Button bt_Productos;
    @FXML
    private Pane pn_FondoIconos;
    @FXML
    private AnchorPane ac_empleado;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
