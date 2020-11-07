package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

enum OsobaType{
    PRACOWNIK,
    PASAZER
}

@Entity(name = "Osoba")
public class Osoba {

    private long id;

    public static List<Osoba> osoby = new ArrayList<>();

    private static Map<String,Osoba> pesels = new HashMap<>();

    protected Set<OsobaType> types;

    private String imie,nazwisko;
    private String adresZamieszkania;
    private String numerTel;

    private String PESEL; //unique

    private Pracownik pracownik;
    private Pasazer pasazer;



    public Osoba(String imie,String nazwisko,String PESEL,String adresZamieszkania,String numerTel)throws Exception{

        if (pesels.containsKey(PESEL)) {
            if(!(pesels.get(PESEL).types.contains(OsobaType.PRACOWNIK) || pesels.get(PESEL).types.contains(OsobaType.PASAZER))) {


                throw new Exception("Osoba już zarejestrowana w systemie");
            }
        }

        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adresZamieszkania = adresZamieszkania;
        this.numerTel = numerTel;
        this.PESEL = PESEL;

        pesels.put(PESEL,this);

        osoby.add(this);
    }

    protected Osoba(){ //for hibernate

    }

    public static void readExtent(List<Osoba> lista) {
        osoby = lista;
        for(Osoba o:osoby){
            pesels.put(o.getPESEL(),o);
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


    @ElementCollection
    public Set<OsobaType> getTypes() {
        return types;
    }

    public void setTypes(Set<OsobaType> types) {
        this.types = types;
    }

    public void setTypes(EnumSet<OsobaType> types) {
        this.types = types;
    }

    @Basic
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    @Basic
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    @Basic
    public String getAdresZamieszkania() {
        return adresZamieszkania;
    }

    public void setAdresZamieszkania(String adresZamieszkania) {
        this.adresZamieszkania = adresZamieszkania;
    }

    @Basic
    public String getNumerTel() {
        return numerTel;
    }

    public void setNumerTel(String numerTel) {
        this.numerTel = numerTel;
    }

    @Basic
    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    @Transient
    public String getImieNazwisko(){
        return imie + " " + nazwisko;
    }

    @Override
    public String toString() {
        return getImieNazwisko();
    }

    public void usunOsobe(){
        usunPracownika();
        usunPasazera();
    }

    public void usunPracownika(){
        if(pracownik != null){
            //delete from db
        }
    }

    public void usunPasazera(){
        if(pasazer != null){
            //delete from db
        }
    }

    @OneToOne
    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    @OneToOne
    public Pasazer getPasazer() {
        return pasazer;
    }

    public void setPasazer(Pasazer pasazer) {
        this.pasazer = pasazer;
    }
}
