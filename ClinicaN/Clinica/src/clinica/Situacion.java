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
public class Situacion {
    private int id, dniPaciente, dniMedico;
    private String diagnostico;

    public Situacion (String id, String dniPaciente, String dniMedico, String diagnostico) throws Exception {
        setDiagnostico(diagnostico);
        setDniMedico(dniMedico);
        setDniPaciente(dniPaciente);
        setId(id);
    }

    public int getId() {
        return id;
    }

    public final void setId(String id) throws Exception {
        try {
            if (id == null || id.trim().isEmpty()) throw new NumberFormatException();
            this.id = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new Exception("La ID debe ser un número válido.");
        }
    }

    public int getDniPaciente() {
        return dniPaciente;
    }

    public final void setDniPaciente(String dniPaciente) throws Exception {
        try {
            if (dniPaciente == null || dniPaciente.trim().isEmpty()) throw new NumberFormatException();
            if(dniPaciente.length() != 8) throw new NumberFormatException(); 
            this.dniPaciente = Integer.parseInt(dniPaciente);
        } catch (NumberFormatException e) {
            throw new Exception("El DNI del paciente debe ser un número entero de 8 dígitos.");
        }
    }

    public int getDniMedico() {
        return dniMedico;
    }

    public final void setDniMedico(String dniMedico) throws Exception {
        try {
            if (dniMedico == null || dniMedico.trim().isEmpty()) throw new NumberFormatException();
            if(dniMedico.length() != 8) throw new NumberFormatException();
            this.dniMedico = Integer.parseInt(dniMedico);
        } catch (NumberFormatException e) {
            throw new Exception("El DNI del médico debe ser un número entero de 8 dígitos.");
        }
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public final void setDiagnostico(String diagnostico) throws Exception {
        if (diagnostico != null && !diagnostico.trim().isEmpty())
            this.diagnostico = diagnostico;
        else throw new Exception("La situación debe tener un diagnóstico.");
    }
}
