/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinica;

/**
 *
 * @author Santi
 */
public class TreeEntity {
    private String nombre;
    private String dni;
    private boolean esPaciente;

    /**
     *
     */
    public TreeEntity() { }
    
    /**
     *
     * @param dni
     * @param nombre
     * @param esPaciente
     */
    public TreeEntity(String dni, String nombre, boolean esPaciente) {
        this.nombre = nombre;
        this.dni = dni;
        this.esPaciente = esPaciente;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getDni() {
        return dni;
    }

    /**
     *
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     *
     * @return
     */
    public boolean esPaciente() {
        return esPaciente;
    }

    /**
     *
     * @param esPaciente
     */
    public void setEsPaciente(boolean esPaciente) {
        this.esPaciente = esPaciente;
    }
    
    @Override
    public String toString() {
        if (this.esPaciente)
            return this.nombre + " (" + this.dni + ")";
        else
            return "Dr. " + this.nombre + " (" + this.dni + ")";
    }
}
