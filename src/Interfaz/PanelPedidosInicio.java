package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Juan Pablo
 */
public class PanelPedidosInicio extends JPanel {

    private String posicion;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screenSize.getWidth();
    int height = (int) screenSize.getHeight();
    private int numeroMesa = 0;
    private String orden, estado,idMesero;
    private int total;
    private final JLabel lblMesa, lblOrden, lblCantidadPersonas, lblTotalPedido, lblEstado;
    private final Controlador ctrl;

    public PanelPedidosInicio(Controlador ctrl, int mesaNumero, boolean opcion) {
        this.ctrl = ctrl;
        setLayout(new GridLayout(5, 1));
        numeroMesa = mesaNumero;
        setBackground(Color.white);

        lblMesa = new JLabel("", SwingConstants.CENTER);
        lblMesa.setForeground(Color.blue.darker().darker());
        add(lblMesa);

        lblOrden = new JLabel();
        lblOrden.setBorder(new EmptyBorder(0, 10, 0, 0));
        add(lblOrden);

        lblTotalPedido = new JLabel();
        lblTotalPedido.setBorder(new EmptyBorder(0, 10, 0, 0));
        add(lblTotalPedido);

        lblCantidadPersonas = new JLabel();
        lblCantidadPersonas.setBorder(new EmptyBorder(0, 10, 0, 0));
        add(lblCantidadPersonas);

        lblEstado = new JLabel("", SwingConstants.RIGHT);
        lblEstado.setBorder(new EmptyBorder(0, 0, 0, 10));
        //lblEstado.setForeground(colorEstado(estado));
        add(lblEstado);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (opcion && !posicion.equals("")) {
                    JFramePedido jframe = new JFramePedido(ctrl, numeroMesa, orden, estado, idMesero);
                }
            }
        });
    }

    public void setLblEtiqueta(String nombre) {
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 15, 0), new TitledBorder(nombre)));
    }

    public void setLblMesa(int mesa) {
        lblMesa.setText((mesa == -1) ? "" : "Mesa: #" + mesa);
        numeroMesa = mesa;
    }

    public void setLblOrden(String orden) {
        this.orden = orden;
        lblOrden.setText((orden.equals(" ")) ? "" : "Orden: " + orden);
    }

    public void setLblCantidad(int personas) {
        lblCantidadPersonas.setText((personas == -1) ? "" : "Personas: " + personas);
    }

    public void setLblTotal(int total) {
        this.total = total;
        lblTotalPedido.setText((total == -1) ? "" : "Total: $ " + total);
    }

    public String getNumeroOrden() {
        String cadena[] = lblOrden.getText().split(" ");
        return cadena[1];
    }
    
    public void setIdMesero(String idMesero){
        this.idMesero = idMesero;
    }

    public void setLblEstado(String estado) {
        this.estado = estado;
        lblEstado.setText(estado);
        lblEstado.setForeground(colorEstado(estado));
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    private Color colorEstado(String estado) {
        Color color = new Color(0);
        switch (estado) {
            case "Completado":
                color = Color.green.darker();
                break;
            case "Esperando":
                color = Color.yellow.darker();
                break;
            case "Ordenando":
                color = Color.blue.darker();
                break;
            case "Comiendo":
                color = Color.red.darker();
                break;
        }
        return color;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public void cambiarColores(boolean opcion) {
        setEnabled(opcion);
        lblEstado.setEnabled(opcion);
        lblTotalPedido.setEnabled(opcion);
        lblCantidadPersonas.setEnabled(opcion);
        lblMesa.setEnabled(opcion);
        lblOrden.setEnabled(opcion);
    }
}
