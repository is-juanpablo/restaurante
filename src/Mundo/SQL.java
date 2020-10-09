package Mundo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

/**
 *
 * @author Juan Pablo
 */
public class SQL {

    private String consultaDiaHoy;
    private String fechaHoyI, fechaSemanaI, fechaSemanaF, fechaMesI, fechaMesF, fechaSemestreI, fechaSemestreF;
    private int objetivo = 0;
    private String select;
    private final String diaMesAnio;
    private int consultaTotal = 0;

    Connection connection = null;

    public SQL() {
        diaMesAnio = tiempoInstancia();
        conectarDB();
    }

    //Conexion con la base de datos
    private Connection conectarDB() {
        try {
            String driverName = "com.mysql.cj.jdbc.Driver";
            String serverName = "localhost";
            String mydatabase = "software_restaurante";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            String username = "root";
            String password = "";
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                System.out.println("Conexion establecida");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public void valorDia() {
        Calendar calBogota = Calendar.getInstance();
        calBogota.setTimeZone(TimeZone.getTimeZone("US/Central"));
        consultaDiaHoy = calBogota.get(Calendar.YEAR) + "-" + (calBogota.get(Calendar.MONTH) + 1) + "-" + calBogota.get(Calendar.DAY_OF_MONTH);

        //fechaHoy
        fechaHoyI = calBogota.get(Calendar.YEAR) + "-" + (calBogota.get(Calendar.MONTH) + 1) + "-" + calBogota.get(Calendar.DAY_OF_MONTH);

        //fechaSemana
        int semanaInicio = (calBogota.get(Calendar.DAY_OF_MONTH) - (calBogota.get(Calendar.DAY_OF_WEEK) - 2));
        int semanaFin = semanaInicio + 6;
        fechaSemanaI = calBogota.get(Calendar.YEAR) + "-" + (calBogota.get(Calendar.MONTH) + 1) + "-" + semanaInicio;
        fechaSemanaF = calBogota.get(Calendar.YEAR) + "-" + (calBogota.get(Calendar.MONTH) + 1) + "-" + semanaFin;

        //fechaMes
        fechaMesI = calBogota.get(Calendar.YEAR) + "-" + (calBogota.get(Calendar.MONTH) + 1) + "-" + 1;
        fechaMesF = calBogota.get(Calendar.YEAR) + "-" + (calBogota.get(Calendar.MONTH) + 1) + "-" + 31;

        //fechaSemestre
        int mesInicio = ((calBogota.get(Calendar.MONTH) / 6) == 0) ? 1 : 7;
        int mesFin = ((calBogota.get(Calendar.MONTH) / 6) == 0) ? 6 : 12;
        fechaSemestreI = calBogota.get(Calendar.YEAR) + "-" + mesInicio + "-" + 1;
        fechaSemestreF = calBogota.get(Calendar.YEAR) + "-" + mesFin + "-" + 31;

        //System.out.println("El dia de hoy: " + consultaDiaHoy);
        System.out.println(calBogota.getTime().toString());
    }

    //String del tiempo instacia
    public final String tiempoInstancia() {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return anio + "-" + mes + "-" + dia;
    }

    //Retorna un string tiempo para las mesas
    private String procesoTiempo(String tiempo) {
        String datosTiempo[] = tiempo.split(":");
        int datosT[] = new int[3];

        datosT[0] = Integer.parseInt(datosTiempo[0]);
        datosT[1] = Integer.parseInt(datosTiempo[1]);
        datosT[2] = Integer.parseInt(datosTiempo[2]);

        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);
        int segundos = calendar.get(Calendar.SECOND);

        datosT[2] = (segundos < datosT[2]) ? datosT[1]++ + (60 - datosT[1] + 1) + segundos - datosT[2] : segundos - datosT[2];
        datosT[1] = (minuto < datosT[1]) ? datosT[0]++ + (60 - datosT[0] + 1) + minuto - datosT[1] : minuto - datosT[1];
        datosT[0] = hora - datosT[0];

        return ((datosT[0] == 0) ? "" : datosT[0] + ":") + datosT[1] + ":" + datosT[2];
    }

    //Inserta un nuevo producto
    public void insertarProducto(String nombre, String categoria, String url, boolean servido, String precio) {

        String query = "INSERT INTO producto (nombre, categoria, servido, imagen, precio) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, categoria);
            stmt.setBoolean(3, servido);
            stmt.setString(4, url);
            stmt.setString(5, precio);
            stmt.executeUpdate();

        } catch (SQLException e) {
        }
    }

    //Retorna un producto por cada ID
    public Producto consultaProductoID(int posicion) {
        Producto producto = new Producto();
        String query = "SELECT * FROM producto WHERE id = " + posicion;

        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                producto.setCategoria(rs.getString("categoria"));
                producto.setId(rs.getInt("id"));
                producto.setImagen(rs.getString("imagen"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setServido(rs.getBoolean("servido"));
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return producto;
    }

    //lista de pedidos con un limite 5-20
    public ArrayList<Pedido> consultaPedidos(int limite) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT * FROM pedido ORDER BY dia DESC, tiempo DESC LIMIT " + limite;

        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String id = rs.getString("id");
                int mesa = rs.getInt("mesa");
                String estado = rs.getString("estado");
                int personas = rs.getInt("personas");
                int total = rs.getInt("total");
                String hoy = rs.getString("dia");
                String idMesero = rs.getString("idMesero");

                Pedido pedido = new Pedido(id, mesa, estado, personas, total, (hoy.equals(tiempoInstancia())) ? true : false, idMesero);

                pedidos.add(pedido);
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return pedidos;
    }

    /*
    public ArrayList<Detalle> consultaDetalle(String id) {
        consultaTotal = 0;
        ArrayList<Detalle> detalles = new ArrayList<>();
        String query = "SELECT producto.nombre, producto.precio, detalle.cantidad FROM detalle INNER JOIN producto ON detalle.idProducto = producto.id WHERE idOrden = '" + id + "'";

        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Detalle detalle = new Detalle();
                int cantidad = rs.getInt("cantidad");
                detalle.setCantidad(cantidad);
                detalle.setNombre(rs.getString("nombre"));
                int precio = rs.getInt("precio");
                consultaTotal = consultaTotal + (precio * cantidad);
                detalle.setPrecio(precio);
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return detalles;
    }
     */
    //Retorna un pedido
    public Pedido buscarPedido(String valor) {
        Pedido pedido = new Pedido("null", 0, "", 0, 0, false, "");

        String query = "SELECT * FROM pedido WHERE id = '" + valor + "' ORDER BY `pedido`.`tiempo` DESC LIMIT 1";

        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                pedido.setId(rs.getString("id"));
                pedido.setMesa(rs.getInt("mesa"));
                pedido.setEstado(rs.getString("estado"));
                pedido.setPersonas(rs.getInt("personas"));
                pedido.setTotal(rs.getInt("total"));
                pedido.setIdMesero(rs.getString("idMesero"));
                pedido.setHoy((rs.getString("dia").equals(tiempoInstancia())) ? true : false);
            }
            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return pedido;
    }

    //Retorna matriz de todos los detalles de un pedido
    public Object[][] consultaDetalle(String id) {
        String query = "SELECT COUNT(*) FROM `detalle` WHERE idOrden='" + id + "'";
        int cantidadFilas = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                cantidadFilas = rs.getInt("COUNT(*)");
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        query = "SELECT producto.nombre, producto.precio, detalle.cantidad FROM detalle INNER JOIN producto ON detalle.idProducto = producto.id WHERE idOrden = '" + id + "'";
        String[][] productosDetalle = new String[cantidadFilas][3];
        consultaTotal = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            int contX = 0;
            int contY = 0;
            while (rs.next()) {
                productosDetalle[contX][contY] = rs.getString("cantidad");
                productosDetalle[contX][contY + 1] = rs.getString("nombre");
                productosDetalle[contX][contY + 2] = rs.getString("precio");
                contX++;
                int cantidad = rs.getInt("cantidad");
                int precio = rs.getInt("precio");
                consultaTotal = consultaTotal + (precio * cantidad);
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return productosDetalle;
    }

    //lista de todas las mesas
    public ArrayList<Mesa> consultaMesa() {
        ArrayList<Mesa> mesas = new ArrayList<>();

        //String query = "SELECT pedido.mesa, pedido.tiempo, mesa.espacio, mesa.estado FROM pedido INNER JOIN mesa ON pedido.mesa = mesa.numeroMesa LIMIT 8";
        String query = "SELECT pedido.mesa, pedido.tiempo, mesa.espacio, mesa.estado FROM pedido INNER JOIN mesa ON pedido.mesa = mesa.numeroMesa AND pedido.dia = '" + diaMesAnio + "' AND id in(SELECT max(id) FROM pedido group by mesa) WHERE mesa.estado = 1 ORDER BY `pedido`.`id` ASC LIMIT 8";

        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                int numeroMesa = rs.getInt("mesa");
                int capacidad = rs.getInt("espacio");
                String tiempo = rs.getString("tiempo");
                tiempo = procesoTiempo(tiempo);
                boolean estado = rs.getBoolean("estado");

                Mesa mesa = new Mesa(numeroMesa, capacidad, tiempo, estado);

                mesas.add(mesa);
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return mesas;
    }

    //Retorna lista de produtos
    public ArrayList<Producto> consultaProducto() {

        ArrayList<Producto> productos = new ArrayList<>();

        //String query = "SELECT * from producto ORDER BY `producto`.`servido` DESC";
        String query = select;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                Producto producto = new Producto();

                producto.setId(rs.getInt("id"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setImagen(rs.getString("imagen"));
                producto.setNombre(rs.getString("nombre"));
                producto.setServido(rs.getBoolean("servido"));
                producto.setPrecio(rs.getInt("precio"));

                productos.add(producto);
            }

            st.close();
        } catch (SQLException e) {
        }
        return productos;
    }

    //Retorna un balance de una fecha en especifico
    public Balance consultaBalanceFecha(String fecha) {
        Balance balance = new Balance();
        String fechaInicio = "";
        String fechaFin = "";
        String dia = "";
        int objetivo = 0;
        switch (fecha) {
            case "hoy":
                fechaInicio = fechaHoyI;
                fechaFin = fechaHoyI;
                dia = consultaDiaHoy;
                objetivo = consultaBalance(1);
                break;
            case "semana":
                fechaInicio = fechaSemanaI;
                fechaFin = fechaSemanaF;
                dia = "Ultima Semana";
                objetivo = consultaBalance(2);
                break;
            case "mes":
                fechaInicio = fechaMesI;
                fechaFin = fechaMesF;
                dia = "Ultimo Mes";
                objetivo = consultaBalance(3);
                break;
            case "semestre":
                fechaInicio = fechaSemestreI;
                fechaFin = fechaSemestreF;
                dia = "Este semestre";
                objetivo = consultaBalance(4);
                break;
            default:
                break;
        }
        //= '" + consultaDiaHoy + "'";
        String query = "SELECT total FROM pedido WHERE dia BETWEEN str_to_date('" + fechaInicio + "', '%Y-%m-%d') AND '" + fechaFin + "'";
        long valorTotal = 0;

        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int total = rs.getInt("total");
                valorTotal += total;
            }

            balance = new Balance(valorTotal, objetivo, dia);

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return balance;
    }

    //Consulta el objetivo de cada balance
    public int consultaBalance(int posicion) {
        String query = "SELECT objetivo FROM objetivobalance WHERE id=" + posicion;
        int balance = 0;

        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                balance = rs.getInt("objetivo");
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return balance;
    }

    //Modifica cualquier campo de un producto
    public void modificarProducto(String categoria, String valor, int posicion) {
        String query = "UPDATE `producto` SET " + categoria + " = ? WHERE `producto`.`id` = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, valor);
            stmt.setInt(2, posicion);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //Modifica los valores del objetivo en balance
    public void modificarValorObjetivo(int valor, int posicion) {
        String query = "UPDATE `objetivobalance` SET objetivo = ? WHERE `objetivobalance`.`id` = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, valor);
            stmt.setInt(2, posicion);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //Modifica el estado de un producto (SERVIDO)
    public void modificarValorCheck(boolean estadoCheck, int posicion) {
        String query = "UPDATE producto SET servido = ? WHERE producto.id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setBoolean(1, !estadoCheck);
            stmt.setInt(2, posicion);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //Ejecuta la eliminación de un producto
    public void eliminarProducto(int posicion) {

        String query = "DELETE FROM producto WHERE id = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, posicion);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    //Retorna una matriz de los ingredientes de un producto
    public Object[][] consultaIngredientes(int id) {

        String query = "SELECT ingrediente FROM producto WHERE id=" + id;
        String[][] ingredientes = null;
        String valor2[] = null;
        try {
            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                String valor = rs.getString("ingrediente");
                valor2 = valor.split("\\,");
            }

            ingredientes = new String[valor2.length][2];

            for (int i = 0; i < valor2.length; i++) {
                for (int j = 0; j < 2; j++) {
                    if (j == 0) {
                        ingredientes[i][j] = String.valueOf(i + 1);
                    } else {
                        ingredientes[i][j] = valor2[i];
                    }
                }
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return ingredientes;
    }

    //Modificar el estado del pedido
    public void modificarPedido(int numeroMesa, String idOrden, String precioTotal, String idMesero) {
        String query = "UPDATE pedido SET estado = ?, total = ?, tiempoEspera = '00:00:00'  WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "Completado");
            stmt.setString(2, precioTotal);
            stmt.setString(3, idOrden);
            stmt.executeUpdate();

        } catch (SQLException e) {
        }

        query = "UPDATE mesa SET estado = ? WHERE numeroMesa = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "0");
            stmt.setString(2, String.valueOf(numeroMesa));
            stmt.executeUpdate();

        } catch (SQLException e) {
        }
        modificarPedidoMesero(idMesero);
    }

    //Retorna la validación de acceso 
    public boolean iniciarSesion(String usuario, String contrasenia) {
        String query = "SELECT * FROM clientes WHERE usuario = ? AND contrasenia = ? LIMIT 1";
        try {
            try (PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setString(1, usuario);
                stmt.setString(2, contrasenia);

                //stmt.executeQuery();
                /*Statement st = connection.createStatement();*/
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    //Retorna una matriz de empleados ordenados por una columna
    public Object[][] consultaEmpleados(String columna) {
        String query = "SELECT COUNT(*) FROM empleado";
        int cantidadFilas = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                cantidadFilas = rs.getInt("COUNT(*)");
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        query = "SELECT * FROM empleado ORDER BY " + columna;
        String[][] empleados = new String[cantidadFilas][5];
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                empleados[i][0] = rs.getString("id");
                empleados[i][1] = rs.getString("nombre");
                empleados[i][2] = rs.getString("apellido");
                empleados[i][3] = rs.getString("cedula");
                if (rs.getString("estado").equals("0")) {
                    empleados[i][4] = "";
                } else {
                    empleados[i][4] = rs.getString("pedidos");
                }
                i++;

            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return empleados;
    }

    //retorna una matriz de todos los empleados dependiendo de una columna
    //*FALTA EDITAR CHECKBOX*//
    public Object[][] consultaEmpleadosPor(String columna, String dato) {
        String query = "SELECT COUNT(*) FROM empleado where " + columna + "= '" + dato + "'";
        int cantidadFilas = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                cantidadFilas = rs.getInt("COUNT(*)");
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        query = "SELECT * FROM empleado where " + columna + "= '" + dato + "'";
        String[][] empleados = new String[cantidadFilas][5];
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                empleados[i][0] = rs.getString("id");
                empleados[i][1] = rs.getString("nombre");
                empleados[i][2] = rs.getString("apellido");
                empleados[i][3] = rs.getString("cedula");
                if (rs.getString("estado").equals("0")) {
                    empleados[i][4] = "";
                } else {
                    empleados[i][4] = rs.getString("pedidos");
                }
                i++;

            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return empleados;

    }

    //Crea un nuevo empleado mesero
    public boolean insertarEmpleado(Empleado empleado) {

        String query = "INSERT INTO empleado (id, nombre, apellido, cedula, contrasenia, estado, pedidos) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, empleado.getId());
            stmt.setString(2, empleado.getNombre());
            stmt.setString(3, empleado.getApellido());
            stmt.setString(4, empleado.getCedula());
            stmt.setString(5, empleado.getContrasenia());
            stmt.setString(6, "1");
            stmt.setString(7, "0");
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    //Modificar numero de pedidos por mesero
    private void modificarPedidoMesero(String idMesero) {
        String query = "SELECT pedidos from empleado WHERE id = '" + idMesero + "' LIMIT 1";
        int valorPedido = 0;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                valorPedido = rs.getInt("pedidos");
            }

            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        query = "UPDATE empleado SET pedidos = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, String.valueOf(++valorPedido));
            stmt.setString(2, idMesero);
            stmt.executeUpdate();

        } catch (SQLException e) {

        }
    }

    public ArrayList<Grafico> consultaGrafico(int i) {

        ArrayList<Grafico> graficos = new ArrayList<>();
        switch (i) {
            case 0: //Grafico para hoy
                graficos.add(graficoMesero(i));
                graficos.add(graficoPedido(i));
                break;
            case 1: //Grafico para semana
                graficos.add(graficoMesero(i));
                graficos.add(graficoPedido(i));
                break;
            case 2: //Grafico para mes
                graficos.add(graficoMesero(i));
                graficos.add(graficoPedido(i));
                graficos.add(graficoClientes(i));
                graficos.add(graficoBalance(i));
                break;
            case 3: //Grafico para semestre
                graficos.add(graficoMesero(i));
                graficos.add(graficoPedido(i));
                graficos.add(graficoClientes(i));
                graficos.add(graficoBalance(i));
                break;
            default:

                return null;
        }
        return graficos;
    }

    public int consultaTotal() {
        return consultaTotal;
    }

    //Set del valor select para los botones de los productos
    public void cambioSelectProducto(String select) {
        this.select = select;
    }

    private Grafico graficoMesero(int fecha) {
        String fechaInicio = "";
        String fechaFin = "";
        switch (fecha) {
            case 0:
                fechaInicio = fechaHoyI;
                fechaFin = fechaHoyI;
                break;
            case 1:
                fechaInicio = fechaSemanaI;
                fechaFin = fechaSemanaF;
                break;
            case 2:
                fechaInicio = fechaMesI;
                fechaFin = fechaMesF;
                break;
            case 3:
                fechaInicio = fechaSemestreI;
                fechaFin = fechaSemestreF;
                break;
            default:
                break;
        }
        Grafico graficoMesero = new Grafico();
        String query = "SELECT idMesero, COUNT(*) FROM pedido WHERE dia BETWEEN str_to_date('" + fechaInicio + "', '%Y-%m-%d') AND '" + fechaFin + "' GROUP BY idMesero";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            LinkedHashMap<String, Integer> mapaMesero = new LinkedHashMap<>();
            while (rs.next()) {
                mapaMesero.put(rs.getString("idMesero"), rs.getInt("COUNT(*)"));
            }

            graficoMesero = new Grafico("Grafico Mesero", mapaMesero, "Mesero", "Pedidos");
            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return graficoMesero;
    }

    private Grafico graficoPedido(int i) {
        Grafico grafico = new Grafico();
        String nombre = "Pedidos";
        int cantidadFor = 0;
        int hora1 = 10;
        int hora2 = 18;
        String fechaInicio = "";
        String arreglo[] = null;
        String fechaI[] = null;
        String fechaF[] = null;

        int contadorFechaSemana = 0;
        switch (i) {
            case 0:
                cantidadFor = 4;
                hora2 = 12;
                nombre += " de Hoy";
                fechaInicio = fechaHoyI;
                String arregloH[] = {"10 - 12", "12 - 2", "2 - 4", "4 - 6"};
                arreglo = arregloH;
                String fechaIH[] = {fechaInicio};
                fechaI = fechaIH;
                String fechaFH[] = {fechaInicio};
                fechaF = fechaFH;
                break;
            case 1:
                cantidadFor = 7;
                nombre += " de la Semana";
                fechaInicio = fechaSemanaI;
                String arregloS[] = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
                arreglo = arregloS;
                String fechaSemana[] = fechaInicio.split("-");
                fechaInicio = fechaSemana[0] + "-" + fechaSemana[1] + "-";
                contadorFechaSemana = Integer.parseInt(fechaSemana[2]);
                String fechaIS[] = {fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++};
                fechaI = fechaIS;
                contadorFechaSemana = Integer.parseInt(fechaSemana[2]);
                String fechaFS[] = {fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++, fechaInicio + contadorFechaSemana++};
                fechaF = fechaFS;

                break;
            case 2:
                cantidadFor = 5;
                nombre += " del Mes";
                fechaInicio = fechaMesI;
                String arregloM[] = {"Semana 1", "Semana 2", "Semana 3", "Semana 4", "Semana 5"};
                arreglo = arregloM;
                String fechaMes[] = fechaInicio.split("-");
                fechaInicio = fechaMes[0] + "-" + fechaMes[1] + "-";
                String fechaIM[] = {fechaInicio + 1, fechaInicio + 8, fechaInicio + 15, fechaInicio + 22, fechaInicio + 29};
                fechaI = fechaIM;
                String fechaFM[] = {fechaInicio + 7, fechaInicio + 14, fechaInicio + 21, fechaInicio + 28, fechaInicio + 31};
                fechaF = fechaFM;
                break;
            case 3:
                cantidadFor = 6;
                nombre += " del Semestre";
                fechaInicio = fechaSemestreI;
                String arregloSe[] = {"Mes 1", "Mes 2", "Mes 3", "Mes 4", "Mes 5", "Mes 6"};
                arreglo = arregloSe;
                String fechaSemestre[] = fechaInicio.split("-");
                fechaInicio = fechaSemestre[0] + "-";
                String fechaAuxI = "-" + 1;
                int contador = (Integer.parseInt(fechaSemestre[1]) / 7 == 0) ? 1 : 7;
                String fechaISe[] = {fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI};
                fechaI = fechaISe;
                String fechaAuxF = "-" + 31;
                contador = (Integer.parseInt(fechaSemestre[1]) / 7 == 0) ? 1 : 7;
                String fechaFSe[] = {fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF};
                fechaF = fechaFSe;
                break;
            default:
                return null;
        }
        grafico.setNombre(nombre);
        grafico.setValorX("Fecha");
        grafico.setValorY("Pedidos");
        LinkedHashMap<String, Integer> mapaPedido = new LinkedHashMap<>();

        for (int j = 0; j < cantidadFor; j++) {
            String query = "SELECT COUNT(*) FROM pedido WHERE tiempo BETWEEN '" + hora1 + ":00:00' and '" + hora2 + ":00:00' and dia BETWEEN str_to_date('" + ((j >= fechaI.length) ? fechaI[0] : fechaI[j]) + "', '%Y-%m-%d') AND '" + ((j >= fechaF.length) ? fechaF[0] : fechaF[j]) + "'";
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    mapaPedido.put(arreglo[j], rs.getInt("COUNT(*)"));
                }
                st.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            if (i == 0) {
                hora1 += 2;
                hora2 += 2;
            }
        }
        grafico.setMapa(mapaPedido);
        return grafico;
    }

    private Grafico graficoClientes(int i) {
        Grafico grafico = new Grafico();
        String nombre = "", tipo = "";
        int cantidadFor = 0;
        String fechaInicio = "";
        String arreglo[] = null;
        String fechaI[] = null;
        String fechaF[] = null;

        if (i == 2) {
            nombre = "Mes";
            tipo = "Semana";
            cantidadFor = 5;
            fechaInicio = fechaMesI;
            String arregloM[] = {"Semana 1", "Semana 2", "Semana 3", "Semana 4", "Semana 5"};
            arreglo = arregloM;
            String fechaMes[] = fechaInicio.split("-");
            fechaInicio = fechaMes[0] + "-" + fechaMes[1] + "-";
            String fechaIM[] = {fechaInicio + 1, fechaInicio + 8, fechaInicio + 15, fechaInicio + 22, fechaInicio + 29};
            fechaI = fechaIM;
            String fechaFM[] = {fechaInicio + 7, fechaInicio + 14, fechaInicio + 21, fechaInicio + 28, fechaInicio + 31};
            fechaF = fechaFM;
        } else if (i == 3) {
            nombre = "Semestre";
            tipo = "Mes";
            cantidadFor = 6;
            fechaInicio = fechaSemestreI;
            String arregloSe[] = {"Mes 1", "Mes 2", "Mes 3", "Mes 4", "Mes 5", "Mes 6"};
            arreglo = arregloSe;
            String fechaSemestre[] = fechaInicio.split("-");
            fechaInicio = fechaSemestre[0] + "-";
            String fechaAuxI = "-" + 1;
            int contador = (Integer.parseInt(fechaSemestre[1]) / 7 == 0) ? 1 : 7;
            String fechaISe[] = {fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI};
            fechaI = fechaISe;
            String fechaAuxF = "-" + 31;
            contador = (Integer.parseInt(fechaSemestre[1]) / 7 == 0) ? 1 : 7;
            String fechaFSe[] = {fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF};
            fechaF = fechaFSe;
        }

        grafico.setNombre("Clientes " + nombre);
        grafico.setValorX("Por " + tipo);
        grafico.setValorY("Cantidad Clientes");
        LinkedHashMap<String, Integer> mapaPedido = new LinkedHashMap<>();

        for (int j = 0; j < cantidadFor; j++) {
            String query = "SELECT personas FROM pedido WHERE tiempo BETWEEN '10:00:00' and '18:00:00' and dia BETWEEN str_to_date('" + ((j >= fechaI.length) ? fechaI[0] : fechaI[j]) + "', '%Y-%m-%d') AND '" + ((j >= fechaF.length) ? fechaF[0] : fechaF[j]) + "'";
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                int cantidadPersonas = 0;

                while (rs.next()) {
                    cantidadPersonas += rs.getInt("personas");
                }
                mapaPedido.put(arreglo[j], cantidadPersonas);
                st.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        grafico.setMapa(mapaPedido);

        return grafico;
    }

    private Grafico graficoBalance(int i) {
        Grafico grafico = new Grafico();

        String nombre = "";
        int cantidadFor = 0;
        String fechaInicio = "";
        String arreglo[] = null;
        String fechaI[] = null;
        String fechaF[] = null;
        String tipo = "";

        if (i == 2) {
            nombre = "Mes";
            tipo = "Semana";
            cantidadFor = 5;
            fechaInicio = fechaMesI;
            String arregloM[] = {"Semana 1", "Semana 2", "Semana 3", "Semana 4", "Semana 5"};
            arreglo = arregloM;
            String fechaMes[] = fechaInicio.split("-");
            fechaInicio = fechaMes[0] + "-" + fechaMes[1] + "-";
            String fechaIM[] = {fechaInicio + 1, fechaInicio + 8, fechaInicio + 15, fechaInicio + 22, fechaInicio + 29};
            fechaI = fechaIM;
            String fechaFM[] = {fechaInicio + 7, fechaInicio + 14, fechaInicio + 21, fechaInicio + 28, fechaInicio + 31};
            fechaF = fechaFM;
        } else if (i == 3) {
            nombre = "Semestre";
            tipo = "Mes";
            cantidadFor = 6;
            fechaInicio = fechaSemestreI;
            String arregloSe[] = {"Mes 1", "Mes 2", "Mes 3", "Mes 4", "Mes 5", "Mes 6"};
            arreglo = arregloSe;
            String fechaSemestre[] = fechaInicio.split("-");
            fechaInicio = fechaSemestre[0] + "-";
            String fechaAuxI = "-" + 1;
            int contador = (Integer.parseInt(fechaSemestre[1]) / 7 == 0) ? 1 : 7;
            String fechaISe[] = {fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI, fechaInicio + contador++ + fechaAuxI};
            fechaI = fechaISe;
            String fechaAuxF = "-" + 31;
            contador = (Integer.parseInt(fechaSemestre[1]) / 7 == 0) ? 1 : 7;
            String fechaFSe[] = {fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF, fechaInicio + contador++ + fechaAuxF};
            fechaF = fechaFSe;
        }

        grafico.setNombre("Balance " + nombre);
        grafico.setValorX("Por " + tipo);
        grafico.setValorY("Ingresos");
        LinkedHashMap<String, Integer> mapaPedido = new LinkedHashMap<>();

        for (int j = 0; j < cantidadFor; j++) {
            String query = "SELECT total FROM pedido WHERE tiempo BETWEEN '10:00:00' and '18:00:00' and dia BETWEEN str_to_date('" + ((j >= fechaI.length) ? fechaI[0] : fechaI[j]) + "', '%Y-%m-%d') AND '" + ((j >= fechaF.length) ? fechaF[0] : fechaF[j]) + "'";
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                int cantidadPersonas = 0;

                while (rs.next()) {
                    cantidadPersonas += rs.getInt("total");
                }
                mapaPedido.put(arreglo[j], cantidadPersonas);
                st.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        grafico.setMapa(mapaPedido);

        return grafico;
    }
}
