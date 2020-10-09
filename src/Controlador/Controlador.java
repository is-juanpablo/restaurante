package Controlador;

import Interfaz.InterfazApp;
import Interfaz.PanelCardInicio;
import Interfaz.PanelEstadistica;
import Interfaz.PanelInicio;
import Interfaz.PanelPedidosInicio;
import Interfaz.PanelProductos;
import Mundo.SQL;
import java.util.ArrayList;
import javax.swing.JPanel;
import models.Balance;
import models.Detalle;
import models.Empleado;
import models.Grafico;
import models.Mesa;
import models.Pedido;
import models.Producto;

/**
 *
 * @author Juan Pablo
 */
public class Controlador {

    private InterfazApp interfaz;
    private final SQL sql;
    private PanelInicio pnlInicio;
    private PanelCardInicio pnlCard;
    private PanelProductos pnlProductos;
    private PanelEstadistica pnlEstadistica;

    public Controlador() {
        sql = new SQL();
    }

    public void conectarInterfaz(InterfazApp interfaz) {
        this.interfaz = interfaz;
    }

    public void conectarPanel(JPanel panel) {
        interfaz.conectarPanel(panel);
    }

    public void conectarPanelCard(PanelCardInicio pnlCard) {
        this.pnlCard = pnlCard;
    }

    public void conectarPanelProducto(PanelProductos pnlProductos) {
        this.pnlProductos = pnlProductos;
    }

    public void conectarPanelInicio(PanelInicio pnlInicio) {
        this.pnlInicio = pnlInicio;
    }

    //Consulta los pedidos que se le pidan
    public ArrayList<Pedido> consultaPedidos(int limite) {
        return sql.consultaPedidos(limite);
    }

    //Consulta las mesas
    public ArrayList<Mesa> consultaMesas() {
        return sql.consultaMesa();
    }

    //Consulta todos los productos
    public ArrayList<Producto> consultaProductos() {
        return sql.consultaProducto();
    }

    //Consulta el balance en una fecha (hoy, semana, mes, semestre)
    public Balance consultaBalanceFecha(String fecha) {
        return sql.consultaBalanceFecha(fecha);
    }

    /*
    //Consulta el pedido de acuerdo a un ID
    public ArrayList<Detalle> consultaDetalle(String id){
        return sql.consultaDetalle(id);
    }
     */
    //
    public int consultaTotal() {
        return sql.consultaTotal();
    }

    //Calcula el valor del dia
    public void consultaBalance() {
        sql.valorDia();
    }

    //Update de los valor objetivo en el balance
    public void modificarValorObjetivo(int valor, int posicion) {
        sql.modificarValorObjetivo(valor, posicion);
    }

    //Update de los productos para server
    public void modificarValorCheck(boolean estadoCheck, int posicion) {
        sql.modificarValorCheck(estadoCheck, posicion);
    }

    //Insertar un producto en la DB
    public void insertarProducto(String nombre, String categoria, String url, boolean servido, String precio) {
        sql.insertarProducto(nombre, categoria, url, servido, precio);
    }

    //Eliminar un producto en la DB
    public void eliminarProducto(int posicion) {
        sql.eliminarProducto(posicion);
    }

    //Set de valor select para los botones de productos
    public void cambioSelectProducto(String select) {
        sql.cambioSelectProducto(select);
    }

    //Select de un producto para el JFrame
    public Producto consultaProductos(int posicion) {
        return sql.consultaProductoID(posicion);
    }

    //Modifica un valor del producto JFrame
    public void modificarProducto(String categoria, String valor, int posicion) {
        sql.modificarProducto(categoria, valor, posicion);
        llamarHiloProducto();
    }

    //Rediseña los productos
    public void llamarHiloProducto() {
        pnlProductos.hiloProductos();
    }

    //Buscar pedido de acuerdo a un valor
    public Pedido buscarPedido(String valor) {
        return sql.buscarPedido(valor);
    }

    public Object[][] consultaIngredientes(int id) {
        return sql.consultaIngredientes(id);
    }

    public Object[][] consultaDetalle(String id) {
        return sql.consultaDetalle(id);
    }

    public void modificarPedido(int numeroMesa, String idOrden, String precioTotal, String idMesero) {
        sql.modificarPedido(numeroMesa, idOrden, precioTotal, idMesero);
    }

    public void hiloBalance() {
        pnlInicio.hiloBalance();
    }

    public boolean iniciarSesion(String usuario, String contrasenia) {
        return sql.iniciarSesion(usuario, contrasenia);
    }

    public Object[][] consultaEmpleados(String columna) {
        return sql.consultaEmpleados(columna);
    }

    public Object[][] consultaEmpleadosPor(String columna, String dato) {
        return sql.consultaEmpleadosPor(columna, dato);
    }

    public boolean insertarEmpleado(Empleado valorEmpleado) {
        return sql.insertarEmpleado(valorEmpleado);
    }

    public ArrayList<Grafico> consultaGrafico(int i) {
        return sql.consultaGrafico(i);
    }

    public void conectarPanelEstadistica(PanelEstadistica pnlEstadistica) {
        this.pnlEstadistica = pnlEstadistica;
    }

    public void conectarPanelBoton(int conts) {
        pnlEstadistica.metodoBotones(conts);
    }

}
