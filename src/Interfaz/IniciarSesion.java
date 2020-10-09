package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import recursos.Variables;

/**
 *
 * @author Juan Pablo
 */
public class IniciarSesion extends JFrame {

    private final JPanel pnlPrincipal, pnlDatos;
    private final int ancho = Variables.width / 2 - 200;
    private final int alto = Variables.heigth / 2 - 300;
    private final JLabel lblImagen, lblUsuario, lblContrasenia;
    private final JTextField eTxtUsuario;
    private final JPasswordField eTxtContrasenia;
    private final JButton btnIniciar;
    private InterfazApp interfazApp;
    private final Controlador ctrl;

    public IniciarSesion(Controlador ctrl) {
        this.ctrl = ctrl;
        setTitle("  Iniciar Sesión  ");
        setLayout(null);
        //getContentPane().setBackground(Color.white);

        pnlPrincipal = new JPanel(null);
        pnlPrincipal.setBackground(Color.white);
        pnlPrincipal.setBounds(ancho, alto, 400, 600);
        pnlPrincipal.setVisible(true);
        pnlPrincipal.setBorder(new CompoundBorder(new EmptyBorder(0, 0, 0, 0), new TitledBorder("Iniciar Sesión")));
        getContentPane().add(pnlPrincipal);

        lblImagen = new JLabel("\"Restaurante\"", JLabel.CENTER);
        lblImagen.setFont(new Font("Arial", 10, 50));
        lblImagen.setBounds(10, 20, 380, 80);
        pnlPrincipal.add(lblImagen);

        pnlDatos = new JPanel(new GridLayout(4, 1));
        pnlDatos.setBorder(new EmptyBorder(10, 40, 10, 40));
        pnlDatos.setBounds(10, 200, 380, 200);
        pnlDatos.setBackground(Color.white);
        pnlPrincipal.add(pnlDatos);

        lblUsuario = new JLabel("Ingrese usuario", JLabel.CENTER);
        pnlDatos.add(lblUsuario);

        eTxtUsuario = new JTextField();
        pnlDatos.add(eTxtUsuario);

        lblContrasenia = new JLabel("Ingrese contraseña", JLabel.CENTER);
        pnlDatos.add(lblContrasenia);

        eTxtContrasenia = new JPasswordField();
        pnlDatos.add(eTxtContrasenia);
        eTxtContrasenia.addActionListener((ae) -> {
            metodoIniciarSesion();
        });

        btnIniciar = new JButton("INICIAR SESIÓN");
        btnIniciar.setBackground(Variables.rojo);
        btnIniciar.setForeground(Color.white);
        btnIniciar.setBounds(60, 430, 280, 40);
        pnlPrincipal.add(btnIniciar);
        btnIniciar.addActionListener((ActionEvent ae) -> {
            metodoIniciarSesion();
        });

        JLabel desarrollado = new JLabel("Desarrollado por: Juan Pablo B.", JLabel.CENTER);
        desarrollado.setBounds(100, 550, 200, 50);
        desarrollado.setEnabled(false);
        pnlPrincipal.add(desarrollado);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public static void main(String[] args) {
        IniciarSesion iniciarSesion = new IniciarSesion(new Controlador());
        iniciarSesion.setVisible(true);
        iniciarSesion.setExtendedState(MAXIMIZED_BOTH);
    }

    private void metodoIniciarSesion() {
        if (ctrl.iniciarSesion(eTxtUsuario.getText().trim(), String.valueOf(eTxtContrasenia.getPassword()))) {
            interfazApp = new InterfazApp(ctrl);
            ctrl.conectarInterfaz(interfazApp);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Los datos son incorrectos.");
        }
    }
}
