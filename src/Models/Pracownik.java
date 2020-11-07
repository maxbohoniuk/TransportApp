package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Entity(name = "Pracownik")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pracownik{

    private long id;

    static List<Pracownik> pracowniki = new ArrayList<>();

    private LocalDate dataZatrudnienia;

    @Transient
    protected final static double min_StawkaGodzinowa = 13.50;

    @Transient
    protected  Pasazer pasazerInstance = null; //overlapping

    public Osoba osoba;

    protected Pracownik(String imie,String nazwisko,String PESEL,String adresZamieszkania,String numerTel)throws Exception{

            this.osoba = new Osoba(imie,nazwisko,PESEL,adresZamieszkania,numerTel);
            this.osoba.types = EnumSet.of(OsobaType.PRACOWNIK);
            setDataZatrudnienia();
            pracowniki.add(this);

    }

    protected Pracownik(){

    }

    @OneToOne
    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public static void readExtent(List<Pracownik> lista) {
        pracowniki = lista;
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

    public void setDataZatrudnienia(){
        dataZatrudnienia = LocalDate.now();
    }

    public void setDataZatrudnienia(int year,int month,int day){
        dataZatrudnienia = LocalDate.of(year,month,day);
    }

    @Basic
    public LocalDate getDataZatrudnienia(){
        return dataZatrudnienia;
    }

    public void setDataZatrudnienia(LocalDate data){
        dataZatrudnienia = data;
    }

    @Transient
    public abstract double getStawkaGodzinowa();






    @Transient
    public abstract void wprowadzSzczegoly(String s);

    public void setAsPasazer(boolean b){
        if(b){
            if(!osoba.types.contains(OsobaType.PASAZER)) {
                try {
                    pasazerInstance = Pasazer.createPasazer(this.osoba);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            if(osoba.types.contains(OsobaType.PASAZER)){
                osoba.types.remove(OsobaType.PASAZER);
                pasazerInstance = null;
            }

        }
    }

    @Transient
    public Pasazer getPasazerInstance()throws Exception{
        if(!osoba.types.contains(OsobaType.PASAZER)){
            throw new Exception("Nie jest pasazerem!");
        }
        return osoba.getPasazer();
    }

    public void usunPracownika(){
        osoba = null;
        osoba.usunPracownika();
    }





    @Override
    public String toString() {
        return osoba.getImieNazwisko();
    }
}
