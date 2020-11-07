package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

enum Specjalizacja{
    MECHANIKA,
    BLACHARSTWO,
    ELEKTRONIKA
}


@Entity(name = "Serwisant")
public class Serwisant extends Pracownik {
    private long id;

    static List<Serwisant> serwisanci = new ArrayList<>();//ekstensja

    private Specjalizacja specjalizacja;

    private List<NaprawaPojazdu> naprawy = new ArrayList<>();



    public Serwisant(String imie,String nazwisko,String PESEL,String adresZamieszkania,String numerTel,Specjalizacja specjalizacja)throws Exception{
        super(imie, nazwisko, PESEL, adresZamieszkania, numerTel);
        this.specjalizacja = specjalizacja;

        serwisanci.add(this);

    }

    protected Serwisant(){ //for hibernate

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

    @Override
    @Transient
    public double getStawkaGodzinowa() {
        switch (specjalizacja){
            case MECHANIKA:
                return 27.10;
            case BLACHARSTWO:
                return 21.60;
            case ELEKTRONIKA:
                return 25.20;
        }
        return Pracownik.min_StawkaGodzinowa;
    }


    @Enumerated
    public Specjalizacja getSpecjalizacja() {
        return specjalizacja;
    }

    public void setSpecjalizacja(Specjalizacja specjalizacja) {
        this.specjalizacja = specjalizacja;
    }

    @Override
    public void wprowadzSzczegoly(String s) {

    }

    public void addNaprawa(NaprawaPojazdu np){
        if(!naprawy.contains(np)){
            naprawy.add(np);
        }
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public List<NaprawaPojazdu> getNaprawy() {
        return naprawy;
    }

    public void setNaprawy(List<NaprawaPojazdu> naprawy) {
        this.naprawy = naprawy;
    }
}
