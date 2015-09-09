package es.elconfidencial.eleccionesec.model;


/**
 * Created by MOONFISH on 01/08/2015.
 */
public class Quiz {
    private int imagen;
    private String titulo;
    private String autor;
    private String link;

    public Quiz(){
        super();
    }

    public Quiz(int imagen, String titulo, String autor, String link) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.autor = autor;
        this.link = link;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}