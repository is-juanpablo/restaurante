package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Juan Pablo
 */
public class PanelBotonesEstadistica extends JPanel {

    private final JButton btnHoy, btnSemana, btnMes, btnSemestre;
    private final Controlador ctrl;

    public PanelBotonesEstadistica(Controlador ctrl) {
        this.ctrl = ctrl;
        setLayout(new GridLayout(1, 4));

        btnHoy = new JButton("HOY");
        btnHoy.setBackground(Color.white);
        btnHoy.setForeground(Color.black);
        add(btnHoy);

        btnSemana = new JButton("SEMANA");
        btnSemana.setBackground(Color.white);
        add(btnSemana);

        btnMes = new JButton("MES");
        btnMes.setBackground(Color.white);
        add(btnMes);

        btnSemestre = new JButton("SEMESTRE");
        btnSemestre.setBackground(Color.white);
        add(btnSemestre);

        MetodoEvento metodoEvento = new MetodoEvento();

        btnHoy.addActionListener(metodoEvento);
        btnSemana.addActionListener(metodoEvento);
        btnMes.addActionListener(metodoEvento);
        btnSemestre.addActionListener(metodoEvento);
    }

    private class MetodoEvento implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            int conts = -1;
            if (ae.getSource() == btnHoy) {
                conts = 0;
            } else if (ae.getSource() == btnSemana) {
                conts = 1;
            } else if (ae.getSource() == btnMes) {
                conts = 2;
            } else if (ae.getSource() == btnSemestre) {
                conts = 3;
            }
            ctrl.conectarPanelBoton(conts);
        }

    }
}
