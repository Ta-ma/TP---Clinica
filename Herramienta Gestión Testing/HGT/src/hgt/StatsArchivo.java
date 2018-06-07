/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Santi
 */
public class StatsArchivo {
    private File archivo;
    private List<StatsMetodo> metodos;
    
    public StatsArchivo (File archivo) {
        this.archivo = archivo;
        metodos = new LinkedList<>();
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public List<StatsMetodo> getMetodos() {
        return metodos;
    }

    public void setMetodos(List<StatsMetodo> metodos) {
        this.metodos = metodos;
    }
    
    @Override
    public boolean equals (Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof StatsArchivo))return false;
        
        StatsArchivo otro = (StatsArchivo)o;
        return this.archivo.equals(otro.archivo);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.archivo);
        return hash;
    }
    
    public String getText() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(archivo.getAbsolutePath()));
        return new String(encoded, "ISO-8859-1");
    }
}
