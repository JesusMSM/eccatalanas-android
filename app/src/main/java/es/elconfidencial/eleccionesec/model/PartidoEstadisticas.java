package es.elconfidencial.eleccionesec.model;

/**
 * Created by JesúsManuel on 20/08/2015.
 */
public class PartidoEstadisticas {

    public String getComunidadAutonoma() {
        return comunidadAutonoma;
    }

    public void setComunidadAutonoma(String comunidadAutonoma) {
        this.comunidadAutonoma = comunidadAutonoma;
    }

    public Double getPorcentajeObtenido() {
        return porcentajeObtenido;
    }

    public void setPorcentajeObtenido(Double porcentajeObtenido) {
        this.porcentajeObtenido = porcentajeObtenido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getElectedMembers() {
        return electedMembers;
    }

    public void setElectedMembers(String electedMembers) {
        this.electedMembers = electedMembers;
    }

    private String comunidadAutonoma = "";
    private Double porcentajeObtenido = 0.0;
    private String nombre ="";
    private String alias ="";
    private String color ="";
    private String electedMembers = "";


}
