package models;

public class Conf {
    private int tema;
    private int idioma;
    private int ocultarElementos;
    private int ocultarVisualizacion;
    private int pulsacionLarga;
    private int cronometroRaton;
    private int tiempoInspeccion;
    private int segundosInspeccion;

    public Conf(){}

    public Conf(int tema, int idioma, int ocultarElementos, int ocultarVisualizacion, int pulsacionLarga, int cronometroRaton, int tiempoInspeccion, int segundosInspeccion) {
        this.tema = tema;
        this.idioma = idioma;
        this.ocultarElementos = ocultarElementos;
        this.ocultarVisualizacion = ocultarVisualizacion;
        this.pulsacionLarga = pulsacionLarga;
        this.cronometroRaton = cronometroRaton;
        this.tiempoInspeccion = tiempoInspeccion;
        this.segundosInspeccion = segundosInspeccion;
    }

    public int getTema() {
        return tema;
    }

    public void setTema(int tema) {
        this.tema = tema;
    }

    public int getIdioma() {
        return idioma;
    }

    public void setIdioma(int idioma) {
        this.idioma = idioma;
    }

    public int getOcultarElementos() {
        return ocultarElementos;
    }

    public void setOcultarElementos(int ocultarElementos) {
        this.ocultarElementos = ocultarElementos;
    }

    public int getOcultarVisualizacion() {
        return ocultarVisualizacion;
    }

    public void setOcultarVisualizacion(int ocultarVisualizacion) {
        this.ocultarVisualizacion = ocultarVisualizacion;
    }

    public int getPulsacionLarga() {
        return pulsacionLarga;
    }

    public void setPulsacionLarga(int pulsacionLarga) {
        this.pulsacionLarga = pulsacionLarga;
    }

    public int getCronometroRaton() {
        return cronometroRaton;
    }

    public void setCronometroRaton(int cronometroRaton) {
        this.cronometroRaton = cronometroRaton;
    }

    public int getTiempoInspeccion() {
        return tiempoInspeccion;
    }

    public void setTiempoInspeccion(int tiempoInspeccion) {
        this.tiempoInspeccion = tiempoInspeccion;
    }

    public int getSegundosInspeccion() {
        return segundosInspeccion;
    }

    public void setSegundosInspeccion(int segundosInspeccion) {
        this.segundosInspeccion = segundosInspeccion;
    }
}
