/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinica;

import java.io.File;
import java.sql.*;

/**
 *
 * @author Santi
 */
public class SQLDAO {
    final String DB = "clinica.db";
    Connection c = null;
    Statement stmt = null;
    
    /**
     * Constructor del DAO, inicializa las tablas si el archivo no existe.
     */
    public SQLDAO () {
        File archivo = new File(DB);
        try {
            if (archivo.exists()) {
                c = DriverManager.getConnection("jdbc:sqlite:" + DB);
            } else {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:" + DB);
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
            stmt = c.createStatement();
            
            // usuarios
            sql =   "CREATE TABLE USUARIOS " +
                    "(ID INTEGER PRIMARY KEY    AUTOINCREMENT  NOT NULL," +
                    " NOMBRE     TEXT           NOT NULL, " + 
                    " PASSWORD   CHAR(50)       NOT NULL, " + 
                    " EMAIL      TEXT)"; 
            stmt.executeUpdate(sql);
            
            // pacientes
            sql =   "CREATE TABLE PACIENTES " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NOMBRE     TEXT        NOT NULL)";
            stmt.executeUpdate(sql);
            
            // médicos
            sql =   "CREATE TABLE MEDICOS " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " ESPECIALIZACION   TEXT    NOT NULL," +
                    " NOMBRE            TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            
            // situaciones
            sql =   "CREATE TABLE SITUACIONES " +
                    "(IDPACIENTE    INT    NOT NULL," +
                    " IDMEDICO      INT    NOT NULL," +
                    " DIAGNOSTICO   TEXT)";
            stmt.executeUpdate(sql);
            
            // creación del usuario admin
            sql = "INSERT INTO USUARIOS (NOMBRE, PASSWORD) " +
                        "VALUES ('admin', 'root');"; 
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
     * Método para validar el usuario en el login
     * @param nombre
     * @param password
     * @return 
     */
    public boolean validarUsuario(String nombre, String password) {
        boolean res = false;
        try {
            String sql = "SELECT PASSWORD FROM USUARIOS WHERE NOMBRE = '" + nombre + "';";
            ResultSet rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                String bdPassword = rs.getString("PASSWORD");
                if (bdPassword.equals(password))
                    res = true;
            }
            // cierro el statement
            stmt.close();
        } catch (SQLException e) {
            // no se que es lo que puede llegar a pasar pero bueno
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        
        return res;
    }
}
