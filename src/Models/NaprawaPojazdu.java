package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

enum StatusNaprawy{
    PRZYJETA,
    W_TRAKCIE_REALIZACJI,
    ZREALIZOWANA
}

@Entity(name = "NaprawaPojazdu")
public class NaprawaPojazdu {

    private long id;

    private static List<NaprawaPojazdu> naprawy = new ArrayList<>(); //ekstensja

    private StatusNaprawy statusNaprawy;
    private Pojazd pojazd;

    private String opis;

    private List<Serwisant> serwisanci = new ArrayList<>();



    private NaprawaPojazdu(Pojazd pojazd,StatusNaprawy statusNaprawy,List<Serwisant> serwisanci){
        this.pojazd = pojazd;
        this.statusNaprawy = statusNaprawy;

        this.serwisanci.addAll(serwisanci);
    }

    public static NaprawaPojazdu createNaprawaPojazdu(Pojazd pojazd,StatusNaprawy statusNaprawy,List<Serwisant> serwisanci) throws Exception{
        if(pojazd == null){
            throw new Exception("Pojazd = null");
        }
        if(serwisanci.size() < 1){
            throw new Exception("Musi być co najmniej 1 serwisant");
        }
        NaprawaPojazdu np = new NaprawaPojazdu(pojazd,statusNaprawy,serwisanci);

        pojazd.dodajNaprawe(np);

        naprawy.add(np);

        return np;
    }

    public void addSerwisant(Serwisant serwisant){
        if(!serwisanci.contains(serwisant)){
            serwisanci.add(serwisant);

            serwisant.addNaprawa(this);

        }
    }

    public void przydzielSerwisantow(Serwisant... serwisants){
        for(Serwisant s:serwisants){
            addSerwisant(s);
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

    @Enumerated
    public StatusNaprawy getStatusNaprawy() {
        return statusNaprawy;
    }

    public void setStatusNaprawy(StatusNaprawy statusNaprawy) {
        this.statusNaprawy = statusNaprawy;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Pojazd getPojazd() {
        return pojazd;
    }

    public void setPojazd(Pojazd pojazd) {
        this.pojazd = pojazd;
    }

    @Basic
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Serwisant> getSerwisanci() {
        return serwisanci;
    }

    public void setSerwisanci(List<Serwisant> serwisanci) {
        this.serwisanci = serwisanci;
    }

    @Override
    public String toString() {
        return "Naprawa pojazdu " + pojazd.getNumer();
    }
}
