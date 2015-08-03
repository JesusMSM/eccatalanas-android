package es.elconfidencial.eleccionesec.model;

/**
 * Created by Afll on 01/08/2015.
 */
public class Noticia {
    private String titulo;
    private String descripcion;
    private String autor;
    private int imagen;

    public Noticia(String titulo, String descripcion, String autor, int imagen){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
