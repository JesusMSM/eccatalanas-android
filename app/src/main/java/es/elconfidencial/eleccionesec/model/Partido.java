package es.elconfidencial.eleccionesec.model;

/**
 * Created by MOONFISH on 19/08/2015.
 */
public class Partido {
    private String nombre;
    private String presidente;
    private String secGeneral;
    private String portavozCongreso;
    private String portavozSenado;
    private String fundacion;
    private String ideologia;
    private String posicionEspectro;
    private String sede;

    public Partido(String nombre, String presidente, String secGeneral, String portavozCongreso, String portavozSenado, String fundacion, String ideologia, String posicionEspectro, String sede) {
        this.nombre = nombre;
        this.presidente = presidente;
        this.secGeneral = secGeneral;
        this.portavozCongreso = portavozCongreso;
        this.portavozSenado = portavozSenado;
        this.fundacion = fundacion;
        this.ideologia = ideologia;
        this.posicionEspectro = posicionEspectro;
        this.sede = sede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getSecGeneral() {
        return secGeneral;
    }

    public void setSecGeneral(String secGeneral) {
        this.secGeneral = secGeneral;
    }

    public String getPortavozCongreso() {
        return portavozCongreso;
    }

    public void setPortavozCongreso(String portavozCongreso) {
        this.portavozCongreso = portavozCongreso;
    }

    public String getPortavozSenado() {
        return portavozSenado;
    }

    public void setPortavozSenado(String portavozSenado) {
        this.portavozSenado = portavozSenado;
    }

    public String getFundacion() {
        return fundacion;
    }

    public void setFundacion(String fundacion) {
        this.fundacion = fundacion;
    }

    public String getIdeologia() {
        return ideologia;
    }

    public void setIdeologia(String ideologia) {
        this.ideologia = ideologia;
    }

    public String getPosicionEspectro() {
        return posicionEspectro;
    }

    public void setPosicionEspectro(String posicionEspectro) {
        this.posicionEspectro = posicionEspectro;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
}
