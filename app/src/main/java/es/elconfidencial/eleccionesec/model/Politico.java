package es.elconfidencial.eleccionesec.model;

/**
 * Created by MOONFISH on 19/08/2015.
 */
public class Politico {
    private int imagen;
    private String nombre;
    private String edad;
    private String partido;
    private String cargo;
    private String perfil;

    public Politico(int imagen, String nombre, String edad, String partido, String cargo, String perfil) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.edad = edad;
        this.partido = partido;
        this.cargo = cargo;
        this.perfil = perfil;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
