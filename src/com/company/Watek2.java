package com.company;

import javafx.scene.layout.Pane;

public class Watek2 implements Runnable {

    private Thread t;
    private String name;
    private Panel p;

    public Watek2(String name, Panel p) {
        this.name = name;
        this.p=p;
        System.out.println("Tworze wlaśnie wątek: "+ name);
    }

    @Override
    public void run() {
        for(;;){
            System.out.println("odswiez");
        p.repaint();
       }

    }
    public void start () {
        System.out.println("Rozpoczynam " +  name );
        if (t == null) {
            t = new Thread (this, name);
            t.start ();
        }
    }
}