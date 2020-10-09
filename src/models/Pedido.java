package models;

/**
 *
 * @author Juan Pablo
 */
public class Pedido {

    private String id;
    private int mesa;
    private String estado;
    private String idMesero;
    private int personas;
    private int total;
    private boolean hoy;

    public Pedido(String id, int mesa, String estado, int personas, int total, boolean hoy, String idMesero) {
        this.id = id;
        this.mesa = mesa;
        this.estado = estado;
        this.personas = personas;
        this.total = total;
        this.hoy = hoy;
        this.idMesero = idMesero;
    }

    public Pedido() {
    }

    public String getIdMesero() {
        return idMesero;
    }

    public void setIdMesero(String idMesero) {
        this.idMesero = idMesero;
    }

    public boolean isHoy() {
        return hoy;
    }

    public void setHoy(boolean hoy) {
        this.hoy = hoy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPersonas() {
        return personas;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
