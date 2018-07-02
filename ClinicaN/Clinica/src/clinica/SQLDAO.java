/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinica;

import java.io.File;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author Santi
 */
public class SQLDAO {
    final String DB = "clinica.db";
    Connection c = null;
    
    /**
     * Constructor del DAO, inicializa las tablas si el archivo no existe.
     */
    public SQLDAO () {
        File archivo = new File(DB);
        try {
            if (archivo.exists()) {
                SQLiteConfig config = new SQLiteConfig();  
                config.enforceForeignKeys(true); 
                c = DriverManager.getConnection("jdbc:sqlite:" + DB, config.toProperties());
            } else {
                Class.forName("org.sqlite.JDBC");
                SQLiteConfig config = new SQLiteConfig();  
                config.enforceForeignKeys(true); 
                c = DriverManager.getConnection("jdbc:sqlite:" + DB, config.toProperties());
                inicializar();
                System.out.println("Instanciado archivo de base de datos.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Conexión con base de datos establecida.");
    }
    
    /**
     * Inicialización de las tablas de la base de datos.
     */
    private void inicializar() {
        String sql = null;
        try {
            // creación de tablas por defecto
            Statement stmt = c.createStatement();
            
            // usuarios
            sql =   "CREATE TABLE USUARIOS " +
                    " (NOMBRE    TEXT           PRIMARY KEY     NOT NULL, " + 
                    " PASSWORD   CHAR(50)       NOT NULL, " + 
                    " EMAIL      TEXT)"; 
            stmt.executeUpdate(sql);
            
            // pacientes
            sql =   "CREATE TABLE PACIENTES " +
                    "(DNI INTEGER PRIMARY KEY NOT NULL," +
                    " NOMBRE     TEXT        NOT NULL)";
            stmt.executeUpdate(sql);
            
            // médicos
            sql =   "CREATE TABLE MEDICOS " +
                    "(DNI INTEGER PRIMARY KEY NOT NULL," +
                    " ESPECIALIDAD   TEXT    NOT NULL," +
                    " NOMBRE            TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            
            // situaciones
            sql =   "CREATE TABLE SITUACIONES " +
                    "(ID INTEGER PRIMARY KEY NOT NULL," +
                    " DNIPACIENTE    INT    NOT NULL," +
                    " DNIMEDICO      INT    NOT NULL," +
                    " DIAGNOSTICO   TEXT," +
                    " FOREIGN KEY(DNIPACIENTE) REFERENCES PACIENTES (DNI)" +
                    " FOREIGN KEY(DNIMEDICO) REFERENCES MEDICOS (DNI)" + ")";
            stmt.executeUpdate(sql);
            
            // creación del usuario admin
            sql = "INSERT INTO USUARIOS (NOMBRE, PASSWORD, EMAIL) " +
                        "VALUES ('admin', 'root', 'admin@hotmail.com');"; 
            stmt.executeUpdate(sql);
            
            // cierro el statement
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
    
    /**
     * Método para cerrar la conexión con la base de datos
     */
    public void cerrar() {
        try {
            c.close();
        } catch (SQLException e) {
            // no se que es lo que puede llegar a pasar pero bueno
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
    
    /**
     * Método para lanzar excepciones no reconocidas de SQLite
     * @param e
     * @throws Exception 
     */
    private void lanzarEx (SQLException e) throws Exception {
        throw new Exception("Error en la base de datos."
                        + "\nCódigo de error: " + e.getErrorCode() + "\nMensaje: " + e.getMessage());
    }
    
    /**
     * Método para validar el usuario en el login
     * @param nombre
     * @param password
     * @return 
     */
    public boolean validarUsuario(String nombre, String password) {
        boolean res = false;
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT PASSWORD FROM USUARIOS WHERE NOMBRE='" + nombre + "';";
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                String bdPassword = rs.getString("PASSWORD");
                if (bdPassword.equals(password))
                    res = true;
            }
            // cierro el statement
            stmt.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return res;
    }
    
    public void insertarUsuario(Usuario usuario) throws Exception {
        String nombre = usuario.getNombre();
        String password = usuario.getPassword();
        String email = usuario.getEmail();
        try {
            // agrego el usuario
            String sql = "INSERT INTO USUARIOS (NOMBRE, PASSWORD, EMAIL) " +
                        "VALUES ('" + nombre + "', '" + password + "', '" + email + "');";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.execute();
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 19:
                    throw new Exception("Este nombre de usuario ya existe.");
                default:
                    lanzarEx(e);
            }
        }
    }
    
    public void eliminarUsuario (String nombre) throws Exception {
        try {
            // borro el usuario
            String sql = "DELETE FROM USUARIOS WHERE NOMBRE='" + nombre + "';";
            PreparedStatement ps = c.prepareStatement(sql);
            if(ps.executeUpdate() == 0) {
                ps.close();
                throw new Exception("No se encontró ningún registro con ese nombre de usuario.");
            }
            // cierro el statement
            
        } catch (SQLException e) {
            lanzarEx(e);
        }
    }
    
    public void actualizarUsuario (Usuario usuario) throws Exception {
        String nombre = usuario.getNombre();
        String password = usuario.getPassword();
        String email = usuario.getEmail();
        try {
            // actualizo el usuario
            String sql = "UPDATE USUARIOS "
                + "SET PASSWORD='" + password + "', EMAIL='" + email + "' "
                + "WHERE NOMBRE='" + nombre + "';";
            PreparedStatement ps = c.prepareStatement(sql);
            if(ps.executeUpdate() == 0) {
                ps.close();
                throw new Exception("No se encontró ningún registro con ese nombre de usuario.");
            }
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
    }
    
    public ObservableList<Usuario> obtenerUsuarios () throws Exception {
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT NOMBRE, PASSWORD, EMAIL FROM USUARIOS;";
            ResultSet rs = stmt.executeQuery(sql);
            // voy agregando los usuarios a la lista
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getString("NOMBRE"), rs.getString("PASSWORD"), rs.getString("EMAIL")));
            }
            // cierro el statement
            stmt.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
        return usuarios;
    }
    
    public void insertarPaciente (Paciente paciente) throws Exception {
        int dni = paciente.getDni();
        String nombre = paciente.getNombre();
        try {
            // agrego el paciente
            String sql = "INSERT INTO PACIENTES (DNI, NOMBRE) " +
                        "VALUES ( " + dni + ", '" + nombre + "');";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.execute();
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 19:
                    throw new Exception("Este DNI de paciente ya existe.");
                default:
                    lanzarEx(e);
            }
        }
    }
    
    public void eliminarPaciente (int dni) throws Exception {
        try {
            // borro el paciente
            String sql = "DELETE FROM PACIENTES WHERE DNI=" + dni + ";";
            PreparedStatement ps = c.prepareStatement(sql);
            if(ps.executeUpdate() == 0) {
                ps.close();
                throw new Exception("No se encontró ningún registro con ese DNI de paciente.");
            }
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
    }
    
    public void actualizarPaciente (Paciente paciente) throws Exception {
        int dni = paciente.getDni();
        String nombre = paciente.getNombre();
        try {
            // actualizo el paciente
            String sql = "UPDATE PACIENTES "
                + "SET NOMBRE='" + nombre + "' "
                + "WHERE DNI=" + dni + ";";
            PreparedStatement ps = c.prepareStatement(sql);
            if(ps.executeUpdate() == 0) {
                ps.close();
                throw new Exception("No se encontró ningún registro con ese DNI de paciente.");
            }
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
    }
    
    public ObservableList<Paciente> obtenerPacientes () throws Exception  {
        ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT DNI, NOMBRE FROM PACIENTES;";
            ResultSet rs = stmt.executeQuery(sql);
            // voy agregando los pacientes a la lista
            while (rs.next()) {
                pacientes.add(new Paciente(rs.getString("DNI"), rs.getString("NOMBRE")));
            }
            // cierro el statement
            stmt.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
        return pacientes;
    }
    
    public void insertarMedico (Medico med) throws Exception {
        int dni = med.getDni();
        String nombre = med.getNombre();
        String especialidad = med.getEspecialidad();
        try {
            // agrego el médico
            String sql = "INSERT INTO MEDICOS (DNI, NOMBRE, ESPECIALIDAD) " +
                        "VALUES (" + dni + ", '" + nombre + "', '" + especialidad + "');";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.execute();
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 19:
                    throw new Exception("Este DNI de médico ya existe.");
                default:
                    lanzarEx(e);
            }
        }
    }
    
    public void eliminarMedico (int dni) throws Exception {
        try {
            // borro el médico
            String sql = "DELETE FROM MEDICOS WHERE DNI=" + dni + ";";
            PreparedStatement ps = c.prepareStatement(sql);
            if(ps.executeUpdate() == 0) {
                ps.close();
                throw new Exception("No se encontró ningún registro con ese DNI de médico.");
            }
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
    }
    
    public void actualizarMedico (Medico med) throws Exception {
        int dni = med.getDni();
        String nombre = med.getNombre();
        String especialidad = med.getEspecialidad();
        try {
            // actualizo el médico
            String sql = "UPDATE MEDICOS "
                + "SET NOMBRE='" + nombre + "', ESPECIALIDAD='" + especialidad + "' "
                + "WHERE DNI=" + dni + ";";
            PreparedStatement ps = c.prepareStatement(sql);
            if(ps.executeUpdate() == 0) {
                ps.close();
                throw new Exception("No se encontró ningún registro con ese DNI de médico.");
            }
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
    }
    
    public ObservableList<Medico> obtenerMedicos () throws Exception  {
        ObservableList<Medico> medicos = FXCollections.observableArrayList();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT DNI, NOMBRE, ESPECIALIDAD FROM MEDICOS;";
            ResultSet rs = stmt.executeQuery(sql);
            // voy agregando los médicos a la lista
            while (rs.next()) {
                medicos.add(new Medico(rs.getString("DNI"), rs.getString("NOMBRE"), rs.getString("ESPECIALIDAD")));
            }
            // cierro el statement
            stmt.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
        return medicos;
    }
    
    public void insertarSituacion (Situacion sit) throws Exception {
        int id = sit.getId();
        int dniPac = sit.getDniPaciente();
        int dniMed = sit.getDniMedico();
        String diag = sit.getDiagnostico();
        try {
            // agrego la situación
            String sql = "INSERT INTO SITUACIONES (ID, DNIPACIENTE, DNIMEDICO, DIAGNOSTICO) " +
                        "VALUES (" + id + ", " + dniPac + ", " + dniMed + ", '" + diag + "');";
            
            PreparedStatement ps = c.prepareStatement(sql);
            ps.execute();
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 19:
                    throw new Exception("Error al agregar situación.\n"
                            + "Verifique que la ID de situación no existe actualmente, y que los DNI del paciente y del médico"
                            + " están cargados en los registros de Pacientes y Médicos.");
                default:
                    lanzarEx(e);
            }
        }
    }
    
    public void eliminarSituacion (int id) throws Exception {
        try {
            // borro la situación
            String sql = "DELETE FROM SITUACIONES WHERE ID=" + id + ";";
            PreparedStatement ps = c.prepareStatement(sql);
            if(ps.executeUpdate() == 0) {
                ps.close();
                throw new Exception("No se encontró ningún registro con esa ID de situación.");
            }
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
    }
    
    public void actualizarSituacion (Situacion sit) throws Exception {
        int id = sit.getId();
        int dniPac = sit.getDniPaciente();
        int dniMed = sit.getDniMedico();
        String diag = sit.getDiagnostico();
        try {
            // actualizo la situación
            String sql = "UPDATE SITUACIONES "
                + "SET DNIPACIENTE=" + dniPac + ", DNIMEDICO=" + dniMed + ", '" + diag + "'"
                + "WHERE ID=" + id + ";";
            PreparedStatement ps = c.prepareStatement(sql);
            if(ps.executeUpdate("PRAGMA foreign_keys = ON") == 0) {
                ps.close();
                throw new Exception("No se encontró ningún registro con esa ID de situación.");
            }
            // cierro el statement
            ps.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
    }
    
    public ObservableList<Situacion> obtenerSituaciones () throws Exception  {
        ObservableList<Situacion> sits = FXCollections.observableArrayList();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT ID, DNIPACIENTE, DNIMEDICO, DIAGNOSTICO FROM SITUACIONES;";
            ResultSet rs = stmt.executeQuery(sql);
            // voy agregando las situaciones a la lista
            while (rs.next()) {
                sits.add(new Situacion(rs.getString("ID"), rs.getString("DNIPACIENTE"),
                    rs.getString("DNIMEDICO"), rs.getString("DIAGNOSTICO")));
            }
            // cierro el statement
            stmt.close();
        } catch (SQLException e) {
            lanzarEx(e);
        }
        return sits;
    }
}
