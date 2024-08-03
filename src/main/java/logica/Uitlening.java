package logica;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Uitlening {
    private int id;
    private Timestamp datum;
    private Date terugbrengdatum;
    private int ontlenerId;
    private int lenerId;
    private ArrayList<Integer> components;
    private ArrayList<Integer> aantal;

    public Uitlening(Timestamp datum, Date Terugbrengdatum, int ontlenerId, int lenerId, ArrayList<Integer> components, ArrayList<Integer> aantal) {
        this.datum = datum;
        this.terugbrengdatum = Terugbrengdatum;
        this.ontlenerId = ontlenerId;
        this.lenerId = lenerId;
        this.components = components;
        this.aantal = aantal;
    }

    public Timestamp getDatum() {
        return datum;
    }

    public Date getTerugbrengdatum() {
        return terugbrengdatum;
    }

    public int getOntlenerId() {
        return ontlenerId;
    }

    public int getLenerId() {
        return lenerId;
    }

    public ArrayList<Integer> getComponentsid() {
        return components;
    }

    public ArrayList<Integer> getAantal() {
        return aantal;
    }


}
