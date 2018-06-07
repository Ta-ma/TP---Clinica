/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;

import com.jfoenix.controls.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Santi
 */
public class PantallaController implements Initializable {
    
    // botones
    @FXML private JFXButton buttonInicio;
    @FXML private JFXButton buttonFuncionalidad;
    @FXML private JFXButton buttonEficiencia;
    @FXML private JFXButton buttonFiabilidad;
    @FXML private JFXButton buttonMantenibilidad;
    @FXML private JFXButton buttonUsabilidad;
    @FXML private JFXButton buttonPortabilidad;
    @FXML private JFXButton buttonFinalizar;
    private JFXButton buttonActual;
    // anchor panes
    @FXML private AnchorPane apInicio;
    @FXML private AnchorPane apFuncionalidad;
    @FXML private AnchorPane apEficiencia;
    @FXML private AnchorPane apFiabilidad;
    @FXML private AnchorPane apMantenibilidad;
    @FXML private AnchorPane apUsabilidad;
    @FXML private AnchorPane apPortabilidad;
    @FXML private AnchorPane apResultados;
    private AnchorPane apActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apActual = apInicio;
        buttonActual = buttonInicio;
    }  
    
    @FXML
    private void handleButtonInicio(ActionEvent event) {
        cambiarPantalla(buttonInicio, apInicio);
    }
    
    @FXML
    private void handleButtonFuncionalidad(ActionEvent event) {
        cambiarPantalla(buttonFuncionalidad, apFuncionalidad);
    }
    
    @FXML
    private void handleButtonEficiencia(ActionEvent event) {
        cambiarPantalla(buttonEficiencia, apEficiencia);
    }
    
    @FXML
    private void handleButtonFiabilidad(ActionEvent event) {
        cambiarPantalla(buttonFiabilidad, apFiabilidad);
    }
    
    @FXML
    private void handleButtonMantenibilidad(ActionEvent event) {
        cambiarPantalla(buttonMantenibilidad, apMantenibilidad);
    }
    
    @FXML
    private void handleButtonUsabilidad(ActionEvent event) {
        cambiarPantalla(buttonUsabilidad, apUsabilidad);
    }
    
    @FXML
    private void handleButtonPortabilidad(ActionEvent event) {
        cambiarPantalla(buttonPortabilidad, apPortabilidad);
    }
    
    @FXML
    private void handleButtonFinalizar(ActionEvent event) {
        cambiarPantalla(buttonFinalizar, apResultados);
    }
    
    // método para cambiar entre las distintas pantallas del programa
    private void cambiarPantalla (JFXButton button, AnchorPane ap) {
        if (apActual == ap)
            return;
        
        buttonActual.setUnderline(false);
        button.setUnderline(true);
        apActual.setVisible(false);
        ap.setVisible(true);
        apActual = ap;
        buttonActual = button;
    }
}
