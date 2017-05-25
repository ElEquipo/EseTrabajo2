package vista.login;

import Datos.ConexionBD;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import javax.swing.Timer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable {

    private boolean mayusculasActivadas = false;
    private ConexionBD conexion;
    private String user;

    /*ATRIBUTOS FXML*/
    @FXML
    private TextField tf_user;
    @FXML
    private Label lbl_inciarSesion;
    @FXML
    private Button bt_iniciarSesion;
    @FXML
    private Label lbl_bienvendio;
    @FXML
    private Separator separator;
    @FXML
    private Pane pn_inciarSesion;
    @FXML
    private Pane pn_principalBienvenida;
    @FXML
    private PasswordField pf_contraseña;
    @FXML
    private Label lb_errorIniciar;
    @FXML
    private ImageView iv_bloqMayus;
    @FXML
    private AnchorPane paneLogin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = new ConexionBD();

        FadeTransition transicion = new FadeTransition(Duration.millis(4000), pn_principalBienvenida);
        transicion.setAutoReverse(true);
        transicion.setFromValue(1.0);
        transicion.setToValue(0.0);

        Timer timer = new Timer(1000, new ActionListener() { // Genera el difuminación del panel principal
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                transicion.play();
            }
        });
        timer.setRepeats(false);
        timer.start();

        Timer tiempoTransicion = new Timer(5000, new ActionListener() { // Permite que podemaos interactuar con el segundo panel
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                pn_principalBienvenida.setVisible(false);
            }
        });
        tiempoTransicion.setRepeats(false);
        tiempoTransicion.start();

        Image img_warnning = new Image("/vista/login/images/warningIcon3.png");
        iv_bloqMayus.setImage(img_warnning);
        iv_bloqMayus.setDisable(true);
        iv_bloqMayus.setVisible(false);
    }

    @FXML
    private void inciarSesionAction(ActionEvent event) {
        iniciarSesion();
    }

    public void iniciarSesion() {
        user = tf_user.getText();
        String pass = pf_contraseña.getText();
        Alert alerta;
        try {

            if (!user.isEmpty() && !pass.isEmpty()) {

                if (conexion.conectar("jdbc:mysql://localhost:3306/justComerce", "root", "ROOT")) {

                    if (conexion.existe(user, pass)) {

                        if (conexion.puesto(user).equalsIgnoreCase("Gerente")) {
                            cambiarContraseña(user);
                            alerta = bienvenida();
                            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/gerente/GerenteFXML.fxml"));
                            paneLogin.getChildren().setAll(pane);
                            cerrarBienvendia(alerta);

                        } else if (conexion.puesto(user).equalsIgnoreCase("Dependiente")) {
                            cambiarContraseña(user);
                            alerta = bienvenida();
                            AnchorPane pane = FXMLLoader.load(getClass().getResource("/vista/Empleado/EmpleadoFXML.fxml"));
                            paneLogin.getChildren().setAll(pane);
                            cerrarBienvendia(alerta);

                        }

                    } else {
                        lb_errorIniciar.setText("Usuario no existente");
                        lb_errorIniciar.setVisible(true);
                        lb_errorIniciar.setStyle("-fx-background-color:rgba(89, 89, 89, 0.6);"
                                + " -fx-border-radius:2px;");
                    }

                } else {
                    lb_errorIniciar.setText("Error en la conexión");
                    lb_errorIniciar.setVisible(true);
                    lb_errorIniciar.setStyle("-fx-background-color:rgba(89, 89, 89, 0.6);"
                            + " -fx-border-radius:2px;");
                }

            } else {
                lb_errorIniciar.setVisible(true);
                lb_errorIniciar.setText("Rellene los campos");
                lb_errorIniciar.setStyle("-fx-background-color:rgba(89, 89, 89, 0.6);"
                        + " -fx-border-radius:2px;");
            }

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Alert bienvenida() {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Bienvenido");
        alerta.setHeaderText("Bienvenido a JustComerce " + user);
        alerta.setGraphic(null);
        darleEstiloAlPanel(alerta);
        alerta.show();
        try {
            Thread.sleep(1200);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alerta;
    }

    public void cambiarContraseña(String user) throws SQLException {
        Alert alerta;
        
        if (conexion.cambiarContraseña(user)) {
            alerta = new Alert(AlertType.CONFIRMATION);
            alerta.setTitle("Modificar contraseña");
            alerta.setHeaderText("Porfavor intruduza su contraseña");
            alerta.showAndWait();
        }
    }

    public void cerrarBienvendia(Alert alerta) {
        alerta.close();
    }

    @FXML
    private void conectarAction(KeyEvent event) {
        KeyCode tecla = event.getCode();

        if (tecla == KeyCode.ENTER) {
            iniciarSesion();
        }
//        boolean isOn = Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);
//        System.out.println(isOn);
//        if (isOn) {
//            mayusculasActivadas = true;
//        }
//        
//        
//
//        if (mayusculasActivadas && pf_contraseña.isFocused()) {
//            iv_bloqMayus.setDisable(false);
//            iv_bloqMayus.setVisible(true);
//            mayusculasActivadas = false;
//            tf_user.setText("0");
//
//        } else if (!mayusculasActivadas && pf_contraseña.isFocused()) {
//            iv_bloqMayus.setDisable(false);
//            iv_bloqMayus.setVisible(true);
//            mayusculasActivadas = true;
//            tf_user.setText("1");
//
//        } else if (tecla == KeyCode.CAPS && pf_contraseña.isFocused() && mayusculasActivadas == true) {
//            iv_bloqMayus.setDisable(true);
//            iv_bloqMayus.setVisible(false);
//            mayusculasActivadas = false;
//            tf_user.setText("2");
//
//        } else if (tecla == KeyCode.CAPS && pf_contraseña.isFocused() && mayusculasActivadas == false) {
//            iv_bloqMayus.setDisable(true);
//            iv_bloqMayus.setVisible(false);
//            mayusculasActivadas = true;
//            tf_user.setText("3");
//
//        } else if (!pf_contraseña.isFocused() && mayusculasActivadas == true) {
//            iv_bloqMayus.setDisable(true);
//            iv_bloqMayus.setVisible(false);
//            mayusculasActivadas = false;
//            tf_user.setText("4");
//
//        } else if (!pf_contraseña.isFocused() && mayusculasActivadas == false) {
//            iv_bloqMayus.setDisable(true);
//            iv_bloqMayus.setVisible(false);
//            mayusculasActivadas = true;
//            tf_user.setText("5");
//
//        } else if (mayusculasActivadas == false) {
//            mayusculasActivadas = true;
//            tf_user.setText("6");
//
//        } else if (mayusculasActivadas == true) {
//            mayusculasActivadas = false;
//            tf_user.setText("7");
//        }
    }

    private void darleEstiloAlPanel(Alert panel) {
        DialogPane dialogPane;
        Stage alertaStage;

        dialogPane = panel.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("css.css").toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        alertaStage = (Stage) panel.getDialogPane().getScene().getWindow();
        // COGER LA RUTA DEL ICONO
        // alertaStage.getIcons().add(new Image("file:/images/icon.png"));
    }

}
