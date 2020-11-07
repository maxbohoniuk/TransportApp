package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(name = "Przystanek")
public class Przystanek {

    private long id;

    public static List<Przystanek> przystanki = new ArrayList<>(); //ekstensja


    private List<PrzystanekNaTrasie> trasy = new ArrayList<>();

    private String nazwa;
    private String adres;


    private int numer; //unikalny w ramach jednej nazwy

    public static Map<String,Przystanek> numery = new HashMap<>(); //dla unique

    public Przystanek(String nazwa,String adres,int numer) throws Exception{
        this.nazwa = nazwa;
        this.adres = adres;

        this.setNumerPrzystanku(numer); //unique test

        przystanki.add(this);
    }


    protected Przystanek(){

    }

    public void addPrzystanekNaTrasie(PrzystanekNaTrasie przystanekNaTrasie){
        if(!trasy.contains(przystanekNaTrasie)){
            trasy.add(przystanekNaTrasie);
        }
    }

    public void removePrzystanekNaTrasie(PrzystanekNaTrasie przystanekNaTrasie){
        trasy.remove(przystanekNaTrasie);
    }
    @Override
    public String toString() {
        return nazwa + " " + numer;
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
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    @Basic
    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
    @Basic
    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }


    public void setNumerPrzystanku(int numer) throws Exception {
        boolean juzJest = numery.containsKey(nazwa + " " + numer);

        if(juzJest){
            throw new Exception("Numer przystanku nie jest unikalny w ramach jednej nazwy");
        }
        else {
            this.numer = numer;

            numery.put(nazwa + " " + numer,this);
        }
    }


    @ManyToMany(cascade = CascadeType.ALL)
    public List<PrzystanekNaTrasie> getTrasy() {
        return trasy;
    }

    public void setTrasy(List<PrzystanekNaTrasie> trasy) {
        this.trasy = trasy;
    }

    public static void readExtent(List<Przystanek> list){
        przystanki = list;

        for(Przystanek p:list){
            numery.put((p.nazwa + " " + p.numer),p);
        }
    }

}
