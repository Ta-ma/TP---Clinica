/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author TuVieja
 */
public class VentanaController implements Initializable {
    @FXML private TextArea textAreaCodigo;
    @FXML private TreeView<TreeEntity> treeViewArchivos;
    @FXML private TreeView<StatsMetodo> treeViewMetodos;
    @FXML private Label labelLineas;
    @FXML private Label labelLineasCom;
    @FXML private Label labelPorcentajeCom;
    @FXML private Label labelComplejidad;
    @FXML private Label labelFanIn;
    @FXML private Label labelFanOut;
    @FXML private Label labelHalsteadLong;
    @FXML private Label labelHalsteadVol;
    @FXML private PieChart pieChart;
    @FXML private Group groupGeneral;
    @FXML private Group groupMetodo;
    @FXML private Label caption;
    @FXML private Label labelTotalArchivos;
    @FXML private Label labelTotalMetodos;
    @FXML private Label labelMaxCC;
    @FXML private Label labelMaxFanOut;
    
    Stage stage;
    List<StatsArchivo> statsArchivos;
    List<StatsMetodo> statsMetodos;
    
    @FXML
    private void handleButtonCarpeta (ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        //File selectedDirectory = new File("D:\\Arca\\Unlam\\Análisis de Software\\Clinica");
        if(selectedDirectory == null) {
            textAreaCodigo.setText("No se seleccionó ningún directorio.");
        } else {
            try {
                // búsqueda recursiva por los archivos del directorio
                File dir = new File(selectedDirectory.getAbsolutePath());
                TreeItem<TreeEntity> root = getFileTree(dir);
                
                if (root != null) {
                    groupGeneral.setVisible(true);
                    root.setExpanded(true);
                    treeViewArchivos.setRoot(root);
                    
                    treeViewArchivos.getSelectionModel().selectedItemProperty()
                    .addListener((v, oldValue, newValue) -> {
                        TreeEntity entity = newValue.getValue();
                        
                        if (newValue != null && oldValue != newValue) {
                            groupGeneral.setVisible(true);
                            groupMetodo.setVisible(false);
                            if (!entity.fileIsDirectory()) {
                                try {
                                    textAreaCodigo.setText(entity.getFileText());
                                    TreeItem<StatsMetodo> rootMetodos = new TreeItem<>();

                                    for(StatsMetodo m : entity.getStatsArchivo().getMetodos()) {
                                        rootMetodos.getChildren().add(new TreeItem<>(m));
                                    }
                                    treeViewMetodos.setRoot(rootMetodos);
                                    treeViewMetodos.setShowRoot(false);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    Alert alert = new Alert(AlertType.ERROR, "Error al intentar decodificar el texto del archivo.", ButtonType.CLOSE);
                                    alert.showAndWait();
                                }
                            } else
                                textAreaCodigo.setText("");
                        }
                    });
                }
                Analizador analizador = new Analizador(statsArchivos, statsMetodos);
                try {
                    analizador.analizar();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(AlertType.ERROR, "Error al analizar el código. Asegurese que el código java es compilable, no tiene caracteres especiales (como acentos) ni caracteres de escape de Unicode.", ButtonType.CLOSE);
                    alert.showAndWait();
                }
                
                treeViewMetodos.getSelectionModel().selectedItemProperty()
                    .addListener((v, oldValue, newValue) -> {
                        if(newValue != null) {
                            groupGeneral.setVisible(false);
                            groupMetodo.setVisible(true);
                            textAreaCodigo.setText(newValue.getValue().getTexto());
                            setMetricas(newValue.getValue());
                        }  
                    });
                
                realizarReporte();
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR, "Error al intentar acceder a los archivos de la carpeta. Asegurese de que la carpeta es accesible.", ButtonType.CLOSE);
                alert.showAndWait();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textAreaCodigo.setEditable(false);
        statsArchivos = new LinkedList<>();
        statsMetodos = new LinkedList<>();
        groupGeneral.setVisible(false);
        groupMetodo.setVisible(false);
    } 
    
    public void setStage (Stage stage) {
        this.stage = stage;
    }
    
    public TreeItem<TreeEntity> getFileTree (File dir) {
        String name = dir.getName();
        TreeItem<TreeEntity> root = new TreeItem<>(new TreeEntity(name, dir));
        
        for(File f : dir.listFiles()) {
            String fileName = f.getName();
            if(f.isDirectory()) {
                // si es un directorio, se llama a la función recursiva
                TreeItem<TreeEntity> newRoot = getFileTree(f);
                if (newRoot != null) {
                    root.setExpanded(true);
                    root.getChildren().add(newRoot);
                }
            } else {
                // si es un archivo, verifico que sea .java antes de agregarlo
                String extension = "";
                int index = fileName.lastIndexOf('.');
                
                if (index > 0) {
                    extension = fileName.substring(index + 1);
                    if (extension.equals("java"))  {
                        TreeEntity treeEntity = new TreeEntity(fileName, f);
                        StatsArchivo statsArchivo = new StatsArchivo(f);

                        treeEntity.setStatsArchivo(statsArchivo);
                        root.getChildren().add(new TreeItem<>(treeEntity));
                        statsArchivos.add(statsArchivo);
                    } 
                }
            }
        }
        // si el item no tiene hijos, es un directorio vacío y hay que fletarlo
        if(root.getChildren().isEmpty())
            return null;
        
        return root;
    }
    
    private void setMetricas (StatsMetodo sm) {
        labelLineas.setText(String.valueOf(sm.getLineasTotales()));
        labelLineasCom.setText(String.valueOf(sm.getLineasComentarios()));
        
        if (sm.getPorcentajeComentarios() >= 20.0)
            labelPorcentajeCom.setTextFill(Color.web("#00aa00"));
        else if (sm.getPorcentajeComentarios() >= 10.0)
            labelPorcentajeCom.setTextFill(Color.web("#bbbb00"));
        else
            labelPorcentajeCom.setTextFill(Color.web("#ff0000"));
        labelPorcentajeCom.setText(String.format("%.2f", sm.getPorcentajeComentarios()));
        
        if (sm.getComplejidadCiclomatica() <= 10)
            labelComplejidad.setTextFill(Color.web("#00aa00"));
        else if (sm.getComplejidadCiclomatica() <= 20)
            labelComplejidad.setTextFill(Color.web("#bbbb00"));
        else
            labelComplejidad.setTextFill(Color.web("#ff0000"));
        
        labelComplejidad.setText(String.valueOf(sm.getComplejidadCiclomatica()));
        labelFanIn.setText(String.valueOf(sm.getFanIn()));
        labelFanOut.setText(String.valueOf(sm.getFanOut()));
        labelHalsteadLong.setText(String.valueOf(sm.getHalsteadLong()));
        labelHalsteadVol.setText(String.format("%.2f", sm.getHalsteadVol()));
    }
    
    public void realizarReporte () {
        int totalLineasBlancas = 0;
        int totalLineasComentarios = 0;
        int totalLineas = 0;
        int totalArchivos = 0;
        int totalMetodos = 0;
        int maxCC = 0;
        int maxFanOut = 0;
        
        for (StatsMetodo sm : statsMetodos) {
            totalMetodos++;
            totalLineas += sm.getLineasTotales();
            totalLineasComentarios += sm.getLineasComentarios();
            
            if(sm.getFanOut() > maxFanOut)
                maxFanOut = sm.getFanOut();
            if(sm.getComplejidadCiclomatica() > maxCC)
                maxCC = sm.getComplejidadCiclomatica();
        }
        
        for (StatsArchivo sa : statsArchivos) {
            totalArchivos++;
            try {
                String[] lineas = sa.getText().split("\\r?\\n");
                for(String linea : lineas) {
                    if (StringUtils.isEmpty(linea) || StringUtils.isBlank(linea))
                        totalLineasBlancas++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR, "Error al intentar generar reporte.", ButtonType.CLOSE);
                alert.showAndWait();
            }
        }
        
        labelTotalArchivos.setText("" + totalArchivos);
        labelTotalMetodos.setText("" + totalMetodos);
        labelMaxCC.setText("" + maxCC);
        labelMaxFanOut.setText("" + maxFanOut);
        
        int total = totalLineas + totalLineasBlancas + totalLineasComentarios;
        double porcentajeBlancas = total == 0 ? 0: ((double)totalLineasBlancas / total) * 100;
        double porcentajeComentarios = total == 0 ? 0: ((double)totalLineasComentarios / total) * 100;
        double porcentajeCodigo = total == 0 ? 0: ((double)(totalLineas - totalLineasComentarios) / total) * 100;
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Lineas de comentarios", porcentajeComentarios),
                new PieChart.Data("Lineas en blanco", porcentajeBlancas),
                new PieChart.Data("Lineas de código", porcentajeCodigo));
        pieChart.setData(pieChartData);
        pieChart.setTitle("Reporte general");
        
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        caption.setTranslateX(e.getSceneX() - groupGeneral.getLayoutX() - caption.getWidth() / 2);
                        caption.setTranslateY(e.getSceneY() - groupGeneral.getLayoutY() - caption.getHeight() / 2);
                        caption.setText(String.format("%.2f", data.getPieValue()) + "%");
                     }
                });
        }
    }
}
