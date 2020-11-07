package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Bilet")
public class Bilet {

    private long id;

    static List<Bilet> bilety = new ArrayList<>(); //ekstensja

    private double cenaZwykla;
    private int okresWaznosci;

    private boolean czyJednorazowy;

    private int procentZnizki = 0;

    private Pasazer pasazer;


    private Bilet(double cenaZwykla,int okresWaznosci,boolean czyJednorazowy,Pasazer pasazer){
        this.cenaZwykla = cenaZwykla;
        this.okresWaznosci = okresWaznosci;
        this.czyJednorazowy = czyJednorazowy;

        this.pasazer = pasazer;

        obliczProcentZnizki();

        bilety.add(this);


    }

    public static void readExtent(List<Bilet> lista) {
        bilety = lista;
    }

    public void obliczProcentZnizki(){
        if(pasazer.getUlga() != null) {
            switch (pasazer.getUlga()) {
                case EMERYT:
                    procentZnizki = 80;
                    break;
                case DZIECKO:
                    procentZnizki = 100;
                    break;
                case STUDENT:
                    procentZnizki = 51;
                    break;
                case NIEPELNOSPRAWNOSC:
                    procentZnizki = 95;
                    break;
                 default:
                     procentZnizki = 50;
                     break;
            }
        }
    }


    public static Bilet createBilet(Pasazer pasazer,double cenaZwykla,int okresWaznosci,boolean czyJednorazowy) throws Exception{
        if(pasazer != null){
            Bilet bilet = new Bilet(cenaZwykla, okresWaznosci, czyJednorazowy, pasazer);

            return bilet;
        }
        else{
            throw new Exception("Pasazer = null");
        }

    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment",strategy = "increment")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    public double getCenaZwykla() {
        return cenaZwykla;
    }

    public void setCenaZwykla(double cenaZwykla) {
        this.cenaZwykla = cenaZwykla;
    }

    @Basic
    public int getOkresWaznosci() {
        return okresWaznosci;
    }

    public void setOkresWaznosci(int okresWaznosci) {
        this.okresWaznosci = okresWaznosci;
    }

    @Basic
    public boolean isCzyJednorazowy() {
        return czyJednorazowy;
    }

    public void setCzyJednorazowy(boolean czyJednorazowy) {
        this.czyJednorazowy = czyJednorazowy;
    }

    @Basic
    public int getProcentZnizki() {
        return procentZnizki;
    }

    public void setProcentZnizki(int procentZnizki) {
        this.procentZnizki = procentZnizki;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Pasazer getPasazer() {
        return pasazer;
    }

    public void setPasazer(Pasazer pasazer) {
        this.pasazer = pasazer;
    }
}
