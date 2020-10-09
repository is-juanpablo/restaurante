package Interfaz;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import models.Pedido;
import recursos.Variables;

/**
 *
 * @author Juan Pablo
 */
public class PanelPedidos extends JPanel {

    private final JPanel pnlPedido, pnlOrden, pnlBuscar, pnlOrdenIndividual;
    private final JPanel pnlOIMesa, pnlOIORden, pnlOIPrecio, pnlOIDetalle;
    private final JButton btnBuscar, btnPagado;
    private JTextField jTxtBuscar;
    private final JLabel lblMesa, lblPedidoValor, lblPrecioValor;
    private final JTable tableDetalle;
    PanelPedidosInicio[] pnlPedidos = new PanelPedidosInicio[20];
    int pnlOrdenInvidi = Variables.heigth - 250;
    private final Controlador ctrl;
    private DefaultTableModel dtm;
    private String[] columnas = {"Cantidad", "Producto", "Precio"};
    private String idOrden;
    private String estado;
    private String precioTotal;
    private String idMesero;
    private int numeroMesa;

    public PanelPedidos(Controlador ctrl) {
        this.ctrl = ctrl;
        setBackground(Color.white);
        setLayout(null);

        pnlOrden = new JPanel();
        pnlOrden.setLayout(new GridLayout(4, 5));
        pnlOrden.setBackground(Color.white);
        pnlOrden.setBounds(390, 0, Variables.width - 390, Variables.heigth - 80);
        pnlOrden.setBorder(new CompoundBorder((new EmptyBorder(10, 10, 10, 10)), new TitledBorder("Ordenes")));
        add(pnlOrden);

        //
        for (int i = 0; i < 20; i++) {
            PanelPedidosInicio pnlPedido = new PanelPedidosInicio(ctrl, 0, false);
            pnlPedidos[i] = pnlPedido;

            pnlPedido.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    idOrden = pnlPedido.getNumeroOrden();
                    Pedido pedido = ctrl.buscarPedido(idOrden);
                    dtm.setDataVector(ctrl.consultaDetalle(idOrden), columnas);
                    int total = ctrl.consultaTotal();
                    precioTotal = String.valueOf(total);
                    visualizarOrden(pnlPedido.getNumeroMesa(), idOrden, total, pedido.getEstado(), pedido.getIdMesero());
                }
            });
            pnlOrden.add(pnlPedido);
        }
        hiloOrdenes();

        pnlPedido = new JPanel();
        pnlPedido.setLayout(null);
        pnlPedido.setBounds(0, 0, 400, Variables.heigth - 80);
        pnlPedido.setBackground(Color.white);
        pnlPedido.setBorder(new CompoundBorder((new EmptyBorder(10, 10, 10, 10)), new TitledBorder("Pedido")));
        add(pnlPedido);

        //Panel para buscar una orden
        pnlBuscar = new JPanel();
        pnlBuscar.setLayout(new BorderLayout());
        pnlBuscar.setBackground(Color.white);
        pnlBuscar.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Buscar")));
        pnlBuscar.setBounds(20, 30, 360, 120);
        pnlPedido.add(pnlBuscar);

        JPanel valoresBuscar = new JPanel(new GridBagLayout());
        valoresBuscar.setPreferredSize(new Dimension(360, 120));
        valoresBuscar.setBackground(Color.white);
        pnlBuscar.add(valoresBuscar);

        jTxtBuscar = new JTextField();
        jTxtBuscar.setPreferredSize(new Dimension(140, 30));
        jTxtBuscar.setBackground(Color.white);
        valoresBuscar.add(jTxtBuscar);

        btnBuscar = new JButton("Buscar Orden");
        btnBuscar.setBackground(Variables.rojo);
        btnBuscar.setPreferredSize(new Dimension(140, 30));
        btnBuscar.setForeground(Color.white);
        valoresBuscar.add(btnBuscar);
        btnBuscar.addActionListener((ActionEvent ae) -> {
            String idOrdenBuscar = jTxtBuscar.getText().trim();
            Pedido pedido = ctrl.buscarPedido(idOrdenBuscar);
            jTxtBuscar.setText("");
            if (!pedido.getId().equals("null")) {
                dtm.setDataVector(ctrl.consultaDetalle(idOrdenBuscar), columnas);
                int total = ctrl.consultaTotal();
                precioTotal = String.valueOf(total);
                visualizarOrden(pedido.getMesa(), idOrdenBuscar.toUpperCase(), total, pedido.getEstado(), pedido.getIdMesero());
            } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado la orden '" + idOrdenBuscar + "'.");
            }
        });
        jTxtBuscar.addActionListener((ActionEvent e) -> {
            String idOrdenBuscar = jTxtBuscar.getText().trim();
            jTxtBuscar.setText("");
            Pedido pedido = ctrl.buscarPedido(idOrdenBuscar);
            if (!pedido.getId().equals("null")) {
                idOrden = idOrdenBuscar;
                dtm.setDataVector(ctrl.consultaDetalle(idOrdenBuscar), columnas);
                int total = ctrl.consultaTotal();
                precioTotal = String.valueOf(total);
                visualizarOrden(pedido.getMesa(), idOrdenBuscar.toUpperCase(), total, pedido.getEstado(), pedido.getIdMesero());
            } else {
                JOptionPane.showMessageDialog(null, "No se ha encontrado la orden '" + idOrdenBuscar + "'.");
            }
        });

        //Panel donde se podrá visualizar la orden seleccionada
        pnlOrdenIndividual = new JPanel();
        pnlOrdenIndividual.setLayout(null);
        pnlOrdenIndividual.setBackground(Color.white);
        pnlOrdenIndividual.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Orden")));
        pnlOrdenIndividual.setBounds(20, 150, 360, Variables.heigth - 250);
        pnlPedido.add(pnlOrdenIndividual);

        pnlOIMesa = new JPanel();
        lblMesa = new JLabel("", JLabel.CENTER);
        pnlOrdenIndividual.add(pnlOIMesa);
        pnlOIMesa.add(lblMesa);

        pnlOIORden = new JPanel(new GridBagLayout());
        lblPrecioValor = new JLabel("", JLabel.CENTER);
        pnlOrdenIndividual.add(pnlOIORden);
        pnlOIORden.add(lblPrecioValor);

        pnlOIPrecio = new JPanel(new GridBagLayout());
        lblPedidoValor = new JLabel("", JLabel.CENTER);
        pnlOrdenIndividual.add(pnlOIPrecio);
        pnlOIPrecio.add(lblPedidoValor);

        pnlOIDetalle = new JPanel();
        pnlOIDetalle.setLayout(new GridLayout());
        pnlOrdenIndividual.add(pnlOIDetalle);

        dtm = new DefaultTableModel(null, columnas);
        tableDetalle = new JTable(dtm);
        pnlOIDetalle.add(tableDetalle);
        JScrollPane scrollPane = new JScrollPane(tableDetalle);
        pnlOIDetalle.add(scrollPane, BorderLayout.CENTER);

        btnPagado = new JButton("PAGADO");
        btnPagado.addActionListener((ActionEvent ae) -> {
            if (estado.equals("Comiendo")) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Desea poner este pedido como pago?");
                if (opcion == 0) {
                    ctrl.modificarPedido(numeroMesa, idOrden, precioTotal, idMesero);
                    hiloOrdenes();
                    btnPagado.setEnabled(false);
                    btnPagado.setBackground(Variables.white);
                    btnPagado.setText("COMPLETADO");
                }
            }
        });
        pnlOrdenIndividual.add(btnPagado);
    }

    public void visualizarOrden(int numeroMesa, String numeroOrden, int numeroPrecio, String estado, String idMesero) {
        this.estado = estado;
        this.idMesero = idMesero;
        pnlOIMesa.setLayout(new GridBagLayout());
        pnlOIMesa.setBounds(10, 15, 340, 30);
        pnlOIMesa.setBackground(Color.white);
        lblMesa.setText("Mesa # " + numeroMesa);
        this.numeroMesa = numeroMesa;

        pnlOIORden.setBackground(Color.white);
        pnlOIORden.setBounds(4, 55, 352, 50);
        pnlOIORden.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Numero Orden")));
        lblPrecioValor.setText(numeroOrden);

        pnlOIPrecio.setBackground(Color.white);
        pnlOIPrecio.setBounds(4, 105, 352, 50);
        pnlOIPrecio.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Precio")));
        lblPedidoValor.setText("$ " + numeroPrecio);

        pnlOIDetalle.setBackground(Color.white);
        pnlOIDetalle.setBounds(4, 155, 352, pnlOrdenInvidi - 200);
        pnlOIDetalle.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Detalle")));

        btnPagado.setBounds(4, 155 + pnlOrdenInvidi - 200, 352, 40);
        btnPagado.setBackground(Variables.rojo);
        btnPagado.setForeground(Color.white);

        if (estado.equals("Completado")) {
            btnPagado.setEnabled(false);
            btnPagado.setBackground(Variables.white);
            btnPagado.setText("COMPLETADO");
        } else {
            btnPagado.setEnabled(true);
            btnPagado.setBackground(Variables.rojo);
            btnPagado.setText("PAGADO");
        }
    }

    public final void hiloOrdenes() {
        new Thread(() -> {
            ArrayList<Pedido> pedidos = ctrl.consultaPedidos(20);

            for (int i = 0; i < 20; i++) {
                Pedido pedido = new Pedido("", -1, "", -1, -1, false, "");
                boolean opcion = false;
                if (i < pedidos.size()) {
                    pedido = pedidos.get(i);
                    pnlPedidos[i].setLblEtiqueta("# " + (i + 1));
                    if (!pedido.isHoy()) {
                        pnlPedidos[i].cambiarColores(false);
                    } else {
                        pnlPedidos[i].cambiarColores(true);
                    }
                    opcion = true;
                } else {
                    pnlPedidos[i].setLblEtiqueta(" ");
                }
                pnlPedidos[i].setLblTotal(pedido.getTotal());
                pnlPedidos[i].setLblMesa(pedido.getMesa());
                pnlPedidos[i].setLblCantidad(pedido.getPersonas());
                pnlPedidos[i].setLblEstado(pedido.getEstado());
                pnlPedidos[i].setLblOrden(pedido.getId());
                pnlPedidos[i].setPosicion(pedido.getId());
                pnlPedidos[i].setVisible(opcion);
                pnlPedidos[i].setIdMesero(pedido.getIdMesero());

                pnlOrden.revalidate();
                pnlOrden.repaint();
            }

        }).start();
    }
}
