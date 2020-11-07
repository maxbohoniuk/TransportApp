package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Entity(name = "Pojazd")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pojazd implements Serializable {

    private long id;

    public static List<Pojazd> pojazdy = new ArrayList<>(); // ekstensja


    protected int rok_produkcji;

    protected PojazdTyp typ;



    protected int numer; //unique

    @Transient
    private static Map<Integer,Pojazd> numery = new HashMap<>();



    protected String adres_zajezdni;

    protected boolean niskopodlogowy;


    protected Status status;

    protected LocalDate data_ostatniego_przegladu; //atr. zlozony

    protected PojazdNaTrasie pojazdNaTrasie;

    protected List<NaprawaPojazdu> naprawy = new ArrayList<>();




    protected List<Lokalizacja> lokalizacja = new ArrayList<>(); //atr. powtarzalny  (latitude longitude) update co 2min.

    public Pojazd(int rok_produkcji,String adres_zajezdni,LocalDate data_ostatniego_przegladu,boolean niskopodlogowy){
        this.rok_produkcji = rok_produkcji;
        this.adres_zajezdni = adres_zajezdni;
        this.status = Status.WOLNY;
        this.data_ostatniego_przegladu = data_ostatniego_przegladu;
        this.niskopodlogowy = niskopodlogowy;

        randomNumer(); //random numer set
    }

    protected Pojazd(){ //hibernate

    }

    public static void readExtent(List<Pojazd> lista) {
        pojazdy = lista;
        for(Pojazd p : pojazdy){
            numery.put(p.getNumer(),p);
        }

    }

    @Basic
    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    //generuje przypisuje losowy unikalny numer pojazdu
    public void randomNumer(){
        int n = (int)(Math.random()*100000);
        while(numery.containsKey(n)){
            n = (int)(Math.random()*100000);
        }
        setNumer(n);
    }

    public void setStatus(Status s){
        status = s;
    }

    public void setData_ostatniego_przegladu(LocalDate date){
        data_ostatniego_przegladu = date;
    }

    public long ileDniDoPrzegladu(){
        LocalDate today = LocalDate.now();
        LocalDate data_przegladu = data_ostatniego_przegladu.plusMonths(1);
        Duration dur = Duration.between(today.atStartOfDay(),data_przegladu.atStartOfDay());
        return dur.toDays();
    }

    public void dodajLokalizacje(double lat,double lon){
        if(lokalizacja.size() == 60){
            lokalizacja.clear();
        }

        lokalizacja.add(new Lokalizacja(lon,lat));
    }

    public Lokalizacja pobierz_lokalizacje(){
        return lokalizacja.get(lokalizacja.size()-1);         //PRZECIAZENIE

    }

    public Lokalizacja pobierz_lokalizacje(int minuta)throws Exception{
        if(minuta % 2 != 0 ){
            throw new  Exception("Niepoprawne dane");
        }
        else {
            return lokalizacja.get((minuta/2)-1);
        }

    }


    public void setPojazdNaTrasie(PojazdNaTrasie pojazdNaTrasie){
        if(this.pojazdNaTrasie != null){
            this.pojazdNaTrasie.usunPolaczenie();
        }
        this.pojazdNaTrasie = pojazdNaTrasie;
        status = Status.KURSUJE;
    }

    public void dodajNaprawe(NaprawaPojazdu np){
        if(!naprawy.contains(np)) {
            naprawy.add(np);
            setStatus(Status.W_TRAKCIE_NAPRAWY);
        }
    }










    @Override
    public String toString() {
        return /*"Pojazd #"+pojazdy.indexOf(this)+"\nStatus: " + status + "\nRok produkcji: " + rok_produkcji
                + "\nData ostatniego przegladu: " + data_ostatniego_przegladu
                + "\nAdres zajezdni: " + adres_zajezdni
                + "\nNiskopodlogowy: " + (niskopodlogowy ? "TAK" : "NIE")
                + ( (lokalizacja.size() != 0) ? ("\n" + pobierz_lokalizacje()) : "" );*/
        typ.typ+" | "+status + " | " + rok_produkcji + " | " + adres_zajezdni + " | " + (niskopodlogowy ? "Niskopodlogowy" : "NIE Niskopodlogowy");
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

    @ManyToOne(cascade = CascadeType.ALL)
    public PojazdNaTrasie getPojazdNaTrasie() {
        return pojazdNaTrasie;
    }

    @Basic
    public int getRok_produkcji() {
        return rok_produkcji;
    }

    public void setRok_produkcji(int rok_produkcji) {
        this.rok_produkcji = rok_produkcji;
    }

    @Basic
    public String getAdres_zajezdni() {
        return adres_zajezdni;
    }

    public void setAdres_zajezdni(String adres_zajezdni) {
        this.adres_zajezdni = adres_zajezdni;
    }

    @Basic
    public boolean isNiskopodlogowy() {
        return niskopodlogowy;
    }

    public void setNiskopodlogowy(boolean niskopodlogowy) {
        this.niskopodlogowy = niskopodlogowy;
    }

    @Enumerated
    public Status getStatus() {
        return status;
    }

    @Basic
    public LocalDate getData_ostatniego_przegladu() {
        return data_ostatniego_przegladu;
    }

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    public List<Lokalizacja> getLokalizacja() {
        return lokalizacja;
    }

    public void setLokalizacja(List<Lokalizacja> lokalizacja) {
        this.lokalizacja = lokalizacja;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<NaprawaPojazdu> getNaprawy() {
        return naprawy;
    }

    public void setNaprawy(List<NaprawaPojazdu> naprawy) {
        this.naprawy = naprawy;
    }

    @Enumerated
    public PojazdTyp getTyp() {
        return typ;
    }

    public void setTyp(PojazdTyp typ) {
        this.typ = typ;
    }
}
