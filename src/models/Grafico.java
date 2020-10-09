package models;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author Juan Pablo
 */
public class Grafico {

    private String nombre;
    private LinkedHashMap<String, Integer> mapa;
    private String valorX;
    private String valorY;

    public Grafico() {
    }

    public Grafico(String nombre, LinkedHashMap<String, Integer> mapa, String valorX, String valorY) {
        this.nombre = nombre;
        this.mapa = mapa;
        this.valorX = valorX;
        this.valorY = valorY;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, Integer> getMapa() {
        return mapa;
    }

    public void setMapa(LinkedHashMap<String, Integer> mapa) {
        this.mapa = mapa;
    }

    public String getValorX() {
        return valorX;
    }

    public void setValorX(String valorX) {
        this.valorX = valorX;
    }

    public String getValorY() {
        return valorY;
    }

    public void setValorY(String valorY) {
        this.valorY = valorY;
    }

}
