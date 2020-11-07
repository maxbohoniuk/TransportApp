package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Metro")
public class Metro extends Pojazd {

    private long id;

    private static List<Metro> metros = new ArrayList<>(); //ekstensja

    private List<Integer> lokalizacja_miejsc_rowerowych = null;  //atr. opcjonalny
    private static int ilosc_wagonow = 5; //atr. klasowy

    public Metro(int rok_produkcji, String adres_zajezdni, LocalDate data_ostatniego_przegladu) {
        super(rok_produkcji, adres_zajezdni, data_ostatniego_przegladu, true);
        metros.add(this);

        setTyp(PojazdTyp.Metro);
    }

    public Metro(){

    }

    public static void setIlosc_wagonow(int ilosc){
        ilosc_wagonow = ilosc;
    }

    public static void readExtentMetro(List<Metro> lista) {
        metros = lista;
    }

    public void dodaj_miejsce_rowerowe(int numer_wagonu) throws Exception{
        if(numer_wagonu <= ilosc_wagonow){
            if(lokalizacja_miejsc_rowerowych == null){
                lokalizacja_miejsc_rowerowych = new ArrayList<>();
            }
            lokalizacja_miejsc_rowerowych.add(numer_wagonu);
        }
        else{
            throw new Exception("Niepoprawna liczba!");
        }
    }

    @Override
    public long ileDniDoPrzegladu() { //przesloniecie
        LocalDate today = LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth());
        int year = today.getYear();

        LocalDate data_przegladu;

        if( (year - rok_produkcji) > 5){ //jeśli metro ma więcej niż 5 lat to co 2 tygodnie
            data_przegladu = data_ostatniego_przegladu.plusWeeks(2);
        }
        else{
            data_przegladu = data_ostatniego_przegladu.plusMonths(1);
        }

        Duration dur = Duration.between(today.atStartOfDay(),data_przegladu.atStartOfDay());
        return dur.toDays();
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
    @ElementCollection
    public List<Integer> getLokalizacja_miejsc_rowerowych() {
        return lokalizacja_miejsc_rowerowych;
    }

    public void setLokalizacja_miejsc_rowerowych(List<Integer> lokalizacja_miejsc_rowerowych) {
        this.lokalizacja_miejsc_rowerowych = lokalizacja_miejsc_rowerowych;
    }
}
