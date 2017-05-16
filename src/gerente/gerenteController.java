/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerente;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author daw
 */
public class gerenteController implements Initializable {

    @FXML
    private Pane pn_Trabajadores;
    @FXML
    private Button bt_TrabajadorMenos;
    @FXML
    private Button bt_TrabajadorMas;
    @FXML
    private Button bt_Atras;
    @FXML
    private Pane pn_Inicio;
    @FXML
    private Button bt_Incidencias;
    @FXML
    private Button bt_Productos;
    @FXML
    private Button bt_Tiendas;
    @FXML
    private Button bt_Trabajadores;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void trabajadoresAction(ActionEvent event) {
    }
    
}
