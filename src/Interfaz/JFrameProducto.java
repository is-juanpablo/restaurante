package Interfaz;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import models.Producto;
import recursos.Variables;

/**
 *
 * @author Juan Pablo
 */
public class JFrameProducto extends JFrame {

    private final int ancho = 800, alto = 500;
    private final JPanel pnlDatos, pnlIngredientes, pnlImagen, pnlCampos;
    private JLabel lblImagen;
    private ImageIcon icon;
    private Image imagenProducto;
    private final Controlador ctrl;
    private final PanelProductos pnlProductos;

    public JFrameProducto(Producto productoFrame, Controlador ctrl, PanelProductos pnlProductos) {
        this.pnlProductos = pnlProductos;
        this.ctrl = ctrl;
        setTitle("Datos Producto");
        setResizable(false);
        setVisible(true);
        setLayout(null);
        setBounds(Variables.width / 2 - 300, Variables.heigth / 2 - 250, 600, 500);

        pnlDatos = new JPanel();
        pnlDatos.setBackground(Color.white);
        pnlDatos.setLayout(null);
        pnlDatos.setBounds(0, 0, 600, 200);
        pnlDatos.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new TitledBorder("Datos")));
        add(pnlDatos);

        pnlImagen = new JPanel();
        pnlImagen.setLayout(new GridLayout());
        pnlImagen.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new TitledBorder("Imagen")));
        pnlImagen.setBackground(Color.white);
        pnlImagen.setBounds(0, 0, 140, 200);
        pnlDatos.add(pnlImagen);

        pnlCampos = new JPanel();
        pnlCampos.setLayout(new GridLayout(4, 1));
        pnlCampos.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new TitledBorder("Datos")));
        pnlCampos.setBackground(Color.white);
        pnlCampos.setBounds(140, 0, 460, 200);
        pnlDatos.add(pnlCampos);

        pnlIngredientes = new JPanel();
        pnlIngredientes.setBackground(Color.white);
        pnlIngredientes.setLayout(new GridLayout());
        pnlIngredientes.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new TitledBorder("Ingredientes")));
        pnlIngredientes.setBounds(0, 200, 600, 270);
        add(pnlIngredientes);

        if (productoFrame.getId() > 0) {
            llenarImagen(productoFrame.getImagen());
            llenarCampos(productoFrame);
            llenarTabla(productoFrame.getId());
        }
    }

    private void llenarTabla(int id) {

        String[] columnas = {"#", "Ingrediente"};

        //Object[][] datos = {{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"},{"1", "Arroz", "Grano"}, {"2", "Pasta", "Principio"}};
        DefaultTableModel dtm = new DefaultTableModel(ctrl.consultaIngredientes(id), columnas);
        JTable table = new JTable(dtm);
        pnlIngredientes.add(table);

        JScrollPane scrollPane = new JScrollPane(table);
        pnlIngredientes.add(scrollPane, BorderLayout.CENTER);
    }

    private void llenarImagen(String path) {
        try {
            icon = new ImageIcon(new URL(path));
        } catch (MalformedURLException ex) {
            Logger.getLogger(PanelProductoIndividual.class.getName()).log(Level.SEVERE, null, ex);
        }
        imagenProducto = icon.getImage();
        Image newimg = imagenProducto.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        lblImagen = new JLabel(icon, JLabel.CENTER);
        pnlImagen.add(lblImagen);
    }

    private void llenarCampos(Producto productoFrame) {
        JLabel lblNombre, lblCategoria, lblPrecio;
        JTextField jTxtNombre, jTxtCategoria, jTxtPrecio;
        JRadioButton checkSi, checkNo;
        JPanel pnlCampo1, pnlCampo2, pnlCampo3, pnlCampo4;

        pnlCampo1 = new JPanel();
        pnlCampo1.setLayout(new GridLayout(1, 2));
        pnlCampo1.setBackground(Color.white);
        pnlCampos.add(pnlCampo1);
        lblNombre = new JLabel("Nombre:", JLabel.CENTER);
        pnlCampo1.add(lblNombre);
        jTxtNombre = new JTextField(productoFrame.getNombre());
        jTxtNombre.setBorder(new EmptyBorder(8, 15, 8, 15));
        pnlCampo1.add(jTxtNombre, BorderLayout.CENTER);
        jTxtNombre.addActionListener((ActionEvent e) -> {
            int resp = JOptionPane.showConfirmDialog(null, "¿Desea modificar el nombre?", "Modificar valores", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                ctrl.modificarProducto("nombre", jTxtNombre.getText().trim(), productoFrame.getId());
            }
        });

        pnlCampo2 = new JPanel();
        pnlCampo2.setLayout(new GridLayout(1, 2));
        pnlCampo2.setBackground(Color.white);
        pnlCampos.add(pnlCampo2);
        lblCategoria = new JLabel("Categoria:", JLabel.CENTER);
        pnlCampo2.add(lblCategoria);
        jTxtCategoria = new JTextField(productoFrame.getCategoria());
        jTxtCategoria.setBorder(new EmptyBorder(8, 15, 8, 15));
        pnlCampo2.add(jTxtCategoria, BorderLayout.CENTER);
        jTxtCategoria.addActionListener((ActionEvent e) -> {
            int resp = JOptionPane.showConfirmDialog(null, "¿Desea modificar la categoria?", "Modificar valores", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                ctrl.modificarProducto("categoria", jTxtCategoria.getText().trim(), productoFrame.getId());
            }
        });

        pnlCampo3 = new JPanel();
        pnlCampo3.setLayout(new GridLayout(1, 2));
        pnlCampo3.setBackground(Color.white);
        pnlCampos.add(pnlCampo3);
        lblPrecio = new JLabel("Precio:", JLabel.CENTER);
        pnlCampo3.add(lblPrecio);
        jTxtPrecio = new JTextField(String.valueOf(productoFrame.getPrecio()));
        jTxtPrecio.setBorder(new EmptyBorder(8, 15, 8, 15));
        pnlCampo3.add(jTxtPrecio, BorderLayout.CENTER);
        jTxtPrecio.addActionListener((ActionEvent e) -> {
            int resp = JOptionPane.showConfirmDialog(null, "¿Desea modificar el precio?", "Modificar valores", JOptionPane.YES_NO_OPTION);
            if (resp == 0) {
                ctrl.modificarProducto("precio", jTxtPrecio.getText().trim(), productoFrame.getId());
            }
        });

        pnlCampo4 = new JPanel();
        pnlCampo4.setLayout(new GridLayout(1, 3));
        pnlCampo4.setBackground(Color.white);
        pnlCampos.add(pnlCampo4);
        JLabel lblServido = new JLabel("¿Servir hoy?", JLabel.CENTER);
        pnlCampo4.add(lblServido);
        checkSi = new JRadioButton("SI");
        checkSi.setBackground(Color.white);
        checkNo = new JRadioButton("NO");
        checkNo.setBackground(Color.white);

        checkNo.setSelected(true);
        checkNo.setEnabled(false);
        checkSi.setEnabled(true);
        boolean variableCheck = false;
        if (productoFrame.isServido()) {
            checkSi.setSelected(true);
            checkSi.setEnabled(false);
            checkNo.setEnabled(true);
        }
        ButtonGroup grupoCheck = new ButtonGroup();
        grupoCheck.add(checkSi);
        grupoCheck.add(checkNo);
        pnlCampo4.add(checkSi);
        pnlCampo4.add(checkNo);
        checkNo.addActionListener((ActionEvent ae) -> {
            checkNo.setEnabled(false);
            if (checkNo.isSelected()) {
                int resp = JOptionPane.showConfirmDialog(null, "¿Desea poner el producto inhabilitado?", "Modificar valores", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    ctrl.modificarProducto("servido", "0", productoFrame.getId());
                    checkSi.setEnabled(true);
                } else {
                    checkSi.setSelected(true);
                    checkNo.setEnabled(true);
                }
            }
        });
        checkSi.addActionListener((ActionEvent ae) -> {
            checkSi.setEnabled(false);
            if (checkSi.isSelected()) {
                int resp = JOptionPane.showConfirmDialog(null, "¿Desea poner el producto habilitado?", "Modificar valores", JOptionPane.YES_NO_OPTION);
                if (resp == 0) {
                    ctrl.modificarProducto("servido", "1", productoFrame.getId());
                    checkNo.setEnabled(true);
                } else {
                    checkNo.setSelected(true);
                    checkSi.setEnabled(true);
                }
            }
        });
    }
}
