package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(name="Trasa")
public class Trasa {
    private long id;

    public static List<Trasa> trasy = new ArrayList<>(); //ekstensja


    public List<PojazdNaTrasie> pojazdy = new ArrayList<>();

    private String kierunek;

    private String kierunekZwrotny;

    private boolean czyLiniaNocna;

    private int numer_trasy; //unique

    public static Map<Integer,Trasa> numery = new HashMap<>(); //dla unique

    private List<PrzystanekNaTrasie> przystanki = new ArrayList<>();

    private LocalTime okresKursowaniaOd,okresKursowaniaDo; //zalezy od linii nocnej







    public Trasa(String kierunek,String kierunekZwrotny,int numer_trasy,boolean czyLiniaNocna)throws Exception{
        this.kierunek = kierunek;
        this.kierunekZwrotny = kierunekZwrotny;
        this.czyLiniaNocna = czyLiniaNocna;
        setOkresKursowania();
        setNumer(numer_trasy);

        trasy.add(this);



    }

    protected Trasa(){

    }

    private void setOkresKursowania(){
        if(czyLiniaNocna){
            okresKursowaniaOd = LocalTime.of(23,30);
            okresKursowaniaDo = LocalTime.of(5,0);
        }
        else{
            okresKursowaniaOd = LocalTime.of(5,30);
            okresKursowaniaDo = LocalTime.of(23,0);
        }
    }

    public void przydzielPojazd(Pojazd p)throws Exception{
        PojazdNaTrasie pojazdNaTrasie = new PojazdNaTrasie(p,this);
    }

    public void addPojazdNaTrasie(PojazdNaTrasie pojazdNaTrasie){
        if(!pojazdy.contains(pojazdNaTrasie)){
            pojazdy.add(pojazdNaTrasie);
        }

    }

    public void dodajPrzystanekDoTrasy(Przystanek p){

    }

    public void addPrzystanekNaTrasie(PrzystanekNaTrasie przystanekNaTrasie){
        if(!przystanki.contains(przystanekNaTrasie)){
            przystanki.add(przystanekNaTrasie);
        }
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<PojazdNaTrasie> getPojazdy() {
        return pojazdy;
    }


    public void setPojazdy(List<PojazdNaTrasie> pojazdy) {
        this.pojazdy = pojazdy;
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
    public String getKierunek(){
        return kierunek;
    }

    public void setKierunek(String kierunek){
        this.kierunek = kierunek;
    }

    @Basic
    public String getKierunekZwrotny(){
        return kierunekZwrotny;
    }

    public void setKierunekZwrotny(String kierunekZwrotny){
        this.kierunekZwrotny = kierunekZwrotny;
    }

    @Basic
    public int getNumer_trasy() {
        return numer_trasy;
    }

    public void setNumer_trasy(int numer_trasy) throws Exception {
        this.numer_trasy = numer_trasy;
    }

    public void setNumer(int numer)throws Exception{
        boolean juzJest = numery.containsKey(numer);
        if(juzJest){
            throw new Exception("Trasa z takim numerem juz istnije");
        }
        else {
            this.numer_trasy = numer;

            numery.put(numer,this);
        }
    }



    public void removePojazdNaTrasie(PojazdNaTrasie pojazdNaTrasie){
        pojazdy.remove(pojazdNaTrasie);
    }

    public void removePrzystanekNaTrasie(PrzystanekNaTrasie przystanekNaTrasie){
        przystanki.remove(przystanekNaTrasie);
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public List<PrzystanekNaTrasie> getPrzystanki() {
        return przystanki;
    }

    public void setPrzystanki(List<PrzystanekNaTrasie> przystanki) {

        this.przystanki = przystanki;
    }

    @Basic
    public boolean isCzyLiniaNocna() {
        return czyLiniaNocna;
    }

    public void setCzyLiniaNocna(boolean czyLiniaNocna) {
        this.czyLiniaNocna = czyLiniaNocna;
    }

    @Column
    public LocalTime getOkresKursowaniaOd() {
        return okresKursowaniaOd;
    }

    public void setOkresKursowaniaOd(LocalTime okresKursowaniaOd) {
        this.okresKursowaniaOd = okresKursowaniaOd;
    }

    @Column
    public LocalTime getOkresKursowaniaDo() {
        return okresKursowaniaDo;
    }

    public void setOkresKursowaniaDo(LocalTime okresKursowaniaDo) {
        this.okresKursowaniaDo = okresKursowaniaDo;
    }

    @Override
    public String toString() {
        String res = "Trasa " + numer_trasy + "\n" + kierunek + " - " + kierunekZwrotny + "\n";
        /*for(PojazdNaTrasie pojazdNaTrasie: pojazdy){
            res += pojazdNaTrasie.pojazd.toString() + "\n";
            //res += "Kierowcy: " + pojazdNaTrasie.kierowcy.toString() + "\n";
        }*/
        return res;
    }



    public static void readExtent(List<Trasa> tras){
        trasy = tras;

        for(Trasa t:trasy){
            numery.put(t.getNumer_trasy(),t);
        }

    }


}