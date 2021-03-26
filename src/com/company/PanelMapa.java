package com.company;
import static com.company.Pliki.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class PanelMapa extends JPanel {
    ArrayList<Kolizja> listaDoRysowania;
    public PanelMapa(){
        setBackground(Color.BLACK);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        listaDoRysowania=zwrocPunktyKolizji(czytaj("wspolrzedne.txt"));


        for (Kolizja kolizja: listaDoRysowania) {
            g.setColor(Color.white);
            g.drawOval (kolizja.x - kolizja.size / 2, kolizja.y - kolizja.size/ 2, kolizja.size, kolizja.size);
            //licznik kolizji
        }
        g.setColor(Color.white);
        g.drawString(Integer.toString(listaDoRysowania.size()/2), 50, 50);
    }
}