package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(name="PrzystanekNaTrasie")
public class PrzystanekNaTrasie {


    private long id;

    static Map<Trasa, List<Przystanek>> trasa_przystanki = new HashMap<>();
    static List<PrzystanekNaTrasie> przystankiNaTrasie = new ArrayList<>();//ekstensja

    Trasa trasa;

    Przystanek przystanek;



    boolean czyNaZadanie;
    List<LocalTime> czasPrzyjazdu;


    public PrzystanekNaTrasie(Przystanek przystanek,Trasa trasa)throws Exception{
        if(!trasa_przystanki.containsKey(trasa)){
            List<Przystanek> tmp = new ArrayList<>();
            tmp.add(przystanek);
            trasa_przystanki.put(trasa,tmp);
        }
        else{
            if(trasa_przystanki.get(trasa).contains(przystanek)){
                throw new Exception("Przystanek już dodany do trasy!");
            }
            else{
                trasa_przystanki.get(trasa).add(przystanek);
            }
        }

        this.trasa = trasa;
        this.przystanek = przystanek;

        trasa.addPrzystanekNaTrasie(this);
        przystanek.addPrzystanekNaTrasie(this);

        przystankiNaTrasie.add(this);


    }

    protected PrzystanekNaTrasie(){

    }

    public static void readExtent(List<PrzystanekNaTrasie> lista) {
        przystankiNaTrasie = lista;
        for(PrzystanekNaTrasie przystanekNaTrasie:przystankiNaTrasie){
            if(trasa_przystanki.containsKey(przystanekNaTrasie.getTrasa())){
                trasa_przystanki.get(przystanekNaTrasie.getTrasa()).add(przystanekNaTrasie.getPrzystanek());
            }
            else{
                List<Przystanek> tmp = new ArrayList<>();
                tmp.add(przystanekNaTrasie.getPrzystanek());
                trasa_przystanki.put(przystanekNaTrasie.getTrasa(),tmp);
            }
        }
    }

    public void usunPolaczenie(){
        trasa_przystanki.get(trasa).remove(przystanek);

        przystanek.removePrzystanekNaTrasie(this);

        trasa.removePrzystanekNaTrasie(this);
    }

    public void setCzyNaZadanie(boolean b){
        this.czyNaZadanie = b;
    }

    @Basic
    public boolean isCzyNaZadanie() {
        return czyNaZadanie;
    }

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    public Trasa getTrasa() {
        return trasa;
    }

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    public Przystanek getPrzystanek() {
        return przystanek;
    }

    @ElementCollection
    public List<LocalTime> getCzasPrzyjazdu() {
        return czasPrzyjazdu;
    }

    public void setCzasPrzyjazdu(List<LocalTime> czasPrzyjazdu) {
        this.czasPrzyjazdu = czasPrzyjazdu;
    }

    public void setTrasa(Trasa trasa) {
        this.trasa = trasa;
    }

    public void setPrzystanek(Przystanek przystanek) {
        this.przystanek = przystanek;
    }

    public void addCzasPrzyjazdu(int godzina,int minuta) {
        this.czasPrzyjazdu.add(LocalTime.of(godzina,minuta));
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






}
