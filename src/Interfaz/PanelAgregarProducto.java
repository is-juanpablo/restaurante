package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Juan Pablo
 */
public class PanelAgregarProducto extends JPanel {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int) screenSize.getWidth();
    int height = (int) screenSize.getHeight();
    private JPanel pnlNombre, pnlCategoria, pnlServido, pnlImagen, pnlServidoOpcion, pnlPrecio;
    private JLabel lblNombre, lblCategoria, lblServido, lblImagen, lblPrecio;
    private JTextField jTxtNombre, jTxtCategoria, jTxtImagen, jTxtPrecio;
    private JRadioButton checkSi, checkNo;
    private JButton btnAgregar;
    private Controlador ctrl;

    public PanelAgregarProducto(Controlador ctrl, PanelProductos pnlProductos) {
        this.ctrl = ctrl;
        setLayout(new GridLayout(6, 1));
        setBorder(new CompoundBorder(new EmptyBorder(10, 10, 80, 10), new TitledBorder("Agregar Producto")));
        setBackground(Color.white);
        setBounds(width - 505, 300, 505, height - 300);

        pnlNombre = new JPanel();
        pnlNombre.setLayout(new GridLayout());
        pnlNombre.setBackground(Color.white);
        pnlNombre.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(pnlNombre);
        lblNombre = new JLabel("Ingrese nombre: ", JLabel.CENTER);
        pnlNombre.add(lblNombre);
        jTxtNombre = new JTextField();
        pnlNombre.add(jTxtNombre);

        pnlCategoria = new JPanel();
        pnlCategoria.setLayout(new GridLayout());
        pnlCategoria.setBackground(Color.white);
        pnlCategoria.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(pnlCategoria);
        lblCategoria = new JLabel("Ingrese categoria: ", JLabel.CENTER);
        pnlCategoria.add(lblCategoria);
        jTxtCategoria = new JTextField();
        pnlCategoria.add(jTxtCategoria);

        pnlServido = new JPanel();
        pnlServido.setLayout(new GridLayout());
        pnlServido.setBackground(Color.white);
        pnlServido.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(pnlServido);
        lblServido = new JLabel("¿Servido?: ", JLabel.CENTER);
        pnlServido.add(lblServido);
        pnlServidoOpcion = new JPanel();
        pnlServidoOpcion.setLayout(new GridLayout());
        pnlServidoOpcion.setBackground(Color.white);
        pnlServido.add(pnlServidoOpcion);
        checkSi = new JRadioButton("SI");
        checkSi.setBackground(Color.white);
        checkSi.setSelected(true);
        checkNo = new JRadioButton("NO");
        checkNo.setBackground(Color.white);

        ButtonGroup grupoCheck = new ButtonGroup();
        grupoCheck.add(checkSi);
        grupoCheck.add(checkNo);
        pnlServidoOpcion.add(checkSi);
        pnlServidoOpcion.add(checkNo);

        pnlImagen = new JPanel();
        pnlImagen.setLayout(new GridLayout());
        pnlImagen.setBackground(Color.white);
        pnlImagen.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(pnlImagen);
        lblImagen = new JLabel("Ingrese imagen: ", JLabel.CENTER);
        pnlImagen.add(lblImagen);
        jTxtImagen = new JTextField();
        pnlImagen.add(jTxtImagen);

        pnlPrecio = new JPanel();
        pnlPrecio.setLayout(new GridLayout());
        pnlPrecio.setBackground(Color.white);
        pnlPrecio.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(pnlPrecio);
        lblPrecio = new JLabel("Ingrese precio: ", JLabel.CENTER);
        pnlPrecio.add(lblPrecio);
        jTxtPrecio = new JTextField();
        pnlPrecio.add(jTxtPrecio);

        btnAgregar = new JButton("AGREGAR PRODUCTO");
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setBackground(Color.blue.darker().darker());
        btnAgregar.setBorder(new EmptyBorder(30, 30, 30, 30));
        add(btnAgregar);
        btnAgregar.addActionListener((ActionEvent ae) -> {
            if (jTxtNombre.getText().trim().isEmpty() || jTxtCategoria.getText().trim().isEmpty() || jTxtImagen.getText().trim().isEmpty() || jTxtPrecio.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe registrar todos los campos.");
            } else {
                JOptionPane.showMessageDialog(null, "El producto ha sido agregado.");
                ctrl.insertarProducto(jTxtNombre.getText().trim(), jTxtCategoria.getText().trim(), jTxtImagen.getText().trim(), checkSi.isSelected(), jTxtPrecio.getText().trim());
                pnlProductos.hiloProductos();
                jTxtNombre.setText("");
                jTxtPrecio.setText("");
                jTxtCategoria.setText("");
                jTxtImagen.setText("");
            }
        });
    }
}
