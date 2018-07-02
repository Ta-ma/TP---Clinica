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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Santi
 */
public class PrincipalController implements Initializable {
    // variables de uso general
    SQLDAO dao;
    AnchorPane apActual;
    // Usuarios
    @FXML TableView<Usuario> tablaUsuarios;
    @FXML TextField inputUsuariosNombre;
    @FXML TextField inputUsuariosPassword;
    @FXML TextField inputUsuariosEmail;
    @FXML AnchorPane apUsuarios;
    // Pacientes
    @FXML TableView<Paciente> tablaPacientes;
    @FXML TextField inputPacientesNombre;
    @FXML TextField inputPacientesDNI;
    @FXML AnchorPane apPacientes;
    // Médicos
    @FXML TableView<Medico> tablaMedicos;
    @FXML TextField inputMedicosNombre;
    @FXML TextField inputMedicosDNI;
    @FXML TextField inputMedicosEspecialidad;
    @FXML AnchorPane apMedicos;
    // Situaciones
    @FXML TableView<Situacion> tablaSituaciones;
    @FXML TextField inputSituacionesId;
    @FXML TextField inputSituacionesDNIP;
    @FXML TextField inputSituacionesDNIM;
    @FXML TextField inputSituacionesDiagnostico;
    @FXML AnchorPane apSituaciones;
    // Informes
    @FXML AnchorPane apInformes;
    
    // ******************************
    // ****** Métodos Usuarios ******
    // ******************************
    /**
     * Botón de navegación de usuarios
     * @param event 
     */
    @FXML
    private void handleUsuariosClick(MouseEvent event) {
        cambiarPantalla(apUsuarios);
    }
    
    @FXML
    private void handleUsuariosAgregar(ActionEvent event) {
        try {
            Usuario usuario = new Usuario(inputUsuariosNombre.getText(),
                inputUsuariosPassword.getText(), inputUsuariosEmail.getText());
            dao.insertarUsuario(usuario);
            tablaUsuarios.setItems(dao.obtenerUsuarios());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleUsuariosEliminar(ActionEvent event) {
        try {
            dao.eliminarUsuario(inputUsuariosNombre.getText());
            tablaUsuarios.setItems(dao.obtenerUsuarios());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    
    @FXML
    private void handleUsuariosModificar(ActionEvent event) {
        try {
            Usuario usuario = new Usuario(inputUsuariosNombre.getText(),
                inputUsuariosPassword.getText(), inputUsuariosEmail.getText());
            dao.actualizarUsuario(usuario);
            tablaUsuarios.setItems(dao.obtenerUsuarios());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    
    // *******************************
    // ****** Métodos Pacientes ******
    // *******************************
    /**
     * Botón de navegación de pacientes
     * @param event 
     */
    @FXML
    private void handlePacientesClick(MouseEvent event) {
        cambiarPantalla(apPacientes);
    }
    @FXML
    private void handlePacientesAgregar(ActionEvent event) {
        try {
            Paciente paciente = new Paciente(inputPacientesDNI.getText(), inputPacientesNombre.getText());
            dao.insertarPaciente(paciente);
            tablaPacientes.setItems(dao.obtenerPacientes());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    @FXML
    private void handlePacientesEliminar(ActionEvent event) {
        try {
            dao.eliminarPaciente(Integer.parseInt(inputPacientesDNI.getText()));
            tablaPacientes.setItems(dao.obtenerPacientes());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El campo de DNI no contiene un número válido.", ButtonType.CLOSE);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    @FXML
    private void handlePacientesModificar(ActionEvent event) {
        try {
            Paciente paciente = new Paciente(inputPacientesDNI.getText(), inputPacientesNombre.getText());
            dao.actualizarPaciente(paciente);
            tablaPacientes.setItems(dao.obtenerPacientes());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    /**
     * Botón de navegación de médicos
     * @param event 
     */
    @FXML
    private void handleMedicosClick(MouseEvent event) {
        cambiarPantalla(apMedicos);
    }
    @FXML
    private void handleMedicosAgregar(ActionEvent event) {
        try {
            Medico med = new Medico(inputMedicosDNI.getText(), inputMedicosNombre.getText(), inputMedicosEspecialidad.getText());
            dao.insertarMedico(med);
            tablaMedicos.setItems(dao.obtenerMedicos());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    @FXML
    private void handleMedicosEliminar(ActionEvent event) {
        try {
            dao.eliminarMedico(Integer.parseInt(inputMedicosDNI.getText()));
            tablaMedicos.setItems(dao.obtenerMedicos());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El campo de DNI no contiene un número válido.", ButtonType.CLOSE);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    @FXML
    private void handleMedicosModificar(ActionEvent event) {
        try {
            Medico med = new Medico(inputMedicosDNI.getText(), inputMedicosNombre.getText(), inputMedicosEspecialidad.getText());
            dao.actualizarMedico(med);
            tablaMedicos.setItems(dao.obtenerMedicos());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    /**
     * Botón de navegación de situaciones
     * @param event 
     */
    @FXML
    private void handleSituacionesClick(MouseEvent event) {
        cambiarPantalla(apSituaciones);
    }
    @FXML
    private void handleSituacionesAgregar(ActionEvent event) {
        try {
            Situacion sit = new Situacion(inputSituacionesId.getText(), inputSituacionesDNIP.getText(),
                inputSituacionesDNIM.getText(), inputSituacionesDiagnostico.getText());
            dao.insertarSituacion(sit);
            tablaSituaciones.setItems(dao.obtenerSituaciones());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    @FXML
    private void handleSituacionesEliminar(ActionEvent event) {
        try {
            dao.eliminarSituacion(Integer.parseInt(inputSituacionesId.getText()));
            tablaSituaciones.setItems(dao.obtenerSituaciones());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "El campo de ID no contiene un número válido.", ButtonType.CLOSE);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    @FXML
    private void handleSituacionesModificar(ActionEvent event) {
        try {
            Situacion sit = new Situacion(inputSituacionesId.getText(), inputSituacionesDNIP.getText(),
                inputSituacionesDNIM.getText(), inputSituacionesDiagnostico.getText());
            dao.actualizarSituacion(sit);
            tablaSituaciones.setItems(dao.obtenerSituaciones());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    /**
     * Botón de navegación de informes
     * @param event 
     */
    @FXML
    private void handleInformesClick(MouseEvent event) {
        cambiarPantalla(apInformes);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apUsuarios.setVisible(true);
        apActual = apUsuarios;
    }    
    
    public void inicializar() {
        // Usuarios
        TableColumn<Usuario, String> colUsuariosNombre = new TableColumn("Nombre");
        colUsuariosNombre.setPrefWidth(170);
        colUsuariosNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        
        TableColumn<Usuario, String> colUsuariosPassword = new TableColumn("Contraseña");
        colUsuariosPassword.setPrefWidth(170);
        colUsuariosPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        
        TableColumn<Usuario, String> colUsuariosEmail = new TableColumn("Email");
        colUsuariosEmail.setPrefWidth(170);
        colUsuariosEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tablaUsuarios.setRowFactory( tv -> {
            TableRow<Usuario> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Usuario usuario = row.getItem();
                    inputUsuariosNombre.setText(usuario.getNombre());
                    inputUsuariosPassword.setText(usuario.getPassword());
                    inputUsuariosEmail.setText(usuario.getEmail());
                }
            });
            return row ;
        });
        tablaUsuarios.getColumns().addAll(colUsuariosNombre, colUsuariosPassword, colUsuariosEmail);
        
        // Pacientes
        TableColumn<Paciente, String> colPacientesDni = new TableColumn("DNI");
        colPacientesDni.setPrefWidth(255);
        colPacientesDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        
        TableColumn<Paciente, String> colPacientesNombre = new TableColumn("Nombre");
        colPacientesNombre.setPrefWidth(255);
        colPacientesNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        tablaPacientes.setRowFactory( tv -> {
            TableRow<Paciente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Paciente paciente = row.getItem();
                    inputPacientesDNI.setText(Integer.toString(paciente.getDni()));
                    inputPacientesNombre.setText(paciente.getNombre());
                }
            });
            return row ;
        });
        tablaPacientes.getColumns().addAll(colPacientesDni, colPacientesNombre);
        
        // Médicos
        TableColumn<Medico, String> colMedicosDni = new TableColumn("DNI");
        colMedicosDni.setPrefWidth(170);
        colMedicosDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        
        TableColumn<Medico, String> colMedicosNombre = new TableColumn("Nombre");
        colMedicosNombre.setPrefWidth(170);
        colMedicosNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Medico, String> colMedicosEspecialidad = new TableColumn("Especialidad");
        colMedicosEspecialidad.setPrefWidth(170);
        colMedicosEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        
        tablaMedicos.setRowFactory( tv -> {
            TableRow<Medico> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Medico med = row.getItem();
                    inputMedicosDNI.setText(Integer.toString(med.getDni()));
                    inputMedicosNombre.setText(med.getNombre());
                    inputMedicosEspecialidad.setText(med.getEspecialidad());
                }
            });
            return row ;
        });
        tablaMedicos.getColumns().addAll(colMedicosDni, colMedicosNombre, colMedicosEspecialidad);
        
        // Situaciones
        TableColumn<Situacion, Integer> colSituacionesId = new TableColumn("ID");
        colSituacionesId.setPrefWidth(127.5);
        colSituacionesId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Situacion, Integer> colSituacionesDniP = new TableColumn("DNI Paciente");
        colSituacionesDniP.setPrefWidth(127.5);
        colSituacionesDniP.setCellValueFactory(new PropertyValueFactory<>("dniPaciente"));

        TableColumn<Situacion, Integer> colSituacionesDniM = new TableColumn("DNI Médico");
        colSituacionesDniM.setPrefWidth(127.5);
        colSituacionesDniM.setCellValueFactory(new PropertyValueFactory<>("dniMedico"));
        
        TableColumn<Situacion, String> colSituacionesDiag = new TableColumn("Diagnóstico");
        colSituacionesDiag.setPrefWidth(127.5);
        colSituacionesDiag.setCellValueFactory(new PropertyValueFactory<>("diagnostico"));
        
        tablaSituaciones.setRowFactory( tv -> {
            TableRow<Situacion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Situacion sit = row.getItem();
                    inputSituacionesId.setText(Integer.toString(sit.getId()));
                    inputSituacionesDNIM.setText(Integer.toString(sit.getDniMedico()));
                    inputSituacionesDNIP.setText(Integer.toString(sit.getDniPaciente()));
                    inputSituacionesDiagnostico.setText(sit.getDiagnostico());
                }
            });
            return row ;
        });
        tablaSituaciones.getColumns().addAll(colSituacionesId, colSituacionesDniP, colSituacionesDniM, colSituacionesDiag);
        
        // llenar las tablas
        try {
            tablaMedicos.setItems(dao.obtenerMedicos());
            tablaPacientes.setItems(dao.obtenerPacientes());
            tablaUsuarios.setItems(dao.obtenerUsuarios());
            tablaSituaciones.setItems(dao.obtenerSituaciones());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }
    
    public void setDAO (SQLDAO dao) {
        this.dao = dao;
    }
    
    /**
     * Método para cambiar entre las distintas pantallas del programa
     * @param ap 
     */
    private void cambiarPantalla (AnchorPane ap) {
        if (apActual == ap) return;
        apActual.setVisible(false);
        ap.setVisible(true);
        apActual = ap;
    }
}