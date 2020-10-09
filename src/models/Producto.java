package models;

/**
 *
 * @author Juan Pablo
 */
public class Producto {

    private int id;
    private String nombre;
    private boolean servido;
    private String categoria;
    private String imagen;
    private int precio;

    public Producto(int id, String nombre, boolean servido, String categoria, String imagen, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.servido = servido;
        this.categoria = categoria;
        this.imagen = imagen;
        this.precio = precio;
    }

    public Producto() {
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isServido() {
        return servido;
    }

    public void setServido(boolean servido) {
        this.servido = servido;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", servido=" + servido + ", categoria=" + categoria + ", imagen=" + imagen + ", precio=" + precio + '}';
    }
}
