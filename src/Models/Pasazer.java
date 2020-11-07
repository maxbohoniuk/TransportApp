package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

enum Ulga{
    NIEPELNOSPRAWNOSC,
    STUDENT,
    EMERYT,
    DZIECKO
}

@Entity(name = "Pasazer")
public class Pasazer extends Osoba {
    private long id;

    static List<Pasazer> pasazery = new ArrayList<>();

    @Transient
    private static List<String> blackList = new ArrayList<>();


    private boolean isOnBlackList;

    private Ulga ulga = null;

    private Osoba osoba;

    private List<Bilet> bilety = new ArrayList<>();


    private Pasazer(Osoba osoba) throws Exception{
        if(isOnBlackList(osoba.getPESEL())){
            throw new Exception("Pasazer jest na czarnej liscie");

        }
        this.osoba = osoba;

        if(osoba.types.isEmpty()){
            osoba.types = EnumSet.of(OsobaType.PASAZER);
        }
        else{
            osoba.types.add(OsobaType.PASAZER);
        }


        pasazery.add(this);




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
    public Ulga getUlga() {
        return ulga;
    }

    @OneToOne
    public Osoba getOsoba() {
        return osoba;
    }

    public void setOsoba(Osoba osoba) {
        this.osoba = osoba;
    }

    public static Pasazer createPasazer (Osoba osoba) throws Exception{
        if(osoba != null){
            Pasazer pasazer = new Pasazer(osoba);
            return pasazer;
        }
        else{
            throw new Exception("Osoba = null");
        }
    }


    public void kupBilet(Bilet bilet){
        bilety.add(bilet);
    }

    public void setUlga(Ulga ulga){
        this.ulga = ulga;
    }

    public void addToBlackList(Pasazer pasazer){
        blackList.add(pasazer.osoba.getPESEL());
        pasazer.isOnBlackList = true;
    }

    public static boolean isOnBlackList(String PESEL){
        return blackList.contains(PESEL);
    }

    public void usunPasazera(){
        osoba = null;
        osoba.usunPasazera();
    }

    @Basic
    public boolean isOnBlackList() {
        return isOnBlackList;
    }

    public void setOnBlackList(boolean onBlackList) {
        isOnBlackList = onBlackList;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Bilet> getBilety() {
        return bilety;
    }

    public void setBilety(List<Bilet> bilety) {
        this.bilety = bilety;
    }
}
