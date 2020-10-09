package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Juan Pablo
 */
public final class PanelCardInicio extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screenSize.getWidth();
    int height = (int) screenSize.getHeight();
    private final JLabel lblPrecio, lblMensaje, lblObjetivo, lblPorcentaje;
    private long precio;
    private int objetivo;
    private final NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
    private int posicion;
    private Controlador ctrl;

    public PanelCardInicio(long precio, String mensaje, int objetivo, String tipo, Controlador ctrl) {
        this.ctrl = ctrl;
        this.precio = precio;
        this.objetivo = objetivo;
        setBackground(Color.WHITE);
        setLayout(null);
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(tipo)));
        setBounds(0, 0, width, 120);

        lblPrecio = new JLabel("$ " + formatter.format(precio));
        lblPrecio.setForeground(Color.RED);
        lblPrecio.setBounds(30, 20, 100, 20);

        lblMensaje = new JLabel(mensaje);
        lblMensaje.setBounds(30, 50, 160, 20);

        lblObjetivo = new JLabel("Objetivo: $" + formatter.format(objetivo));
        lblObjetivo.setForeground(Color.BLUE.brighter());
        lblObjetivo.setBounds(30, 80, 150, 20);

        lblPorcentaje = new JLabel();
        lblPorcentaje.setFont(new Font("Arial", 0, 20));
        lblPorcentaje.setBounds(240, 50, 100, 30);
        calcularPorcentaje();

        add(lblPrecio);
        add(lblMensaje);
        add(lblObjetivo);
        add(lblPorcentaje);

        lblObjetivo.addMouseListener(new MouseAdapter() {
            String valor = "";

            @Override
            public void mouseClicked(MouseEvent e) {
                valor = JOptionPane.showInputDialog("Ingrese nuevo valor objetivo:", "Sin simbolo ni comas.");
                if (valor != null) {
                    try {
                        Integer.valueOf(valor);
                        ctrl.modificarValorObjetivo(Integer.parseInt(valor), posicion);
                        calcularPorcentaje();
                    } catch (NumberFormatException ee) {
                        JOptionPane.showMessageDialog(null, "Debe ingresar un valor numerico, sin comas ni simbolos.");
                    }
                }
            }
        });
    }

    public void setLblPrecio(long precio) {
        this.precio = precio;
        lblPrecio.setText("$ " + formatter.format(precio));
    }

    public void setLblMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    public void setLblObjetivo(int precio) {
        this.objetivo = precio;
        lblObjetivo.setText("Objetivo: $" + formatter.format(precio));
    }

    public JLabel getLblObjetivo() {
        return lblObjetivo;
    }

    public long getPrecio() {
        return precio;
    }

    public int getObjetivo() {
        return objetivo;
    }

    public void calcularPorcentaje() {
        lblPorcentaje.setText("");
        int valorObjetivo = getObjetivo();
        if (valorObjetivo > 0) {
            long valor = getPrecio();
            valor = valor * 100;
            valor = valor / valorObjetivo;
            lblPorcentaje.setText(valor + "%");
        }
    }

    public void setLblDia(String dia) {
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(dia)));
    }

    public void setPosicion(int i) {
        posicion = (i + 1);
    }

}
