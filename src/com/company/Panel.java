package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import static com.company.Pliki.*;


public class Panel extends JPanel {

    private ArrayList<Kula> listaKul;
    private ArrayList<Kolizja> listaKolizji;
    private int size = 20;
    NoweOkno mapa = new NoweOkno();
    static String s;
    public Panel(){
        listaKul = new ArrayList<>();
        listaKolizji = new ArrayList<>();
        setBackground(Color.BLACK);

        addMouseListener(new Listener());
        addMouseMotionListener(new Listener());
        addMouseWheelListener(new Listener());

        //ms dla 30fps
        int DELAY = 33;
        Timer timer = new Timer(DELAY, new Listener());
        timer.start();
        Watek1 zapisz = new Watek1("zapisz");
        Watek2 odswiez = new Watek2("Odswiez",this);
        odswiez.start();
        zapisz.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        for (Kula k : listaKul) {
            Point2D center = new Point2D.Float(k.x , k.y);
            float radius = (float)k.size/2;
            Point2D focus = new Point2D.Float(600,400);
            float[] dist = {0.0f, 1.0f};
            Color[] colors = {Color.WHITE,k.kolor};
            RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);

            g2.setPaint(p);
            if(k.xc) g2.fillOval(k.x - k.size / 2, k.y - k.size / 2, k.size-k.size/4, k.size);
            else if(k.yc) g2.fillOval(k.x - k.size / 2, k.y - k.size / 2, k.size, k.size-k.size/4);
            else g2.fillOval(k.x - k.size / 2, k.y - k.size / 2, k.size, k.size);
        }
        //licznik kul
        g.setColor(Color.YELLOW);
        g.drawString(Integer.toString(listaKul.size()), 50, 50);

    }
    static String doZapisu(){
        return s;
    }

    class Listener implements MouseListener, MouseMotionListener, MouseWheelListener, ActionListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            listaKul.add(new Kula(mouseEvent.getX(), mouseEvent.getY(), size));
          //  repaint();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            mapa.setVisible(false);
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            if(listaKolizji.size()<5) Pliki.wyczysc();
            mapa.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            for (Kula k : listaKul) {
                k.update();
                k.c(k);
            }

           // if(listaKolizji.size() > 0 && (listaKolizji.size()/2)%5 == 0){
                StringBuilder sb = new StringBuilder();
                for (Kolizja kolizja : listaKolizji) {
                    sb.append(kolizja.x).append(",").append(kolizja.y).append(",").append(kolizja.size);
                    sb.append("\n");
          //      }
                s = sb.toString();

             //   Pliki.zapisz(s);
            }
          //  repaint();
        }



        @Override
        public void mouseDragged(MouseEvent mouseEvent) {
            listaKul.add(new Kula(mouseEvent.getX(), mouseEvent.getY(), size));
          //  repaint();
        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
            for (Kula k : listaKul) {
                if ((k.x - mouseEvent.getX()) * (k.x - mouseEvent.getX()) + (k.y - mouseEvent.getY()) * (k.y - mouseEvent.getY()) <= 0.5 * 0.5 * size * size) {
                    do {
                        k.xspeed = (int) (Math.random() * 5*2 -5);
                        k.yspeed = (int) (Math.random() * 5 -5);
                    } while (k.xspeed == 0 && k.yspeed == 0);
                    k.update();
                }
            }
           // repaint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
            if (mouseWheelEvent.getWheelRotation() > 0) size--;
            else size++;
        }
    }

    private class Kula {

        public int x, y, size, masa, xspeed, yspeed, time;
        public boolean xc = false, yc = false;
        public Color kolor;

        public Kula(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            masa = size * size * size;
            // składowe RGB
            kolor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());

            do {
                int MAX_SPEED = 5;
                xspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
                yspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
            } while (xspeed == 0 && yspeed == 0);

            // xspeed = 0? .....
        }

        //metoda kolizji
        public void c(Kula x) {

            for (Kula a : listaKul) {

                if (!a.equals(x)) {

                    int ax = a.x + a.size / 2;
                    int ay =  a.y + a.size / 2;
                    int bx = x.x + x.size / 2;
                    int by =  x.y + x.size / 2;

                    //SUMA PROMIENI R1 + R2
                    double promien = (a.size / 2.0 + x.size / 2.0);

                    //ODLEGŁOŚĆ PUNKTÓW NA PŁASZCZYŹNIE
                    double dystans = Math.sqrt(Math.pow(ax - bx, 2.0) + Math.pow(ay - by, 2.0));


                    if (dystans <= promien) {
                        if((x.xspeed!=0 || x.yspeed!=0) && (a.xspeed!=0 || a.yspeed!=0)){
                            listaKolizji.add(new Kolizja(ax, ay, a.size));
                            listaKolizji.add(new Kolizja(bx, by, x.size));}

                        double temp1 = x.xspeed, temp2 = x.yspeed;
                        x.xspeed = (x.xspeed * (x.masa - a.masa) + 2 * a.masa * a.xspeed) / (x.masa + a.masa);
                        x.yspeed = (x.yspeed * (masa - a.masa) + 2 * a.masa * a.yspeed) / (x.masa + a.masa);
                        a.xspeed = (int) ((a.xspeed * (a.masa - x.masa) + 2 * x.masa * temp1) / (x.masa + a.masa));
                        a.yspeed = (int) ((a.yspeed * (a.masa - x.masa) + 2 * x.masa * temp2) / (x.masa + a.masa));

                        update();
                    }
                }
            }
        }

        public void update() {
            time++;
            x += xspeed;
            y += yspeed;
            xc=false;
            yc=false;

            if (x - size / 2 <= 0 || x + (size / 2) >= getWidth()) {
                xspeed = -xspeed;
                xc=true;
            }

            if (y - size / 2 <= 0 || y + size / 2 >= getHeight()) {
                yspeed = -yspeed;
                yc=true;
            }

            if (time > 165) {
                if(time%3==0) yspeed++;
                if (y + size / 2 >= getHeight()) {
                    yspeed=0;
                    xspeed=0;
                    yc=false;
                    xc=false;
                    y=getHeight()-size/2;
                }
            }
        }
    }
}