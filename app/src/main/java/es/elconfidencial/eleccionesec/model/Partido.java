package es.elconfidencial.eleccionesec.model;

/**
 * Created by MOONFISH on 19/08/2015.
 */
public class Partido {
    private int imagen;
    private String nombre;
    private String representantes;
    private String fundacion;
    private String escanos;
    private String porcentajeVotos;
    private String ideologia;
    private String partidosRepresentados;
    private String perfil;

    public Partido(int imagen, String nombre, String representantes, String fundacion, String escanos, String porcentajeVotos, String ideologia, String partidosRepresentados, String perfil) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.representantes = representantes;
        this.fundacion = fundacion;
        this.escanos = escanos;
        this.porcentajeVotos = porcentajeVotos;
        this.ideologia = ideologia;
        this.partidosRepresentados = partidosRepresentados;
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

    public String getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(String representantes) {
        this.representantes = representantes;
    }

    public String getFundacion() {
        return fundacion;
    }

    public void setFundacion(String fundacion) {
        this.fundacion = fundacion;
    }

    public String getEscanos() {
        return escanos;
    }

    public void setEscanos(String escanos) {
        this.escanos = escanos;
    }

    public String getPorcentajeVotos() {
        return porcentajeVotos;
    }

    public void setPorcentajeVotos(String porcentajeVotos) {
        this.porcentajeVotos = porcentajeVotos;
    }

    public String getIdeologia() {
        return ideologia;
    }

    public void setIdeologia(String ideologia) {
        this.ideologia = ideologia;
    }

    public String getPartidosRepresentados() {
        return partidosRepresentados;
    }

    public void setPartidosRepresentados(String partidosRepresentados) {
        this.partidosRepresentados = partidosRepresentados;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}