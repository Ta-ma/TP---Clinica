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
    /**
     *
     * @param dni
     * @param nombre
     * @param especialidad
     * @throws Exception
     */
    public Medico(String dni, String nombre, String especialidad) throws Exception {
        setDni(dni);
        setNombre(nombre);
        setEspecialidad(especialidad);
    }
    /**
     *
     * @return
     */
    public int getDni() {
        return dni;
    }
    /**
     *
     * @param dni
     * @throws Exception
     */
    public void setDni(String dni) throws Exception {
        try {
            if (dni == null || dni.trim().isEmpty()) throw new NumberFormatException();
            if(dni.length() != 8) throw new NumberFormatException(); 
            this.dni = Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            throw new Exception("El DNI del paciente debe ser un número entero de 8 dígitos.");
        }
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
     * @throws Exception 
     */
    public final void setNombre(String nombre) throws Exception {
        if (nombre != null && !nombre.trim().isEmpty()) {
        	if(this.soloCaracteres(nombre))
                    this.nombre = nombre;
        	else 
                    throw new Exception("El médico debe contener caracteres válidos!");
        } else {
        	throw new Exception("El médico debe tener un nombre!");
        }
    }
    /**
     *
     * @return
     */
    public String getEspecialidad() {
        return especialidad;
    }
    /**
     * 
     * @param especialidad
     * @throws Exception 
     */
    public void setEspecialidad(String especialidad) throws Exception {
        if (especialidad != null && !especialidad.trim().isEmpty())
            this.especialidad = especialidad;
        else throw new Exception("El médico debe tener una especialidad.");
    }
    /**
     * 
     * @param cadena
     * @return 
     */
    private boolean soloCaracteres(String cadena) {
    	for (int i = 0; i != cadena.length(); ++i) {
            if (!Character.isLetter(cadena.charAt(i)) && !Character.isWhitespace(cadena.charAt(i))) return false;
        }
        return true;
    }
    @Override
    public String toString () {
        return "Dr. " + this.nombre + " (" + this.dni + ")";
    }
}
