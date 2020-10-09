package Interfaz;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import models.Empleado;
import recursos.Variables;

/**
 *
 * @author Juan Pablo
 */
public class PanelEmpleados extends JPanel {

    private final Controlador ctrl;
    private final JPanel pnlEmpleados, pnlEmpleado, pnlBuscar, pnlIngresar;
    private final JPanel pnlNombre, pnlApellido, pnlCedula, pnlContrasenia, pnlCheck;
    private final JLabel lblNombre, lblApellido, lblCedula, lblContrasenia;
    private final JTextField eTxtNombre, eTxtApellido, eTxtCedula;
    private final JPasswordField pTxtContrasenia;
    private final JButton btnIngresar;
    private final JTextField jTxtBuscar;
    private final JButton btnBuscar;
    private final DefaultTableModel dtm;
    private JTable tabla;
    private final String columnas[] = {"ID", "Nombre", "Apellido", "Cedula", "Pedidos"};
    private final String columnas2[][] = {{"ID", "Nombre", "Apellido", "Cedula", "Pedidos"}};

    public PanelEmpleados(Controlador ctrl) {
        this.ctrl = ctrl;
        setBackground(Color.white);
        setLayout(null);

        pnlEmpleados = new JPanel();
        pnlEmpleados.setLayout(new GridLayout());
        pnlEmpleados.setBackground(Color.white);
        pnlEmpleados.setBounds(390, 0, Variables.width - 390, Variables.heigth - 80);
        pnlEmpleados.setBorder(new CompoundBorder((new EmptyBorder(10, 10, 10, 10)), new TitledBorder("Empleados")));
        add(pnlEmpleados);

        dtm = new DefaultTableModel(ctrl.consultaEmpleados("estado DESC"), columnas);

        tabla = new JTable(dtm) {
            @Override
            public java.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                java.awt.Component comp = super.prepareRenderer(renderer, row, col);
                Object value = getModel().getValueAt(row, 4);
                if (value == "") {
                    comp.setBackground(Color.lightGray);
                } else {
                    comp.setBackground(Color.white);
                }
                return comp;
            }
        };

        tabla.setOpaque(true);
        tabla.setFillsViewportHeight(true);
        tabla.setBackground(Color.white);
        tabla.getTableHeader().setBackground(Variables.rojo);
        tabla.getTableHeader().setForeground(Color.white);
        pnlEmpleados.add(tabla);
        tamanioColumna();
        JScrollPane scrollPane = new JScrollPane(tabla);
        pnlEmpleados.add(scrollPane, BorderLayout.CENTER);
        tabla.setRowSelectionAllowed(true);
        tabla.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String columna = tabla.getColumnName(tabla.columnAtPoint(e.getPoint())).toLowerCase();
                dtm.setDataVector(ctrl.consultaEmpleados(columna), columnas);
                tamanioColumna();
            }
        });

        pnlEmpleado = new JPanel();
        pnlEmpleado.setLayout(null);
        pnlEmpleado.setBounds(0, 0, 400, Variables.heigth - 80);
        pnlEmpleado.setBackground(Color.white);
        pnlEmpleado.setBorder(new CompoundBorder((new EmptyBorder(10, 10, 10, 10)), new TitledBorder("")));
        add(pnlEmpleado);

        pnlBuscar = new JPanel();
        pnlBuscar.setLayout(new BorderLayout());
        pnlBuscar.setBackground(Color.white);
        pnlBuscar.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Buscar")));
        pnlBuscar.setBounds(20, 30, 360, 120);
        pnlEmpleado.add(pnlBuscar);

        JPanel valoresBuscar = new JPanel(new GridBagLayout());
        valoresBuscar.setPreferredSize(new Dimension(360, 120));
        valoresBuscar.setBackground(Color.white);
        pnlBuscar.add(valoresBuscar);

        jTxtBuscar = new JTextField();
        jTxtBuscar.setPreferredSize(new Dimension(140, 30));
        jTxtBuscar.setBackground(Color.white);
        valoresBuscar.add(jTxtBuscar);

        btnBuscar = new JButton("  Buscar Empleado  ");
        btnBuscar.setBackground(Variables.rojo);
        btnBuscar.setPreferredSize(new Dimension(150, 30));
        btnBuscar.setForeground(Color.white);
        valoresBuscar.add(btnBuscar);
        btnBuscar.addActionListener((ActionEvent ae) -> {
            eventoBuscar();
        });
        jTxtBuscar.addActionListener((ActionEvent e) -> {
            eventoBuscar();
        });

        pnlIngresar = new JPanel();
        pnlIngresar.setLayout(null);
        pnlIngresar.setBackground(Color.white);
        pnlIngresar.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Ingresar Empleado")));
        pnlIngresar.setBounds(20, 150, 360, Variables.heigth - 250);
        pnlEmpleado.add(pnlIngresar);

        pnlNombre = new JPanel(new GridLayout(1, 2));
        pnlNombre.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Nombre")));
        pnlNombre.setBounds(10, 20, 340, 80);
        pnlNombre.setBackground(Color.white);
        pnlIngresar.add(pnlNombre);
        lblNombre = new JLabel("Ingrese nombre: ", JLabel.CENTER);
        pnlNombre.add(lblNombre);
        eTxtNombre = new JTextField();
        eTxtNombre.setBorder(new CompoundBorder((new EmptyBorder(15, 0, 15, 15)), new TitledBorder("")));
        pnlNombre.add(eTxtNombre);

        pnlApellido = new JPanel(new GridLayout(1, 2));
        pnlApellido.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Apellido")));
        pnlApellido.setBounds(10, 110, 340, 80);
        pnlApellido.setBackground(Color.white);
        pnlIngresar.add(pnlApellido);
        lblApellido = new JLabel("Ingrese apellido: ", JLabel.CENTER);
        pnlApellido.add(lblApellido);
        eTxtApellido = new JTextField();
        eTxtApellido.setBorder(new CompoundBorder((new EmptyBorder(15, 0, 15, 15)), new TitledBorder("")));
        pnlApellido.add(eTxtApellido);

        pnlCedula = new JPanel(new GridLayout(1, 2));
        pnlCedula.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Cedula")));
        pnlCedula.setBounds(10, 200, 340, 80);
        pnlCedula.setBackground(Color.white);
        pnlIngresar.add(pnlCedula);
        lblCedula = new JLabel("Ingrese cedula: ", JLabel.CENTER);
        pnlCedula.add(lblCedula);
        eTxtCedula = new JTextField();
        eTxtCedula.setBorder(new CompoundBorder((new EmptyBorder(15, 0, 15, 15)), new TitledBorder("")));
        pnlCedula.add(eTxtCedula);

        pnlContrasenia = new JPanel(new GridLayout(1, 2));
        pnlContrasenia.setBorder(new CompoundBorder((new EmptyBorder(0, 0, 0, 0)), new TitledBorder("Contraseña")));
        pnlContrasenia.setBounds(10, 290, 340, 80);
        pnlContrasenia.setBackground(Color.white);
        pnlIngresar.add(pnlContrasenia);
        lblContrasenia = new JLabel("Ingrese contraseña: ", JLabel.CENTER);
        pnlContrasenia.add(lblContrasenia);
        pTxtContrasenia = new JPasswordField();
        pTxtContrasenia.setBorder(new CompoundBorder((new EmptyBorder(15, 0, 15, 15)), new TitledBorder("")));
        pnlContrasenia.add(pTxtContrasenia);

        pnlCheck = new JPanel(new GridLayout(1, 2));
        pnlCheck.setBackground(Color.yellow);
        pnlCheck.setBorder(new CompoundBorder((new EmptyBorder(15, 0, 15, 15)), new TitledBorder("")));
        pnlCheck.setBounds(10, 380, 340, 50);
        //pnlIngresar.add(pnlCheck);

        btnIngresar = new JButton("Registrar empleado");
        btnIngresar.setBackground(Variables.rojo);
        btnIngresar.setForeground(Color.white);
        btnIngresar.setBounds(30, 450, 300, 40);
        pnlIngresar.add(btnIngresar);
        btnIngresar.addActionListener((ActionEvent ae) -> {
            if (ctrl.insertarEmpleado(valorEmpleado())) {
                eTxtNombre.setText("");
                eTxtApellido.setText("");
                eTxtCedula.setText("");
                pTxtContrasenia.setText("");
                dtm.setDataVector(ctrl.consultaEmpleados("estado DESC"), columnas);
                tamanioColumna();
            } else {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error.");
            }
        });
    }

    private Empleado valorEmpleado() {
        String nombres[] = eTxtNombre.getText().trim().split(" ");
        String apellidos[] = eTxtApellido.getText().trim().split(" ");
        String nombre = "";
        String apellido = "";
        String id = "";
        String cedula = eTxtCedula.getText().trim();
        for (int i = 0; i < nombres.length; i++) {
            String aux = " ";
            if (i == nombres.length - 1) {
                aux = "";
            }
            nombre += nombres[i].substring(0, 1).toUpperCase() + nombres[i].substring(1) + aux;
        }
        for (int i = 0; i < apellidos.length; i++) {
            String aux = " ";
            if (i == apellidos.length - 1) {
                aux = "";
            }
            apellido += apellidos[i].substring(0, 1).toUpperCase() + apellidos[i].substring(1) + aux;
        }

        id = nombres[0].substring(0, 1).toUpperCase() + nombres[1].substring(0, 1).toUpperCase() + apellidos[0].substring(0, 1).toUpperCase() + apellidos[1].substring(0, 1).toUpperCase() + cedula.substring(cedula.length() - 4);

        return new Empleado(id, nombre, apellido, pTxtContrasenia.getText(), cedula, 0, false);
    }

    private void tamanioColumna() {
        TableColumn columna = tabla.getColumnModel().getColumn(0);
        columna.setMinWidth(150);
        columna.setMaxWidth(150);
        columna.setPreferredWidth(150);

        columna = tabla.getColumnModel().getColumn(3);
        columna.setMinWidth(120);
        columna.setMaxWidth(120);
        columna.setPreferredWidth(120);

        columna = tabla.getColumnModel().getColumn(4);
        columna.setMinWidth(100);
        columna.setMaxWidth(100);
        columna.setPreferredWidth(100);
    }

    private void eventoBuscar() {
        String dato = jTxtBuscar.getText().trim();
        Object[][] objeto = ctrl.consultaEmpleadosPor("cedula", dato);
        if (objeto.length > 0) {
            dtm.setDataVector(objeto, columnas);
        } else {
            JOptionPane.showMessageDialog(null, "No se ha encontrado el empleado.");
        }
    }
}
