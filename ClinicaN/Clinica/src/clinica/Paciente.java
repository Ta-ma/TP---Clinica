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
    
    /**
     *
     * @param dni
     * @param nombre
     * @throws Exception
     */
    public Paciente(String dni, String nombre) throws Exception {
        setDni(dni);
        setNombre(nombre);
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
                    throw new Exception("El paciente debe contener caracteres válidos!");
        } else {
        	throw new Exception("El paciente debe tener un nombre!");
        }
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
    public final void setDni(String dni) throws Exception {
        try {
            if (dni == null || dni.trim().isEmpty()) throw new NumberFormatException();
            if(dni.length() != 8) throw new NumberFormatException(); 
            this.dni = Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            throw new Exception("El DNI del paciente debe ser un número entero de 8 dígitos.");
        }
    }
    
    private boolean soloCaracteres(String cadena) {
    	for (int i = 0; i != cadena.length(); ++i) {
            if (!Character.isLetter(cadena.charAt(i)) && !Character.isWhitespace(cadena.charAt(i))) return false;
        }
        return true;
    }
    
    @Override
    public String toString () {
        return this.dni + " - " + this.nombre;
    }
}
