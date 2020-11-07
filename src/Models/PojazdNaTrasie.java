package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity(name="PojazdNaTrasie")
public class PojazdNaTrasie {

    private long id;

    private double wykorzystanePaliwo;

    private LocalDate data;

    List<Kierowca> kierowcy = new ArrayList<>(2); //asocjacja zwykla

    static Map<Trasa,List<Pojazd>> trasa_pojazdy = new HashMap<>();

    static List<PojazdNaTrasie> pojazdyNaTrasie = new ArrayList<>();//ekstensja

    Trasa trasa;

    Pojazd pojazd;

    public PojazdNaTrasie(Pojazd p, Trasa t)throws Exception{
        if(!trasa_pojazdy.keySet().contains(t)){
            trasa_pojazdy.put(t,new ArrayList<>());
        }

        trasa = t;
        pojazd = p;

        trasa.addPojazdNaTrasie(this);
        pojazd.setPojazdNaTrasie(this);


        trasa_pojazdy.get(trasa).add(p);
        pojazdyNaTrasie.add(this);    //dodawanie do ekstensji
    }

    protected PojazdNaTrasie(){

    }

    public static void readExtent(List<PojazdNaTrasie> lista) {
        pojazdyNaTrasie = lista;
        for(PojazdNaTrasie pojazdNaTrasie:pojazdyNaTrasie){
            if(trasa_pojazdy.containsKey(pojazdNaTrasie.getTrasa())){
                trasa_pojazdy.get(pojazdNaTrasie.getTrasa()).add(pojazdNaTrasie.getPojazd());
            }
            else{
                List<Pojazd> tmp = new ArrayList<>();
                tmp.add(pojazdNaTrasie.getPojazd());
                trasa_pojazdy.put(pojazdNaTrasie.getTrasa(),tmp);
            }
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

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    public List<Kierowca> getKierowcy() {
        return kierowcy;
    }

    public void setKierowcy(List<Kierowca> kierowcy) {
        this.kierowcy = kierowcy;
    }
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    public Pojazd getPojazd() {
        return pojazd;
    }

    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
    }

    public void przydzielKierowce(Kierowca k)throws Exception{
        /*if(kierowcy.size() == 2){
            throw new Exception("Kierowcy juz przydzieloni!");
        }*/
        if(!kierowcy.contains(k)){
            kierowcy.add(k);

            k.setPojazdNaTrasie(this);
        }
    }

    public void removeKierowca(Kierowca k){
        kierowcy.remove(k);
    }

    public void usunPolaczenie(){
        trasa_pojazdy.get(trasa).remove(pojazd);

        pojazd.pojazdNaTrasie = null;
        pojazd.status = Status.WOLNY;

        trasa.removePojazdNaTrasie(this);


    }
    @ManyToOne(cascade = CascadeType.ALL)
    public Trasa getTrasa() {
        return trasa;
    }

    public void setTrasa(Trasa trasa) {
        this.trasa = trasa;
    }

    @Basic
    public double getWykorzystanePaliwo() {
        return wykorzystanePaliwo;
    }

    public void setWykorzystanePaliwo(double wykorzystanePaliwo) {
        this.wykorzystanePaliwo = wykorzystanePaliwo;
    }

    @Basic
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return pojazd.toString() + "\n";
    }
}
