package com.company;

import java.io.*;
import java.util.ArrayList;

public class Pliki {
    static File file = new File("wspolrzedne.txt");
    static FileWriter fw;
    public static void zapisz(String wejscie){

        try {
            fw = new FileWriter(file);
            fw.append(wejscie);
            fw.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Wyjatek zapisu");
        }
    }

    public static void wyczysc(){
        try {
            fw = new FileWriter(file);
            fw.write("");
            fw.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Wyjatek zapisu");
        }
    }

    public static String czytaj(String sciezka){
        String wyjscie = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(sciezka));
            String linia;
            StringBuilder sb= new StringBuilder();
            while((linia=br.readLine()) !=null){
                sb.append(linia).append("\n");
            }
            wyjscie = sb.toString();
            br.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Nie widze takiego pliku");
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Wyjatek IO");
        }
        return wyjscie;
    }

    public static ArrayList<Kolizja>  zwrocPunktyKolizji(String sciezka){
        ArrayList<Kolizja> listaK = new ArrayList<>();
        ArrayList<String> listaL = new ArrayList<>();
        StringBuilder linijka = new StringBuilder();


        for(int i=0; i< sciezka.length(); i++){
            char c = sciezka.charAt(i);
            if(c != '\n')
                linijka.append(c);
            else {
                listaL.add(linijka.toString());
                linijka.setLength(0);
            }
        }
        for(String s: listaL){
            String[] tab;
            tab = s.split(",");
            try{listaK.add(new Kolizja(Integer.parseInt(tab[0]), Integer.parseInt(tab[1]), Integer.parseInt(tab[2])));}
            catch (NumberFormatException e){
                System.out.println("Enter");
            }
        }
        return listaK;
    }

    static class Kolizja{
        int x,y,size;

        public Kolizja(int x, int y, int size){
            this.x=x;
            this.y=y;
            this.size=size;
        }
    }
}
