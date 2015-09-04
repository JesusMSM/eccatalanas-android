package es.elconfidencial.eleccionesec.model;

/**
 * Created by MOONFISH on 04/09/2015.
 */
public class Mensaje {
    private String mensaje;

    public Mensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}