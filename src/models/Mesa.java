package models;

/**
 *
 * @author Juan Pablo
 */
public class Mesa {

    private int idMesa;
    private int capacidadPersonas;
    private String tiempo;
    private boolean estado;

    public Mesa(int idMesa, int capacidadPersonas, String tiempo, boolean estado) {
        this.idMesa = idMesa;
        this.capacidadPersonas = capacidadPersonas;
        this.tiempo = tiempo;
        this.estado = estado;
    }

    public Mesa() {
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getCapacidadPersonas() {
        return capacidadPersonas;
    }

    public void setCapacidadPersonas(int capacidadPersonas) {
        this.capacidadPersonas = capacidadPersonas;
    }

    @Override
    public String toString() {
        return "Mesa{" + "idMesa=" + idMesa + ", capacidadPersonas=" + capacidadPersonas + ", tiempo=" + tiempo + ", estado=" + estado + '}';
    }
}
