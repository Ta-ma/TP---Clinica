/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinica;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Santi
 */
public class LoginController implements Initializable {
    @FXML private Button buttonIngresar;
    @FXML private TextField fieldUsuario;
    @FXML private TextField fieldPassword;
    SQLDAO dao;
    
    @FXML
    private void handleIngresarAction(ActionEvent event) {
        if (dao.validarUsuario(fieldUsuario.getText(), fieldPassword.getText())) {
            finalizar();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Usuario no válido o contraseña incorrecta.", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    
    private void finalizar() {
        Stage stage = (Stage) buttonIngresar.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Principal.fxml"));
        //PrincipalController principal = loader.getController();
        //SQLDAO dao = new SQLDAO();
        //principal.setDAO(dao);
        Scene scene;
        try {
            scene = new Scene(loader.load());
            Stage secondStage = new Stage();
            secondStage.setScene(scene);
            secondStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setDAO (SQLDAO dao) {
        this.dao = dao;
    }
}
