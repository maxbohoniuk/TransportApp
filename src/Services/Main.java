package Services;

import Models.*;
import Views.*;

import javax.swing.*;
import java.util.List;

public class Main {

    public static DbService dbService = DbService.getInstance();
    public static Trasa t;
    public static PojazdNaTrasie p;
    public static List<Kierowca> kierowcy;
    public static TrasaGUI trasaGUI;




    public static void main(String[] args) {
        dbService.readAllData();


        trasaGUI = new TrasaGUI(Trasa.trasy);



        SwingUtilities.invokeLater(trasaGUI);


    }

    public static void trasaWybrana(Trasa trasa){
        t = trasa;

        if(p == null) {
            SwingUtilities.invokeLater(new PojazdGUI(Pojazd.pojazdy));
        }
        else{
            SwingUtilities.invokeLater(new PotwierdzGUI(t,p));
        }

    }

    public static void pojazdWybrany(PojazdNaTrasie pojazdNaTrasie){
        p = pojazdNaTrasie;

        if(p.getKierowcy().size() == 0) {

            SwingUtilities.invokeLater(new KierowcaGUI(Kierowca.kierowcy));
        }
        else{
            SwingUtilities.invokeLater(new PotwierdzGUI(t,p));
        }

    }

    public static void kierowcyWybrani(List<Kierowca> kierowcy){
        Main.kierowcy = kierowcy;

        //p.setKierowcy(kierowcy);
        for(Kierowca k:kierowcy){
            try {
                k.setPojazdNaTrasie(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(new PotwierdzGUI(t,p));
    }

    public static void potwierdzenieNadane(){
        System.out.println(p);
        System.out.println(Pojazd.pojazdy);
        dbService.addPojazdNaTrasie(p);

        dbService.close();
    }








}
