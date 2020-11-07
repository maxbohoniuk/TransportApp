package Services;

import Models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class DbService {

    private static DbService instance;

    StandardServiceRegistry registry = null;
    SessionFactory sessionFactory = null;

    Session session;


    private DbService(){
        //hibernate setting
        registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();


         session = sessionFactory.openSession();

         //addingExampleData();


    }

    public static DbService getInstance(){
        if(instance == null){
            instance = new DbService();
        }
        return instance;
    }

    public void addPrzystanekToDb(Przystanek p){
        Transaction transaction = session.beginTransaction();

        session.save(p);

        transaction.commit();

    }

    public void addTrasaToDb(Trasa t){
        Transaction transaction = session.beginTransaction();

        session.save(t);


        transaction.commit();

    }

    public void addPrzystanekNaTrasieToDb(PrzystanekNaTrasie przystanekNaTrasie){
        Transaction transaction = session.beginTransaction();

        session.save(przystanekNaTrasie);

        transaction.commit();
    }

    public void addPojazdNaTrasie(PojazdNaTrasie pojazdNaTrasie){
        Transaction transaction = session.beginTransaction();

        session.save(pojazdNaTrasie);


        transaction.commit();
    }

    public void refreshPojazdNaTrasie(PojazdNaTrasie pojazdNaTrasie){
        Transaction transaction = session.beginTransaction();

        session.refresh(pojazdNaTrasie);


        transaction.commit();

    }

    public void readAllData(){


        readAllTrasa(session);
        readAllPojazd(session);
        readAllAutobus(session);
        readAllTramwaj(session);
        readAllMetro(session);
        readAllOsoba(session);
        readAllPracownik(session);
        readAllKierowca(session);
        readAllPojazdNaTrasie(session);
        readAllPrzystanek(session);
        readAllPrzystanekNaTrasie(session);

    }

    public void readAllPrzystanek(Session ses){

        Query query = ses.createQuery("FROM Models.Przystanek");
        List<Przystanek> lista = query.list();
        Przystanek.readExtent(lista);
    }

    public void readAllAutobus(Session ses){
        Query query = ses.createQuery("FROM Models.Autobus");
        List<Autobus> lista = query.list();
        Autobus.readExtentAutobus(lista);
    }

    public void readAllKierowca(Session ses){
        Query query = ses.createQuery("FROM Models.Kierowca");
        List<Kierowca> lista = query.list();
        Kierowca.readExtentKierowca(lista);
    }
    public void readAllMetro(Session ses){

        Query query = ses.createQuery("FROM Models.Metro");
        List<Metro> lista = query.list();
        Metro.readExtentMetro(lista);

    }
    public void readAllOsoba(Session ses){

        Query query = ses.createQuery("FROM Models.Osoba");
        List<Osoba> lista = query.list();
        Osoba.readExtent(lista);
    }
    public void readAllPojazd(Session ses){

        Query query = ses.createQuery("FROM Models.Pojazd");
        List<Pojazd> lista = query.list();
        Pojazd.readExtent(lista);

    }
    public void readAllPojazdNaTrasie(Session ses){

        Query query = ses.createQuery("FROM Models.PojazdNaTrasie");
        List<PojazdNaTrasie> lista = query.list();
        PojazdNaTrasie.readExtent(lista);

    }
    public void readAllPracownik(Session ses){

        Query query = ses.createQuery("FROM Models.Pracownik");
        List<Pracownik> lista = query.list();
        Pracownik.readExtent(lista);

    }
    public void readAllPrzystanekNaTrasie(Session ses){

        Query query = ses.createQuery("FROM Models.PrzystanekNaTrasie");
        List<PrzystanekNaTrasie> lista = query.list();
        PrzystanekNaTrasie.readExtent(lista);

    }
    public void readAllTramwaj(Session ses){

        Query query = ses.createQuery("FROM Models.Tramwaj");
        List<Tramwaj> lista = query.list();
        Tramwaj.readExtentTramwaj(lista);

    }
    public void readAllTrasa(Session ses){

        Query query = ses.createQuery("FROM Models.Trasa");
        List<Trasa> lista = query.list();
        Trasa.readExtent(lista);

    }

    public void addingExampleData(){
        Transaction transaction = session.beginTransaction();

        Metro c = new Metro(2018, "ul. Targowa 18",  LocalDate.of(2020, 3, 20));
        Metro d = new Metro(2013, "ul. Kondratowicza 214",  LocalDate.of(2020, 4, 3));
        Metro e = new Metro(2015, "ul. Targowa 18",  LocalDate.of(2020, 3, 24));

        Autobus a = new Autobus(2012, "ul. Koszykowa 83",  LocalDate.of(2020, 3, 13), true, "WP2341F", 35);
        Autobus b = new Autobus(2007, "ul. Stalowa 36",  LocalDate.of(2020, 2, 27), false, "WA2124", 23);

        Tramwaj t1 = new Tramwaj(2011,"ul.Marszalkowska 241",LocalDate.of(2020,3,22));

        try {
            Kierowca k1 = new Kierowca("Jan","Kowalski","6571213","Warszawa","+48550156095",LocalDate.of(2004,3,21));
            k1.setDataWaznosciBadan(LocalDate.of(2023,3,21));
            Kierowca k2 = new Kierowca("Krzysztof","Nowak","8713129","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k2.setDataWaznosciBadan(LocalDate.of(2023,3,21));
            Kierowca k3 = new Kierowca("Maciej","Nowak","87131234","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k3.setDataWaznosciBadan(LocalDate.of(2023,3,21));
            Kierowca k4 = new Kierowca("Tomasz","Nowak","8123124","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k4.setDataWaznosciBadan(LocalDate.of(2023,3,21));
            Kierowca k5 = new Kierowca("Dawid","Nowak","32423422","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k5.setDataWaznosciBadan(LocalDate.of(2023,3,21));
            Kierowca k6 = new Kierowca("Andrzej","Nowak","3242342","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k6.setDataWaznosciBadan(LocalDate.of(2023,6,11));
            Kierowca k7 = new Kierowca("Marcel","Nowak","32423423","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k7.setDataWaznosciBadan(LocalDate.of(2025,7,8));
            Kierowca k8 = new Kierowca("Piotr","Nowak","32423424","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k8.setDataWaznosciBadan(LocalDate.of(2028,2,26));
            Kierowca k9 = new Kierowca("Boleslaw","Nowak","32423425","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k9.setDataWaznosciBadan(LocalDate.of(2027,5,29));
            Kierowca k10 = new Kierowca("Gustaw","Nowak","32423426","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k10.setDataWaznosciBadan(LocalDate.of(2024,8,17));
            Kierowca k11 = new Kierowca("Edward","Nowak","32423427","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k11.setDataWaznosciBadan(LocalDate.of(2025,7,12));
            Kierowca k12 = new Kierowca("Radoslaw","Nowak","32423428","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k12.setDataWaznosciBadan(LocalDate.of(2026,10,20));
            Kierowca k13 = new Kierowca("Filip","Nowak","32423429","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k13.setDataWaznosciBadan(LocalDate.of(2024,12,14));
            Kierowca k14 = new Kierowca("Marcin","Nowak","32423431","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k14.setDataWaznosciBadan(LocalDate.of(2027,6,4));
            Kierowca k15 = new Kierowca("Bartosz","Nowak","32423432","Warszawa","+48794225975",LocalDate.of(2012,7,3));
            k15.setDataWaznosciBadan(LocalDate.of(2026,8,25));

            session.save(k1);
            session.save(k1.osoba);
            session.save(k2);
            session.save(k2.osoba);
            session.save(k3);
            session.save(k3.osoba);
            session.save(k4);
            session.save(k4.osoba);
            session.save(k5);
            session.save(k5.osoba);
            session.save(k6);
            session.save(k6.osoba);
            session.save(k7);
            session.save(k7.osoba);
            session.save(k8);
            session.save(k8.osoba);
            session.save(k9);
            session.save(k9.osoba);
            session.save(k10);
            session.save(k10.osoba);
            session.save(k11);
            session.save(k11.osoba);
            session.save(k12);
            session.save(k12.osoba);
            session.save(k13);
            session.save(k13.osoba);
            session.save(k14);
            session.save(k14.osoba);
            session.save(k15);
            session.save(k15.osoba);
        } catch (Exception ex) {
            ex.printStackTrace();
        }





        session.save(c);
        session.save(d);
        session.save(e);

        session.save(a);
        session.save(b);

        session.save(t1);

        transaction.commit();
    }

    public void close(){
        session.close();
    }





}
