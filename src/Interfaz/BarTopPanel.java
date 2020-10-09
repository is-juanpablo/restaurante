package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import recursos.Variables;

/**
 *
 * @author Juan Pablo
 */
public class BarTopPanel extends JPanel {

    private final Controlador ctrl;
    private final JButton btnInicio;
    private final JButton btnPedidos;
    private final JButton btnProductos;
    private final JButton btnMesas;
    private final JButton btnEmpleados;
    private final JButton btnEstadistica;

    private final Color colorSombra = Variables.sombra;
    private final Color colorSeleccionado = Variables.rojo;
    private final int cantidad = 6;
    private final JButton[] btnTopBar = new JButton[cantidad];
    private final boolean[] booleanTopBar = new boolean[cantidad];

    public BarTopPanel(Controlador ctrl) {
        this.ctrl = ctrl;
        llenarValores();
        setLayout(new GridLayout(1, 3));

        btnInicio = new JButton("INICIO");
        btnTopBar[0] = btnInicio;
        btnTopBar[0].setSelected(true);
        btnTopBar[0].setEnabled(true);
        btnTopBar[0].setBackground(colorSeleccionado);
        btnTopBar[0].setForeground(Color.white);

        btnTopBar[0].getModel().addChangeListener((ChangeEvent ce) -> {
            ButtonModel model = (ButtonModel) ce.getSource();

            if (booleanTopBar[0]) {
                if (model.isRollover()) {
                    btnTopBar[0].setBackground(colorSombra);
                } else {
                    if (booleanTopBar[0]) {
                        btnTopBar[0].setBackground(Color.white);
                    }
                }
            }
        });
        btnTopBar[0].addActionListener((ActionEvent ae) -> {
            if (booleanTopBar[0]) {
                metodoClickBotones(0);
            }
        });

        btnPedidos = new JButton("PEDIDOS");
        btnTopBar[1] = btnPedidos;
        btnTopBar[1].setBackground(Color.WHITE);
        btnTopBar[1].getModel().addChangeListener((ChangeEvent ce) -> {
            ButtonModel model = (ButtonModel) ce.getSource();

            if (booleanTopBar[1]) {
                if (model.isRollover()) {
                    btnTopBar[1].setBackground(colorSombra);
                } else {
                    if (booleanTopBar[1]) {
                        btnTopBar[1].setBackground(Color.white);
                    }
                }
            }
        });
        btnTopBar[1].addActionListener((ActionEvent ae) -> {
            if (booleanTopBar[1]) {
                metodoClickBotones(1);
            }
        });

        btnProductos = new JButton("PRODUCTOS");
        btnTopBar[2] = btnProductos;
        btnTopBar[2].setBackground(Color.WHITE);
        btnTopBar[2].getModel().addChangeListener((ChangeEvent ce) -> {
            ButtonModel model = (ButtonModel) ce.getSource();

            if (booleanTopBar[2]) {
                if (model.isRollover()) {
                    btnTopBar[2].setBackground(colorSombra);
                } else {
                    if (booleanTopBar[2]) {
                        btnTopBar[2].setBackground(Color.white);
                    }
                }
            }
        });
        btnTopBar[2].addActionListener((ActionEvent ae) -> {
            if (booleanTopBar[2]) {
                metodoClickBotones(2);
            }
        });

        btnMesas = new JButton("MESAS");
        btnTopBar[3] = btnMesas;
        btnTopBar[3].setBackground(Color.WHITE);
        btnTopBar[3].getModel().addChangeListener((ChangeEvent ce) -> {
            ButtonModel model = (ButtonModel) ce.getSource();

            if (booleanTopBar[3]) {
                if (model.isRollover()) {
                    btnTopBar[3].setBackground(colorSombra);
                } else {
                    if (booleanTopBar[3]) {
                        btnTopBar[3].setBackground(Color.white);
                    }
                }
            }
        });
        btnTopBar[3].addActionListener((ActionEvent ae) -> {
            if (booleanTopBar[3]) {
                metodoClickBotones(3);
            }
        });

        btnEmpleados = new JButton("EMPLEADOS");
        btnTopBar[4] = btnEmpleados;
        btnTopBar[4].setBackground(Color.WHITE);
        btnTopBar[4].getModel().addChangeListener((ChangeEvent ce) -> {
            ButtonModel model = (ButtonModel) ce.getSource();

            if (booleanTopBar[4]) {
                if (model.isRollover()) {
                    btnTopBar[4].setBackground(colorSombra);
                } else {
                    if (booleanTopBar[4]) {
                        btnTopBar[4].setBackground(Color.white);
                    }
                }
            }
        });
        btnTopBar[4].addActionListener((ActionEvent ae) -> {
            if (booleanTopBar[4]) {
                metodoClickBotones(4);
            }
        });

        btnEstadistica = new JButton("ESTADISTICA");
        btnTopBar[5] = btnEstadistica;
        btnTopBar[5].setBackground(Color.WHITE);
        btnTopBar[5].getModel().addChangeListener((ChangeEvent ce) -> {
            ButtonModel model = (ButtonModel) ce.getSource();
            if (booleanTopBar[5]) {
                if (model.isRollover()) {
                    btnTopBar[5].setBackground(colorSombra);
                } else {
                    if (booleanTopBar[5]) {
                        btnTopBar[5].setBackground(Color.white);
                    }
                }
            }
        });
        btnTopBar[5].addActionListener((ActionEvent ae) -> {
            if (booleanTopBar[5]) {
                metodoClickBotones(5);
            }
        });

        add(btnTopBar[0]);
        add(btnTopBar[1]);
        add(btnTopBar[2]);
        add(btnTopBar[3]);
        add(btnTopBar[4]);
        add(btnTopBar[5]);
    }

    public void metodoClickBotones(int j) {
        for (int i = 0; i < cantidad; i++) {
            if (i == j) {
                booleanTopBar[i] = false;
                btnTopBar[i].setBackground(colorSeleccionado);
                btnTopBar[i].setForeground(Color.white);
            } else {
                booleanTopBar[i] = true;
                btnTopBar[i].setBackground(Color.white);
                btnTopBar[i].setForeground(Color.black);
            }
        }
        if (j > 0) {
            PanelInicio.setVariableHilo(false);
        }
        switch (j) {
            case 0:
                ctrl.conectarPanel(new PanelInicio(ctrl));
                break;
            case 1:
                ctrl.conectarPanel(new PanelPedidos(ctrl));
                break;
            case 2:
                ctrl.conectarPanel(new PanelProductos(ctrl));
                break;
            case 3:
                ctrl.conectarPanel(new PanelMesas(ctrl));
                break;
            case 4:
                ctrl.conectarPanel(new PanelEmpleados(ctrl));
                break;
            case 5:
                ctrl.conectarPanel(new PanelEstadistica(ctrl));
                break;
            default:
                JOptionPane.showMessageDialog(null, "Error");
                break;

        }
    }

    private void llenarValores() {
        booleanTopBar[0] = false; //clickInicio
        booleanTopBar[1] = true; //clickPedidos;
        booleanTopBar[2] = true; //clickProducto;
        booleanTopBar[3] = true; //clickMesas;
        booleanTopBar[4] = true; //clickEmpleados
        booleanTopBar[5] = true;
    }
}
