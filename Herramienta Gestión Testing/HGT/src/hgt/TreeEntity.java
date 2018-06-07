package hgt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class TreeEntity {
    private String name;
    private File file;
    private StatsArchivo statsArchivo;
    
    public TreeEntity (String name, File file) {
        this.name = name;
        this.file = file;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    public boolean fileIsDirectory () {
        return file.isDirectory();
    }
    
    public String getFileText() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
        return new String(encoded, "ISO-8859-1");
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    public StatsArchivo getStatsArchivo() {
        return statsArchivo;
    }

    public void setStatsArchivo(StatsArchivo statsArchivo) {
        this.statsArchivo = statsArchivo;
    }
}
