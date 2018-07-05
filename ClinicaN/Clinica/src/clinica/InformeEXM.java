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
public class InformeEXM {
    private String dniPac, nombrePac, diagnostico;

    /**
     *
     * @param dniPac
     * @param nombrePac
     * @param diagnostico
     */
    public InformeEXM(String dniPac, String nombrePac, String diagnostico) {
        this.dniPac = dniPac;
        this.nombrePac = nombrePac;
        this.diagnostico = diagnostico;
    }

    /**
     *
     * @return
     */
    public String getDniPac() {
        return dniPac;
    }

    /**
     *
     * @param dniPac
     */
    public void setDniPac(String dniPac) {
        this.dniPac = dniPac;
    }

    /**
     *
     * @return
     */
    public String getNombrePac() {
        return nombrePac;
    }

    /**
     *
     * @param nombrePac
     */
    public void setNombrePac(String nombrePac) {
        this.nombrePac = nombrePac;
    }

    /**
     *
     * @return
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     *
     * @param diagnostico
     */
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    
}
