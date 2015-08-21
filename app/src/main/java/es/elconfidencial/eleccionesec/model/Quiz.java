package es.elconfidencial.eleccionesec.model;


/**
 * Created by MOONFISH on 01/08/2015.
 */
public class Quiz {
    private String titulo;
    private String autor;
    private String imagenUrl;
    private String link;

    public Quiz(){
        super();
    }

    public Quiz(String titulo, String autor, String imagenUrl, String link) {
        this.titulo = titulo;
        this.autor = autor;
        this.imagenUrl = imagenUrl;
        this.link = link;
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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}