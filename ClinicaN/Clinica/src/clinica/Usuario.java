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
public class Usuario {
    private String nombre, password, email;
    
    public Usuario(String nombre, String password, String email) throws Exception {
        setNombre(nombre);
        setPassword(password);
        setEmail(email);
    }
    
    public String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) throws Exception {
        if (nombre != null && !nombre.trim().isEmpty())
            this.nombre = nombre;
        else throw new Exception("El usuario debe tener un nombre!");
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String password) throws Exception {
        if (password != null && !password.trim().isEmpty())
            this.password = password;
        else throw new Exception("El usuario debe tener una contraseña.");
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail (String email) throws Exception {
        if (email != null && !email.trim().isEmpty())
            this.email = email;
        else throw new Exception("El usuario debe tener un mail.");
    }
}
