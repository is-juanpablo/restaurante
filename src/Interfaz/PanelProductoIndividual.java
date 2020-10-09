package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import recursos.Variables;

/**
 *
 * @author Juan Pablo
 */
public class PanelProductoIndividual extends JPanel {

    int width = Variables.width;
    int heigth = Variables.heigth;

    int tamanio = (width - 545) / 5;
    int tamanioVertical = 150;
    private Image imagenProducto;
    private ImageIcon icon = null;
    private JLabel lblProducto, lblImagen, lblCheck, lblPrecio;
    private JPanel pnlIndividual;
    private Controlador ctrl;
    private boolean estado;
    private int posicion = 0;

    public PanelProductoIndividual(int dato, String nombre, String pathImagen, String categoria, boolean estadoCheck, Controlador ctrl, int precio) {
        this.ctrl = ctrl;
        estado = estadoCheck;
        int auxTamanio = dato / 5;
        setLayout(new GridLayout(2, 1));
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(categoria)));
        setBounds(tamanio * (dato % 5) + 15, 30 + (tamanioVertical * auxTamanio), tamanio, tamanioVertical);
        setBackground(Variables.white);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)) {
                    if (posicion > 0) {
                        int opcion = JOptionPane.showConfirmDialog(null, "Desea eliminar este producto.");
                        if (opcion == 0) {
                            ctrl.eliminarProducto(posicion);
                            ctrl.llamarHiloProducto();
                        }
                    }
                } else {
                    if (posicion > 0) {
                        JFrame frame = new JFrameProducto(ctrl.consultaProductos(posicion), ctrl, null);
                    }
                }
            }
        });

        if (!pathImagen.equals("")) {
            try {
                icon = new ImageIcon(new URL(pathImagen));
            } catch (MalformedURLException ex) {
                Logger.getLogger(PanelProductoIndividual.class.getName()).log(Level.SEVERE, null, ex);
            }
            imagenProducto = icon.getImage();
            Image newimg = imagenProducto.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
        }
        lblImagen = new JLabel(icon, JLabel.CENTER);
        //add(lblImagen);

        pnlIndividual = new JPanel();
        pnlIndividual.setBackground(Variables.white);
        pnlIndividual.setLayout(new GridLayout(3, 1));

        lblProducto = new JLabel(nombre, JLabel.CENTER);
        pnlIndividual.add(lblProducto);

        lblPrecio = new JLabel(String.valueOf(precio), JLabel.CENTER);
        pnlIndividual.add(lblPrecio);

        lblCheck = new JLabel(nombre);
        ImageIcon iconCheck = new ImageIcon("src/recursos/checkNO.png");
        Image image = iconCheck.getImage();
        Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        iconCheck = new ImageIcon(newimg);
        lblCheck.setIcon(iconCheck);

        pnlIndividual.add(lblCheck);

        lblCheck.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //setAction(estadoCheck);
                ctrl.modificarValorCheck(estado, posicion);
                setAction(!estado, lblProducto.getText());
            }
        });

    }

    public void setLblPrecio(int precio) {
        lblPrecio.setText((precio > 0) ? "$ " + precio : "");
    }

    public void setImageIcon(String path) {
        try {
            setLayout(new GridLayout(2, 1));
            icon = new ImageIcon(new URL(path));
            Image image = icon.getImage();
            Image newimg = image.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);
            lblImagen.setIcon(icon);
            add(lblImagen);
        } catch (MalformedURLException ex) {
            //Logger.getLogger(PanelProductoIndividual.class.getName()).log(Level.SEVERE, null, ex);
            remove(lblImagen);
            setLayout(new GridLayout(1, 1));
        }
        add(pnlIndividual);

    }

    public void setLblNombre(String nombre) {
        lblProducto.setText(nombre);
    }

    public void setEtiqueta(String categoria) {
        setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder(categoria.toUpperCase())));
    }

    public void setCheckIcon(boolean check, String nombre) {
        ImageIcon iconCheck = new ImageIcon("src/recursos/" + ((nombre.equals("")) ? null : ((check) ? "checkSI" : "checkNO") + ".png"));
        Image image = iconCheck.getImage();
        Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        iconCheck = new ImageIcon(newimg);
        lblCheck.setIcon(iconCheck);
    }

    public void setAction(boolean servido, String nombre) {
        estado = servido;
        setEnabled(servido);
        lblImagen.setEnabled(servido);
        setCheckIcon(estado, nombre);
        pnlIndividual.setEnabled(servido);
    }

    public void setPosicion(int i) {
        posicion = i;
    }
}
