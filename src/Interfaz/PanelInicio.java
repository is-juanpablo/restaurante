package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import models.*;

/**
 *
 * @author Juan Pablo
 */
public class PanelInicio extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screenSize.getWidth();
    int height = (int) screenSize.getHeight();
    private int cantidadMesa, cantidadPedido = 0;
    private final PanelCardInicio[] pnlCards = new PanelCardInicio[4];
    private final Controlador ctrl;
    private final JPanel pnlDashboard, pnlPedidos, pnlMesas;
    private JPanel btn1;
    private JPanel btn2;
    private JPanel btn3;
    private JPanel btn4;
    int cantidadBalance = 0;
    private static boolean variableHilo;
    private final Color colorFondo = Color.white.brighter();

    public PanelInicio(Controlador ctrl) {
        this.ctrl = ctrl;
        ctrl.conectarPanelInicio(this);
        variableHilo = true;
        setBackground(colorFondo);
        setLayout(null);

        pnlDashboard = new JPanel();
        pnlDashboard.setLayout(new GridLayout(1, 4, 30, 30));
        pnlDashboard.setBackground(colorFondo);
        pnlDashboard.setBounds(20, 20, width - 50, 120);
        add(pnlDashboard);

        cantidadBalance = 4;
        for (int i = 0; i < cantidadBalance; i++) {
            PanelCardInicio pnlCard = new PanelCardInicio(0, "", 0, "", ctrl);
            pnlCards[i] = pnlCard;
            ctrl.conectarPanelCard(pnlCards[i]);
            pnlDashboard.add(pnlCard);
        }
        hiloBalance();

        pnlPedidos = new JPanel();
        pnlPedidos.setLayout(new GridLayout(1, 5, 15, 15));
        pnlPedidos.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("ULTIMOS PEDIDOS")));
        pnlPedidos.setBounds(20, 160, width - 50, 150);
        pnlPedidos.setBackground(colorFondo);
        add(pnlPedidos);
        hiloPedidos();

        pnlMesas = new JPanel();
        pnlMesas.setLayout(new GridLayout(2, 8));
        pnlMesas.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("MESAS")));
        pnlMesas.setBounds(20, 330, width - 50, 240);
        pnlMesas.setBackground(colorFondo);
        add(pnlMesas);
        hiloMesas();
    }

    public final void hiloBalance() {

        new Thread(() -> {
            for (int i = 0; i < cantidadBalance; i++) {
                Balance balance = new Balance();
                if (i == 0) {
                    balance = ctrl.consultaBalanceFecha("hoy");
                } else if (i == 1) {
                    balance = ctrl.consultaBalanceFecha("semana");
                } else if (i == 2) {
                    balance = ctrl.consultaBalanceFecha("mes");
                } else if (i == 3) {
                    balance = ctrl.consultaBalanceFecha("semestre");
                }

                pnlCards[i].setPosicion(i);
                pnlCards[i].setLblPrecio(balance.getBalance());
                pnlCards[i].setLblMensaje("Facturado: " + balance.getDia());
                pnlCards[i].setLblObjetivo(balance.getObjetivo());
                pnlCards[i].setLblDia(balance.getDia());
                pnlCards[i].calcularPorcentaje();

                pnlDashboard.revalidate();
                pnlDashboard.repaint();
            }
        }
        ).start();
    }

    private void hiloPedidos() {
        Thread hiloPedido = new Thread(() -> {
            cantidadPedido = ctrl.consultaPedidos(5).size();
            PanelPedidosInicio pnlP[] = new PanelPedidosInicio[5];

            for (int i = 0; i < 5; i++) {
                PanelPedidosInicio pnlPedido = new PanelPedidosInicio(ctrl, 0, true);
                pnlP[i] = pnlPedido;
                pnlPedidos.add(pnlPedido);
            }

            while (variableHilo) {
                ArrayList<Pedido> pedidos = ctrl.consultaPedidos(5);

                for (int i = 0; i < 5; i++) {
                    Pedido pedido = new Pedido("", 0, "", 0, 0, false, "");
                    if (i < pedidos.size()) {
                        pedido = pedidos.get(i);
                        pnlP[i].setLblEtiqueta("# " + (i + 1));
                        pnlP[i].setLblMesa(pedido.getMesa());
                        pnlP[i].setLblCantidad(pedido.getPersonas());
                        pnlP[i].setIdMesero(pedido.getIdMesero());
                        pnlP[i].setLblEstado(pedido.getEstado());
                        pnlP[i].setLblOrden(pedido.getId());
                        pnlP[i].setLblTotal(pedido.getTotal());
                        pnlP[i].setPosicion(pedido.getId());
                        if (!pedido.isHoy()) {
                            pnlP[i].cambiarColores(false);
                        } else {
                            pnlP[i].cambiarColores(true);
                        }
                    } else {
                        pnlP[i].setLblTotal(-1);
                        pnlP[i].setLblEtiqueta(" ");
                        pnlP[i].setLblMesa(-1);
                        pnlP[i].setIdMesero("");
                        pnlP[i].setLblCantidad(-1);
                        pnlP[i].setLblEstado(" ");
                        pnlP[i].setLblOrden(" ");
                        pnlP[i].setPosicion("");
                    }

                    pnlMesas.revalidate();
                    pnlMesas.repaint();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PanelInicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        hiloPedido.start();
    }

    private void hiloMesas() {
        Thread hiloPedido = new Thread(() -> {
            PanelMesasInicio pnlM[] = new PanelMesasInicio[8];
            for (int i = 0; i < 8; i++) {
                PanelMesasInicio pnlMesa = new PanelMesasInicio(0, 0, "", " ", false);
                pnlM[i] = pnlMesa;
            }
            for (int i = 0; i < 8; i++) {
                pnlMesas.add(pnlM[i]);
            }

            while (variableHilo) {

                ArrayList<Mesa> mesas = ctrl.consultaMesas();
                for (int i = 0; i < 8; i++) {
                    Mesa mesa = new Mesa(-1, -1, "", false);
                    if (i < mesas.size()) {
                        mesa = mesas.get(i);
                        pnlM[i].setImageIcon((mesa.getEstado()) ? "mesaOcupada" : "mesaVacia");
                    } else {
                        pnlM[i].setImageIcon("");
                    }
                    pnlM[i].setIdMesa(mesa.getIdMesa());
                    pnlM[i].setUso(mesa.getEstado());
                    pnlM[i].setLblTiempo(mesa.getTiempo());
                    pnlM[i].setLblPersonas(mesa.getCapacidadPersonas());

                    pnlMesas.revalidate();
                    pnlMesas.repaint();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PanelInicio.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });
        hiloPedido.start();
    }

    public static void setVariableHilo(boolean opcion) {
        variableHilo = opcion;
    }
}
