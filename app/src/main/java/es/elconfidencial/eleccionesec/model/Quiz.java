package es.elconfidencial.eleccionesec.model;


/**
 * Created by MOONFISH on 01/08/2015.
 */
public class Quiz {
    private int image;
    private String title;
    private String subtitle;

    public Quiz(int image, String title, String subtitle) {
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}