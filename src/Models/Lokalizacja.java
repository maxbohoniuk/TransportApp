package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="Lokalizacja")
public class Lokalizacja {

    private long id;

    double longitude;

    double latitude;

    public Lokalizacja(double longitude,double latitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Lokalizacja(){

    }

    @Override
    public String toString() {
        return "Longitude: " + longitude + "\n" + "Latitude: " + latitude;
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
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    @Basic
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
