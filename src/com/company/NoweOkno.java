package com.company;

import javax.swing.*;
import java.awt.*;

public class NoweOkno extends JFrame {

    public NoweOkno(){
        setTitle("Mapa zderzen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new PanelMapa());

        setPreferredSize(new Dimension(600, 480));
        setLocation(600, 0);
        pack();
        setVisible(false);
    }
}