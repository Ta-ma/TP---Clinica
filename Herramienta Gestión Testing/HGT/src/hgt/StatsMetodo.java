/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgt;

import java.util.Objects;

/**
 *
 * @author Santi
 */
public class StatsMetodo {
    private DefinicionMetodo defMetod;
    private String texto;
    private int lineasTotales;
    private int lineasComentarios;
    private int complejidadCiclomatica;
    private int fanIn;
    private int fanOut;
    private int halsteadLong;
    private double halsteadVol;
    
    public StatsMetodo () {
        defMetod = new DefinicionMetodo();
    }
    
    public StatsMetodo (DefinicionMetodo defMetod) {
        this.defMetod = defMetod;
    }

    public int getLineasTotales() {
        return lineasTotales;
    }

    public void setLineasTotales(int lineasTotales) {
        this.lineasTotales = lineasTotales;
    }

    public int getLineasComentarios() {
        return lineasComentarios;
    }

    public void setLineasComentarios(int lineasComentarios) {
        this.lineasComentarios = lineasComentarios;
    }

    public int getComplejidadCiclomatica() {
        return complejidadCiclomatica;
    }

    public void setComplejidadCiclomatica(int complejidadCiclomatica) {
        this.complejidadCiclomatica = complejidadCiclomatica;
    }

    public int getFanIn() {
        return fanIn;
    }

    public void setFanIn(int fanIn) {
        this.fanIn = fanIn;
    }

    public int getFanOut() {
        return fanOut;
    }

    public void setFanOut(int fanOut) {
        this.fanOut = fanOut;
    }

    public int getHalsteadLong() {
        return halsteadLong;
    }

    public void setHalsteadLong(int halsteadLong) {
        this.halsteadLong = halsteadLong;
    }

    public double getHalsteadVol() {
        return halsteadVol;
    }

    public void setHalsteadVol(double halsteadVol) {
        this.halsteadVol = halsteadVol;
    }
    
    public double getPorcentajeComentarios () {
        return lineasTotales == 0 ? 0: ((double)lineasComentarios / lineasTotales) * 100;
    }
    
    @Override
    public boolean equals (Object o) {
        /*if (o == null) return false;
        if (o == this) return true;*/
        if (!(o instanceof StatsMetodo)) return false;
        
        StatsMetodo otro = (StatsMetodo)o;
        return getDefMetod().equals(otro.getDefMetod());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.getDefMetod());
        return hash;
    }
    
    @Override
    public String toString() {
        return getDefMetod().getNombre();
    }
    
    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the defMetod
     */
    public DefinicionMetodo getDefMetod() {
        return defMetod;
    }

    /**
     * @param defMetod the defMetod to set
     */
    public void setDefMetod(DefinicionMetodo defMetod) {
        this.defMetod = defMetod;
    }
}
