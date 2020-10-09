package Interfaz;

import Controlador.Controlador;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Juan Pablo
 */
public class PanelMesas extends JPanel {

    private final JPanel pnlPiso1, pnlPiso2;
    private final Controlador ctrl;
    public PanelMesas(Controlador ctrl) {
        this.ctrl = ctrl;
        setBackground(Color.red);
        setLayout(new GridLayout(1, 2));

        pnlPiso1 = new JPanel();
        pnlPiso1.setLayout(new GridLayout(5, 5));
        pnlPiso1.setBorder(new CompoundBorder((new EmptyBorder(10, 10, 30, 10)), new TitledBorder("Piso 1")));
        pnlPiso1.setBackground(Color.white);
        add(pnlPiso1);

        pnlPiso2 = new JPanel();
        pnlPiso2.setLayout(new GridLayout(5, 5));
        pnlPiso2.setBorder(new CompoundBorder((new EmptyBorder(10, 10, 30, 10)), new TitledBorder("Piso 2")));
        pnlPiso2.setBackground(Color.white);
        add(pnlPiso2);

        for (int i = 0; i < 25; i++) {
            JLabel lbl = new JLabel();
            if ((i / 4) % 2 == 0) {
                /*
                lbl = new JLabel(new ImageIcon(), JLabel.CENTER);
                ImageIcon iconCheck = new ImageIcon("src/recursos/mesaPiso.jpg");   
                Image image = iconCheck.getImage();
                Image newimg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
                iconCheck = new ImageIcon(newimg);
                lbl.setIcon(iconCheck);
                 */
                pnlPiso1.add(new PanelMesaGeneral(i, "mesaPisoVacia", Color.GREEN));
            } else {
                pnlPiso1.add(lbl);
            }
            lbl = new JLabel();
            if ((i / 5) % 2 == 0) {
                pnlPiso2.add(new PanelMesaGeneral(i, "mesaPisoOcupada", Color.RED));
            } else {
                pnlPiso2.add(lbl);
            }
        }
    }
}

class PanelMesaGeneral extends JPanel {

    public PanelMesaGeneral(int numeroMesa, String path, Color color) {

        setLayout(new GridBagLayout());
        setBackground(Color.white);

        JLabel lblImagen = new JLabel(new ImageIcon(), JLabel.CENTER);
        ImageIcon iconCheck = new ImageIcon("src/recursos/" + path + ".jpg");
        Image image = iconCheck.getImage();
        Image newimg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
        iconCheck = new ImageIcon(newimg);
        lblImagen.setIcon(iconCheck);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 3;
        constraints.weighty = 5.0;
        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(lblImagen, constraints);

        JPanel pnlDatosMesa = new JPanel();
        pnlDatosMesa.setLayout(new FlowLayout());
        pnlDatosMesa.setBackground(Color.white);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 3;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        add(pnlDatosMesa, constraints);

        JLabel lblNumeroMesa = new JLabel(String.valueOf(numeroMesa));
        pnlDatosMesa.add(lblNumeroMesa);

        JLabel lblNumeroMesa1 = new JLabel("ESTADO", JLabel.CENTER);
        lblNumeroMesa1.setForeground(color.darker());
        pnlDatosMesa.add(lblNumeroMesa1);
    }
}
