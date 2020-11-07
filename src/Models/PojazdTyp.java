package Models;

public enum PojazdTyp{
    Autobus("Autobus"),
    Tramwaj("Tramwaj"),
    Metro("Metro");

    String typ;


    PojazdTyp(String s){
        typ = s;
    }

}
