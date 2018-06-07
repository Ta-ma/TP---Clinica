/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgt;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Santi
 */
class DefinicionMetodo {
    private String clase;
    private String nombre;
    private List<String> argumentos;
    
    public DefinicionMetodo () {
        clase = "";
        nombre = "";
        argumentos = new LinkedList<>();
    }

    public DefinicionMetodo (String clase, String nombre, List<String> argumentos) {
        this.clase = clase;
        this.nombre = nombre;
        this.argumentos = argumentos;
    }
    
    /**
     * @return the clase
     */
    public String getClase() {
        return clase;
    }

    /**
     * @param clase the clase to set
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the argumentos
     */
    public List<String> getArgumentos() {
        return argumentos;
    }

    /**
     * @param argumentos the argumentos to set
     */
    public void setArgumentos(List<String> argumentos) {
        this.argumentos = argumentos;
    }
    
    @Override
    public boolean equals (Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof DefinicionMetodo))return false;
        
        DefinicionMetodo otro = (DefinicionMetodo)o;
        return this.nombre.equals(otro.nombre) && this.clase.equals(otro.clase)
                && this.argumentos.equals(otro.argumentos);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.clase);
        hash = 23 * hash + Objects.hashCode(this.nombre);
        hash = 23 * hash + Objects.hashCode(this.argumentos);
        return hash;
    }
    
    @Override
    public String toString() {
        return this.clase + " " + this.nombre + " " + this.argumentos.toString();
    }
}
