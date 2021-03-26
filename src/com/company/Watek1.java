package com.company;

public class Watek1 implements Runnable {

    private Thread t;
    private String name;

    public Watek1(String name) {
        this.name = name;
        System.out.println("Tworze wlaśnie wątek: "+ name);
    }

    @Override
    public void run() {
        for(;;) {
            System.out.println("zapisz");
            Pliki.zapisz(Panel.doZapisu());
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