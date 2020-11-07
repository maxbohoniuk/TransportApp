package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Autobus")
public class Autobus extends Pojazd {

    private long id;

    private static List<Autobus> autobusy = new ArrayList<>(); //ekstensja


    private String numer_rejestracyjny;

    private int ilosc_miejsc;

    public Autobus(int rok_produkcji, String adres_zajezdni, LocalDate data_ostatniego_przegladu, boolean niskopodlogowy, String numer_rejestracyjny, int ilosc_miejsc){
        super(rok_produkcji,adres_zajezdni,data_ostatniego_przegladu,niskopodlogowy);
        this.numer_rejestracyjny = numer_rejestracyjny;
        this.ilosc_miejsc = ilosc_miejsc;

        autobusy.add(this);

        setTyp(PojazdTyp.Autobus);




    }

    public Autobus(){

    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment",strategy = "increment")
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Basic
    public String getNumer_rejestracyjny() {
        return numer_rejestracyjny;
    }

    public void setNumer_rejestracyjny(String numer_rejestracyjny) {
        this.numer_rejestracyjny = numer_rejestracyjny;
    }
    @Basic
    public int getIlosc_miejsc() {
        return ilosc_miejsc;
    }

    public void setIlosc_miejsc(int ilosc_miejsc) {
        this.ilosc_miejsc = ilosc_miejsc;
    }

    public static void readExtentAutobus(List<Autobus> list){
        autobusy = list;
    }
}
