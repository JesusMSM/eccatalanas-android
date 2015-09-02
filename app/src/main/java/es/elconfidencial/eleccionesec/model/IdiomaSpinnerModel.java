package es.elconfidencial.eleccionesec.model;

/**
 * Created by Afll on 02/09/2015.
 */
public class IdiomaSpinnerModel {

    private  String nombre;
    private  String sigla;
    private  int imagenRes;

    public IdiomaSpinnerModel(String nombre, String sigla, int imagen) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.imagenRes = imagen;
    }

    public int getImagen() {
        return imagenRes;
    }

    public void setImagen(int imagen) {
        this.imagenRes = imagen;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
