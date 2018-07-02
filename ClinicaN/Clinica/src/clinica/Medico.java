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
public final class Medico {
    private int dni;
    private String nombre, especialidad;

    public Medico(String dni, String nombre, String especialidad) throws Exception {
        setDni(dni);
        setNombre(nombre);
        setEspecialidad(especialidad);
    }
    
    public int getDni() {
        return dni;
    }

    public void setDni(String dni) throws Exception {
        try {
            if (dni == null || dni.trim().isEmpty()) throw new NumberFormatException();
            if(dni.length() != 8) throw new NumberFormatException(); 
            this.dni = Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            throw new Exception("El DNI del paciente debe ser un número entero de 8 dígitos.");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws Exception {
        if (nombre != null && !nombre.trim().isEmpty())
            this.nombre = nombre;
        else throw new Exception("El médico debe tener un nombre!");
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) throws Exception {
        if (especialidad != null && !especialidad.trim().isEmpty())
            this.especialidad = especialidad;
        else throw new Exception("El médico debe tener una especialidad.");
    }
}
