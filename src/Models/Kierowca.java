package Models;

import Services.Main;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Kierowca")
public class Kierowca extends Pracownik {

    private long id;

    public static List<Kierowca> kierowcy = new ArrayList<>();

    private LocalDate dataOtrzymaniaPrawaJazdy;
    private LocalDate dataWaznosciBadan;


    private PojazdNaTrasie pojazdNaTrasie; //asocjacja zwykla



    @ManyToOne(cascade = CascadeType.ALL)
    public PojazdNaTrasie getPojazdNaTrasie() {
        return pojazdNaTrasie;
    }

    public Kierowca(String imie,String nazwisko,String PESEL,String adresZamieszkania,String numerTel, LocalDate dataOtrzymaniaPrawaJazdy)throws Exception{
        super(imie, nazwisko, PESEL, adresZamieszkania, numerTel);
        this.dataOtrzymaniaPrawaJazdy = dataOtrzymaniaPrawaJazdy;

        kierowcy.add(this);


    }

    protected Kierowca(){ //for hibernate

    }

    public void setDataWaznosciBadan(int year,int month,int day) throws Exception {
        LocalDate date = LocalDate.of(year,month,day);
        if(date.compareTo(LocalDate.now()) < 0){
            throw new Exception("Bledna data");

        }
        else{
            dataWaznosciBadan = date;
        }
    }

    public static void readExtentKierowca(List<Kierowca> lista) {
        kierowcy = lista;
    }

    @Transient
    public long getDoswiadczenie(){
        //oblicz sie na podstawie dataOtrzymaniaPrawaJazdy
        return ChronoUnit.YEARS.between(dataOtrzymaniaPrawaJazdy,LocalDate.now());
    }


    public void setPojazdNaTrasie(PojazdNaTrasie p) throws Exception{




        if(pojazdNaTrasie != p) {
            if (pojazdNaTrasie != null) {
                pojazdNaTrasie.removeKierowca(this);
            }
            pojazdNaTrasie = p;
            try {
                p.przydzielKierowce(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Transient
    @Override
    public double getStawkaGodzinowa() {
        long experience = getDoswiadczenie();
        if(experience < 3){
            return Pracownik.min_StawkaGodzinowa;
        }
        else{
            if(experience < 7){
                return 17.40;
            }
            else{
                return 22.80;
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

    @Basic
    public LocalDate getDataOtrzymaniaPrawaJazdy() {
        return dataOtrzymaniaPrawaJazdy;
    }

    public void setDataOtrzymaniaPrawaJazdy(LocalDate dataOtrzymaniaPrawaJazdy) {
        this.dataOtrzymaniaPrawaJazdy = dataOtrzymaniaPrawaJazdy;
    }

    @Basic
    public LocalDate getDataWaznosciBadan() {
        return dataWaznosciBadan;
    }

    public void setDataWaznosciBadan(LocalDate dataWaznosciBadan) {
        this.dataWaznosciBadan = dataWaznosciBadan;
    }

    @Override
    public void wprowadzSzczegoly(String s) {
        getPojazdNaTrasie().setWykorzystanePaliwo(Double.parseDouble(s));
    }

    @Override
    public String toString() {
        return this.osoba.getImieNazwisko() + " | " + getDoswiadczenie() + " | " + getDataWaznosciBadan();
    }
}
