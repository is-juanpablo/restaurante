package Interfaz;

import Controlador.Controlador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import recursos.Variables;

/**
 *
 * @author Juan Pablo
 */
public class JFramePedido extends JFrame {

    private Controlador ctrl;
    private JPanel pnlDatos, pnlProductos;
    private JPanel pnlMesa, pnlPrecio, pnlNumeroOrden;
    private JButton btnPagar;
    private JLabel lblMesa, lblPrecio, lblOrden;
    private String[] columnas = {"Cantidad", "Producto", "Precio"};
    private String orden;
    private String idMesero;
    private int total;

    public JFramePedido(Controlador ctrl, int numeroMesa, String orden, String estado, String idMesero) {
        this.ctrl = ctrl;
        this.idMesero = idMesero;
        this.orden = orden;
        setTitle("Datos Orden");
        setResizable(false);

        setLayout(new GridLayout(1, 2));
        setBounds(Variables.width / 2 - 350, Variables.heigth / 2 - 150, 700, 300);
        setBackground(Variables.white);
        pnlDatos = new JPanel(null);
        pnlDatos.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new TitledBorder("Datos")));
        pnlDatos.setBackground(Variables.white);
        add(pnlDatos);

        pnlProductos = new JPanel(new GridLayout());
        pnlProductos.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new TitledBorder("Productos")));
        pnlProductos.setBackground(Variables.white);
        add(pnlProductos);
        datosProductos();

        pnlMesa = new JPanel();
        pnlMesa.setBackground(Variables.white);
        pnlMesa.setBounds(10, 20, 325, 20);
        lblMesa = new JLabel("Mesa # " + numeroMesa, JLabel.CENTER);
        pnlMesa.add(lblMesa);
        pnlDatos.add(pnlMesa);

        pnlPrecio = new JPanel();
        pnlPrecio.setBackground(Variables.white);
        pnlPrecio.setBounds(10, 70, 325, 60);
        pnlPrecio.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Precio")));
        pnlDatos.add(pnlPrecio);
        total = ctrl.consultaTotal();
        lblPrecio = new JLabel("$ " + total, JLabel.CENTER);
        pnlPrecio.add(lblPrecio);

        pnlNumeroOrden = new JPanel();
        pnlNumeroOrden.setBackground(Variables.white);
        pnlNumeroOrden.setBounds(10, 150, 325, 60);
        pnlNumeroOrden.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Numero Orden")));
        pnlDatos.add(pnlNumeroOrden);
        lblOrden = new JLabel(orden.toUpperCase(), JLabel.CENTER);
        pnlNumeroOrden.add(lblOrden);

        btnPagar = new JButton("PAGAR (" + estado + ")");
        btnPagar.setBackground(Variables.rojo);
        btnPagar.setBounds(10, 220, 325, 40);
        btnPagar.setForeground(Variables.white);
        if (estado.equals("Completado")) {
            btnPagar.setForeground(Color.BLACK);
            btnPagar.setBackground(Color.white);
            btnPagar.setText(estado);
            btnPagar.setEnabled(false);
        }
        pnlDatos.add(btnPagar);
        btnPagar.addActionListener((ActionEvent ae) -> {
            if (estado.equals("Comiendo")) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Desea poner este pedido como pago?");
                if (opcion == 0) {
                    ctrl.modificarPedido(numeroMesa, orden, String.valueOf(total),idMesero);
                    btnPagar.setEnabled(false);
                    btnPagar.setBackground(Variables.white);
                    btnPagar.setText("COMPLETADO");
                    ctrl.hiloBalance();
                    dispose();
                }
            }
        });

        setVisible(true);
    }

    private void datosProductos() {
        DefaultTableModel dtm = new DefaultTableModel(ctrl.consultaDetalle(orden), columnas);
        JTable table = new JTable(dtm);
        pnlProductos.add(table);

        JScrollPane scrollPane = new JScrollPane(table);
        pnlProductos.add(scrollPane, BorderLayout.CENTER);
    }
}
