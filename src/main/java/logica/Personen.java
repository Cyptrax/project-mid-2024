package logica;

public class Personen {

    private int id;
    private String voornaam;
    private String familienaam;
    private long nummer;


    public Personen(String voornaam, String familienaam, long nummer) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.nummer = nummer;
    }

    public Personen(int id, String voornaam, String familienaam, long nummer) {
        this.id = id;
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.nummer = nummer;
    }

    public int getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getfamilienaam() {
        return familienaam;
    }
    public long getNummer() {
        return nummer;
    }
}
