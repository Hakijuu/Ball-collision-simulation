package com.company;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args){

        JFrame frame = new JFrame("Zderzenia sprężyste niecentralne");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Panel());

        frame.setPreferredSize(new Dimension(600,480));
        frame.pack();
        frame.setVisible(true);
    }
}