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
public class Paciente {
    private int dni;
    private String nombre;
    
    public Paciente(String dni, String nombre) throws Exception {
        setDni(dni);
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) throws Exception {
        if (nombre != null && !nombre.trim().isEmpty())
            this.nombre = nombre;
        else throw new Exception("El paciente debe tener un nombre!");
    }
    
    public int getDni() {
        return dni;
    }

    public final void setDni(String dni) throws Exception {
        try {
            if (dni == null || dni.trim().isEmpty()) throw new NumberFormatException();
            if(dni.length() != 8) throw new NumberFormatException(); 
            this.dni = Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            throw new Exception("El DNI del paciente debe ser un número entero de 8 dígitos.");
        }
    }
}
