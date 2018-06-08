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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
    // INPUTS
    // funcionalidad
    @FXML private JFXRadioButton fuRadio1;
    @FXML private JFXRadioButton fuRadio2;
    @FXML private JFXTextField fuInputError;
    // eficiencia
    @FXML private JFXTextField efInputCPU;
    @FXML private JFXTextField efInputSegs;
    // fiabilidad
    @FXML private JFXRadioButton fiRadio1;
    @FXML private JFXRadioButton fiRadio2;
    @FXML private JFXRadioButton fiRadio3;
    @FXML private JFXRadioButton fiRadio4;
    // mantenibilidad
    @FXML private JFXTextField maInputComentarios;
    @FXML private JFXTextField maInputCC;
    @FXML private JFXTextField maInputPromError;
    // usabilidad
    @FXML private JFXToggleButton usToggleCat;
    @FXML private JFXTextField usInputPasos;
    @FXML private JFXComboBox<String> usComboAyuda;
    @FXML private JFXRadioButton usRadio1;
    @FXML private JFXRadioButton usRadio2;
    // portabilidad
    @FXML private JFXTextField poInputSO;
    @FXML private JFXTextField poInputPasos;
    // resultados
    @FXML private Label resLabelCalificacion;
    @FXML private Label resPuntosFu;
    @FXML private Label resPuntosEf;
    @FXML private Label resPuntosFi;
    @FXML private Label resPuntosMa;
    @FXML private Label resPuntosUs;
    @FXML private Label resPuntosPo;
    @FXML private Label resPuntosTotales;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apActual = apInicio;
        buttonActual = buttonInicio;
        // opciones del combo box de usabilidad
        usComboAyuda.getItems().add("Requiere de la ayuda de personal especializado");
        usComboAyuda.getItems().add("Requiere de la ayuda contextual y manual de uso");
        usComboAyuda.getItems().add("No requiere ayuda");
        usComboAyuda.setValue("Requiere de la ayuda de personal especializado");
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
        try {
            float puntosFu = 10;
            float puntosEf = 20;
            float puntosFi = 15;
            float puntosMa = 30;
            float puntosUs = 15;
            float puntosPo = 10;

            // funcionalidad
            float multFu = 0;
            multFu += fuRadio1.selectedProperty().getValue() ? 0.25 : 0;
            multFu += fuRadio2.selectedProperty().getValue() ? 0.25 : 0;
            try {
                int ordenError = Integer.parseInt(fuInputError.getText());
                if (ordenError <= -7) multFu += 0.5;
                else if (ordenError < -3) multFu += 0.25;
            } catch (NumberFormatException e) {
                throw new Exception("El valor ingresado en el campo Orden de Error en la pantalla de Funcionalidad no es correcto.");
            }
            puntosFu *= multFu;
            setearPuntaje(resPuntosFu, puntosFu, 10);
            // eficiencia
            float multEf = 0;
            try {
                float porcentajeCPU = Float.parseFloat(efInputCPU.getText());
                if (porcentajeCPU <= 10) multEf += 0.5;
                else if (porcentajeCPU <= 41) multEf += 0.25;
            } catch (NumberFormatException e) {
                throw new Exception("El valor ingresado en el campo Porcentaje de uso de CPU en la pantalla de Eficiencia no es correcto.");
            }
            try {
                int segs = Integer.parseInt(efInputSegs.getText());
                if (segs <= 3) multEf += 0.5;
                else if (segs <= 5) multEf += 0.25;
            } catch (NumberFormatException e) {
                throw new Exception("El valor ingresado en el campo Segundos en la pantalla de Eficiencia no es correcto.");
            }
            puntosEf *= multEf;
            setearPuntaje(resPuntosEf, puntosEf, 20);
            // fiabilidad
            float multFi = 0;
            multFi += fiRadio1.selectedProperty().getValue() ? 0.25 : 0;
            multFi += fiRadio2.selectedProperty().getValue() ? 0.25 : 0;
            multFi += fiRadio3.selectedProperty().getValue() ? 0.25 : 0;
            multFi += fiRadio4.selectedProperty().getValue() ? 0.25 : 0;
            puntosFi *= multFi;
            setearPuntaje(resPuntosFi, puntosFi, 15);
            // mantenibilidad
            float multMa = 0;
            try {
                float porcentajeComentarios = Float.parseFloat(maInputComentarios.getText());
                if (porcentajeComentarios >= 20) multMa += 1/3.0;
                else if (porcentajeComentarios >= 10) multMa += 1/6.0;
            } catch (NumberFormatException e) {
                throw new Exception("El valor ingresado en el campo Porcentaje de código comentado en la pantalla de Mantenibilidad no es correcto.");
            }
            try {
                int cc = Integer.parseInt(maInputCC.getText());
                if (cc <= 10) multMa += 1/3.0;
                else if (cc <= 20) multMa += 1/6.0;
            } catch (NumberFormatException e) {
                throw new Exception("El valor ingresado en el campo Complejidad Ciclomática en la pantalla de Mantenibilidad no es correcto.");
            }
            try {
                float promErrores = Float.parseFloat(maInputPromError.getText());
                if (promErrores <= 2) multMa += 1/3.0;
                else if (promErrores < 5) multMa += 1/6.0;
            } catch (NumberFormatException e) {
                throw new Exception("El valor ingresado en el campo Promedio de rrores por prueba en la pantalla de Mantenibilidad no es correcto.");
            }
            puntosMa *= multMa;
            setearPuntaje(resPuntosMa, puntosMa, 30);
            // usabilidad
            float multUs = 0;
            multUs += usRadio1.selectedProperty().getValue() ? 1/6.0 : 0;
            multUs += usRadio2.selectedProperty().getValue() ? 1/6.0 : 0;
            multUs += usComboAyuda.getValue().equals("No requiere ayuda") ? 1/3.0 : 0;
            multUs += usComboAyuda.getValue().equals("Requiere de la ayuda contextual y manual de uso") ? 1/6.0 : 0;
            
            try {
                float promPasos = Float.parseFloat(usInputPasos.getText());
                boolean cat = usToggleCat.selectedProperty().getValue();
                if (promPasos <= 2 && cat) multUs += 1/3.0;
                else if (promPasos <= 6 && cat) multUs += 1/6.0;
            } catch (NumberFormatException e) {
                throw new Exception("El valor ingresado en el campo Promedio de pasos en la pantalla de Usabilidad no es correcto.");
            }
            puntosUs *= multUs;
            setearPuntaje(resPuntosUs, puntosUs, 15);
            // portabilidad
            float multPo = 0;
            try {
                int so = Integer.parseInt(poInputSO.getText());
                if (so >= 3) multPo += 0.5;
                else if (so == 2) multPo += 0.25;
            } catch (NumberFormatException e) {
                throw new Exception("El valor ingresado en el campo Sistemas operativos en la pantalla de Portabilidad no es correcto.");
            }
            try {
                int pasos = Integer.parseInt(poInputPasos.getText());
                if (pasos <= 4) multPo += 0.5;
                else if (pasos <= 7) multPo += 0.25;
            } catch (NumberFormatException e) {
                throw new Exception("El valor ingresado en el campo Pasos de instalación en la pantalla de Portabilidad no es correcto.");
            }
            puntosPo *= multPo;
            setearPuntaje(resPuntosPo, puntosPo, 10);
            // sumo todos los puntajes
            float total = puntosFu + puntosEf + puntosFi + puntosMa + puntosUs + puntosPo;
            resPuntosTotales.setText(String.format("%.2f", total));
            if (total >= 80) {
                resPuntosTotales.setStyle("-fx-text-fill: #5cd188;");
                resLabelCalificacion.setText("Satisfactorio");
                resLabelCalificacion.setStyle("-fx-text-fill: #5cd188;");
            } else {
                resPuntosTotales.setStyle("-fx-text-fill: #d35858;");
                resLabelCalificacion.setText("No satisfactorio");
                resLabelCalificacion.setStyle("-fx-text-fill: #d35858;");
            }
            // cambio la pantalla
            cambiarPantalla(buttonFinalizar, apResultados);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
        
    }
    /**
     * Método para setear las labels de puntajes.
     * @param label
     * @param valor
     * @param valorMax 
     */
    private void setearPuntaje (Label label, float valor, int valorMax) {
        // color rojito: #d35858
        // color amarillo: #ccbe60
        // color verdecito: #5cd188
        label.setText(String.format("%.2f", valor));
        float rate = valor / valorMax;
        if (rate > 2/3.0) {
            label.setStyle("-fx-text-fill: #5cd188;");
        } else if (rate > 1/3.0) {
            label.setStyle("-fx-text-fill: #ccbe60;");
        } else label.setStyle("-fx-text-fill: #d35858;");
    }
    /**
     * Método para cambiar entre las distintas pantallas del programa
     * @param button
     * @param ap 
     */
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
