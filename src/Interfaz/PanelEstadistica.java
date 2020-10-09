package Interfaz;

import Controlador.Controlador;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;
import recursos.Variables;
import models.Grafico;

/**
 *
 * @author Juan Pablo
 */
public class PanelEstadistica extends JPanel {

    private final Controlador ctrl;
    private final JPanel pnlBotones, pnlGraficosIndividuales, pnlGraficos;
    private final CardLayout cardLayout;

    public PanelEstadistica(Controlador ctrl) {
        this.ctrl = ctrl;
        setBackground(Color.white);
        setLayout(null);

        pnlBotones = new PanelBotonesEstadistica(ctrl);
        pnlBotones.setBounds(0, 0, Variables.width, 50);
        add(pnlBotones);

        pnlGraficos = new JPanel(new CardLayout());
        pnlGraficos.setBounds(0, 50, Variables.width, Variables.heigth - 130);
        add(pnlGraficos);

        pnlGraficosIndividuales = new JPanel(new GridLayout(2, 2));
        //pnlGraficosIndividuales.setBounds(0, 50, Variables.width, Variables.heigth - 130);
        pnlGraficosIndividuales.setBackground(Color.white);
        pnlGraficos.add(pnlGraficosIndividuales);

        /*
        Grafico grafico = new Grafico();
        grafico.setNombre("Mapa ");
        grafico.setValorX("Valor X");
        grafico.setValorY("Valor Y");
        HashMap<String, Integer> mapa = new HashMap<>();

        for (int i = 0; i < 4; i++) {
            int valor = (int) (Math.random() * (1 - 9) + 9);
            String valorString = String.valueOf((char) (Math.random() * (65 - 122) + 122));
            mapa.put(valorString, valor);
        }

        grafico.setMapa(mapa);
         */
        //(int) (Math.random() * (0 - 3) + 3)
        ArrayList<Grafico> grafico = ctrl.consultaGrafico(0);
        for (int i = 0; i < grafico.size(); i++) {
            pnlGraficosIndividuales.add(new PanelGraficoIndividual(i, true, grafico.get(i)));
        }

        cardLayout = (CardLayout) pnlGraficos.getLayout();
        cardLayout.show(pnlGraficos, "alta");
        SwingUtilities.updateComponentTreeUI(pnlGraficos);
        this.repaint();

        ctrl.conectarPanelEstadistica(this);
    }

    public void metodoBotones(int j) {
        pnlGraficos.removeAll();
        pnlGraficosIndividuales.removeAll();
        ArrayList<Grafico> grafico = ctrl.consultaGrafico(j);
        for (int i = 0; i < grafico.size(); i++) {
            pnlGraficosIndividuales.add(new PanelGraficoIndividual(i, true, grafico.get(i)));
        }
        pnlGraficos.add(pnlGraficosIndividuales, "alta");
        cardLayout.show(pnlGraficos, "alta");
        SwingUtilities.updateComponentTreeUI(pnlGraficos);
        this.repaint();
    }
}
