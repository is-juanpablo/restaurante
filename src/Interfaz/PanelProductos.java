package Interfaz;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import models.Producto;

/**
 *
 * @author Juan Pablo
 */
public class PanelProductos extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screenSize.getWidth();
    int height = (int) screenSize.getHeight();
    private final int cantidadDeProductos = 30;
    private final JPanel pnlProducto, pnlAgregar, pnlConsultar;
    private final Controlador ctrl;
    private final PanelProductoIndividual[] pnlProducts = new PanelProductoIndividual[cantidadDeProductos];

    public PanelProductos(Controlador ctrl) {
        this.ctrl = ctrl;
        setLayout(null);
        setBackground(Color.white);

        pnlProducto = new JPanel();
        pnlProducto.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 80, 10), new TitledBorder("Productos")));
        pnlProducto.setBackground(Color.white);
        pnlProducto.setLayout(null);
        pnlProducto.setPreferredSize(new Dimension(width - 515, cantidadDeProductos / 5 * 150 + 150));
        pnlProducto.setAutoscrolls(true);

        JScrollPane scrollPane = new JScrollPane(pnlProducto);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, width - 505, height);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBounds(0, 0, width, height);
        contentPane.add(scrollPane);

        ctrl.cambioSelectProducto("SELECT * from producto ORDER BY `producto`.`servido` DESC");

        for (int i = 0; i < cantidadDeProductos; i++) {
            PanelProductoIndividual productoIndividual = new PanelProductoIndividual(i, "", "", " ", false, ctrl, 0);
            pnlProducts[i] = productoIndividual;
            pnlProducto.add(productoIndividual);
        }
        hiloProductos();

        pnlConsultar = new JPanel();
        pnlConsultar.setLayout(new GridLayout(1, 1));
        pnlConsultar.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new TitledBorder("Consultar Productos")));
        pnlConsultar.setBackground(Color.white);
        pnlConsultar.setBounds(width - 505, 0, 505, 300);
        contentPane.add(pnlConsultar);

        JPanel pnlConsulta = new PanelConsultaProductos(ctrl, this);
        pnlConsultar.add(pnlConsulta);

        pnlAgregar = new PanelAgregarProducto(ctrl, this);
        contentPane.add(pnlAgregar);

        add(contentPane);

        ctrl.conectarPanelProducto(PanelProductos.this);

    }

    public final void hiloProductos() {
        new Thread(() -> {
            ArrayList<Producto> productos = ctrl.consultaProductos();

            for (int i = 0; i < cantidadDeProductos; i++) {
                Producto producto = new Producto(0, "", false, " ", "", 0);
                if (i < productos.size()) {
                    producto = productos.get(i);
                }
                pnlProducts[i].setLblNombre(producto.getNombre());
                pnlProducts[i].setImageIcon(producto.getImagen());
                pnlProducts[i].setEtiqueta(producto.getCategoria());
                pnlProducts[i].setAction(producto.isServido(), producto.getNombre());
                pnlProducts[i].setCheckIcon(producto.isServido(), producto.getNombre());
                pnlProducts[i].setPosicion(producto.getId());
                pnlProducts[i].setLblPrecio(producto.getPrecio());

                pnlProducto.revalidate();
                pnlProducto.repaint();
            }
        }).start();
    }
}

class PanelConsultaProductos extends JPanel {

    private Controlador ctrl;
    private Color colorBotones = Color.white;

    public PanelConsultaProductos(Controlador ctrl, PanelProductos pnlProductos) {
        this.ctrl = ctrl;
        setLayout(new GridLayout(3, 3, 20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.white);

        JButton btn0 = new JButton("TODOS");
        btn0.setBackground(colorBotones);
        btn0.addActionListener((ActionEvent ae) -> {
            ctrl.cambioSelectProducto("SELECT * from producto ORDER BY `producto`.`servido` DESC");
            pnlProductos.hiloProductos();
        });
        add(btn0);

        JButton btn1 = new JButton("SERVIDO");
        btn1.setBackground(colorBotones);
        btn1.addActionListener((ActionEvent ae) -> {
            ctrl.cambioSelectProducto("SELECT * from producto WHERE servido = '1'");
            pnlProductos.hiloProductos();
        });
        add(btn1);

        JButton btn2 = new JButton("NO SERVIDO");
        btn2.setBackground(colorBotones);
        btn2.addActionListener((ActionEvent ae) -> {
            ctrl.cambioSelectProducto("SELECT * from producto WHERE servido = '0'");
            pnlProductos.hiloProductos();
        });
        add(btn2);

        JButton btn3 = new JButton("BEBIDAS");
        btn3.setBackground(colorBotones);
        btn3.addActionListener((ActionEvent ae) -> {
            ctrl.cambioSelectProducto("SELECT * FROM `producto` WHERE categoria = 'bebida'");
            pnlProductos.hiloProductos();
        });
        add(btn3);

        JButton btn4 = new JButton("HAMBURGUESAS");
        btn4.setBackground(colorBotones);
        btn4.addActionListener((ActionEvent ae) -> {
            ctrl.cambioSelectProducto("SELECT * FROM `producto` WHERE categoria = 'hamburguesa'");
            pnlProductos.hiloProductos();
        });
        add(btn4);

        JButton btn5 = new JButton("HOT DOG");
        btn5.setBackground(colorBotones);
        btn5.addActionListener((ActionEvent ae) -> {
            ctrl.cambioSelectProducto("SELECT * FROM `producto` WHERE categoria = 'hot dog'");
            pnlProductos.hiloProductos();
        });
        add(btn5);

        JButton btn6 = new JButton("SOPA");
        btn6.setBackground(colorBotones);
        btn6.addActionListener((ActionEvent ae) -> {
            ctrl.cambioSelectProducto("SELECT * FROM `producto` WHERE categoria = 'sopa'");
            pnlProductos.hiloProductos();
        });
        add(btn6);

        JButton btn7 = new JButton("PIZZA");
        btn7.setBackground(colorBotones);
        btn7.addActionListener((ActionEvent ae) -> {
            ctrl.cambioSelectProducto("SELECT * FROM `producto` WHERE categoria = 'pizza'");
            pnlProductos.hiloProductos();
        });
        add(btn7);

        JButton btn8 = new JButton("");
        btn8.setBackground(colorBotones);
        btn8.addActionListener((ActionEvent ae) -> {
            System.out.println("Buscando por " + btn8.getText());
        });
        add(btn8);

    }
}
