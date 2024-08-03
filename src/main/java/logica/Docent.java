package logica;

import java.util.Date;

public class Docent extends Personen{
    private int id;
    private String voornaam;
    private String familienaam;
    private int nummer;
    private String vakgebied;
    private Date beginjaar;

    public Docent(String voornaam, String familienaam, long nummer, String vakgebied, Date beginjaar) {
        super(voornaam, familienaam, nummer);
        this.vakgebied = vakgebied;
        this.beginjaar = beginjaar;
    }

    public Docent(int id, String voornaam, String familienaam, long nummer, String vakgebied, Date beginjaar) {
        super(id, voornaam, familienaam, nummer);
        this.vakgebied = vakgebied;
        this.beginjaar = beginjaar;
    }

    public String getVakgebied() {
        return vakgebied;
    }

    public Date getBeginjaar() {
        return beginjaar;
    }

}
