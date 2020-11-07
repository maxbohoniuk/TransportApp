package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Tramwaj")
public class Tramwaj extends Pojazd {


    private long id;

    private boolean czyMaKlimatyzacje;

    private static List<Tramwaj> tramwaje = new ArrayList<>(); //ekstensja

    public Tramwaj(int rok_produkcji, String adres_zajezdni, LocalDate data_ostatniego_przegladu) {
        super(rok_produkcji, adres_zajezdni, data_ostatniego_przegladu, false);
        tramwaje.add(this);

        setTyp(PojazdTyp.Tramwaj);
    }

    public Tramwaj(){

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
    public boolean isCzyMaKlimatyzacje() {
        return czyMaKlimatyzacje;
    }

    public void setCzyMaKlimatyzacje(boolean czyMaKlimatyzacje) {
        this.czyMaKlimatyzacje = czyMaKlimatyzacje;
    }

    public static void readExtentTramwaj(List<Tramwaj> tramwaje){
        tramwaje = tramwaje;
    }
}
